package com.mark.misc;

/**
 * Author: Mark
 * Date  : 2015/3/13
 * Time  : 12:14
 */
public class PrivateMatter {
    public static void main(String[] args) {
//        System.out.println(new com.mark.misc.Child().name); //编译错误
        System.out.println(((Parent)new Child()).name);
    }
}
/*
override 针对实例方法
hidden 针对静态方法、属性、内部类
override 父类方法不能被调用，除非在子类内部使用super
hidden 父类属性、静态方法等可以通过强制类型转换被调用
 */
class Parent {
    public String name = "com.mark.misc.Parent";
}

class Child extends Parent {
    private String name = "com.mark.misc.Child";
}