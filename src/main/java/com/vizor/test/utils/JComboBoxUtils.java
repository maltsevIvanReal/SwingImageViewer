package com.vizor.test.utils;


import com.vizor.test.components.ImagesPanel;

import javax.swing.*;

public class JComboBoxUtils extends JFrame {

    public static int calculateCountImagePages(int countAllFiles) {
        return (int) Math.ceil((double) countAllFiles / (double) ImagesPanel.MAX_IMAGES_PER_PAGE);
    }

    public static String[] getPagesAsArray(int countOfPages) {
        String[] pagesSelect = new String[countOfPages];

        for (int i = 0; i < countOfPages; i++) {
            pagesSelect[i] = String.valueOf(i + 1);
        }
        return pagesSelect;
    }
}
