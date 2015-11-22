package com.vampirehemophile.ghosts.util;

import com.vampirehemophile.ghosts.entities.EvilPawn;
import com.vampirehemophile.ghosts.entities.GoodPawn;
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
    GoodPawn g = new GoodPawn();
    EvilPawn e = new EvilPawn();

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
    GoodPawn g1 = new GoodPawn();
    EvilPawn e1 = new EvilPawn();

    GoodPawn g = new GoodPawn(p);
    EvilPawn e = new EvilPawn(p);

    if (c == g1.charIcon()) {
      return g;
    } else if (c == e1.charIcon()) {
      return e;
    }
    return null;
  }
}
