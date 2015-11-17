package com.vampirehemophile.ghosts.entities;

/**
 * An evil pawn that can take opponent's pawns.
 */
public class EvilPawn extends Pawn {
  /**
   * Constructs a new EvilPawn.
   *
   * @param player The pawn's player.
   */
  public EvilPawn(Player player) {
    super(player);
  }

  /**
   * Gets the pawn's char icon for CLI printing.
   *
   * @return pawn's char icon.
   */
  @Override public char charIcon() {
    return (char)(player.charIcon() - 'a' + 'A');
  }
}
