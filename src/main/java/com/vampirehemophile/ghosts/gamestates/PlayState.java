package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.event.MouseInputListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
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

    // mouse event queue
    public Queue<MouseEvent> eventQueue;

    private PlayState parent;
    private Board board;

    private int mouseX;
    private int mouseY;


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
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;

      drawBoard(g2d);

      // Process mouse events
      MouseEvent e = eventQueue.poll();
      String message = null;
      while (e != null) {
        switch (e.getID()) {
          case MouseEvent.MOUSE_PRESSED:
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
          } else if (pawn.player().equals(white)) {
            switch (pawn.type()) {
              case GOOD:
              g2d.drawImage(whiteGoodPawn, x*100 + 25 + 12, y*100 + 25, 25, 50, null);
              break;
              case EVIL:
              g2d.drawImage(whiteEvilPawn, x*100 + 25 + 12, y*100 + 25, 25, 50, null);
              break;
              case UNKNOWN:
              g2d.drawImage(whiteNeutralPawn, x*100 + 25 + 12, y*100 + 25, 25, 50, null);
              break;
            }
          } else if (pawn.player().equals(black)) {
            switch (pawn.type()) {
              case GOOD:
              g2d.drawImage(blackGoodPawn, x*100 + 25 + 12, y*100 + 25, 25, 50, null);
              break;
              case EVIL:
              g2d.drawImage(blackEvilPawn, x*100 + 25 + 12, y*100 + 25, 25, 50, null);
              break;
              case UNKNOWN:
              g2d.drawImage(blackNeutralPawn, x*100 + 25 + 12, y*100 + 25, 25, 50, null);
              break;
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
      * @param string the message.
      */
    protected void drawString(Graphics2D g2d, String string) {
      g2d.drawString(string != null ? string : "", 10, 615);
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
