package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.vampirehemophile.ghosts.assets.ImageLoader;
import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.math.Coordinates;

/**
 * State to use when switching players.
 */
public class WaitingState extends GameState {

  /**
   * Constructs a new WaitingState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public WaitingState(JPanel panel, BoardManager bm) {
    super(panel, bm);
  }

  /** {@inheritDoc} */
  @Override
  public void paint(Graphics2D g2d) {
    drawBoard(g2d);
    drawPawns(g2d);
    drawMessage(g2d, "Press ENTER to switch to the next player.", 1);
  }

  /**
   * Draws the pawn set on the board.
   *
   * @param g2d the graphics object.
   */
  @Override
  protected void drawPawns(Graphics2D g2d) {
    Pawn pawn;

    int i = 0;
    int j = 0;

    boolean isWhite = current.equals(white);

    for (int x = 0; x < bm.size(); x++) {
    	for (int y = 0; y < bm.size(); y++) {
    		pawn = board.at(new Coordinates(x, y, board.size()));

        if (isWhite) {
    			i = x * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_CENTER_X;
    			j = (bm.size() - 1 - y) * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_CENTER_Y;
    		} else {
    			i = (bm.size() - 1 - x) * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_CENTER_X;
    			j = y * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_CENTER_Y;
    		}

    		if (pawn != null) {
    			if(cheatModeEnabled) {
    				g2d.drawImage(imageFromPawn(pawn), i, j, null);
    			} else {
    				if (pawn.player().equals(white)) {
    					g2d.drawImage(ImageLoader.whiteNeutralPawn, i, j, null);
    				} else if (pawn.player().equals(black)) {
    					g2d.drawImage(ImageLoader.blackNeutralPawn, i, j, null);
    				}
    			}
    		}
    	}
    }
  }

  /** {@inheritDoc} */
  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      setChanged();
      notifyObservers();
    }
  }

  @Override
  public void enableCheatMode() {
	  setChanged();
	  notifyObservers();
  }
}
