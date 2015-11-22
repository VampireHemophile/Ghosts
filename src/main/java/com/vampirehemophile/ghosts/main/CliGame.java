package com.vampirehemophile.ghosts.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

import com.vampirehemophile.ghosts.entities.*;
import com.vampirehemophile.ghosts.exceptions.BoardTooSmallException;
import com.vampirehemophile.ghosts.exceptions.InvalidCoordinatesException;
import com.vampirehemophile.ghosts.exceptions.OutOfBoardCoordinatesException;
import com.vampirehemophile.ghosts.exceptions.FreeSquareException;
import com.vampirehemophile.ghosts.managers.BoardManager;
import com.vampirehemophile.ghosts.math.Coordinates;
import static com.vampirehemophile.ghosts.util.PawnFactory.makePawn;

public class CliGame {

  /** White player. */
  private Player white;

  /** Black player. */
  private Player black;

  /** Game board manager. */
  private BoardManager boardManager;


  /** Constructs a game in CLI. */
  public CliGame() {
    white = new Player('o');
    black = new Player('x');
  }

  /** Starts the game. */
  public void start() {
    boardManager = new BoardManager(white, black, 6);

    askPawns(white);
    askPawns(black);

    boolean hasWon = false;
    boolean hasLost = false;
    Player current = black;
    do {
      current = boardManager.opponent(current);
      render(current);
      askMove(current);
      render(current);
      try { Thread.sleep(3000); } catch (Exception expected) {}
      clearScreen();
      hasWon = boardManager.hasWon(current);
      hasLost = boardManager.hasLost(current);
    } while (!hasWon && !hasLost);

    if (hasWon) {
      System.out.println(((current == white) ? "White" : "Black") + " wins !");
    } else if (hasLost) {
      System.out.println(((current == white) ? "Black" : "White") + " wins !");
    }
  }

  /**
   * Asks the Board size to the player.
   *
   * @return board size.
   */
  private int askBoardSize() {
    int size = 6;
    String line = null;
    boolean keepAsking = true;
    BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));

    while (keepAsking) {
      System.out.print("Board size (>=6) [6]: ");
      try {
        line = buf.readLine();
      } catch (IOException expected) {};
      if (line.isEmpty()) {
        size = 6;
        keepAsking = false;
      } else {
        try {
          size = Integer.parseInt(line);
          try {
            new Coordinates(0, 0, size);
            keepAsking = false;
          } catch (BoardTooSmallException expected) {}
        } catch (NumberFormatException expected) {}
      }
    }

    return size;
  }

  /**
   * Ask a player to set its pawns.
   *
   * @param player the player.
   */
  private void askPawns(Player player) {
    if (player == null) {
      throw new NullPointerException();
    }

    Board board = boardManager.board();
    GoodPawn g = new GoodPawn();
    EvilPawn e = new EvilPawn();

    StringBuilder question = new StringBuilder();
    StringBuilder end = new StringBuilder();
    end.append(" (");
    end.append(g.charIcon());
    end.append("|");
    end.append(e.charIcon());
    end.append("): ");

    Coordinates c = null;
    int size = board.size();
    boolean isWhite = player == white;
    int y = (isWhite) ? 0 : size - 1;
    int endY = (isWhite) ? 2 : size - 3;
    int incY = (isWhite) ? 1 : -1;
    do {
      for (int x = 1; x < size - 1; x++) {
        c = new Coordinates(x, y, board.size());

        question.append("Pawn at ");
        question.append(c.x());
        question.append(":");
        question.append(c.y());
        question.append(end);

        clearScreen();
        render(player);
        Pawn pawn = askPawn(question.toString(), player);
        board.set(pawn, c);
        question.delete(0, question.length());
      }

      y += incY;
    } while (y != endY);
  }

  /**
   * Aks the player to set a specific pawn.
   *
   * @param q printed message.
   * @param player the player.
   * @return created pawn.
   */
  private Pawn askPawn(String q, Player player) {
    Pawn pawn = null;
    char pawnChar = '\0';
    String line = null;
    boolean keepAsking = true;
    Scanner sc = new Scanner(System.in);

    while (keepAsking) {
      System.out.print(q);
      if (sc.hasNext()) {
        line = sc.next();
        if (line.length() == 1) {
          pawn = makePawn(player, line.charAt(0));
          keepAsking = pawn == null;
        }
      }
    }

    return pawn;
  }

  private void askMove(Player player) {
    String line = null;
    String[] coords = null;
    Coordinates start = null;
    Coordinates end = null;
    boolean keepAsking = true;
    Scanner sc = new Scanner(System.in);

    while (keepAsking) {
      System.out.print("Move pawn from ij to xy [ij xy]: ");
      if (sc.hasNextLine()) {
        line = sc.nextLine();
        coords = line.split(" ");

        if (coords.length == 2) {
          try {
            start = new Coordinates(coords[0], boardManager.size());
            keepAsking = false;
          } catch (InvalidCoordinatesException e) {
            System.out.println(e.getMessage());
          } catch (OutOfBoardCoordinatesException e) {
            System.out.println(e.getMessage());
          }
          try {
            end = new Coordinates(coords[1], boardManager.size());
          } catch (InvalidCoordinatesException e) {
            System.out.println(e.getMessage());
            keepAsking = true;
          } catch (OutOfBoardCoordinatesException e) {
            System.out.println(e.getMessage());
            keepAsking = true;
          }

          try {
            boolean canMove = boardManager.canMove(start, end);
            keepAsking = !((!keepAsking) && canMove);
          } catch (FreeSquareException e) {
            System.out.println(e.getMessage());
            keepAsking = true;
          }
        } else {
          System.out.println("No valid coordinates found.");
        }
      }
    }

    boardManager.move(start, end);
  }

  /** Clears the screen with ANSI CLEAR sequence. */
  private void clearScreen() {
    System.out.print("\033[H\033[2J");
  }

  /**
   * Prints the Board to the CLI.
   *
   * @param player Player to render the board for.
   */
  private void render(Player player) {
    Board board = boardManager.board();
    int size = board.size();

    int headOffsetSize = 1;
    int headOffsetSizeNumber = size + 1;
    while (headOffsetSizeNumber >= 10) {
      headOffsetSizeNumber /= 10;
      headOffsetSize++;
    }
    StringBuilder sbOffset = new StringBuilder(size / 10 + 1);
    for (int i = 0; i <= headOffsetSize; i++) {
      sbOffset.append(" ");
    }

    StringBuilder sbColumnsHeader = new StringBuilder(size + sbOffset.length() + 2);
    sbColumnsHeader.append(sbOffset);
    sbColumnsHeader.append(" ");
    for (char x = 'a'; x < size + 'a'; x++) {
      sbColumnsHeader.append(x);
    }
    sbColumnsHeader.append("\n");

    StringBuilder sbColumnsHyphens = new StringBuilder(size + sbOffset.length() + 3);
    sbColumnsHyphens.append(sbOffset);
    sbColumnsHyphens.append("+");
    for (int i = 0; i < size; i++) {
      sbColumnsHyphens.append("-");
    }
    sbColumnsHyphens.append("+\n");

    StringBuilder sbBoard = new StringBuilder((size + sbOffset.length() * 2 + 3) * size);
    Pawn pawn = null;

    int offsetSize = 0;
    int offsetMaxSize = 1;
    int yNumber = 0;
    int sizeNumber = size;
    while(sizeNumber >= 10) {
      sizeNumber /= 10;
      offsetMaxSize++;
    }

    boolean isWhite = player == white;
    int y = isWhite ? size - 1 : 0;
    int endY = isWhite ? -1 : size;
    int incY = isWhite ? -1 : 1;
    do {
      offsetSize = 1;
      yNumber = y + 1;
      while (yNumber >= 10) {
        yNumber /= 10;
        offsetSize++;
      }

      for (int i = 0; i < offsetMaxSize - offsetSize; i++) {
        sbBoard.append(" ");
      }
      sbBoard.append(y + 1);
      sbBoard.append(" |");
      for (int x = 0; x < size; x++) {
        pawn = board.at(new Coordinates(x, y, size));
        if (pawn == null) {
          if ((y == 0 || y == size - 1) && (x == 0 || x ==  size - 1)) {
            sbBoard.append("#");
          } else {
            sbBoard.append(".");
          }
        } else if (pawn.player() == player) {
          if (pawn.player() == white) {
            sbBoard.append((char)27 + "[1;37m");
          } else {
            sbBoard.append((char)27 + "[1;30m");
          }
          sbBoard.append(pawn.charIcon());
        } else {
          if (pawn.player() == white) {
            sbBoard.append((char)27 + "[1;37m");
          } else {
            sbBoard.append((char)27 + "[1;30m");
          }
          sbBoard.append(Pawn.defaultCharIcon);
        }
        sbBoard.append((char)27 + "[0m");
      }
      sbBoard.append("| ");
      sbBoard.append(y + 1);
      sbBoard.append("\n");

      y += incY;
    } while (y != endY);

    StringBuilder sb = new StringBuilder(2 * (sbColumnsHeader.length() + sbColumnsHyphens.length())
                                         + sbBoard.length());
    sb.append(sbColumnsHeader);
    sb.append(sbColumnsHyphens);
    sb.append(sbBoard);
    sb.append(sbColumnsHyphens);
    sb.append(sbColumnsHeader);

    System.out.println(sb.toString());
  }
}
