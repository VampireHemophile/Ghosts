package com.vampirehemophile.ghosts.main;

import com.vampirehemophile.ghosts.entities.Board;
import com.vampirehemophile.ghosts.entities.Pawn;
import com.vampirehemophile.ghosts.math.Coordinates;


public class CliGame {
  public void start() {

  }

  public void render(Board board) {

    int size = board.size();

    StringBuilder sbOffset = new StringBuilder(size / 10);
    for (int i = 0; i <= size / 10; i++) {
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
    Pawn p = null;

    int offsetSize = 0;
    int offsetMaxSize = 1;
    int yNumber = 0;
    int sizeNumber = size;
    while(sizeNumber >= 10) {
      sizeNumber /= 10;
      offsetMaxSize++;
    }

    for (int y = 0; y < size; y++) {

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
        p = board.at(new Coordinates(x, y, size));
        if (p == null) {
          // sbBoard.append((char)27 + "[XXm");
          if ((y == 0 || y == size - 1) && (x == 0 || x ==  size - 1)) {
            sbBoard.append("#");
          } else {
            sbBoard.append(".");
          }
        } else {
          sbBoard.append(p.charIcon());
        }
        // sbBoard.append((char)27 + "[0m");
      }
      sbBoard.append("| ");
      sbBoard.append(y + 1);
      sbBoard.append("\n");
    }

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
