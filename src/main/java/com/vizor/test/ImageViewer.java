package com.vizor.test;

import com.vizor.test.utils.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ImageViewer {
    private static JFrame mainJFrame;
    private static JPanel mainJPanel;
    private static JPanel imagesPanel;
    private static JPanel functionalPanel;
    private static JButton uploadImageBtn;
    private static File pathAssets;
    private static ArrayList<File> allFiles;
    private static String[] pagesAsArray;
    private static JComboBox jComboBox;
    private static JLabel[] label;
    private static int displayFrom;
    private static int currentPageSelected = 1;
    private static JLabel jLabelLoaderImage;
    private static SwingWorker<Void, Void> swingWorkerPaginate;


    public static void constructImageViewer() {
        mainJFrame = FrameUtils.createMainJFrame();
        FrameUtils.setOptions(mainJFrame);

        mainJPanel = PanelUtils.createMainPanel();
        PanelUtils.setOptions(mainJPanel);

        imagesPanel = PanelUtils.createImagesPanel();
        functionalPanel = PanelUtils.createJPanel();

        uploadImageBtn = JButtonUtils.createUploadJButton();
        JButtonUtils.setUploadActionListener(uploadImageBtn);

        pathAssets = FileUtils.getFileByName("assets");
        allFiles = FileUtils.getSortedFilesByLastModified(pathAssets);

        int countAllFiles = FileUtils.getCountFiles(allFiles);
        int countOfPages = JComboBoxUtils.calculateCountImagePages(countAllFiles);
        pagesAsArray = JComboBoxUtils.getPagesAsArray(countOfPages);

        jComboBox = JComboBoxUtils.createJComboBox(pagesAsArray);
        label = JLabelUtils.createJLabelArray();
        JComboBoxUtils.setPaginateActionListener(jComboBox, imagesPanel, allFiles, label, mainJFrame);

        PanelUtils.packJPanels(mainJPanel, imagesPanel, functionalPanel, uploadImageBtn, jComboBox);

        mainJFrame.add(mainJPanel);
        mainJFrame.pack();
        mainJFrame.setLocationRelativeTo(null);
        mainJFrame.setVisible(true);

        displayFrom = PanelUtils.getDisplayFrom(currentPageSelected, allFiles);

        PanelUtils.setImagesCurrentPage(new BufferedImage[PanelUtils.MAX_IMAGES_PER_PAGE]);
        PanelUtils.fillImageContainer(allFiles, displayFrom, label, imagesPanel, mainJFrame);
    }

    public static void rebuildAfterUploadImage(File file) {
        allFiles.add(file);

        int countAllFiles = FileUtils.getCountFiles(allFiles);
        int countOfPages = JComboBoxUtils.calculateCountImagePages(countAllFiles);

        functionalPanel.remove(jComboBox);
        mainJPanel.remove(imagesPanel);

        displayFrom = PanelUtils.getDisplayFrom(currentPageSelected, allFiles);
        pagesAsArray = JComboBoxUtils.getPagesAsArray(countOfPages);

        jComboBox = JComboBoxUtils.createJComboBox(pagesAsArray);
        label = JLabelUtils.createJLabelArray();
        JComboBoxUtils.setPaginateActionListener(jComboBox, imagesPanel, allFiles, label, mainJFrame);
        PanelUtils.packJPanels(mainJPanel, imagesPanel, functionalPanel, uploadImageBtn, jComboBox);

        jComboBox.repaint();
        functionalPanel.repaint();
        imagesPanel.repaint();
        jComboBox.setSelectedIndex(currentPageSelected - 1);
    }

    public static JFrame getMainJFrame() {
        return mainJFrame;
    }

    public static JPanel getMainJPanel() {
        return mainJPanel;
    }

    public static JPanel getImagesPanel() {
        return imagesPanel;
    }

    public static JPanel getFunctionalPanel() {
        return functionalPanel;
    }

    public static JButton getUploadImageBtn() {
        return uploadImageBtn;
    }

    public static File getPathAssets() {
        return pathAssets;
    }

    public static ArrayList<File> getAllFiles() {
        return allFiles;
    }

    public static String[] getPagesAsArray() {
        return pagesAsArray;
    }

    public static JComboBox getjComboBox() {
        return jComboBox;
    }

    public static JLabel[] getLabel() {
        return label;
    }

    public static int getDisplayFrom() {
        return displayFrom;
    }

    public static void setCurrentPageSelected(int currentPageSelected) {
        ImageViewer.currentPageSelected = currentPageSelected;
    }

    public static SwingWorker<Void, Void> getSwingWorkerPaginate() {
        return swingWorkerPaginate;
    }

    public static void setSwingWorkerPaginate(SwingWorker<Void, Void> swingWorkerPaginate) {
        ImageViewer.swingWorkerPaginate = swingWorkerPaginate;
    }

    public static JLabel getLoaderImage() {
        return jLabelLoaderImage;
    }

    public static void setLoaderImage(JLabel label) {
        jLabelLoaderImage = label;
    }

}
