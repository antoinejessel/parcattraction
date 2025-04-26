package vue;

import modele.Client;

import javax.swing.*;

public class FenetreAccueilClient extends JFrame {

    public FenetreAccueilClient(Client client) {
        setTitle("Accueil Client");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String message = "Bienvenue " + client.getNom() + " !";
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }
}
