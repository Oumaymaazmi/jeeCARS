/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import bean.Annonce;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.primefaces.shaded.json.JSONObject;
/**
 *
 * @author faiss
 */
@Stateless
public class AnnonceFilterProcess extends AbstractFacade<Annonce>{
    @PersistenceContext(unitName = "cars-projectPU")
    private EntityManager em;

    public AnnonceFilterProcess() {
        super(Annonce.class);
    }
    // Fuel fuel;
//     Modele model;
//     Representative representative;
//     Transmission transmission;
//     int nombrePorte;
//     int puissanceFiscale;   
//     double kilometrage;
//     Date dateAchat;
//     double prix;

    public List<Annonce> search(Date dateAnnonce, String fuel, String modele, String transmission, int nombrePorte, int puissance, int kilometrageMax, double prixMax, Date dateAchat) {

        String query = "" ;
        String tablesQuery = "";
        String clauseQuery = "and annonce.car = car.id";
        String tableJointure = "";
        String tableCar = ", car";
        if(fuel != null){
            tablesQuery += tableCar +", fuel";
            tableCar = "";
            clauseQuery += " and car.fuel_id = fuel.id and fuel.libelle = " + '"' + fuel + '"';
        }
        if(modele != null){
            tablesQuery += tableCar +", modele";
            tableCar = "";
            clauseQuery += " and car.modele_id = modele.id and modele.libelle = " + '"' + modele + '"';
        }
        if(transmission != null){
            tablesQuery += tableCar +", transmission";
            tableCar = "";
            clauseQuery += " and car.modele_id = transmission.id and transmission.libelle = " + '"' + transmission + '"';
        }
        if(prixMax > 0){
            tablesQuery += tableCar;
            tableCar = "";
            clauseQuery += " and car.prixmax >= " + '"' + prixMax + '"';
        }
        if(kilometrageMax > 0){
            tablesQuery += tableCar;
            tableCar = "";
            clauseQuery += " and car.kilometrage >= " + '"' + kilometrageMax + '"';
        }
        if(puissance > 0){
            tablesQuery += tableCar;
            tableCar = "";
            clauseQuery += " and car.puissance >= " + '"' + puissance + '"';
        }
        if(dateAchat != null){
            tablesQuery += tableCar;
            tableCar = "";
            clauseQuery += " and car.dateachat >= " + '"' + dateAchat + '"';
        }
        if(dateAnnonce != null ){
            tablesQuery += " ";
            clauseQuery = tableCar.isEmpty() ? " and dateannonce >=" + '"' + transmission + '"' : clauseQuery + " and dateannonce >=" + '"' + transmission + '"';
        }
        query = "SELECT annonce.* FROM annonce" + tablesQuery + " WHERE annonce.etat=\"valide\" " + clauseQuery;

        System.out.println("filtre:   " + query);

//        Query query2 = getEntityManager().createNativeQuery(query);
//
//        List<Object[]> res = query2.getResultList();
//
//        List<Annonce> list = new ArrayList<>();
//        JSONObject obj = new JSONObject();
//
//        Iterator it = res.iterator();
//        while (it.hasNext()) {
//            Object[] line = (Object[]) it.next();
//            Annonce annonce = new Annonce();
//            annonce.set((Long) line[0]);
//            annonce.setPrix((BigDecimal) line[11]);
//            annonce.setReference((String) line[12]);
//            annonce.setStatus((AnnonceStatus) line[13]);
//            list.add(annonce);
//        }
//
//        System.err.println("list " + list.size());
//
//        if (list == null || list.isEmpty()) {
//            return new ArrayList<>();
//        }

        return null;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
