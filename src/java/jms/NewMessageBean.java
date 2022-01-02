/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;

import bean.Annonce;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import org.primefaces.freya.view.CrudDemoView;
import service.AnnonceFacade;

/**
 *
 * @author a
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewMessageBean implements MessageListener {

    @Resource
    private MessageDrivenContext mdctx;

    @EJB
    private AnnonceFacade annonceFacade;
     @Inject
    private CrudDemoView crudDemoView;

    public NewMessageBean() {
    }

    @Override
    public void onMessage(Message message) {

        try {
            ObjectMessage msg = (ObjectMessage) message;
            Annonce annonce = (Annonce) msg.getObject();
            // annonceFacade.create(annonce);
            List<Annonce> annonces = crudDemoView.getAnnonces();
            annonces.add(annonce);
            crudDemoView.setAnnonces(annonces);
            annonces.stream().forEach(a -> {
                System.out.println("annonc  " + a.getId());

            });
        } catch (JMSException ex) {
            Logger.getLogger(NewMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public CrudDemoView getCrudDemoView() {
        return crudDemoView;
    }

    public void setCrudDemoView(CrudDemoView crudDemoView) {
        this.crudDemoView = crudDemoView;
    }
    
    

}
