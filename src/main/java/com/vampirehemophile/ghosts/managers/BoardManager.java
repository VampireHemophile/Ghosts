package com.vampirehemophile.ghosts.managers;

import com.vampirehemophile.ghosts.math.Coordinates;
import com.vampirehemophile.ghosts.entities.Board;
import com.vampirehemophile.ghosts.entities.Player;
import com.vampirehemophile.ghosts.entities.Pawn;
import com.vampirehemophile.ghosts.entities.GoodPawn;
import com.vampirehemophile.ghosts.entities.Square;
import com.vampirehemophile.ghosts.exceptions.BoardTooSmallException;

/**
 * Provides a Manager to handle a board, the pawns movements functions on it,
 * and a set of functions to analyse the game's state.
 */
public class BoardManager {

  /** Board's size. */
  private int size;

  /** Game board. */
  private Board board;

  /** White player. */
  private Player white;

  /** Black player. */
  private Player black;

  public BoardManager(Player white, Player black) {
    this(white, black, 6);
  }

  /**
   * Constructs a new BoardManager.
   *
   * @param white white player [b1 x e2] minimum.
   * @param black black player [b5 x e6] minimum.
   * @throws NullPointerException if one or both of the players are null.
   * @throws RuntimeException if players represents the same instance.
   * @throws BoardTooSmallException if the size is inferior to 6.
   */
  public BoardManager(Player white, Player black, int size) {
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
    this.board = new Board(size);
  }


  /**
   * Tests if a pawn can move to a new location.
   * A pawn can move to another location if it is within the board,
   * in its movement range, if the location is free or if it is occupied by
   * a pawn of the other player and the pawn is aggressive.
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

    Square square = board.squareAt(loc);
    return isInRange && (square.isFree() || (square.isOccupied()
        && pawn.player() != pawn.player() && pawn.isAggressive()));
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

    return board.set(pawn, loc);
  }

  /**
  * Check if a player can exit one of its pawns, and thus win the game.
  * A player can exit one of its pawns if the pawn is an instance of
  * @{link GoodPawn} and if the pawn is located on the opponent's side corner of
  * the board.
  *
  * @param player the player.
  * @return true if the player can exit one of his pawns.
  * @throws NullPointerException if player is null.
  */
  public boolean exitsPawn(Player player) {
    if (player == null) {
      throw new NullPointerException();
    }

    Square square = null;
    Pawn pawn = null;

    Coordinates topLeft = new Coordinates(0, 0, size);
    square = board.squareAt(topLeft);
    if (square.isOccupied()) {
      pawn = square.pawn();
      return pawn instanceof GoodPawn && player == pawn.player()
          && player == black;
    }
    Coordinates topRight = new Coordinates(size - 1, 0, size);
    square = board.squareAt(topRight);
    if (square.isOccupied()) {
      pawn = square.pawn();
      return pawn instanceof GoodPawn && player == pawn.player()
          && player == black;
    }
    Coordinates bottomLeft = new Coordinates(0, size - 1, size);
    square = board.squareAt(bottomLeft);
    if (square.isOccupied()) {
      pawn = square.pawn();
      return pawn instanceof GoodPawn && player == pawn.player()
          && player == white;
    }
    Coordinates bottomRight = new Coordinates(size - 1, size - 1, size);
    square = board.squareAt(bottomRight);
    if (square.isOccupied()) {
      pawn = square.pawn();
      return pawn instanceof GoodPawn && player == pawn.player()
          && player == white;
    }

    return false;
  }

  /**
   * Gets the Manager's board.
   *
   * @return the board.
   */
  public Board board() {
    return board;
  }

  /**
   * Gets the board size.
   *
   * @return board size.
   */
  public int size() {
    return size;
  }
}
