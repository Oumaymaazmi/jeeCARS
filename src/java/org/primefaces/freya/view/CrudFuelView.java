package org.primefaces.freya.view;


import bean.Fuel;

import java.io.IOException;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import service.FuelFacade;


@Named
@ViewScoped
public class CrudFuelView implements Serializable {

    private List<Fuel> fuels;

    private Fuel selectedFuel;

    private List<Fuel> selectedFuels;
 
    @EJB
    private FuelFacade fuelFacade ;

    @PostConstruct
    public void init() {
//        this.cars = this.carService.getCars();
        this.fuels = this.fuelFacade.findAll();
        System.out.println(this.fuels);
    }

    public List<Fuel> getFuels() {
        return fuels;
    }

    public void setFuels(List<Fuel> fuels) {
        this.fuels = fuels;
    }

    public Fuel getSelectedFuel() {
        return selectedFuel;
    }

    public void setSelectedFuel(Fuel selectedFuel) {
        this.selectedFuel = selectedFuel;
    }

    public List<Fuel> getSelectedFuels() {
        return selectedFuels;
    }

    public void setSelectedFuels(List<Fuel> selectedFuels) {
        this.selectedFuels = selectedFuels;
    }

    public void openNew() {
        this.selectedFuel= new Fuel();
    }
    
    public void reload() throws IOException {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
}
    public void saveFuel() throws IOException {
        System.out.println("saving fuel");
        if (this.selectedFuel.getId() == null) {
//            this.cars.add(this.selectedCar);
            this.selectedFuel.setId((long) fuelFacade.findAll().size() + 1);
            System.out.println(selectedFuel.getLibelle());
            this.fuelFacade.create(selectedFuel);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Added"));
            this.reload();
        } else {
            this.fuelFacade.edit(selectedFuel);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marques");
    }

    public void deleteFuel() throws IOException {
        this.fuelFacade.remove(selectedFuel);
        this.fuelFacade = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Removed"));
        this.reload();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marques");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedFuels()) {
            int size = this.selectedFuels.size();
            return size > 1 ? size + " Marque selected" : "1 Car selected";
        }

        return "Delete";
    }

    public boolean hasSelectedFuels() {
        return this.selectedFuels != null && !this.selectedFuels.isEmpty();
    }

    public void deleteselectedFuels() throws IOException {
        this.fuelFacade.removeAll(this.selectedFuels);
        this.selectedFuels = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");
        this.reload();

    }

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            Fuel fuel = (Fuel) event.getData();
//            if (car.getOrders() == null) {
//                car.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
        }
    }
}
