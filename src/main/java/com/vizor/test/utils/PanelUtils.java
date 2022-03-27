package com.vizor.test.utils;

import javax.swing.*;

public class PanelUtils {
    public static void clearJPanel(JPanel jPanel) {
        jPanel.removeAll();
        jPanel.repaint();
    }
}
