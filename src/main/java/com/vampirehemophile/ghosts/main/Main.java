package com.vampirehemophile.ghosts.main;

import javax.swing.SwingUtilities;

/**
 * Main starting class.
 */
public class Main {

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
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          GuiGame game = new GuiGame();
          game.start();
        }
      });
    } else {
      CliGame game = new CliGame();
      game.start();
    }
  }
}
