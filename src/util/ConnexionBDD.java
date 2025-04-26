package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class ConnexionBDD {
    private static Connection connexion = null;

    public static Connection getConnexion() {
        if (connexion == null) {
            try {
                Properties props = new Properties();
                InputStream input = ConnexionBDD.class.getClassLoader().getResourceAsStream("database.properties");
                props.load(input);

                String url = props.getProperty("url");
                String user = props.getProperty("user");
                String password = props.getProperty("password");

                connexion = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Connexion réussie à MySQL !");
            } catch (Exception e) {
                System.err.println("❌ Erreur de connexion : " + e.getMessage());
            }
        }
        return connexion;
    }
}
