package com.vampirehemophile.ghosts.managers;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyManager implements KeyListener {

  private boolean[] keys;
  public boolean up, down, left, right, enter, escape;

  public KeyManager() {
    keys = new boolean[256];
  }

  public void tick() {
    up = keys[KeyEvent.VK_Z];
    down = keys[KeyEvent.VK_S];
	left = keys[KeyEvent.VK_Q];
	right = keys[KeyEvent.VK_D];
	enter = keys[KeyEvent.VK_ENTER];
	escape = keys[KeyEvent.VK_ESCAPE];
  }

  public void keyPressed(KeyEvent e){

    keys[e.getKeyCode()] = true;
    //System.out.println("pressed !"); //debug
    
  }

  public void keyReleased(KeyEvent e){

    keys[e.getKeyCode()] = false;

  }

  public void keyTyped(KeyEvent e){


  }


}
