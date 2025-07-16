package com.github.leo_proger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenManager {

    private static final Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static Point getCursorPosition() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        if (pointerInfo == null) {
            return null;
        }
        return pointerInfo.getLocation();
    }

    public static BufferedImage takeScreenshot(int x, int y, int width, int height) {
        return RobotManager.screenshot(x, y, width, height);
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

    // Показывает яркие области (потенциальные наклейки)
    public static void showBrightRegions(BufferedImage image, int brightnessThreshold) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Вычисляем средний фон
        long totalBrightness = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                totalBrightness += getBrightness(image.getRGB(x, y));
            }
        }
        int avgBrightness = (int) (totalBrightness / (width * height));

        // Создаем изображение с выделенными областями
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);

        // Выделяем яркие области зеленым
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int brightness = getBrightness(image.getRGB(x, y));

                if (brightness > avgBrightness + brightnessThreshold) {
                    // Зеленый цвет для ярких областей
                    result.setRGB(x, y, 0x00FF00);
                }
            }
        }

        g.dispose();
        showImage(result);
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
        Timer timer = new Timer(100, e -> {
            BufferedImage screenshot = ScreenManager.takeScreenshot(x, y, width, height);
            label.setIcon(new ImageIcon(screenshot));
        });
        timer.start();
    }

    private static int getBrightness(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return (r + g + b) / 3;
    }
}
