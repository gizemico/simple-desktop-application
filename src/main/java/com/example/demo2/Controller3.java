package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.format.TextStyle;
import java.util.Scanner;

// Controller3 is created for Add-Festival-Run
public class Controller3 {

    @FXML
    private TextField festivalRunIdTextField;
    @FXML
    private TextField startDateTextField;
    @FXML
    private TextField durationTextField;

    @FXML
    private ComboBox chooseFestivalComboBox;

    public void festivalRunAddButtonPressed(ActionEvent event) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/addFestivalRun").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json; utf-8");
        connection.setDoInput(true); //Set the DoInput flag to true if you intend to use the URL connection for input
        connection.setDoOutput(true);
        String festivalid = chooseFestivalComboBox.getValue().toString().split("-")[0];

        JSONObject frun = new JSONObject();
        frun.put("festivalRunId", festivalRunIdTextField.getText());
        frun.put("startDate", startDateTextField.getText());
        frun.put("duration", Integer.parseInt(durationTextField.getText()));

        JSONObject festival = new JSONObject();
        festival.put("festivalid", festivalid);
        frun.put("festival", festival);
        System.out.println(frun.toJSONString());
        try(OutputStream os = connection.getOutputStream()){
            byte[] input = frun.toJSONString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        String response = "";
        int responsecode = connection.getResponseCode();
        if(responsecode == 200){
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response += scanner.nextLine();
            }
            scanner.close();
        }

        System.out.println(response);

    }

    public void goBackFestivalRun(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setTitle("Main Screen");
        s.setScene(new Scene(root,305,189));
        s.show();
    }


}
