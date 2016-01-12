package com.vampirehemophile.ghosts.gamestates;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
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

  /** The main content panel. */
  protected JPanel panel;

  /** A transparent cursor. */
  private static final Cursor BLANK_CURSOR =
      Toolkit.getDefaultToolkit().createCustomCursor(
        new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
        new Point(0, 0),
        "blank cursor");

  /** The board manager. */
  protected BoardManager bm;

  /** THe board. */
  protected Board board;

  /** The current player. */
  protected Player current;

  /** The white player. */
  protected Player white;

  /** The black player. */
  protected Player black;

  /** The mouse cursor X-axis value. */
  protected int mouseX;

  /** The mouse cursor Y-axis value. */
  protected int mouseY;

  /** If the cheat mode is enabled. */
  protected boolean cheatModeEnabled = false;



  /**
   * Constructs a new GameState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public GameState(final JPanel panel, final BoardManager bm) {
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
  public abstract void paint(final Graphics2D g2d);

  /**
   * Called when entering a state.
   */
  public void enter() {
    panel.repaint();
  }

  /**
   * Called when exiting a state.
   */
  public void exit() {

  }


  // Graphics functions

  /** The cursor width. */
  private static final int CURSOR_WIDTH = 64;

  /** THe cursor height. */
  private static final int CURSOR_HEIGHT = 64;

  /**
   * Draws the board.
   *
   * @param g2d the graphics object.
   */
  protected void drawBoard(final Graphics2D g2d) {
    boolean dark = true;
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.size(); j++) {
        if (dark) {
          g2d.drawImage(ImageLoader.DARK_TILE,
              i * ImageLoader.SQUARE_SIZE, j * ImageLoader.SQUARE_SIZE, null);
        } else {
          g2d.drawImage(ImageLoader.LIGHT_TILE,
              i * ImageLoader.SQUARE_SIZE, j * ImageLoader.SQUARE_SIZE, null);
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
  protected void drawPawns(final Graphics2D g2d) {
    Pawn pawn;

    int i = 0;
    int j = 0;

    boolean isWhite = current.equals(white);

    for (int x = 0; x < bm.size(); x++) {
      for (int y = 0; y < bm.size(); y++) {
        pawn = board.at(new Coordinates(x, y, board.size()));

        if (isWhite) {
          i = x * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_TOP_X;
          j = (bm.size() - 1 - y) * ImageLoader.SQUARE_SIZE
              + ImageLoader.IMAGE_TOP_Y;
        } else {
          i = (bm.size() - 1 - x) * ImageLoader.SQUARE_SIZE
              + ImageLoader.IMAGE_TOP_X;
          j = y * ImageLoader.SQUARE_SIZE + ImageLoader.IMAGE_TOP_Y;
        }

        if (pawn != null) {
          if (cheatModeEnabled) {
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
   * Draws the number of captured pawns by both players.
   *
   * @param g2d the graphics object.
   */
  protected void drawEatenPawns(final Graphics2D g2d) {
    g2d.drawString("Good/Evil taken : ",
                   bm.size() * ImageLoader.SQUARE_SIZE,
                   ImageLoader.SQUARE_SIZE / 2);

    g2d.drawString(String.valueOf(Player.DEFAULT_GOOD_NUMBER
                                - this.current.countGoodPawns())
                 + " / "
                 + String.valueOf(Player.DEFAULT_EVIL_NUMBER
                                - this.current.countEvilPawns()),
                   bm.size() * ImageLoader.SQUARE_SIZE + 10,
                   ImageLoader.SQUARE_SIZE * 1.5f);
    g2d.drawString(String.valueOf(Player.DEFAULT_GOOD_NUMBER
                                - bm.opponent(current).countGoodPawns())
                 + " / "
                 + String.valueOf(Player.DEFAULT_EVIL_NUMBER
                                - bm.opponent(current).countEvilPawns()),
                   bm.size() * ImageLoader.SQUARE_SIZE + 10,
                   ImageLoader.SQUARE_SIZE * bm.size() / 1.333f);
  }

  /**
   * Draws an image under the mouse cursor.
   *
   * @param image the image.
   * @param g2d the graphics object.
   */
  protected void drawUnderMouse(final Graphics2D g2d, final Image image) {
    g2d.drawImage(image,
        mouseX - ImageLoader.IMAGE_CENTER_X,
        mouseY - ImageLoader.IMAGE_CENTER_Y,
        null);
  }

  /** Offset in pixel from where to start drawing a message.*/
  private static final int OFFSET = 10;

  /** Height of the font, adds some vertical spacing between the two lines. */
  private static final int FONT_HEIGHT = 15;

  /**
    * Draws a message.
    *
    * @param g2d the graphics object.
    * @param message the message.
    * @param line the line number to print the message on. Line 1 or 2.
    */
  protected void drawMessage(final Graphics2D g2d,
                             final String message,
                             final int line) {
    g2d.drawString(message != null ? message : "",
        OFFSET, bm.size() * ImageLoader.SQUARE_SIZE + line * FONT_HEIGHT);
  }

  /**
   * Returns the image corresponding to a pawn. If the player's pawns is not
   * set, returns the image corresponding to the current player. Returns null
   * otherwise.
   *
   * @param pawn the pawn to render.
   * @return the image.
   */
  protected Image imageFromPawn(final Pawn pawn) {
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
        default:
          throw new RuntimeException();
      }
    } else if (player.equals(black)) {
      switch (pawn.type()) {
        case GOOD:
          return ImageLoader.BLACK_GOOD_PAWN;
        case EVIL:
          return ImageLoader.BLACK_EVIL_PAWN;
        case UNKNOWN:
          return ImageLoader.BLACK_NEUTRAL_PAWN;
        default:
          throw new RuntimeException();
      }
    }
    return null;
  }

  /**
   * Draws a rectangle around the square indicated by the coordinates.
   *
   * @param g2d the graphics object.
   * @param c the square's coordinates.
   */
  protected void rectSquare(final Graphics2D g2d, final Coordinates c) {
    int x, y, size;
    if (current.equals(white)) {
      x = c.xMatrix() * ImageLoader.SQUARE_SIZE;
      y = (bm.size() - 1 - c.yMatrix()) * ImageLoader.SQUARE_SIZE;
      size = ImageLoader.SQUARE_SIZE;
    } else if (current.equals(black)) {
      x = (bm.size() - 1 - c.xMatrix()) * ImageLoader.SQUARE_SIZE;
      y = c.yMatrix() * ImageLoader.SQUARE_SIZE;
      size = ImageLoader.SQUARE_SIZE;
    } else {
      throw new RuntimeException();
    }
    g2d.fillRect(x, y, size, 5);
    g2d.fillRect(x + size - 5, y, 5, size);
    g2d.fillRect(x, y, 5, size);
    g2d.fillRect(x, y + size - 5, size, 5);
  }

  /**
   * Gets coordinates of the hovered square.
   *
   * @return the corresponding coordinates or null if no square is hovered.
   */
  protected Coordinates hoveredSquare() {
    if (mouseX > bm.size() * ImageLoader.SQUARE_SIZE
        || mouseY > bm.size() * ImageLoader.SQUARE_SIZE) {
      return null;
    }

    int squareSize = ImageLoader.SQUARE_SIZE;
    int x = 0;
    int y = 0;
    if (current.equals(white)) {
      int mx = mouseX;
      x = 0;
      while (mx > squareSize) {
        mx -= squareSize;
        x++;
      }

      int my = mouseY;
      y = bm.size() - 1;
      while (my > squareSize) {
        my -= squareSize;
        y--;
      }
    } else if (current.equals(black)) {
      int mx = mouseX;
      x = bm.size() - 1;
      while (mx > squareSize) {
        mx -= squareSize;
        x--;
      }

      int my = mouseY;
      y = 0;
      while (my > squareSize) {
        my -= squareSize;
        y++;
      }
    }

    return new Coordinates(x, y, board.size());
  }

  /**
   * Shows the default cursor.
   */
  protected void showCursor() {
    panel.setCursor(Cursor.getDefaultCursor());
  }

  /**
   * Hides the cursor.
   */
  protected void hideCursor() {
    panel.setCursor(BLANK_CURSOR);
  }


  // Mouse events

  /** {@inheritDoc} */
  @Override
  public void mouseDragged(final MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  /** {@inheritDoc} */
  @Override
  public void mouseMoved(final MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  /** {@inheritDoc} */
  @Override
  public void mouseClicked(final MouseEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void mouseEntered(final MouseEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void mouseExited(final MouseEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void mousePressed(final MouseEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void mouseReleased(final MouseEvent e) {

  }


  // Key events

  /** {@inheritDoc} */
  @Override
  public void keyPressed(final KeyEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void keyReleased(final KeyEvent e) {

  }

  /** {@inheritDoc} */
  @Override
  public void keyTyped(final KeyEvent e) {

  }

  //getters and setters
  /** Enables the cheat mode. */
  public void enableCheatMode() {
    this.cheatModeEnabled = true;
  }
}
