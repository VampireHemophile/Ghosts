package com.vampirehemophile.ghosts.gamestatemanager;

import java.awt.Graphics;

import com.vampirehemophile.ghosts.entities.*;


public class PlayState extends State {

  private Board board;

  private Player playerOne;
  private Player playerTwo;


  public PlayState(int size) {

    this.playerOne = new Player();
    this.playerTwo = new Player();

    this.board = new Board(playerOne, playerTwo, size);


  }

  public void tick() {

  }

  public void render(Graphics g) {




  }



}
