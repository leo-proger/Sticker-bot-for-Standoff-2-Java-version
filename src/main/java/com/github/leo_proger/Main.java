package com.github.leo_proger;

import java.awt.*;

import static com.github.leo_proger.ScreenManager.getCursorPosition;

public class Main {
    static int xStart = 1188;
    static int yStart = 310;

    static int width = 38;
    static int height = 38;

    static int lotHeight = 80;

    static Point buyButton = new Point(1400, 350);
    static Point confirmPurchaseButton = new Point(950, 650);

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(getCursorPosition());
//        System.exit(1);


//        for (int i = 0; i < 8; i++) {
//            // Или проверка одного изображения на наличие ярких областей
//            BufferedImage screen = takeScreenshot(
//                    xStart - 5,
//                    yStart + oneLotHeight * i + oneLotHeight / 2 - 5,
//                    width,
//                    height
//            );
//            boolean hasSticker = StickerDetector.hasSticker(screen, 50);
//            System.out.println(hasSticker);
//        }

//        xStart - 19, yStart + oneLotHeight / 2 - 19 + oneLotHeight * 7, width, height
//        BufferedImage s = ScreenManager.takeScreenshot(xStart - 19, yStart + oneLotHeight / 2 - 19 + oneLotHeight * 7, width, height);
//        ScreenManager.showBrightRegions(s, 50);

        Buyer buyer = new Buyer();
        buyer.run();
    }
}
