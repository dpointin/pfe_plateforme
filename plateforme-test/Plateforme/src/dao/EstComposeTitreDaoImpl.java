package dao;

import static dao.DAOUtilitaire.executeRequete;
import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;
import static dao.DAOUtilitaire.verification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.config.DAOException;
import modele.Portefeuille;
import modele.Titre;

/**
* Classe EstComposeTitreDaoImpl implementant l'interface EstComposeTitreDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class EstComposeTitreDaoImpl implements EstComposeTitreDao{
	/**
	* SQL_SELECT correspond a la requete SQL de recherche par idPortefeuille dans la table estComposeTitre.
	*/ 
	private static final String SQL_SELECT = "SELECT * FROM EstComposeTitre WHERE idPortefeuille = ?";
	
	
	/**
	* SQL_DELETE_PORTEFEUILLE correspond a la requete SQL de suppression par idPortefeuille dans la table estComposeTitre.
	*/ 
	private static final String SQL_DELETE_PORTEFEUILLE="DELETE FROM EstComposeTitre WHERE idPortefeuille=?";
	
	
	/**
	* SQL_DETELE_PORTEFEUILLE_CODE correspond a la requete SQL de suppresion par idPortefeuille et code du titre dans la table estComposeTitre.
	*/ 
	private static final String SQL_DELETE_PORTEFEUILLE_CODE="DELETE FROM EstComposeTitre WHERE idPortefeuille=? AND code=?";
    
	
	/**
	* SQL_SELECT_CODE correspond a la requete SQL de recherche par idPortefeuille et code du titre dans la table estComposeTitre.
	*/ 
	private static final String SQL_SELECT_CODE="SELECT CODE FROM EstComposeTitre WHERE idPortefeuille=? AND code=?";
	
	
	/**
	* SQL_INSERER correspond a la requete SQL pour inserer une ligne dans la table estComposeTitre.
	*/ 
	private static final String SQL_INSERER="INSERT INTO EstComposeTitre (idPortefeuille, code, quantite, prixUnitaire ) VALUES (?,?,?,?)";
    
	
	/**
	* SQL_UPDATE correspond a la requete SQL de mise a jour d'une ligne dans la table estComposeTitre.
	*/ 
	private static final String SQL_UPDATE="UPDATE EstComposeTitre SET quantite=?, prixUnitaire=? WHERE idPortefeuille=? AND code=?";
	
	
	/**
	* La daoFactory qui va permettre la connection a la base de donnee.
	*/
	private DAOFactory daoFactory;

	
	/**
	* Constructeur EstComposeTitreDaoImpl.
	* <p>
	* Avec le parametre daoFactory
	* </p>
	*
	* @param daoFactory
	* La Fabrique dao de EstComposeTitreDaoImpl.
	*
	* @see EstComposeTitreDaoImpl#daoFactory
	* @see DAOFactory
	*/ 
    EstComposeTitreDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    
    /**
   	* Implementation de la methode definie dans l'interface EstComposeTitreDao
   	*
   	* @param portefeuille que l'on modifie
   	* @param titre dont on modifie le nombre et le prix
   	* 
   	* @throws DAOException Si une erreur arrive lors la mise a jour de la bdd
   	* 
   	* @see Portefeuille
   	* @see Titre
   	* @see DAOException
   	* @see EstComposeTitreDao
   	* @see DAOUtilitaire#executeRequete(DAOFactory, String, Object...)
   	* @see DAOUtilitaire#verification(DAOFactory, String, Object...)
   	*/ 
    @Override
	public void mettreAJour(Portefeuille portefeuille, Titre titre) throws DAOException {
    	if(portefeuille.getQuantiteObjetFinancier().get(titre)==0){
    		executeRequete(daoFactory,SQL_DELETE_PORTEFEUILLE_CODE, portefeuille.getIdPortefeuille(), titre.getCode());
    		portefeuille.getQuantiteObjetFinancier().remove(titre);
    		portefeuille.getPrixObjetFinancier().remove(titre);
    	}
    	else
    		if(verification(daoFactory,SQL_SELECT_CODE,portefeuille.getIdPortefeuille(), titre.getCode())==false)
    			executeRequete(daoFactory,SQL_INSERER, portefeuille.getIdPortefeuille(), titre.getCode(), portefeuille.getQuantiteObjetFinancier().get(titre), portefeuille.getPrixObjetFinancier().get(titre));
    		else
    			executeRequete(daoFactory,SQL_UPDATE, portefeuille.getQuantiteObjetFinancier().get(titre), portefeuille.getPrixObjetFinancier().get(titre), portefeuille.getIdPortefeuille(), titre.getCode());
    }


    /**
   	* Implementation de la methode definie dans l'interface EstComposeTitreDao
   	*
   	* @param idPortefeuille que l'on supprime
   	* 
   	* @throws DAOException Si une erreur arrive lors la suppression dans la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see EstComposeTitreDao
   	* @see DAOUtilitaire#executeRequete(DAOFactory, String, Object...)
   	*/ 
	@Override
	public void supprimer(Integer idPortefeuille) throws DAOException {
		executeRequete(daoFactory,SQL_DELETE_PORTEFEUILLE, idPortefeuille);
	}


	/**
   	* Implementation de la methode definie dans l'interface EstComposeTitreDao
   	*
   	* @param portefeuille que l'on veut remplir avec les titres lui appartenant
   	* 
   	* @throws DAOException Si une erreur arrive lors la lecture dans la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see EstComposeTitreDao
   	* @see EstComposeTitreDaoImpl#trouver(String, Portefeuille)
   	*/ 
	@Override
	public void trouver(Portefeuille portefeuille) throws DAOException {
		trouver(SQL_SELECT, portefeuille);
	}


	/**
   	* Methode privee qui permet de trouver toutes les titres d'un portefeuille
   	*
   	* @param sql correspond a la requete SQL pour rechercher dans la table
   	* @param portefeuille que l'on veut remplir
   	* 
   	* @throws DAOException Si une erreur arrive lors l'execution de la requete
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see EstComposeTitreDao
   	* @see Titre
   	*/ 
    private void trouver( String sql, Portefeuille portefeuille ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Recuperation d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
             // Preparation de la requete avec les objets passes en arguments
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, portefeuille.getIdPortefeuille() );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de donnees retournee dans le ResultSet */
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

}
