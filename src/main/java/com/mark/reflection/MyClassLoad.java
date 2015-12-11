package com.mark.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Author: Mark
 * Date  : 15/12/11.
 */
public class MyClassLoad extends ClassLoader {


    /**
     * Creates a new class loader using the specified parent class loader for
     * delegation.
     * <p>
     * <p> If there is a security manager, its {@link
     * SecurityManager#checkCreateClassLoader()
     * <tt>checkCreateClassLoader</tt>} method is invoked.  This may result in
     * a security exception.  </p>
     *
     * @param parent The parent class loader
     * @throws SecurityException If a security manager exists and its
     *                           <tt>checkCreateClassLoader</tt> method doesn't allow creation
     *                           of a new class loader.
     * @since 1.2
     */
    protected MyClassLoad(ClassLoader parent) {
        super(parent);
    }

    /**
     * Loads the class with the specified <a href="#name">binary name</a>.
     * This method searches for classes in the same manner as the {@link
     * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
     * machine to resolve class references.  Invoking this method is equivalent
     * to invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
     * false)</tt>}.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class was not found
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (!"com.mark.reflection.TestObject".equals(name)) {
            return super.loadClass(name);
        }

        File file = new File("target/classes/"+"com.mark.reflection.TestObject".replaceAll("\\.", "/")+".class");
        try {
            InputStream in = new FileInputStream(file);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            return defineClass(name, buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
