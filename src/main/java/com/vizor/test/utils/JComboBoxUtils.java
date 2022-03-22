package com.vizor.test.utils;

import com.vizor.test.ImageViewer;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class JComboBoxUtils extends JFrame {
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

    public static void setPaginateActionListener(JComboBox jComboBox, JPanel imagesPanel, ArrayList<File> allFiles, JLabel[] label, JFrame mainJFrame) {
        jComboBox.addActionListener(e -> {
            int selectedPageNumber = Integer.parseInt((String) jComboBox.getSelectedItem());
            ImageViewer.setCurrentPageSelected(selectedPageNumber);
            jComboBox.disable();
            start(allFiles, selectedPageNumber, label, imagesPanel, mainJFrame);
        });
    }

    private static void start(ArrayList<File> allFiles, int selectedPageNumber, JLabel[] label, JPanel imagesPanel, JFrame mainJFrame) {
        if (ImageViewer.getSwingWorkerPaginate() != null && !ImageViewer.getSwingWorkerPaginate().getState().equals(SwingWorker.StateValue.DONE)) {
            SwingWorker.StateValue state = ImageViewer.getSwingWorkerPaginate().getState();
            return;
        }
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                PanelUtils.clearJPanel(imagesPanel);
                PanelUtils.addLoaderAtImagePage();
                PanelUtils.fillImageContainer(allFiles, PanelUtils.getDisplayFrom(selectedPageNumber, allFiles), label, imagesPanel, mainJFrame);
                return null;
            }

            @Override
            protected void done() {
                JPanel imagerPanel = ImageViewer.getImagesPanel();
                JLabel loaderImage = ImageViewer.getLoaderImage();
                imagerPanel.remove(loaderImage);
                imagerPanel.repaint();

                JComboBox jComboBox = ImageViewer.getjComboBox();
                jComboBox.enable();
                jComboBox.repaint();
                mainJFrame.getContentPane().invalidate();
                mainJFrame.getContentPane().validate();
            }
        };

        ImageViewer.setSwingWorkerPaginate(swingWorker);

        swingWorker.execute();
    }


}
