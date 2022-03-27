package com.vizor.test.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public final class ImageJLabel extends JLabel {
    public ImageJLabel(String text, File file) {
        super(text);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDialog dialog = new JDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setTitle("Image Loading Demo");
                try {
                    dialog.add(new JLabel(new ImageIcon(new ImageIcon(ImageIO.read(file)).getImage())));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }
}
