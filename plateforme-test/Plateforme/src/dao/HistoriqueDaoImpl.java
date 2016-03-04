package dao;

import java.util.GregorianCalendar;
import java.util.Hashtable;
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

public class HistoriqueDaoImpl implements HistoriqueDao {

    private static final String SQL_SELECT_PAR_CODE_DATE = "SELECT * FROM Historique WHERE code = ? AND dateCours = ?";
    private static final String SQL_SELECT_PAR_CODE_INTERVALLE_DATES = "SELECT * FROM Historique WHERE code = ? AND dateCours >= ? AND dateCours <= ?";
    private static final String SQL_INSERT           = "INSERT INTO Historique (code, dateCours, valeurOuverture, valeurFermeture, valeurBas, valeurHaut, volume, valeurAjustee) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_MAX = "SELECT MAX(dateCours) FROM Historique WHERE code = ?";

    private DAOFactory daoFactory;

    HistoriqueDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }


	@Override
	public void mettreAJour(String code) throws DAOException {
		GregorianCalendar dateMax = max(code);
		TelechargeurCours tc = new TelechargeurCours(code,dateMax,new GregorianCalendar());
		ajouterCours(tc.getHistorique());
	}
	
	@Override
	public Historique trouver(String code, GregorianCalendar date) throws DAOException {
		return trouver(SQL_SELECT_PAR_CODE_DATE, code, new java.sql.Date(date.getTimeInMillis()));
	}


	@Override
	public Historique trouver(String code, GregorianCalendar date_debut, GregorianCalendar date_fin)
			throws DAOException {
		return trouver(SQL_SELECT_PAR_CODE_INTERVALLE_DATES, code, new java.sql.Date(date_debut.getTimeInMillis()), new java.sql.Date(date_fin.getTimeInMillis()));
	}
	

	// METHODES PRIVEES
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
            	//	throw new DAOException("Échec de l'ajout du cours, il existe déjà dans la table.");
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
	
	private GregorianCalendar max (String code) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        GregorianCalendar dateMax = new GregorianCalendar(2000,0,1);
        
        
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_MAX, false, code);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
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

	
    private Historique trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Historique cours = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement une adresse email) et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
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

        Hashtable<GregorianCalendar,Vector<Double>> ht = new Hashtable<GregorianCalendar,Vector<Double>>();
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
