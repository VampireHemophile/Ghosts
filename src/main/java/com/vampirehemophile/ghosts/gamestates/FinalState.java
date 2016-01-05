package com.vampirehemophile.ghosts.gamestates;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.vampirehemophile.ghosts.entities.Player;
import com.vampirehemophile.ghosts.managers.BoardManager;


/**
 * State used when one of the players has won.
 */
public class FinalState extends GameState {

  /** This game's winner. */
  private Player winner;


  /**
   * Constructs a new FinalState object.
   *
   * @param panel The game panel.
   * @param bm The game board manager.
   * @param winner The winner.
   */
  public FinalState(final JPanel panel,
                    final BoardManager bm,
                    final Player winner) {
    super(panel, bm);
    this.winner = winner;
  }

  /** {@inheritDoc} */
  @Override
  public void paint(final Graphics2D g2d) {
    drawBoard(g2d);
    drawMessage(g2d, "Thanks for playing, the winner is "
        + (winner == white ? "white" : "black"), 1);
  }
}
