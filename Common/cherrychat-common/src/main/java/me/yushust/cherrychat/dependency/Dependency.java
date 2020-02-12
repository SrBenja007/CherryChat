package me.yushust.cherrychat.dependency;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class Dependency {

    private static final String MAVEN_REPO = "https://repo1.maven.org/maven2/";
    private static final ClassLoader SYSTEM_CLASS_LOADER = ClassLoader.getSystemClassLoader();
    private static final Method ADD_URL_CLASSLOADER_METHOD;

    private File folder;
    private String groupId;
    private String artifactId;
    private String version;

    public Dependency(File folder, String groupId, String artifactId, String version) {
        this.folder = folder;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    static {
        try {
            ADD_URL_CLASSLOADER_METHOD = SYSTEM_CLASS_LOADER.getClass().getDeclaredMethod("addURL", URL.class);
            ADD_URL_CLASSLOADER_METHOD.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("ignored")
    public void download() {
        /*
        * Downloading JAR File
        * */
        String urlString = MAVEN_REPO +
                groupId.replace('.', '/') + "/" +
                artifactId + "/" +
                version + "/" +
                artifactId + "-" + version +
                ".jar";

        boolean folderExists = false;
        if(!folder.exists()) {
            folderExists = folder.mkdirs();
        }

        if(!folderExists) return;

        File file = new File(folder, artifactId + "-" + version + ".jar");

        boolean fileExists;

        try {
            if(!file.exists()) {
                fileExists = file.createNewFile();
            } else {
                return;
            }

            if(!fileExists) return;

            URL url = new URL(urlString);
            InputStream inStream = url.openStream();
            BufferedInputStream bufIn = new BufferedInputStream(inStream);

            OutputStream out= new FileOutputStream(file);
            BufferedOutputStream bufOut = new BufferedOutputStream(out);

            byte[] buffer = new byte[4096];
            while (true) {
                int nRead = bufIn.read(buffer, 0, buffer.length);
                if (nRead <= 0) break;
                bufOut.write(buffer, 0, nRead);
            }

            bufOut.flush();
            out.close();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        * Adding JAR to class path
        * */
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            ADD_URL_CLASSLOADER_METHOD.invoke(classLoader, file.toURI().toURL());
        } catch (IllegalAccessException | InvocationTargetException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
