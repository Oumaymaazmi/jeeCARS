/**
 *  Copyright 2009-2020 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.primefaces.freya.view;

import bean.Annonce;
import bean.Car;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.freya.domain.Product;
import org.primefaces.freya.service.ProductService;

import org.primefaces.model.DualListModel;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import bean.Representative;
import service.AnnonceFacade;
import service.CarFacade;
import service.FuelFacade;
import service.MarqueFacade;
import service.ModeleFacade;
import service.RepresentativeFacade;
import service.TransmissionFacade;
import service.UserFacade;

@ManagedBean
@RequestScoped
public class ListDemoView1 implements Serializable {

    @Resource(mappedName = "jms/test")
    private Queue myQueue;

    @Resource(mappedName = "jsmDemo/myMsg")
    private ConnectionFactory connectionFactory;
    private List<Annonce> annonces;
    private List<Annonce> annoncesAll = new ArrayList<>();
    private Annonce selectedAnnonce;
    @EJB
    private AnnonceFacade annonceFacade;

    @EJB
    private UserFacade userFacade;

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
    private DualListModel<String> cities1;

    private List<String> cities2;

    private List<Product> products;

    private double price = 65.00;

    @PostConstruct
    public void init() {

        try {
            recevJMSMessageFromMyQueue();
        } catch (JMSException ex) {
            Logger.getLogger(ListDemoView1.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.annonces= annonceFacade.findAll();

    }

    public String sendDetaisAnnonce(Annonce annonce) {

        Annonce test = (Annonce) FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap().put("annonce", "data");
        return "detail";

    }

    public DualListModel<String> getCities1() {
        return cities1;
    }

    public List<String> getCities2() {
        return cities2;
    }

    public Annonce getSelectedAnnonce() {
        return selectedAnnonce;
    }

    public void setSelectedAnnonce(Annonce selectedAnnonce) {
        this.selectedAnnonce = selectedAnnonce;
    }

    public void sendData() {
        
    }
    public List<Product> getProducts() {
        return products;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Annonce> getAnnonces() {
      
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    public AnnonceFacade getAnnonceFacade() {
        return annonceFacade;
    }

    public void setAnnonceFacade(AnnonceFacade annonceFacade) {
        this.annonceFacade = annonceFacade;
    }

    private void recevJMSMessageFromMyQueue() throws JMSException {
        Connection connection = connectionFactory.createConnection("admin", "admin");
        System.out.println("connection " + connection);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        QueueBrowser browser = session.createBrowser(myQueue);
        Enumeration<?> enumeration = browser.getEnumeration();
        while (enumeration.hasMoreElements()) {
            Message m = (Message) enumeration.nextElement();
            MessageConsumer consumer = session.createConsumer(myQueue, "JMSMessageID='" + m.getJMSMessageID() + "'");
            Message message = consumer.receive(1000);
            if (message != null) {
                if (this.annonces == null) {
                    this.annonces = new ArrayList<>();
                  
                    annonceFacade.save(message.getBody(Annonce.class));
                } else {
                    this.annonces.add(message.getBody(Annonce.class));
                }

                //    this.annoncesAll.add(message.getBody(Annonce.class));
                //System.out.println("message" + message.getBody(Annonce.class));
            }
        }
    }

   
}
