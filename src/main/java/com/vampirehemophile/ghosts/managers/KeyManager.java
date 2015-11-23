package com.vampirehemophile.ghosts.managers;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * KeyManager class.
 */
public class KeyManager implements KeyListener {

  private boolean[] keys;
  public boolean up, down, left, right, enter, escape;

  /**
   * Constructor for KeyManager.
   */
  public KeyManager() {
    keys = new boolean[256];
  }

  /**
   * tick.
   */
  public void tick() {
    up = keys[KeyEvent.VK_Z];
    down = keys[KeyEvent.VK_S];
  left = keys[KeyEvent.VK_Q];
  right = keys[KeyEvent.VK_D];
  enter = keys[KeyEvent.VK_ENTER];
  escape = keys[KeyEvent.VK_ESCAPE];
  }

  /** {@inheritDoc} */
  @Override public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
    // System.out.println("pressed !"); // debug
  }

  /** {@inheritDoc} */
  @Override public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
  }

  /** {@inheritDoc} */
  @Override public void keyTyped(KeyEvent e) {
  }
}
