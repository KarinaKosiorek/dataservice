package xformation.minesweeper;

public class App {

  public static void main(String[] args) {
    MineSweeper mineSweeperImplementation = new MineSweeperImplementation();
    String mineField = "*...\n..*.\n....";
    mineSweeperImplementation.setMineField(mineField);
    String result = mineSweeperImplementation.getHintField();
    System.out.println(result);
  }
}
