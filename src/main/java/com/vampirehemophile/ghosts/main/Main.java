package com.vampirehemophile.ghosts.main;

public class Main {
  public static void main(String[] args) {
    boolean help = false;
    boolean cli = false;

    for (String opt : args) {
      if (opt.equals("-h") || opt.equals("--help")) {
        help = true;
        break;
      } else if (opt.equals("--nogui")) {
        cli = true;
      } else {
        System.err.println("Unrecognized option.");
        System.exit(1);
      }
    }

    if (help) {
      System.out.println("Ghosts options:\n    -h --help\n    --nogui");
    } else if (cli) {
      CliGame cg = new CliGame();
      cg.start();
    }
  }
}
