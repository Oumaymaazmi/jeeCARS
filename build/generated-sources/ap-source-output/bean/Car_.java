package bean;

import bean.Fuel;
import bean.Modele;
import bean.Representative;
import bean.Transmission;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-01-03T15:29:29")
@StaticMetamodel(Car.class)
public class Car_ { 

    public static volatile SingularAttribute<Car, Transmission> transmission;
    public static volatile SingularAttribute<Car, String> matricule;
    public static volatile SingularAttribute<Car, Integer> nombrePorte;
    public static volatile SingularAttribute<Car, Integer> puissanceFiscale;
    public static volatile SingularAttribute<Car, Double> prix;
    public static volatile SingularAttribute<Car, Fuel> fuel;
    public static volatile SingularAttribute<Car, Date> dateAchat;
    public static volatile SingularAttribute<Car, Double> kilometrage;
    public static volatile SingularAttribute<Car, Modele> model;
    public static volatile SingularAttribute<Car, Long> id;
    public static volatile ListAttribute<Car, Representative> representatives;

}