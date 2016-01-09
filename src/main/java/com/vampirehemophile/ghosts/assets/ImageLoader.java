package com.vampirehemophile.ghosts.assets;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


/**
 * Provides utility functions to load images from the resource path.
 */
public class ImageLoader {

  // Image dimensions constants

  /** Displayed size of a square tile. */
  public static final int SQUARE_SIZE = 100;

  /** Displayed width of a pawn's image. */
  public static final int IMAGE_WIDTH = 90;

  /** Displayed width of a pawn's image. */
  public static final int IMAGE_HEIGHT = 95;


  // Utility constants

  /** X-center of a pawn image. */
  public static final int IMAGE_CENTER_X = IMAGE_WIDTH / 2;

  /** Y-center of a pawn image. */
  public static final int IMAGE_CENTER_Y = IMAGE_HEIGHT / 2;

  /** X-offset to display a pawn image relative to a square tile. */
  public static final int IMAGE_TOP_X = SQUARE_SIZE / 2 - IMAGE_CENTER_X;

  /** Y-offset to display a pawn image relative to a square tile. */
  public static final int IMAGE_TOP_Y = SQUARE_SIZE / 2 - IMAGE_CENTER_Y;


  // Images

  /** Image of the light square tile. */
  public static final Image LIGHT_TILE = ImageLoader.loadResizedTile(
      "/images/lighttile.png");

  /** Image of the dark square tile. */
  public static final Image DARK_TILE = ImageLoader.loadResizedTile(
      "/images/darktile.png");

  /** Image of the good white pawn. */
  public static final Image WHITE_GOOD_PAWN = ImageLoader.loadResizedImage(
      "/images/white/goodpawn.png");

  /** Image of the evil white pawn. */
  public static final Image WHITE_EVIL_PAWN = ImageLoader.loadResizedImage(
      "/images/white/evilpawn.png");

  /** Image of the neutral white pawn. */
  public static final Image WHITE_NEUTRAL_PAWN = ImageLoader.loadResizedImage(
      "/images/white/neutralpawn.png");

  /** Image of the good black pawn. */
  public static final Image BLACK_GOOD_PAWN = ImageLoader.loadResizedImage(
      "/images/black/goodpawn.png");

  /** Image of the evil black pawn. */
  public static final Image BLACK_EVIL_PAWN = ImageLoader.loadResizedImage(
      "/images/black/evilpawn.png");

  /** Image of the neutral black pawn. */
  public static final Image BLACK_NEUTRAL_PAWN = ImageLoader.loadResizedImage(
      "/images/black/neutralpawn.png");


  /**
   * Prevents calls from subclass.
   *
   * @throws UnsupportedOperationException if called.
   */
  protected ImageLoader() {
    throw new UnsupportedOperationException();
  }

  /**
   * Loads an image from the resource path.
   *
   * @param path the image path.
   * @return the image.
   */
  public static BufferedImage loadImage(final String path) {
    try {
      return ImageIO.read(ImageLoader.class.getResource(path));
    } catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Loads a tile image.
   *
   * @param path the image path.
   * @return the image.
   */
  public static Image loadResizedTile(final String path) {
    return loadImage(path).getScaledInstance(
        SQUARE_SIZE, SQUARE_SIZE, Image.SCALE_SMOOTH);
  }

  /**
   * Loads a pawn image.
   *
   * @param path the image path.
   * @return the image.
   */
  public static Image loadResizedImage(final String path) {
    return loadImage(path).getScaledInstance(
        IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
  }
}
