package vue;

import dao.AttractionDAO;
import modele.Attraction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminGestionAttractions extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private AttractionDAO attractionDAO;

    public AdminGestionAttractions() {
        setTitle("Gestion des Attractions (Admin)");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        attractionDAO = new AttractionDAO();

        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Type", "Prix", "Description", "Actif", "Image"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshButton = new JButton("Rafraîchir");
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        JButton reportingButton = new JButton("Voir Reporting");


        refreshButton.addActionListener(e -> loadAttractions());
        addButton.addActionListener(e -> ajouterAttraction());
        editButton.addActionListener(e -> modifierAttraction());
        deleteButton.addActionListener(e -> supprimerAttraction());
        reportingButton.addActionListener(e -> voirreporting());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(reportingButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        add(panel);

        loadAttractions();

        setVisible(true);
    }

    private void loadAttractions() {
        tableModel.setRowCount(0);
        List<Attraction> attractions = attractionDAO.findAll();
        for (Attraction a : attractions) {
            tableModel.addRow(new Object[]{
                    a.getIdAttraction(),
                    a.getNom(),
                    a.getType(),
                    a.getPrix(),
                    a.getDescription(),
                    a.isActif() ? "Oui" : "Non",
                    a.getImagePath()
            });
        }
    }

    private void ajouterAttraction() {
        String nom = JOptionPane.showInputDialog(this, "Nom de l'attraction :");
        String type = JOptionPane.showInputDialog(this, "Type :");
        double prix = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix :"));
        String description = JOptionPane.showInputDialog(this, "Description :");
        boolean actif = JOptionPane.showConfirmDialog(this, "L'attraction est-elle active ?", "Actif", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        String imagePath = JOptionPane.showInputDialog(this, "Chemin de l'image :");

        Attraction attraction = new Attraction(0, nom, type, prix, description, actif, imagePath);
        attractionDAO.insert(attraction);
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
            String imagePath = (String) JOptionPane.showInputDialog(this, "Chemin de l'image :", "Modifier", JOptionPane.PLAIN_MESSAGE, null, null, tableModel.getValueAt(selectedRow, 6));

            Attraction attraction = new Attraction(id, nom, type, prix, description, actif, imagePath);
            attractionDAO.update(attraction);
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
                attractionDAO.delete(id);
                loadAttractions();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction à supprimer.");
        }
    }

    public void voirreporting() {
        new ReportingAttractions().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminGestionAttractions::new);
    }
}
