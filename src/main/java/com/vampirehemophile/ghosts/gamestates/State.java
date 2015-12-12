package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;
import java.util.Observable;

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

  /**
   * Finds a resource with a given name.
   *
   * @param name of the desired resource.
   * @return A java.net.URL object or null if no resource with this name is found.
   */
  public static java.net.URL getResource(String name) {
    return State.class.getResource(name);
  }
}
