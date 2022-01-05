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
import bean.Car;
import bean.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.ejb.EJB;
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
    @EJB
    private CarFacade carFacade;
    @EJB
    private UserFacade userFacade;
    public AnnonceFilterProcess() {
        super(Annonce.class);
    }

    public List<Annonce> search(Date dateFabrication, Long fuel, Long marque, Long transmission, Integer nombrePorte, Integer puissance, Double kilometrageMax, Double prixMax) {

        String query = "" ;
        String clauseQuery = "";
        String tables = "annonce, car";
        clauseQuery += fuel != null && fuel != 0 ? " and car.fuel_id = " + '"' + fuel + '"':"";
        clauseQuery += marque != null && marque != 0 ?" and car.model_id = modele.id and modele.marque_id=" + '"' + marque + '"': "";
        clauseQuery += transmission != null && transmission != 0 ?" and car.transmission_id = " + '"' + transmission + '"': "";
        clauseQuery += prixMax != null && prixMax > 0 ? " and car.prix >= " + '"' + prixMax + '"' : "";
        clauseQuery += kilometrageMax != null && kilometrageMax > 0 ? " and car.kilometrage >= " + '"' + kilometrageMax + '"' : "";
        clauseQuery += puissance !=null && puissance > 0 ? " and car.puissancefiscale >= " + '"' + puissance + '"' : "";
        clauseQuery += dateFabrication != null ? " and car.dateachat >= " + '"' + dateFabrication + '"' : "";
        tables = marque!= null && marque != 0 ? "annonce, car, modele ":"annonce, car ";
        query = clauseQuery.length() > 0  ? "SELECT annonce.* FROM "+ tables +"WHERE annonce.CAR_ID = car.id and annonce.etat=\"valide\""+ clauseQuery:"SELECT annonce.* FROM annonce WHERE annonce.etat=\"valide\" ";
        
        System.out.println("filtre:   " + query);
       
        Query query2 = getEntityManager().createNativeQuery(query);

        List<Object[]> res = query2.getResultList();

        List<Annonce> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            Annonce annonce = new Annonce();
            annonce.setId((Long) line[0]);
            annonce.setDateAnnonce((Date) line[1]);
            Car car = carFacade.find((Long) line[4]);
            User user = userFacade.find((Long) line[3]);
            if(car != null)annonce.setCar(car);
            if(user != null)annonce.setUser(user);
            
            list.add(annonce);
        }

        System.err.println("list " + list.size());

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        return list;
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
