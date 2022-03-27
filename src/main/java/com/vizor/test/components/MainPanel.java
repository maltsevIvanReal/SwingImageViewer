package com.vizor.test.components;

import com.vizor.test.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(FrameUtils.getWIDTH(), FrameUtils.getHEIGHT()));

        ImagesPanel imagesPanel = new ImagesPanel();
        FunctionalPanel functionalPanel = new FunctionalPanel(imagesPanel);

        add(imagesPanel);
        add(functionalPanel);

    }
}
