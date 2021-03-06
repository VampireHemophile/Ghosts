package com.vampirehemophile.ghosts.entities;


/**
 * A Square in the {@link Board Board}.
 */
public class Square {

  /**
   * The pawn on this square. If null, it means that the board is empty.
   */
  private Pawn pawn;


  /**
   * Constructs a new Square.
   *
   */
  public Square() {
    this.pawn = null;
  }

  /**
   * Checks if this there is no pawn on the square.
   *
   * @return true if this square is free.
   */
  public boolean isFree() {
    return pawn == null;
  }

  /**
   * Checks if this there is a pawn on the square.
   *
   * @return true if this square is occupied.
   */
  public boolean isOccupied() {
    return pawn != null;
  }


  /**
   * Frees the square.
   *
   * @return the pawn that was on this square, or null if this was free.
   */
  public Pawn free() {
    Pawn p = pawn;
    pawn = null;
    return p;
  }

  /**
   * Sets this square's pawn.
   *
   * @param pawn new pawn.
   * @throws java.lang.NullPointerException if pawn is null.
   * @return the previous pawn on the square, or null if this was free.
   */
  public Pawn setPawn(final Pawn pawn) {
    if (pawn == null) {
      throw new NullPointerException();
    }
    Pawn p = free();
    this.pawn = pawn;
    return p;
  }

  /**
   * Gets this square's pawn.
   *
   * @return the pawn.
   */
  public Pawn pawn() {
    return this.pawn;
  }
}
