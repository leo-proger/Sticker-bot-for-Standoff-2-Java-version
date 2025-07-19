package com.github.leo_proger;

import com.github.leo_proger.config.Config;
import com.github.leo_proger.config.ConfigDTO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import static com.github.leo_proger.ScreenManager.getCursorPosition;
import static com.github.leo_proger.config.Config.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
//        System.out.println(getCursorPosition());
//        System.exit(1);

        int x1 = 2426;
        int y1 = 312;

        int x2 = 3271;
        int y2 = 386;

        int lotHeight = y2 - y1;
        int lotNumber = 6;

        BufferedImage s = ScreenManager.takeScreenshot(
                (int) Math.round(x1 + (x2 - x1) * 0.505),
                (int) Math.round((y1 + lotHeight * 0.3) + lotHeight * (lotNumber - 1) + 5 * (lotNumber - 1)),
                100,
                100
        );
//        ScreenManager.showImage(s);

//        StickerDetector stickerDetector = new ColorDetection(thresholdForColorDetection);
//        StickerDetector stickerDetector = new StandardDeviation(thresholdForStandardDeviation);

        //        Buyer buyer = new Buyer(stickerDetector, 4);
//        buyer.run();

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Выберите цифру:");
//        System.out.println("1. Ловить наклейки");
//        System.out.println("2. Откалибровать координаты");
//        System.out.print(">>> ");
//        int choice1 = sc.nextInt();
//
//        if (choice1 == 1) {
//
//        } else if (choice1 == 2) {
//            System.out.print(">>> ");
//            int choice2 = sc.nextInt();
//
//
//                System.out.println("Калибровка завершена успешно");
//
//        }
//        System.in.read();
    }

    public static void checkDetection(StickerDetector stickerDetector) {
        for (int i = 0; i < 8; i++) {
//            BufferedImage s = ScreenManager.takeScreenshot(xStart - 122, yStart + lotHeight / 2 - 19 + lotHeight * i, width, height);
//            System.out.print("Лот " + (i + 1) + " - ");
//            stickerDetector.hasSticker(s);
        }
    }
}
