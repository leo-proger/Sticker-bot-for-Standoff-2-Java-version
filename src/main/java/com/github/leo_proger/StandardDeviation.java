package com.github.leo_proger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class StandardDeviation implements StickerDetector {

    private final int[][] baseImageColorRed;
    private final int[][] baseImageColorGreen;
    private final int[][] baseImageColorBlue;

    private final int threshold;
    private boolean debug = false;


    public StandardDeviation(int threshold) {
        this.threshold = threshold;

        BufferedImage baseImage = getBaseImage();
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();

        baseImageColorRed = new int[height][width];
        baseImageColorGreen = new int[height][width];
        baseImageColorBlue = new int[height][width];

        getBaseImageProperties(baseImage);
    }

    public StandardDeviation(int threshold, boolean debug) {
        this(threshold);
        this.debug = debug;
    }

    private BufferedImage getBaseImage() {
        URL baseImageURL = StandardDeviation.class.getResource("template_image.png");

        if (baseImageURL == null) {
            throw new RuntimeException("ERROR: Can't find base image from resources");
        }

        BufferedImage baseImage;
        try {
            baseImage = ImageIO.read(baseImageURL);
        } catch (IOException e) {
            throw new RuntimeException("ERROR: Can't read base image from resources");
        }
        return baseImage;
    }

    private void getBaseImageProperties(BufferedImage baseImage) {
        for (int y = 0; y < baseImage.getHeight(); y++) {
            for (int x = 0; x < baseImage.getWidth(); x++) {
                Color color = new Color(baseImage.getRGB(x, y));

                baseImageColorRed[y][x] = color.getRed();
                baseImageColorGreen[y][x] = color.getGreen();
                baseImageColorBlue[y][x] = color.getBlue();
            }
        }
    }

    @Override
    public boolean hasSticker(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        double totalMSE = 0;

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                Color currentImageColor = new Color(image.getRGB(x, y));

                int redDiff = baseImageColorRed[y][x] - currentImageColor.getRed();
                int greenDiff = baseImageColorGreen[y][x] - currentImageColor.getGreen();
                int blueDiff = baseImageColorBlue[y][x] - currentImageColor.getBlue();

                totalMSE += (redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff);
            }
        }

        double mse = totalMSE / (width * height * 3);

        if (debug) {
            System.out.println(((int) mse));
        }
        return mse > threshold;
    }
}
