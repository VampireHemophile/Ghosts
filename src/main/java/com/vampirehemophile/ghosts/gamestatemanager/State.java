package com.vampirehemophile.ghosts.gamestatemanager;

import java.awt.Graphics;

import com.vampirehemophile.ghosts.managers.KeyManager;

/**
 * Abstract State class.
 */
public abstract class State {

  protected GameStateManager gsm;
  protected KeyManager km;


  /**
   * Constructor for State.
   *
   * @param gsm a {@link com.vampirehemophile.ghosts.gamestatemanager.GameStateManager}
	 *     object.
   * @param km a {@link com.vampirehemophile.ghosts.managers.KeyManager} object.
   */
  public State(GameStateManager gsm, KeyManager km) {
    this.gsm = gsm;
    this.km = km;
  }

  /**
   * tick.
   */
  public abstract void tick();

  /**
   * render.
   *
   * @param g a {@link java.awt.Graphics} object.
   */
  public abstract void render(Graphics g);
}
