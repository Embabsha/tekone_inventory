package com.example.demo;

import com.example.demo.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Button button;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = (Parent) fxmlLoader1.load();
        MainController controller = fxmlLoader1.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
