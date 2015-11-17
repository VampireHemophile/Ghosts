package com.vampirehemophile.ghosts.exceptions;

/**
 * Thrown when the board size is inferior to 6.
 */
@SuppressWarnings("serial")
public class BoardTooSmallException extends RuntimeException {
  public BoardTooSmallException(int size) {
    super("Board size was " + size + " but board minimum size allowed is 6.");
  }
}
