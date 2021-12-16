/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.primefaces.freya.view;

import bean.User;
import controller.util.AES;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.UserFacade;

/**
 *
 * @author pc
 */
@Named("signUpView")
@ViewScoped
public class SignUpView implements Serializable{
     final String secretKey = "ssshhhhhhhhhhh!!!!";
    private String pass="";
    private String error;
    private User selectedUser;
    
     @Inject
    private UserFacade userService;

    @Inject
    private UserFacade userFacade;

    @PostConstruct
    public void init() {
        this.selectedUser=new User();
//        this.users = this.userService.getUsers();
        //System.out.println(users.isEmpty());
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    public String signUpUser(){
         System.out.println("in Sing up");
         if(!this.pass.equals("")){
             if(this.pass.equals(this.selectedUser.getPassword())){
                 selectedUser.setPassword(AES.encrypt(selectedUser.getPassword(), secretKey));
                 this.userFacade.create(selectedUser);
             }else{
             this.error="the password does not match";
            }
         }else{
             this.error="please confirm the password";
         }
          return "/dashboard?faces-redirect=true";
     }
}
