package com.github.leo_proger;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ScreenManager {

	private static final Robot robot;

	static
	{
		try
		{
			robot = new Robot();
		} catch (AWTException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static Point getCursorPosition() {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		if (pointerInfo == null)
		{
			return null;
		}
		return pointerInfo.getLocation();
	}

	// TODO: Реализовать, в конце показать окно, какое изображение было сделано
	public static void takeTemplateImage(int lotNumber) {
		//        return RobotManager.screenshot(x, y, width, height);
	}

	// Показывает изображение в новом окне
	public static void showImage(BufferedImage image) {
		JFrame frame = new JFrame();
		JLabel label = new JLabel(new ImageIcon(image));
		frame.add(label);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	// Постоянно обновляющееся окно с областью экрана
	public static void startLiveView(int x, int y, int width, int height) {
		JFrame frame = new JFrame("Live View");
		JLabel label = new JLabel();
		frame.add(label);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Обновляем каждые 100ms
		Timer timer = new Timer(
				100, e -> {
			BufferedImage screenshot = ScreenManager.takeScreenshot(x, y, width, height);
			label.setIcon(new ImageIcon(screenshot));
		}
		);
		timer.start();
	}

	public static BufferedImage takeScreenshot(int x, int y, int width, int height) {
		return RobotManager.screenshot(x, y, width, height);
	}

}
