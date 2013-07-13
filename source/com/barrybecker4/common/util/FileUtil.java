/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.util;

import com.barrybecker4.common.app.ClassLoaderSingleton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;

/**
 * Miscellaneous commonly used file related static utility methods.
 * @author Barry Becker
 */
public final class FileUtil {

    /**
     * Get the correct file separator whether on windows (\) or linux (/).
     * Getting error in applets if trying to use System.getProperty("file.separator")
     */
    public static final String FILE_SEPARATOR = "/";

    /**
     * Points to the main project directory.
     * If not deployed, you can use System.getenv("PROJECT_HOME") + FILE_SEPARATOR;
     */
    public static final String PROJECT_HOME = getProjectHomeDir();

    /**
     * cannot instantiate static class.
     */
    private FileUtil() {}

    /**
     * @return home directory. Assumes running as an Application.
     */
    public static String getHomeDir() {
        return PROJECT_HOME;
    }

    /**
     * @return Current working directory if possible
     */
    private static String getProjectHomeDir() {
        String home = System.getProperty("user.dir");
        System.out.println("home=" + home);
        return home + FILE_SEPARATOR;
    }

    /**
     * Tries to create the specified directory if it does not exist.
     * @param path path to the directory to verify
     * @throws IOException if any problem creating the specified directory
     */
    public static void verifyDirectoryExistence(String path) throws IOException {
        File directory = new File(path);

        if (!directory.exists()) {
            boolean success = directory.mkdir();
            if (!success) {
                throw new IOException("Could not create directory: " + directory.getAbsolutePath());
            }
        }
    }

    /**
     * create a PrintWriter with utf8 encoding
     * returns null if there was a problem creating it.
     * @param filename including the full path
     * @return new PrintWriter instance
     */
    public static PrintWriter createPrintWriter( String filename ) {
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream( filename, false ),
                                    "UTF-8" ) ) ); //NON-NLS
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outfile;
    }


    /**
     * @return a URL given the path to a file.
     */
    public static URL getURL(String sPath) {

        return getURL(sPath, true);
    }

    /**
     * @return a URL given the path to an existing file.
     */
    public static URL getURL(String sPath, boolean failIfNotFound) {

        URL url = ClassLoaderSingleton.getClassLoader().getResource(sPath);
        assert (url != null || !failIfNotFound):
            "failed to create url for  " + sPath;

        return url;
    }
}
