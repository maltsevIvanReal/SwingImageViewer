package com.vizor.test.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelUtils {
    private static BufferedImage[] imagesCurrentPage;
    private static int displayTo;
    public static final int MAX_IMAGES_PER_PAGE = 40;
    public static final int HGAP = 45;
    public static final int VGAP = 45;


    public static JPanel createMainPanel() {
        return new JPanel(new GridLayout());
    }

    public static JPanel createImagesPanel() {
        return new JPanel(new FlowLayout(FlowLayout.LEFT, HGAP, VGAP));
    }

    public static JPanel createJPanel() {
        return new JPanel();
    }

    public static void packJPanels(JPanel mainJPanel, JPanel imagesJPanel, JPanel functionalJPanel, JButton uploadImageBtn, JComboBox jComboBox) {
        functionalJPanel.add(uploadImageBtn);
        functionalJPanel.add(jComboBox);
        mainJPanel.add(imagesJPanel);
        mainJPanel.add(functionalJPanel);
    }

    public static void setOptions(JPanel mainJPanel) {
        mainJPanel.setPreferredSize(new Dimension(FrameUtils.getWIDTH(), FrameUtils.getHEIGHT()));
    }

    public static synchronized void fillImageContainer(File[] allFiles, int displayFrom, JLabel[] label, JPanel jPanel) {
        int countImagesToFill = displayTo - displayFrom;
        for (int i = 0; i <= countImagesToFill; i++) {
            try {
                imagesCurrentPage[i] = ImageIO.read(allFiles[displayFrom]);
                label[i] = new JLabel();
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagesCurrentPage[i]).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
                label[i].setIcon(imageIcon);
                label[i].setHorizontalAlignment(SwingConstants.CENTER);
                label[i].setVerticalAlignment(SwingConstants.CENTER);
                jPanel.add(label[i]);
                displayFrom++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getDisplayFrom(int currentPageNumber, File[] allFiles) {
        displayTo = (currentPageNumber * MAX_IMAGES_PER_PAGE) - 1;
        int displayFrom = (displayTo - MAX_IMAGES_PER_PAGE) + 1;

        if (displayTo > allFiles.length - 1) {
            displayTo = allFiles.length - 1;
        }
        return displayFrom;
    }

    public static void setImagesCurrentPage(BufferedImage[] imagesCurrentPage) {
        PanelUtils.imagesCurrentPage = imagesCurrentPage;
    }

}
