/**
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
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import service.AnnonceFacade;
import service.CarFacade;
import service.ModeleFacade;

/**
 *
 * @author a
 */
@Named
@SessionScoped
public class AnnonceDetail implements Serializable {

    private List<Annonce> annonces;
    @EJB
    private AnnonceFacade annonceFacade;

    private DualListModel<String> cities1;

    private List<String> cities2;

    private List<Product> products;

    private Annonce detail;

    private double price = 65.00;

    @PostConstruct
    public void init() {
        //        detail = (Annonce) FacesContext.getCurrentInstance().getExternalContext()
//                .getRequestMap().get("annonce");
//               System.out.println("frooom annonce " +annonceDetail.getCar().getMatricule());
        // System.out.println("hahia annonce " + this.getAnnonceDetail().getCar().getMatricule());

    }

    public void sendData(ActionEvent event) {
        System.out.println("ana dkhalt ");
        Annonce detaill = (Annonce) event.getComponent().getAttributes().get("detail");
        this.setDetail(detaill);
        System.out.println("image " + detaill.getCar().getRepresentatives().size());

        System.out.println("data = " + this.detail.getUser().getPhone());
//         Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        String test = params.get("annonce");
//        return test;
    }

    public String afficher() {
        return this.getDetail().getCar().getMatricule();
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

    public Annonce getDetail() {
        return detail;
    }

    public void setDetail(Annonce detail) {
        this.detail = detail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
