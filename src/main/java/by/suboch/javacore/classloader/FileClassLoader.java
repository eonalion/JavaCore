package by.suboch.javacore.classloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class FileClassLoader extends ClassLoader {
    private static final String CLASS_EXTENSION = ".class";
    private String path;


    public FileClassLoader(ClassLoader parent, String path) {
        super(parent);
        this.path = path;
    }

    public FileClassLoader(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] bytes = fetchBytes(name + CLASS_EXTENSION);
            return super.defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            return super.findClass(name);
        }
    }

    private byte[] fetchBytes(String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(path, fileName));
    }
}
