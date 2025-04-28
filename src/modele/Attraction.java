package modele;

public class Attraction {
    private int idAttraction;
    private String nom;
    private String type;
    private double prix;
    private String description;
    private boolean actif;
    private byte[] image;

    public Attraction() {}

    public Attraction(int idAttraction, String nom, String type, double prix, String description, boolean actif, byte[] image) {
        this.idAttraction = idAttraction;
        this.nom = nom;
        this.type = type;
        this.prix = prix;
        this.description = description;
        this.actif = actif;
        this.image = image;
    }

    public int getIdAttraction() {
        return idAttraction;
    }

    public void setIdAttraction(int idAttraction) {
        this.idAttraction = idAttraction;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
