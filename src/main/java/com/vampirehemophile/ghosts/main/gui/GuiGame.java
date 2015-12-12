package com.vampirehemophile.ghosts.main.gui;

/**
 * GuiGame class.
 */
public class GuiGame {

  /** main frame. */
  private Display display;


  /**
   * Constructor for GuiGame.
   */
  public GuiGame() {
    display = new Display();
  }

  /**
   * start.
   */
  public void start() {
    display.setVisible(true);
  }
}
