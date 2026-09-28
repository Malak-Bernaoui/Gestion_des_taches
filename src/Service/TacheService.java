package Service;

import model.Tache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TacheService {

    // Ajouter une tâche dans MySQL
    public void ajouterTache(Tache tache) {

        String sql = "INSERT INTO taches " +
                "(id, titre, description, priorite, statut, date_limite) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connexion = Connexion.seConnecter();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setInt(1, tache.getId());
            statement.setString(2, tache.getTitre());
            statement.setString(3, tache.getDescription());
            statement.setString(4, tache.getPriorite());
            statement.setString(5, tache.getStatut());
            statement.setString(6, tache.getDateLimite());

            statement.executeUpdate();

            System.out.println("Tâche ajoutée avec succès !");

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de la tâche.");
            e.printStackTrace();
        }
    }


    // Afficher toutes les tâches
    public List<Tache> getTaches() {

        List<Tache> taches = new ArrayList<>();

        String sql = "SELECT * FROM taches";

        try (Connection connexion = Connexion.seConnecter();
             PreparedStatement statement = connexion.prepareStatement(sql);
             ResultSet resultat = statement.executeQuery()) {

            while (resultat.next()) {

                Tache tache = new Tache(
                        resultat.getInt("id"),
                        resultat.getString("titre"),
                        resultat.getString("description"),
                        resultat.getString("priorite"),
                        resultat.getString("statut"),
                        resultat.getString("date_limite")
                );

                taches.add(tache);
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des tâches.");
            e.printStackTrace();
        }

        return taches;
    }


    // Rechercher une tâche par son ID
    public Tache rechercherParId(int id) {

        String sql = "SELECT * FROM taches WHERE id = ?";

        try (Connection connexion = Connexion.seConnecter();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultat = statement.executeQuery();

            if (resultat.next()) {

                return new Tache(
                        resultat.getInt("id"),
                        resultat.getString("titre"),
                        resultat.getString("description"),
                        resultat.getString("priorite"),
                        resultat.getString("statut"),
                        resultat.getString("date_limite")
                );
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche.");
            e.printStackTrace();
        }

        return null;
    }


    // Modifier une tâche
    public boolean modifierTache(int id,
                                 String titre,
                                 String description,
                                 String priorite,
                                 String statut,
                                 String dateLimite) {

        String sql = "UPDATE taches SET " +
                "titre = ?, " +
                "description = ?, " +
                "priorite = ?, " +
                "statut = ?, " +
                "date_limite = ? " +
                "WHERE id = ?";

        try (Connection connexion = Connexion.seConnecter();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, titre);
            statement.setString(2, description);
            statement.setString(3, priorite);
            statement.setString(4, statut);
            statement.setString(5, dateLimite);
            statement.setInt(6, id);

            int lignesModifiees = statement.executeUpdate();

            return lignesModifiees > 0;

        } catch (Exception e) {
            System.out.println("Erreur lors de la modification.");
            e.printStackTrace();
        }

        return false;
    }


    // Supprimer une tâche
    public boolean supprimerTache(int id) {

        String sql = "DELETE FROM taches WHERE id = ?";

        try (Connection connexion = Connexion.seConnecter();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            int lignesSupprimees = statement.executeUpdate();

            return lignesSupprimees > 0;

        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression.");
            e.printStackTrace();
        }

        return false;
    }


    // Rechercher par titre
    public List<Tache> rechercherParTitre(String titre) {

        List<Tache> resultat = new ArrayList<>();

        String sql = "SELECT * FROM taches WHERE titre LIKE ?";

        try (Connection connexion = Connexion.seConnecter();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, "%" + titre + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Tache tache = new Tache(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getString("priorite"),
                        resultSet.getString("statut"),
                        resultSet.getString("date_limite")
                );

                resultat.add(tache);
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche par titre.");
            e.printStackTrace();
        }

        return resultat;
    }
}