package com.vampirehemophile.ghosts.entities;

import com.vampirehemophile.ghosts.math.Coordinates;
import com.vampirehemophile.ghosts.exceptions.BoardTooSmallException;
import java.util.ArrayList;

/**
 * Provides a board to play on.
 */
public class Board {
  /**
   * Represents the board as a square matrix in an array.
   */
  private Square[] board;

  /**
   * Length of one side of the board (it's a square matrix).
   */
  private final int size;

  /** White player. */
  private Player white;

  /** Black player. */
  private Player black;


  /**
   * Constructs a new size x size square Board.
   *
   * @param white white player [b1 x e2] minimum.
   * @param black black player [b5 x e6] minimum.
   * @param size size of one side of the board.
   * @throws NullPointerException if one or both of the players are null.
   * @throws RuntimeException if players represents the same instance.
   * @throws BoardTooSmallException if the size is inferior to 6.
   */
  public Board(Player white, Player black, int size) {
    if (white == null || black == null) {
      throw new NullPointerException();
    } else if (white == black) {
      throw new RuntimeException("Players represents the same instance.");
    } else if (size < 6) {
      throw new BoardTooSmallException(size);
    }
    this.white = white;
    this.black = black;

    this.size = size;
    this.board = new Square[size * size];
    Coordinates c = null;
    for (int i = 0; i < size * size; i++) {
      c = new Coordinates(i, size);
      board[c.index()] = new Square(c);
    }
  }

  /**
   * Returns the square located at coordinates.
   *
   * @param c the location.
   * @return the square.
   */
  private Square squareAt(Coordinates c) {
    return board[c.index()];
  }

  /**
   * Returns the pawn located at coordinates.
   *
   * @param c the location.
   * @return the pawn.
   */
  public Pawn at(Coordinates c) {
    return squareAt(c).pawn();
  }

  /**
   * Sets a pawn at coordinates. Won't check if the square was occupied.
   *
   * @param pawn the pawn.
   * @param loc the location.
   * @return the previous pawn on the square, or null if this was free.
   */
  public Pawn set(Pawn pawn, Coordinates loc) {
    return squareAt(loc).setPawn(pawn);
  }

  /**
   * Removes a pawn from square at coordinates.
   *
   * @param c the location.
   */
  private Pawn remove(Coordinates c) {
    return squareAt(c).free();
  }

  /**
   * Tests if a pawn can move to a new location.
   * A pawn can move to another location if it is within the board,
   * in its movement range, if the location is free or if it is occupied by
   * a pawn of the other player.
   *
   * @param pawn the pawn.
   * @param loc the new location.
   * @return true if the pawn can be moved to the new location.
   */
  public boolean canMoveTo(Pawn pawn, Coordinates loc) {
    boolean isInRange = false;
    for (Coordinates mv : pawn.range(loc)) {
      if (mv.equals(loc)) {
        isInRange = true;
        break;
      }
    }

    return isInRange
        && (squareAt(loc).isFree()
          || (squareAt(loc).isOccupied()
            && squareAt(loc).pawn().player() != pawn.player()
            && pawn instanceof EvilPawn));
  }

  /**
   * Moves a pawn to a new location, eventually removing an opponent's pawn.
   *
   * @param pawn the pawn.
   * @param loc the new location.
   * @return the opponent's pawn that may have been taken, or null.
   */
  public Pawn moveTo(Pawn pawn, Coordinates loc) {
    if (!canMoveTo(pawn, loc)) {
      return null;
    }

    return set(pawn, loc);
  }

  /**
   * Check if a player can exit one of its pawns, and thus win the game.
   *
   * @param player the player.
   * @return true if the player can exit one of his pawns.
   * @throws NullPointerException if player is null.
   */
  public boolean exitsPawn(Player player) {
    if (player == null) {
      throw new NullPointerException();
    }

    Coordinates topLeft = new Coordinates(0, 0, size);
    if (squareAt(topLeft).isOccupied()) {
      return player == squareAt(topLeft).pawn().player() && player == black;
    }
    Coordinates topRight = new Coordinates(size - 1, 0, size);
    if (squareAt(topLeft).isOccupied()) {
      return player == squareAt(topLeft).pawn().player() && player == black;
    }
    Coordinates bottomLeft = new Coordinates(0, size - 1, size);
    if (squareAt(topLeft).isOccupied()) {
      return player == squareAt(topLeft).pawn().player() && player == white;
    }
    Coordinates bottomRight = new Coordinates(size - 1, size - 1, size);
    if (squareAt(topLeft).isOccupied()) {
      return player == squareAt(topLeft).pawn().player() && player == white;
    }

    return false;
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
