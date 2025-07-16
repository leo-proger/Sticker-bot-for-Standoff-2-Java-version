package com.github.leo_proger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.leo_proger.Main.*;

public class Buyer {

    private volatile boolean isRunning = true;

    private final Point refreshButton = new Point(760, 280); // Кнопка для обновления лотов (чтобы купленные пропали)
    private final int refreshLotsFrequency = 10; // В секундах
    private final int delayAfterRefresh = 300; // В миллисекундах. Нужно, чтобы избежать ложных срабатываний. Увеличьте значение, если у вас плохой интернет
    private final int threshold = 700; // Если есть ложные срабатывания, увеличьте значение

    private final Lock lock = new ReentrantLock();

    private final StickerDetector stickerDetector;

    public Buyer(StickerDetector stickerDetector) {
        this.stickerDetector = stickerDetector;
    }

    public void run() throws InterruptedException {
        Thread refreshThread = new Thread(this::refreshLots);
        refreshThread.setDaemon(true);
        refreshThread.start();

        Thread[] workerThreads = new Thread[2];
        for (int i = 0; i < workerThreads.length; i++) {
            final int threadIndex = i;
            workerThreads[i] = new Thread(() -> checkLots(threadIndex));
            workerThreads[i].start();
        }

        for (Thread workerThread : workerThreads) {
            workerThread.join();
        }
    }

    private void checkLots(int threadIndex) {
        int startLot = threadIndex * 4;
        int endLot = startLot + 4;

        while (isRunning) {
            if (lock.tryLock()) {

                try {
                    for (int i = startLot; i < endLot; i++) {
                        if (!isRunning) break;

                        BufferedImage image = ScreenManager.takeScreenshot(
                                xStart - 19,
                                yStart + lotHeight / 2 - 19 + lotHeight * i,
                                width,
                                height
                        );
                        if (stickerDetector.hasSticker(image, threshold)) {

                            try {
                                buyLot(new Point(1400, yStart + lotHeight / 2 + lotHeight * i), confirmPurchaseButton);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                System.out.println("ОШИБКА: Прерывание во время покупки лота");
                            }
                            System.out.println("Куплено!");
                            isRunning = false;
                            break;
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("ОШИБКА: Прерывание во время ожидания");
            }
        }
    }

    private void buyLot(Point buyButton, Point confirmPurchaseButton) throws InterruptedException {
        RobotManager.click(buyButton.x, buyButton.y);
        Thread.sleep(180);
        RobotManager.click(confirmPurchaseButton.x, confirmPurchaseButton.y);
    }

    private void refreshLots() {
        while (isRunning) {
            try {
                Thread.sleep(refreshLotsFrequency * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("ОШИБКА: Прерывание во время обновления слотов");
                break;
            }

            lock.lock();

            try {
                RobotManager.click(refreshButton.x, refreshButton.y);
                Thread.sleep(20);
                RobotManager.click(refreshButton.x, refreshButton.y);
                Thread.sleep(delayAfterRefresh);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("ОШИБКА: Прерывание во время обновления слотов");
                break;
            } finally {
                lock.unlock();
            }
        }
    }
}
