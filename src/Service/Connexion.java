package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/gestion_taches";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection seConnecter() {
        try {
            Connection connexion = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Connexion à MySQL réussie !");

            return connexion;

        } catch (SQLException e) {
            System.out.println("Erreur de connexion à MySQL !");
            e.printStackTrace();

            return null;
        }
    }
}