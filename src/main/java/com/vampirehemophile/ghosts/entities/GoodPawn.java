package com.vampirehemophile.ghosts.entities;

/**
 * A good pawn that can't take opponent's pawns.
 */
public class GoodPawn extends Pawn {

  /** Constructs a new GoodPawn. */
  public GoodPawn() {
    this(null);
  }

  /**
   * Constructs a new GoodPawn. Adds itself to the player's pawn set.
   *
   * @param player The pawn's player.
   */
  public GoodPawn(Player player) {
    super(player);
  }

  /**
   * Gets the pawn's char icon for CLI printing.
   * If the pawn is not related to the player, returns a standard char.
   * For example, if the pawn is not related to a player it will return 'G',
   * but if it is, it may return 'x' or 'o'.
   *
   * @return pawn's char icon.
   */
  @Override public char charIcon() {
    if (player() == null) {
      return 'G';
    } else {
      return player().charIcon();
    }
  }
}
