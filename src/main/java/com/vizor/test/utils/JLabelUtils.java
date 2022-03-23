package com.vizor.test.utils;

import com.vizor.test.panel.ImagesPanel;

import javax.swing.*;

public class JLabelUtils {
    public static JLabel[] createJLabelArray() {
        return new JLabel[ImagesPanel.MAX_IMAGES_PER_PAGE];
    }
}
