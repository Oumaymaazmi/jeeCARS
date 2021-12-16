/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Transmission;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author a
 */
@Stateless
public class TransmissionFacade extends AbstractFacade<Transmission> {

    @PersistenceContext(unitName = "cars-projectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransmissionFacade() {
        super(Transmission.class);
    }

   
}
