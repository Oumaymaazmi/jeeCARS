/**
 *  Copyright 2009-2020 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.primefaces.freya.view;

import bean.Annonce;
import bean.Fuel;
import bean.Marque;
import bean.Transmission;
import java.io.Serializable;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import service.AnnonceFacade;
import service.AnnonceFilterProcess;
import service.FuelFacade;
import service.MarqueFacade;
import service.TransmissionFacade;

@Named
@ViewScoped
public class ListDemoView implements Serializable {

    private List<Annonce> annonces;
    @EJB
    private AnnonceFacade annonceFacade;
    @EJB
    private MarqueFacade marqueFacade;
     @EJB
    private TransmissionFacade transmissionFacade;
    @EJB
    private FuelFacade fuelFacade;
    @EJB
    private AnnonceFilterProcess annonceFilterProcess;
    
    private List<Fuel> fuels;
    private List<Marque> marques;
    private List<Transmission> transmissions;
    
    private Long fuelSelected;
    private Long marqueSelected;
    private Long transmission;
    private Date dateAchatSelected;
    private int porteSelected;
    private int puissance;
    private double kilometrage;
    private double priceSelected;
    private double price = 65.00;
    
    @PostConstruct
    public void init() {
        this.fuels = this.fuelFacade.findAll();
        this.transmissions = this.transmissionFacade.findAll();
        this.marques = this.marqueFacade.findAll();
        this.annonces = this.annonceFacade.findAll();
        
        System.out.println("annonces " + annonces.size());

    }

    public void sendDetaisAnnonce(Annonce annonce) {
        FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap().put("annonce", annonce);
      //  return "annonce";

    }

     public void search(){
        this.annonces = this.annonceFilterProcess.search(this.dateAchatSelected,this.fuelSelected,this.marqueSelected,this.transmission,this.porteSelected,this.puissance,this.kilometrage,this.priceSelected);
     }

    public List<Fuel> getFuels() {
        return fuels;
    }

    public void setFuels(List<Fuel> fuels) {
        this.fuels = fuels;
    }

    public Long getFuelSelected() {
        return fuelSelected;
    }

    public void setFuelSelected(Long fuelSelected) {
        this.fuelSelected = fuelSelected;
    }

    public double getPriceSelected() {
        return priceSelected;
    }

    public void setPriceSelected(double priceSelected) {
        this.priceSelected = priceSelected;
    }
    
   

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    public AnnonceFacade getAnnonceFacade() {
        return annonceFacade;
    }

    public void setAnnonceFacade(AnnonceFacade annonceFacade) {
        this.annonceFacade = annonceFacade;
    }

    public List<Marque> getMarques() {
        return marques;
    }

    public void setMarques(List<Marque> marques) {
        this.marques = marques;
    }

    public List<Transmission> getTransmissions() {
        return transmissions;
    }

    public void setTransmissions(List<Transmission> transmissions) {
        this.transmissions = transmissions;
    }

    public Long getMarqueSelected() {
        return marqueSelected;
    }

    public void setMarqueSelected(Long marqueSelected) {
        this.marqueSelected = marqueSelected;
    }

    public Long getTransmission() {
        return transmission;
    }

    public void setTransmission(Long transmission) {
        this.transmission = transmission;
    }
    

    public Date getDateAchatSelected() {
        return dateAchatSelected;
    }

    public void setDateAchatSelected(Date dateAchatSelected) {
        this.dateAchatSelected = dateAchatSelected;
    }

    public int getPorteSelected() {
        return porteSelected;
    }

    public void setPorteSelected(int porteSelected) {
        this.porteSelected = porteSelected;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(double kilometrage) {
        this.kilometrage = kilometrage;
    }
    
    
}
