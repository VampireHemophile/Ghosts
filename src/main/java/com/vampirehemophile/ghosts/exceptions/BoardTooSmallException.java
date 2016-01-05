package com.vampirehemophile.ghosts.exceptions;

import static com.vampirehemophile.ghosts.entities.Board.DEFAULT_BOARD_SIZE;


/**
 * Thrown when the board size is inferior to
 * {@link com.vampirehemophile.ghosts.entities.Board#DEFAULT_BOARD_SIZE}.
 */
@SuppressWarnings("serial")
public class BoardTooSmallException extends RuntimeException {

  /**
   * Constructor for BoardTooSmallException.
   *
   * @param size size of the board.
   */
  public BoardTooSmallException(final int size) {
    super("Board size was " + size + " but board minimum size allowed is "
        + DEFAULT_BOARD_SIZE + ".");
  }
}
