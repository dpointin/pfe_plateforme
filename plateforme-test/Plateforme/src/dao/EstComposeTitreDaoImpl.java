package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modele.Portefeuille;
import modele.Titre;

public class EstComposeTitreDaoImpl implements EstComposeTitreDao{
	
	private static final String SQL_SELECT = "SELECT * FROM EstComposeTitre WHERE idPortefeuille = ?";
	private static final String SQL_DELETE_PORTEFEUILLE="DELETE FROM EstComposeTitre WHERE idPortefeuille=?";
	private static final String SQL_DELETE_PORTEFEUILLE_CODE="DELETE FROM EstComposeTitre WHERE idPortefeuille=? AND code=?";
    private static final String SQL_SELECT_CODE="SELECT CODE FROM EstComposeTitre WHERE idPortefeuille=? AND code=?";
	private static final String SQL_INSERER="INSERT INTO EstComposeTitre (idPortefeuille, code, quantite, prixUnitaire ) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE="UPDATE EstComposeTitre SET quantite=?, prixUnitaire=? WHERE idPortefeuille=? AND code=?";
	
	private DAOFactory daoFactory;

    EstComposeTitreDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
	public void mettreAJour(Portefeuille portefeuille, Titre titre) throws DAOException {
    	if(portefeuille.getQuantiteObjetFinancier().get(titre)==0)
    		supprimerCode(SQL_DELETE_PORTEFEUILLE_CODE, portefeuille.getIdPortefeuille(), titre.getCode());
    	else
    		if(trouverCode(SQL_SELECT_CODE,portefeuille.getIdPortefeuille(), titre.getCode())==false)
    			inserer(SQL_INSERER, portefeuille.getIdPortefeuille(), titre.getCode(), portefeuille.getQuantiteObjetFinancier().get(titre), portefeuille.getPrixObjetFinancier().get(titre));
    		else
    			update(SQL_UPDATE, portefeuille.getQuantiteObjetFinancier().get(titre), portefeuille.getPrixObjetFinancier().get(titre), portefeuille.getIdPortefeuille(), titre.getCode());
    }


	@Override
	public void supprimer(Integer idPortefeuille) throws DAOException {
		supprimer(SQL_DELETE_PORTEFEUILLE, idPortefeuille);
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
            	TitreDao t=new TitreDaoImpl(daoFactory);
            	Titre titre=t.recupererTitre(resultSet.getString("code"));
                int qte=resultSet.getInt("quantite");
                portefeuille.ajoutQuantiteObjetFinancier(titre, qte);
                double prix=resultSet.getDouble("prixUnitaire");
                portefeuille.ajoutPrixObjetFinancier(titre, prix);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }


	private void supprimer(String sql, Object... objets){
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets);
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression du portefeuille" );
            }        
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
		
	}

	
	private void supprimerCode(String sql, Object... objets){
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets);
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression dans le portefeuille" );
            }        
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
	}
	
	private void inserer(String sql, Object...objets ){
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets);
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression dans le portefeuille" );
            }        
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
	}
	
	private void update(String sql, Object...objets ){
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets);
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression dans le portefeuille" );
            }        
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
	}
	
	private boolean trouverCode(String sql, Object... objets){
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
