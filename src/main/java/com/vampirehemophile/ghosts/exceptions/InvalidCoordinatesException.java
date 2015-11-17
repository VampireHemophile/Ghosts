package com.vampirehemophile.ghosts.exceptions;

/**
 * Thrown when the coordinates format is invalid.
 */
@SuppressWarnings("serial")
public class InvalidCoordinatesException extends RuntimeException {
  public InvalidCoordinatesException(String xy) {
    super("(" + xy + ") are invalid coordinates.");
  }

  public InvalidCoordinatesException(String x, int y) {
    super("(" + x + ", " + y + ") are invalid coordinates.");
  }

  public InvalidCoordinatesException(int x, int y) {
    super("(" + x + ", " + y + ") are invalid coordinates.");
  }
}
