/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Annonce;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

/**
 *
 * @author a
 */
@javax.ejb.Stateless
public class AnnonceFacade extends AbstractFacade<Annonce> {

    @PersistenceContext(unitName = "cars-projectPU")
    private EntityManager em;

    public AnnonceFacade() {
        super(Annonce.class);

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
