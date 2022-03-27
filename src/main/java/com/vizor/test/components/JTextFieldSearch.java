package com.vizor.test.components;

import com.vizor.test.utils.PanelUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public final class JTextFieldSearch extends JTextField {

    public JTextFieldSearch(ImagesPanel imagesPanel, FunctionalPanel functionalPanel) {
        super(15);
        setPreferredSize(new Dimension(300, 30));

        getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                start(imagesPanel, functionalPanel);
            }
        });
    }

    private void start(ImagesPanel imagesPanel, FunctionalPanel functionalPanel) {
        JFrame mainFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, functionalPanel);

        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                imagesPanel.filterImagesByString(getText());
                PanelUtils.clearJPanel(imagesPanel);
                imagesPanel.fillImagesPanel(imagesPanel.getDisplayFrom(1), imagesPanel.getLabels(), imagesPanel);
                return null;
            }

            @Override
            protected void done() {
                imagesPanel.repaint();
                JComboBox<String> jComboBox = functionalPanel.getjComboBox();
                jComboBox.enable();
                jComboBox.repaint();
                mainFrame.revalidate();
                imagesPanel.rebuild(functionalPanel, false);
            }
        };

        swingWorker.execute();
    }

}
