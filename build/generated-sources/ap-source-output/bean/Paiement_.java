package bean;

import bean.DemandeLocation;
import bean.TypePaiement;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-01-03T15:29:29")
@StaticMetamodel(Paiement.class)
public class Paiement_ { 

    public static volatile SingularAttribute<Paiement, String> reference;
    public static volatile SingularAttribute<Paiement, DemandeLocation> demandeLocation;
    public static volatile SingularAttribute<Paiement, Date> datePaiement;
    public static volatile SingularAttribute<Paiement, TypePaiement> typePaiement;
    public static volatile SingularAttribute<Paiement, Double> montant;
    public static volatile SingularAttribute<Paiement, Long> id;

}