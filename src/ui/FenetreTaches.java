package ui;

import model.Tache;
import Service.TacheService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FenetreTaches extends JFrame {

    private TacheService service;

    private JTextField txtId;
    private JTextField txtTitre;
    private JTextField txtDescription;
    private JTextField txtDate;

    private JComboBox<String> comboPriorite;
    private JComboBox<String> comboStatut;

    private JTable table;
    private DefaultTableModel model;

    public FenetreTaches(TacheService service) {

        this.service = service;

        setTitle("Gestion des tâches");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        creerInterface();
        afficherTaches();
    }

    private void creerInterface() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        // Formulaire
        JPanel formulaire = new JPanel(new GridLayout(6, 2, 10, 10));

        formulaire.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        formulaire.add(new JLabel("ID :"));
        txtId = new JTextField();
        formulaire.add(txtId);

        formulaire.add(new JLabel("Titre :"));
        txtTitre = new JTextField();
        formulaire.add(txtTitre);

        formulaire.add(new JLabel("Description :"));
        txtDescription = new JTextField();
        formulaire.add(txtDescription);

        formulaire.add(new JLabel("Priorité :"));
        comboPriorite = new JComboBox<>(
                new String[]{"Basse", "Moyenne", "Haute"}
        );
        formulaire.add(comboPriorite);

        formulaire.add(new JLabel("Statut :"));
        comboStatut = new JComboBox<>(
                new String[]{"À faire", "En cours", "Terminée"}
        );
        formulaire.add(comboStatut);

        formulaire.add(new JLabel("Date limite :"));
        txtDate = new JTextField();
        formulaire.add(txtDate);

        panelPrincipal.add(formulaire, BorderLayout.NORTH);

        // Tableau
        String[] colonnes = {
                "ID",
                "Titre",
                "Description",
                "Priorité",
                "Statut",
                "Date limite"
        };

        model = new DefaultTableModel(colonnes, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JPanel boutons = new JPanel();

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnRechercher = new JButton("Rechercher");
        JButton btnAfficher = new JButton("Afficher");
        JButton btnVider = new JButton("Vider");

        boutons.add(btnAjouter);
        boutons.add(btnModifier);
        boutons.add(btnSupprimer);
        boutons.add(btnRechercher);
        boutons.add(btnAfficher);
        boutons.add(btnVider);

        panelPrincipal.add(boutons, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // Actions des boutons
        btnAjouter.addActionListener(e -> ajouterTache());

        btnModifier.addActionListener(e -> modifierTache());

        btnSupprimer.addActionListener(e -> supprimerTache());

        btnRechercher.addActionListener(e -> rechercherTache());

        btnAfficher.addActionListener(e -> afficherTaches());

        btnVider.addActionListener(e -> viderChamps());

        // Sélection d'une ligne
        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int ligne = table.getSelectedRow();

                if (ligne >= 0) {

                    txtId.setText(
                            table.getValueAt(ligne, 0).toString()
                    );

                    txtTitre.setText(
                            table.getValueAt(ligne, 1).toString()
                    );

                    txtDescription.setText(
                            table.getValueAt(ligne, 2).toString()
                    );

                    comboPriorite.setSelectedItem(
                            table.getValueAt(ligne, 3).toString()
                    );

                    comboStatut.setSelectedItem(
                            table.getValueAt(ligne, 4).toString()
                    );

                    txtDate.setText(
                            table.getValueAt(ligne, 5).toString()
                    );
                }
            }
        });
    }

    // Ajouter une tâche
    private void ajouterTache() {

        try {

            int id = Integer.parseInt(txtId.getText());

            String titre = txtTitre.getText();
            String description = txtDescription.getText();
            String priorite = comboPriorite.getSelectedItem().toString();
            String statut = comboStatut.getSelectedItem().toString();
            String date = txtDate.getText();

            if (titre.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Le titre est obligatoire."
                );

                return;
            }

            Tache tache = new Tache(
                    id,
                    titre,
                    description,
                    priorite,
                    statut,
                    date
            );

            service.ajouterTache(tache);

            afficherTaches();
            viderChamps();

            JOptionPane.showMessageDialog(
                    this,
                    "Tâche ajoutée avec succès !"
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "L'ID doit être un nombre."
            );
        }
    }

    // Modifier une tâche
    private void modifierTache() {

        try {

            int id = Integer.parseInt(txtId.getText());

            boolean resultat = service.modifierTache(
                    id,
                    txtTitre.getText(),
                    txtDescription.getText(),
                    comboPriorite.getSelectedItem().toString(),
                    comboStatut.getSelectedItem().toString(),
                    txtDate.getText()
            );

            if (resultat) {

                afficherTaches();

                JOptionPane.showMessageDialog(
                        this,
                        "Tâche modifiée avec succès !"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Tâche introuvable."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID invalide."
            );
        }
    }

    // Supprimer une tâche
    private void supprimerTache() {

        try {

            int id = Integer.parseInt(txtId.getText());

            boolean resultat = service.supprimerTache(id);

            if (resultat) {

                afficherTaches();
                viderChamps();

                JOptionPane.showMessageDialog(
                        this,
                        "Tâche supprimée avec succès !"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Tâche introuvable."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID invalide."
            );
        }
    }

    // Rechercher une tâche
    private void rechercherTache() {

        String titre = txtTitre.getText();

        model.setRowCount(0);

        for (Tache tache : service.rechercherParTitre(titre)) {

            model.addRow(new Object[]{
                    tache.getId(),
                    tache.getTitre(),
                    tache.getDescription(),
                    tache.getPriorite(),
                    tache.getStatut(),
                    tache.getDateLimite()
            });
        }
    }

    // Afficher les tâches
    private void afficherTaches() {

        model.setRowCount(0);

        for (Tache tache : service.getTaches()) {

            model.addRow(new Object[]{
                    tache.getId(),
                    tache.getTitre(),
                    tache.getDescription(),
                    tache.getPriorite(),
                    tache.getStatut(),
                    tache.getDateLimite()
            });
        }
    }

    // Vider les champs
    private void viderChamps() {

        txtId.setText("");
        txtTitre.setText("");
        txtDescription.setText("");
        txtDate.setText("");

        comboPriorite.setSelectedIndex(0);
        comboStatut.setSelectedIndex(0);

        table.clearSelection();
    }
}