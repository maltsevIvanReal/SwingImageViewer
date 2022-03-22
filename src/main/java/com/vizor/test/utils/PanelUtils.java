package com.vizor.test.utils;

import com.vizor.test.ImageViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

    public static void fillImageContainer(ArrayList<File> allFiles, int displayFrom, JLabel[] label, JPanel imagesPanel, JFrame mainJFrame) {
        int countImagesToFill = displayTo - displayFrom;
        for (int i = 0; i <= countImagesToFill; i++) {
            try {
                imagesCurrentPage[i] = ImageIO.read(allFiles.get(displayFrom));
                label[i] = new JLabel();
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagesCurrentPage[i]).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
                label[i].setIcon(imageIcon);
                label[i].setHorizontalAlignment(SwingConstants.CENTER);
                label[i].setVerticalAlignment(SwingConstants.CENTER);
                imagesPanel.add(label[i]);
                displayFrom++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getDisplayFrom(int currentPageNumber, ArrayList<File> allFiles) {
        displayTo = (currentPageNumber * MAX_IMAGES_PER_PAGE) - 1;
        int displayFrom = (displayTo - MAX_IMAGES_PER_PAGE) + 1;

        if (displayTo > allFiles.size() - 1) {
            displayTo = allFiles.size() - 1;
        }
        return displayFrom;
    }

    public static void setImagesCurrentPage(BufferedImage[] imagesCurrentPage) {
        PanelUtils.imagesCurrentPage = imagesCurrentPage;
    }

    public static void addLoaderAtImagePage() {
        Icon imgIcon = new ImageIcon("loading.gif");
        JLabel label = new JLabel(imgIcon);
        ImageViewer.getImagesPanel().setLayout(new GridLayout());
        ImageViewer.setLoaderImage(label);
        ImageViewer.getImagesPanel().add(label);
        ImageViewer.getMainJFrame().getContentPane().invalidate();
        ImageViewer.getMainJFrame().getContentPane().validate();
        ImageViewer.getImagesPanel().setLayout(new FlowLayout(FlowLayout.LEFT, HGAP, VGAP));

//        ImageIcon loading = new ImageIcon("ajax-loader.gif");
//        JLabel jLabel = new JLabel(loading);
//        ImageViewer.setLoaderImage(jLabel);
//
//        jLabel.add(new JLabel("loading... ", loading, JLabel.CENTER));
//        jLabel.setSize(400, 300);
//        jLabel.setVisible(true);
//        JPanel imagesPanel = ImageViewer.getImagesPanel();
//        imagesPanel.add(imagesPanel);
    }
    public static void clearJPanel(JPanel jPanel) {
        jPanel.removeAll();
        jPanel.repaint();
    }
}
