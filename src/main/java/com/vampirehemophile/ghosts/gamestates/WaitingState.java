package com.vampirehemophile.ghosts.playstates;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.math.Coordinates;

/**
 * State to use when switching players.
 */
public class WaitingState extends GameState {

  /**
   * Constructs a new WaitingState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public WaitingState(JPanel panel, BoardManager bm) {
    super(panel, bm);
    current = null;
  }

  /** {@inheritDoc} */
  @Override
  public void paint(Graphics2D g2d) {
    drawBoard(g2d);
    drawPawns(g2d);
    drawMessage(g2d, "Press ENTER to switch to the next player.", 1);
  }

  /** {@inheritDoc} */
  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      setChanged();
      notifyObservers();
    }
  }
}
