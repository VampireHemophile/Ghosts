package com.vampirehemophile.ghosts.exceptions;

/**
 * Thrown when the coordinates points a location outside of the board.
 */
@SuppressWarnings("serial")
public class OutOfBoardCoordinatesException extends RuntimeException {
  public OutOfBoardCoordinatesException(int index) {
    super("(" + index + ") is out of the array.");
  }

  public OutOfBoardCoordinatesException(String xy) {
    super("(" + xy + ") is out of the board.");
  }

  public OutOfBoardCoordinatesException(String x, int y) {
    super("(" + x + ", " + y + ") is out of the board.");
  }

  public OutOfBoardCoordinatesException(int x, int y) {
    super("(" + x + ", " + y + ") is out the matrix.");
  }
}
