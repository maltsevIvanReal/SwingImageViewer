package com.vizor.test.panel;

import com.vizor.test.utils.FileUtils;
import com.vizor.test.utils.JComboBoxUtils;
import com.vizor.test.utils.JLabelUtils;
import com.vizor.test.utils.PanelUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class JPanelConfigurator {
    public static void configureFunctionalAndImgPanels(FunctionalPanel functionalPanel, ImagesPanel imagesPanel) {
        int countAllFiles = FileUtils.getCountFiles(imagesPanel.getAllFiles());
        int countOfPages = JComboBoxUtils.calculateCountImagePages(countAllFiles);
        String[] pagesAsArray = JComboBoxUtils.getPagesAsArray(countOfPages);
        imagesPanel.setPagesAsArray(pagesAsArray);

        JComboBox jComboBox = new JComboBox(imagesPanel.getPagesAsArray());
        functionalPanel.setjComboBox(jComboBox);
        functionalPanel.add(jComboBox);

        JLabel[] jLabelArray = JLabelUtils.createJLabelArray();
        imagesPanel.setLabels(jLabelArray);

        setUploadActionListener(functionalPanel, imagesPanel);
        setPaginateActionListener(functionalPanel, imagesPanel);
    }

    public static void setPaginateActionListener(FunctionalPanel functionalPanel, ImagesPanel imagesPanel) {
        JComboBox jComboBox = functionalPanel.getjComboBox();
        jComboBox.addActionListener(e -> {
            int selectedPageNumber = Integer.parseInt((String) jComboBox.getSelectedItem());
            imagesPanel.setCurrentPageSelected(selectedPageNumber);
            jComboBox.disable();
            start(imagesPanel.getAllFiles(), selectedPageNumber, imagesPanel.getLabels(), imagesPanel, functionalPanel);
        });
    }

    private static void start(ArrayList<File> allFiles, int selectedPageNumber, JLabel[] label, ImagesPanel imagesPanel, FunctionalPanel functionalPanel) {
        if (imagesPanel.getSwingWorkerPaginate() != null && !imagesPanel.getSwingWorkerPaginate().getState().equals(SwingWorker.StateValue.DONE)) {
            return;
        }
        JFrame mainFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, functionalPanel);
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                PanelUtils.clearJPanel(imagesPanel);
                imagesPanel.addLoaderAtImagePage(mainFrame);
                System.out.println(imagesPanel.getCurrentPageSelected() + " fill");
                imagesPanel.fillImagesPanel(allFiles, imagesPanel.getDisplayFrom(selectedPageNumber, allFiles), label, imagesPanel);
                return null;
            }

            @Override
            protected void done() {
                JLabel loaderImage = imagesPanel.getjLabelLoaderImage();
                imagesPanel.remove(loaderImage);
                imagesPanel.repaint();

                JComboBox jComboBox = functionalPanel.getjComboBox();
                jComboBox.enable();
                jComboBox.repaint();
                mainFrame.revalidate();
            }
        };

        imagesPanel.setSwingWorkerPaginate(swingWorker);
        swingWorker.execute();
    }

    private static void setUploadActionListener(FunctionalPanel functionalPanel, ImagesPanel imagesPanel) {
        functionalPanel.getUploadImageButton().addActionListener(event -> {
            JFileChooser fc = new JFileChooser();
            FileUtils.setFileFilterImages(fc, false, true);
            int result = fc.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                File folder = new File("assets");
                String destination = folder.getAbsolutePath() + File.separator + fc.getSelectedFile().getName();
                try {
                    FileChannel source = new FileInputStream(path).getChannel();
                    FileChannel dest = new FileOutputStream(destination).getChannel();
                    dest.transferFrom(source, 0, source.size());
                    source.close();
                    dest.close();

                    imagesPanel.rebuildAfterUploadImage(fc.getSelectedFile(), imagesPanel, functionalPanel);
                    JOptionPane.showMessageDialog(null, "Item was successfully saved");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to save item. " + e.getMessage());
                }
            }
        });
    }
}
