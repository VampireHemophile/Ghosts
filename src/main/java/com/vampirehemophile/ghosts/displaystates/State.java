package com.vampirehemophile.ghosts.displaystates;

import java.util.Observable;

import javax.swing.JPanel;


/**
 * Provides an abstract class for game states.
 * A state holds a JPanel object that is added to the main frame. It is also
 * responsible for catching events that may be fired from this panel, and can
 * implement listeners.
 * A state is also made observable. It notifies its observers when the game
 * switches to a new state, and sends the new state.
 */
public abstract class State extends Observable {

  /**
   * Create a panel to set as default content pane for the main frame.
   *
   * @return the panel.
   */
  public abstract JPanel render();
}
