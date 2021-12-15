package bean;

import bean.DemandeLocationDetail;
import bean.Fuel;
import bean.Modele;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-12-02T22:07:40")
@StaticMetamodel(Car.class)
public class Car_ { 

    public static volatile SingularAttribute<Car, String> img;
    public static volatile SingularAttribute<Car, String> matricule;
    public static volatile SingularAttribute<Car, Fuel> fuel;
    public static volatile SingularAttribute<Car, Date> dateAchat;
    public static volatile ListAttribute<Car, DemandeLocationDetail> demandeLocationDetails;
    public static volatile SingularAttribute<Car, Modele> model;
    public static volatile SingularAttribute<Car, Long> id;

}