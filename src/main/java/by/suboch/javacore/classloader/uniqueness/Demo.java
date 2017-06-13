package by.suboch.javacore.classloader.uniqueness;

import by.suboch.javacore.classloader.FileClassLoader;

/**
 *
 */
public class Demo {
    public static void main(String[] args) {
        FileClassLoader fileClassLoader1 = new FileClassLoader("data/classloader/out");
        FileClassLoader fileClassLoader2 = new FileClassLoader("data/classloader/out");
        String classNme = "Module";

        Class clazz1 = null;
        Class clazz2 = null;
        Class clazz3 = null;
        try {
            clazz1 = fileClassLoader1.loadClass(classNme);
            clazz2 = fileClassLoader2.loadClass(classNme);
            clazz3 = fileClassLoader2.loadClass(classNme);
            Object iTest1 = clazz1.newInstance();
            Object iTest2 = clazz2.newInstance();
            Object iTest3 = clazz3.newInstance();

            System.out.println(iTest1.getClass().equals(iTest2.getClass())); // false
            System.out.println(iTest2.getClass().equals(iTest3.getClass())); // true
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


    }
}
