/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Annonce;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import org.primefaces.freya.domain.Representative;

/**
 *
 * @author a
 */
@javax.ejb.Stateful
public class AnnonceFacade extends AbstractFacade<Annonce> {

    @PersistenceContext(unitName = "cars-projectPU")
    private EntityManager em;

    @EJB
    private CarFacade carFacade;
    @EJB
    private RepresentativeFacade representativeFacade;
    @EJB
    private UserFacade userFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnnonceFacade() {
        super(Annonce.class);

    }

    public void save(Annonce annonce) {
        System.out.println(annonce);
        if (annonce != null) {
            annonce.setId((long) findAll().size() + 1);
            annonce.getCar().setId((long) carFacade.findAll().size() + 1);
            annonce.setUser(null);
            create(annonce);
            System.out.println("f khatr aicha");
            annonce.getCar().getRepresentatives().stream().forEach(r -> {
                System.out.println("idddd " + r.getId());
                // Representative re = representativeFacade.findBy("id", r.getId().toString());
                r.getCar().setId(annonce.getCar().getId());
                representativeFacade.edit(r);
            });
            /* annonce.getCar().getRepresentatives().stream().forEach(r -> {
              r.setId((long) representativeFacade.findAll().size() + 1);
              r.getCar().setId(annonce.getCar().getId());
              representativeFacade.create(r);
            });*/
        }
    }
   

    /*public void save(Annonce annonce) {
        if (annonce != null) {
            annonce.setId((long) findAll().size() + 1);
            
            annonce.getCar().setId((long) carFacade.findAll().size() + 1);
            System.out.println("before create car ");
         //   carFacade.create( annonce.getCar());
            System.out.println("caaaaar "+annonce.getCar());
            //   annonce.setUser(this.user.get(0));
            System.out.println("after create car ");
         //   annonce.setUser(userFacade.findAll().get(0));
            
            create(annonce);
//            annonce.getCar().getRepresentatives().stream().forEach(r -> {
//                r.setId((long) representativeFacade.findAll().size() + 1);
//                r.getCar().setId(annonce.getCar().getId());
//                representativeFacade.create(r);
//            });
        }
    }
     */
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public CarFacade getCarFacade() {
        if (this.carFacade == null) {
            this.carFacade = new CarFacade();
        }
        return carFacade;
    }

    public void setCarFacade(CarFacade carFacade) {
        this.carFacade = carFacade;
    }

    public RepresentativeFacade getRepresentativeFacade() {
        return representativeFacade;
    }

    public void setRepresentativeFacade(RepresentativeFacade representativeFacade) {
        this.representativeFacade = representativeFacade;
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

}
