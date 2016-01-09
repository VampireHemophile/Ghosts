package com.vampirehemophile.ghosts.entities;

import com.vampirehemophile.ghosts.exceptions.BoardTooSmallException;
import com.vampirehemophile.ghosts.math.Coordinates;


/**
 * Provides a board to play on.
 */
public class Board {

  /**
   * Represents the board as a square matrix in an array.
   */
  private Square[][] board;

  /**
   * Length of one side of the board (it's a square matrix).
   */
  private final int size;

  /** Default board size. */
  public static final int DEFAULT_BOARD_SIZE = 6;


  /**
   * Constructs a new 6 x 6 square Board.
   */
  public Board() {
    this(DEFAULT_BOARD_SIZE);
  }

  /**
   * Constructs a new size x size square Board.
   *
   * @param size size of one side of the board.
   * @throws com.vampirehemophile.ghosts.exceptions.BoardTooSmallException if
   *     the size is inferior to 6.
   */
  public Board(final int size) {
    this.size = size;
    if (size < DEFAULT_BOARD_SIZE) {
      throw new BoardTooSmallException(size);
    }

    this.board = new Square[size][size];
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        board[x][y] = new Square();
      }
    }
  }

  /**
   * Returns the square located at coordinates.
   *
   * @param c the location.
   * @return the square.
   */
  public Square squareAt(final Coordinates c) {
    return board[c.xMatrix()][c.yMatrix()];
  }

  /**
   * Returns the pawn located at coordinates.
   *
   * @param c the location.
   * @return the pawn.
   */
  public Pawn at(final Coordinates c) {
    return squareAt(c).pawn();
  }

  /**
   * Sets a pawn at coordinates. Won't check if the square was occupied.
   *
   * @param pawn the pawn.
   * @param loc the location.
   * @return the previous pawn on the square, or null if this was free.
   */
  public Pawn set(final Pawn pawn, final Coordinates loc) {
    return squareAt(loc).setPawn(pawn);
  }

  /**
   * Removes a pawn from square at coordinates.
   *
   * @param c the location.
   * @return the removed pawn.
   */
  public Pawn remove(final Coordinates c) {
    return squareAt(c).free();
  }

  /**
   * Gets the size of the board.
   *
   * @return the size.
   */
  public int size() {
    return size;
  }
}
