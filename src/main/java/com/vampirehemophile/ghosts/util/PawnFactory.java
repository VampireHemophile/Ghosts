package com.vampirehemophile.ghosts.util;

import com.vampirehemophile.ghosts.entities.Player;
import com.vampirehemophile.ghosts.entities.Pawn;
import com.vampirehemophile.ghosts.entities.GoodPawn;
import com.vampirehemophile.ghosts.entities.EvilPawn;

public class PawnFactory {
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

  public static GoodPawn makeGoodPawn() {
    return new GoodPawn();
  }

  public static GoodPawn makeGoodPawn(Player p) {
    return new GoodPawn(p);
  }

  public static EvilPawn makeEvilPawn() {
    return new EvilPawn();
  }

  public static EvilPawn makeEvilPawn(Player p) {
    return new EvilPawn(p);
  }
}
