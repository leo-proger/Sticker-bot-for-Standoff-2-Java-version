package com.github.leo_proger.config;


import java.awt.*;


public class ConfigDTO {

	private int x1;
	private int y1;

	private int x2;
	private int y2;

	private int buyButtonX;
	private Point confirmPurchaseButton;
	private Point refreshButton;

	private int refreshLotsFrequency;
	private int delayAfterRefresh;

	private int thresholdForColorDetection;
	private int thresholdForStandardDeviation;

	private int stickerDetectionMethod;

	public ConfigDTO() {
	}

	public ConfigDTO(
			int x1,
			int y1,
			int x2,
			int y2,
			int buyButtonX,
			Point confirmPurchaseButton,
			Point refreshButton,
			int refreshLotsFrequency,
			int delayAfterRefresh,
			int thresholdForColorDetection,
			int thresholdForStandardDeviation,
			int stickerDetectionMethod
	) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.buyButtonX = buyButtonX;
		this.confirmPurchaseButton = confirmPurchaseButton;
		this.refreshButton = refreshButton;
		this.refreshLotsFrequency = refreshLotsFrequency;
		this.delayAfterRefresh = delayAfterRefresh;
		this.thresholdForColorDetection = thresholdForColorDetection;
		this.thresholdForStandardDeviation = thresholdForStandardDeviation;
		this.stickerDetectionMethod = stickerDetectionMethod;
	}

	public void setLotSize(Point upperLeftCorner, Point lowerRightCorner) {
		this.x1 = upperLeftCorner.x;
		this.y1 = upperLeftCorner.y;

		this.x2 = lowerRightCorner.x;
		this.y2 = lowerRightCorner.y;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getBuyButtonX() {
		return buyButtonX;
	}

	public void setBuyButtonX(int buyButtonX) {
		this.buyButtonX = buyButtonX;
	}

	public Point getConfirmPurchaseButton() {
		return confirmPurchaseButton;
	}

	public void setConfirmPurchaseButton(Point confirmPurchaseButton) {
		this.confirmPurchaseButton = confirmPurchaseButton;
	}

	public Point getRefreshButton() {
		return refreshButton;
	}

	public void setRefreshButton(Point refreshButton) {
		this.refreshButton = refreshButton;
	}

	public int getRefreshLotsFrequency() {
		return refreshLotsFrequency;
	}

	public void setRefreshLotsFrequency(int refreshLotsFrequency) {
		this.refreshLotsFrequency = refreshLotsFrequency;
	}

	public int getDelayAfterRefresh() {
		return delayAfterRefresh;
	}

	public void setDelayAfterRefresh(int delayAfterRefresh) {
		this.delayAfterRefresh = delayAfterRefresh;
	}

	public int getThresholdForColorDetection() {
		return thresholdForColorDetection;
	}

	public void setThresholdForColorDetection(int thresholdForColorDetection) {
		this.thresholdForColorDetection = thresholdForColorDetection;
	}

	public int getThresholdForStandardDeviation() {
		return thresholdForStandardDeviation;
	}

	public void setThresholdForStandardDeviation(int thresholdForStandardDeviation) {
		this.thresholdForStandardDeviation = thresholdForStandardDeviation;
	}

	public int getStickerDetectionMethod() {
		return stickerDetectionMethod;
	}

	public void setStickerDetectionMethod(int stickerDetectionMethod) {
		this.stickerDetectionMethod = stickerDetectionMethod;
	}

}
