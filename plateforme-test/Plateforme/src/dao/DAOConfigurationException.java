package dao;

/**
* Classe DAOConfigurationException representant les exceptions de configuration du DAO
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class DAOConfigurationException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


	/**
	* Constructeur DAOConfigurationException.
	* <p>
	* Avec le parametre message
	* </p>
	*
	* @param message
	* Le message de l'exception.
	*/ 	
	public DAOConfigurationException( String message ) {
		super( message );
	}
	
	
	/**
	* Constructeur DAOConfigurationException.
	* <p>
	* Avec le parametre message et la cause
	* </p>
	*
	* @param message
	* Le message de l'exception.
	* @param cause
	* La cause de l'exception
	*/ 	
	public DAOConfigurationException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	
	/**
	* Constructeur DAOConfigurationException.
	* <p>
	* Avec le parametre cause
	* </p>
	*
	* @param cause
	* La cause de l'exception.
	*/ 	
	public DAOConfigurationException( Throwable cause ) {
		super( cause );
	}
	
} 