package com.vampirehemophile.ghosts.gamestatemanager;

import java.awt.Graphics;

import com.vampirehemophile.ghosts.main.*;

public class MenuState extends State {

  private String[] options = {"Play", "Resume", "Settings", "Exit"};



  public void tick() {

    getInput();

  }

  public void render(Graphics g) {

    for(int i = 0; i < options.length; i++){
      g.drawString(options[i], Main.WINDOW_WIDTH / 2 - 35, Main.WINDOW_HEIGHT / 2 - 30 + i * 15);
    }


  }



  public void getInput() {

    
  }



}
