package com.vampirehemophile.ghosts.displaystates;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JPanel;

import com.vampirehemophile.ghosts.entities.Pawn;
import com.vampirehemophile.ghosts.entities.Player;
import com.vampirehemophile.ghosts.gamestates.GameState;
import com.vampirehemophile.ghosts.gamestates.MainGameState;
import com.vampirehemophile.ghosts.gamestates.SetupState;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.math.Coordinates;

/** Main game state. */
public class PlayState extends State implements Observer {

  /** main game state's panel. */
  @SuppressWarnings("serial")
  private class PlayPanel extends JPanel {

    /** Constructs a PlayPanel object. */
    public PlayPanel() {
      super();
      setPreferredSize(new Dimension(600, 640));
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
      addKeyListener((KeyListener)state);
    }

    /**
     * Removes a game state as listener.
     *
     * @param state the state.
     */
    public void removeStateListener(GameState state) {
      removeMouseListener((MouseListener)state);
      removeMouseMotionListener((MouseMotionListener)state);
      removeKeyListener((KeyListener)state);
    }
  }


  /** states panel. */
  private PlayPanel panel;

  private Stack<GameState> states;

  private BoardManager bm;

  private boolean cheatModeEnabled;

  /** Constructs a PlayState object. */
  public PlayState(boolean cheatModeEnabled) {
    super();
    
    panel = new PlayPanel();

    bm = new BoardManager(new Player(), new Player());
    
    this.cheatModeEnabled = cheatModeEnabled;
    
    states = new Stack<>();
    GameState setupState = new SetupState(panel, bm);
    states.push(setupState);
    panel.addStateListener(setupState);
    if(cheatModeEnabled){
    	setupState.enableCheatMode();
    }
    setupState.addObserver(this);
  }
  
  private enum ReadingState {
	  SETUP, PLAY
  }
  
  /**
   * Constructs a PlayState object based on a pre existing game file
   */
  
  public PlayState(boolean cheatModeEnabled, File file) {
	  super();
	    
	  panel = new PlayPanel();

	  bm = new BoardManager(new Player(), new Player());
	    
	  this.cheatModeEnabled = cheatModeEnabled;
	  

	  ReadingState rs = ReadingState.SETUP;
	  Player current = null;
	  int setupLine = 0;
	  
	  try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				if (line.charAt(0) == '#') {
					if (line.equals("# Player1")) {
						current = bm.white();
						setupLine = 0;
					} else if (line.equals("# Player2")) {
						current = bm.black();
						setupLine = 0;
					} else if (line.equals("# Move")) {
						current = null;
						rs = ReadingState.PLAY;
					}
					continue;
				}
				switch (rs) {
				case SETUP:
					processSetup(line, current, setupLine);
					setupLine++;
				break;
				case PLAY:
					processPlay(line);
				break;
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	  
	    states = new Stack<>();
	    GameState setupState = new MainGameState(panel, bm);
	    states.push(setupState);
	    panel.addStateListener(setupState);
	    if(cheatModeEnabled){
	    	setupState.enableCheatMode();
	    }
	    setupState.addObserver(this);
  }
  
  
  public void processSetup(String line, Player current, int setupLine) {
	  int y = 0;
	  int x = 0;
	  if (current.equals(bm.white())) {
		  y = setupLine;
	  } else if (current.equals(bm.black())) {
		  y = bm.size() - setupLine - 1;
	  }
	  for (int i = 0; i < line.length(); i++) {
		  if (i % 2 == 1) continue;
		  x++;
		  char c = line.charAt(i);
		  Pawn p = null;
		  if (c == 'G') {
			  p = new Pawn(Pawn.PawnType.GOOD);
		  } else if (c == 'B') {
			  p = new Pawn(Pawn.PawnType.EVIL);
		  }
		  current.add(p);
		  bm.board().set(p, new Coordinates(x, y, bm.size()));
	  }
  }
  
  
  public void processPlay(String line) {
	  String[] tokens = line.split("\\s");
	  String[] tok1 = tokens[2].split(",");
	  String[] tok2 = tokens[4].split(",");
	  
	  Coordinates start = null;
	  Coordinates end = null;
	  
	  start = new Coordinates(tok1[0], bm.size());
	  end = new Coordinates(tok1[1], bm.size());
	  
	  if (bm.canMove(start, end)) {
		  bm.move(start, end);
	  } else {
		  throw new RuntimeException("Invalid file"); // TODO
	  }
	  
	  start = new Coordinates(tok2[0], bm.size());
	  end = new Coordinates(tok2[1], bm.size());
	  
	  if (bm.canMove(start, end)) {
		  bm.move(start, end);
	  } else {
		  throw new RuntimeException("Invalid file"); // TODO
	  }
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
      if(this.cheatModeEnabled)
    	  newState.enableCheatMode();
      newState.enter();
    }
  }
}
