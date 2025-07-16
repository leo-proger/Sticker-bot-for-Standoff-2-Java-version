package com.github.leo_proger;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorDetection implements StickerDetector {

    private final Color baseGray = new Color(44, 49, 72);
    private final int threshold;

    public ColorDetection(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean hasSticker(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int nonGrayCount = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                if (!isGray(red, green, blue, baseGray)) {
                    nonGrayCount++;

                    if (nonGrayCount > threshold) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isGray(int red, int green, int blue, Color baseGray) {
        int tolerance = 100; // Погрешность для оттенков серого
        return Math.abs(red - baseGray.getRed()) < tolerance &&
               Math.abs(green - baseGray.getGreen()) < tolerance &&
               Math.abs(blue - baseGray.getBlue()) < tolerance;
    }
}
