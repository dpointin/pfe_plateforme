package formulaire;

/**
* Classe Joueur representant les exceptions de formulaire 
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class FormValidationException extends Exception {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	* Constructeur FormValidationException.
	* <p>
	* Avec le paramètre message
	* </p>
	*
	* @param message
	* Le message de l'exception.
	*/ 	
	public FormValidationException( String message ) {
		super( message );
	}
} 