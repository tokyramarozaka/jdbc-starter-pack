package repository;

import javax.naming.directory.InvalidAttributesException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private Connection connection = null;

    public DataBaseConnection() {
        try {
            FileInputStream fis = new FileInputStream("application.properties");
            Properties properties = new Properties();
            properties.load(fis);

            String driver = (String) properties.get("driver");
            String url = (String) properties.get("URL");
            String user = (String) properties.get("Uname");
            String password = (String) properties.get("password");

            this.connection = DriverManager.getConnection(url,user,password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e){
            System.out.println("Misy tsy mety leka ny connexion tamin'ny base tao...");
        } catch (Exception e){

        }
    }

    public Connection getConnection(){
        return this.connection;
    }
}
