package com.vizor.test.panel;

import javax.swing.*;
import java.awt.*;

public class FunctionalPanel extends JPanel {
    private JButton uploadImageButton;
    private JComboBox jComboBox;

    public FunctionalPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        uploadImageButton = new JButton("Upload new image");
        add(uploadImageButton);
    }

    public JButton getUploadImageButton() {
        return uploadImageButton;
    }

    public JComboBox getjComboBox() {
        return jComboBox;
    }

    public void setjComboBox(JComboBox jComboBox) {
        this.jComboBox = jComboBox;
    }

}
