package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class ConnexionBDD {

    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties props = new Properties();
            InputStream input = ConnexionBDD.class.getClassLoader().getResourceAsStream("database.properties");
            props.load(input);

            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des propriétés : " + e.getMessage());
        }
    }

    public static Connection getConnexion() {
        try {
            Connection connexion = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie à MySQL !");
            return connexion;
        } catch (Exception e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            return null;
        }
    }
}
