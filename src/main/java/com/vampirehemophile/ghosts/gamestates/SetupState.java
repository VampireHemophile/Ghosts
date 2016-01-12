package com.vampirehemophile.ghosts.gamestates;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.vampirehemophile.ghosts.math.Coordinates;
import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.BoardManager;


/**
 * State allowing the players to set their pawns on the board.
 */
public class SetupState extends GameState {

  /** A good pawn that waits to be set. */
  protected Pawn goodPawn;

  /** A good pawn that waits to be set. */
  protected Pawn evilPawn;

  /** A pawn that waits to be set. */
  protected Pawn selectedPawn;

  /** True if it is the black player turn to set its pawns up. */
  private boolean blackSetup;


  /**
   * Constructs a new SetupState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public SetupState(final JPanel panel, final BoardManager bm) {
    super(panel, bm);
    current = white;
    goodPawn = new Pawn(Pawn.PawnType.GOOD);
    evilPawn = new Pawn(Pawn.PawnType.EVIL);

    selectedPawn = goodPawn;

    blackSetup = false;
  }

  /** {@inheritDoc} */
  @Override
  public void enter() {
    hideCursor();
    if (blackSetup) {
      current = black;
      selectedPawn = goodPawn;
    }
    panel.repaint();
  }

  /** {@inheritDoc} */
  @Override
  public void exit() {
    showCursor();
  }

  /** {@inheritDoc} */
  @Override
  public void mouseDragged(final MouseEvent e) {
    super.mouseDragged(e);
    panel.repaint();
  }

  /** {@inheritDoc} */
  @Override
  public void mouseMoved(final MouseEvent e) {
    super.mouseMoved(e);
    panel.repaint();
  }

  /** {@inheritDoc} */
  @Override
  public void mousePressed(final MouseEvent e) {
    super.mousePressed(e);

    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        Coordinates loc = hoveredSquare();
        if (loc == null) {
          return;
        }

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
        if (good == Player.DEFAULT_GOOD_NUMBER
            && evil == Player.DEFAULT_EVIL_NUMBER) {
          if (current.equals(white)) {
            blackSetup = true;
            setChanged();
            GameState gs = new WaitingState(panel, bm);
            gs.current = black;
            notifyObservers(gs);
          } else if (current.equals(black)) {
            current = white;
            setChanged();
            notifyObservers(new MainGameState(panel, bm));
          }
        } else if (good == Player.DEFAULT_GOOD_NUMBER) {
          selectedPawn = evilPawn;
        } else if (evil == Player.DEFAULT_EVIL_NUMBER) {
          selectedPawn = goodPawn;
        }
        break;
      case MouseEvent.BUTTON3:
        if (current.countGoodPawns() < Player.DEFAULT_GOOD_NUMBER
            && current.countEvilPawns() < Player.DEFAULT_EVIL_NUMBER) {
          selectedPawn = selectedPawn == goodPawn ? evilPawn : goodPawn;
          // panel.setCursor(cursorFromPawn(selectedPawn));
        }
        break;
      default:
        break;
    }
    panel.repaint();
  }

  /** {@inheritDoc} */
  @Override
  public void paint(final Graphics2D g2d) {
    drawBoard(g2d);
    drawPawns(g2d);

    drawUnderMouse(g2d, imageFromPawn(selectedPawn));

    drawMessage(g2d,
        "Set your pawns up with left click, "
      + "switch between pawns whith right click.", 1);
    drawMessage(g2d,
        current.countGoodPawns() + " / " + Player.DEFAULT_GOOD_NUMBER
      + " good pawns, "
      + current.countEvilPawns() + " / " + Player.DEFAULT_EVIL_NUMBER
      + "evil pawns.", 2);
  }
}
