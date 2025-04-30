package vue;

import controller.AttractionController;
import modele.Attraction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminGestionAttractions extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public AdminGestionAttractions() {
        setTitle("Gestion des Attractions (Admin)");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Type", "Prix", "Description", "Actif", "Image"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6) return ImageIcon.class;
                return String.class;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(100);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshButton = creerBouton("Rafraîchir", new Color(52, 152, 219));
        JButton addButton = creerBouton("Ajouter", new Color(46, 204, 113));
        JButton editButton = creerBouton("Modifier", new Color(241, 196, 15));
        JButton deleteButton = creerBouton("Supprimer", new Color(231, 76, 60));
        JButton reportingButton = creerBouton("Voir Reporting", new Color(155, 89, 182));
        JButton retourButton = creerBouton("Retour Accueil", new Color(52, 73, 94));

        refreshButton.addActionListener(e -> loadAttractions());
        addButton.addActionListener(e -> ajouterAttraction());
        editButton.addActionListener(e -> modifierAttraction());
        deleteButton.addActionListener(e -> supprimerAttraction());
        reportingButton.addActionListener(e -> voirReporting());
        retourButton.addActionListener(e -> {
            new FenetreAccueil().setVisible(true);
            dispose();
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 3, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(reportingButton);
        buttonsPanel.add(retourButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        loadAttractions();

        setVisible(true);
    }

    private JButton creerBouton(String texte, Color couleur) {
        JButton bouton = new JButton(texte);
        bouton.setBackground(couleur);
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("SansSerif", Font.BOLD, 14));
        return bouton;
    }

    private void loadAttractions() {
        tableModel.setRowCount(0);
        List<Attraction> attractions = AttractionController.getAllAttractions();
        for (Attraction a : attractions) {
            ImageIcon imageIcon = null;
            if (a.getImage() != null) {
                ImageIcon originalIcon = new ImageIcon(a.getImage());
                Image img = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(img);
            }
            tableModel.addRow(new Object[]{
                    a.getIdAttraction(),
                    a.getNom(),
                    a.getType(),
                    a.getPrix(),
                    a.getDescription(),
                    a.isActif() ? "Oui" : "Non",
                    imageIcon
            });
        }
    }

    private void ajouterAttraction() {
        String nom = JOptionPane.showInputDialog(this, "Nom de l'attraction :");
        String type = JOptionPane.showInputDialog(this, "Type :");
        double prix = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix :"));
        String description = JOptionPane.showInputDialog(this, "Description :");
        boolean actif = JOptionPane.showConfirmDialog(this, "L'attraction est-elle active ?", "Actif", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        byte[] imageBytes = null;
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                java.nio.file.Path path = fileChooser.getSelectedFile().toPath();
                imageBytes = java.nio.file.Files.readAllBytes(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Attraction attraction = new Attraction(0, nom, type, prix, description, actif, imageBytes);
        AttractionController.ajouterAttraction(attraction);
        loadAttractions();
    }

    private void modifierAttraction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nom = (String) JOptionPane.showInputDialog(this, "Nom :", "Modifier", JOptionPane.PLAIN_MESSAGE, null, null, tableModel.getValueAt(selectedRow, 1));
            String type = (String) JOptionPane.showInputDialog(this, "Type :", "Modifier", JOptionPane.PLAIN_MESSAGE, null, null, tableModel.getValueAt(selectedRow, 2));
            double prix = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix :", tableModel.getValueAt(selectedRow, 3).toString()));
            String description = (String) JOptionPane.showInputDialog(this, "Description :", "Modifier", JOptionPane.PLAIN_MESSAGE, null, null, tableModel.getValueAt(selectedRow, 4));
            boolean actif = JOptionPane.showConfirmDialog(this, "L'attraction est-elle active ?", "Actif", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(this);
            byte[] imageBytes = null;
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    java.nio.file.Path path = fileChooser.getSelectedFile().toPath();
                    imageBytes = java.nio.file.Files.readAllBytes(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Attraction attraction = new Attraction(id, nom, type, prix, description, actif, imageBytes);
            AttractionController.modifierAttraction(attraction);
            loadAttractions();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction à modifier.");
        }
    }

    private void supprimerAttraction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette attraction ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                AttractionController.supprimerAttraction(id);
                loadAttractions();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction à supprimer.");
        }
    }

    private void voirReporting() {
        new ReportingAttractions().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminGestionAttractions::new);
    }
}
