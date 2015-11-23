package com.vampirehemophile.ghosts.managers;

import com.vampirehemophile.ghosts.entities.Board;
import com.vampirehemophile.ghosts.entities.Player;
import com.vampirehemophile.ghosts.entities.Pawn;
import com.vampirehemophile.ghosts.entities.Square;
import com.vampirehemophile.ghosts.exceptions.BoardTooSmallException;
import com.vampirehemophile.ghosts.exceptions.FreeSquareException;
import com.vampirehemophile.ghosts.math.Coordinates;

/**
 * Provides a manager to handle a board, the pawns movements functions on it,
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

  /**
   * Constructs a new BoardManager where the board size is 6.
   *
   * @param white white player [b1 x e2] minimum.
   * @param black black player [b5 x e6] minimum.
   * @throws NullPointerException if one or both of the players are null.
   * @throws RuntimeException if players represents the same instance.
   * @throws BoardTooSmallException if the size is inferior to 6.
   */
  public BoardManager(Player white, Player black) {
    this(white, black, 6);
  }

  /**
   * Constructs a new BoardManager.
   *
   * @param white white player [b1 x e2] minimum.
   * @param black black player [b5 x e6] minimum.
   * @param size size of the board.
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
   * Gets the player's opponent.
   *
   * @param player the player.
   * @return its opponent.
   */
  public Player opponent(Player player) {
    return player == white ? black : white;
  }

  /**
   * Tests if a pawn can move to a new location.
   * A pawn can move to another location if it is within the board,
   * in its movement range, if the location is free or if it is occupied by
   * a pawn of the other player and the pawn is aggressive.
   *
   * @param start coordinates where the pawn is located.
   * @param end the new location.
   * @return true if the pawn can be moved to the new location, false else.
   * @throws FreeSquareException if there is no pawn at the specified location.
  */
  public boolean canMove(Coordinates start, Coordinates end) {
    if (start.equals(end)) {
      throw new RuntimeException("Coordinates match the same location.");
    }

    Pawn pawn = board.at(start);
    if (pawn == null) {
      throw new FreeSquareException(start);
    }

    boolean isInRange = false;
    for (Coordinates mv : pawn.range(start)) {
      if (mv.equals(end)) {
        isInRange = true;
        break;
      }
    }

    Square square = board.squareAt(end);
    return isInRange && (square.isFree() || (square.isOccupied()
        && !pawn.player().equals(square.pawn().player())
        && pawn.isAggressive()));
  }

  /**
  * Moves a pawn to a new location, eventually capturing one of the opponent's
  * pawns.
  * User must test {@link canMove} before.
  *
  * @param start coordinates where the pawn is located.
  * @param end the new location.
  * @return the opponent's pawn that may have been taken, or null.
  */
  public Pawn move(Coordinates start, Coordinates end) {
    if (start == null || end == null) {
      throw new NullPointerException();
    }
    if (start.equals(end)) {
      throw new RuntimeException("Coordinates match the same location.");
    }

    Pawn pawn = board.remove(start);
    Pawn captured = board.set(pawn, end);
    if (captured != null) {
      pawn.player().capture(captured);
    }

    return captured;
  }

  /**
  * Check if a player can exit one of its pawns, and thus win the game.
  * A player can exit one of its pawns if the pawn is a good pawn, and if the
  * pawn is located on the opponent's side corner of the board.
  *
  * @param player the player.
  * @return true if the player can exit one of his pawns.
  * @throws NullPointerException if player is null.
  */
  public boolean canExitPawn(Player player) {
    if (player == null) {
      throw new NullPointerException();
    }

    Square square = null;
    Pawn pawn = null;

    Coordinates topLeft = new Coordinates(0, 0, size);
    square = board.squareAt(topLeft);
    if (square.isOccupied()) {
      pawn = square.pawn();
      if (pawn.isGood() && player == pawn.player() && player == black) {
        return true;
      }
    }
    Coordinates topRight = new Coordinates(size - 1, 0, size);
    square = board.squareAt(topRight);
    if (square.isOccupied()) {
      pawn = square.pawn();
      if (pawn.isGood() && player == pawn.player() && player == black) {
        return true;
      }
    }
    Coordinates bottomLeft = new Coordinates(0, size - 1, size);
    square = board.squareAt(bottomLeft);
    if (square.isOccupied()) {
      pawn = square.pawn();
      if (pawn.isGood() && player == pawn.player() && player == white) {
        return true;
      }
    }
    Coordinates bottomRight = new Coordinates(size - 1, size - 1, size);
    square = board.squareAt(bottomRight);
    if (square.isOccupied()) {
      pawn = square.pawn();
      if (pawn.isGood() && player == pawn.player() && player == white) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if a player has won. The check should happen at the end of the
   * player's turn.
   * A player has won if it one of its pawns has reached the other side of the
   * board {@link canExitPawn} or if all of the opponent's good pawns have been
   * captured.
   *
   * @param player potential winner ?
   * @return true if the player has won.
   */
  public boolean hasWon(Player player) {
    return canExitPawn(player) || opponent(player).countGoodPawns() == 0;
  }

  /**
   * Check if a player has lost. The check should happen at the end of the
   * player's turn.
   * A player has lost if is has captured all of the opponent's evil pawns.
   *
   * @param player potential looser ?
   * @return true if the player has lost.
   */
  public boolean hasLost(Player player) {
    return opponent(player).countEvilPawns() == 0;
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
