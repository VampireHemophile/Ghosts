package com.vampirehemophile.ghosts.main;

import javax.swing.JFrame;

/**
 * Display class.
 */
@SuppressWarnings("serial")
public class Display extends JFrame {

  /**
   * Constructor for Display.
   */
  public Display() {
    super();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    setTitle("Ghosts !");
    setFocusable(true);

    pack();
  }
}
