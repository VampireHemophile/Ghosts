package com.vampirehemophile.ghosts.gamestatemanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.vampirehemophile.ghosts.main.*;
import com.vampirehemophile.ghosts.managers.*;


public class MenuState extends State {

	
	
	private String[] options = {"Play", "Resume", "Settings", "Exit"};
	
	
  	private Font font;

  	private int currentChoice = 0;

  	

  	
  	//for the timer
  	private long now = System.nanoTime();

  	
  	
  	/**
  	 * Constructor
  	 * @param gsm 
  	 * @param km 
  	 */
  	
  	public MenuState(GameStateManager gsm, KeyManager km) {
	  super(gsm, km);

	  font = new Font("SANS_SERIF", Font.PLAIN, 12);
  	}
  	
  	

  	public void tick() {

  		getInput();

  	}

  	public void render(Graphics g) {

	  // draw menu options
	  g.setFont(font);
	  for(int i = 0; i < options.length; i++) {
		  if(i == currentChoice) {
			g.setColor(Color.BLACK);
		  } else {
			g.setColor(Color.PINK);
		  }
		  g.drawString(options[i], Main.WINDOW_WIDTH / 2 - 22, (Main.WINDOW_HEIGHT / 2 - 30) + i * 15);
	  }
	  
  	}
  
  	
  	/**
  	 * 
  	 * Pushes new State to the GSM 
  	 */

	private void selectChoice() {

		if(currentChoice == 0) {
			this.gsm.add(new PlayState(this.gsm, this.km, 6));
		}
		if(currentChoice == 1) {
			// resume -- reads a file
		}
		if(currentChoice == 2) {
			this.gsm.add(new SettingState(this.gsm, this.km));
		}
		if(currentChoice == 3) {
			System.exit(0);
		}
	}

	
	/**
	 * 
	 * Allows to select via the KeyManager the option you want
	 */
	public void getInput() {


		long delta = System.nanoTime() - now;

		boolean youCanPress = false;

		if(delta > 150000000)
			youCanPress = true;

		if(km.enter && youCanPress){
			selectChoice();
			now = System.nanoTime();
		}

		if(km.up && youCanPress) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
			now = System.nanoTime();
		}

		if(km.down && youCanPress) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
			now = System.nanoTime();
		}

	}


}
