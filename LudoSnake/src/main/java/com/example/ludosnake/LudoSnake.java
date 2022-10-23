package com.example.ludosnake;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class LudoSnake extends Application {
    public static final int tileSize =  40;
    int height = 10;

    int width = 10;
    Group tileGroup = new Group();


    int yLine = 430;
    int xLine =40;

    int diceValue =1;
    Button gameButton;

    Player playerOne , playerTwo;
    boolean gameStart = false, playerOneTurn = true, playerTwoTurn = false;

    Label randResult;

//    int TileSize = 40;

    public Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width*tileSize,height*tileSize+100);
        root.getChildren().addAll(tileGroup);
//
        for(int i = 0;i<height;i++){
            for(int j=0;j<width;j++){
                Tile tile = new Tile(tileSize,tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                tileGroup.getChildren().addAll(tile);
            }
        }

        playerOne = new Player(tileSize, Color.BLACK);
        playerTwo = new Player(tileSize-10, Color.WHITE);

        randResult = new Label("Game not Started");
        randResult.setTranslateX(150);
        randResult.setTranslateY(410);
        Button plyer1Button = new Button("Player One");
        plyer1Button.setTranslateX(10);
        plyer1Button.setTranslateY(yLine);

        plyer1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(playerOneTurn){
                        getDiceValue();
                        randResult.setText("PlayerOne - " + String.valueOf(diceValue));
                        // move the player
                        playerOne.movePlayer(diceValue);
                        playerOneTurn = false;
                        playerTwoTurn = true;
//                        playerOne.playerAtSnakeOrLadder();
                        gameOver();
                    }
                }

            }
        });


        Button plyer2Button = new Button("Player two");
        plyer2Button.setTranslateX(300);
        plyer2Button.setTranslateY(yLine);

        plyer2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(playerTwoTurn){
                        getDiceValue();
                        randResult.setText("PlayerTwo - " + String.valueOf(diceValue));
                        // move the player
                        playerTwo.movePlayer(diceValue);
                        playerOneTurn = true;
                        playerTwoTurn = false;
//                        playerTwo.playerAtSnakeOrLadder();
                        gameOver();
                    }
                }

            }
        });
         gameButton = new Button("Start Game");
        gameButton.setTranslateX(150);
        gameButton.setTranslateY(yLine);
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                randResult.setText("Started");
                gameStart = true;
                gameButton.setText("Game Going");
            }
        });

        Image img = new Image("C:\\Users\\Prathamesh Karbele\\IdeaProjects\\LudoSnake\\src\\SnakeLadderGame.jpg");
        ImageView boardImage = new ImageView();
        boardImage.setImage(img);
        boardImage.setFitHeight(tileSize*height);
        boardImage.setFitWidth(tileSize*width);

        tileGroup.getChildren().addAll(boardImage,randResult, playerOne.getGamePiece(),playerTwo.getGamePiece(),plyer1Button ,plyer2Button,gameButton);

        return root;
    }
    void  gameOver(){
        if(playerOne.getWinningStatus()==true){
            randResult.setText("Player One Won");
            gameButton.setText("Start Again");
            gameStart = false;
        }
        else if(playerTwo.getWinningStatus()==true){
            randResult.setText("Player Two Won");
            gameButton.setText("Start Again");
            gameStart = false;
        }
    }
    private void getDiceValue(){
        diceValue =(int)(Math.random()*6+1);
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Ludo Snake");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long currentTime = System.currentTimeMillis();
                long dt = currentTime - Player.lastMovementTime;
//                System.out.println(currentTime + "  " +Player.lastMovementTime +"  " + dt);

                if(dt > 1e3){
                    Player.lastMovementTime = currentTime;
//                    System.out.println(currentTime +"  "+ Player.lastMovementTime);

                    playerOne.playerAtSnakeOrLadder();
                    playerTwo.playerAtSnakeOrLadder();
                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }

}