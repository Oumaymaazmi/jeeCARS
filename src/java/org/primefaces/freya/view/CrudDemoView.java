package org.primefaces.freya.view;

import bean.Annonce;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import service.CarFacade;
import service.FuelFacade;
import service.ModeleFacade;

@Named
@ApplicationScoped
public class CrudDemoView implements Serializable {

    @Resource(mappedName = "jms/test")
    private Queue myQueue;

    @Resource(mappedName = "jsmDemo/myMsg")
    private ConnectionFactory connectionFactory;
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

    private List<Annonce> annonces = new ArrayList<>();
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

    private void sendJMSMessageToMyQueue(Annonce annonce) {
        try {
            Connection con = connectionFactory.createConnection();
            Session s = con.createSession();
            MessageProducer mp = s.createProducer(myQueue);
            mp.setDeliveryMode(DeliveryMode.PERSISTENT);
            ObjectMessage tm = s.createObjectMessage(annonce);
            //  tm.setText(messageData);
            mp.send(tm);

        } catch (JMSException ex) {
            Logger.getLogger(CrudDemoView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void test() throws JMSException, NamingException {
        Annonce an = new Annonce();
        an.setId(new Long(annonces.size() + 1));
        // JMSContext context = connectionFactory.createContext("admin", "admin");
        Connection connection = connectionFactory.createConnection("admin", "admin");
        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//        QueueBrowser browser = session.createBrowser(myQueue);
//        Enumeration elems = browser.getEnumeration();
//        System.out.println("elsem" + elems.());
//        while (elems.hasMoreElements()) {
//            elems.nextElement();
//            System.out.println("elems " + elems.toString());
//        }
        // QueueBrowser qb = context.createBrowser(myQueue);
//
//sendJMSMessageToMyQueue(an);
//        InitialContext ctx = new InitialContext();
//        Queue queue = (Queue) ctx.lookup("jms/myQueue");
//        QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.
//                lookup("jsmDemo/myMsg");
//        QueueConnection queueConn = connFactory.createQueueConnection();
//        QueueSession session = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//
//        QueueBrowser queueBrowser = session.createBrowser(queue);
//
//        queueConn.start();
//        Enumeration e = queueBrowser.getEnumeration();
//      
//        while (e.hasMoreElements()) {
//            Message message = (Message) e.nextElement();
//            System.out.println("enim "+ message.getBody(Annonce.class));
//        }
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // javax.jms.Queue queue = session.createQueue(myQueue);
        MessageProducer producer = session.createProducer(myQueue);
//        TextMessage message = session.createTextMessage();
//        message.setText("hello");
//        producer.send(message, DeliveryMode.PERSISTENT, 5, 0);
//        message = session.createTextMessage();
//        message.setText("hello + 9");
//        producer.send(message, DeliveryMode.PERSISTENT, 9, 0);
        QueueBrowser browser = session.createBrowser(myQueue);
        Enumeration<?> enumeration = browser.getEnumeration();
        int count = 0;
        while (enumeration.hasMoreElements()) {
            Message m = (Message) enumeration.nextElement();
            count++;
            MessageConsumer consumer = session.createConsumer(myQueue, "JMSMessageID='" + m.getJMSMessageID() + "'");
            Message message = consumer.receive(1000);
            if (message != null) {
                System.out.println("message" + message.getBody(String.class));
            }
        }
        // System.out.println("count " + count);

    }

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

}
