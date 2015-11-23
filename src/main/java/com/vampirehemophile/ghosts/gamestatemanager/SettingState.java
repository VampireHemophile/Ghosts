package com.vampirehemophile.ghosts.gamestatemanager;

import java.awt.Graphics;

import com.vampirehemophile.ghosts.managers.*;

/**
 * SettingState class.
 */
public class SettingState extends State {

  /**
   * Constructor for SettingState.
   *
   * @param gsm a {@link com.vampirehemophile.ghosts.gamestatemanager.GameStateManager}
   *     object.
   * @param km a {@link com.vampirehemophile.ghosts.managers.KeyManager} object.
   */
  public SettingState(GameStateManager gsm, KeyManager km) {
    super(gsm, km);
  }

  /** {@inheritDoc} */
  @Override public void tick() {
  }

  /** {@inheritDoc} */
  @Override public void render(Graphics g) {
  }
}
