package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Iterator;

import modele.Obligation;
import modele.Option;
import modele.Portefeuille;
import modele.Titre;
import modele.TypeOption;
import modele.TypePosition;

public class PortefeuilleDaoImpl implements PortefeuilleDao {

    private static final String SQL_SELECT_PAR_LOGIN = "SELECT * FROM Portefeuille WHERE login = ?";
    private static final String SQL_SELECT_PAR_IDPF_TITRES = "SELECT * FROM EstComposeTitre WHERE idPortefeuille = ?";
    private static final String SQL_SELECT_PAR_IDPF_OPTIONS = "SELECT * FROM EstComposeOption WHERE idPortefeuille = ?";
    private static final String SQL_SELECT_PAR_IDPF_OBLIGATIONS = "SELECT * FROM EstComposeObligation WHERE idPortefeuille = ?";

    private static final String SQL_INSERT_OPTION = "INSERT INTO EstComposeOption (type, position, maturite, strike, prime, idPortefeuille, code) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_ARGENT_INVESTI = "";
    private static final String SQL_UPDATE_ARGENT_DISPONIBLE = "";
    
    private DAOFactory daoFactory;

    PortefeuilleDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
    
	@Override
	public Portefeuille charger(String login) throws DAOException {
		Portefeuille p = charger(SQL_SELECT_PAR_LOGIN, login);
		
		chargerTitres(SQL_SELECT_PAR_IDPF_TITRES, p);
		chargerOptions(SQL_SELECT_PAR_IDPF_OPTIONS, p);
		chargerObligations(SQL_SELECT_PAR_IDPF_OBLIGATIONS, p);
		
		return p;
	}

	
/*	
	public void acquerirOption(TypeOption typeOption, TypePosition typePosition, GregorianCalendar maturite, Double strike, Double prime, Integer idPortefeuille, String code) throws DAOException {
		acquerirOption(SQL_INSERT_OPTION, typeOption, typePosition, maturite, strike, prime, idPortefeuille, code);
		investir(SQL_UPDATE_ARGENT_INVESTI, idPortefeuille, strike);
	}


	public void exercerOption(Integer idOption) {
		// TODO Auto-generated method stub
		
	}*/
    

// METHODES PRIVEES
    private Portefeuille charger( String sql, String login ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Portefeuille p = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement une adresse email) et exécution.
             */
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
    
    private void chargerTitres( String sql, Portefeuille p) throws DAOException {
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
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, p.getIdPortefeuille());
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            while ( resultSet.next() ) {
            	String code = resultSet.getString("code");
            	Titre titre = daoFactory.getTitreDao().recupererTitre(code);
            	Integer quantite = resultSet.getInt("quantite");
            	Double prixUnitaire = (double) resultSet.getFloat("prixUnitaire");
            	// set quantite et prix dans p
            	p.ajoutPrixTitre(titre, prixUnitaire);
            	p.ajoutQuantiteTitre(titre, quantite);            	
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }

    private void chargerOptions( String sql, Portefeuille p) throws DAOException {
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
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, p.getIdPortefeuille());
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            while ( resultSet.next() ) {
            	Integer idOption = resultSet.getInt("idOption");
            	TypeOption type ;
            	if (resultSet.getString("type").equals("CALL")) {
            		type = TypeOption.CALL;
            	} else {
            		type = TypeOption.PUT;
            	}
            	TypePosition position ;
            	if (resultSet.getString("position").equals("LONG")) {
            		position = TypePosition.LONG;
            	} else {
            		position = TypePosition.SHORT;
            	}           	
            	GregorianCalendar maturite = new GregorianCalendar();
            	maturite.setTime(resultSet.getDate("maturite"));
            	Double strike = (double) resultSet.getFloat("strike");
            	Double prime = (double) resultSet.getFloat("prime");
            	
            	Titre titre = daoFactory.getTitreDao().recupererTitre(resultSet.getString("code"));
       
            	Option option = new Option(idOption,type,position,maturite,strike,prime,titre);
            	
            	// set quantite et prix dans p
            	p.ajoutPrixOption(option, prime);
            	p.ajoutQuantiteOption(option, 1);            	
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }


	private void chargerObligations(String sql, Portefeuille p) {
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
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, p.getIdPortefeuille());
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            while ( resultSet.next() ) {
            	String emetteur = resultSet.getString("emetteur");
            	GregorianCalendar dateFin = new GregorianCalendar();
            	dateFin.setTime(resultSet.getDate("dateFin"));
            	
            	Obligation obligation = daoFactory.getObligationDao().recupererObligation(emetteur);
            	
            	obligation.setDateFin(dateFin);
            	
            	Integer quantite = resultSet.getInt("nombre");
            	Double prix = obligation.getPrix();
            	
            	// set quantite et prix dans p
            	p.ajoutPrixObligation(obligation, prix); // prix*quantite?
            	p.ajoutQuantiteObligation(obligation, quantite);            	
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
	}
    
    private static Portefeuille map( ResultSet resultSet ) throws SQLException {
        Portefeuille p = new Portefeuille();

        p.setArgentInvesti(Double.parseDouble(resultSet.getString("argentInvesti")));
        p.setArgentDisponible(Double.parseDouble(resultSet.getString("argentDisponible")));
        if (resultSet.getString("rendement") != null) {
        	p.setRendement(Double.parseDouble(resultSet.getString("rendement")));
        } else {
        	p.setRendement(-1.0);
        }
        p.setIdPortefeuille(Integer.parseInt(resultSet.getString("idPortefeuille")));
        
        return p;
    }
    
	private void acquerirOption(String sql, TypeOption typeOption, TypePosition typePosition, GregorianCalendar maturite, Double strike, Double prime, Integer idPortefeuille, String code) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            
        	preparedStatement = initialisationRequetePreparee(connexion, sql, true, typeOption, typePosition, maturite, strike, prime, idPortefeuille, code);
        	int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de l'ajout de l'option, aucune ligne ajoutée dans la table." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }	
	}
	
	private void investir(String sql, Integer idPortefeuille, Double somme) {
	  	Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
		    connexion = daoFactory.getConnection();
		    
			preparedStatement = initialisationRequetePreparee(connexion, sql, true, idPortefeuille, somme);
			int statut = preparedStatement.executeUpdate();
		    if ( statut == 0 ) {
		        throw new DAOException( "Échec de modification du l'argent investi et de l'argent disponible du portefeuille." );
		}
		} catch ( SQLException e ) {
		    throw new DAOException( e );
		} finally {
		    fermeturesSilencieuses( preparedStatement, connexion );
		}	
	}


	@Override
	public void creer(String login, Portefeuille portefeuille) throws DAOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mettreAJour(String login, Portefeuille portefeuille) throws DAOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void supprimer(String login) throws DAOException {
		// TODO Auto-generated method stub
		
	}
}
