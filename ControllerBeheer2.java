package Apparatenbeheer.controllers;

import Apparatenbeheer.Apparatenbeheer;
import DatabaseStuff.ExecuteQuery;
import Login.IngelogdeHuisbaas;
import Login.IngelogdePersoon;
import Login.IngelogdeStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import overkoepelendeKlasses.Apparaat;
import overkoepelendeKlasses.Location;
import overkoepelendeKlasses.Student;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.chrono.MinguoEra;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ControllerBeheer2 implements Initializable {

    private ObservableList<String> ApparaatNamen;
    Apparatenbeheer apparatenbeheer;

    @FXML
    private ListView<String> Lijst;
    @FXML
    private Text text;

    @FXML
    private TextField TekstVeld1;
    @FXML
    private TextField TekstVeld2;
    @FXML
    private TextField TekstVeld3;

    @FXML
    private Button Toevoegen;

    @FXML
    void BtnDelete(ActionEvent event) {
        String ApNameSelected = Lijst.getSelectionModel().getSelectedItem();
        apparatenbeheer.deleten(ApNameSelected);
        ApparaatNamen.remove(ApNameSelected);
        Lijst.setItems(ApparaatNamen);
        System.out.println("apparaat gedeletet");

    }
    public static boolean isInteger(String s, int radix) {
        Scanner sc = new Scanner(s.trim());
        if(!sc.hasNextInt(radix)) return false;
        // we know it starts with a valid int, now make sure
        // there's nothing left!
        sc.nextInt(radix);
        return !sc.hasNext();
    }
    @FXML
    void tohome(ActionEvent event) throws IOException, SQLException {
        ApparaatNamen.removeAll();
        Lijst.setItems(ApparaatNamen);
        AnchorPane Root = FXMLLoader.load(getClass().getResource("/homeVanStudenten/FXML/HomeVanStudenten.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(Root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void BtnToevoegen(ActionEvent event) throws SQLException {
        //check geldige waarden tekstfield
        //check of apparaatnaam al niet bestaat
        //voeg toe aan database
        //toon in lijst
        ExecuteQuery executeQuery = new ExecuteQuery();
        Location location = executeQuery.getLocationOfStudent(IngelogdeStudent.getIngelogdeStudent().getUsername());
        boolean flag = true;
        if(TekstVeld1.getText().equals(""))
            System.out.println("Een apparaatnaam moet letters bevatten");
        for (int i  = 0; i < TekstVeld1.getLength(); i ++){
            if(!Character.isLetter(TekstVeld1.getCharacters().charAt(i))){
                flag = false;
                System.out.println("Een apparaatnaam moet letters bevatten");
            break;}
        }


        if(TekstVeld2.getLength() > 1 || TekstVeld2.getLength() == 0 || (TekstVeld2.getCharacters().charAt(0) != 'A' && TekstVeld2.getCharacters().charAt(0) != 'B' && TekstVeld2.getCharacters().charAt(0) != 'C' && TekstVeld2.getCharacters().charAt(0) != 'D' && TekstVeld2.getCharacters().charAt(0) != 'E' && TekstVeld2.getCharacters().charAt(0) != 'F' && TekstVeld2.getCharacters().charAt(0) != 'G')){
                System.out.println("Energie efficientie klasse: Kies uit A,B,C,D,E,F of G");
                flag = false;
        }


        if(!ControllerBeheer2.isInteger(TekstVeld3.getText(), 10)){
            System.out.println("kWh/annum: Opgegeven waarde is niet geldig");
            flag = false;
            }

        for (String apparaat: Lijst.getItems()) {
            if (apparaat.equals(TekstVeld1.getText())){
                System.out.println("Apparaat met dezelfde naam bestaat al");
                flag = false;
                break;
            }
        }

        if (flag){
        apparatenbeheer.ApparatenToevoegen(TekstVeld1.getText(),TekstVeld2.getText(), Integer.parseInt(TekstVeld3.getText()), location.getRoomID());
        ArrayList<String> ApName;
        ApName = apparatenbeheer.getApNames();
        ApparaatNamen = FXCollections.observableArrayList();
        ApparaatNamen.addAll(ApName);
        System.out.println("ApName elementen toegevoegd aan ApparaatNamen");
        Lijst.setItems(ApparaatNamen);
        System.out.println(Lijst.getItems());}
    }

    @FXML
    void BtnWijzig(ActionEvent event) throws SQLException {
        int index = Lijst.getSelectionModel().getSelectedIndex();

        //tekstfield setten
        System.out.println(apparatenbeheer.GetTewijzigenApparaat() + " in btnwijzig");
        this.apparatenbeheer.wijzigen(TekstVeld1.getText(),TekstVeld2.getText(),Integer.parseInt(TekstVeld3.getText()), apparatenbeheer.GetTewijzigenApparaat());
        ApparaatNamen.set(index,TekstVeld1.getText());
        Lijst.setItems(ApparaatNamen);
    }
    @FXML
    public void listviewClick(javafx.scene.input.MouseEvent event) {

        String item = Lijst.getSelectionModel().getSelectedItem();
        TekstVeld1.setText(item);
        for (Apparaat apparaat: apparatenbeheer.getApparaten()) {
            if (apparaat.getApName().equals(item)){
                TekstVeld3.setText(String.valueOf(apparaat.getKWH()));
                TekstVeld2.setText(apparaat.getEEC());
            }
        }
        apparatenbeheer.setTeWijzigenApparaat(TekstVeld1.getText(), TekstVeld2.getText(),Integer.parseInt(TekstVeld3.getText()));
        System.out.println(apparatenbeheer.GetTewijzigenApparaat());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ExecuteQuery executeQuery = new ExecuteQuery();
        Location location = null;
        System.out.println(IngelogdeStudent.getIngelogdeStudent().getUsername());
        try {
            location = executeQuery.getLocationOfStudent(IngelogdeStudent.getIngelogdeStudent().getUsername());
            System.out.println(location.toString());
            System.out.println("Location was found");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assert location != null;
        this.apparatenbeheer = new Apparatenbeheer(location); //Locatie object moet meegegeven worden, via gem. contactpersoon
        ArrayList<String> ApName = null;
        ApName = apparatenbeheer.getApNames();

        assert ApName != null;
        ApparaatNamen = FXCollections.observableArrayList();
        ApparaatNamen.addAll(ApName);
        System.out.println("ApName elementen toegevoegd aan ApparaatNamen");
        Lijst.setItems(ApparaatNamen);
        System.out.println(Lijst.getItems());

    }}


