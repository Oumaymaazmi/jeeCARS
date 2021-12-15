package org.primefaces.freya.view;

import bean.Car;
import bean.Marque;
import bean.Modele;
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
import service.ModeleFacade;

@Named
@ViewScoped
public class CrudModeleView implements Serializable {

    private List<Modele> models;

    private Modele selectedModel;

    private List<Modele> selectedModels;
    
  
    @Inject
    private ModeleFacade modeleFacade ;

    @PostConstruct
    public void init() {
//        this.cars = this.carService.getCars();
        this.models = this.modeleFacade.findAll();
    }

    public List<Modele> getModels() {
        return models;
    }

    public Modele getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedModel(Modele selectedModel) {
        this.selectedModel = selectedModel;
    }

    public List<Modele> getSelectedModels() {
        return selectedModels;
    }
    public void setSelectedModels(List<Modele> selectedModels) {
        this.selectedModels = selectedModels;
    }

    public void openNew() {
        this.selectedModel = new Modele();
    }
    
    public void reload() throws IOException {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
}
    public void saveModel() throws IOException {
        if (this.selectedModel.getId() == null) {
//            this.cars.add(this.selectedCar);
            this.selectedModel.setId((long) modeleFacade.findAll().size() + 1);
            System.out.println(selectedModel.getLibelle());
            this.modeleFacade.create(selectedModel);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modele Added"));
            this.reload();
        } else {
            this.modeleFacade.edit(selectedModel);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modele Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-models");
    }

    public void deleteModel() throws IOException {
        this.modeleFacade.remove(selectedModel);
        this.modeleFacade = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modele Removed"));
        this.reload();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-models");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedModels()) {
            int size = this.selectedModels.size();
            return size > 1 ? size + " modele selected" : "1 Modele selected";
        }

        return "Delete";
    }

    public boolean hasSelectedModels() {
        return this.selectedModels != null && !this.selectedModels.isEmpty();
    }

    public void deleteSelectedModels() throws IOException {
        this.modeleFacade.removeAll(this.selectedModels);
        this.selectedModels = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modele Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-models");
        this.reload();

    }

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            Modele model = (Modele) event.getData();
//            if (car.getOrders() == null) {
//                car.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
        }
    }
}
