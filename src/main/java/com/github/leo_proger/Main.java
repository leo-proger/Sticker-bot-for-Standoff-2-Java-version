package com.github.leo_proger;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import static com.github.leo_proger.MathUtils.*;
import static com.github.leo_proger.ScreenManager.getCursorPosition;
import static com.github.leo_proger.config.Config.*;


public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Выберите цифру:");
		System.out.println("1. Ловить наклейки");
		// System.out.println("2. Посмотреть область определения наклеек");
		// System.out.println("3. Откалибровать координаты");
		System.out.print(">>> ");
		int selectNumber = sc.nextInt();

		if (selectNumber == 1)
		{
			System.out.println("Введите количество наклеек (от 1 до 4):");
			System.out.print(">>> ");
			int stickerNumber = sc.nextInt();

			if (stickerDetectionMethod == 1)
			{
				StickerDetector stickerDetector = new ColorDetection(thresholdForColorDetection);
				Buyer buyer = new Buyer(stickerDetector, stickerNumber);
				buyer.run();
			} else if (stickerDetectionMethod == 2)
			{
				StickerDetector stickerDetector = new StandardDeviation(thresholdForStandardDeviation);
				Buyer buyer = new Buyer(stickerDetector, stickerNumber);
				buyer.run();
			}
		}
		System.in.read();
	}

	private static void checkDetection(StickerDetector stickerDetector) {
		for (int i = 0; i < 8; i++)
		{
			BufferedImage s = ScreenManager.takeScreenshot(
					getStickerPosByX(x1, x2 - x1, 0.505),
					getStickerPosByY(y1, y2 - y1, 0.3, 0, 5),
					getStickerWidthAndHeight(y2 - y1, 0.43),
					getStickerWidthAndHeight(y2 - y1, 0.43)
			);
			System.out.print("Лот " + (i + 1) + " - ");
			stickerDetector.hasSticker(s);
		}
		System.exit(1);
	}

	private static void showImage() {
		int x1 = 2667, y1 = 312;
		int x2 = 3512, y2 = 386;

		int lotNumber = 2;
		int lotHeight = y2 - y1;
		int lotWidth = x2 - x1;
		int lotIndent = getLotIndent(lotHeight, 0.08);

		int stickerWidthAndHeight = getStickerWidthAndHeight(lotHeight, 0.43);

		BufferedImage s = ScreenManager.takeScreenshot(
				getStickerPosByX(x1, lotWidth, 0.505),
				getStickerPosByY(y1, lotHeight, 0.3, lotNumber, lotIndent),
				stickerWidthAndHeight,
				stickerWidthAndHeight
		);
		ScreenManager.showImage(s);
	}

	private static void printCursorPos() {
		Point point = getCursorPosition();
		System.out.println("int x1 = " + point.x + ", y1 = " + point.y + ";");
		System.out.println("int x2 = " + point.x + ", y2 = " + point.y + ";");
		System.exit(1);
	}

}
