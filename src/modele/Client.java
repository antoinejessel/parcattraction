package modele;

import java.sql.Date;

public class Client {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private Date dateNaissance;
    private String typeClient;
    private int pointsFidelite;

    public Client() {}

    public Client(int id, String nom, String email, String motDePasse, Date dateNaissance, String typeClient, int pointsFidelite) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
        this.typeClient = typeClient;
        this.pointsFidelite = pointsFidelite;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
    public Date getDateNaissance() { return dateNaissance; }
    public String getTypeClient() { return typeClient; }
    public int getPointsFidelite() { return pointsFidelite; }

    // ✅ Setters
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setEmail(String email) { this.email = email; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }
    public void setTypeClient(String typeClient) { this.typeClient = typeClient; }
    public void setPointsFidelite(int pointsFidelite) { this.pointsFidelite = pointsFidelite; }
}
