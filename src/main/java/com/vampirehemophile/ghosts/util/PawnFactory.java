package com.vampirehemophile.ghosts.util;

import com.vampirehemophile.ghosts.entities.Pawn;
import com.vampirehemophile.ghosts.entities.Player;

/**
 * Provides a simple factory for quickly making new pawns.
 */
public class PawnFactory {

  /**
   * Makes Pawn from corresponding char type.
   *
   * @param c pawn's type.
   * @return the created pawn or null if type is unknown.
   */
  public static Pawn makePawn(char c) {
    Pawn g = new Pawn(true);
    Pawn e = new Pawn(false);

    if (c == g.charIcon()) {
      return g;
    } else if (c == e.charIcon()) {
      return e;
    }
    return null;
  }

  /**
   * Makes Pawn from corresponding char type.
   *
   * @param p the player to whom the pawn belongs.
   * @param c pawn's type.
   * @return the created pawn or null if type is unknown.
   */
  public static Pawn makePawn(Player p, char c) {
    Pawn g1 = new Pawn(true);
    Pawn e1 = new Pawn(false);

    Pawn g = new Pawn(p, true);
    Pawn e = new Pawn(p, false);

    if (c == g1.charIcon()) {
      return g;
    } else if (c == e1.charIcon()) {
      return e;
    }
    return null;
  }

  /**
   * Creates a new good and aggressive pawn.
   *
   * @param p this pawn's player.
   * @return the created pawn.
   */
  public static Pawn makeGoodPawn(Player p) {
    return new Pawn(p, true, true);
  }

  /**
   * Creates a new evil and aggressive pawn.
   *
   * @param p this pawn's player.
   * @return the created pawn.
   */
  public static Pawn makeEvilPawn(Player p) {
    return new Pawn(p, false, true);
  }
}
