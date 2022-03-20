package com.vizor.test.utils;

import javax.swing.*;
import java.awt.*;

public class FrameUtils {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    public static JFrame createMainJFrame() {
        return new JFrame("DT Developer Test");
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }


    public static void setOptions(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
    }
}
