package com.github.leo_proger;

import java.awt.image.BufferedImage;

public class StickerDetector {

    // Детектор наклеек по появлению ярких областей на тусклом фоне
    public static boolean hasSticker(BufferedImage image, int brightnessThreshold) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Считаем среднюю яркость всего изображения (фона)
        long totalBrightness = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                totalBrightness += getBrightness(rgb);
            }
        }
        int avgBrightness = (int) (totalBrightness / (width * height));

        // Ищем области значительно ярче фона
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int pixelBrightness = getBrightness(rgb);

                // Если пиксель значительно ярче фона - возможно наклейка
                if (pixelBrightness > avgBrightness + brightnessThreshold) {
                    return true;
                }
            }
        }

        return false;
    }

    // Вычисляет яркость одного пикселя
    private static int getBrightness(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return (r + g + b) / 3;
    }
}