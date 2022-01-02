package org.primefaces.freya.view;

import bean.Annonce;
import bean.Car;
import bean.Fuel;
import bean.Marque;
import bean.Modele;
import bean.Representative;
import bean.Transmission;
import bean.User;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.shaded.commons.io.FilenameUtils;
import service.AnnonceFacade;
import service.CarFacade;
import service.FuelFacade;
import service.MarqueFacade;
import service.ModeleFacade;
import service.RepresentativeFacade;
import service.TransmissionFacade;
import service.UserFacade;

@Named
@RequestScoped
public class CreateAnnonceView implements Serializable {

    @Resource(mappedName = "jms/test")
    private Queue myQueue;

    @Resource(mappedName = "jsmDemo/myMsg")
    private ConnectionFactory connectionFactory;

    private Annonce annonce;
    private String message = "test";
    private List<Marque> marques;
    private List<Fuel> fuels;
    private List<Modele> models;
    private List<Car> cars;
    private List<User> user;
    private List<Transmission> transmissions;
    private List<String> imgsPath;
    @EJB
    private UserFacade userFacade;
    @EJB
    private AnnonceFacade annonceFacade;
    @EJB
    private CarFacade carFacade;
    @EJB
    private MarqueFacade marqueFacade;
    @EJB
    private FuelFacade fuelFacade;
    @EJB
    private RepresentativeFacade representativeFacade;

    @EJB
    private ModeleFacade modeleFacade;
    @EJB
    private TransmissionFacade transmissionFacade;
    private UploadedFiles files;

    @PostConstruct
    public void init() {
//        this.cars = this.carService.getCars();

        this.marques = this.marqueFacade.findAll();
        this.fuels = this.fuelFacade.findAll();
        this.transmissions = this.transmissionFacade.findAll();
        this.models = this.modeleFacade.findAll();
        this.annonce = new Annonce();
        //  this.cars = this.carFacade.findAll();
        this.user = this.userFacade.findAll();
        //  this.imgsPath = new ArrayList<>();

        // this.models = this.modeleFacade.findBy('marque',this.annonce.getCar().getMarque());
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }

    public void save() throws JMSException, NamingException {
        if (this.annonce != null) {
            //this.annonce.setId((long) annonceFacade.findAll().size() + 1);
//            if (this.annonce.getCar() != null) {
//                this.annonce.getCar().setId((long) carFacade.findAll().size() + 1);
//            }

            if (files != null) {
                List<Representative> rs = new ArrayList<>();
                for (UploadedFile f : files.getFiles()) {
                    rs.add(new Representative(f.getFileName()));
                }
                this.annonce.getCar().setRepresentatives(rs);

            }
            sendJMSMessageToMyQueue(annonce);
        }

    }

    /*   public void upload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        String fileName = uploadedFile.getFileName();
        System.out.println("yyy"+fileName);

    // ... Save it, now!
}*/
 /*  public void upload() {
        
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else{
            System.out.println("yyy");
        }
    }*/
 /*public void upload() throws Exception {  

         if (file != null) {
        String fileName = file.getFileName(); 
        System.out.println("non 1"+fileName);  
         }
         else{
                 System.out.println("reed"); 
         }
                /*  if (this.files != null) {
                  for (UploadedFile f : files.getFiles()) {   
                  System.out.println("first 1");  
                  //this.file = event.getFile();
                  ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                  String resImgPath = servletContext.getRealPath("resources/demo/images/car/");
                  Path folder = Paths.get(resImgPath);
                  String filename = FilenameUtils.getBaseName(f.getFileName()); 
                  String extension = FilenameUtils.getExtension(f.getFileName());
                  Path filee = Files.createTempFile(folder, filename + "-", "." + extension);
                  try (InputStream input =  f.getInputStream()) {
                      Files.copy(input, filee, StandardCopyOption.REPLACE_EXISTING);
                  }
                   /*Representative representative = new Representative();
                   representative.setId((long) representativeFacade.findAll().size() + 1);
                   representative.setImage(filee.toString());
                   representative.setCar(carFacade.find((long)1));
                   representativeFacade.create(representative);
                 System.out.println("Uploaded file successfully saved in " + filee);
               
              }
            }*/
    public void saveAnnonce() throws IOException {
        //this.upload(event);   
        //System.out.println("hay"+this.imgsPath.size());
        System.out.println("annonce save " + this.annonce.getCar().getMatricule());
        System.out.println("annonce save image " + this.annonce.getCar().getRepresentatives().get(0).getImage());

    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public void send() {
        System.out.println("test");
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public CarFacade getCarFacade() {
        return carFacade;
    }

    public void setCarFacade(CarFacade carFacade) {
        this.carFacade = carFacade;
    }

    public List<Fuel> getFuels() {
        return fuels;
    }

    public List<Modele> getModels() {
        return models;
    }

    public void setModels(List<Modele> models) {
        this.models = models;
    }

    public ModeleFacade getModeleFacade() {
        return modeleFacade;
    }

    public void setModeleFacade(ModeleFacade modeleFacade) {
        this.modeleFacade = modeleFacade;
    }

    public void setFuels(List<Fuel> fuels) {
        this.fuels = fuels;
    }

    public List<Transmission> getTransmissions() {
        return transmissions;
    }

    public void setTransmissions(List<Transmission> transmissions) {
        this.transmissions = transmissions;
    }

    public FuelFacade getFuelFacade() {
        return fuelFacade;
    }

    public void setFuelFacade(FuelFacade fuelFacade) {
        this.fuelFacade = fuelFacade;
    }

    public TransmissionFacade getTransmissionFacade() {
        return transmissionFacade;
    }

    public void setTransmissionFacade(TransmissionFacade transmissionFacade) {
        this.transmissionFacade = transmissionFacade;
    }

    public List<Marque> getMarques() {
        return marques;
    }

    public void setMarques(List<Marque> marques) {
        this.marques = marques;
    }

    public MarqueFacade getMarqueFacade() {
        return marqueFacade;
    }

    public void setMarqueFacade(MarqueFacade marqueFacade) {
        this.marqueFacade = marqueFacade;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public AnnonceFacade getAnnonceFacade() {
        return annonceFacade;
    }

    public void setAnnonceFacade(AnnonceFacade annonceFacade) {
        this.annonceFacade = annonceFacade;
    }

    public void openNew() {
        //this.annonce = new Annonce();
        //this.annonce.setCar(new Car());
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    /*    if (this.annonce.getId() == null) { 
             this.annonce.setId((long) annonceFacade.findAll().size() + 1);
             this.annonce.getCar().setId((long)carFacade.findAll().size()+1 );
             this.annonce.setUser(this.user.get(0));
             System.out.println(this.annonce);
             System.out.println(this.annonce.getCar().getMatricule());
             System.out.println(this.annonce.getCar().getTransmission());
             for(String pathImg : this.imgsPath){
                 Representative representative = new Representative();
                 representative.setId((long) representativeFacade.findAll().size() + 1);
                 representative.setImage(pathImg);
                 representative.setCar(this.annonce.getCar());
             }
             this.annonceFacade.create(annonce);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("annonce Added"));
            this.reload();
        } else {
             System.out.println("hay");
            //this.annonceFacade.edit(annonce);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("annonce Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cars");*/
 /* public void deleteCar() {
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

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            Car car = (Car) event.getData();
//            if (car.getOrders() == null) {
//                car.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
        }
    }*/
    private void sendJMSMessageToMyQueue(Annonce annonce) throws JMSException {
        Connection connection = connectionFactory.createConnection("admin", "admin");
        System.out.println("connection " + connection);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(myQueue);
        ObjectMessage message = session.createObjectMessage(annonce);
        producer.send(message, DeliveryMode.PERSISTENT, 9, 0);
        System.out.println("annonce mchat " + message.getBody(Annonce.class));
    }

}
