package com.vizor.test.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class JButtonUtils {
    public static JButton createUploadJButton() {
        return new JButton("Upload");
    }

    public static void setUploadActionListener(JButton jButton) {
        jButton.addActionListener(event -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("*.jpg", "jpg"));
            int result = fc.showOpenDialog(null);
            if (result != JFileChooser.APPROVE_OPTION) {
                System.exit(0);
            }
            String path = fc.getSelectedFile().getAbsolutePath();
            File folder = new File("assets");
            String destination = folder.getAbsolutePath() + File.separator + fc.getSelectedFile().getName();
            try {
                FileChannel source = new FileInputStream(path).getChannel();
                FileChannel dest = new FileOutputStream(destination).getChannel();
                dest.transferFrom(source, 0, source.size());
                source.close();
                dest.close();

                JOptionPane.showMessageDialog(null, "Successfully saved item");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to save Image. " + e.getMessage());
            }
        });
    }
}
