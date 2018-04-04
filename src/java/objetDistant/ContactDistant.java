package objetDistant;

import daoJdbcMapping.ContactDAO;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import metierMapping.*;
import utilitairesMG.jdbc.AccesBase;
import utilitairesMG.jdbc.BaseDeDonnees;

@Stateless
public class ContactDistant implements ContactDistantRemote
{
    private BaseDeDonnees base;

// ----------------------------------------------------------------------------
// Chargement de l'EJB : ouverture de la connexion a la base
// ----------------------------------------------------------------------------
    @PostConstruct
    public void ouvreBase()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        
        base = new BaseDeDonnees("jdbc:mysql://localhost/gestioncontact?user=newuser&password=newuser");
        base.setFormatDate("dd/MM/yyyy");
    }

// ----------------------------------------------------------------------------
// Implementation de la methode de l'interface
// ----------------------------------------------------------------------------
    public Contact lireContact(Integer numeroContact) throws SQLException
    {
        Contact contact;
        AccesBase accesBase;
        ContactDAO contactDAO;

        accesBase = new AccesBase(base);
        contactDAO = new ContactDAO(accesBase);

        accesBase.getConnection();

        try
        {
            contact = new Contact();
            contact.setNumero(numeroContact);
            contactDAO.lire(contact);
        }
        finally
        {
            accesBase.closeConnection();
        }
        return contact;
    }
}
