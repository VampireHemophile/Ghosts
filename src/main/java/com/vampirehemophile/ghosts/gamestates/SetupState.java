package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import com.vampirehemophile.ghosts.math.Coordinates;
import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.BoardManager;

/**
 * State allowing the players to set their pawns on the board.
 */
public class SetupState extends GameState {

  protected Pawn goodPawn;
  protected Pawn evilPawn;
  protected Pawn selectedPawn;

  private boolean blackSetup;


  /**
   * Constructs a new SetupState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public SetupState(JPanel panel, BoardManager bm) {
    super(panel, bm);
    current = white;
    goodPawn = new Pawn(Pawn.PawnType.GOOD);
    evilPawn = new Pawn(Pawn.PawnType.EVIL);

    selectedPawn = goodPawn;
    panel.setCursor(whiteGoodPawnCursor);

    blackSetup = false;
  }

  /** {@inheritDoc} */
  @Override
  public void enter() {
    if (blackSetup) {
      current = black;
      selectedPawn = goodPawn;
      panel.setCursor(blackGoodPawnCursor);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void mousePressed(MouseEvent e) {
	  
    super.mousePressed(e);
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
      Coordinates loc = hoveredSquare(100);
      
      if (!bm.canSet(current, loc)) {
        break;
      }
      current.add(selectedPawn);
      board.set(selectedPawn, loc);
      if (selectedPawn == goodPawn) {
        goodPawn = new Pawn(Pawn.PawnType.GOOD);
        selectedPawn = goodPawn;
      } else if (selectedPawn == evilPawn) {
        evilPawn = new Pawn(Pawn.PawnType.EVIL);
        selectedPawn = evilPawn;
      }
      int good = current.countGoodPawns();
      int evil = current.countEvilPawns();
      if (good == 4 && evil == 4) {
        if (current.equals(white)) {
          blackSetup = true;
          resetCursor();
          setChanged();
          GameState gs = new WaitingState(panel, bm);
          gs.current = black;
          notifyObservers(gs);
        } else if (current.equals(black)) {
          current = white;
          resetCursor();
          setChanged();
          notifyObservers(new MainGameState(panel, bm));
        }
      } else if (good == 4) {
        selectedPawn = evilPawn;
        panel.setCursor(cursorFromPawn(selectedPawn));
      } else if (evil == 4) {
        selectedPawn = goodPawn;
        panel.setCursor(cursorFromPawn(selectedPawn));
      }
      panel.repaint();
      break;
      case MouseEvent.BUTTON3:
      if (current.countGoodPawns() < 4
          && current.countEvilPawns() < 4) {
        selectedPawn = selectedPawn == goodPawn ? evilPawn : goodPawn;
        panel.setCursor(cursorFromPawn(selectedPawn));
      }
      break;
    }
  }

  /** {@inheritDoc} */
  @Override
  public void paint(Graphics2D g2d) {
    drawBoard(g2d);
    drawPawns(g2d);
    drawMessage(g2d, "Set your pawns up with left click, switch between pawns whith right click.", 1);
    drawMessage(g2d, current.countGoodPawns() + " / 4 good pawns, "
                   + current.countEvilPawns() + " / 4 evil pawns.", 2);
  }
}
