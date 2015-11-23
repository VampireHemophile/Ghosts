package com.vampirehemophile.ghosts.entities;

import java.util.Set;
import java.util.HashSet;

import com.vampirehemophile.ghosts.math.Coordinates;

/**
 * Pawns.
 */
public class Pawn {

  /** The pawn's player. */
  private Player player;

  /** Default char icon in CLI for an hidden pawn. */
  public final static char defaultCharIcon = 'A';

  /**
   * Pawns's behaviour. Aggressive means that it can take an
   * opponent's pawn.
   */
  private boolean aggressive;

  /**
   * Pawn's state. A pawn can be good (allows the user to win by exiting the
   * pawn) or evil.
   */
  private boolean good;


  /** Constructs a new Pawn. */
  public Pawn() {
    this(null);
  }

  /**
   * Constructs a new good and aggressive pawn. Adds this pawn to the player
   * pawn set.
   *
   * @param player Pawn's player.
   */
  public Pawn(Player player) {
    this(player, true);
  }

  /**
   * Constructs a new aggressive pawn.
   *
   * @param good this pawn state, true if good. A pawn can be good (allows the
   * user to win by exiting the pawn) or evil.
   */
  public Pawn(boolean good) {
    this(null, good);
  }

  /**
   * Constructs a new pawn.
   *
   * @param good this pawn state, true if good. A pawn can be good (allows the
   * user to win by exiting the pawn) or evil.
   * @param aggressive this pawn's behaviour, true if aggressive. Aggressive
   * means that it can take an opponent's pawn.
   */
  public Pawn(boolean good, boolean aggressive) {
    this(null, good, aggressive);
  }

  /**
   * Constructs a new aggressive pawn. Adds this pawn to the player pawn set.
   *
   * @param player Pawn's player.
   * @param good this pawn state, true if good. A pawn can be good (allows the
   * user to win by exiting the pawn) or evil.
   */
  public Pawn(Player player, boolean good) {
    this(player, good, true);
  }

  /**
   * Constructs a new pawn. Adds this pawn to the player pawn set.
   *
   * @param player Pawn's player.
   * @param good this pawn state, true if good. A pawn can be good (allows the
   * user to win by exiting the pawn) or evil.
   * @param aggressive this pawn's behaviour, true if aggressive. Aggressive
   * means that it can take an opponent's pawn.
   */
  public Pawn(Player player, boolean good, boolean aggressive) {
    this.player = player;
    if (player != null) {
      player.add(this);
    }
    this.good = good;
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
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Gets the pawn's char icon for CLI printing.
   *
   * @return pawn's char icon.
   */
   public char charIcon() {
     if (good) {
       if (player() == null) {
         return 'G';
       } else {
         return player().charIcon();
       }
     } else {
       if (player() == null) {
         return 'B';
       } else {
         return (char)(player().charIcon() - 'a' + 'A');
       }
     }
   }

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
  public void setBehaviour(boolean aggressive) {
    this.aggressive = aggressive;
  }

  /**
   * Tells if this pawn is good or not. Good allows the user to win by exiting
   * this pawn.
   *
   * @return true if it is good (default).
   */
  public boolean isGood() {
    return good;
  }

  /**
   * Tells if this pawn is evil or not. Evil won't allow the user to win by
   * exiting this pawn.
   *
   * @return true if it is good (default).
   */
  public boolean isEvil() {
    return !good;
  }

  /**
   * Set pawn's state. A pawn can be good (allows the user to win by exiting the
   * pawn) or evil.
   *
   * @param good Pawn's state.
   */
  public void setState(boolean good) {
    this.good = good;
  }
}
