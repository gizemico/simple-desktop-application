package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
// Controller4 is created for Statistics
public class Controller4 {

    @FXML
    private CheckBox longestConcertCheckBox;
    @FXML
    private CheckBox popularFestivalsCheckBox;
    @FXML
    private ListView longestConcertListView;
    @FXML
    private ListView popularFestivalsListView;


    public void showButtonPressed(ActionEvent event) throws IOException, ParseException {
        if (longestConcertCheckBox.isSelected()) {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/getlongestconcerts").openConnection();
            connection.setRequestMethod("GET");
            String response = "";
            if (connection.getResponseCode() == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine())
                    response += scanner.nextLine();
                scanner.close();
            }
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(response);
            for (int i = 0; i < array.size(); i++) {
                String record = "";
                try {
                    JSONObject object = (JSONObject) array.get(i);
                    record += object.get("publicId") + " " + object.get("startDate") + " " + object.get("startType") + " " + object.get("duration") + " " + object.get("name") + " " + object.get("description") + " " + object.get("festivalRun") + " " + object.get("performer");
                } catch (Exception k) {
                    HttpURLConnection connection2 = (HttpURLConnection) new URL("http://localhost:8080/getFestival/" + array.get(i).toString()).openConnection();
                    connection2.setRequestMethod("GET");
                    String response2 = "";
                    if (connection2.getResponseCode() == 200) {
                        Scanner scanner = new Scanner(connection2.getInputStream());
                        while (scanner.hasNextLine())
                            response2 += scanner.nextLine();
                        scanner.close();
                    }
                    System.out.println(response2);
                    JSONObject object2 = (JSONObject) parser.parse(response2);
                    record += object2.get("publicId") + " " + object2.get("startDate") + " " + object2.get("startType") + " " + object2.get("duration") + " " + object2.get("name") + " " + object2.get("description") + " " + object2.get("festivalRun") + " " + object2.get("performer");
                }

                longestConcertListView.getItems().add(record);
            }
        }

        if (popularFestivalsCheckBox.isSelected()) {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/popularfestivals").openConnection();
            connection.setRequestMethod("GET");
            String response = "";
            if (connection.getResponseCode() == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine())
                    response += scanner.nextLine();
                scanner.close();
            }
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(response);
            for (int i = 0; i < array.size(); i++) {
                String record = "";
                try {
                    JSONObject object = (JSONObject) array.get(i);
                    record += object.get("festivalid") + " " + object.get("name") + " " + object.get("place") + " " + object.get("festivalRuns");
                } catch (Exception k) {
                    HttpURLConnection connection2 = (HttpURLConnection) new URL("http://localhost:8080/getFestival/" + array.get(i).toString()).openConnection();
                    connection2.setRequestMethod("GET");
                    String response2 = "";
                    if (connection2.getResponseCode() == 200) {
                        Scanner scanner = new Scanner(connection2.getInputStream());
                        while (scanner.hasNextLine())
                            response2 += scanner.nextLine();
                        scanner.close();
                    }
                    System.out.println(response2);
                    JSONObject object2 = (JSONObject) parser.parse(response2);
                    record += object2.get("festivalid") + " " + object2.get("name") + " " + object2.get("place") + " " + object2.get("festivalRuns");
                }

                popularFestivalsListView.getItems().add(record);
            }
        }
    }

    public void goBackStatistics(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setTitle("Main Screen");
        s.setScene(new Scene(root,305,189));
        s.show();
    }

}
