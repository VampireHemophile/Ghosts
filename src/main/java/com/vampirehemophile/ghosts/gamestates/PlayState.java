package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;

/** Main game state. */
public class PlayState extends State {

  /** main game state's panel. */
  @SuppressWarnings("serial")
  private class PlayPanel extends JPanel {

    /** Constructs a PlayPanel object. */
    public PlayPanel() {
      super();
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
