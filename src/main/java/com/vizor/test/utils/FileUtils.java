package com.vizor.test.utils;

import java.io.File;

public class FileUtils {
    public static File getFileByName(String fileName) {
        return new File(fileName);
    }

    public static File[] getListOfFiles(File path) {
        return path.listFiles();
    }

    public static int getCountFiles(File[] files) {
        return files.length;
    }
}
