package com.vampirehemophile.ghosts.gamestates;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.vampirehemophile.ghosts.assets.ImageLoader;
import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.math.Coordinates;


/**
 * State for the main game process.
 */
public class MainGameState extends GameState {

  /** True is the user has selected a pawn. */
  private boolean hasSelectedPawn = false;

  /** The coordinates of the square where the selected pawn is located. */
  private Coordinates selectedCoord = null;

  /** An optional error message, printed in the message box. */
  private String errorMessage = null;

  /** First launch. */
  private boolean firstLaunch = true;


  /**
   * Constructs a new MainGameState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public MainGameState(final JPanel panel, final BoardManager bm) {
    super(panel, bm);
    current = white;
  }

  /** {@inheritDoc} */
  @Override
  public void enter() {
    if (firstLaunch) {
      firstLaunch = false;
      setChanged();
      GameState gs = new WaitingState(panel, bm);
      gs.current = white;
      notifyObservers(gs);
    }
    panel.repaint();
  }

  /** {@inheritDoc} */
  @Override
  public void paint(final Graphics2D g2d) {
    drawBoard(g2d);
    drawPawns(g2d);
    if (selectedCoord != null) {
      if (current.equals(white)) {
        g2d.drawRect(
            selectedCoord.xMatrix() * ImageLoader.SQUARE_SIZE,
            (bm.size() - 1 - selectedCoord.yMatrix()) * ImageLoader.SQUARE_SIZE,
            ImageLoader.SQUARE_SIZE, ImageLoader.SQUARE_SIZE);
      } else if (current.equals(black)) {
        g2d.drawRect(
            (bm.size() - 1 - selectedCoord.xMatrix()) * ImageLoader.SQUARE_SIZE,
            selectedCoord.yMatrix() * ImageLoader.SQUARE_SIZE,
            ImageLoader.SQUARE_SIZE, ImageLoader.SQUARE_SIZE);
      }
    }
    if (errorMessage != null) {
      drawMessage(g2d, errorMessage, 1);
    }
    drawEatenPawns(g2d);
  }

  /** {@inheritDoc} */
  public void mousePressed(final MouseEvent e) {
    super.mousePressed(e);

    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        Coordinates loc = hoveredSquare();
        if (loc == null) {
          return;
        }

        if (!hasSelectedPawn) {
          Pawn temp = bm.board().at(loc);
          if (temp != null && temp.player().equals(current)) {
            hasSelectedPawn = true;
            selectedCoord = loc;
          }
        } else {
          if (loc.equals(selectedCoord)) {
            selectedCoord = null;
            hasSelectedPawn = false;
            errorMessage = null;
          } else if (bm.board().at(loc) != null
                     && bm.board().at(loc).player().equals(current)) {
            selectedCoord = loc;
          } else if (bm.canMove(selectedCoord, loc)) {
            bm.move(selectedCoord, loc);
            hasSelectedPawn = false;
            selectedCoord = null;
            errorMessage = null;
            boolean hasWon = bm.hasWon(current);
            boolean hasLost = bm.hasLost(current);
            if (hasWon) {
              setChanged();
              notifyObservers(new FinalState(panel, bm, current));
            } else if (hasLost) {
              setChanged();
              notifyObservers(new FinalState(panel, bm, bm.opponent(current)));
            } else {
              current = bm.opponent(current);
              setChanged();
              GameState gs = new WaitingState(panel, bm);
              gs.current = current;
              notifyObservers(gs);
            }
          } else {
            errorMessage = "This move is unauthorized";
          }
        }
        break;
      case MouseEvent.BUTTON3:
        if (hasSelectedPawn) {
          hasSelectedPawn = false;
          selectedCoord = null;
        }
        break;
      default:
        break;
    }
    panel.repaint();
  }
}
