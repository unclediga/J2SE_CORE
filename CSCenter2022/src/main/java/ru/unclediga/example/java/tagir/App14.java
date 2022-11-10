package ru.unclediga.example.java.tagir;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Hello world!
 *
 */
public class App14 {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        System.out.println("Hello World!");
        new MyRunnable().run();

        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (name.endsWith("MyRunnable")) {
                    try (InputStream stream = MyRunnable.class.getResourceAsStream("MyRunnable.class")) {
                        System.out.println("availible bytes " + stream.available());
                        
                        byte[] classData = new byte[10000000];                                         
                        int bytesReaded = stream.read(classData);
                        System.out.println("readed " + bytesReaded + " bytes");
                        // = stream.readAllBytes();
                        return defineClass(name, classData, 0, bytesReaded);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return super.loadClass(name);
            }

        };

        Class<?> loadedClass = loader.loadClass("ru.unclediga.example.java.tagir.MyRunnable");
        Runnable loaded = loadedClass.asSubclass(Runnable.class).getConstructor().newInstance();
        loaded.run();
    }
}
