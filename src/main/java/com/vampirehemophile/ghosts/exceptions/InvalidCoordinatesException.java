package com.vampirehemophile.ghosts.exceptions;

/**
 * Thrown when the coordinates format is invalid.
 */
@SuppressWarnings("serial")
public class InvalidCoordinatesException extends RuntimeException {

  /**
   * Constructor for InvalidCoordinatesException.
   *
   * @param xy a {@link java.lang.String} object.
   */
  public InvalidCoordinatesException(String xy) {
    super("(" + xy + ") are invalid coordinates.");
  }

  /**
   * Constructor for InvalidCoordinatesException.
   *
   * @param x a {@link java.lang.String} object.
   * @param y an int.
   */
  public InvalidCoordinatesException(String x, int y) {
    super("(" + x + ", " + y + ") are invalid coordinates.");
  }

  /**
   * Constructor for InvalidCoordinatesException.
   *
   * @param x an int.
   * @param y an int.
   */
  public InvalidCoordinatesException(int x, int y) {
    super("(" + x + ", " + y + ") are invalid coordinates.");
  }
}
