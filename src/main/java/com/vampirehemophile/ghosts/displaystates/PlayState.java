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

import com.vampirehemophile.ghosts.assets.ImageLoader;
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

    /** The size in pixels of the message box at the bottom of the frame. */
    private static final int MESSAGE_BOX_HEIGHT = 40;

    /** Constructs a PlayPanel object. */
    PlayPanel() {
      super();
      int size = PlayState.this.bm.size() * ImageLoader.SQUARE_SIZE;
      setPreferredSize(new Dimension(size + ImageLoader.SQUARE_SIZE,
                                     size + MESSAGE_BOX_HEIGHT));
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(final Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;

      PlayState.this.states.peek().paint(g2d);
    }

    /**
     * Adds a game state as listener.
     *
     * @param state the state.
     */
    public void addStateListener(final GameState state) {
      addMouseListener((MouseListener) state);
      addMouseMotionListener((MouseMotionListener) state);
      addKeyListener((KeyListener) state);
    }

    /**
     * Removes a game state as listener.
     *
     * @param state the state.
     */
    public void removeStateListener(final GameState state) {
      removeMouseListener((MouseListener) state);
      removeMouseMotionListener((MouseMotionListener) state);
      removeKeyListener((KeyListener) state);
    }
  }


  /** states panel. */
  private PlayPanel panel;

  /** stack of states. */
  private Stack<GameState> states;

  /** board manager. */
  private BoardManager bm;

  /** If the cheat mode is enabled. */
  private boolean cheatModeEnabled;

  /**
   * Constructs a PlayState object.
   *
   * @param cheatModeEnabled Whether the cheat mode is enabled or not. If it is
   *     enabled, pawns won't be hidden.
   */
  public PlayState(final boolean cheatModeEnabled) {
    super();

    bm = new BoardManager(new Player(), new Player());

    panel = new PlayPanel();

    this.cheatModeEnabled = cheatModeEnabled;

    states = new Stack<>();
    GameState setupState = new SetupState(panel, bm);
    states.push(setupState);
    panel.addStateListener(setupState);
    if (cheatModeEnabled) {
      setupState.enableCheatMode();
    }
    setupState.addObserver(this);
    setupState.enter();
  }

  /** The state of the parsing of the file. */
  private enum ReadingState {

    /** Parsing is in setup. */
    SETUP,

    /** Parsing the pawns moves. */
    PLAY
  }

  /**
   * Constructs a PlayState object based on a pre existing game file.
   *
   * @param cheatModeEnabled Whether the cheat mode is enabled or not. If it is
   *     enabled, pawns won't be hidden.
   * @param file The file to read the game from.
   */
  public PlayState(final boolean cheatModeEnabled, final File file) {
    super();

    bm = new BoardManager(new Player(), new Player());

    panel = new PlayPanel();

    this.cheatModeEnabled = cheatModeEnabled;


    ReadingState rs = ReadingState.SETUP;
    Player current = null;
    int setupLine = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
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
        default:
          throw new RuntimeException();
        }
      }
    } catch (IOException e) {
        e.printStackTrace();
    }

    states = new Stack<>();
    GameState setupState = new MainGameState(panel, bm);
    states.push(setupState);
    panel.addStateListener(setupState);
    if (cheatModeEnabled) {
      setupState.enableCheatMode();
    }
    setupState.addObserver(this);
    setupState.enter();
  }

  /**
   * Sets the pawns on the board given string input.
   *
   * @param line The parsed line.
   * @param current The current playing player.
   * @param setupLine The number of the line currently parsed. It is 0-indexed,
   *     and is reseted when switching to the second player.
   */
  private void processSetup(final String line,
                            final Player current,
                            final int setupLine) {
    int y = 0;
    int x = 0;
    if (current.equals(bm.white())) {
      y = setupLine;
    } else if (current.equals(bm.black())) {
      y = bm.size() - setupLine - 1;
    }
    for (int i = 0; i < line.length(); i++) {
      if (i % 2 == 1) {
        continue;
      }
      x++;
      char c = line.charAt(i);
      Pawn p = null;
      if (c == 'G') {
        p = new Pawn(Pawn.PawnType.GOOD);
      } else if (c == 'B') {
        p = new Pawn(Pawn.PawnType.EVIL);
      }
      if (p != null) {
        current.add(p);
        bm.board().set(p, new Coordinates(x, y, bm.size()));
      }
    }
  }

  /** The location of the first movement token in the splitted string. */
  private static final int TOKEN1 = 2;

  /** The location of the second movement token in the splitted string. */
  private static final int TOKEN2 = 4;

  /**
   * Moves the pawns on the board given string input.
   *
   * @param line The parsed line.
   */
  private void processPlay(final String line) {
    String[] tokens = line.split("\\s");
    String[] tok1 = tokens[TOKEN1].split(",");
    String[] tok2 = tokens[TOKEN2].split(",");

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
  public void update(final Observable o, final Object arg) {
    GameState state = states.peek();
    GameState newState = (GameState) arg;
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
      if (this.cheatModeEnabled) {
        newState.enableCheatMode();
      }
      newState.enter();
    }
  }
}
