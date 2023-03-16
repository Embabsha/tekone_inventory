package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

@FXML
private Button add;
@FXML
private Button clear;
@FXML
private Button back;


@FXML
private void handleAdd(ActionEvent event){

}

@FXML
private  void handleClear(ActionEvent event){

}

@FXML
private void handleBack(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StocksList.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();

}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
