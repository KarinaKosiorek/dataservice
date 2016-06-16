package xformation.minesweeper;

public class MineSweeperImplementation implements MineSweeper {

  private String[][] hintField = null;
  private String[][] mineField = null;
  private int N = 0;
  private int M = 0;

  private final static String MINE_SQUARE = "*";
  private final static String NO_MINE_SQUARE = ".";
  private final static int HINT_AREA = 1;

  public void setMineField(String mineField) throws IllegalArgumentException {
    try {
      if (mineField == null) {
        throw new IllegalArgumentException("Input argument mineField is null.");
      }

      String[] mineFieldRows = mineField.split("\n");

      // setting field dimensions
      N = mineFieldRows.length;
      M = mineFieldRows.length == 0 ? 0 : mineFieldRows[0].length();

      // creating helper array with copied field squares
      this.mineField = new String[N][M];

      for (int n = 0; n < N; n++) {

        if (mineFieldRows[n].length() != M) {
          throw new IllegalArgumentException("Wrong length of row with index = " + n + " in mine field square:\n" + mineField
              + "\nLength is " + mineFieldRows[n].length() + " but should be " + M);
        }

        for (int m = 0; m < M; m++) {
          String square = mineFieldRows[n].substring(m, m + 1);
          if (isMineSquare(square) || isNoMineSquare(square)) {
            this.mineField[n][m] = square;
          } else {
            throw new IllegalArgumentException("Wrong square character \"" + square + "\" at position [" + n + ", " + m
                + "] in mine field square:\n" + mineField);
          }
        }
      }

      // copying mineField array to build-result array
      hintField = new String[N][M];
      for (int n = 0; n < N; n++) {
        for (int m = 0; m < M; m++) {
          hintField[n][m] = this.mineField[n][m];
        }
      }

      // debug
      // System.out.println("mineField:");
      // for (int n = 0; n < N; n++) {
      // for (int m = 0; m < M; m++) {
      // System.out.print(this.mineField[n][m]);
      // }
      // System.out.println();
      // }
      // System.out.println("hintField:");
      // for (int n = 0; n < N; n++) {
      // for (int m = 0; m < M; m++) {
      // System.out.print(this.hintField[n][m]);
      // }
      // System.out.println();
      // }
    } catch (IllegalArgumentException e) {
      this.mineField = null;
      this.hintField = null;
      throw e;
    }
  }

  public String getHintField() throws IllegalStateException {
    if (mineField == null || hintField == null) {
      throw new IllegalStateException();
    }
    // generating result
    for (int n = 0; n < N; n++) {
      for (int m = 0; m < M; m++) {
        if (isNoMineSquare(mineField[n][m])) {
          setHintSquare(n, m);
        }
      }
    }
    // converting result
    StringBuilder resultBuilder = new StringBuilder();
    for (int n = 0; n < N; n++) {
      for (int m = 0; m < M; m++) {
        resultBuilder.append(hintField[n][m]);
      }
      resultBuilder.append("\n");
    }
    // deleting last '\n' character
    if (resultBuilder.length() > 0) {
      resultBuilder.deleteCharAt(resultBuilder.length() - 1);
    }
    // returning result in String
    return resultBuilder.toString();
  }

  private void setHintSquare(int n, int m) {
    int fromBound = 0 - HINT_AREA;
    int toBound = 0 + HINT_AREA;
  
    int result = 0;
    for (int nshift = fromBound; nshift <= toBound; nshift++) {
      for (int mshift = fromBound; mshift <= toBound; mshift++) {
        int npos = n + nshift;
        int mpos = m + mshift;
        if (npos >= 0 && npos < N && mpos >= 0 && mpos < M) {
          if (isMineSquare(mineField[npos][mpos])) {
            result++;
          }
        }
      }
    }
    hintField[n][m] = Integer.toString(result);
  }

  private boolean isMineSquare(String value) {
    return value.equals(MINE_SQUARE);
  }

  private boolean isNoMineSquare(String value) {
    return value.equals(NO_MINE_SQUARE);
  }
}
