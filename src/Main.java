import Service.Connexion;
import Service.TacheService;
import ui.FenetreTaches;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        Connexion.seConnecter();

        TacheService service = new TacheService();

        SwingUtilities.invokeLater(() -> {

            FenetreTaches fenetre =
                    new FenetreTaches(service);

            fenetre.setVisible(true);
        });
    }
}