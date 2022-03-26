package com.vizor.test.panel;

import javax.swing.*;
import java.awt.*;

public class FunctionalPanel extends JPanel {
    private JButtonUploadFile uploadImageButton;
    private JComboBoxPaginator jComboBox;
    private ImagesPanel imagesPanel;

    public FunctionalPanel(ImagesPanel leftImagesPanel) {
        imagesPanel = leftImagesPanel;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        uploadImageButton = new JButtonUploadFile("Upload new image", imagesPanel, this);
        add(uploadImageButton);

        jComboBox = new JComboBoxPaginator(imagesPanel.getPagesAsArray(), imagesPanel, this);
        add(jComboBox);
    }

    public JButton getUploadImageButton() {
        return uploadImageButton;
    }

    public JComboBox getjComboBox() {
        return jComboBox;
    }

    public void updatePagesComboBox(String[] pages){
        jComboBox = new JComboBoxPaginator(pages, imagesPanel, this);
    }

    public void setjComboBox(JComboBoxPaginator jComboBox) {
        this.jComboBox = jComboBox;
    }

}
