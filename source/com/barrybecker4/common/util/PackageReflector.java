package com.barrybecker4.common.util;

import com.barrybecker4.common.app.ClassLoaderSingleton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Use to find all classes in a specified package on the classpath.
 * Could be useful in a plugin implementation.
 *
 * @author Barry Becker
 */
public class PackageReflector {

    private static final String CLASS_EXT = ".class";
    /**
     * Finds all classes in the specified package accessible from the class loader.
     *
     * @param packageName the package to search in.
     * @return a list of classes found in the classpath
     */
    public List<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {

        ClassLoader classLoader = ClassLoaderSingleton.getClassLoader();
        String path = packageName.replace('.', '/');

        List<File> files = getFiles(classLoader, path);
        return getClassesFromFiles(packageName, files);
    }


    private List<File> getFiles(ClassLoader classLoader, String path) throws IOException {

        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> files = new ArrayList<File>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File dir = new File(resource.getFile());
            files.addAll(Arrays.asList(dir.listFiles()));
        }
        return files;
    }


    private ArrayList<Class> getClassesFromFiles(String packageName, List<File> files)
            throws ClassNotFoundException {

        ArrayList<Class> classes = new ArrayList<Class>();
        for (File file : files) {
            if (file.getName().endsWith(CLASS_EXT)) {
                String className = file.getName().substring(0, file.getName().length() - CLASS_EXT.length());
                classes.add(Class.forName(packageName + '.' + className));
            }
        }
        return classes;
    }
}