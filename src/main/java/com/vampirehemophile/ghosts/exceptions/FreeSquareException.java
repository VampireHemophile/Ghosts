package com.vampirehemophile.ghosts.exceptions;

import com.vampirehemophile.ghosts.math.Coordinates;


/**
 * Thrown when trying to move a pawn from an empty square.
 */
@SuppressWarnings("serial")
public class FreeSquareException extends RuntimeException {

  /**
   * Constructor for FreeSquareException.
   *
   * @param c a {@link com.vampirehemophile.ghosts.math.Coordinates} object.
   */
  public FreeSquareException(final Coordinates c) {
    super("Trying to move pawn from empty square at " + c.x() + c.y());
  }
}
