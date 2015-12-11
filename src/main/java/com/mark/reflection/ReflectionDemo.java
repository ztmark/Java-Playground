package com.mark.reflection;


import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Mark
 * Date  : 15/12/10.
 */
public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
//        innerClsDemo();
//        arrayDemo();
//        genericDemo();


        MyClassLoad mcl = new MyClassLoad(ReflectionDemo.class.getClassLoader());
        Class cls = mcl.loadClass("com.mark.reflection.TestObject");
        Object obj = cls.newInstance();
        TestObject testObject = new TestObject();
        System.out.println(obj); //com.mark.reflection.TestObject@123772c4
        Method method = cls.getMethod("method", null);
        method.invoke(obj); // TestObject
        System.out.println(testObject); //com.mark.reflection.TestObject@2d363fb3
        testObject.method(); // TestObject
        System.out.println(obj instanceof TestObject); // false
        System.out.println(testObject instanceof TestObject); // true


    }

    private static void genericDemo() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {
        Class cls = Class.forName("com.mark.reflection.GenericClass");
        Method method = cls.getMethod("method1", null);
        System.out.println(method.getGenericReturnType());
        System.out.println(method.getReturnType());
        method = cls.getMethod("method2", null);
        System.out.println(method.getGenericReturnType());
        System.out.println(method.getReturnType());
        Field field = cls.getField("val");
        System.out.println(field.getGenericType());
        System.out.println(field.getType());

        System.out.println("++++++++++");
        method = cls.getMethod("method4", List.class);
        Parameter parameter = method.getParameters()[0];
        Type type = parameter.getType();
        System.out.println(type); // interface java.util.List
        type = parameter.getParameterizedType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            System.out.println(pt); // java.util.List<java.lang.String>
            System.out.println(Arrays.toString(pt.getActualTypeArguments())); //[class java.lang.String]
        }
    }

    private static void arrayDemo() throws ClassNotFoundException {
        Class cls = int[].class;
        System.out.println(cls);
        cls = Class.forName("[I");
        System.out.println(cls);
        System.out.println(cls.getComponentType());
        int[] ints = (int[]) Array.newInstance(int.class, 2);
        System.out.println(ints.length);

        System.out.println(boolean[].class);
        System.out.println(byte[].class);
        System.out.println(char[].class);
        System.out.println(short[].class);
        System.out.println(int[].class);
        System.out.println(long[].class);
        System.out.println(float[].class);
        System.out.println(double[].class);
    }

    private static void innerClsDemo() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class cls = Class.forName("com.mark.reflection.GenericClass$InnerClass");
        // NoSuchMethodException
//        GenericClass.InnerClass ic = (GenericClass.InnerClass) cls.getConstructor().newInstance();
//        ic.method1();

        Class outerCls = cls.getEnclosingClass();
        @SuppressWarnings("unchecked")
        GenericClass<String> gc = (GenericClass<String>) outerCls.<String>newInstance();
        @SuppressWarnings("unchecked")
        GenericClass.InnerClass ic = (GenericClass.InnerClass) cls.getConstructor(GenericClass.class).newInstance(gc);
        ic.method1();

        System.out.println("=======");
        cls = Class.forName("com.mark.reflection.GenericClass");
        GenericClass<String> gcs = (GenericClass<String>) cls.<String>newInstance();
        GenericClass.InnerClass gi = gcs.new InnerClass();
        gi.method1();
    }


}
