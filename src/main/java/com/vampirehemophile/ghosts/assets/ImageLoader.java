package com.vampirehemophile.ghosts.assets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageLoader {

	public static int SQUARE_SIZE = 100;
	public static int IMAGE_WIDTH = 43;
	public static int IMAGE_HEIGHT = 90;
	public static int IMAGE_CENTER_X = SQUARE_SIZE / 2 - IMAGE_WIDTH  / 2;
	public static int IMAGE_CENTER_Y = SQUARE_SIZE / 2 - IMAGE_HEIGHT / 2;

	// images
	public static Image lightTile;
	public static Image darkTile;
	public static Image whiteGoodPawn;
	public static Image whiteEvilPawn;
	public static Image whiteNeutralPawn;
	public static Image blackGoodPawn;
	public static Image blackEvilPawn;
	public static Image blackNeutralPawn;

	public static void init() {
		lightTile        = ImageLoader.loadResizedImage("/images/lighttile.png");
		darkTile         = ImageLoader.loadResizedImage("/images/darktile.png");

		whiteGoodPawn    = ImageLoader.loadResizedImage("/images/white/goodpawn.png");
		whiteEvilPawn    = ImageLoader.loadResizedImage("/images/white/evilpawn.png");
		whiteNeutralPawn = ImageLoader.loadResizedImage("/images/white/neutralpawn.png");
		blackGoodPawn    = ImageLoader.loadResizedImage("/images/black/goodpawn.png");
		blackEvilPawn    = ImageLoader.loadResizedImage("/images/black/evilpawn.png");
		blackNeutralPawn = ImageLoader.loadResizedImage("/images/black/neutralpawn.png");
	}


	public static BufferedImage loadImage(String path){
		try  {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			System.out.println(path);
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public static Image loadResizedImage(String path) {
		return loadImage(path).getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
	}
}
