package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Action;
import modele.Indice;
import modele.Titre;


public class TitreDaoImpl implements TitreDao {
    private static final String SQL_SELECT_TOUS_TIRES = "SELECT code, libelle FROM Titre";
	private static final String SQL_SELECT_PAR_CODE_TYPE_TITRE = "SELECT type FROM Titre WHERE code = ?";
	private static final String SQL_SELECT_PAR_CODE = "SELECT * FROM Titre WHERE code = ?";
	
    private DAOFactory daoFactory;

    TitreDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
  
	@Override
	public ArrayList<String> trouverTousCodes() throws DAOException {
		Connection con=null;
		PreparedStatement pre=null;
		ResultSet res=null;
		ArrayList<String> sol=null;
		try {
			con=daoFactory.getConnection();
			pre=initialisationRequetePreparee(con, SQL_SELECT_TOUS_TIRES, false);
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
	
	@Override
	public ArrayList<Titre> trouverTousTitres() throws DAOException {
		ArrayList<String> codes = trouverTousCodes();
		ArrayList<Titre> titres = new ArrayList<Titre>();
		for (String code : codes) {
			titres.add(recupererTitre(code));
		}
		return titres;
	}
	

	@Override
	public String recupererTypeTitre(String code) throws DAOException {
		return recupererTypeTitre(SQL_SELECT_PAR_CODE_TYPE_TITRE, code);
	}
	

	@Override
	public Titre recupererTitre(String code) throws DAOException {
		if (recupererTypeTitre(code).equals("INDICE")) {
			return recupererIndice(SQL_SELECT_PAR_CODE, code);
		} else {
			return recupererAction(SQL_SELECT_PAR_CODE, code);
		}
	}
	
	private Indice recupererIndice(String sql, String code) throws DAOException {
      	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Indice indice = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        /*
	         * Préparation de la requête avec les objets passés en arguments
	         * (ici, uniquement une adresse email) et exécution.
	         */
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false, code);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données retournée dans le ResultSet */
	        if ( resultSet.next() ) {
	        	String libelle = resultSet.getString("libelle");
	        	Integer nbDispo = resultSet.getInt("nombreDisponible");
	        	indice = new Indice(code,libelle,nbDispo);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
		return indice;
	}
	
	private Action recupererAction(String sql, String code) throws DAOException {
      	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Action action = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        /*
	         * Préparation de la requête avec les objets passés en arguments
	         * (ici, uniquement une adresse email) et exécution.
	         */
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false, code);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données retournée dans le ResultSet */
	        if ( resultSet.next() ) {
	        	String libelle = resultSet.getString("libelle");
	        	Integer nbDispo = resultSet.getInt("nombreDisponible");
	        	Double dividende = (double) resultSet.getFloat("rendementDividende");
	        	action = new Action(code,libelle,nbDispo,dividende);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
		return action;
	}	
	
	private String recupererTypeTitre(String sql, String code) throws DAOException {
      	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String res = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        /*
	         * Préparation de la requête avec les objets passés en arguments
	         * (ici, uniquement une adresse email) et exécution.
	         */
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false, code);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données retournée dans le ResultSet */
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