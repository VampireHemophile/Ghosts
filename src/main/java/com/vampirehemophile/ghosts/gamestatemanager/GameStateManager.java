package com.vampirehemophile.ghosts.gamestatemanager;

import java.util.Stack;

public class GameStateManager {

  private Stack<State> states;

  /**
  * By default, the first State is the menu.
  */

  public GameStateManager() {
    this.states = new Stack<State>();
    this.states.push(new MenuState());
  }


  /**
  *
  * @return the state currently on top of the GameStateManager Stack
  *
  */
  public State currentState() {
    return this.states.peek();
  }

}
