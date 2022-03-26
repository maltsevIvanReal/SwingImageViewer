package com.vizor.test.panel;

import com.vizor.test.utils.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class JButtonUploadFile extends JButton {

    public JButtonUploadFile(String text, ImagesPanel imagesPanel, FunctionalPanel functionalPanel){
        super(text);

        addActionListener(event -> {
            JFileChooser fc = new JFileChooser();
            FileUtils.setFileFilterImages(fc, false, true);
            int result = fc.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                File folder = new File("assets");
                String destination = folder.getAbsolutePath() + File.separator + fc.getSelectedFile().getName();
                try {
                    FileChannel source = new FileInputStream(path).getChannel();
                    FileChannel dest = new FileOutputStream(destination).getChannel();
                    dest.transferFrom(source, 0, source.size());
                    source.close();
                    dest.close();

                    imagesPanel.rebuildAfterUploadImage(fc.getSelectedFile(), imagesPanel, functionalPanel);
                    JOptionPane.showMessageDialog(null, "Item was successfully saved");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to save item. " + e.getMessage());
                }
            }
        });
    }

}
