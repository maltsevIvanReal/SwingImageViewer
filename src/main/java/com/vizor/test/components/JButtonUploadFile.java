package com.vizor.test.components;

import com.vizor.test.utils.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class JButtonUploadFile extends JButton {

    public JButtonUploadFile(String text, ImagesPanel imagesPanel, FunctionalPanel functionalPanel) {
        super(text);
        File folder = new File("assets");

        addActionListener(event -> {
            String newFileName = "";
            JFileChooser fc = new JFileChooser();
            FileUtils.setFileFilterImages(fc, false, true);
            int result = fc.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File cloneSelectedFile = fc.getSelectedFile();

                String path = fc.getSelectedFile().getAbsolutePath();
                String destination = folder.getAbsolutePath() + File.separator + fc.getSelectedFile().getName();

                if (new File(destination).isFile()) {
                    newFileName = FileUtils.changeFileName(fc.getSelectedFile().getName(), folder);
                    destination = folder.getAbsolutePath() + File.separator + newFileName;
                    cloneSelectedFile = new File(destination);
                }

                try {
                    FileChannel source = new FileInputStream(path).getChannel();
                    FileChannel dest = new FileOutputStream(destination).getChannel();
                    dest.transferFrom(source, 0, source.size());
                    source.close();
                    dest.close();

                    imagesPanel.rebuildAfterUploadImage(cloneSelectedFile, imagesPanel, functionalPanel);
                    JOptionPane.showMessageDialog(null, "Item was successfully saved");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to save item. " + e.getMessage());
                }
            }
        });
    }
}
