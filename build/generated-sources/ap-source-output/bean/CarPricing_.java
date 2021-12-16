package bean;

import bean.Modele;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-12-16T20:48:13")
@StaticMetamodel(CarPricing.class)
public class CarPricing_ { 

    public static volatile SingularAttribute<CarPricing, String> reference;
    public static volatile SingularAttribute<CarPricing, Date> dateMax;
    public static volatile SingularAttribute<CarPricing, Date> dateMin;
    public static volatile SingularAttribute<CarPricing, Modele> modele;
    public static volatile SingularAttribute<CarPricing, Double> montant;
    public static volatile SingularAttribute<CarPricing, Long> id;

}