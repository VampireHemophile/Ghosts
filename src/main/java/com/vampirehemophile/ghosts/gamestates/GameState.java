package com.vampirehemophile.ghosts.playstates;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.imageio.ImageIO;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Observable;
import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.math.Coordinates;


/**
 * Provides an abstract class for ingame play states.
 * It is responsible for catching events that may be fired from the game panel,
 * can implement listeners, and draw on the graphics object.
 * A state is also made observable. It notifies its observers when the game
 * switches to a new state, and sends the new state.
 */
public abstract class GameState extends Observable implements MouseInputListener {

  protected JPanel panel;

  // images
  protected BufferedImage lightTile;
  protected BufferedImage darkTile;
  protected BufferedImage whiteGoodPawn;
  protected BufferedImage whiteEvilPawn;
  protected BufferedImage whiteNeutralPawn;
  protected BufferedImage blackGoodPawn;
  protected BufferedImage blackEvilPawn;
  protected BufferedImage blackNeutralPawn;

  // cursors
  protected Cursor whiteGoodPawnCursor;
  protected Cursor whiteEvilPawnCursor;
  protected Cursor blackGoodPawnCursor;
  protected Cursor blackEvilPawnCursor;
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


  /**
   * Constructs a new GameState object.
   *
   * @param panel the game panel.
   * @param bm the game board manager.
   */
  public GameState(JPanel panel, BoardManager bm) {
    try {
      lightTile        = ImageIO.read(GameState.getResource("/images/lighttile.png"));
      darkTile         = ImageIO.read(GameState.getResource("/images/darktile.png"));
      whiteGoodPawn    = ImageIO.read(GameState.getResource("/images/white/goodpawn.png"));
      whiteEvilPawn    = ImageIO.read(GameState.getResource("/images/white/evilpawn.png"));
      whiteNeutralPawn = ImageIO.read(GameState.getResource("/images/white/neutralpawn.png"));
      blackGoodPawn    = ImageIO.read(GameState.getResource("/images/black/goodpawn.png"));
      blackEvilPawn    = ImageIO.read(GameState.getResource("/images/black/evilpawn.png"));
      blackNeutralPawn = ImageIO.read(GameState.getResource("/images/black/neutralpawn.png"));
    } catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    whiteGoodPawnCursor = loadCursor(whiteGoodPawn);
    whiteEvilPawnCursor = loadCursor(whiteEvilPawn);
    blackGoodPawnCursor = loadCursor(blackGoodPawn);
    blackEvilPawnCursor = loadCursor(blackEvilPawn);

    this.panel = panel;

    this.bm = bm;
    white = bm.white();
    black = bm.black();
    board = bm.board();
  }


  // General functions

  /**
   * Finds a resource with a given name.
   *
   * @param name of the desired resource.
   * @return A java.net.URL object or null if no resource with this name is found.
   */
  public static java.net.URL getResource(String name) {
    return GameState.class.getResource(name);
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
  public void enter() {}

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
  private Cursor loadCursor(BufferedImage image) {
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
          g2d.drawImage(darkTile, i*100, j*100, 100, 100, null);
        } else {
          g2d.drawImage(lightTile, i*100, j*100, 100, 100, null);
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
    for (int x = 0; x < board.size(); x++) {
      for (int y = 0; y < board.size(); y++) {
        pawn = board.at(new Coordinates(x, y, board.size()));
        if (pawn != null) {
          if (pawn.player().equals(current)) {
            g2d.drawImage(imageFromPawn(pawn), x*100 + 25 + 12, y*100 + 25, 25, 50, null);
          } else if (pawn.player().equals(white)) {
            g2d.drawImage(whiteNeutralPawn, x*100 + 25 + 12, y*100 + 25, 25, 50, null);
          } else if (pawn.player().equals(black)) {
            g2d.drawImage(blackNeutralPawn, x*100 + 25 + 12, y*100 + 25, 25, 50, null);
          }
        }
      }
    }
  }

  /**
   * Draws a pawn under the mouse cursor.
   *
   * @param string the message.
   * @param g2d the graphics object.
   */
  protected void drawUnderMouse(Graphics2D g2d, BufferedImage image) {
    g2d.drawImage(image, mouseX - 12, mouseY - 25, 25, 50, null);
  }

  /**
    * Draws a message.
    *
    * @param g2d the graphics object.
    * @param message the message.
    * @param line the line number to print the message on. Line 1 or 2.
    */
  protected void drawMessage(Graphics2D g2d, String message, int line) {
    g2d.drawString(message != null ? message : "", 10, 600 + line * 15);
  }

  /**
   * Returns the image corresponding to a pawn. If the player's pawns is not
   * set, returns the image corresponding to the current player. Returns null
   * otherwise.
   *
   * @param pawn the pawn to render.
   * @return the image.
   */
  protected BufferedImage imageFromPawn(Pawn pawn) {
    Player player = pawn.player();
    player = player != null ? player : current;
    if (player == null) {
      return null;
    }

    if (player.equals(white)) {
      switch (pawn.type()) {
        case GOOD:
        return whiteGoodPawn;
        case EVIL:
        return whiteEvilPawn;
        case UNKNOWN:
        return whiteNeutralPawn;
      }
    } else if (player.equals(black)) {
      switch (pawn.type()) {
        case GOOD:
        return blackGoodPawn;
        case EVIL:
        return blackEvilPawn;
        case UNKNOWN:
        return blackNeutralPawn;
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
        return whiteGoodPawnCursor;
        case EVIL:
        return whiteEvilPawnCursor;
      }
    } else if (player.equals(black)) {
      switch (pawn.type()) {
        case GOOD:
        return blackGoodPawnCursor;
        case EVIL:
        return blackEvilPawnCursor;
      }
    }
    return null;
  }

  /**
   * Gets coordinates of the hovered square.
   *
   * @param size the size of a square, in pixels.
   * @return the corresponding coordinates.
   */
  protected Coordinates hoveredSquare(int size) {
    int mx = mouseX;
    int x = 0;
    while (mx > size) {
      mx -= size;
      x++;
    }

    int my = mouseY;
    int y = 0;
    while (my > size) {
      my -= size;
      y++;
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
}
