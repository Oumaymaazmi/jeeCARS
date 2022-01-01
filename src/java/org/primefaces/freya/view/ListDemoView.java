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
import bean.Car;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.freya.domain.Product;
import org.primefaces.freya.service.ProductService;

import org.primefaces.model.DualListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import service.AnnonceFacade;
import service.AnnonceFilterProcess;
import service.CarFacade;
import service.ModeleFacade;

@Named
@ViewScoped
public class ListDemoView implements Serializable {

    private List<Annonce> annonces;
    @EJB
    private AnnonceFacade annonceFacade;
    @EJB
    private AnnonceFilterProcess annonceFilterProcess;

    private DualListModel<String> cities1;

    private List<String> cities2;

    private List<Product> products;

    private double price = 65.00;

    @PostConstruct
    public void init() {
//    Date dateAnnonce, String fuel, String model, String transmission, int nombrePorte, int puissance, int kilometrage, double prix, Date dateAchat) {

        this.annonces = this.annonceFacade.findAll();
        this.annonceFilterProcess.search(null,"diesel",null,"automatic",0,0,0,0,null);
        System.out.println("caaaars " + annonces.size());

    }

    public void sendDetaisAnnonce(Annonce annonce) {
        FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap().put("annonce", annonce);
      //  return "annonce";

    }

    public DualListModel<String> getCities1() {
        return cities1;
    }

    public List<String> getCities2() {
        return cities2;
    }

    public List<Product> getProducts() {
        return products;
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

}
