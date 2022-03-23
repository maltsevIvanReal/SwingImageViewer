package com.vizor.test.panel;

import com.vizor.test.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(FrameUtils.getWIDTH(), FrameUtils.getHEIGHT()));
        setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        ImagesPanel imagesPanel = new ImagesPanel();
        FunctionalPanel functionalPanel = new FunctionalPanel();

        add(imagesPanel);
        add(functionalPanel);

        JPanelConfigurator.configureFunctionalAndImgPanels(functionalPanel, imagesPanel);
    }
}
