package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.config.DAOException;


/**
* Classe DAOFactory representant la fabrique DAO
*
* @author  Celine Chaugny & Damien Pointin 
*/
public final class DAOUtilitaire {
   	/**
	* Constructeur cache par defaut (car c'est une classe finale utilitaire,
    * contenant uniquement des methode appelees de maniere statique)
	*/ 
    private DAOUtilitaire() {
    }


    /**
	* Fermeture silencieuse du resultset
	* 
	* @param resultSet
	* le resultSet que l'on veut fermer
	*
	* @see SQLException
	* @see ResultSet
	*/ 
    public static void fermetureSilencieuse( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }


    /**
   	* Fermeture silencieuse du statement
   	* 
   	* @param statement
   	* le statement que l'on veut fermer
   	*
   	* @see SQLException
   	* @see Statement
   	*/
    public static void fermetureSilencieuse( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }
    
    
    /**
   	* Fermeture silencieuse de la connexion
   	* 
   	* @param connexion
   	* la connexion que l'on veut fermer
   	*
   	* @see SQLException
   	* @see Connection
   	*/
    public static void fermetureSilencieuse( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }

    
    /**
   	* Fermeture silencieuse du statement et de la connexion
   	* 
   	* @param statement
   	* le statement que l'on veut fermer
   	* @param connexion
   	* la connexion que l'on veut fermer
   	* 
   	* @see SQLException
   	* @see Statement
   	* @see Connection
   	*/
    public static void fermeturesSilencieuses( Statement statement, Connection connexion ) {
        fermetureSilencieuse( statement );
        fermetureSilencieuse( connexion );
    }

    
    /**
   	* Fermeture silencieuse du resultSet, du statement et de la connexion
   	* 
   	* @param resultSet
   	* le resultSet que l'on veut fermer
   	* @param statement
   	* le statement que l'on veut fermer
   	* @param connexion
   	* la connexion que l'on veut fermer
   	* 
   	* @see SQLException
   	* @see Statement
   	* @see Connection
   	* @see ResultSet
   	*/
    public static void fermeturesSilencieuses( ResultSet resultSet, Statement statement, Connection connexion ) {
        fermetureSilencieuse( resultSet );
        fermetureSilencieuse( statement );
        fermetureSilencieuse( connexion );
    }

    
    /**
   	* Initialise la requete preparee basee sur la connexion passee en argument,
    * avec la requete SQL et les objets donnes.
   	* 
   	* @param connexion
   	* la connexion a la bdd
   	* @param sql
   	* la requete SQL
   	* @param returnGeneratedKeys
   	* boolean stipulant s'il faut retourner des valeurs auto-generees
   	* @param objets
   	* une succession d'objets correspondant aux parametres de la requete
   	* 
   	* @return la requete preparee
   	* 
   	* @throws SQLException Une exception si la preparation de la requete se passe mal
   	* @see PreparedStatement
   	*/
    public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
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
   	* Methode privee qui permet de savoir si une requete renvoie au moins une ligne
   	*
   	* @param sql correspondant a la requete SQL
   	* @param objets parametre de la requete 
   	* 
   	* @throws DAOException Si une erreur arrive lors l'execution de la requete
   	* 
   	* @see DAOException
   	*/ 
	public static boolean verification(DAOFactory daoFactory, String sql, Object... objets){
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
