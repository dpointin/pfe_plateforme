package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import modele.Obligation;

public class ObligationDaoImpl implements ObligationDao {
    private static final String SQL_SELECT_TOUTES_OBLIGATIONS = "SELECT emetteur FROM Obligation";
	private static final String SQL_SELECT_PAR_EMETTEUR = "SELECT * FROM Obligation WHERE emetteur = ?";
		
    private DAOFactory daoFactory;

    ObligationDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	    
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

	@Override
	public Obligation recupererObligation(String emetteur) throws DAOException {
		return recupererObligation(SQL_SELECT_PAR_EMETTEUR, emetteur);
	}
		
	private Obligation recupererObligation(String sql, String emetteur) throws DAOException {	
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Obligation obligation = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        /*
	         * Préparation de la requête avec les objets passés en arguments
	         * (ici, uniquement une adresse email) et exécution.
	         */
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false, emetteur);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données retournée dans le ResultSet */
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
