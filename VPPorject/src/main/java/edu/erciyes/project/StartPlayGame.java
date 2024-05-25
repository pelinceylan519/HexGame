package edu.erciyes.project;

import javafx.application.Application;
import javafx. scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartPlayGame extends Application {

    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(5);

        Label lbltitle = new Label("SELECT OPTIONS:");
        lbltitle.setFont(Font.font("Arial", 25));
        pane.add(lbltitle, 20, 8);

        Label lblplayer1 = new Label("Player 1 is: ");
        lblplayer1.setFont(Font.font("Times New Roman", 20));
        pane.add(lblplayer1, 20, 14);
        ToggleGroup colorGroup = new ToggleGroup();
        RadioButton red = new RadioButton("Red");
        red.setToggleGroup(colorGroup);
        RadioButton blue = new RadioButton("Blue");
        blue.setToggleGroup(colorGroup);

        HBox colorbox = new HBox(red, blue);
        pane.add(colorbox, 21, 14);

        Label lblplayer2 = new Label("Player 2 is: ");
        lblplayer2.setFont(Font.font("Times New Roman", 20));
        pane.add(lblplayer2, 20, 16);
        ToggleGroup colorGroup2 = new ToggleGroup();
        RadioButton red2 = new RadioButton("Red");
        red2.setToggleGroup(colorGroup2);
        RadioButton blue2 = new RadioButton("Blue");
        blue2.setToggleGroup(colorGroup2);
        HBox colorbox2 = new HBox(red2, blue2);
        pane.add(colorbox2, 21, 16);

        Label lblSwap = new Label("Swap Rule: ");
        lblSwap.setFont(Font.font("Times New Roman", 20));
        pane.add(lblSwap, 20, 18);
        ToggleGroup onOfGroup = new ToggleGroup();
        RadioButton on = new RadioButton("On");
        on.setToggleGroup(onOfGroup);
        RadioButton off = new RadioButton("Off");
        off.setToggleGroup(onOfGroup);
        HBox onOfBox = new HBox(on, off);
        pane.add(onOfBox, 21, 18);
        Label lblSwap2 = new Label(" You can use this rule " +
                "by pressing the S key" +
                " when you click on. ");
        lblSwap2.setFont(Font.font("Times New Roman", 15));
        pane.add(lblSwap2, 20, 20);

        Label lblBoardSize = new Label("Hex Board Size: ");
        lblBoardSize.setFont(Font.font("Times New Roman", 20));
        pane.add(lblBoardSize, 20, 24);
        ComboBox<String> cmbBoardSize = new ComboBox<>();
        cmbBoardSize.getItems().addAll(
                "5 x 5",
                "11 x 11",
                "17 x 17"
        );
        cmbBoardSize.setPromptText("Select Hex Board Size");
        pane.add(cmbBoardSize, 21, 24);

        Button btnStart = new Button("START GAME");
        btnStart.setFont(Font.font("Arial", 25));
        btnStart.setOnAction(e -> {
            String player1Color = red.isSelected() ? "Red" : "Blue";
            String player2Color = red2.isSelected() ? "Red" : "Blue";
            boolean swapRule = on.isSelected();
            int boardSize = Integer.parseInt(cmbBoardSize.getValue().split(" ")[0]); // Extracting board size
            startHexGame(player1Color, player2Color, swapRule, boardSize);
        });
        pane.add(btnStart, 20, 35);

        Scene scene = new Scene(pane, 800, 500);
        stage.setTitle("Setting Options");
        stage.setScene(scene);
        stage.show();

        this.primaryStage = stage;
    }

    private void startHexGame(String player1Color, String player2Color, Boolean swapRule, int boardSize) {
        Stage hexStage = new Stage();
        HexGame hexGame = new HexGame();
        hexGame.startGame(hexStage, boardSize, player1Color, player2Color, swapRule);
    }
}

