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

public class MenuListController implements Initializable {
@FXML
private Button delete;
@FXML
private Button add;
@FXML
private Button update;
@FXML
private Button Back;


@FXML
private void handleBack(ActionEvent event ) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
}

@FXML
private void handleAdd(ActionEvent event) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddReport.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();

}
@FXML
private void handleDelete(ActionEvent event) throws IOException{


    }


@FXML
private void handleUpdate(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateReport.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
