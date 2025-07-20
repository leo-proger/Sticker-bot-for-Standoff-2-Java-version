package com.github.leo_proger.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Config {

	public static int x1; // Координата левого верхнего угла лота по X
	public static int y1; // Координата левого верхнего угла лота по Y

	public static int x2; // Координата правого нижнего угла лота по X
	public static int y2; // Координата правого нижнего угла лота по Y

	public static int buyButtonX; // Координаты кнопки покупки лота по X
	public static Point confirmPurchaseButton; // Координаты кнопки подтверждения покупки (полные координаты, выраженные классом Point)
	public static Point refreshButton; // Кнопка для обновления лотов (чтобы купленные пропали)

	public static int refreshLotsFrequency; // Частота обновления лотов в секундах
	public static int delayAfterRefresh; // В миллисекундах. Нужно, чтобы избежать ложных срабатываний. Увеличьте значение, если у вас плохой интернет

	public static int thresholdForColorDetection; // Порог для распознавания наклеек по цвету
	public static int thresholdForStandardDeviation; // Порог для распознавания наклеек по среднеквадратичному отклонению

	public static int stickerDetectionMethod; // Метод определения наклеек (1 - сравнение цветов, 2 - среднеквадратичное отклонение)

	static
	{
		try
		{
			ConfigDTO dto = loadDataFromJson();

			x1 = dto.getX1();
			y1 = dto.getY1();

			x2 = dto.getX2();
			y2 = dto.getY2();

			buyButtonX = dto.getBuyButtonX();
			confirmPurchaseButton = dto.getConfirmPurchaseButton();
			refreshButton = dto.getRefreshButton();

			refreshLotsFrequency = dto.getRefreshLotsFrequency();
			delayAfterRefresh = dto.getDelayAfterRefresh();

			thresholdForColorDetection = dto.getThresholdForColorDetection();
			thresholdForStandardDeviation = dto.getThresholdForStandardDeviation();

			stickerDetectionMethod = dto.getStickerDetectionMethod();
		} catch (IOException e)
		{
			throw new RuntimeException("ОШИБКА: Не удалось загрузить config.json", e);
		}
	}

	public static ConfigDTO loadDataFromJson() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		Path json = getConfigPath();
		if (!Files.exists(json))
		{
			throw new FileNotFoundException("ОШИБКА: Файл с настройками не найден. Попробуйте откалибровать координаты");
		}

		return mapper.readValue(json.toFile(), ConfigDTO.class);
	}

	public static Path getConfigPath() {
		return Path.of(System.getProperty("user.dir"), "config.json");
	}

	public static void saveDataToJson(ConfigDTO configDTO) {
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

			File json = getConfigPath().toFile();
			objectWriter.writeValue(json, configDTO);
		} catch (IOException e)
		{
			throw new RuntimeException("ОШИБКА: Не удалось сохранить данные", e);
		}
	}

}
