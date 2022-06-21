package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private RadioButton radioButton3;

    public void continueButtonPressed(ActionEvent event) throws IOException{
        if(radioButton2.isSelected()) {
                Parent root = FXMLLoader.load(getClass().getResource("Add-Concert.fxml"));
                Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
                s.setTitle("Add Concert");
                s.setScene(new Scene(root,485,339));
                s.show();

        }
        else if(radioButton1.isSelected()){
                Parent root = FXMLLoader.load(getClass().getResource("Add-Festival-Run.fxml"));
                Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
                s.setTitle("Add Festival");
                s.setScene(new Scene(root,353,199));
                s.show();
        }
        else{
                Parent root = FXMLLoader.load(getClass().getResource("Statistics.fxml"));
                Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
                s.setTitle("Statistics");
                s.setScene(new Scene(root,587,344));
                s.show();
        }

    }

}
