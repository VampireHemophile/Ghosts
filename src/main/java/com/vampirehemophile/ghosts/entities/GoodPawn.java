package com.vampirehemophile.ghosts.entities;

/**
 * A good pawn that can't take opponent's pawns.
 */
public class GoodPawn extends Pawn {
  /**
   * Constructs a new GoodPawn.
   *
   * @param player The pawn's player.
   */
  public GoodPawn(Player player) {
    super(player);
  }

  /**
   * Gets the pawn's char icon for CLI printing.
   *
   * @return pawn's char icon.
   */
  @Override public char charIcon() {
    return player.charIcon();
  }
}
