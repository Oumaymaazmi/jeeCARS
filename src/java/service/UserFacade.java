/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import controller.util.AES;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author faiss
 */
@javax.ejb.Stateful
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "cars-projectPU")
    private EntityManager em;

    public User findBylogin(String login) {
        return findBy("login", login);
    }

    public int seConnecter(String login, String password) {
         User loadedUser = findBylogin(login);
        String encodedPassword=AES.encrypt(password,"ssshhhhhhhhhhh!!!!");
        System.out.println(encodedPassword);
        if (loadedUser == null) {
             System.out.println("no user found");
            return -1;
           
        } else if (!loadedUser.getPassword().equals(encodedPassword)) {
            System.out.println("wrong password");
            return -2;
        } else {
            System.out.println("logged in");
            return 1;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

}
