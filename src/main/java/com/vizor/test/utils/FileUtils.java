package com.vizor.test.utils;

import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.*;

public class FileUtils {

    public final static int MAX_FILE_NAME_LENGTH = 4;

    public static File getFileByName(String fileName) {
        return new File(fileName);
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

    public static String truncateFileName(String fileName) {
        if (fileName.length() <= MAX_FILE_NAME_LENGTH) {
            return fileName;
        }
        return fileName.substring(0, MAX_FILE_NAME_LENGTH) + "...";
    }

    public static String changeFileName(String fileName, File folder) {
        String baseName = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        String newFileName = baseName + "1" + "." + extension;
        String newDestination = folder.getAbsolutePath() + File.separator + newFileName;
        if (new File(newDestination).isFile()) {
            newFileName = changeFileName(newFileName, folder);
        }
        return newFileName;
    }
}
