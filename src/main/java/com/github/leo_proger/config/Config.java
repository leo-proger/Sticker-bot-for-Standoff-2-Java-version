package com.github.leo_proger.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class Config {
    public static final int xStart;
    public static final int yStart;

    public static final int width;
    public static final int height;

    public static final int lotHeight;

    public static final int buyButtonX;
    public static final Point confirmPurchaseButton;

    static {
        try {
            ConfigDTO dto = loadDataFromJson();

            xStart = dto.getX_start();
            yStart = dto.getY_start();

            width = dto.getWidth();
            height = dto.getHeight();

            lotHeight = dto.getLot_height();

            buyButtonX = dto.getBuy_button_x();
            confirmPurchaseButton = dto.getConfirm_purchase_button();
        } catch (IOException e) {
            throw new RuntimeException("ОШИБКА: Не удалось загрузить config.json", e);
        }
    }

    public static ConfigDTO loadDataFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Path json = Path.of(System.getProperty("user.dir"), "config.json");

        return mapper.readValue(json.toFile(), ConfigDTO.class);
    }
}
