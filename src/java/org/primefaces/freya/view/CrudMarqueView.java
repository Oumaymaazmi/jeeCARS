package org.primefaces.freya.view;

import bean.Car;
import bean.Marque;
import java.io.IOException;
import javax.inject.Inject;
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
import service.CarFacade;
import service.MarqueFacade;

@Named
@ViewScoped
public class CrudMarqueView implements Serializable {

    private List<Marque> marques;

    private Marque selectedMarque;

    private List<Marque> selectedMarques;
 
    @EJB
    private MarqueFacade marqueFacade ;

    @PostConstruct
    public void init() {
//        this.cars = this.carService.getCars();
        this.marques = this.marqueFacade.findAll();
        System.out.println(this.marques);
    }

    public List<Marque> getMarques() {
        return marques;
    }

    public Marque getSelectedMarque() {
        return selectedMarque;
    }

    public void setSelectedMarque(Marque selectedMarque) {
        this.selectedMarque = selectedMarque;
    }

    public List<Marque> getSelectedMarques() {
        return selectedMarques;
    }
    public void setSelectedMarques(List<Marque> selectedMarques) {
        this.selectedMarques = selectedMarques;
    }

    public void openNew() {
        this.selectedMarque = new Marque();
    }
    
    public void reload() throws IOException {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
}
    public void saveMarque() throws IOException {
        if (this.selectedMarque.getId() == null) {
//            this.cars.add(this.selectedCar);
            this.selectedMarque.setId((long) marqueFacade.findAll().size() + 1);
            System.out.println(selectedMarque.getLibelle());
            this.marqueFacade.create(selectedMarque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Added"));
            this.reload();
        } else {
            this.marqueFacade.edit(selectedMarque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marques");
    }

    public void deleteMarque() throws IOException {
        this.marqueFacade.remove(selectedMarque);
        this.marqueFacade = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Removed"));
        this.reload();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marques");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMarques()) {
            int size = this.selectedMarques.size();
            return size > 1 ? size + " Marque selected" : "1 Car selected";
        }

        return "Delete";
    }

    public boolean hasSelectedMarques() {
        return this.selectedMarques != null && !this.selectedMarques.isEmpty();
    }

    public void deleteSelectedMarques() throws IOException {
        this.marqueFacade.removeAll(this.selectedMarques);
        this.selectedMarques = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");
        this.reload();

    }

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            Marque marque = (Marque) event.getData();
//            if (car.getOrders() == null) {
//                car.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
        }
    }
}
