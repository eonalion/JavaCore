package by.suboch.javacore.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class JarClassLoader extends ClassLoader {
    private static final String JAR_EXTENSION = ".jar";
    private static final String CLASS_EXTENSION = ".class";
    private static final String DEFAULT_PATH = "";
    private static final String PACKAGE_SEPARATOR = ".";
    private static final String FILE_SEPARATOR = "/";
    private static final String PATH_SEPARATOR = " ";
    private String path;


    public JarClassLoader(ClassLoader parent, String path) {
        super(parent);
        this.path = path;
    }

    public JarClassLoader(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File dir = new File(path);
        File[] jars = dir.listFiles((f, s) -> s.endsWith(JAR_EXTENSION));

        if (jars != null) {
            for (File file : jars) {
                try (JarFile jarFile = new JarFile(file)) {
                    List<String> classPaths = defineClassPaths(jarFile);
                    Enumeration<JarEntry> enumeration = jarFile.entries();

                    while (enumeration.hasMoreElements()) {
                        JarEntry entry = enumeration.nextElement();
                        Optional<String> tempClassPath = classPaths.stream().filter(entry.getName()::startsWith).findFirst();

                        if (tempClassPath.isPresent() && !entry.isDirectory() && entry.getName().endsWith(CLASS_EXTENSION)) {
                            String jarClassName = entry.getName().split(CLASS_EXTENSION)[0];
                            String className = processClassPath(tempClassPath.get(), name);
                            if (className.equals(jarClassName)) {
                                byte[] bytes = fetchBytes(jarFile.getInputStream(entry));
                                return super.defineClass(name, bytes, 0, bytes.length);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new ClassNotFoundException("Error while initializing JarFile", e);
                }
            }
        }
        return super.findClass(name);
    }

    private String processClassPath(String classPath, String className) {
        String path = DEFAULT_PATH;
        if (!classPath.isEmpty()) {
            path = classPath + FILE_SEPARATOR;
        }
        return path + className.replace(PACKAGE_SEPARATOR, FILE_SEPARATOR);
    }

    private List<String> defineClassPaths(JarFile jarFile) throws IOException {
        Manifest manifest = jarFile.getManifest();
        Attributes attributes = manifest.getMainAttributes();
        String classpath = attributes.getValue(Attributes.Name.CLASS_PATH);
        List<String> classPaths = Stream.of(DEFAULT_PATH).collect(Collectors.toList());
        if (classpath != null) {
            classPaths = Arrays.stream(classpath.split(PATH_SEPARATOR)).collect(Collectors.toList());
        }
        return classPaths;
    }

    private byte[] fetchBytes(InputStream is) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[0xFFFF];
            for (int len; (len = is.read(buffer)) != -1; ) {
                os.write(buffer, 0, len);
            }
            os.flush();
            return os.toByteArray();
        }
    }
}
