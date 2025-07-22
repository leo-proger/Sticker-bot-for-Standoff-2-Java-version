package com.github.leo_proger.ui;


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;


public class RobotManager {

	private static final Robot robot;

	// Инициализация один раз для всего приложения
	static
	{
		try
		{
			robot = new Robot();
			robot.setAutoDelay(0);
		} catch (AWTException e)
		{
			throw new RuntimeException("Can't create Robot instance", e);
		}
	}

	public static Robot getRobot() {
		return robot;
	}

	public static void click(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static BufferedImage screenshot(int x, int y, int width, int height) {
		return robot.createScreenCapture(new Rectangle(x, y, width, height));
	}

}
