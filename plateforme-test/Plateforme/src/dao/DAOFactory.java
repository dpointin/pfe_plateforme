package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
* Classe DAOFactory representant la fabrique DAO
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class DAOFactory {
	/**
	* Le FICHIER_PROPERTIES correspond a la localisation du fichier dao.properties.
	*/ 
	private static final String FICHIER_PROPERTIES = "dao.properties";
	
	
	/**
	* Le PROPERTY_URL correspond a l'url du fichier dao.properties.
	*/ 
	private static final String PROPERTY_URL = "url";
	
	
	/**
	* Le PROPERTY_DRIVER correspond au driver du fichier dao.properties.
	*/ 
	private static final String PROPERTY_DRIVER = "driver";
	
	
	/**
	* Le PROPERTY_NOM_UTILISATEUR correspond au nom utilisateur du fichier dao.properties.
	*/ 
	private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
	
	
	/**
	* Le PROPERTY_MOT_DE_PASSE correspond au mot de passe du fichier dao.properties.
	*/ 
	private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";
	
	
	/**
	* L'url de la bdd
	*/ 
	private String url;
	
	
	/**
	* Le nom d'utilisateur de la bdd.
	*/ 
	private String username;
	
	
	/**
	* Le mot de passe de la bdd.
	*/ 
	private String password;
	
	
	/**
	* Constructeur DAOFactory.
	* <p>
	* Avec les parametres url, nom utilisateur et mot de passe
	* </p>
	*
	* @param url
	* L'url de la bdd.
	* @param username
	* Le nom d'utilisateur de la bdd
	* @param password
	* Le mot de passe de la bdd
	*/ 	
	DAOFactory( String url, String username, String password ) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	
	/**
	* Méthode chargée de récupérer les informations de connexion à la base de
	* données, charger le driver JDBC et retourner une instance de la Factory	
	*
	* @return une instance de la fabrique
	* 
	* @throws DAOConfigurationException Si une erreur arrive lors de la configuration de la BDD
	* 
	* @see DAOConfigurationException
	* @see Properties
	*/ 
	public static DAOFactory getInstance() throws DAOConfigurationException {
		Properties properties = new Properties();
		String url;
		String driver;
		String nomUtilisateur;
		String motDePasse;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
		if ( fichierProperties == null ) {
			throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
		}
		try {
			properties.load( fichierProperties );
			url = properties.getProperty( PROPERTY_URL );
			driver = properties.getProperty( PROPERTY_DRIVER );
			nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
			motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
		} catch ( IOException e ) {
			throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
		}
		try {
			Class.forName( driver );
		} catch ( ClassNotFoundException e ) {
			throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
		}
		DAOFactory instance = new DAOFactory( url, nomUtilisateur, motDePasse );
			return instance;
		}
		/* Méthode chargée de fournir une connexion à la base de données */
		/* package */ 
		Connection getConnection() throws SQLException {
		return DriverManager.getConnection( url, username, password );
	}
		
	
	// Méthodes de récupération de l'implémentation des différents DAO (un seul pour le moment)
	/**
	* Retourne une implémentation d'un joueurDAO.
	*
	* @return un joueurDao.
	* 
	* @see JoueurDao
	* @see JoueurDaoImpl
	*/ 	
	public JoueurDao getJoueurDao() {
		return new JoueurDaoImpl( this );
	}
	
	/**
	* Retourne une implémentation d'un historiqueDAO.
	*
	* @return un historiqueDao.
	* 
	* @see HistoriqueDao
	* @see HistoriqueDaoImpl
	*/
	public HistoriqueDao getHistoriqueDao() {
		return new HistoriqueDaoImpl( this );
	}	
	
	/**
	* Retourne une implémentation d'un historiqueDAO.
	*
	* @return un historiqueDao.
	* 
	* @see HistoriqueDao
	* @see HistoriqueDaoImpl
	*/
	public PortefeuilleDao getPortefeuilleDao() {
		return new PortefeuilleDaoImpl( this );
	}	
	

	/**
	* Retourne une implémentation d'un titreDao.
	*
	* @return un titreDao.
	* 
	* @see TitreDao
	* @see TitreDaoImpl
	*/
	public TitreDao getTitreDao() {
		return new TitreDaoImpl( this );
	}

	
	/**
	* Retourne une implémentation d'un obligationDao.
	*
	* @return un obligationDao.
	* 
	* @see ObligationDao
	* @see ObligationDaoImpl
	*/
	public ObligationDao getObligationDao() {
		return new ObligationDaoImpl( this );
	}


	public EstComposeTitreDao getEstComposeTitreDao() {
		return new EstComposeTitreDaoImpl(this);
	}
	
	
	public EstComposeObligationDao getEstComposeObligationDao() {
		return new EstComposeObligationDaoImpl(this);
	}
} 