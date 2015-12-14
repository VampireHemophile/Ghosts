package com.vampirehemophile.ghosts.entities;

import java.util.function.Consumer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;

/**
 * Represents a player.
 */
public class Player implements Iterable<Pawn> {

  /** Set of player's pawns. */
  private Set<Pawn> pawns;

  /** Set of captured pawns. */
  private Set<Pawn> capturedPawns;

  /** Number of good pawns. */
  private int goodPawnsNumber;

  /** Number of evil pawns. */
  private int evilPawnsNumber;


  /**
   * Constructs a new player.
   * Player has 8 pawns by default, 4 good pawns and evil pawns.
   *
   */
  public Player() {
    this.pawns = new HashSet<>();
    this.capturedPawns = new HashSet<>();
    this.goodPawnsNumber = 0;
    this.evilPawnsNumber = 0;
  }

  /**
   * Add a pawn to this player's pawn set.
   *
   * @param p pawn to add.
   * @return true if the pawn set contained the specified pawn.
   */
  public boolean add(Pawn p) {
    if (p == null) {
      throw new NullPointerException();
    }
    p.setPlayer(this);

    if (p.isGood()) {
      goodPawnsNumber++;
    } else if (p.isEvil()) {
      evilPawnsNumber++;
    }
    return pawns.add(p);
  }

  /**
   * Capture an opponent's pawn. Will remove the opponent's reference to the
   * pawn.
   *
   * @param p captured pawn.
   */
  public void capture(Pawn p) {
    if (p == null) {
      throw new NullPointerException();
    }
    capturedPawns.add(p);
    p.player().remove(p);
  }

  /**
   * Removes a pawn from this player's pawn set.
   *
   * @param p pawn to remove.
   * @return true if the pawn set contained the specified pawn.
   */
  public boolean remove(Pawn p) {
    if (p == null) {
      throw new NullPointerException();
    }

    if (p.isGood()) {
      goodPawnsNumber--;
    } else if (p.isEvil()) {
      evilPawnsNumber--;
    }
    return pawns.remove(p);
  }

  /**
   * Gets the number of good pawns this player has.
   *
   * @return number of good pawns.
   */
  public int countGoodPawns() {
    return goodPawnsNumber;
  }

  /**
   * Gets the number of evil pawns this player has.
   *
   * @return number of evil pawns.
   */
  public int countEvilPawns() {
    return evilPawnsNumber;
  }

  /** {@inheritDoc} */
  @Override public void forEach(Consumer<? super Pawn> action) {
    pawns.forEach(action);
  }

  /** {@inheritDoc} */
  @Override public Iterator<Pawn> iterator() {
    return pawns.iterator();
  }

  /** {@inheritDoc} */
  @Override public Spliterator<Pawn> spliterator() {
    return pawns.spliterator();
  }
}
