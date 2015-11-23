package com.vampirehemophile.ghosts.exceptions;

/**
 * Thrown when the coordinates points a location outside of the board.
 */
@SuppressWarnings("serial")
public class OutOfBoardCoordinatesException extends RuntimeException {
  /**
   * Constructor for OutOfBoardCoordinatesException.
   *
   * @param index an int.
   */
  public OutOfBoardCoordinatesException(int index) {
    super("(" + index + ") is out of the array.");
  }

  /**
   * Constructor for OutOfBoardCoordinatesException.
   *
   * @param xy a {@link java.lang.String} object.
   */
  public OutOfBoardCoordinatesException(String xy) {
    super("(" + xy + ") is out of the board.");
  }

  /**
   * Constructor for OutOfBoardCoordinatesException.
   *
   * @param x a {@link java.lang.String} object.
   * @param y an int.
   */
  public OutOfBoardCoordinatesException(String x, int y) {
    super("(" + x + ", " + y + ") is out of the board.");
  }

  /**
   * Constructor for OutOfBoardCoordinatesException.
   *
   * @param x an int.
   * @param y an int.
   */
  public OutOfBoardCoordinatesException(int x, int y) {
    super("(" + x + ", " + y + ") is out the matrix.");
  }
}
