package com.vizor.test.components;

import com.vizor.test.utils.FileUtils;
import com.vizor.test.utils.JComboBoxUtils;
import com.vizor.test.utils.JLabelUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class ImagesPanel extends JPanel {
    public static final int MAX_IMAGES_PER_PAGE = 35;
    public static final int HGAP = 45;
    public static final int VGAP = 45;

    private final ArrayList<File> allFiles;
    private final ArrayList<ImageIcon> scaledImages;
    private SwingWorker<Void, Void> swingWorkerPaginate;
    private ImageJLabel[] labels;
    private String[] pagesAsArray;
    private ArrayList<Integer> currentImageIndexes = new ArrayList<>();
    private JLabel jLabelLoaderImage;
    private int displayImagesFrom;
    private int displayImagesTo;
    private int currentPageSelected = 1;

    public ImagesPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, HGAP, VGAP));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //TODO check folder exists
        File pathAssets = FileUtils.getFileByName("assets");
        allFiles = FileUtils.getSortedFilesByLastModified(pathAssets);

        for (int i = 0; i < allFiles.size(); i++) {
            currentImageIndexes.add(i);
        }

        labels = new ImageJLabel[MAX_IMAGES_PER_PAGE];
        displayImagesFrom = getDisplayFrom(currentPageSelected);
        scaledImages = new ArrayList<>(allFiles.size());

        resizeAndSaveAllImages();
        fillImagesPanel(displayImagesFrom, labels, this);

        int countAllFiles = currentImageIndexes.size();
        int countOfPages = JComboBoxUtils.calculateCountImagePages(countAllFiles);

        String[] pagesAsArray = JComboBoxUtils.getPagesAsArray(countOfPages);
        setPagesAsArray(pagesAsArray);
        setLabels(JLabelUtils.createJLabelArray());
    }

    private void resizeAndSaveAllImages() {
        for (File file : allFiles) {
            try {
                scaledImages.add(new ImageIcon(new ImageIcon(ImageIO.read(file)).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void filterImagesByString(String filterString) {
        ArrayList<Integer> newCurrentIndexes = new ArrayList<>();
        for (int i = 0; i < allFiles.size(); i++) {
            if (allFiles.get(i).getName().contains(filterString)) {
                newCurrentIndexes.add(i);
            }
        }
        currentImageIndexes = newCurrentIndexes;
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

    private void setNothingFound() {
        JLabel label = new JLabel("Nothing was found");
        setLayout(new GridLayout());
        add(label);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void fillImagesPanel(int displayFrom, ImageJLabel[] labels, ImagesPanel imagesPanel) {
        if (currentImageIndexes.size() == 0) {
            setNothingFound();
        } else {
            setLayout(new FlowLayout(FlowLayout.LEFT, HGAP, VGAP));
            int countImagesToFill = displayImagesTo - displayFrom;
            for (int i = 0; i <= countImagesToFill; i++) {
                File file = allFiles.get(currentImageIndexes.get(displayFrom));
                labels[i] = new ImageJLabel(FileUtils.truncateFileName(allFiles.get(currentImageIndexes.get(displayFrom)).getName()), file);
                labels[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                ImageIcon imageIcon = scaledImages.get(currentImageIndexes.get(displayFrom));
                labels[i].setIcon(imageIcon);
                labels[i].setHorizontalAlignment(SwingConstants.CENTER);
                labels[i].setVerticalAlignment(SwingConstants.CENTER);
                labels[i].setHorizontalTextPosition(SwingConstants.CENTER);
                labels[i].setVerticalTextPosition(SwingConstants.BOTTOM);
                imagesPanel.add(labels[i]);
                displayFrom++;
            }
        }
    }

    public void rebuild(FunctionalPanel functionalPanel, boolean actionAfterUpload) {
        int countAllFiles = currentImageIndexes.size();
        int countOfPages = JComboBoxUtils.calculateCountImagePages(countAllFiles);

        setDisplayImagesFrom(getDisplayFrom(currentPageSelected));
        pagesAsArray = JComboBoxUtils.getPagesAsArray(countOfPages);
        functionalPanel.updatePagesComboBox(pagesAsArray);
        setLabels(JLabelUtils.createJLabelArray());

        if (currentImageIndexes.size() > 1 && actionAfterUpload) {
            functionalPanel.getjComboBox().setSelectedIndex(currentPageSelected - 1);
        }
    }

    public void rebuildAfterUploadImage(File file, ImagesPanel imagesPanel, FunctionalPanel functionalPanel) {
        allFiles.add(file);
        try {
            scaledImages.add(new ImageIcon(new ImageIcon(ImageIO.read(file)).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagesPanel.filterImagesByString("");
        rebuild(functionalPanel, true);
    }

    public SwingWorker<Void, Void> getSwingWorkerPaginate() {
        return swingWorkerPaginate;
    }

    public void setSwingWorkerPaginate(SwingWorker<Void, Void> swingWorkerPaginate) {
        this.swingWorkerPaginate = swingWorkerPaginate;
    }

    public void setDisplayImagesFrom(int displayImagesFrom) {
        this.displayImagesFrom = displayImagesFrom;
    }

    public int getDisplayFrom(int currentPageNumber) {
        displayImagesTo = (currentPageNumber * MAX_IMAGES_PER_PAGE) - 1;
        int displayFrom = (displayImagesTo - MAX_IMAGES_PER_PAGE) + 1;
        if (displayImagesTo > currentImageIndexes.size() - 1) {
            displayImagesTo = currentImageIndexes.size() - 1;
        }
        return displayFrom;
    }

    public ImageJLabel[] getLabels() {
        return labels;
    }

    public void setLabels(ImageJLabel[] labels) {
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

    public void setCurrentPageSelected(int pageSelected) {
        currentPageSelected = pageSelected;
    }

}
