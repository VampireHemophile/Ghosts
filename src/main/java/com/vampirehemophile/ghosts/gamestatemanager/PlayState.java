package com.vampirehemophile.ghosts.gamestatemanager;

import java.awt.Graphics;

import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.*;

/**
 * PlayState class.
 */
public class PlayState extends State {

  private BoardManager boardManager;
  private Board board;

  private Player playerOne;
  private Player playerTwo;


  /**
   * Constructor for PlayState.
   *
   * @param gsm a {@link com.vampirehemophile.ghosts.gamestatemanager.GameStateManager}
   *     object.
   * @param km a {@link com.vampirehemophile.ghosts.managers.KeyManager} object.
   * @param size a int.
   */
  public PlayState(GameStateManager gsm, KeyManager km, int size) {

    super(gsm, km);

    this.playerOne = new Player();
    this.playerTwo = new Player();

    this.boardManager = new BoardManager(playerOne, playerTwo, size);
    this.board = boardManager.board();
  }

  /** {@inheritDoc} */
  @Override public void tick() {
    getInput();
  }

  /** {@inheritDoc} */
  @Override public void render(Graphics g) {
  }

  /**
   * getInput.
   */
  public void getInput() {
    if (km.escape) {
      this.gsm.removeState();
    }
  }
}
