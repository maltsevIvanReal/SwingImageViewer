package com.vizor.test.utils;

import javax.swing.*;
import java.io.File;

public class JComboBoxUtils {
    public static JComboBox createJComboBox(String[] pagesArray) {
        return new JComboBox(pagesArray);
    }

    public static int calculateCountImagePages(int countAllFiles) {
        return (int) Math.ceil((double) countAllFiles / (double) PanelUtils.MAX_IMAGES_PER_PAGE);
    }

    public static String[] getPagesAsArray(int countOfPages) {
        String[] pagesSelect = new String[countOfPages];

        for (int i = 0; i < countOfPages; i++) {
            pagesSelect[i] = String.valueOf(i + 1);
        }
        return pagesSelect;
    }

    public static void setPaginateActionListener(JComboBox jComboBox, JPanel imagesPanel, File[] allFiles, JLabel[] label, JFrame mainJFrame) {
        jComboBox.addActionListener(e -> {
            imagesPanel.removeAll();
            imagesPanel.repaint();
            int selectedPageNumber = Integer.parseInt((String) jComboBox.getSelectedItem());
            PanelUtils.fillImageContainer(allFiles, PanelUtils.getDisplayFrom(selectedPageNumber, allFiles), label, imagesPanel);
            mainJFrame.getContentPane().invalidate();
            mainJFrame.getContentPane().validate();
        });
    }
}
