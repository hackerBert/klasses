package Apparatenbeheer;

import Bin.InformatieScherm;
import DatabaseStuff.ExecuteQuery;
import javafx.fxml.Initializable;
import overkoepelendeKlasses.Apparaat;
import overkoepelendeKlasses.Location;
import overkoepelendeKlasses.QR;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Apparatenbeheer implements Initializable{

    ArrayList<Apparaat> apparaten;//apparaten horende bij de locatie
    Location locatie;
    Apparatenbeheer apparatenbeheer;
    String ApName;
    String EEC;
    int KWH;

    public Apparatenbeheer(Location locatie){
        this.apparaten = locatie.getAppliances();
        this.locatie = locatie;
    }


    public void ApparatenToevoegen(String ApName, String EEC, int KWH, int roomid) throws SQLException {
        Apparaat apparaat = new Apparaat(ApName,  EEC,  KWH, roomid);
        apparaten.add(apparaat);
        ExecuteQuery executeQuery = new ExecuteQuery();
        executeQuery.addAppliance(apparaat); //naar database
    }

    public void wijzigen(String ApNameWijzig, String EECWijzig, int KWHwijzig, Apparaat apparaatTeWijzigen) throws SQLException { //stel 2 dezelfde namen in lijst die een ander apparaat object zijn, hoe lossen we dit op?
        System.out.println(apparaatTeWijzigen+ " in wijzigen");
        Apparaat apparaatTeWijzigen1 = new Apparaat(apparaatTeWijzigen.getApName(),apparaatTeWijzigen.getEEC(),apparaatTeWijzigen.getKWH(),apparaatTeWijzigen.getRoomid());
        System.out.println(apparaatTeWijzigen1+ " in wijzigen 2");
        apparaatTeWijzigen.setApName(ApNameWijzig);
        apparaatTeWijzigen.setEEC(EECWijzig);
        apparaatTeWijzigen.setKWH(KWHwijzig);
        System.out.println(apparaatTeWijzigen1);


        ExecuteQuery executeQuery = new ExecuteQuery();
        ExecuteQuery executeQuery1 = new ExecuteQuery();
        executeQuery1.deleteAppliance(apparaatTeWijzigen1);
        executeQuery.setAppliance(apparaatTeWijzigen, apparaatTeWijzigen1);
        }

    public Apparaat GetTewijzigenApparaat(){ //stel 2 dezelfde namen in lijst die een ander apparaat object zijn, hoe lossen we dit op?
        Apparaat TeWijzigenApparaat = null;
        for (Apparaat apparaat: apparaten) {
            if(apparaat.getApName().equals(this.ApName) && apparaat.getEEC().equals(this.EEC) && apparaat.getKWH() == this.KWH){
                System.out.println(apparaat);
                TeWijzigenApparaat = apparaat;
                break;
            }
        }
        return TeWijzigenApparaat;
    }
    public void setTeWijzigenApparaat(String ApnameSelected, String EECSelected, int KWHSelected){ //stel 2 dezelfde namen in lijst die een ander apparaat object zijn, hoe lossen we dit op?
        this.ApName = ApnameSelected;
        this.EEC = EECSelected;
        this.KWH = KWHSelected;
        }



    public void deleten(String ApName){
        apparaten.removeIf(apparaat -> apparaat.getApName().equals(ApName));
    }

    public Apparatenbeheer getApparatenbeheer() {
        return apparatenbeheer;
    }
    public String[] getApparaatColumns(Apparaat apparaat){
        String[] array = new String[5];
        array = apparaat.toString().split(" ");
        return  array; //[ApName, apID, EEC,KWH]
    }

    public ArrayList<String> getApNames(){
        ArrayList<String> lijst = new ArrayList<>();
        for (Apparaat apparaat: apparaten) {
            lijst.add(getApparaatColumns(apparaat)[0]);
        }
        return lijst;
    }


    public ArrayList<Apparaat> getApparaten() {
        return apparaten;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
