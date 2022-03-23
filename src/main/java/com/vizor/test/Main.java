package com.vizor.test;

import com.vizor.test.panel.MainPanel;
import com.vizor.test.utils.FrameUtils;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame imageViewerFrame = new JFrame("Image Viewer");
            imageViewerFrame.setName("Test App Image Viewer");

            imageViewerFrame.add(new MainPanel());

            FrameUtils.setOptions(imageViewerFrame);
        });
    }
}
