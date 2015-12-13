package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

/** Main game state. */
public class PlayState extends State {

  /** main game state's panel. */
  @SuppressWarnings("serial")
  private class PlayPanel extends JPanel {

    /** Constructs a PlayPanel object. */
    public PlayPanel() {
      super();
      setPreferredSize(new Dimension(600, 600));
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;

      BufferedImage lightTile = null;
      BufferedImage darkTile = null;
      try {
        lightTile = ImageIO.read(State.getResource("/images/lighttile.png"));
        darkTile  = ImageIO.read(State.getResource("/images/darktile.png"));
      } catch (IOException e) {}
      boolean dark = true;

      for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 6; j++) {
          if (dark) {
            g2d.drawImage(darkTile, i*100, j*100, 100, 100, null);
          } else {
            g2d.drawImage(lightTile, i*100, j*100, 100, 100, null);
          }
          dark = !dark;
        }
        dark = !dark;
      }
    }
  }


  /** states panel. */
  private JPanel panel;


  /** Constructs a PlayState object. */
  public PlayState() {
    super();
    panel = new PlayPanel();
  }

  /** {@inheritDoc} */
  @Override
  public JPanel render() {
    return panel;
  }
}
