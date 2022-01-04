package bean;

import bean.Car;
import bean.DemandeLocation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-01-03T15:29:29")
@StaticMetamodel(DemandeLocationDetail.class)
public class DemandeLocationDetail_ { 

    public static volatile SingularAttribute<DemandeLocationDetail, String> reference;
    public static volatile SingularAttribute<DemandeLocationDetail, DemandeLocation> demandeLocation;
    public static volatile SingularAttribute<DemandeLocationDetail, Car> car;
    public static volatile SingularAttribute<DemandeLocationDetail, Double> prix;
    public static volatile SingularAttribute<DemandeLocationDetail, Date> dateRetour;
    public static volatile SingularAttribute<DemandeLocationDetail, Long> id;

}