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
  public InvalidCoordinatesException(final String xy) {
    super("(" + xy + ") are invalid coordinates.");
  }

  /**
   * Constructor for InvalidCoordinatesException.
   *
   * @param x a {@link java.lang.String} object.
   * @param y an int.
   */
  public InvalidCoordinatesException(final String x, final int y) {
    super("(" + x + ", " + y + ") are invalid coordinates.");
  }

  /**
   * Constructor for InvalidCoordinatesException.
   *
   * @param x an int.
   * @param y an int.
   */
  public InvalidCoordinatesException(final int x, final int y) {
    super("(" + x + ", " + y + ") are invalid coordinates.");
  }
}
