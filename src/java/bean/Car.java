/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author faiss
 */
@Entity
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String matricule;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Fuel fuel;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Modele model;
    @OneToOne
    private Representative representative;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Transmission transmission;
    private int nombrePorte;
    private int puissanceFiscale;
    private double kilometrage;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAchat;
    private Double prix;

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
    

    @OneToMany(mappedBy = "car")
    private List<DemandeLocationDetail> demandeLocationDetails;

    public List<DemandeLocationDetail> getDemandeLocationDetails() {
        return demandeLocationDetails;
    }

    public void setDemandeLocationDetails(List<DemandeLocationDetail> demandeLocationDetails) {
        this.demandeLocationDetails = demandeLocationDetails;
    }
    
    
    
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public Fuel getFuel() {
        return fuel;
    }

    

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public Modele getModel() {
        return model;
    }

    public void setModel(Modele model) {
        this.model = model;
    }
    

    public Long getId() {
        return id;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }

   

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public int getNombrePorte() {
        return nombrePorte;
    }

    public void setNombrePorte(int nombrePorte) {
        this.nombrePorte = nombrePorte;
    }

    public int getPuissanceFiscale() {
        return puissanceFiscale;
    }

    public void setPuissanceFiscale(int puissanceFiscale) {
        this.puissanceFiscale = puissanceFiscale;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(double kilometrage) {
        this.kilometrage = kilometrage;
    }

  

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return matricule;
    }

}
