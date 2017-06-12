package by.suboch.javacore.classloader;


/**
 *
 */
public class Demo {
    public static void main(String[] args) {
        FileClassLoader fileClassLoader = new FileClassLoader("data/classloader/out");
        JarClassLoader jarClassLoader = new JarClassLoader("data/classloader/lib");
        try {
            String name = "Module";
            Class clazz = fileClassLoader.loadClass(name);
            Class clazz2 = jarClassLoader.loadClass(name);
            Object instance = clazz.newInstance();
            Object instance2 = clazz2.newInstance();
            System.out.println(instance.getClass().getClassLoader());
            System.out.println(instance2.getClass().getClassLoader());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
