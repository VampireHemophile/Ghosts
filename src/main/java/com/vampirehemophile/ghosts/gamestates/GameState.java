package com.vampirehemophile.ghosts.gamestates;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.vampirehemophile.ghosts.assets.ImageLoader;
import com.vampirehemophile.ghosts.entities.Board;
import com.vampirehemophile.ghosts.entities.Pawn;
import com.vampirehemophile.ghosts.entities.Player;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.math.Coordinates;


/**
 * Provides an abstract class for ingame play states.
 * It is responsible for catching events that may be fired from the game panel,
 * can implement listeners, and draw on the graphics object.
 * A state is also made observable. It notifies its observers when the game
 * switches to a new state, and sends the new state.
 */
public abstract class GameState extends Observable
    implements MouseInputListener, KeyListener {

  protected JPanel panel;


  // cursors
  protected Cursor WHITE_GOOD_PAWNCursor;
  protected Cursor WHITE_EVIL_PAWNCursor;
  protected Cursor BLACK_GOOD_PAWNCursor;
  protected Cursor BLACK_EVIL_PAWNCursor;
  private static Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

  // board
  protected BoardManager bm;
  protected Board board;

  // players
  protected Player current;
  protected Player white;
  protected Player black;

  // mouse
  protected int mouseX;
  protected int mouseY;

  //cheatMode
  protected boolean cheatModeEnabled = false;



  /**
   * Constructs a new GameState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public GameState(JPanel panel, BoardManager bm) {
    WHITE_GOOD_PAWNCursor = loadCursor(ImageLoader.WHITE_GOOD_PAWN);
    WHITE_EVIL_PAWNCursor = loadCursor(ImageLoader.WHITE_EVIL_PAWN);
    BLACK_GOOD_PAWNCursor = loadCursor(ImageLoader.BLACK_GOOD_PAWN);
    BLACK_EVIL_PAWNCursor = loadCursor(ImageLoader.BLACK_EVIL_PAWN);

    this.panel = panel;

    this.bm = bm;
    white = bm.white();
    black = bm.black();
    board = bm.board();
  }


  // State functions

  /**
   * Paint a graphics object.
   *
   * @param g2d the graphics object.
   */
  public abstract void paint(Graphics2D g2d);

  /**
   * Called when entering a state.
   */
  public void enter() {
    panel.repaint();
  }

  /**
   * Called when exiting a state.
   */
  public void exit() {}


  // Graphics functions

  /**
   * Creates a cursor from a pawn image. The cursor can be used when dragging
   * a pawn.
   *
   * @param image the pawn's image.
   * @return the corresponding cursor.
   */
  private Cursor loadCursor(Image image) {
    Image imageCursor = image.getScaledInstance(25, 50, Image.SCALE_DEFAULT);
    return Toolkit.getDefaultToolkit().createCustomCursor(
        imageCursor,
        new Point(imageCursor.getWidth(null) / 2,
                  imageCursor.getHeight(null) / 2),
        null);
  }

  /**
   * Draws the board.
   *
   * @param g2d the graphics object.
   */
  protected void drawBoard(Graphics2D g2d) {
    boolean dark = true;
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        if (dark) {
          g2d.drawImage(ImageLoader.DARK_TILE, i*ImageLoader.SQUARE_SIZE, j*ImageLoader.SQUARE_SIZE, null);
        } else {
          g2d.drawImage(ImageLoader.LIGHT_TILE, i*ImageLoader.SQUARE_SIZE, j*ImageLoader.SQUARE_SIZE, null);
        }
        dark = !dark;
      }
      dark = !dark;
    }
  }

  /**
   * Draws the pawn set on the board.
   *
   * @param g2d the graphics object.
   */
  protected void drawPawns(Graphics2D g2d) {
    Pawn pawn;

    int i = 0;
    int j = 0;

    boolean isWhite = current.equals(white);

    for (int x = 0; x < bm.size(); x++) {
    	for (int y = 0; y < bm.size(); y++) {
    		pawn = board.at(new Coordinates(x, y, board.size()));

    		if (isWhite) {
    			i = x * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_TOP_X;
    			j = (bm.size() - 1 - y) * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_TOP_Y;
    		} else {
    			i = (bm.size() - 1 - x) * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_TOP_X;
    			j = y * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_TOP_Y;
    		}

    		if (pawn != null) {
    			if(cheatModeEnabled) {
    				g2d.drawImage(imageFromPawn(pawn), i, j, null);
    			} else {
    				if (pawn.player().equals(current)) {
    					g2d.drawImage(imageFromPawn(pawn), i, j, null);
    				} else if (pawn.player().equals(white)) {
    					g2d.drawImage(ImageLoader.WHITE_NEUTRAL_PAWN, i, j, null);
    				} else if (pawn.player().equals(black)) {
    					g2d.drawImage(ImageLoader.BLACK_NEUTRAL_PAWN, i, j, null);
    				}
    			}
    		}
    	}
    }
  }


  /**
   * Draws an image under the mouse cursor.
   *
   * @param image the image.
   * @param g2d the graphics object.
   */
  protected void drawUnderMouse(Graphics2D g2d, Image image) {
    g2d.drawImage(image, mouseX - image.getWidth(null) / 2, mouseY - image.getHeight(null) / 2, null);
  }

  /**
    * Draws a message.
    *
    * @param g2d the graphics object.
    * @param message the message.
    * @param line the line number to print the message on. Line 1 or 2.
    */
  protected void drawMessage(Graphics2D g2d, String message, int line) {
    g2d.drawString(message != null ? message : "", 10, bm.size() * ImageLoader.SQUARE_SIZE + line * 15);
  }

  /**
   * Returns the image corresponding to a pawn. If the player's pawns is not
   * set, returns the image corresponding to the current player. Returns null
   * otherwise.
   *
   * @param pawn the pawn to render.
   * @return the image.
   */
  protected Image imageFromPawn(Pawn pawn) {
    Player player = pawn.player();
    player = player != null ? player : current;
    if (player == null) {
      return null;
    }

    if (player.equals(white)) {
      switch (pawn.type()) {
        case GOOD:
        return ImageLoader.WHITE_GOOD_PAWN;
        case EVIL:
        return ImageLoader.WHITE_EVIL_PAWN;
        case UNKNOWN:
        return ImageLoader.WHITE_NEUTRAL_PAWN;
      }
    } else if (player.equals(black)) {
      switch (pawn.type()) {
        case GOOD:
        return ImageLoader.BLACK_GOOD_PAWN;
        case EVIL:
        return ImageLoader.BLACK_EVIL_PAWN;
        case UNKNOWN:
        return ImageLoader.BLACK_NEUTRAL_PAWN;
      }
    }
    return null;
  }

  /**
   * Returns the cursor corresponding to a pawn. If the player's pawns is not
   * set, returns the cursor corresponding to the current player. Returns null
   * otherwise.
   *
   * @param pawn the pawn to render.
   * @return the cursor.
   */
  protected Cursor cursorFromPawn(Pawn pawn) {
    Player player = pawn.player();
    player = player != null ? player : current;
    if (player == null) {
      return null;
    }

    if (player.equals(white)) {
      switch (pawn.type()) {
        case GOOD:
        return WHITE_GOOD_PAWNCursor;
        case EVIL:
        return WHITE_EVIL_PAWNCursor;
      }
    } else if (player.equals(black)) {
      switch (pawn.type()) {
        case GOOD:
        return BLACK_GOOD_PAWNCursor;
        case EVIL:
        return BLACK_EVIL_PAWNCursor;
      }
    }
    return null;
  }

  /**
   * Gets coordinates of the hovered square.
   *
   * @return the corresponding coordinates.
   */
  protected Coordinates hoveredSquare() {
    int size = ImageLoader.SQUARE_SIZE;
	  int x = 0;
	  int y = 0;
	  if (current.equals(white)) {
		  int mx = mouseX;
		  x = 0;
		  while (mx > size) {
			  mx -= size;
			  x++;
		  }

		  int my = mouseY;
		  y = bm.size() - 1;
		  while (my > size) {
			  my -= size;
			  y--;
		  }
	  } else if (current.equals(black)) {
		  int mx = mouseX;
		  x = bm.size() - 1;
		  while (mx > size) {
			  mx -= size;
			  x--;
		  }

		  int my = mouseY;
		  y = 0;
		  while (my > size) {
			  my -= size;
			  y++;
		  }
	  }

    return new Coordinates(x, y, board.size());
  }

  /**
   * Resets the cursor.
   */
  protected void resetCursor() {
    panel.setCursor(defaultCursor);
  }


  // Mouse events

  /** {@inheritDoc} */
  @Override
  public void mouseDragged(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  /** {@inheritDoc} */
  @Override
  public void mouseMoved(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  /** {@inheritDoc} */
  @Override
  public void mouseClicked (MouseEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void mouseEntered (MouseEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void mouseExited (MouseEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void mousePressed (MouseEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void mouseReleased (MouseEvent e) {

  }


  // Key events

  /** {@inheritDoc} */
  @Override
  public void keyPressed(KeyEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void keyReleased(KeyEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void keyTyped(KeyEvent e) {

  }

  //getters and setters

  public void enableCheatMode() {
	  this.cheatModeEnabled = true;
  }
}
