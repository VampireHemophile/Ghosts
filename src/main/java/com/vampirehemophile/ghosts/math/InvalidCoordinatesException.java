package com.vampirehemophile.ghosts.math;

/**
 * Thrown when the coordinates format is invalid.
 */
@SuppressWarnings("serial")
public class InvalidCoordinatesException extends RuntimeException {
  public InvalidCoordinatesException(int index) {
    super("(" + index + ") is an invalid index.");
  }

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
