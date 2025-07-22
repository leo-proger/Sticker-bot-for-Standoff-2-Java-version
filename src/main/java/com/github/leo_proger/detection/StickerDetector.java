package com.github.leo_proger.detection;


import java.awt.image.BufferedImage;


public interface StickerDetector {

	boolean hasSticker(BufferedImage image);

}