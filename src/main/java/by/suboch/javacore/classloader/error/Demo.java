package by.suboch.javacore.classloader.error;

import by.suboch.javacore.classloader.FileClassLoader;

/**
 *
 */
public class Demo {
    public static void main(String[] args) {
        FileClassLoader fileClassLoader = new FileClassLoader("data/classloader/out");
        String testClassName = "by.suboch.javacore.classloader.error.Test";
        Class textClass = null;
        try {
            textClass = fileClassLoader.loadClass(testClassName);
            ITest1 iTest = (ITest1) textClass.newInstance();
            System.out.println(iTest.getClass().getClassLoader());
            iTest.show();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

/*
Exception in thread "main" java.lang.NoClassDefFoundError: by/suboch/javacore/classloader/error/ITest
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:642)
	at by.suboch.javacore.classloader.FileClassLoader.findClass(FileClassLoader.java:31)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	at by.suboch.javacore.classloader.error.Demo.main(Demo.java:14)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)
Caused by: java.lang.ClassNotFoundException: by.suboch.javacore.classloader.error.ITest
	at java.lang.ClassLoader.findClass(ClassLoader.java:530)
	at by.suboch.javacore.classloader.FileClassLoader.findClass(FileClassLoader.java:33)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 12 more
*/
