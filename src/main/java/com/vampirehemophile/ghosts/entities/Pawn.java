package com.vampirehemophile.ghosts.entities;

import java.util.Set;
import java.util.HashSet;

import com.vampirehemophile.ghosts.math.Coordinates;

/**
 * Abstract class for pawns.
 */
public abstract class Pawn {

  /** The pawn's player. */
  protected Player player;

  /** Default char icon in CLI for an hidden pawn. */
  public final static char defaultCharIcon = 'A';

  /** Constructs a new Pawn. */
  public Pawn() {
    this.player = null;
  }

  /**
   * Constructs a new Pawn. Adds itself to the player's pawn set.
   *
   * @param player Pawn's player.
   * @throws NullPointerException if player is null.
   */
  public Pawn(Player player) {
    if (player == null) {
      throw new NullPointerException();
    }
    this.player = player;
    player.add(this);
  }

  /**
   * Gets the pawn's player.
   *
   * @return pawn's player.
   */
  public Player player() {
    return player;
  }

  /**
   * Gets the pawn's char icon for CLI printing.
   *
   * @return pawn's char icon.
   */
  public abstract char charIcon();

  /**
   * Gets the pawn's movement range.
   *
   * @param coord the starting location
   * @return Set of all possible movements. If the set is empty, no movements
   * are possible.
   */
  public Set<Coordinates> range(Coordinates coord) {
    Set<Coordinates> set = new HashSet<>(4, 0.0f);
    Coordinates north = coord.north();
    Coordinates south = coord.south();
    Coordinates east = coord.east();
    Coordinates west = coord.west();

    if (north != null) {
      set.add(north);
    }
    if (south != null) {
      set.add(south);
    }
    if (east != null) {
      set.add(east);
    }
    if (west != null) {
      set.add(west);
    }

    return set;
  }

  /**
   * Tells if a pawn is aggressive or not. Aggressive means that it can take
   * the opponents pawns.
   *
   * @return true if it is aggressive (default).
   */
  public boolean isAggressive() {
    return true;
  }
}
