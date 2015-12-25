package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.event.MouseInputListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.math.Coordinates;

/** Main game state. */
public class PlayState extends State implements MouseInputListener {

  /** game state. */
  private enum GameState {
    SETUP, PLAY
  }

  /** main game state's panel. */
  @SuppressWarnings("serial")
  private class PlayPanel extends JPanel {

    // images
    private BufferedImage lightTile;
    private BufferedImage darkTile;
    private BufferedImage whiteGoodPawn;
    private BufferedImage whiteEvilPawn;
    private BufferedImage whiteNeutralPawn;
    private BufferedImage blackGoodPawn;
    private BufferedImage blackEvilPawn;
    private BufferedImage blackNeutralPawn;

    // cursors
    private Cursor whiteGoodPawnCursor;
    private Cursor whiteEvilPawnCursor;
    private Cursor blackGoodPawnCursor;
    private Cursor blackEvilPawnCursor;

    // mouse event queue
    public Queue<MouseEvent> eventQueue;

    private PlayState parent;
    private Board board;

    private int mouseX;
    private int mouseY;

    private Pawn goodPawn;
    private Pawn evilPawn;
    private Pawn selectedPawn;


    /** Constructs a PlayPanel object. */
    public PlayPanel() throws IOException {
      super();
      setPreferredSize(new Dimension(600, 620));
      eventQueue = new LinkedList<>();

      parent = PlayState.this;
      board = parent.bm.board();

      lightTile        = ImageIO.read(State.getResource("/images/lighttile.png"));
      darkTile         = ImageIO.read(State.getResource("/images/darktile.png"));
      whiteGoodPawn    = ImageIO.read(State.getResource("/images/white/goodpawn.png"));
      whiteEvilPawn    = ImageIO.read(State.getResource("/images/white/evilpawn.png"));
      whiteNeutralPawn = ImageIO.read(State.getResource("/images/white/neutralpawn.png"));
      blackGoodPawn    = ImageIO.read(State.getResource("/images/black/goodpawn.png"));
      blackEvilPawn    = ImageIO.read(State.getResource("/images/black/evilpawn.png"));
      blackNeutralPawn = ImageIO.read(State.getResource("/images/black/neutralpawn.png"));

      whiteGoodPawnCursor = loadCursor(whiteGoodPawn);
      whiteEvilPawnCursor = loadCursor(whiteEvilPawn);
      blackGoodPawnCursor = loadCursor(blackGoodPawn);
      blackEvilPawnCursor = loadCursor(blackEvilPawn);

      goodPawn = new Pawn(Pawn.PawnType.GOOD);
      evilPawn = new Pawn(Pawn.PawnType.EVIL);
    }

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

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;

      drawBoard(g2d);

      if (parent.state == GameState.SETUP) {
        if (selectedPawn == null) {
          selectedPawn = goodPawn;
          if (parent.current.equals(parent.white)) {
            setCursor(whiteGoodPawnCursor);
          } else if (parent.current.equals(parent.black)) {
            setCursor(blackGoodPawnCursor);
          }
        }
      }

      // Process mouse events
      MouseEvent e = eventQueue.poll();
      String message = null;
      while (e != null) {
        switch (e.getID()) {

          case MouseEvent.MOUSE_PRESSED:
          if (parent.state == GameState.SETUP) {
            switch (e.getButton()) {
              case MouseEvent.BUTTON1:
              Coordinates loc = hoveredSquare(100);
              if (!parent.bm.canSet(parent.current, loc)) {
                break;
              }
              parent.current.add(selectedPawn);
              board.set(selectedPawn, loc);
              if (selectedPawn == goodPawn) {
                goodPawn = new Pawn(Pawn.PawnType.GOOD);
                selectedPawn = goodPawn;
              } else if (selectedPawn == evilPawn) {
                evilPawn = new Pawn(Pawn.PawnType.EVIL);
                selectedPawn = evilPawn;
              }
              int good = parent.current.countGoodPawns();
              int evil = parent.current.countEvilPawns();
              if (good == 4 && evil == 4) {
                if (parent.current.equals(parent.white)) {
                  parent.current = parent.black;
                  selectedPawn = null;
                  repaint();
                } else if (parent.current.equals(parent.black)) {
                  parent.current = parent.white;
                  parent.state = GameState.PLAY;
                  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
              } else if (good == 4) {
                selectedPawn = evilPawn;
                setCursor(cursorFromPawn(selectedPawn));
              } else if (evil == 4) {
                selectedPawn = goodPawn;
                setCursor(cursorFromPawn(selectedPawn));
              }
              break;
              case MouseEvent.BUTTON3:
              if (parent.current.countGoodPawns() < 4
                  && parent.current.countEvilPawns() < 4) {
                selectedPawn = selectedPawn == goodPawn ? evilPawn : goodPawn;
                setCursor(cursorFromPawn(selectedPawn));
              }
              break;
            }
          }
          break;

          case MouseEvent.MOUSE_RELEASED:
          break;

          case MouseEvent.MOUSE_CLICKED:
          break;

          case MouseEvent.MOUSE_ENTERED:
          break;

          case MouseEvent.MOUSE_EXITED:
          break;

          case MouseEvent.MOUSE_DRAGGED:
          mouseX = e.getX();
          mouseY = e.getY();
          break;

          case MouseEvent.MOUSE_MOVED:
          mouseX = e.getX();
          mouseY = e.getY();
          break;
        }
        drawString(g2d, message);
        e = eventQueue.poll();
      }

      drawPawns(g2d);
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
          if (pawn == null) {
          } else {
            g2d.drawImage(imageFromPawn(pawn), x*100 + 25 + 12, y*100 + 25, 25, 50, null);
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
      * @param string the message.
      */
    protected void drawString(Graphics2D g2d, String string) {
      g2d.drawString(string != null ? string : "", 10, 615);
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
      player = player != null ? player : parent.current;
      if (player == null) {
        return null;
      }

      if (player.equals(parent.white)) {
        switch (pawn.type()) {
          case GOOD:
          return whiteGoodPawn;
          case EVIL:
          return whiteEvilPawn;
          case UNKNOWN:
          return whiteNeutralPawn;
        }
      } else if (player.equals(parent.black)) {
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
      player = player != null ? player : parent.current;
      if (player == null) {
        return null;
      }

      if (player.equals(parent.white)) {
        switch (pawn.type()) {
          case GOOD:
          return whiteGoodPawnCursor;
          case EVIL:
          return whiteEvilPawnCursor;
        }
      } else if (player.equals(parent.black)) {
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
    private Coordinates hoveredSquare(int size) {
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
  }


  /** states panel. */
  private PlayPanel panel;

  private GameState state;

  private BoardManager bm;

  private Player white;
  private Player black;
  private Player current;


  /** Constructs a PlayState object. */
  public PlayState() {
    super();

    state = GameState.SETUP;

    white = new Player();
    black = new Player();
    current = white;
    bm = new BoardManager(white, black);

    Board board = bm.board();

    try {
      panel = new PlayPanel();
      panel.addMouseListener(this);
      panel.addMouseMotionListener(this);
    } catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  /** {@inheritDoc} */
  @Override
  public JPanel render() {
    return panel;
  }


  // Mouse event handling

  /**
   * Push mouse event to the panel event queue.
   *
   * @param e an event.
   */
  public void pushEvent(MouseEvent e) {
    panel.eventQueue.add(e);
    panel.repaint();
  }

  /** {@inheritDoc} */
  @Override
  public void mouseDragged(MouseEvent e) {
    pushEvent(e);
  }

  /** {@inheritDoc} */
  @Override
  public void mouseMoved(MouseEvent e) {
    pushEvent(e);
  }

  /** {@inheritDoc} */
  @Override
  public void mouseClicked (MouseEvent e) {
    pushEvent(e);
  }

  /** {@inheritDoc} */
  @Override
  public void mouseEntered (MouseEvent e) {
    pushEvent(e);
  }

  /** {@inheritDoc} */
  @Override
  public void mouseExited (MouseEvent e) {
    pushEvent(e);
  }

  /** {@inheritDoc} */
  @Override
  public void mousePressed (MouseEvent e) {
    pushEvent(e);
  }

  /** {@inheritDoc} */
  @Override
  public void mouseReleased (MouseEvent e) {
    pushEvent(e);
  }
}
