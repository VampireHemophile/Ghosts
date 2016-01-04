package com.vampirehemophile.ghosts.gamestates;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.vampirehemophile.ghosts.entities.Player;
import com.vampirehemophile.ghosts.managers.BoardManager;

public class FinalState extends GameState {

	private Player winner;
	
	public FinalState(JPanel panel, BoardManager bm, Player winner) {
		super(panel, bm);
		this.winner = winner;
	}

	@Override
	public void paint(Graphics2D g2d) {
		drawBoard(g2d);
		drawMessage(g2d, "Thanks for playing, the winner is " + (winner==white?"white":"black"), 1);
	}

}
