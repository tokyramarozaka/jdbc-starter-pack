package Maholy;

import java.sql.*;
import java.util.*;

/**
 *
 */
public class TransportDatabase {
    private Statement statement;
    private final String URL;
    private final String USER;
    private final String password;

    public TransportDatabase(String URL, String USER, String password) {
        this.URL = URL;
        this.USER = USER;
        this.password = password;

        // establish connection
        try {
            Connection connection = DriverManager.getConnection(URL, USER, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Error on Connection");
            e.printStackTrace();
        }
    }

    /**
     * Get all values from a specified rows on a specified table
     *
     * @param tableName the name of the table
     * @return all column value separed by {@code new line \n}
     */
    public String select(String tableName, String columnName) {
        ResultSet resultSet;
        StringBuilder result = new StringBuilder("");

        try {
            resultSet = statement.executeQuery("SELECT * FROM ville");
            while (resultSet != null && resultSet.next()) {
                result.append(resultSet.getString("nom_ville")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * Places disponibles a chaque vehicules
     *
     * @return List of vailable places |  vehicles |
     */
    public List<String> showAvailablePlaces() {
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery("""
                                                       SELECT
                                                            ville_depart.nom_ville AS ville_depart,
                                                            ville_arrivee.nom_ville AS ville_arrivee,
                                                            vl.matricule,
                                                            places_dispo
                                                       FROM utiliser ut
                                                                INNER JOIN voyage vg ON ut.id_voyage = vg.id_voyage
                                                                INNER JOIN vehicules vl ON ut.id_vehicule = vl.id_vehicule
                                                                INNER JOIN ville AS ville_depart ON ville_depart.id_ville = vg.id_ville_depart
                                                                INNER JOIN ville AS ville_arrivee ON ville_arrivee.id_ville = vg.id_ville_arrivee;
                    """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<String> results = new ArrayList<>();
        StringBuilder currentRow;
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    currentRow = new StringBuilder("")
                            .append(resultSet.getString("ville_depart")).append(" ")
                            .append(resultSet.getString("ville_arrivee")).append(" ")
                            .append(resultSet.getString("matricule")).append(" ")
                            .append(resultSet.getString("places_dispo"));

                    results.add(currentRow.toString());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results.stream().sorted().toList();
    }

    /**
     * Show all client destination
     */
    public List<String> clientsDestination() {
        List<String> clientDestination = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT nom_ville, nom_client, prenom_client FROM client, voyage, ville,
                    reserver WHERE ville.id_ville = voyage.id_ville_arrivee AND 
                    client.id_client = reserver.id_client AND reserver.id_voyage = voyage.id_voyage ;
                   """);

            while (resultSet.next()) {
                clientDestination.add(String.valueOf(new StringBuilder()
                        .append(resultSet.getString(1)).append(" ")
                        .append(resultSet.getString(2)).append(" ")
                        .append(resultSet.getString(3))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientDestination;
    }

    /**
     * Show destination for a specified client name or containing the {@code clientName}
     *
     * @param clientName
     * @return the destination and the full name
     */

    public List<String> clientsDestination(String clientName) {
        return clientsDestination().stream()
                .filter(row -> row.toLowerCase().contains(clientName.toLowerCase()))
                .toList();
    }

    /**
     * Show all client that will go to the specified destination
     *
     * @param destination
     * @return the list of client that will go to ...
     */
    public List<String> willGoTo(String destination) {
        List<String> clientsDestinationTable = clientsDestination();
        return (!clientsDestinationTable.isEmpty()) ? (
                clientsDestination().stream()
                        .filter(dest -> dest.toLowerCase().contains(destination.toLowerCase()))
                        .toList()
        ) : new ArrayList<>(Collections.singleton("\"No records data found\""));
    }

    /**
     * Increase all trip cost by <strong>rate</strong> percent
     * For all offer (VIP | PREMIUM | LITE)
     *
     * @param rate the increasing rate
     */
    public void increaseTripCost(float rate) {
        String increasingValue = "" + rate + "*tarif/100";

        try {
            statement.executeUpdate("UPDATE offre SET tarif=tarif+" + increasingValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * show all offer
     */
    public List<String> offerList() {
        ArrayList<String> offerList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM offre");
            while (resultSet.next()) {
                offerList.add(
                        resultSet.getString("type") + " " +
                                resultSet.getString("tarif")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offerList;
    }

    /**
     * Increase all cost for all offer with the specified type (VIP|Lite|Premium)
     *
     * @param rate      increasing rate
     * @param offerType type of the offer
     */
    public void increaseTripCost(float rate, String offerType) {
        String increasingValue = rate + "*tarif/100";
        try {
            statement.executeUpdate("""
                    UPDATE offre SET tarif=tarif+
                    """ + increasingValue +
                    " WHERE type ILIKE ('%" + offerType + "%')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTown(String townName) {
        try {
            statement.executeUpdate("INSERT INTO ville VALUES (" + townName + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTown(List<String> townList) {
        for(var town:townList){
            insertTown(town);
        }
    }

    public void printResult(List<String> results) {
        for (var result : results) {
            System.out.println(result);
        }
    }
}

