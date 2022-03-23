package com.vizor.test.panel;

import com.vizor.test.utils.FileUtils;
import com.vizor.test.utils.JComboBoxUtils;
import com.vizor.test.utils.JLabelUtils;
import com.vizor.test.utils.PanelUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImagesPanel extends JPanel {
    public static final int MAX_IMAGES_PER_PAGE = 40;
    public static final int HGAP = 45;
    public static final int VGAP = 45;

    private SwingWorker<Void, Void> swingWorkerPaginate;
    private BufferedImage[] imagesCurrentPage;
    private File pathAssets;
    private ArrayList<File> allFiles;
    private JLabel[] labels;
    private String[] pagesAsArray;
    private JLabel jLabelLoaderImage;
    private int displayFrom;
    private int displayTo;
    private int currentPageSelected = 1;

    public ImagesPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, HGAP, VGAP));
        setBorder(BorderFactory.createLineBorder(Color.GREEN));

        pathAssets = FileUtils.getFileByName("assets");
        allFiles = FileUtils.getSortedFilesByLastModified(pathAssets);

        labels = new JLabel[MAX_IMAGES_PER_PAGE];
        imagesCurrentPage = new BufferedImage[MAX_IMAGES_PER_PAGE];
        displayFrom = getDisplayFrom(currentPageSelected, allFiles);
        fillImagesPanel(allFiles, displayFrom, labels, this);
    }

    public void addLoaderAtImagePage(JFrame mainFrame) {
        Icon imgIcon = new ImageIcon("loading.gif");
        JLabel label = new JLabel(imgIcon);
        setLayout(new GridLayout());
        jLabelLoaderImage = label;
        add(label);
        mainFrame.revalidate();
        setLayout(new FlowLayout(FlowLayout.LEFT, HGAP, VGAP));
    }

    public void fillImagesPanel(ArrayList<File> allFiles, int displayFrom, JLabel[] labels, ImagesPanel imagesPanel) {
        int countImagesToFill = displayTo - displayFrom;
        for (int i = 0; i <= countImagesToFill; i++) {
            try {
                imagesPanel.imagesCurrentPage[i] = ImageIO.read(allFiles.get(displayFrom));
                labels[i] = new JLabel();
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagesPanel.imagesCurrentPage[i]).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
                labels[i].setIcon(imageIcon);
                labels[i].setHorizontalAlignment(SwingConstants.CENTER);
                labels[i].setVerticalAlignment(SwingConstants.CENTER);
                imagesPanel.add(labels[i]);
                displayFrom++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getDisplayFrom(int currentPageNumber, ArrayList<File> allFiles) {
        displayTo = (currentPageNumber * MAX_IMAGES_PER_PAGE) - 1;
        int displayFrom = (displayTo - MAX_IMAGES_PER_PAGE) + 1;
        if (displayTo > allFiles.size() - 1) {
            displayTo = allFiles.size() - 1;
        }
        return displayFrom;
    }

    public void rebuildAfterUploadImage(File file, ImagesPanel imagesPanel, FunctionalPanel functionalPanel) {
        allFiles.add(file);

        int countAllFiles = FileUtils.getCountFiles(allFiles);
        int countOfPages = JComboBoxUtils.calculateCountImagePages(countAllFiles);

        functionalPanel.remove(functionalPanel.getjComboBox());
        Container parent = this.getParent();
        parent.remove(imagesPanel);

        imagesPanel.setDisplayFrom(getDisplayFrom(imagesPanel.currentPageSelected, allFiles));
        pagesAsArray = JComboBoxUtils.getPagesAsArray(countOfPages);

        functionalPanel.setjComboBox(new JComboBox(pagesAsArray));
        imagesPanel.setLabels(JLabelUtils.createJLabelArray());

        JPanelConfigurator.setPaginateActionListener(functionalPanel, imagesPanel);
        PanelUtils.packJPanels(imagesPanel, functionalPanel);
        JComboBox jComboBox = functionalPanel.getjComboBox();

        jComboBox.repaint();
        functionalPanel.repaint();
        imagesPanel.repaint();
        jComboBox.setSelectedIndex(imagesPanel.currentPageSelected - 1);
    }

    public SwingWorker<Void, Void> getSwingWorkerPaginate() {
        return swingWorkerPaginate;
    }

    public void setSwingWorkerPaginate(SwingWorker<Void, Void> swingWorkerPaginate) {
        this.swingWorkerPaginate = swingWorkerPaginate;
    }


    public ArrayList<File> getAllFiles() {
        return allFiles;
    }


    public JLabel[] getLabels() {
        return labels;
    }

    public void setLabels(JLabel[] labels) {
        this.labels = labels;
    }

    public String[] getPagesAsArray() {
        return pagesAsArray;
    }

    public void setPagesAsArray(String[] pagesAsArray) {
        this.pagesAsArray = pagesAsArray;
    }

    public JLabel getjLabelLoaderImage() {
        return jLabelLoaderImage;
    }


    public void setDisplayFrom(int displayFrom) {
        this.displayFrom = displayFrom;
    }


    public int getCurrentPageSelected() {
        return currentPageSelected;
    }

    public void setCurrentPageSelected(int pageSelected) {
        currentPageSelected = pageSelected;
    }

}
