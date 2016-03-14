package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import modele.ObjetFinancier;
import modele.Obligation;
import modele.Portefeuille;
import modele.Titre;

/**
* Classe PortefeuilleDaoImpl implémentant l'interface PortefeuilleDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class PortefeuilleDaoImpl implements PortefeuilleDao {
	/**
	* SQL_SELECT_PAR_LOGIN correspond a la requete SQL de recherche par login dans la table portefeuille.
	*/ 
    private static final String SQL_SELECT_PAR_LOGIN = "SELECT * FROM Portefeuille WHERE login = ?";
    
    
    /**
	* SQL_SELECT_CODE correspond a la requete SQL de suppression par idPortefeuille dans la table portefeuille.
	*/ 
    private static final String SQL_DELETE_CODE="DELETE FROM Portefeuille WHERE idPortefeuille=?";
    
    
    /**
	* SQL_SELECT_INSERT correspond a la requete SQL pour inserer une ligne dans la table portefeuille.
	*/ 
    private static final String SQL_INSERT="INSERT INTO Portefeuille (login, argentDisponible, rendement) VALUES (?,?,?)";
    
    
    /**
	* SQL_UPDATE_ARGENT_DISPONIBLE correspond a la requete SQL de mise a jour de l'argent disponible pour un portefeuille dans la table portefeuille.
	*/ 
    private static final String SQL_UPDATE_ARGENT_DISPONIBLE = "UPDATE Portefeuille SET argentDisponible=? WHERE idPortefeuille=?";
    
    
    /**
   	* La daoFactory qui va permettre la connection a la base de donnee.
   	*/ 
    private DAOFactory daoFactory;

    
    
    /**
   	* Constructeur PortefeuilleDaoImpl.
   	* <p>
   	* Avec le parametre daoFactory
   	* </p>
   	*
   	* @param daoFactory
   	* La Fabrique dao du PortefeuilleDaoImpl.
   	*
   	* @see PortefeuilleDaoImpl#daoFactory
   	* @see DAOFactory
   	*/ 
    PortefeuilleDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
    
    /**
   	* Implementation de la methode definie dans l'interface PortefeuilleDao
   	*
   	* @param login du joueur dont on veut le portefeuille
   	* 
   	* @return Portefeuille du joueur
   	* 
   	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see PortefeuilleDao
   	* @see PortefeuilleDaoImpl#charger(String)
   	* @see EstComposeTitreDao
   	* @see EstComposeObligationDao
   	*/ 
	@Override
	public Portefeuille charger(String login) throws DAOException {
		Portefeuille p = charger(SQL_SELECT_PAR_LOGIN, login);
		
		if(p!=null){
			EstComposeTitreDao estTitre=new EstComposeTitreDaoImpl(daoFactory);
			estTitre.trouver(p);
			EstComposeObligationDao estObligation= new EstComposeObligationDaoImpl(daoFactory);
			estObligation.trouver(p);
		}
		return p;
	}

	
	
	/**
   	* Implementation de la methode definie dans l'interface PortefeuilleDao
   	*
   	* @param login du joueur auquel appartient le portefeuille
   	* @param portefeuille auquel appartient le joueur
   	* 
   	* @throws DAOException Si une erreur arrive lors l'ajout dans la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see PortefeuilleDao
   	*/ 
	@Override
	public void creer(String login, Portefeuille portefeuille) throws DAOException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
           	preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, login, portefeuille.getArgentDisponible(), portefeuille.getRendement());
            int statut = preparedStatement.executeUpdate();
            //ON DONNE A LA DEFENSE SON ID GENERE DANS LA TABLE 
            ResultSet result=preparedStatement.getGeneratedKeys();
            if(result.next())
            	portefeuille.setIdPortefeuille(result.getInt(1));
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la creation de la table" );
            }
 
			Iterator<ObjetFinancier> it=portefeuille.getPrixObjetFinancier().keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
			 
			while(it.hasNext())
			{
			   ObjetFinancier key=(ObjetFinancier) it.next();
			   mettreAJour(portefeuille,key);
			}
        
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
	}


	/**
   	* Implementation de la methode definie dans l'interface PortefeuilleDao
   	*
   	* @param portefeuille du joueur que l'on veut mettre a jour
   	* @param objetFinancier que l'on veut mettre a jour
   	*  
   	* @throws DAOException Si une erreur arrive lors la mise a jour de la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see PortefeuilleDao
   	* @see EstComposeTitreDao
   	* @see EstComposeObligationDao
   	* @see PortefeuilleDaoImpl#executeRequete(String, Object...)
   	*/ 
	@Override
	public void mettreAJour(Portefeuille portefeuille, ObjetFinancier objetFinancier) throws DAOException {
		if(objetFinancier instanceof Titre){
			EstComposeTitreDao titre=new EstComposeTitreDaoImpl(daoFactory);
			titre.mettreAJour(portefeuille, (Titre)objetFinancier); 
		}else { if(objetFinancier instanceof Obligation){
						EstComposeObligationDao obligation=new EstComposeObligationDaoImpl(daoFactory);
						obligation.mettreAJour(portefeuille, (Obligation) objetFinancier); 
				}else{
					//Pour les options
				}
		}
		executeRequete(SQL_UPDATE_ARGENT_DISPONIBLE, portefeuille.getArgentDisponible(), portefeuille.getIdPortefeuille());
	}


	/**
   	* Implementation de la methode definie dans l'interface PortefeuilleDao
   	*
   	* @return login du joueur dont on veut supprimer le portefeuille
   	* 
   	* @throws DAOException Si une erreur arrive lors la suppression dans la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see PortefeuilleDao
   	* @see PortefeuilleDaoImpl#executeRequete(String, Object...)
   	* @see EstComposeTitreDao
   	* @see EstComposeObligationDao
   	*/ 
	@Override
	public void supprimer(String login) throws DAOException {
		Portefeuille p=charger(login);
		
		EstComposeTitreDao estTitre=new EstComposeTitreDaoImpl(daoFactory);
		estTitre.supprimer(p.getIdPortefeuille());
		EstComposeObligationDao estCompose=new EstComposeObligationDaoImpl(daoFactory);
		estCompose.supprimer(p.getIdPortefeuille());
		
		//Dans cet ordre sinon problème de supprimer une foreign key
		executeRequete(SQL_DELETE_CODE, p.getIdPortefeuille());
	}
    

	/**
   	* Methode privee permettant de charger un portefeuille
   	*
   	* @param sql correspond a la requete sql
   	* @param login correspond au login du joueur
   	* 
   	* @return Portefeuille que l'on modifie
   	*  
   	* @throws DAOException Si une erreur arrive lors la lecture dans la bdd
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see PortefeuilleDao
   	*/ 
    private Portefeuille charger( String sql, String login ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Portefeuille p = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            // Préparation de la requête avec les objets passés en arguments/
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, login);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
                p = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return p;
    }
    
    
    /**
   	* Methode privee permettant l'excution d'une requete
   	*
   	* @param sql correspond a la requete sql
   	* @param objects correspond aux parametres de la requete
   	*  
   	* @throws DAOException Si une erreur arrive lors l'execution de la requete
   	* 
   	* @see Portefeuille
   	* @see DAOException
   	* @see PortefeuilleDao
   	*/ 
    private void executeRequete(String sql, Object...objects){
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objects);
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
   	* Methode privee permettant de faire correspondre un resultSet avec un Portefeuille
   	*
   	* @param resultSet que l'on veut faire correspondre avec un portefeuille
   	* 
   	* @return Portefeuille correspondant au resultSet 
   	*  
   	* @throws SQLException Si une erreur arrive lors du mappin
   	* 
   	* @see Portefeuille
   	* @see SQLException
   	* @see PortefeuilleDao
   	*/ 
    private static Portefeuille map( ResultSet resultSet ) throws SQLException {
        Portefeuille p = new Portefeuille();

        p.setArgentDisponible(Double.parseDouble(resultSet.getString("argentDisponible")));
        if (resultSet.getString("rendement") != null) {
        	p.setRendement(Double.parseDouble(resultSet.getString("rendement")));
        } else {
        	p.setRendement(-1.0);
        }
        p.setIdPortefeuille(Integer.parseInt(resultSet.getString("idPortefeuille")));
        
        return p;
    }

}
