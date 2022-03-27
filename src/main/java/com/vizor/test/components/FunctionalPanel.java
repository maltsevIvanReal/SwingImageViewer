package com.vizor.test.components;

import com.vizor.test.layout.VerticalLayout;
import com.vizor.test.utils.TextPrompt;

import javax.swing.*;
import java.awt.*;

public final class FunctionalPanel extends JPanel {
    private final JComboBoxPaginator jComboBox;

    public FunctionalPanel(ImagesPanel leftImagesPanel) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new VerticalLayout());

        JButtonUploadFile uploadImageButton = new JButtonUploadFile("Upload new image", leftImagesPanel, this);
        jComboBox = new JComboBoxPaginator(leftImagesPanel.getPagesAsArray(), leftImagesPanel, this);

        JTextFieldSearch jTextFieldSearch = new JTextFieldSearch(leftImagesPanel, this);
        TextPrompt placeholder = new TextPrompt("Enter image name...", jTextFieldSearch);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);

        add(jTextFieldSearch);
        add(uploadImageButton);
        add(jComboBox);
    }

    public JComboBox<String> getjComboBox() {
        return jComboBox;
    }

    public void updatePagesComboBox(String[] pages) {
        jComboBox.setModel(new DefaultComboBoxModel<>(pages));
    }
}
