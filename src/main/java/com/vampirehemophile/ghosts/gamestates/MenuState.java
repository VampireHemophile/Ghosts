package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;

/** Main menu state. */
public class MenuState extends State {

  /** main game menu state's panel. */
  @SuppressWarnings("serial")
  private class MenuPanel extends JPanel {

    /** Constructs a MenuPanel object. */
    public MenuPanel() {
      super();
    }
  }


  /** states panel. */
  private JPanel panel;


  /** Constructs a MenuState object. */
  public MenuState() {
    super();
    panel = new MenuPanel();
  }

  /** {@inheritDoc} */
  @Override
  public JPanel render() {
    return panel;
  }
}
