package DatabaseStuff;

import overkoepelendeKlasses.*;
import Acties.acties;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;



public class ExecuteQuery {

    Connection connection = null;
    Statement statement= null;
    PreparedStatement preparedStatement = null;

    public ExecuteQuery(){


        String dbname = "db2021_26";
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        String databaseURL = "jdbc:mysql://pdbmbook.com:3306/" + dbname;

        final String username ="db2021_26";
        final String password = "61818dcd2e6f3";




        try{
            //registreer driver
            Class.forName(jdbcDriver);

            //open connection
            System.out.println("Connecting to database...");
            this.connection = DriverManager.getConnection(databaseURL,username,password);
            System.out.println("Connected to database"); //fix!

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Apparaat> getAlleApparaten(int roomid) throws SQLException {

        ArrayList<Apparaat> apparaten = new ArrayList<>();

        //execute query
        System.out.println("Create statement");
        this.statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT apname, energyClass ,kwhAnnum, roomid FROM appliances " +
                "WHERE RoomID = " +  roomid +";");
        String ApName = null;
        String EEC = null;
        int kwh = 0;
        int roomId;



       while(rs.next())
        {
             ApName =rs.getString("apname");
             EEC = rs.getString("energyclass");
             kwh = rs.getInt("kwhAnnum");
             roomId = rs.getInt("roomid");

             apparaten.add(new Apparaat(ApName, EEC, kwh, roomId));
        }


        rs.close();
        statement.close();
        connection.close();

        return apparaten;
    }

    public HashSet<String> getApparaatNamen() throws SQLException {

        HashSet<String> lijst = new HashSet<>();

        //execute query
        System.out.println("Creating statement...");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        ResultSet rs = statement.executeQuery("SELECT apname FROM appliances");
        System.out.println(rs);

        while(rs.next())
        {
            String ApName = rs.getString("apname");
            lijst.add(ApName);
            System.out.println(ApName);
        }

        rs.close();
        statement.close();
        connection.close();

        return lijst;
    }

    public HashSet<String> getKwhAnnum(String query) throws SQLException {

        HashSet<String> lijst = new HashSet<>();

        //execute query
        System.out.println("Creating statement...");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        ResultSet rs = statement.executeQuery(query);
        System.out.println(rs);

        while(rs.next())
        {
            String kWhAnnum = rs.getString("kWh/annum");
            lijst.add(kWhAnnum);
            System.out.println(kWhAnnum);
        }

        rs.close();
        statement.close();
        connection.close();

        return lijst;
    }

    public HashMap<String, String> getUsernameAndPassword() throws SQLException {
        HashMap<String, String> login = new HashMap<>();

        //execute query
        System.out.println("Creating statement...");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        ResultSet rs = statement.executeQuery("SELECT username, password FROM person");
        System.out.println("Valid result");

        while (rs.next()) {
            String Username = rs.getString("username");
            String Password = rs.getString("Password");
            login.put(Username, Password);
        }
        System.out.println(login);

        rs.close();
        statement.close();
        connection.close();

        return login;
    }
    public void setPersoon(String persoon) throws SQLException {
        String[] columns;
        columns = persoon.split(" ");
        System.out.println(Arrays.toString(columns));

        //execute query
        System.out.println("Creating statement...");
        statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO person (firstname, lastname, TNR, email, StudentOrLandlord, username, password) " +
                "VALUES (" + '"' + columns[0] +'"'  +", " + '"' +columns[1]+'"'+ ", "+ '"' +columns[2]+'"' +", " + '"' +columns[3]+'"' + ", " + '"' +columns[4]+'"' + ", " + '"' +columns[5]+'"' + ", " + '"' +columns[6]+'"' +")");
        System.out.println("executing Query...");
        System.out.println("Valid result");

        statement.close();
        statement.close();
        connection.close();

    }
    public Persoon getPersoon(String username) throws SQLException {
        String[] lijst = new String[7];

        //execute query
        System.out.println("Creating statement...");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        ResultSet rs = statement.executeQuery("SELECT firstname, lastname, TNR, email, studentorlandlord, username, password FROM person WHERE username = " + '"' +username +'"' + ";");
        System.out.println(rs);

        while(rs.next())
        {
            lijst[0] = rs.getString("firstname");
            lijst[1]  = rs.getString("lastname");
            lijst[2]  = rs.getString("TNR");
            lijst[3]  = rs.getString("email");
            lijst[4] = rs.getString("studentorlandlord");
            lijst[5] = username;
            lijst[6]  = rs.getString("password");
        }

        Persoon persoon = new Persoon(lijst[0],lijst[1], lijst[2],lijst[3], lijst[4],lijst[5], lijst[6]);

        rs.close();
        statement.close();
        connection.close();

        return persoon;

    }

    public ArrayList<Location> getLocationsOfLandlord (String query) throws SQLException{
        ArrayList<Location> locations = new ArrayList<>();
        String city = null;
        int locationRoomID = 0;
        String buildingtype = null;
        int insulated = 0;
        String street = null;
        int StrNumber = 0;
        int zip = 0;
        Double area = null;
        String SUsername = null;
        String LUsername = null;




            //execute query
        System.out.println("Create statement");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        ResultSet rs = statement.executeQuery("SELECT roomid, buildingtype, insulated, number, street, zip, city, area, sUsername, Lusername FROM location;");
        System.out.println(rs);


        while(rs.next())
        {
            locationRoomID = rs.getInt("roomID");
            city = rs.getString("city");
            buildingtype = rs.getString("buildingtype");
            insulated = rs.getInt("insulated");
            StrNumber = rs.getInt("number");
            street = rs.getString("street");
            zip = rs.getInt("zip");
            area = rs.getDouble("area");
            SUsername = rs.getString("SUsername");
            LUsername = rs.getString("LUsername");
            locations.add(new Location(locationRoomID, buildingtype, insulated, StrNumber, street, zip, city, area, SUsername, LUsername));
        }

        rs.close();
        statement.close();
        connection.close();

        return locations;
    }

    public ArrayList<acties> getActions(String query) throws SQLException{
        ArrayList<acties> actions = new ArrayList<>();

        //execute query
        System.out.println("Create statement");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        ResultSet rs = statement.executeQuery(query);
        System.out.println(rs);


        while(rs.next())
        {
            int actionsNR = rs.getInt("actionNR");
            String actioNname = rs.getString("actionname");
            Date actionDate = rs.getDate("actiondate");
            actions.add(new acties());
        }

        rs.close();
        statement.close();
        connection.close();

        return actions;
    }

    public ArrayList<EConsumption>getEconsumption(String query) throws SQLException{
        ArrayList<EConsumption> eConsumptions = new ArrayList<>();

        //execute query
        System.out.println("Create statement");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        ResultSet rs = statement.executeQuery(query);
        System.out.println(rs);


        while(rs.next())
        {
            double water = rs.getDouble("water");
            double gas = rs.getDouble("gas");
            double electricity = rs.getDouble("electricity");
            eConsumptions.add(new EConsumption(water, gas, electricity));
        }

        rs.close();
        statement.close();
        connection.close();

        return eConsumptions;
    }

    public void addAppliance(Apparaat apparaat) throws SQLException{
        preparedStatement = connection.prepareStatement("INSERT INTO appliances (apname, energyclass, kwhannum, roomid) VALUES " + " (?,?,?,?);");


            preparedStatement.setString(1, apparaat.getApName());
            preparedStatement.setString(2, apparaat.getEEC());
            preparedStatement.setInt(3, apparaat.getKWH());
            preparedStatement.setInt(4, apparaat.getRoomid());


            preparedStatement.close();
            preparedStatement.close();
            connection.close();
    }
    public void deleteAppliance(Apparaat apparaat) throws SQLException{
        System.out.println(apparaat);
        System.out.println(apparaat.getApName());

        //execute query
        System.out.println("Creating Prepared statement...");
        statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM appliances WHERE roomid= "+"'" +apparaat.getRoomid()+"'"+ " AND " + "apname= "  + "'" +apparaat.getApName()+"'" +";");

        System.out.println("executing Query...");
        System.out.println("Valid result");



        statement.close();
        statement.close();
        connection.close();


    }

    public void setAppliance(Apparaat Gewijzigd, Apparaat oorspronkelijk) throws SQLException{

        //execute query
        System.out.println("Creating Prepared statement...");
        statement = connection.createStatement();
        statement.executeUpdate("insert INTO appliances (apname, energyclass, kwhannum, roomid) VALUES  (" + '"' + Gewijzigd.getApName() +'"'  +", " + '"' +Gewijzigd.getEEC()+'"'+ ", "+ '"' +Gewijzigd.getKWH()+'"' +", " + '"' +oorspronkelijk.getRoomid()+ '"'+ ");");

        System.out.println("executing Query...");
        System.out.println("Valid result");



        statement.close();
        statement.close();
        connection.close();


    }

    public void deleteAppliance(String query) throws SQLException{

        //execute query
        System.out.println("Create statement");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        int deletedRows = statement.executeUpdate(query); //query: DELETE FROM appliances WHERE apname = de meegegeven apname
        System.out.println(deletedRows + " appliances were deleted");
    }
    public Location getLocationOfStudent (String Username) throws SQLException{

        String city;
        int locationRoomID ;
        String buildingtype ;
        String street ;
        int StrNumber;
        int zip;
        double area;
        String SUsername;
        String LUsername ;
        Location location = null;
        int insulated;


        //execute query
        System.out.println("Create statement");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        ResultSet rs = statement.executeQuery("SELECT roomid, buildingtype, insulated, number, street, zip, city, area, sUsername, Lusername FROM location WHERE SUsername = "+ "'" +Username+ "'" + ";");
        System.out.println(rs);


        while(rs.next())
        {
            locationRoomID = rs.getInt("roomID");
            city = rs.getString("city");
            buildingtype = rs.getString("buildingtype");
            insulated = rs.getInt("insulated");
            StrNumber = rs.getInt("number");
            street = rs.getString("street");
            zip = rs.getInt("zip");
            area = rs.getDouble("area");
            LUsername = rs.getString("LUsername");
            location = new Location(locationRoomID, buildingtype, insulated, StrNumber, street, zip, city, area, Username, LUsername);
        }

        rs.close();
        statement.close();
        connection.close();

        return location;
    }

    public void setApparaten(ArrayList<Apparaat> apparaten) throws SQLException {

        {
            int i = 0;
            preparedStatement = connection.prepareStatement("INSERT INTO appliances (apname, energyclass, kwhannum, roomid) VALUES " + " (?,?,?,?);");

            for (Apparaat apparaat : apparaten) {

                preparedStatement.setString(1, apparaat.getApName());
                preparedStatement.setString(2, apparaat.getEEC());
                preparedStatement.setInt(3, apparaat.getKWH());
                preparedStatement.setInt(4, apparaat.getRoomid());


                preparedStatement.addBatch();
                i++;

                if (i % 1000 == 0 || i == apparaten.size()) {
                    preparedStatement.executeBatch(); // Execute every 1000 items.
                }
            }
        }
            preparedStatement.close();
            preparedStatement.close();
            connection.close();
    }

    public void addStudent(String query) throws SQLException{

        //execute query
        System.out.println("Create statement");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        int insertedRows = statement.executeUpdate(query); //query: INSERT INTO student VALUES (values dat je wil toevoegen)
        System.out.println(insertedRows + " student(s) were inserted");
    }

    public void removeStudent(String query) throws SQLException{

        //execute query
        System.out.println("Create statement");
        this.statement = connection.createStatement();
        System.out.println("Statement was made");
        System.out.println("executing Query...");
        int deletedRows = statement.executeUpdate(query); //query: DELETE FROM student WHERE SUsername = de meegegeven SUsername
        System.out.println(deletedRows + " student(s) were deleted");
    }
}
