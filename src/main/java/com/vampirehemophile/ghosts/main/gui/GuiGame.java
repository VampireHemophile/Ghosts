package com.vampirehemophile.ghosts.main.gui;

import java.util.Observer;
import java.util.Observable;
import com.vampirehemophile.ghosts.gamestates.State;
import com.vampirehemophile.ghosts.gamestates.MenuState;

/**
 * GuiGame class.
 */
public class GuiGame implements Observer {

  /** main frame. */
  private Display display;

  /** current state. */
  private State state;


  /**
   * Constructor for GuiGame.
   */
  public GuiGame() {
    display = new Display();
    state = new MenuState();
  }

  /**
   * Starts the game.
   */
  public void start() {
    state.addObserver(this);
    display.setContentPane(state.render());
    display.validate();
    display.pack();
    display.setLocationRelativeTo(null);
    display.setVisible(true);
  }

  /**
   * Switches between two game states. Updates the main frame.
   *
   * @param o the last state.
   * @param arg the state to switch to.
   */
  @Override
  public void update(Observable o, Object arg) {
    State newState = (State)arg;

    state.deleteObserver(this);
    newState.addObserver(this);

    display.setContentPane(newState.render());
    display.validate();
    display.pack();
    display.setLocationRelativeTo(null);
    state = newState;
  }
}
