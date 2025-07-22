package com.github.leo_proger.utils;


public class MathUtils {

	public static int getStickerPosByX(int x1, int lotWidth, double xMultiplier) {
		return Math.toIntExact(Math.round(x1 + lotWidth * xMultiplier));
	}

	public static int getStickerPosByY(int y1, int lotHeight, double yMultiplier, int lotNumber, int lotIndent) {
		return Math.toIntExact(Math.round(y1 + lotHeight * (yMultiplier + lotNumber) + lotIndent * lotNumber));
	}

	public static int getBuyButtonByY(int y1, int lotHeight, int lotNumber, int lotIndent) {
		return Math.toIntExact(Math.round(y1 + lotHeight * (0.5 + lotNumber) + lotIndent * lotNumber));
	}

	public static int getStickerWidthAndHeight(int lotHeight, double stickerWidthAndHeightMultiplier) {
		return Math.toIntExact(Math.round(lotHeight * stickerWidthAndHeightMultiplier));
	}

	public static int getLotIndent(int lotHeight, double lotIndentMultiplier) {
		return Math.toIntExact(Math.round(lotHeight * lotIndentMultiplier));
	}

}
