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
    GoodPawn g = makeGoodPawn();
    EvilPawn e = makeEvilPawn();

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
    GoodPawn g1 = makeGoodPawn();
    EvilPawn e1 = makeEvilPawn();

    GoodPawn g = makeGoodPawn(p);
    EvilPawn e = makeEvilPawn(p);

    if (c == g1.charIcon()) {
      return g;
    } else if (c == e1.charIcon()) {
      return e;
    }
    return null;
  }

  /**
   * Makes a new @{link GoodPawn}.
   *
   * @return the created pawn.
   */
  public static GoodPawn makeGoodPawn() {
    return new GoodPawn();
  }

  /**
   * Makes a new @{link GoodPawn}.
   *
   * @param p the player to whom the pawn belongs.
   * @return the created pawn.
   */
  public static GoodPawn makeGoodPawn(Player p) {
    return new GoodPawn(p);
  }

  /**
   * Makes a new @{link EvilPawn}.
   *
   * @return the created pawn.
   */
  public static EvilPawn makeEvilPawn() {
    return new EvilPawn();
  }

  /**
   * Makes a new @{link EvilPawn}.
   *
   * @param p the player to whom the pawn belongs.
   * @return the created pawn.
   */
  public static EvilPawn makeEvilPawn(Player p) {
    return new EvilPawn(p);
  }
}
