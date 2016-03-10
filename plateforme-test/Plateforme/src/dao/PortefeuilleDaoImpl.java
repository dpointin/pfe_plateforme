package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Iterator;

import modele.ObjetFinancier;
import modele.Obligation;
import modele.Option;
import modele.Portefeuille;
import modele.Titre;
import modele.TypeOption;
import modele.TypePosition;

public class PortefeuilleDaoImpl implements PortefeuilleDao {

    private static final String SQL_SELECT_PAR_LOGIN = "SELECT * FROM Portefeuille WHERE login = ?";
    private static final String SQL_DELETE_CODE="DELETE FROM Portefeuille WHERE idPortefeuille=?";
    private static final String SQL_INSERT="INSERT INTO Portefeuille (login, argentInvesti, argentDisponible, rendement VALUES (?,?,?,?)";
    
    private static final String SQL_UPDATE_ARGENT_INVESTI = "UPDATE Portefeuille SET argentInvesti=? WHERE idPortefeuille=?";
    private static final String SQL_UPDATE_ARGENT_DISPONIBLE = "UPDATE Portefeuille SET argentDisponible=? WHERE idPortefeuille=?";
    
    private DAOFactory daoFactory;

    PortefeuilleDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
    
	@Override
	public Portefeuille charger(String login) throws DAOException {
		Portefeuille p = charger(SQL_SELECT_PAR_LOGIN, login);
		
		EstComposeTitreDao estTitre=new EstComposeTitreDaoImpl(daoFactory);
		estTitre.trouver(p);
		EstComposeObligationDao estObligation= new EstComposeObligationDaoImpl(daoFactory);
		estObligation.trouver(p);
		
		return p;
	}

	@Override
	public void creer(String login, Portefeuille portefeuille) throws DAOException {

	}


	@Override
	public void mettreAJour(Portefeuille portefeuille, ObjetFinancier objetFinancier) throws DAOException {
		if(objetFinancier instanceof Titre){
			EstComposeTitreDao titre=new EstComposeTitreDaoImpl(daoFactory);
			titre.mettreAJour(portefeuille, (Titre)objetFinancier); 
		}else { if(objetFinancier instanceof Obligation){
						EstComposeObligationDao obligation=new EstComposeObligationDaoImpl(daoFactory);
						obligation.mettreAJour(portefeuille, (Obligation) objetFinancier); 
				}else{
					
				}
		}
		executeRequete(SQL_UPDATE_ARGENT_DISPONIBLE, portefeuille.getArgentDisponible(), portefeuille.getIdPortefeuille());
		executeRequete(SQL_UPDATE_ARGENT_INVESTI, portefeuille.getArgentInvesti(), portefeuille.getIdPortefeuille());
	}


	@Override
	public void supprimer(String login) throws DAOException {
		Portefeuille p=charger(login);
		
		EstComposeTitreDao estTitre=new EstComposeTitreDaoImpl(daoFactory);
		estTitre.supprimer(p.getIdPortefeuille());
		EstComposeObligationDao estCompose=new EstComposeObligationDaoImpl(daoFactory);
		estCompose.supprimer(p.getIdPortefeuille());
		
		executeRequete(SQL_DELETE_CODE, p.getIdPortefeuille());
	}
    

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

    
  /*  private void chargerOptions( String sql, Portefeuille p) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connexion depuis la Factory 
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement une adresse email) et exécution.
             
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, p.getIdPortefeuille());
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet 
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
            	p.ajoutPrixObjetFinancier(option, prime);
            	p.ajoutQuantiteObjetFinancier(option, 1);            	
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }*/

    
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
    
/*	private void acquerirOption(String sql, TypeOption typeOption, TypePosition typePosition, GregorianCalendar maturite, Double strike, Double prime, Integer idPortefeuille, String code) throws DAOException {
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
	}*/
	
	/*private void investir(String sql, Integer idPortefeuille, Double somme) {
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
	}*/


}
