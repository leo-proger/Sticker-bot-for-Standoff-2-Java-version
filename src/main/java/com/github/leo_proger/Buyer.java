package com.github.leo_proger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.leo_proger.Main.*;

public class Buyer {

    private volatile boolean isRunning = true;

    private final Point refreshButton = new Point(760, 280); // Кнопка для обновления лотов (чтобы купленные пропали)
    private final int refreshLotsFrequency = 10; // В секундах
    private final int delayAfterRefresh = 300; // В миллисекундах. Нужно, чтобы избежать ложных срабатываний. Увеличьте значение, если у вас плохой интернет

    private final ReentrantLock lock = new ReentrantLock();

    public void run() throws InterruptedException {
        Thread refreshThread = new Thread(this::refreshLots);
        refreshThread.setDaemon(true);
        refreshThread.start();

        while (isRunning) {
            lock.lock();

            try {
                BufferedImage image = ScreenManager.takeScreenshot(
                        xStart - 19,
                        yStart + lotHeight / 2 - 19 + lotHeight * 0,
                        width,
                        height
                );
                if (isThereSticker(image, 30)) {
                    buyLot(buyButton, confirmPurchaseButton);
                    System.out.println("Куплено!");
                    isRunning = false;
                    break;
                }
            } finally {
                lock.unlock();
            }
            Thread.sleep(10);
        }
    }

    private boolean isThereSticker(BufferedImage image, int brightnessThreshold) {
        return StickerDetector.hasSticker(image, brightnessThreshold);
    }

    private void buyLot(Point buyButton, Point confirmPurchaseButton) throws InterruptedException {
        RobotManager.click(buyButton.x, buyButton.y);
        Thread.sleep(300);
        RobotManager.click(confirmPurchaseButton.x, confirmPurchaseButton.y);
    }

    private void refreshLots() {
        while (isRunning) {
            try {
                Thread.sleep(refreshLotsFrequency * 1000);
            } catch (InterruptedException e) {
                System.out.println("ОШИБКА: Не могу приостановиться во время обновления слотов");
                break;
            }

            lock.lock();
            try {
                RobotManager.click(refreshButton.x, refreshButton.y);
                RobotManager.click(refreshButton.x, refreshButton.y);
                Thread.sleep(delayAfterRefresh);
            } catch (InterruptedException e) {
                System.out.println("ОШИБКА: Не могу приостановиться во время обновления слотов");
                break;
            } finally {
                lock.unlock();
            }
        }
    }
}
