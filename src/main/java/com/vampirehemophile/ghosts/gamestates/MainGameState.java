package com.vampirehemophile.ghosts.playstates;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.math.Coordinates;

/**
 * State for the main game process.
 */
public class MainGameState extends GameState {

  /**
   * Constructs a new MainGameState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public MainGameState(JPanel panel, BoardManager bm) {
    super(panel, bm);
    current = white;
  }

  /** {@inheritDoc} */
  @Override
  public void paint(Graphics2D g2d) {

  }
}
