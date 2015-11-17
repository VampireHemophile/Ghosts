package com.vampirehemophile.ghosts.entities;

import java.util.Set;
import java.util.HashSet;

/**
 * Represents a player.
 */
public class Player {

  private Set<Pawn> pawns;
  private char charIcon;

  public Player(char charIcon) {
    this.pawns = new HashSet<>();
    this.charIcon = charIcon;
  }

  public char charIcon() {
    return charIcon;
  }
  
}
