package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import modele.Obligation;
import modele.Portefeuille;

/**
* Classe EstComposeObligationDaoImpl implémentant l'interface EstComposeObligationDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class EstComposeObligationDaoImpl implements EstComposeObligationDao {
	/**
	* SQL_SELECT correspond a la requete SQL de recherche par idPortefeuille dans la table estComposeObligation.
	*/ 
	private static final String SQL_SELECT = "SELECT * FROM EstComposeObligation WHERE idPortefeuille = ?";
	
	
	/**
	* SQL_DELETE_PORTEFEUILLE correspond a la requete SQL de suppression par idPortefeuille dans la table estComposeObligation.
	*/
	private static final String SQL_DELETE_PORTEFEUILLE="DELETE FROM EstComposeObligation WHERE idPortefeuille=?";
	
	
	/**
	* SQL_DELETE_PORTEFEUILLE_EMETTEUR correspond a la requete SQL de suppression par idPortefeuille et emetteur dans la table estComposeObligation.
	*/
	private static final String SQL_DELETE_PORTEFEUILLE_EMETTEUR="DELETE FROM EstComposeObligation WHERE idPortefeuille=? AND emetteur=? AND dateFin=?";
    
	
	/**
	* SQL_SELECT correspond a la requete SQL de recherche par idPortefeuille, emetteur et dateFin dans la table estComposeObligation.
	*/
	private static final String SQL_SELECT_EMETTEUR="SELECT emetteur FROM EstComposeObligation WHERE idPortefeuille=? AND emetteur=? AND dateFin=?";
	
	
	/**
	* SQL_INSERER correspond a la requete SQL pour inserer une ligne dans la table estComposeObligation.
	*/
	private static final String SQL_INSERER="INSERT INTO EstComposeObligation (idPortefeuille, emetteur, quantite, dateFin) VALUES (?,?,?,?)";
    
	
	/**
	* SQL_UPDATE correspond a la requete SQL pour mettre a jour une ligne dans la table estComposeObligation.
	*/
	private static final String SQL_UPDATE="UPDATE EstComposeObligation SET quantite=? WHERE idPortefeuille=? AND emetteur=? AND dateFin=?";
	
	
	/**
	* La daoFactory qui va permettre la connection a la base de donnee.
	*/ 
	private DAOFactory daoFactory;

	
	/**
	* Constructeur EstComposeObligationDaoImpl.
	* <p>
	* Avec le parametre daoFactory
	* </p>
	*
	* @param daoFactory
	* La Fabrique dao de EstComposeObligationDaoImpl.
	*
	* @see EstComposeObligationDaoImpl#daoFactory
	* @see DAOFactory
	*/ 
    EstComposeObligationDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    
    /**
   	* Implementation de la methode definie dans l'interface EstComposeObligationDao
   	*
   	* @param portefeuille que l'on modifie
   	* @param obligation dont on modifie le nombre
   	* 
   	* @throws DAOException Si une erreur arrive lors la mise a jour de la bdd
   	* 
   	* @see Portefeuille
   	* @see Obligation
   	* @see DAOException
   	* @see EstComposeObligationDao
   	* @see EstComposeObligationDaoImpl#executeRequete(String, Object...)
   	* @see EstComposeObligationDaoImpl#verification(String, Object...)
   	*/ 
    @Override
	public void mettreAJour(Portefeuille portefeuille, Obligation obligation) throws DAOException {
    	if(portefeuille.getQuantiteObjetFinancier().get(obligation)==0){
    		executeRequete(SQL_DELETE_PORTEFEUILLE_EMETTEUR, portefeuille.getIdPortefeuille(), obligation.getEmetteur(), new java.sql.Date(obligation.getDateFin().getTimeInMillis()));    	
    	}else
    		if(verification(SQL_SELECT_EMETTEUR,portefeuille.getIdPortefeuille(), obligation.getEmetteur(), new java.sql.Date(obligation.getDateFin().getTimeInMillis()))==false){
    			executeRequete(SQL_INSERER, portefeuille.getIdPortefeuille(), obligation.getEmetteur(), portefeuille.getQuantiteObjetFinancier().get(obligation), new java.sql.Date(obligation.getDateFin().getTimeInMillis()));
    		}	else
    			executeRequete(SQL_UPDATE, portefeuille.getQuantiteObjetFinancier().get(obligation),portefeuille.getIdPortefeuille(), obligation.getEmetteur(), new java.sql.Date(obligation.getDateFin().getTimeInMillis()));
    }


    /**
   	* Implementation de la methode definie dans l'interface EstComposeObligationDao
   	*
   	* @param id du portefeuille que l'on supprime
   	* 
   	* @throws DAOException Si une erreur arrive lors la suppression dans la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see EstComposeObligationDao
   	* @see EstComposeObligationDaoImpl#executeRequete(String, Object...)
   	*/ 
	@Override
	public void supprimer(Integer idPortefeuille) throws DAOException {
		executeRequete(SQL_DELETE_PORTEFEUILLE, idPortefeuille);
	}


	/**
   	* Implementation de la methode definie dans l'interface EstComposeObligationDao
   	*
   	* @param portefeuille que l'on veut remplir avec les obligations correspondantes
   	* 
   	* @throws DAOException Si une erreur arrive lors la lecture dans la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see EstComposeObligationDao
   	* @see EstComposeObligationDaoImpl#trouver(String, Portefeuille)
   	*/ 
	@Override
	public void trouver(Portefeuille portefeuille) throws DAOException {
		trouver(SQL_SELECT, portefeuille);
	}


	/**
   	* Methode privee qui permet de trouver toutes les obligations d'un portefeuille
   	*
   	* @param sql correspond a la requete SQL pour rechercher dans la table
   	* @param portefeuille que l'on veut remplir
   	* 
   	* @throws DAOException Si une erreur arrive lors l'execution de la requete
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see EstComposeObligationDao
   	* @see Obligation
   	*/ 
    private void trouver( String sql, Portefeuille portefeuille ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, portefeuille.getIdPortefeuille() );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            while ( resultSet.next() ) {
            	ObligationDao o=new ObligationDaoImpl(daoFactory);
            	Obligation obligation=o.recupererObligation(resultSet.getString("emetteur"));
            	GregorianCalendar dateFin = new GregorianCalendar();
            	dateFin.setTime(resultSet.getDate("dateFin"));
            	obligation.setDateFin(dateFin);
                int qte=resultSet.getInt("quantite");
                portefeuille.ajoutQuantiteObjetFinancier(obligation, qte);
                double prix=obligation.getPrix();
                portefeuille.ajoutPrixObjetFinancier(obligation, prix);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }

	
    /**
   	* Methode privee generique qui permet l'execution d'une requete SQL quelconque
   	* ne demandant aucun traitement supplémentaire
   	*
   	* @param sql correspond a la requete SQL
   	* @param objets correspond aux différents paramètres de la requête
   	* 
   	* @throws DAOException Si une erreur arrive lors l'exécution de la requête
   	* 
   	* @see DAOException
   	* @see EstComposeObligationDao
   	*/ 
	private void executeRequete(String sql, Object...objets ){
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets);
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de l'execution" );
            }        
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
	}
	
	
	/**
   	* Methode privee qui permet de savoir si une requête renvoie au moins une ligne
   	*
   	* @param sql correspondant a la requête SQL
   	* @param objets parametre de la requete 
   	* 
   	* @throws DAOException Si une erreur arrive lors l'execution de la requete
   	* 
   	* @see DAOException
   	* @see EstComposeObligationDao
   	*/ 
	private boolean verification(String sql, Object... objets){
		boolean b=false;
		
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets);
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) 
            	b=true;        
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return b;
	}

}
