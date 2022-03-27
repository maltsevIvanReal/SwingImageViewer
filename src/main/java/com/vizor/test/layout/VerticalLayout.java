package com.vizor.test.layout;

import java.awt.*;

public class VerticalLayout implements LayoutManager {
    private final Dimension size = new Dimension();

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension minimumLayoutSize(Container c) {
        return calculateBestSize(c);
    }

    public Dimension preferredLayoutSize(Container c) {
        return calculateBestSize(c);
    }

    public void layoutContainer(Container container) {
        Component[] list = container.getComponents();
        int currentY = 170;
        for (Component component : list) {
            Dimension pref = component.getPreferredSize();
            component.setBounds(170, currentY, pref.width, pref.height);
            currentY += 100;
            currentY += pref.height;
        }
    }

    private Dimension calculateBestSize(Container c) {
        Component[] list = c.getComponents();
        int maxWidth = 0;
        for (Component component : list) {
            int width = component.getWidth();
            if (width > maxWidth)
                maxWidth = width;
        }
        size.width = maxWidth + 5;
        int height = 0;
        for (Component component : list) {
            height += 5;
            height += component.getHeight();
        }
        size.height = height;
        return size;
    }
}