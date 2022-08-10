package Maholy;


import java.sql.*;

public class Main {

    public static void main(String[] args) {
//        replace toky_trans by your db name
        String urlString = "jdbc:postgresql://localhost:5432/toky_trans";
//        set your username
        String user = "postgres";
//        set your postrgesql password
        String password = "";

//        test example
        TransportDatabase transportDatabase = new TransportDatabase(urlString, user, password);
        transportDatabase.increaseTripCost(3, "Classic");
    }

}

