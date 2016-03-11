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

public class EstComposeObligationDaoImpl implements EstComposeObligationDao {

	private static final String SQL_SELECT = "SELECT * FROM EstComposeObligation WHERE idPortefeuille = ?";
	private static final String SQL_DELETE_PORTEFEUILLE="DELETE FROM EstComposeObligation WHERE idPortefeuille=?";
	private static final String SQL_DELETE_PORTEFEUILLE_EMETTEUR="DELETE FROM EstComposeObligation WHERE idPortefeuille=? AND emetteur=? AND dateFin=?";
    private static final String SQL_SELECT_EMETTEUR="SELECT emetteur FROM EstComposeObligation WHERE idPortefeuille=? AND emetteur=? AND dateFin=?";
	private static final String SQL_INSERER="INSERT INTO EstComposeObligation (idPortefeuille, emetteur, quantite, dateFin) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE="UPDATE EstComposeObligation SET quantite=? WHERE idPortefeuille=? AND emetteur=? AND dateFin=?";
	
	private DAOFactory daoFactory;

    EstComposeObligationDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
	public void mettreAJour(Portefeuille portefeuille, Obligation obligation) throws DAOException {
    	if(portefeuille.getQuantiteObjetFinancier().get(obligation)==0){
    		executeRequete(SQL_DELETE_PORTEFEUILLE_EMETTEUR, portefeuille.getIdPortefeuille(), obligation.getEmetteur(), new java.sql.Date(obligation.getDateFin().getTimeInMillis()));    	
    	}else
    		if(trouverEmetteurDate(SQL_SELECT_EMETTEUR,portefeuille.getIdPortefeuille(), obligation.getEmetteur(), new java.sql.Date(obligation.getDateFin().getTimeInMillis()))==false){
    			executeRequete(SQL_INSERER, portefeuille.getIdPortefeuille(), obligation.getEmetteur(), portefeuille.getQuantiteObjetFinancier().get(obligation), new java.sql.Date(obligation.getDateFin().getTimeInMillis()));
    		}	else
    			executeRequete(SQL_UPDATE, portefeuille.getQuantiteObjetFinancier().get(obligation),portefeuille.getIdPortefeuille(), obligation.getEmetteur(), new java.sql.Date(obligation.getDateFin().getTimeInMillis()));
    }


	@Override
	public void supprimer(Integer idPortefeuille) throws DAOException {
		executeRequete(SQL_DELETE_PORTEFEUILLE, idPortefeuille);
	}


	@Override
	public void trouver(Portefeuille portefeuille) throws DAOException {
		trouver(SQL_SELECT, portefeuille);
	}


	// METHODES PRIVEES
    private void trouver( String sql, Portefeuille portefeuille ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement une adresse email) et exécution.
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
	
	private boolean trouverEmetteurDate(String sql, Object... objets){
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
