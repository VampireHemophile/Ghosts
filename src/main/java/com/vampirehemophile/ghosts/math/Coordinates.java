package com.vampirehemophile.ghosts.math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vampirehemophile.ghosts.exceptions.BoardTooSmallException;
import com.vampirehemophile.ghosts.exceptions.InvalidCoordinatesException;
import com.vampirehemophile.ghosts.exceptions.OutOfBoardCoordinatesException;

import static com.vampirehemophile.ghosts.entities.Board.DEFAULT_BOARD_SIZE;

/**
 * <p>Coordinates provides a way of storing and manipulating coordinates.
 * Coordinates are immutable and always makes sure its coordinates are valid.
 * The format is valid, and they are located inside the board,
 * but is does not check wether the square it might represents on the board is
 * free or not. Assumes the board is a square.</p>
 *
 * <p>Let size be the size of one side of the board (size = 6 if board is 6x6).
 * The size must always be superior or equals to
 * {@link com.vampirehemophile.ghosts.entities.Board#DEFAULT_BOARD_SIZE}.
 * There are three types of coordinates :</p><ul>
 * <li>"real-world" coordinates indexing the board like a chessboard,</li>
 * <li>"matrix" coordinates indexing the board like a matrix,
 * from (0, 0) to (size - 1, size - 1),</li>
 * <li>"array" coordinates converted from real-world, from (0) to (size - 1).
 * </li></ul>
 */
public class Coordinates {

  /** board size. */
  private int size;

  /** x-axis coordinate. */
  private String xWorld;

  /** y-axis coordinate. */
  private int yWorld;

  /** x-axis coordinate. */
  private int xMatrix;

  /** y-axis coordinate. */
  private int yMatrix;


  /** Pattern to match coordinates. */
  private static final Pattern PATTERN = Pattern.compile(
      "^([a-z]+)(\\d+)$");

  /** Pattern to match only x-axis coordinate. */
  private static final Pattern PATTERN_X = Pattern.compile("^[a-z]+$");


  /**
   * Duplicates a Coordinates object.
   *
   * @param c the Coordinates to duplicate.
   */
  public Coordinates(final Coordinates c) {
    this.size = c.size;
    this.xWorld = new String(c.xWorld);
    this.yWorld = c.yWorld;
    this.xMatrix = c.xMatrix;
    this.yMatrix = c.yMatrix;
  }

  /**
   * Contructs a coord object from matrix coordinates.
   *
   * @param x x-axis coordinate.
   * @param y y-axis coordinate.
   * @param size size of the board.
   * @throws com.vampirehemophile.ghosts.exceptions.OutOfBoardCoordinatesException
   *     if the coordinates are outside of the board.
   * @throws com.vampirehemophile.ghosts.exceptions.BoardTooSmallException if
   *     the size is inferior to {@link com.vampirehemophile.ghosts.entities.Board#DEFAULT_BOARD_SIZE}.
   */
  public Coordinates(final int x, final int y, final int size) {
    testMinimumSize(size);

    if (x < 0 || size <= x || y < 0 || size <= y) {
      throw new OutOfBoardCoordinatesException(x, y);
    }

    this.xMatrix = x;
    this.yMatrix = y;
    this.size = size;
    genXWorld();
    genYWorld();
  }

  /**
   * Constructs a coord object from real-world coordinates.
   *
   * @param xy contains the coordinates (ex: "a1", "b9", "ab26").
   * @param size size of the board.
   * @throws com.vampirehemophile.ghosts.exceptions.InvalidCoordinatesException
   *     if the coordinates are invalid.
   * @throws com.vampirehemophile.ghosts.exceptions.OutOfBoardCoordinatesException
   *     if the coordnates are outside f the board.
   * @throws com.vampirehemophile.ghosts.exceptions.BoardTooSmallException if
   *     the size is inferior to {@link com.vampirehemophile.ghosts.entities.Board#DEFAULT_BOARD_SIZE}.
   */
  public Coordinates(final String xy, final int size) {
    testMinimumSize(size);

    Matcher matcher = PATTERN.matcher(xy);
    if (!matcher.matches()) {
      throw new InvalidCoordinatesException(xy);
    }

    this.xWorld = matcher.group(1);
    this.yWorld = Integer.parseInt(matcher.group(2));
    this.size = size;
    genXMatrix();
    genYMatrix();

    if (!isIn()) {
      throw new OutOfBoardCoordinatesException(xy);
    }
  }

  /**
   * Constructs a coord object from real-world coordinates.
   *
   * @param x coordinate on x-axis.
   * @param y coordinate on y-axis.
   * @param size size of the board.
   * @throws com.vampirehemophile.ghosts.exceptions.InvalidCoordinatesException
   *     if the coordinates are invalid.
   * @throws com.vampirehemophile.ghosts.exceptions.OutOfBoardCoordinatesException
   *     if the coordnates are outside of the board.
   * @throws com.vampirehemophile.ghosts.exceptions.BoardTooSmallException if
   *     the size is inferior to {@link com.vampirehemophile.ghosts.entities.Board#DEFAULT_BOARD_SIZE}.
   */
  public Coordinates(final String x, final int y, final int size) {
    testMinimumSize(size);

    Matcher xMatcher = PATTERN_X.matcher(x);
    if (!xMatcher.matches()) {
      throw new InvalidCoordinatesException(x, y);
    }

    this.xWorld = xMatcher.group(0);
    this.yWorld = y;
    this.size = size;
    genXMatrix();
    genYMatrix();

    if (!isIn()) {
      throw new OutOfBoardCoordinatesException(x, y);
    }
  }

  /**
   * Tests minimum size requirements.
   *
   * @param size size of the board.
   * @throws com.vampirehemophile.ghosts.exceptions.BoardTooSmallException if
   *     the size is inferior to {@link com.vampirehemophile.ghosts.entities.Board#DEFAULT_BOARD_SIZE}.
   */
  private void testMinimumSize(final int size) {
    if (size < DEFAULT_BOARD_SIZE) {
      throw new BoardTooSmallException(size);
    }
  }

  /**
   * Creates x-axis world coordinate from matrix coordinates.
   */
  private void genXWorld() {
    xWorld = new String();
    int x = xMatrix + 1;
    int digit = 0;
    while (x > 0) {
      digit = x % 26;
      x /= 26;
      xWorld = (char) (digit + 'a' - 1) + xWorld;
    }
  }

  /**
   * Creates y-axis world coordinate from matrix coordinates.
   */
  private void genYWorld() {
    yWorld = yMatrix + 1;
  }

  /**
   * Creates x-axis matrix coordinate from world coordinates.
  */
  private void genXMatrix() {
    xMatrix = 0;
    for (int i = 0; i < xWorld.length(); i++) {
      xMatrix += xWorld.charAt(i) - 'a' + i * 26;
    }
  }

  /**
   * Creates y-axis matrix coordinate from world coordinates.
  */
  private void genYMatrix() {
    yMatrix = yWorld - 1;
  }

  /**
   * Returns true if the two coordinates point the same location.
   *
   * @param coord second coordinate.
   * @return true if they points the same location.
   */
  public boolean equals(final Coordinates coord) {
    return coord.xMatrix == this.xMatrix && coord.yMatrix == this.yMatrix;
  }

  /**
   * Checks if this is in its board.
   *
   * @return true if this is in.
   */
  private boolean isIn() {
    return 0 <= xMatrix && xMatrix < size
        && 0 <= yMatrix && yMatrix < size;
  }

  /**
   * Returns the north Coordinates from this.
   *
   * @return the coordinate or null if this is in the edge.
   */
  public Coordinates north() {
    Coordinates c = new Coordinates(this);
    c.moveNorth();
    if (!c.equals(this)) {
      return c;
    } else {
      return null;
    }
  }

  /**
   * Returns the south Coordinates from this.
   *
   * @return the coordinate or null if this is in the edge.
   */
  public Coordinates south() {
    Coordinates c = new Coordinates(this);
    c.moveSouth();
    if (!c.equals(this)) {
      return c;
    } else {
      return null;
    }
  }

  /**
   * Returns the east Coordinates from this.
   *
   * @return the coordinate or null if this is in the edge.
   */
  public Coordinates east() {
    Coordinates c = new Coordinates(this);
    c.moveEast();
    if (!c.equals(this)) {
      return c;
    } else {
      return null;
    }
  }

  /**
   * Returns the west Coordinates from this.
   *
   * @return the coordinate or null if this is in the edge.
   */
  public Coordinates west() {
    Coordinates c = new Coordinates(this);
    c.moveWest();
    if (!c.equals(this)) {
      return c;
    } else {
      return null;
    }
  }

  /**
   * Returns the north-east Coordinates.
   *
   * @return the coordinate or null if this is in the edge.
   */
  public Coordinates northEast() {
    Coordinates c = new Coordinates(this);
    c.moveNorthEast();
    if (!c.equals(this)) {
      return c;
    } else {
      return null;
    }
  }

  /**
   * Returns the south-west Coordinates from this.
   *
   * @return the coordinate or null if this is in the edge.
   */
  public Coordinates northWest() {
    Coordinates c = new Coordinates(this);
    c.moveNorthWest();
    if (!c.equals(this)) {
      return c;
    } else {
      return null;
    }
  }

  /**
   * Returns the south-east Coordinates from this.
   *
   * @return the coordinate or null if this is in the edge.
   */
  public Coordinates southEast() {
    Coordinates c = new Coordinates(this);
    c.moveSouthEast();
    if (!c.equals(this)) {
      return c;
    } else {
      return null;
    }
  }

  /**
   * Returns the south-west Coordinates from this.
   *
   * @return the coordinate or null if this is in the edge.
   */
  public Coordinates southWest() {
    Coordinates c = new Coordinates(this);
    c.moveSouthWest();
    if (!c.equals(this)) {
      return c;
    } else {
      return null;
    }
  }

  /**
   * Tests if this can move north.
   *
   * @return true if this can move north.
   */
  public boolean canMoveNorth() {
    return yMatrix > 0;
  }

  /**
   * Tests if this can move south.
   *
   * @return true if this can move south.
   */
  public boolean canMoveSouth() {
    return yMatrix < size - 1;
  }

  /**
   * Tests if this can move east.
   *
   * @return true if this can move east.
   */
  public boolean canMoveEast() {
    return xMatrix < size - 1;
  }

  /**
   * Tests if this can move west.
   *
   * @return true if this can move west.
   */
  public boolean canMoveWest() {
    return xMatrix > 0;
  }

  /**
   * Tests if this can move north-east.
   *
   * @return true if this can move north-east.
   */
  public boolean canMoveNorthEast() {
    return canMoveNorth() && canMoveEast();
  }

  /**
   * Tests if this can move north-west.
   *
   * @return true if this can move north-west.
   */
  public boolean canMoveNorthWest() {
    return canMoveNorth() && canMoveNorthWest();
  }

  /**
   * Tests if this can move south-east.
   *
   * @return true if this can move south-east.
   */
  public boolean canMoveSouthEast() {
    return canMoveSouth() && canMoveEast();
  }

  /**
   * Tests if this can move south-west.
   *
   * @return true if this can move south-west.
   */
  public boolean canMoveSouthWest() {
    return canMoveSouth() && canMoveWest();
  }

  /**
   * Move this north. Won't move if this can't.
   */
  private void moveNorth() {
    if (!canMoveNorth()) {
      return;
    }
    yMatrix--;
    genYWorld();
  }

  /**
   * Move this south. Won't move if this can't.
   */
  private void moveSouth() {
    if (!canMoveSouth()) {
      return;
    }
    yMatrix++;
    genYWorld();
  }

  /**
   * Move this east. Won't move if this can't.
   */
  private void moveEast() {
    if (!canMoveEast()) {
      return;
     }
    xMatrix++;
    genXWorld();
  }

  /**
   * Move this west. Won't move if this can't.
   */
  private void moveWest() {
    if (!canMoveWest()) {
      return;
    }
    xMatrix--;
    genXWorld();
  }

  /**
   * Move this nort-east. Won't move if this can't.
   */
  private void moveNorthEast() {
    if (!(canMoveNorth() && canMoveEast())) {
      return;
    }
    moveNorth();
    moveEast();
  }

  /**
   * Move this nort-west. Won't move if this can't.
   */
  private void moveNorthWest() {
    if (!(canMoveNorth() && canMoveWest())) {
      return;
    }
    moveNorth();
    moveWest();
  }

  /**
   * Move this south-east. Won't move if this can't.
   */
  private void moveSouthEast() {
    if (!(canMoveSouth() && canMoveEast())) {
      return;
    }
    moveSouth();
    moveEast();
  }

  /**
   * Move this south-west. Won't move if this can't.
   */
  private void moveSouthWest() {
    if (!(canMoveNorth() && canMoveWest())) {
      return;
    }
    moveSouth();
    moveWest();
  }

  /**
   * Gets the world x-axis coordinate.
   *
   * @return the coordinate.
   */
  public String x() {
    return xWorld;
  }

  /**
   * Gets the world y-axis coordinate.
   *
   * @return the coordinate.
   */
  public int y() {
    return yWorld;
  }

  /**
   * Gets the matrix x-axis coordinate.
   *
   * @return the coordinate.
   */
  public int xMatrix() {
    return xMatrix;
  }

  /**
   * Gets the matrix y-axis coordinate.
   *
   * @return the coordinate.
   */
  public int yMatrix() {
    return yMatrix;
  }

  /**
   * Builds a String description of this, of this format:
   * <code>xWorld:yWorld xMatrix:yMatrix</code>.
   *
   * @return String description.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(xWorld);
    sb.append(":");
    sb.append(yWorld);
    sb.append(" ");
    sb.append(xMatrix);
    sb.append(":");
    sb.append(yMatrix);
    return sb.toString();
  }
}
