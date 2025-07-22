package com.github.leo_proger.config;


import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CoordsConfigurer implements NativeMouseListener {

	private final CountDownLatch clickLatch;
	private final ConfiguringType configuringType;
	private final ArrayList<Point> clicks = new ArrayList<>(2);

	static
	{
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackageName());
		logger.setLevel(Level.OFF);

		try
		{
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e)
		{
			throw new RuntimeException(e);
		}
	}

	public CoordsConfigurer(ConfiguringType configuringType) {
		this.configuringType = configuringType;

		clickLatch = new CountDownLatch(configuringType.getClicks());
	}

	public void configure() throws IOException, URISyntaxException {
		System.out.println("При нажатии Enter появится окно эмулятора");
		System.out.println(configuringType.getMessage());

		System.in.read();
		focusOnApp();

		GlobalScreen.addNativeMouseListener(this);

		System.out.println("Ожидание кликов...");

		try
		{
			clickLatch.await();
		} catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		} finally
		{
			GlobalScreen.removeNativeMouseListener(this);
		}

	}

	private void focusOnApp() throws IOException, URISyntaxException {
		URL url = CoordsConfigurer.class.getClassLoader().getResource("com/github/leo_proger/focusOnWindow.vbs");
		if (url == null)
		{
			throw new IllegalStateException("ОШИБКА: Файл из ресурсов не найден");
		}
		Path scriptFile = Path.of(url.toURI());
		String scriptPath = scriptFile.toAbsolutePath().toString();

		Runtime.getRuntime().exec(new String[]{
				"wscript.exe", scriptPath
		});
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		System.out.printf("Зафиксирован клик (x=%d, y=%d)\n", x, y);
		clickLatch.countDown();
		clicks.add(new Point(x, y));

		if (clickLatch.getCount() == 0) saveClicks();
	}

	private void saveClicks() {
		try
		{
			ConfigDTO configDTO = Config.loadDataFromJson();

			switch (configuringType)
			{
				case LOT_SIZE -> configDTO.setLotSize(clicks.getFirst(), clicks.getLast());
				case BUY_BUTTON_X -> configDTO.setBuyButtonX(clicks.getFirst().x);
				case CONFIRM_PURCHASE_BUTTON -> configDTO.setConfirmPurchaseButton(clicks.getFirst());
				case REFRESH_BUTTON -> configDTO.setRefreshButton(clicks.getFirst());
				default -> System.out.println("ОШИБКА: Нет обработки для данной опции");
			}

			Config.saveDataToJson(configDTO);
		} catch (IOException e)
		{
			throw new RuntimeException("ОШИБКА: Не удалось загрузить конфиг", e);
		}
	}

	public static void finishSettingUp() throws NativeHookException {
		GlobalScreen.unregisterNativeHook();
	}

}
