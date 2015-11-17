package com.vampirehemophile.ghosts.gamestatemanager;

import java.awt.Graphics;

import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.BoardManager;

public class PlayState extends State {

  private BoardManager boardManager;
  private Board board;

  private Player playerOne;
  private Player playerTwo;


  public PlayState(int size) {

    this.playerOne = new Player();
    this.playerTwo = new Player();

    this.boardManager = new BoardManager(playerOne, playerTwo, size);
    this.board = boardManager.board();

  }

  public void tick() {

  }

  public void render(Graphics g) {




  }



}
