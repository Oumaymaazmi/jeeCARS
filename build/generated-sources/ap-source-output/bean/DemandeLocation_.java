package bean;

import bean.DemandeLocationDetail;
import bean.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-12-16T19:59:26")
@StaticMetamodel(DemandeLocation.class)
public class DemandeLocation_ { 

    public static volatile SingularAttribute<DemandeLocation, String> reference;
    public static volatile SingularAttribute<DemandeLocation, Double> total;
    public static volatile SingularAttribute<DemandeLocation, Double> totalPaye;
    public static volatile ListAttribute<DemandeLocation, DemandeLocationDetail> demandeLocationDetails;
    public static volatile SingularAttribute<DemandeLocation, String> description;
    public static volatile SingularAttribute<DemandeLocation, Long> id;
    public static volatile SingularAttribute<DemandeLocation, User> user;
    public static volatile SingularAttribute<DemandeLocation, Date> dateLocation;

}