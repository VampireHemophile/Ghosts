package com.vampirehemophile.ghosts.entities;

import java.util.Set;
import java.util.HashSet;
import java.util.Spliterator;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Represents a player.
 */
public class Player implements Iterable<Pawn> {
  /**
   * Set of player's pawns.
   */
  private Set<Pawn> pawns;

  /** Player's char for CLI. */
  private char charIcon;


  /**
   * Constructs a new player.
   */
  public Player(char charIcon) {
    this.pawns = new HashSet<>();
    this.charIcon = charIcon;
  }

  /**
   * Gets the player's char.
   *
   * @return player's char.
   */
  public char charIcon() {
    return charIcon;
  }

  /**
   * Add a pawn to the player's pawn set.
   *
   * @param p pawn to add.
   */
  public void add(Pawn p) {
    if (p == null) {
      throw new NullPointerException();
    }
    pawns.add(p);
  }

  /**
   * Remove a pawn from the player's pawn set.
   *
   * @param p pawn to remove.
   * @return true if the pawn set contained the specified pawn.
   */
  public boolean remove(Pawn p) {
    if (p == null) {
      throw new NullPointerException();
    }
    return pawns.remove(p);
  }

  @Override public void forEach(Consumer<? super Pawn> action) {
    pawns.forEach(action);
  }

  @Override public Iterator<Pawn> iterator() {
    return pawns.iterator();
  }

  @Override public Spliterator<Pawn> spliterator() {
    return pawns.spliterator();
  }
}
