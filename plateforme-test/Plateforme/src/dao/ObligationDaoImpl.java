package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import modele.Obligation;

/**
* Classe ObligationDaoImpl implementant l'interface ObligationDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class ObligationDaoImpl implements ObligationDao {
	/**
	* SQL_SELECT_TOUTES_OBLIGATIONS correspond a la requete SQL de recherche de tous les emetteurs dans la table Obligation.
	*/ 
    private static final String SQL_SELECT_TOUTES_OBLIGATIONS = "SELECT emetteur FROM Obligation";
	
    
    /**
	* SQL_SELECT_PAR_EMETTEUR correspond a la requete SQL de recherche d'une obligation par emetteur dans la table Obligation.
	*/ 
    private static final String SQL_SELECT_PAR_EMETTEUR = "SELECT * FROM Obligation WHERE emetteur = ?";
		
    
    /**
	* La daoFactory qui va permettre la connection a la base de donnee.
	*/ 
    private DAOFactory daoFactory;

    
    /**
	* Constructeur ObligationDaoImpl.
	* <p>
	* Avec le parametre daoFactory
	* </p>
	*
	* @param daoFactory
	* La Fabrique dao de l'ObligationDaoImpl.
	*
	* @see ObligationDaoImpl#daoFactory
	* @see DAOFactory
	*/ 
    ObligationDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	    
    
    /**
	* Implementation de la methode definie dans l'interface JoueurDao
	*
	* @return Vector<String> contenant tous les emetteurs
	* 
	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDao
	*/ 
	@Override
	public Vector<String> trouverToutesObligations() throws DAOException {
		Connection con=null;
		PreparedStatement pre=null;
		ResultSet res=null;
		Vector<String> sol=null;
		try {
			con=daoFactory.getConnection();
			pre=initialisationRequetePreparee(con, SQL_SELECT_TOUTES_OBLIGATIONS, false);
			res=pre.executeQuery();
			sol=new Vector<String>();
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
	* Implementation de la methode definie dans l'interface JoueurDao
	*
	* @param emetteur de l'obligation que l'on recherche
	*
	* @return Obligation que l'on recherche
	* 
	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDao
	* @see ObligationDaoImpl#recupererObligation(String, String)
	*/ 
	@Override
	public Obligation recupererObligation(String emetteur) throws DAOException {
		return recupererObligation(SQL_SELECT_PAR_EMETTEUR, emetteur);
	}
		
	
	/**
	* Methode privee permettant de recuperer une obligation
	* 
	* @param sql correspond a la requete sql
	* @param emetteur correspond a l'emetteur de l'obligation
	*
	* @return Obligation que l'on veut trouver
	* 
	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDao
	*/ 
	private Obligation recupererObligation(String sql, String emetteur) throws DAOException {	
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Obligation obligation = null;
	    
	    try {
	        /* Recuperation d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	         // Preparation de la requête avec les objets passes en arguments
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false, emetteur);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donnees retournee dans le ResultSet */
	        if ( resultSet.next() ) {
	        	Double prix = (double) resultSet.getFloat("prix");
	        	Double tauxInterets = (double) resultSet.getFloat("tauxInterets");
	        	Integer nbDispo = resultSet.getInt("nombreDisponible");
	        	obligation = new Obligation(emetteur,prix,tauxInterets,nbDispo,null);	        	
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
		return obligation;
	}

}
