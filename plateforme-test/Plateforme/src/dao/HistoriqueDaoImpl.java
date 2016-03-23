package dao;

import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Vector;

import modele.Historique;
import modele.TelechargeurCours;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
* Classe HistoriqueDaoImpl implementant l'interface HistoriqueDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class HistoriqueDaoImpl implements HistoriqueDao {
	/**
	* SQL_SELECT_PAR_CODE_DATE correspond a la requete SQL de recherche par code et date dans la table Historique.
	*/
    private static final String SQL_SELECT_PAR_CODE_DATE = "SELECT * FROM Historique WHERE code = ? AND dateCours = ?";
   
    
    /**
	* SQL_SELECT_PAR_CODE_INTERVALLE_DATE correspond a la requete SQL de recherche par code et intervalle de date dans la table Historique.
	*/
    private static final String SQL_SELECT_PAR_CODE_INTERVALLE_DATES = "SELECT * FROM Historique WHERE code = ? AND dateCours >= ? AND dateCours <= ?";
    
    
    /**
	* SQL_INSERT correspond a la requete SQL d'insertion dans la table Historique.
	*/
    private static final String SQL_INSERT           = "INSERT INTO Historique (code, dateCours, valeurOuverture, valeurFermeture, valeurBas, valeurHaut, volume, valeurAjustee) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    
    /**
	* SQL_SELECT_MAX correspond a la requete SQL de recherche de la date maximale pour un code donne dans la table Historique.
	*/
    private static final String SQL_SELECT_MAX = "SELECT MAX(dateCours) FROM Historique WHERE code = ?";

    
    /**
	* La daoFactory qui va permettre la connection a la base de donnee.
	*/ 
    private DAOFactory daoFactory;

    
    /**
	* Constructeur HistoriqueDaoImpl.
	* <p>
	* Avec le parametre daoFactory
	* </p>
	*
	* @param daoFactory
	* La Fabrique dao de HistoriqueDaoImpl.
	*
	* @see HistoriqueDaoImpl#daoFactory
	* @see DAOFactory
	*/ 
    HistoriqueDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }


    /**
	* Implementation de la methode definie dans l'interface HistoriqueDao
	*
	* @param code du titre que l'on veut trouver
	* 
	* @throws DAOException Si une erreur arrive lors l'ajout dans la bdd
	* 
	* @see Historique
	* @see DAOException
	* @see HistoriqueDao
	* @see HistoriqueDaoImpl#ajouterCours(Historique)
	*/ 
	@Override
	public void mettreAJour(String code) throws DAOException {
		GregorianCalendar dateMax = max(code);
		TelechargeurCours tc = new TelechargeurCours(code,dateMax,new GregorianCalendar());
		ajouterCours(tc.getHistorique());
	}
	
	
	/**
	* Implementation de la methode definie dans l'interface HistoriqueDao
	*
	* @param code du titre que l'on veut trouver
	* @param date a laquelle on veut le retrouver
	* 
	* @return Historique a la date donnee
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Historique
	* @see DAOException
	* @see HistoriqueDao
	* @see HistoriqueDaoImpl#trouver(String, Object...)
	*/ 
	@Override
	public Historique trouver(String code, GregorianCalendar date) throws DAOException {
		return trouver(SQL_SELECT_PAR_CODE_DATE, code, new java.sql.Date(date.getTimeInMillis()));
	}


	/**
	* Implementation de la methode definie dans l'interface HistoriqueDao
	*
	* @param code du titre que l'on veut trouver
	* @param date_debut date a partir de laquelle on veut le retrouver
	* @param date_fin date de fin ou on veut trouver le titre
	* 
	* @return Historique correspondant a l'intervalle
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Historique
	* @see DAOException
	* @see HistoriqueDao
	* @see HistoriqueDaoImpl#trouver(String, Object...)
	*/ 
	@Override
	public Historique trouver(String code, GregorianCalendar date_debut, GregorianCalendar date_fin)
			throws DAOException {
		return trouver(SQL_SELECT_PAR_CODE_INTERVALLE_DATES, code, new java.sql.Date(date_debut.getTimeInMillis()), new java.sql.Date(date_fin.getTimeInMillis()));
	}
	

	/**
	* Methode privee qui permet d'ajouter l'historique a la table 
	*
	* @param cours historique que l'on veut ajouter a la table Historique
	* 
	* @throws DAOException Si une erreur arrive lors de l'ajout dans la bdd
	* 
	* @see Historique
	* @see DAOException
	* @see HistoriqueDao
	*/ 
	private void ajouterCours(Historique cours) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            
            Iterator<GregorianCalendar> dates = cours.getValeurs().keySet().iterator();
            String code = cours.getCode();
            
            while (dates.hasNext()) {
            	GregorianCalendar date = dates.next();
            	//(code, dateCours, valeurOuverture, valeurFermeture, valeurBas, valeurHaut, volume, valeurAjustee)
            	if (trouver(code, date)!=null) {
            	//	throw new DAOException("echec de l'ajout du cours, il existe deja dans la table.");
            	} else {
	            	preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, code, new java.sql.Date(date.getTimeInMillis()), cours.getOuvertureJours(date), cours.getFermetureJours(date), cours.getBasJours(date), cours.getHautJours(date), cours.getVolumeJours(date), cours.getAdjFermetureJours(date));
	            	int statut = preparedStatement.executeUpdate();
	                if ( statut == 0 ) {
	                    throw new DAOException( "Échec de l'ajout du cours, aucune ligne ajoutée dans la table." );
	                }
            	}
            }        
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
	}
	
	
	/**
	* Methode privee qui permet de trouver la date maximale pour un titre
	*
	* @param code du titre dont on veut trouver la date max
	* 
	* @return date maximale
	* 
	* @throws DAOException Si une erreur arrive lors de l'ajout dans la bdd
	* 
	* @see Historique
	* @see DAOException
	* @see HistoriqueDao
	* @see GregorianCalendar
	*/ 
	private GregorianCalendar max (String code) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //changer pour que ça aille plus vite
        GregorianCalendar dateMax = new GregorianCalendar(2015,0,2);
        
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_MAX, false, code);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de donnees retournee dans le ResultSet */
            if ( resultSet.next() ) {
            	if (resultSet.getDate(1)!=null)
            		dateMax.setTime(resultSet.getDate(1));
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
		return dateMax;
	}

	
	/**
	* Methode privee qui permet de trouver un historique
	*
	* @param sql correspondant a la requete
	* @param objets parametre de la requete
	* 
	* @return Historique que l'on souhaite avoir a la sortie de la requete
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Historique
	* @see DAOException
	* @see HistoriqueDao
	*/ 
    private Historique trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Historique cours = null;

        try {
            /* Recuperation d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
           // Preparation de la requete avec les objets passes en arguments
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de donnees retournee dans le ResultSet */
            if ( resultSet.next() ) {
                cours = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return cours;
    }

    
    /**
	* Methode privee qui permet de faire correspondre un historique par rapport a un resultSet
	*
	* @param resultSet requete que l'on veut faire correspondre avec un historique
	* 
	* @return Historique qui correspond a la requete
	* 
	* @throws SQLException Si une erreur arrive lors du mapping
	* 
	* @see Historique
	* @see SQLException
	* @see HistoriqueDao
	* @see ResultSet
	*/
    private static Historique map( ResultSet resultSet ) throws SQLException {
        Historique cours = new Historique();
        cours.setCode(resultSet.getString("code"));
        
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(resultSet.getDate("dateCours"));
        
        Vector<Double> valeursCours = new Vector<Double>();
        valeursCours.add(Double.parseDouble(resultSet.getString("valeurOuverture")));
        valeursCours.add(Double.parseDouble(resultSet.getString("valeurHaut")));
        valeursCours.add(Double.parseDouble(resultSet.getString("valeurBas")));
        valeursCours.add(Double.parseDouble(resultSet.getString("valeurFermeture")));
        valeursCours.add(Double.parseDouble(resultSet.getString("volume")));
        valeursCours.add(Double.parseDouble(resultSet.getString("valeurAjustee")));

        TreeMap<GregorianCalendar,Vector<Double>> ht = new TreeMap<GregorianCalendar,Vector<Double>>();
        ht.put(date, valeursCours);
        
        // si on recherch plusieurs date d'un cours
        while (resultSet.next()) {
        	date = new GregorianCalendar();
            date.setTime(resultSet.getDate("dateCours"));
        	
        	valeursCours = new Vector<Double>();
            valeursCours.add(Double.parseDouble(resultSet.getString("valeurOuverture")));
            valeursCours.add(Double.parseDouble(resultSet.getString("valeurHaut")));
            valeursCours.add(Double.parseDouble(resultSet.getString("valeurBas")));
            valeursCours.add(Double.parseDouble(resultSet.getString("valeurFermeture")));
            valeursCours.add(Double.parseDouble(resultSet.getString("volume")));
            valeursCours.add(Double.parseDouble(resultSet.getString("valeurAjustee")));
       
            ht.put(date, valeursCours);
        }
        cours.setValeurs(ht);
        return cours;
    }

	
}
