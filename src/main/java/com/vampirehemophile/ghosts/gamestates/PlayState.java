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

/** Main game state. */
public class PlayState extends State implements MouseInputListener {

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


    /** Constructs a PlayPanel object. */
    public PlayPanel() throws IOException {
      super();
      setPreferredSize(new Dimension(600, 620));
      eventQueue = new LinkedList<>();

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

      // Draw background
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

      // Process mouse events
      MouseEvent e = eventQueue.poll();
      String type = null;
      while (e != null) {
        switch (e.getID()) {
          case MouseEvent.MOUSE_PRESSED:
          type = "MOUSE_PRESSED";
          break;
          case MouseEvent.MOUSE_RELEASED:
          type = "MOUSE_RELEASED";
          break;
          case MouseEvent.MOUSE_CLICKED:
          type = "MOUSE_CLICKED";
          break;
          case MouseEvent.MOUSE_ENTERED:
          type = "MOUSE_ENTERED";
          break;
          case MouseEvent.MOUSE_EXITED:
          type = "MOUSE_EXITED";
          break;
          case MouseEvent.MOUSE_DRAGGED:
          type = "MOUSE_DRAGGED";
          break;
          case MouseEvent.MOUSE_MOVED:
          type = "MOUSE_MOVED";
          g2d.drawImage(whiteGoodPawn, e.getX() - 12, e.getY() - 25, 25, 50, null);
          break;
        }
        drawString(type, g2d);
        e = eventQueue.poll();
      }
    }

    /**
      * Draw a message.
      *
      * @param string the message.
      * @param g2d the graphics object.
      */
    private void drawString(String string, Graphics2D g2d) {
      g2d.drawString(string, 10, 615);
    }
  }


  /** states panel. */
  private PlayPanel panel;


  /** Constructs a PlayState object. */
  public PlayState() {
    super();

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
