package model;

public class Tache {

    private int id;
    private String titre;
    private String description;
    private String priorite;
    private String statut;
    private String dateLimite;

    public Tache(int id, String titre, String description,
                 String priorite, String statut, String dateLimite) {

        this.id = id;
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.statut = statut;
        this.dateLimite = dateLimite;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getPriorite() {
        return priorite;
    }

    public String getStatut() {
        return statut;
    }

    public String getDateLimite() {
        return dateLimite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setDateLimite(String dateLimite) {
        this.dateLimite = dateLimite;
    }
}