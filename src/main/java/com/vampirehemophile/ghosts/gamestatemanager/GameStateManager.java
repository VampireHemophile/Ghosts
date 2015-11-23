package com.vampirehemophile.ghosts.gamestatemanager;

import java.util.Stack;

import com.vampirehemophile.ghosts.managers.KeyManager;

/**
 * GameStateManager class.
 */
public class GameStateManager {

  private Stack<State> states;
  private KeyManager km;

  /**
   * By default, the first State is the menu.
   *
   * @param km the key manager.
   */
  public GameStateManager(KeyManager km) {

    this.km = km;
    this.states = new Stack<State>();
    this.states.push(new MenuState(this, km));

    // test
    // this.states.push(new PlayState(6));
  }


  /**
   * currentState.
   *
   * @return the state currently on top of the GameStateManager Stack.
   */
  public State currentState() {
    return this.states.peek();
  }

  /**
   * Pushes a new State to the Stack.
   *
   * @param state the state.
   */
  public void add(State state) {
    this.states.push(state);
  }

  /**
   * Pops the current State.
   */
  public void removeState() {
    this.states.pop();
  }
}
