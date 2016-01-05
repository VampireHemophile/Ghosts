package com.vampirehemophile.ghosts.entities;

import java.util.Set;
import java.util.HashSet;

import com.vampirehemophile.ghosts.math.Coordinates;


/**
 * Pawns.
 */
public class Pawn {

  /** Pawn's state. */
  public enum PawnType {

    /**
     * Allows the user to win by exiting this pawn. A player wins if it captures
     * all of its opponent's pawns.
     */
    GOOD,

    /** A player looses if it captures all of its opponent's pawns. */
    EVIL,

    /** An unknow type of pawn. */
    UNKNOWN
  }


  /** The pawn's player. */
  private Player player;

  /**
   * Pawns's behaviour. Aggressive means that it can take an opponent's pawn.
   */
  private boolean aggressive;

  /** Pawn's type. */
  private PawnType type;


  /**
   * Constructs a new aggressive pawn.
   *
   * @param type this pawn's type.
   */
  public Pawn(final PawnType type) {
    this(type, true);
  }

  /**
   * Constructs a new pawn.
   *
   * @param type this pawn's type.
   * @param aggressive this pawn's behaviour, true if aggressive. Aggressive
   *     means that it can take an opponent's pawn.
   */
  public Pawn(final PawnType type, final boolean aggressive) {
    this.type = type;
    this.aggressive = aggressive;
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
  public void setPlayer(final Player player) {
    this.player = player;
  }

  /**
   * Gets the pawn's movement range.
   *
   * @param coord the starting location
   * @return Set of all possible movements. If the set is empty, no movements
   *     are possible.
   */
  public Set<Coordinates> range(final Coordinates coord) {
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
   * Tells if a pawn is passive or not. Passive means that it can not take an
   * opponent's pawn.
   *
   * @return true if it is passive.
   */
  public boolean isPassive() {
    return !aggressive;
  }

  /**
   * Set pawn's behaviour. Aggressive means that it can take an opponent's pawn.
   *
   * @param aggressive Pawn's behaviour.
   */
  public void setBehaviour(final boolean aggressive) {
    this.aggressive = aggressive;
  }

  /**
   * Gets this pawn's type.
   *
   * @return this pawn's type.
   */
  public PawnType type() {
    return type;
  }

  /**
   * Tells if this pawn is good or not.
   *
   * @return true if it is good.
   */
  public boolean isGood() {
    return type.equals(PawnType.GOOD);
  }

  /**
   * Tells if this pawn is evil or not.
   *
   * @return true if it is evil.
   */
  public boolean isEvil() {
    return type.equals(PawnType.EVIL);
  }
}
