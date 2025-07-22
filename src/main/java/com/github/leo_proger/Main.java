package com.github.leo_proger;


import com.github.leo_proger.config.ConfiguringType;
import com.github.leo_proger.config.CoordsConfigurer;
import com.github.leo_proger.detection.ColorDetection;
import com.github.leo_proger.detection.StandardDeviation;
import com.github.leo_proger.detection.StickerDetector;
import com.github.leo_proger.ui.ScreenManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Scanner;

import static com.github.leo_proger.config.Config.*;
import static com.github.leo_proger.ui.ScreenManager.getCursorPosition;
import static com.github.leo_proger.utils.MathUtils.*;


public class Main {

	private static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		int selectNumber = showMainMenu();

		switch (selectNumber)
		{
			case 1 -> runStickerCatching();
			case 2 -> calibration();
			case 3 -> checkStickerPosition();
			default -> System.out.println("ОШИБКА: Неизвестная опция");
		}

		System.in.read();
	}

	private static int showMainMenu() {
		System.out.println("Выберите цифру:");
		System.out.println("1. Ловить наклейки");
		System.out.println("2. Настроить координаты");
		System.out.println("3. Посмотреть, как настроились координаты");
		System.out.print(">>> ");
		return sc.nextInt();
	}

	private static void runStickerCatching() throws InterruptedException {
		System.out.println("Введите количество наклеек (от 1 до 4):");
		System.out.print(">>> ");
		int stickerNumber = sc.nextInt();

		StickerDetector stickerDetector = createStickerDetector();
		Buyer buyer = new Buyer(stickerDetector, stickerNumber);
		buyer.run();
	}

	private static StickerDetector createStickerDetector() {
		if (stickerDetectionMethod == 1)
		{
			return new ColorDetection(thresholdForColorDetection);
		} else if (stickerDetectionMethod == 2)
		{
			return new StandardDeviation(thresholdForStandardDeviation);
		} else
		{
			throw new IllegalArgumentException("ОШИБКА: Неверный метод определения наклеек");
		}
	}

	private static void checkStickerPosition() {
		sc.nextLine();

		System.out.print("Введите через пробел количество наклеек и номер лота, которые хотите проверить >>> ");

		String[] data = sc.nextLine().split(" ");

		int stickerNumber = Integer.parseInt(data[0]);
		int lotNumber = Integer.parseInt(data[1]);

		showSticker(stickerNumber, lotNumber - 1);
	}

	private static void calibration() throws Exception {
		System.out.println("Выберите компонент для настройки:");
		System.out.println("1. Размер лота (нужно в принципе для ловли наклеек)");
		System.out.println("2. Кнопка покупки лота");
		System.out.println("3. Кнопка подтверждения покупки");
		System.out.println("4. Кнопка обновления лотов (чекбокс \"Только мои запросы\")");
		System.out.print(">>> ");

		int setting = sc.nextInt();

		switch (setting)
		{
			case 1 -> new CoordsConfigurer(ConfiguringType.LOT_SIZE).configure();
			case 2 -> new CoordsConfigurer(ConfiguringType.BUY_BUTTON_X).configure();
			case 3 -> new CoordsConfigurer(ConfiguringType.CONFIRM_PURCHASE_BUTTON).configure();
			case 4 -> new CoordsConfigurer(ConfiguringType.REFRESH_BUTTON).configure();
			default -> System.out.println("ОШИБКА: Неверная опция");
		}
		System.out.println("Еще что-нибудь настроить?");
		System.out.println("1. Да, продолжить настройку");
		System.out.println("2. Нет, выйти");
		System.out.print(">>> ");

		int toContinue = sc.nextInt();
		if (toContinue == 1) calibration();
		else if (toContinue == 2) CoordsConfigurer.finishSettingUp();
	}

	private static void showSticker(int stickerNumber, int lotNumber) {
		// int x1 = 2667, y1 = 312;
		// int x2 = 3512, y2 = 386;

		int lotHeight = y2 - y1;
		int lotWidth = x2 - x1;
		int lotIndent = getLotIndent(lotHeight, 0.08);

		int stickerWidthAndHeight = getStickerWidthAndHeight(lotHeight, 0.43);

		double xMultiplier = 0;
		switch (stickerNumber)
		{
			case 1 -> xMultiplier = 0.505;
			case 2 -> xMultiplier = 0.465;
			case 3 -> xMultiplier = 0.423;
			case 4 -> xMultiplier = 0.382;
		}

		BufferedImage s = ScreenManager.takeScreenshot(
				getStickerPosByX(x1, lotWidth, xMultiplier),
				getStickerPosByY(y1, lotHeight, 0.3, lotNumber, lotIndent),
				stickerWidthAndHeight,
				stickerWidthAndHeight
		);
		ScreenManager.showImage(s);
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

	private static void printCursorPos() {
		Point point = getCursorPosition();
		System.out.println("int x1 = " + point.x + ", y1 = " + point.y + ";");
		System.out.println("int x2 = " + point.x + ", y2 = " + point.y + ";");
		System.exit(1);
	}

}
