package com.vampirehemophile.ghosts.main;

/** Main starting class. */
public class Main {

  /** Game window width. */
  public static int WINDOW_WIDTH = 500;

  /** Game windows height. */
  public static int WINDOW_HEIGHT = 500;


  /**
   * Ghosts point of entry.
   *
   * @param args console arguments.
   */
  public static void main(String[] args) {

    boolean help = false;
    boolean gui = true;

    for (String opt : args) {
      if (opt.equals("-h") || opt.equals("--help")) {
        help = true;
        break;
      } else if (opt.equals("--nogui")) {
        gui = false;
      } else {
        System.err.println("Unrecognized option.");
        System.exit(1);
      }
    }

    if (help) {
      System.out.println("Ghosts options:\n    -h --help\n    --nogui");
    } else if (gui) {
      GuiGame game = new GuiGame("Game Title !", WINDOW_WIDTH, WINDOW_HEIGHT);
      game.start();
    } else {
      CliGame game = new CliGame();
      game.start();
    }
  }
}
