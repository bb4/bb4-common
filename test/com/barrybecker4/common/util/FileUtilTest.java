package com.barrybecker4.common.util;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class FileUtilTest {


    @Test
    public void testFilesInDirectory() {

        String dir = "test/com/barrybecker4/common/util";
        List<File> files = FileUtil.getFilesInDirectory(dir);
        assertEquals("Unexpected number of entriesfiles in dir " + dir, 5, files.size());
    }

    @Test
    public void testGetRoot() {

        String dir = FileUtil.getHomeDir() + "test/com/barrybecker4/common/util";
        List<File> files = FileUtil.getFilesInDirectory(dir);
        assertEquals("Unexpected number of entriesfiles in dir " + dir, 5, files.size());
    }
}
