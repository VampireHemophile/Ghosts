package com.vampirehemophile.ghosts.main;

import javax.swing.JFrame;
import java.awt.*;

/**
 * Display class.
 */
public class Display {

  private JFrame frame;
  private Canvas canvas;
  private String title;
  private int width, height;

  /**
   * Constructor for Display.
   *
   * @param title a {@link java.lang.String} object.
   * @param width width of the window.
   * @param height height of the window.
   */
  public Display(String title, int width, int height) {
    this.title = title;
    this.width = width;
    this.height = height;
    createDisplay();
  }

  /**
   * createDisplay.
   */
  private void createDisplay() {
    frame = new JFrame(title);
    frame.setSize(width, height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(true);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    canvas = new Canvas();
    canvas.setPreferredSize(new Dimension(width, height));
    canvas.setMaximumSize(new Dimension(width, height));
    canvas.setMinimumSize(new Dimension(width, height));
    canvas.setFocusable(false);

    frame.add(canvas);
    frame.pack();
  }

  /**
   * Gets the canvas.
   *
   * @return a {@link java.awt.Canvas} object.
   */
  public Canvas getCanvas() {
    return this.canvas;
  }

  /**
   * Gets the frame.
   *
   * @return a {@link javax.swing.JFrame} object.
   */
  public JFrame getFrame() {
    return frame;
  }
}
