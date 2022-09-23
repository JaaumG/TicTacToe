package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Choice class, this class is the controller of menu with the same name,
 * here all it does is, after player choose which sign ill be played against the machine,
 * its send this information to the Game class, by passing the color, and its symbol.
 */
public class Choice {

    public void InitGame(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 750);
            Game helloController = fxmlLoader.getController();
            Button botao = (Button)actionEvent.getSource();
            String playericon = botao.getText();
            helloController.setPlayerIcon(playericon);
            if(playericon.equals("TIMES")) {
                helloController.setPlayerColor(Color.BLUE);
            }else{
                helloController.setPlayerColor(Color.RED);
            }
            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
