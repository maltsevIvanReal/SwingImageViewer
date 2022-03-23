package com.vizor.test.utils;

import com.vizor.test.panel.FunctionalPanel;
import com.vizor.test.panel.ImagesPanel;

import javax.swing.*;
import java.awt.*;

public class PanelUtils {

    public static void packJPanels(ImagesPanel imagesJPanel, FunctionalPanel functionalJPanel) {
        Container mainPanel = functionalJPanel.getParent();
        functionalJPanel.add(functionalJPanel.getUploadImageButton());
        functionalJPanel.add(functionalJPanel.getjComboBox());
        mainPanel.add(imagesJPanel);
        mainPanel.add(functionalJPanel);
    }

    public static void clearJPanel(JPanel jPanel) {
        jPanel.removeAll();
        jPanel.repaint();
    }
}
