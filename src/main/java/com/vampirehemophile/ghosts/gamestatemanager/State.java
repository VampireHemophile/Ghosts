package com.vampirehemophile.ghosts.gamestatemanager;

import java.awt.Graphics;

import com.vampirehemophile.ghosts.managers.KeyManager;

public abstract class State {

	protected GameStateManager gsm;
	protected KeyManager km;
	
	public State(GameStateManager gsm, KeyManager km) {
		this.gsm = gsm;
		this.km = km;
	}
	
	
	public abstract void tick();

	public abstract void render(Graphics g);



}
