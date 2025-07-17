package com.github.leo_proger.config;


import java.awt.*;

public class ConfigDTO {
    private int xStart;
    private int yStart;

    private int width;
    private int height;

    private int lotHeight;

    private int buyButtonX;
    private Point confirmPurchaseButton;

    public int getX_start() {
        return xStart;
    }

    public void setX_start(int x_start) {
        this.xStart = x_start;
    }

    public int getY_start() {
        return yStart;
    }

    public void setY_start(int y_start) {
        this.yStart = y_start;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLot_height() {
        return lotHeight;
    }

    public void setLot_height(int lot_height) {
        this.lotHeight = lot_height;
    }

    public int getBuy_button_x() {
        return buyButtonX;
    }

    public void setBuy_button_x(int buy_button_x) {
        this.buyButtonX = buy_button_x;
    }

    public Point getConfirm_purchase_button() {
        return confirmPurchaseButton;
    }

    public void setConfirm_purchase_button(Point confirm_purchase_button) {
        this.confirmPurchaseButton = confirm_purchase_button;
    }
}
