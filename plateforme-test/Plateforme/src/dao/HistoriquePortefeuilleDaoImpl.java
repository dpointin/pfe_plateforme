package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;


import modele.ObjetFinancier;
import modele.Obligation;
import modele.Operation;
import modele.Portefeuille;
import modele.Titre;

/**
* Classe HistoriquePortefeuilleDaoImpl implementant l'interface HistoriquePortefeuilleDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class HistoriquePortefeuilleDaoImpl implements HistoriquePortefeuilleDao {
	/**
	* SQL_SELECT_IS correspond a la requete SQL de recherche par idPortefeuille date dans la table Historique.
	*/
	private static final String SQL_SELECT_ID = "SELECT * FROM HistoriquePortefeuille WHERE idPortefeuille = ?";
	    
	    
	/**
	* SQL_INSERT_OBLIGATION correspond a la requete SQL d'insertion d'une operation obligation dans la table HistoriquePortefeuilles.
	*/
	private static final String SQL_INSERT_OBLIGATION= "INSERT INTO HistoriquePortefeuille (idPortefeuille, emetteur, prix, quantite, dateOperation, dateFinObligation) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    
	/**
	* SQL_INSERT_TITRE correspond a la requete SQL d'insertion d'une operation titre dans la table HistoriquePortefeuille.
	*/
	private static final String SQL_INSERT_TITRE= "INSERT INTO HistoriquePortefeuille (idPortefeuille, code, prix, quantite, dateOperation) VALUES (?, ?, ?, ?, ?)";
	
	
	/**
	* La daoFactory qui va permettre la connection a la base de donnee.
	*/ 
	private DAOFactory daoFactory;

	    
	/**
	* Constructeur HistoriquePortefeuilleDaoImpl.
	* <p>
	* Avec le parametre daoFactory
	* </p>
	*
	* @param daoFactory
	* La Fabrique dao de HistoriquePortefeuilleDaoImpl.
	*
	* @see HistoriquePortefeuilleDaoImpl#daoFactory
	* @see DAOFactory
	*/ 
	HistoriquePortefeuilleDaoImpl( DAOFactory daoFactory ) {
	    this.daoFactory = daoFactory;
	}
	
	
	
	/**
	* Implementation de la methode definie dans l'interface HistoriquePortefeuilleDao
	*
	* @param idPortefeuille correspond au portefeuille dont on souhaite recuperer les operations l'operation
	* 
	* @return Vector<Operation> correspond a la liste des operations
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Operation
	* @see Portefeuille
	* @see DAOException
	* @see HistoriquePortefeuilleDaoImpl
	*/
	@Override
	public Vector<Operation> trouver(int idPortefeuille) throws DAOException {
		Connection con=null;
		PreparedStatement pre=null;
		ResultSet res=null;
		Vector<Operation> sol=null;
		try {
			con=daoFactory.getConnection();
			pre=initialisationRequetePreparee(con, SQL_SELECT_ID, false,idPortefeuille);
			res=pre.executeQuery();
			sol=new Vector<Operation>();
			while(res.next())
				sol.add(map(res));
		}
		catch ( SQLException e){
			throw new DAOException(e);
		}finally{
			fermeturesSilencieuses(pre,con);
		}
		//System.out.println(sol);
		return sol;
	}

	
	/**
	* Implementation de la methode definie dans l'interface HistoriquePortefeuilleDao
	*
	* @param idPortefeuille correspond au portefeuille qui a effectue l'operation
	* @param Operation correspond a l'operation que l'on souhaite ajouter
	* 
	* @throws DAOException Si une erreur arrive lors de l'ajout dans la bdd
	* 
	* @see Operation
	* @see Portefeuille
	* @see DAOException
	* @see HistoriquePortefeuilleDaoImpl
	*/
	@Override
	public void ajouter(int idPortefeuille, Operation operation) throws DAOException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connexion = daoFactory.getConnection();
            if(operation.getObjetFinancier() instanceof Obligation){
            	Obligation o=(Obligation) operation.getObjetFinancier();
            	preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_OBLIGATION, true, idPortefeuille, o.getEmetteur(),operation.getPrixUnitaire(),operation.getQuantite(), new java.sql.Date(operation.getDate().getTimeInMillis()),new java.sql.Date(o.getDateFin().getTimeInMillis()));
            }
            else{
            	Titre t=(Titre) operation.getObjetFinancier();
            	preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_TITRE, true, idPortefeuille, t.getCode(), operation.getPrixUnitaire(),operation.getQuantite(), new java.sql.Date(operation.getDate().getTimeInMillis()));

            }
            ResultSet result=preparedStatement.getGeneratedKeys();
            if(result.next())
            	operation.setId(result.getInt(1));
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
   	* Methode permettant l'excution d'une requete
   	*
   	* @param sql correspond a la requete sql
   	* @param objects correspond aux parametres de la requete
   	*  
   	* @throws DAOException Si une erreur arrive lors l'execution de la requete
   	* 
   	* @see DAOException
   	*/ 
    public static void executeRequete(DAOFactory daoFactory, String sql, Object...objects){
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
	* Methode qui fait la correspondance entre une ligne de la table
	* et une operation du modele
	* 
	* @param ResultSet
	* le resultSet contenant la ligne de la table
	*
	* @return l'operation de la base de donnee transforme en operaiton du modele
	* 
	* @throws SQLException s'il y a une erreur lors du traitement de la requete
	* 
	* @see Operation
	* @see SQLException
	* @see ResultSet
	*/ 
    private Operation map( ResultSet resultSet ) throws SQLException {
        Operation operation = new Operation();
        ObjetFinancier objet;
        if (resultSet.getString("code")!=null){
        	TitreDao t=new TitreDaoImpl(daoFactory);
        	objet=t.recupererTitre(resultSet.getString("code"));
        }
        else{
        	ObligationDao odao=new ObligationDaoImpl(daoFactory);
        	Obligation o=odao.recupererObligation(resultSet.getString("emetteur"));
        	GregorianCalendar dateFin = new GregorianCalendar();
        	dateFin.setTime(resultSet.getDate("dateFinObligation"));
        	o.setDateFin(dateFin);
        	objet=o;
        }
        operation.setObjetFinancier(objet);	
        GregorianCalendar dateOp = new GregorianCalendar();
    	dateOp.setTime(resultSet.getDate("dateOperation"));
        operation.setDate(dateOp);
        operation.setPrixUnitaire(resultSet.getDouble("prix"));
        operation.setQuantite(resultSet.getInt("quantite"));
        operation.setId(resultSet.getInt("idHistoriquePortefeuille"));
        return operation;
    }
}
