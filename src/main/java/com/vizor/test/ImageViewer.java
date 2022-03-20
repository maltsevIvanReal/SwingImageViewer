package com.vizor.test;

import com.vizor.test.utils.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageViewer {
    public static ImageViewer createImageViewer() {
        return new ImageViewer();
    }

    public void constructImageViewer() {
        JFrame mainJFrame = FrameUtils.createMainJFrame();
        FrameUtils.setOptions(mainJFrame);

        JPanel mainJPanel = PanelUtils.createMainPanel();
        PanelUtils.setOptions(mainJPanel);

        JPanel imagesPanel = PanelUtils.createImagesPanel();
        JPanel functionalPanel = PanelUtils.createJPanel();

        JButton uploadImageBtn = JButtonUtils.createUploadJButton();
        JButtonUtils.setUploadActionListener(uploadImageBtn);

        File pathAssets = FileUtils.getFileByName("assets");
        File[] allFiles = FileUtils.getListOfFiles(pathAssets);

        int countAllFiles = FileUtils.getCountFiles(allFiles);
        int countOfPages = JComboBoxUtils.calculateCountImagePages(countAllFiles);
        String[] pagesAsArray = JComboBoxUtils.getPagesAsArray(countOfPages);

        JComboBox jComboBox = JComboBoxUtils.createJComboBox(pagesAsArray);
        JLabel[] label = JLabelUtils.createJLabelArray();
        JComboBoxUtils.setPaginateActionListener(jComboBox, imagesPanel, allFiles, label, mainJFrame);

        PanelUtils.packJPanels(mainJPanel, imagesPanel, functionalPanel, uploadImageBtn, jComboBox);

        mainJFrame.add(mainJPanel);
        mainJFrame.pack();
        mainJFrame.setLocationRelativeTo(null);
        mainJFrame.setVisible(true);

        int displayFrom = PanelUtils.getDisplayFrom(1, allFiles);

        PanelUtils.setImagesCurrentPage(new BufferedImage[PanelUtils.MAX_IMAGES_PER_PAGE]);
        PanelUtils.fillImageContainer(allFiles, displayFrom, label, imagesPanel);
    }

}
