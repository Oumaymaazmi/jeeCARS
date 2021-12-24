package org.primefaces.freya.view;

import bean.Car;
import bean.Fuel;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import service.CarFacade;
import service.FuelFacade;
import service.ModeleFacade;

@Named
@ViewScoped
public class CrudDemoView implements Serializable {

    private List<Car> cars;

    private Car selectedCar;

    private List<Car> selectedCars;

    @EJB
    private CarFacade carFacade;
    @EJB
    private FuelFacade fuelFacade;
    private List<Fuel> fuels;
    @EJB
    private ModeleFacade modeleFacade;
    private List<Modele> modele;

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    @PostConstruct
    public void init() {
        this.cars = this.carFacade.findAll();
        this.fuels = this.fuelFacade.findAll();
        this.modele = this.modeleFacade.findAll();


    }

    public List<Car> getCars() {
        return cars;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public List<Car> getSelectedCars() {
        return selectedCars;
    }

    public void setSelectedCars(List<Car> selectedCars) {
        this.selectedCars = selectedCars;
    }

    public void openNew() {
        this.selectedCar = new Car();
    }

    public void upload(FileUploadEvent event) {
        this.file = event.getFile();
        System.out.println("imaage " + this.file.getFileName());

    }

    public void saveCar() throws IOException {

        if (this.selectedCar.getId() == null) {
            this.selectedCar.setId((long) carFacade.findAll().size() + 1);
            // this.selectedCar.setImg();
//            this.cars.add(this.selectedCar);
          //  this.selectedCar.setRepresentatives(this.file.getFileName());
            System.out.println(selectedCar.getMatricule() + " " + selectedCar.getDateAchat().toString());
            this.carFacade.create(selectedCar);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Car Added"));
            this.reload();
        } else {
            //this.selectedCar.setImg(this.file.getFileName());

            this.carFacade.edit(selectedCar);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Car Updated"));
            this.reload();
            
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");
    }

    public void deleteCar() {
        this.carFacade.remove(selectedCar);
        this.selectedCar = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Car Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCars()) {
            int size = this.selectedCars.size();
            return size > 1 ? size + " carss selected" : "1 Car selected";
        }

        return "Delete";
    }

    public boolean hasSelectedCars() {
        return this.selectedCars != null && !this.selectedCars.isEmpty();
    }

    public void deleteSelectedCars() {
        this.carFacade.removeAll(this.selectedCars);
        this.selectedCars = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cars Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            Car car = (Car) event.getData();
//            if (car.getOrders() == null) {
//                car.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
        }
    }

    public FuelFacade getFuelFacade() {
        return fuelFacade;
    }

    public void setFuelFacade(FuelFacade fuelFacade) {
        this.fuelFacade = fuelFacade;
    }

    public List<Fuel> getFuels() {
        return fuels;
    }

    public void setFuels(List<Fuel> fuels) {
        this.fuels = fuels;
    }

    public List<Modele> getModele() {
        return modele;
    }

    public void setModele(List<Modele> modele) {
        this.modele = modele;
    }

}
