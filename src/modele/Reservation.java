package modele;

import java.sql.Date;
import java.sql.Timestamp;

public class Reservation {
    private int id;
    private int idClient;
    private int idAttraction;
    private Date dateReservation;
    private Timestamp dateCreation;
    private double prixTotal;
    private Integer idReduction;

    public Reservation() {}

    public Reservation(int id, int idClient, int idAttraction, Date dateReservation,
                       Timestamp dateCreation, double prixTotal, Integer idReduction) {
        this.id = id;
        this.idClient = idClient;
        this.idAttraction = idAttraction;
        this.dateReservation = dateReservation;
        this.dateCreation = dateCreation;
        this.prixTotal = prixTotal;
        this.idReduction = idReduction;
    }

    // Getters et setters

    public int getId() { return id; }
    public int getIdClient() { return idClient; }
    public int getIdAttraction() { return idAttraction; }
    public Date getDateReservation() { return dateReservation; }
    public Timestamp getDateCreation() { return dateCreation; }
    public double getPrixTotal() { return prixTotal; }
    public Integer getIdReduction() { return idReduction; }

    public void setId(int id) { this.id = id; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
    public void setIdAttraction(int idAttraction) { this.idAttraction = idAttraction; }
    public void setDateReservation(Date dateReservation) { this.dateReservation = dateReservation; }
    public void setDateCreation(Timestamp dateCreation) { this.dateCreation = dateCreation; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }
    public void setIdReduction(Integer idReduction) { this.idReduction = idReduction; }
}
