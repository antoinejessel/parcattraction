package vue;

import modele.Client;

import javax.swing.*;

public class AdminPanel extends JFrame {

    public AdminPanel(Client admin) {
        setTitle("Panneau d'administration");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Bienvenue, " + admin.getNom() + " (admin)", SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }
}
