package edu.erciyes.project;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HexGame extends Application {
    private int size;
    private HexPolygon[][] board;
    private char[][] boardState;
    private char currentPlayer = 'X';
    private boolean isSwapAvailable;
    private int firstX = -1, firstY = -1;
    private Label statusLabel;
    private String player1Color;
    private String player2Color;

    public static void main(String[] args) {
        launch(args);
    }

    public void startGame(Stage primaryStage, int size, String player1Color, String player2Color, Boolean swapRule) {
        this.board = new HexPolygon[size][size];
        this.size = size;
        this.boardState = new char[size][size];
        this.isSwapAvailable =swapRule;
        this.player1Color = player1Color;
        this.player2Color = player2Color;


        Pane pane = new Pane();

        double hexRadius = 20;
        double hexHeight = Math.sqrt(3) * hexRadius;
        double hexWidth = 2 * hexRadius;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double x = j * hexWidth * 0.75;
                double y = i * hexHeight + (j % 2 == 0 ? 0 : hexHeight / 2);
                HexPolygon cell = createHexagon(hexRadius, i, j);
                cell.setTranslateX(x);
                cell.setTranslateY(y);
                board[i][j] = cell;
                boardState[i][j] = '.';
                pane.getChildren().add(cell);

            }
        }

        statusLabel = new Label("Player " + player1Color + " turn");
        statusLabel.setFont(Font.font("Arial Black",25));
        statusLabel.setAlignment(Pos.BOTTOM_CENTER);

        pane.getChildren().add(statusLabel);





        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setTitle("Hex Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equalsIgnoreCase("S") && currentPlayer == 'O' && isSwapAvailable) {
                swapFirstMove();
            }
        });
    }



    @Override
    public void start(Stage primaryStage) {

    }

    private HexPolygon createHexagon(double radius, int row, int col) {
        HexPolygon hexPolygon = new HexPolygon(radius);

        hexPolygon.setOnMouseClicked(event -> handleMove(row, col));

        return hexPolygon;
    }

    private void handleMove(int row, int col) {
        if (board[row][col].getFill() != Color.LIGHTGRAY) {
            return;
        }

        if (currentPlayer == 'X') {
            if(Objects.equals(player1Color, "Red")) {
                board[row][col].setFill(Color.RED);
            }
            else {
                board[row][col].setFill(Color.BLUE);
            }

            boardState[row][col] = 'X';
            if (firstX == -1 && firstY == -1) {
                firstX = row;
                firstY = col;
            }
            if (checkWin('X')) {
                statusLabel.setText("Player " + player1Color +" won!");
                disableBoard();
                return;
            }
            currentPlayer = 'O';
            statusLabel.setText("Player " + player2Color +" turn. ");
        } else {
            makeMove(row, col);
        }
    }

    private void makeMove(int row, int col) {
        if (board[row][col].getFill() != Color.LIGHTGRAY) {
            return; // Invalid move
        }

        if(player2Color.equals("Red")) {
            board[row][col].setFill(Color.RED);
        }
        else {
            board[row][col].setFill(Color.BLUE);
        }
        boardState[row][col] = 'O';
        if (checkWin('O')) {
            statusLabel.setText("Player "+ player2Color +" won!");
            disableBoard();
            return;
        }
        currentPlayer = 'X';
        statusLabel.setText("Player " + player1Color + " turn.");
    }

    private void swapFirstMove() {
        if (firstX != -1 && firstY != -1) {
            board[firstX][firstY].setFill(Color.LIGHTGRAY);
            boardState[firstX][firstY] = '.';
            isSwapAvailable = false;
            currentPlayer = 'O';
            statusLabel.setText("Player " + player2Color + " turn. Make a move.");
        }
    }

    private void disableBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].setOnMouseClicked(null);
            }
        }
    }

    private boolean checkWin(char player) {
        Set<Point2D> visited = new HashSet<>();
        if (player == 'X') {
            for (int row = 0; row < size; row++) {
                if (boardState[row][0] == 'X' && dfs(row, 0, 'X', visited)) {
                    return true;
                }
            }
        } else {
            for (int col = 0; col < size; col++) {
                if (boardState[0][col] == 'O' && dfs(0, col, 'O', visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(int row, int col, char player, Set<Point2D> visited) {
        if ((player == 'X' && col == size - 1) || (player == 'O' && row == size - 1)) {
            return true;
        }

        visited.add(new Point2D(row, col));

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValid(newRow, newCol) && boardState[newRow][newCol] == player && !visited.contains(new Point2D(newRow, newCol))) {
                if (dfs(newRow, newCol, player, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
}
