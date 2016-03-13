package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modele.Joueur;

/**
* Classe JoueurDaoImpl implémentant l'interface JoueurDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class JoueurDaoImpl implements JoueurDao {
	/**
	* SQL_SELECT_PAR_LOGIN correspond a la requete SQL de recherche par login dans la table joueur.
	*/ 
    private static final String SQL_SELECT_PAR_LOGIN = "SELECT login, motDePasse FROM Joueur WHERE login = ?";
    
    
    /**
	* SQL_INSERT correspond a la requete SQL d'insertion dans la table joueur.
	*/ 
    private static final String SQL_INSERT           = "INSERT INTO Joueur (login, motDePasse) VALUES (?, ?)";

    
    /**
	* La daoFactory qui va permettre la connection a la base de donnee.
	*/ 
    private DAOFactory daoFactory;

    
    /**
	* Constructeur JoueurDaoImpl.
	* <p>
	* Avec le parametre daoFactory
	* </p>
	*
	* @param daoFactory
	* La Fabrique dao du joueurDaoImpl.
	*
	* @see JoueurDaoImpl#daoFactory
	* @see DAOFactory
	*/ 
    JoueurDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    
    /**
	* Implementation de la methode definie dans l'interface JoueurDao
	*
	* @return joueur que l'on veut trouver
	* 
	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
	* 
	* @see Joueur
	* @see DAOException
	* @see JoueurDao
	* @see JoueurDaoImpl#trouver(String, Object...)
	*/ 
    @Override
    public Joueur trouver( String login ) throws DAOException {
    	return trouver( SQL_SELECT_PAR_LOGIN, login );
    }

    
    /**
	* Implémentation de la méthode définie dans l'interface JoueurDao 
	*
	* @param joueur que l'on veut ajouter
	* 
	* @throws DAOException Si une erreur arrive lors de l'ajout a la bdd
	* 
	* @see Joueur
	* @see DAOException
	* @see JoueurDao
	*/ 
    @Override
    public void creer( Joueur joueur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, joueur.getLogin(), joueur.getMotDePasse());
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création du joueur, aucune ligne ajoutée dans la table." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }


	@Override
	public void supprimer(String login) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	
    /**
	* Methode qui retourne un utilisateur depuis la base
    * de donnees, correspondant à la requete SQL donnee prenant en parametres
    * les objets passes en argument.
	* 
	* @param sql
	* la requete sql
	* @param objets
	* les parametre de la requete sql
	*
	* @return le joueur de la base de donnes
	* 
	* @throws DAOException s'il y a une erreur lors du traitement de la requete
	* @see Joueur
	* @see DAOException
	* @see PreparedStatement
	*/ 
    private Joueur trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Joueur joueur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            // Préparation de la requête avec les objets passés en arguments
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
                joueur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return joueur;
    }

   
    /**
	* Methode qui fait la correspondance entre une ligne de la table
	* et un Joueur du modele
	* 
	* @param ResultSet
	* le resultSet contenant la ligne de la table
	*
	* @return le joueur de la base de donnee transforme en joueur du modele
	* 
	* @throws SQLException s'il y a une erreur lors du traitement de la requete
	* 
	* @see Joueur
	* @see SQLException
	* @see ResultSet
	*/ 
    private static Joueur map( ResultSet resultSet ) throws SQLException {
        Joueur joueur = new Joueur();
        joueur.setLogin( resultSet.getString( "login" ) );
        joueur.setMotDePasse( resultSet.getString( "motDePasse" ) );
        return joueur;
    }



}