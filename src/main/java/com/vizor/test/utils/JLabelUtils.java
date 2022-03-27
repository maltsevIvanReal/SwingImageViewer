package com.vizor.test.utils;

import com.vizor.test.components.ImageJLabel;
import com.vizor.test.components.ImagesPanel;

public final class JLabelUtils {
    public static ImageJLabel[] createJLabelArray() {
        return new ImageJLabel[ImagesPanel.MAX_IMAGES_PER_PAGE];
    }
}
