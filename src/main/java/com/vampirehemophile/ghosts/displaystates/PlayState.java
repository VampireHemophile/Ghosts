package com.vampirehemophile.ghosts.displaystates;

import javax.swing.JPanel;
import java.util.Stack;
import java.util.Observable;
import java.util.Observer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import com.vampirehemophile.ghosts.playstates.*;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.entities.Player;

/** Main game state. */
public class PlayState extends State implements Observer {

  /** main game state's panel. */
  @SuppressWarnings("serial")
  private class PlayPanel extends JPanel {

    /** Constructs a PlayPanel object. */
    public PlayPanel() {
      super();
      setPreferredSize(new Dimension(600, 620));
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;

      PlayState.this.states.peek().paint(g2d);
    }

    /**
     * Adds a game state as listener.
     *
     * @param state the state.
     */
    public void addStateListener(GameState state) {
      addMouseListener((MouseListener)state);
      addMouseMotionListener((MouseMotionListener)state);
    }

    /**
     * Removes a game state as listener.
     *
     * @param state the state.
     */
    public void removeStateListener(GameState state) {
      removeMouseListener((MouseListener)state);
      removeMouseMotionListener((MouseMotionListener)state);
    }
  }


  /** states panel. */
  private PlayPanel panel;

  private Stack<GameState> states;

  private BoardManager bm;


  /** Constructs a PlayState object. */
  public PlayState() {
    super();

    panel = new PlayPanel();

    bm = new BoardManager(new Player(), new Player());

    states = new Stack<>();
    GameState setupState = new SetupState(panel, bm);
    states.push(setupState);
    panel.addStateListener(setupState);
    setupState.addObserver(this);
  }

  /** {@inheritDoc} */
  @Override
  public JPanel render() {
    return panel;
  }

  /**
   * Switches between two game states. Updates the main frame.
   *
   * @param o the last state.
   * @param arg the state to switch to.
   */
  @Override
  public void update(Observable o, Object arg) {
    GameState state = states.peek();
    GameState newState = (GameState)arg;
    if (newState == null) {
      panel.removeStateListener(state);
      state.deleteObserver(this);
      state.exit();
      states.pop();

      state = states.peek();
      panel.addStateListener(state);
      state.addObserver(this);
      state.enter();
    } else {
      panel.removeStateListener(state);
      state.deleteObserver(this);
      state.exit();

      states.push(newState);
      panel.addStateListener(newState);
      newState.addObserver(this);
      newState.enter();
    }
  }
}
