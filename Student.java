package overkoepelendeKlasses;

import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends Persoon {

    ArrayList<Apparaat> apparaten = new ArrayList<>();
    Persoon persoon;



    public Student(String firstName, String lastName, String TNR, String email,String landlordOfStudent ,String username, String password) throws SQLException {
        super(firstName, lastName, TNR, email, landlordOfStudent, username, password);
    }

    public Student(Persoon persoon) {
        super(persoon);
        this.persoon = persoon;
    }


    public void apparaatToevoegen(Apparaat apparaat){
        if (apparaat != null && !apparaten.contains(apparaat)){
            apparaten.add(apparaat);
        }
        else
            System.out.println("Apparaat toevoegen lukte niet.");
    }

    public void apparaatVerwijderen(Apparaat apparaat){
        if (apparaat != null && apparaten.contains(apparaat)) {
            apparaten.remove(apparaat);
        }
        else
            System.out.println("Apparaat verwijderen lukte niet.");
    }




    @Override
    public String getUsername() {
        return persoon.getUsername();
    }



    @Override
    public String toString() {
        return "Student{ persoon=" + persoon +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}