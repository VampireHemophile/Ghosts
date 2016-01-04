package com.vampirehemophile.ghosts.assets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageLoader {

	public static int squareWidth = 100, squareHeight = 100;
	public static int imageWidth = 95, imageHeight = 90;
	
	//images
	public static BufferedImage lightTile;
	public static BufferedImage darkTile;
	public static BufferedImage whiteGoodPawn;
	public static BufferedImage whiteEvilPawn;
	public static BufferedImage whiteNeutralPawn;
	public static BufferedImage blackGoodPawn;
	public static BufferedImage blackEvilPawn;
	public static BufferedImage blackNeutralPawn;
	
	public static void init() { 
		lightTile        = ImageLoader.loadImage("/lighttile.png");
		darkTile         = ImageLoader.loadImage("/darktile.png");
		
		whiteGoodPawn    = ImageLoader.loadImage("/white/fantome3.png");
		whiteEvilPawn    = ImageLoader.loadImage("/white/fantome4.png");
		whiteNeutralPawn = ImageLoader.loadImage("/white/neutralpawn.png");
		blackGoodPawn    = ImageLoader.loadImage("/black/fantome3.png");
		blackEvilPawn    = ImageLoader.loadImage("/black/fantome4.png");
		blackNeutralPawn = ImageLoader.loadImage("/black/neutralpawn.png");
	}
	
	
	public static BufferedImage loadImage(String path){
		try  {
			return ImageIO.read(ImageLoader.class.getResource(path));
			//return ImageIO.read(this.getClass().getResource("res/textures/beam.png"));
		
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static BufferedImage loadImageResized(String path) {
		try {
			
			BufferedImage temp = ImageIO.read(ImageLoader.class.getResource(path));
			return (BufferedImage) temp.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
