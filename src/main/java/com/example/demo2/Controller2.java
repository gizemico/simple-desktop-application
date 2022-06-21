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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Controller2 {
    @FXML
    private ComboBox addConcertComboBox;
    @FXML
    private TextField concertNameTextField;
    @FXML
    private TextField performerNameTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField publicIdTextField;
    @FXML
    private TextField durationTextField;
    @FXML
    private TextField timeTextField;


    public void goBackConcert(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setTitle("Main Screen");
        s.setScene(new Scene(root,305,189));
        s.show();
    }

    public void concertAddButtonPressed(ActionEvent event) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/addConcert").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json; utf-8");
        connection.setDoInput(true); //Set the DoInput flag to true if you intend to use the URL connection for input
        connection.setDoOutput(true);
        String festivalid = addConcertComboBox.getValue().toString().split("-")[0];

        JSONObject concert = new JSONObject();
        concert.put("name", concertNameTextField.getText());
        concert.put("performer", performerNameTextField.getText());
        //concert.put("startDate", Date.dateTextField.getText());
        concert.put("description", descriptionTextField.getText());
        concert.put("startType", timeTextField.getText());
        concert.put("duration", Integer.parseInt(durationTextField.getText()));
        concert.put("publicId", Integer.parseInt(publicIdTextField.getText()));

        JSONObject festival = new JSONObject();
        festival.put("festivalid", festivalid);
        concert.put("festival", festival);
        System.out.println(concert.toJSONString());
        try(OutputStream os = connection.getOutputStream()){
            byte[] input = concert.toJSONString().getBytes(StandardCharsets.UTF_8);
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

    public void initialize() throws IOException, ParseException{
        String response = "";
        HttpURLConnection connection = (HttpURLConnection)new URL("http://localhost:8080/getAllFestivals").openConnection();
        connection.setRequestMethod("GET");
        int responsecode = connection.getResponseCode();
        if(responsecode == 200){
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()){
                response += scanner.nextLine();
            }
            scanner.close();
        }

        JSONParser parser = new JSONParser();
        JSONArray array = null;
        try {
            array = (JSONArray) parser.parse(response);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        for(int i=0; i<array.size(); i++){
            try {
                JSONObject temp = (JSONObject) array.get(i);
                addConcertComboBox.getItems().add(temp.get("festivalid") + "-" + temp.get("name"));
            }
            catch(Exception e){
                String response2 = "";
                HttpURLConnection connection2 = (HttpURLConnection)new URL("http://localhost:8080/getFestival/" + array.get(i)).openConnection();
                connection2.setRequestMethod("GET");
                int responsecode2 = connection2.getResponseCode();
                if(responsecode2 == 200){
                    Scanner scanner = new Scanner(connection2.getInputStream());
                    while(scanner.hasNextLine()){
                        response2 += scanner.nextLine();
                    }
                    scanner.close();
                }
                JSONObject object = null;
                try {
                    object = (JSONObject) parser.parse(response2);
                } catch (org.json.simple.parser.ParseException ex) {
                    ex.printStackTrace();
                }
                addConcertComboBox.getItems().add(object.get("festivalid") + "-" + object.get("name"));
            }
        }
    }

}
