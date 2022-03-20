package com.vizor.test;

import javax.swing.*;

public class Main {
    public void run() {
        ImageViewer imageViewer = ImageViewer.createImageViewer();
        imageViewer.constructImageViewer();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }
}
