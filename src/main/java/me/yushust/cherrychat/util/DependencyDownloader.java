package me.yushust.cherrychat.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DependencyDownloader {

    private static Method classLoaderAddUrl;

    private DependencyDownloader() throws IllegalAccessException {
        throw new IllegalAccessException("This class couldn't be instantiated.");
    }

    public static void downloadFile(URL url, File file) throws IOException {

        InputStream inStream = url.openStream();
        System.out.println(inStream.available());
        BufferedInputStream bufIn = new BufferedInputStream(inStream);

        OutputStream out= new FileOutputStream(file);
        BufferedOutputStream bufOut = new BufferedOutputStream(out);
        byte[] buffer = new byte[4096];
        while (true) {
            int nRead = bufIn.read(buffer, 0, buffer.length);
            if (nRead <= 0)
                break;
            bufOut.write(buffer, 0, nRead);
        }

        bufOut.flush();
        out.close();
        inStream.close();
    }

    public static void addJarToClasspath(File jar) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, MalformedURLException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        if (classLoaderAddUrl == null) {
            Class<?> clazz = classLoader.getClass();

            Method method = clazz.getSuperclass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);

            classLoaderAddUrl = method;
        }

        classLoaderAddUrl.invoke(classLoader, jar.toURI().toURL());
    }
}

