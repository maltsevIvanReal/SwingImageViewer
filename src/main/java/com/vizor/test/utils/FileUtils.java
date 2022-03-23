package com.vizor.test.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.*;

public class FileUtils {
    public static File getFileByName(String fileName) {
        return new File(fileName);
    }

    public static int getCountFiles(ArrayList<File> files) {
        return files.size();
    }

    public static ArrayList<File> getSortedFilesByLastModified(File path) {
        File[] files = path.listFiles();
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        return new ArrayList<>(Arrays.asList(files));
    }

    public static void setFileFilterImages(JFileChooser fc, boolean setAllFilesAccept, boolean allImagesFilter) {
        if (!setAllFilesAccept) {
            fc.setAcceptAllFileFilterUsed(false);
        }

        if (allImagesFilter) {
            fc.setFileFilter(new FileNameExtensionFilter("ALL IMAGES", "jpeg", "png", "gif", "jpg"));
        }

        HashMap<String, String> filters = new LinkedHashMap<>();
        filters.put("JPEG", "jpeg");
        filters.put("PNG", "png");
        filters.put("GIF", "gif");
        filters.put("JPG", "jpg");

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            fc.setFileFilter(new FileNameExtensionFilter(entry.getKey(), entry.getValue()));
        }
    }
}
