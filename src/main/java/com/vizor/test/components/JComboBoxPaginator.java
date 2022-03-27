package com.vizor.test.components;

import com.vizor.test.utils.PanelUtils;

import javax.swing.*;

public final class JComboBoxPaginator extends JComboBox<String> {

    JComboBoxPaginator(String[] pages, ImagesPanel imagesPanel, FunctionalPanel functionalPanel){
        super(pages);

        addActionListener(e -> {
            int selectedPageNumber = Integer.parseInt((String) getSelectedItem());
            imagesPanel.setCurrentPageSelected(selectedPageNumber);
            disable();
            start(selectedPageNumber, imagesPanel.getLabels(), imagesPanel, functionalPanel);
        });
    }

    private void start(int selectedPageNumber, ImageJLabel[] label, ImagesPanel imagesPanel, FunctionalPanel functionalPanel) {
        if (imagesPanel.getSwingWorkerPaginate() != null && !imagesPanel.getSwingWorkerPaginate().getState().equals(SwingWorker.StateValue.DONE)) {
            return;
        }
        JFrame mainFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, functionalPanel);
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                PanelUtils.clearJPanel(imagesPanel);
                imagesPanel.addLoaderAtImagePage(mainFrame);
                imagesPanel.fillImagesPanel(imagesPanel.getDisplayFrom(selectedPageNumber), label, imagesPanel);
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

}
