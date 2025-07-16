package com.github.leo_proger;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    static int xStart = 1190;
    static int yStart = 310;

    static int width = 38;
    static int height = 38;

    static int lotHeight = 80;

    static int buyButtonX = 1400;
    static Point confirmPurchaseButton = new Point(950, 650);

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(getCursorPosition());
//        System.exit(1);

//        xStart - 19, yStart + oneLotHeight / 2 - 19 + oneLotHeight * 7, width, height

//        BufferedImage s = ScreenManager.takeScreenshot(xStart - 122, yStart + lotHeight / 2 - 19 + lotHeight * 0, width, height);
//        ScreenManager.showBrightRegions(s, 40);
//        ScreenManager.showImage(s);

//        StickerDetector stickerDetector = new ColorDetection(100);
        StickerDetector stickerDetector = new StandardDeviation(700);

        Buyer buyer = new Buyer(stickerDetector, 4);
        buyer.run();
    }

    public static void checkDetection(StickerDetector stickerDetector) {
        for (int i = 0; i < 8; i++) {
            BufferedImage s = ScreenManager.takeScreenshot(xStart - 122, yStart + lotHeight / 2 - 19 + lotHeight * i, width, height);
            System.out.print("Лот " + (i + 1) + " - ");
            stickerDetector.hasSticker(s);
        }
    }
}
