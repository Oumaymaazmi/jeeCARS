/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.primefaces.freya.view;


import bean.User;
import controller.util.AES;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import service.UserFacade;

/**
 *
 * @author pc
 */
@Named
@ViewScoped
public class UserCrudView implements Serializable {
    final String secretKey = "ssshhhhhhhhhhh!!!!";
    private String pass="";
    private List<User> users;
    private String error;
    private User selectedUser;

    private List<User> selectedUsers;

    @Inject
    private UserFacade userService;

    @Inject
    private UserFacade userFacade;

    @PostConstruct
    public void init() {
//        this.users = this.userService.getUsers();
        this.users = this.userFacade.findAll();
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
    
     public void openNew() {
        this.selectedUser = new User();
    }
     
     public void saveUser() {
        if (this.selectedUser.getId() == null) {
           this.users.add(this.selectedUser);
            //System.out.println(selectedUser.getLogin() + " " + selectedUser.getPassword());
            selectedUser.setPassword(AES.encrypt(selectedUser.getPassword(), secretKey));
           // System.out.println("encrypted password " + selectedUser.getPassword());
            this.userFacade.create(selectedUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Added"));
        } else {
            this.userFacade.edit(selectedUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Updated"));
        }
      //   System.out.println(PrimeFaces.current());
        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");
         System.out.println("ajax executed");
    }
     public void signUpUser(){
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
         
     }
     public boolean hasSelectedUsers() {
        return this.selectedUsers != null && !this.selectedUsers.isEmpty();
    }
     public void deleteUser() {
        this.userFacade.remove(selectedUser);
        this.users.remove(selectedUser);
        this.selectedUser = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Removed"));
        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");
    }
     
      public String getDeleteButtonMessage() {
        if (hasSelectedUsers()) {
            int size = this.selectedUsers.size();
            return size > 1 ? size + " users selected" : "1 user selected";
        }

        return "Delete";
    }
       public void deleteSelectedUsers() {
        this.userFacade.removeAll(this.selectedUsers);
        this.selectedUsers = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");
        
    }
    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            User user = (User) event.getData();
//            if (car.getOrders() == null) {
//                car.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
        }
    }
    
}
