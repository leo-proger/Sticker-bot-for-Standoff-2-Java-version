package com.github.leo_proger;


import com.github.leo_proger.detection.StickerDetector;
import com.github.leo_proger.ui.RobotManager;
import com.github.leo_proger.ui.ScreenManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.leo_proger.utils.MathUtils.*;
import static com.github.leo_proger.config.Config.*;


public class Buyer {

	private final double xMultiplier; // Коэффициент, отвечающий за нахождение наклеек по X
	private final double yMultiplier = 0.3; // Коэффициент, отвечающий за нахождение наклеек по Y
	private final double lotIndentMultiplier = 0.08; // Коэффициент, отвечающий за отступ между лотами
	private final double stickerWidthAndHeightMultiplier = 0.43; // Коэффициент, отвечающий ширину и высоту наклейки

	private final int lotWidth = x2 - x1; // Ширина лота в пикселях
	private final int lotHeight = y2 - y1; // Высота лота в пикселях
	private final int lotIndent = getLotIndent(lotHeight, lotIndentMultiplier); // Отступ между лотами в пикселях
	private final int stickerWidthAndHeight = getStickerWidthAndHeight(
			lotHeight,
			stickerWidthAndHeightMultiplier
	); // Ширина наклейки в пикселях

	private final StickerDetector stickerDetector; // Механизм обнаружения наклеек
	private volatile boolean isRunning = true; // Для многопоточности
	private final Lock lock = new ReentrantLock();

	public Buyer(StickerDetector stickerDetector, int stickerCount) {
		this.stickerDetector = stickerDetector;

		if (stickerCount == 1) xMultiplier = 0.505;
		else if (stickerCount == 2) xMultiplier = 0.465;
		else if (stickerCount == 3) xMultiplier = 0.423;
		else if (stickerCount == 4) xMultiplier = 0.382;
		else throw new IllegalArgumentException("ОШИБКА: Количество наклеек должно быть от 1 до 4");

	}

	public void run() throws InterruptedException {
		System.out.println("Поиск лотов с наклейками...");

		if (refreshLotsFrequency != 0)
		{
			Thread refreshThread = new Thread(this::refreshLots);
			refreshThread.setDaemon(true);
			refreshThread.start();
		}

		Thread[] workerThreads = new Thread[2];
		for (int i = 0; i < workerThreads.length; i++)
		{
			final int threadIndex = i;
			workerThreads[i] = new Thread(() -> checkLots(threadIndex));
			workerThreads[i].start();
		}

		for (Thread workerThread : workerThreads)
		{
			workerThread.join();
		}
	}

	private void checkLots(int threadIndex) {
		int startLot = threadIndex * 4;
		int endLot = startLot + 4;

		while (isRunning)
		{
			if (lock.tryLock())
			{
				try
				{
					for (int lotNumber = startLot; lotNumber < endLot; lotNumber++)
					{
						if (!isRunning) break;

						BufferedImage image = ScreenManager.takeScreenshot(
								getStickerPosByX(x1, lotWidth, xMultiplier),
								getStickerPosByY(y1, lotHeight, yMultiplier, lotNumber, lotIndent),
								stickerWidthAndHeight,
								stickerWidthAndHeight
						);
						if (stickerDetector.hasSticker(image))
						{
							try
							{
								buyLot(
										new Point(buyButtonX, getBuyButtonByY(y1, lotHeight, lotNumber, lotIndent)),
										confirmPurchaseButton
								);
							} catch (InterruptedException e)
							{
								Thread.currentThread().interrupt();
								System.out.println("ОШИБКА: Прерывание во время покупки лота");
							}
							System.out.println("Куплено!");
							isRunning = false;
							break;
						}
					}
				} finally
				{
					lock.unlock();
				}
			}

			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
				System.out.println("ОШИБКА: Прерывание во время ожидания");
			}
		}
	}

	private void buyLot(Point buyButton, Point confirmPurchaseButton) throws InterruptedException {
		RobotManager.click(buyButton.x, buyButton.y);
		Thread.sleep(180); // Ждем появления окна с подтверждением покупки
		RobotManager.click(confirmPurchaseButton.x, confirmPurchaseButton.y);
	}

	private void refreshLots() {
		while (isRunning)
		{
			try
			{
				Thread.sleep(refreshLotsFrequency * 1000L);
			} catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
				System.out.println("ОШИБКА: Прерывание во время обновления слотов");
				break;
			}

			lock.lock();

			try
			{
				RobotManager.click(refreshButton.x, refreshButton.y);
				Thread.sleep(70); // Ждем, потому что кнопка перезагрузки лотов просто не успевает среагировать на 2ой клик
				RobotManager.click(refreshButton.x, refreshButton.y);
				Thread.sleep(delayAfterRefresh);
			} catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
				System.out.println("ОШИБКА: Прерывание во время обновления слотов");
				break;
			} finally
			{
				lock.unlock();
			}
		}
	}

}
