package com.vampirehemophile.ghosts.entities;

import java.util.Set;
import java.util.HashSet;

import com.vampirehemophile.ghosts.math.Coordinates;

/**
 * Abstract class for pawns.
 */
public abstract class Pawn {

  /** The pawn's player. */
  private Player player;

  /** Default char icon in CLI for an hidden pawn. */
  public final static char defaultCharIcon = 'A';

  /**
   * Pawns's behaviour. Aggressive means that it can take an
   * opponent's pawn.
   */
  private boolean aggressive;

  /** Constructs a new Pawn. */
  public Pawn() {
    this(null);
  }

  /**
   * Constructs a new Pawn. Adds this pawn to the player pawn set.
   *
   * @param player Pawn's player.
   */
  public Pawn(Player player) {
    this.player = player;
    if (player != null) {
      player.add(this);
    }
    this.aggressive = true;
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
   * Sets the pawn's player. Won't add this pawn to the player's pawn set.
   *
   * @param player pawn's player.
   */
  public void setPlayer(Player player) {
    this.player = player;
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
    Set<Coordinates> set = new HashSet<>();
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
   * Tells if a pawn is aggressive or not. Aggressive means that it can take an
   * opponent's pawn.
   *
   * @return true if it is aggressive (default).
   */
  public boolean isAggressive() {
    return aggressive;
  }

  /**
   * Set pawn's behaviour. Aggressive means that it can take an opponent's pawn.
   *
   * @param aggressive Pawn's behaviour.
   */
  public void setAggressive(boolean aggressive) {
    this.aggressive = aggressive;
  }
}
