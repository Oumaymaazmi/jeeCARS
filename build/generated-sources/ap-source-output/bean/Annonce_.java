package bean;

import bean.Car;
import bean.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-12-16T19:59:26")
@StaticMetamodel(Annonce.class)
public class Annonce_ { 

    public static volatile SingularAttribute<Annonce, Car> car;
    public static volatile SingularAttribute<Annonce, Long> id;
    public static volatile SingularAttribute<Annonce, Date> dateAnnonce;
    public static volatile SingularAttribute<Annonce, String> etat;
    public static volatile SingularAttribute<Annonce, User> user;

}