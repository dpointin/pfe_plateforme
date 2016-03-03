package dao;

/**
* Classe DAOException representant les exceptions du DAO
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class DAOException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


	/**
	* Constructeur DAOException.
	* <p>
	* Avec le paramètre message
	* </p>
	*
	* @param message
	* Le message de l'exception.
	*/ 
	public DAOException( String message ) {
		super( message );
	}
	
	
	/**
	* Constructeur DAOException.
	* <p>
	* Avec le paramètre message et la cause
	* </p>
	*
	* @param message
	* Le message de l'exception.
	* @param cause
	* La cause de l'exception
	*/ 
	public DAOException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	
	/**
	* Constructeur DAOException.
	* <p>
	* Avec le paramètre cause
	* </p>
	*
	* @param cause
	* La cause de l'exception.
	*/ 	
	public DAOException( Throwable cause ) {
		super( cause );
	}
	
} 