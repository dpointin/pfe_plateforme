package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import modele.Action;
import modele.Indice;
import modele.Titre;

/**
* Classe TitreDaoImpl implementant l'interface TitreDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class TitreDaoImpl implements TitreDao {
	/**
	* SQL_SELECT_TOUS_TITRES correspond a la requete SQL de recherche de tous les codes dans la table Titre.
	*/ 
    private static final String SQL_SELECT_TOUS_TITRES = "SELECT code, libelle FROM Titre";
	
    
    /**
	* SQL_SELECT_PAR_CODE_TYPE_TITRE correspond a la requete SQL de recherche du type d'un titre correspondant a un code dans la table Titre.
	*/ 
    private static final String SQL_SELECT_PAR_CODE_TYPE_TITRE = "SELECT type FROM Titre WHERE code = ?";
	
    
    /**
	* SQL_SELECT_PAR_CODE correspond a la requete SQL de recherche d'un titre en fonction de son code dans la table Titre.
	*/ 
    private static final String SQL_SELECT_PAR_CODE = "SELECT * FROM Titre WHERE code = ?";
	
    
    /**
	* La daoFactory qui va permettre la connection a la base de donnee.
	*/     
    private DAOFactory daoFactory;

    
    
    /**
   	* Constructeur TitreDaoImpl.
   	* <p>
   	* Avec le parametre daoFactory
   	* </p>
   	*
   	* @param daoFactory
   	* La Fabrique dao du titreDaoImpl.
   	*
   	* @see TitreDaoImpl#daoFactory
   	* @see DAOFactory
   	*/ 
    TitreDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
  
    /**
	* Implementation de la methode definie dans l'interface TitreDao
	*
	* @return ArrayList<String> correspondant a l'ensemble des codes
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDao
	*/ 
	@Override
	public ArrayList<String> trouverTousCodes() throws DAOException {
		Connection con=null;
		PreparedStatement pre=null;
		ResultSet res=null;
		ArrayList<String> sol=null;
		try {
			con=daoFactory.getConnection();
			pre=initialisationRequetePreparee(con, SQL_SELECT_TOUS_TITRES, false);
			res=pre.executeQuery();
			sol=new ArrayList<String>();
			while(res.next()) 
				sol.add(res.getString("code"));
		}
		catch ( SQLException e){
			throw new DAOException(e);
		}finally{
			fermeturesSilencieuses(pre,con);
		}
		return sol;
	}
	
	
	/**
	* Implementation de la methode definie dans l'interface TitreDao
	*
	* @return ArrayList<Titre> correspondant a l'ensemble des titres
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDao
	* @see TitreDaoImpl#recupererTitre(String)
	*/ 
	@Override
	public ArrayList<Titre> trouverTousTitres() throws DAOException {
		ArrayList<String> codes = trouverTousCodes();
		ArrayList<Titre> titres = new ArrayList<Titre>();
		for (String code : codes) {
			titres.add(recupererTitre(code));
		}
		return titres;
	}
	

	/**
	* Implementation de la methode definie dans l'interface TitreDao
	*
	* @param code dont on veut savoir le type
	*
	* @return String correspondant au type
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDao
	* @see TitreDaoImpl#recupererTypeTitre(String, String)
	*/ 
	@Override
	public String recupererTypeTitre(String code) throws DAOException {
		return recupererTypeTitre(SQL_SELECT_PAR_CODE_TYPE_TITRE, code);
	}
	

	/**
	* Implementation de la methode definie dans l'interface TitreDao
	*
	* @param code dont on veut recuperer le titre
	*
	* @return Titre correspondant au code
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDao
	* @see TitreDaoImpl#recupererIndice(String, String)
	* @see TitreDaoImpl#recupererAction(String, String)
	*/
	@Override
	public Titre recupererTitre(String code) throws DAOException {
		if (recupererTypeTitre(code).equals("INDICE")) {
			return recupererIndice(SQL_SELECT_PAR_CODE, code);
		} else {
			return recupererAction(SQL_SELECT_PAR_CODE, code);
		}
	}
	
	
	/**
	* Methode privee permettant de recuperer un indice
	* 
	* @param sql correspond a la requete sql
	* @param code correspond au code de l'indice
	*
	* @return Indice que l'on veut trouver
	* 
	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDao
	* @see Indice
	*/ 
	private Indice recupererIndice(String sql, String code) throws DAOException {
      	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Indice indice = null;
	    
	    try {
	        /* Recuperation d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	         // Preparation de la requête avec les objets passes en arguments
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false, code);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donnees retournee dans le ResultSet */
	        if ( resultSet.next() ) {
	        	String libelle = resultSet.getString("libelle");
	        	Integer nbDispo = resultSet.getInt("nombreDisponible");
	        	indice = new Indice(code,libelle,nbDispo);
	        	HistoriqueDao h_dao=new HistoriqueDaoImpl(daoFactory);
	        	indice.setHistorique(h_dao.trouver(code, new GregorianCalendar(2016,1,2),new GregorianCalendar()));
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
		return indice;
	}
	
	
	/**
	* Methode privee permettant de recuperer une action
	* 
	* @param sql correspond a la requete sql
	* @param code correspond au code de l'action
	*
	* @return Action que l'on veut trouver
	* 
	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDao
	* @see Action
	*/
	private Action recupererAction(String sql, String code) throws DAOException {
      	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Action action = null;
	    
	    try {
	        /* Recuperation d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        // Preparation de la requête avec les objets passes en arguments
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false, code);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donnees retournee dans le ResultSet */
	        if ( resultSet.next() ) {
	        	String libelle = resultSet.getString("libelle");
	        	Integer nbDispo = resultSet.getInt("nombreDisponible");
	        	Double dividende = (double) resultSet.getFloat("rendementDividende");
	        	action = new Action(code,libelle,nbDispo,dividende);
	        	HistoriqueDao h_dao=new HistoriqueDaoImpl(daoFactory);
	        	action.setHistorique(h_dao.trouver(code, new GregorianCalendar(2016,1,2),new GregorianCalendar()));
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
		return action;
	}	
	
	
	/**
	* Methode privee permettant de recuperer le type d'un titre
	* 
	* @param sql correspond a la requete sql
	* @param code correspond au code du titre
	*
	* @return Type que l'on veut trouver
	* 
	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDao
	* @see Indice
	* @see Action
	*/
	private String recupererTypeTitre(String sql, String code) throws DAOException {
      	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String res = null;
	    
	    try {
	        /* Recuperation d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        // Preparation de la requête avec les objets passes en arguments
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false, code);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donnees retournee dans le ResultSet */
	        if ( resultSet.next() ) {
	        	res = resultSet.getString("type");
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
		return res;
	}

}