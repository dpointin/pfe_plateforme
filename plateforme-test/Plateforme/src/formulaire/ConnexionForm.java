package formulaire;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import dao.DAOException;
import dao.JoueurDao;
import modele.Joueur;

/**
* Classe ConnexionForm representant un formulaire de connexion
*
* @author  Celine Chaugny & Damien Pointin 
*/
public final class ConnexionForm {
	/**
	* Le CHAMP_LOGIN correspond au login.
	*/ 
	private static final String CHAMP_LOGIN = "login";
	
	
	/**
	* Le CHAMP_PASS correspond au motDePasse.
	*/
	private static final String CHAMP_PASS = "motDePasse";

	
	/**
	* Le ALGO_CHIFFREMENT correspond à SHA-256.
	*/
	private static final String ALGO_CHIFFREMENT ="SHA-256"; 
	
	
	/**
	* Le resultat du formulaire.
	*/ 
	private String resultat;
	
	
	/**
	* Les erreurs du formulaire.
	* Une map liant les mots clés d'erreur à la précision du type d'erreur 
	*/ 
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	
	/**
	* Le joueurDao du formulaire.
	*
	* @see JoueurDao
	* @see InscriptionForm#InscriptionForm(JoueurDao)
	*/ 
	private JoueurDao joueurDao;
	
	
	/**
	* Retourne le resultat du formulaire.
	*
	* @return resultat du formulaire.
	*/ 
	public String getResultat() {
		return resultat;
	}
	
	
	/**
	* Retourne les erreurs du formulaire
	* 
	* @return Map<String,String> correspondant aux erreurs.
	*/ 
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	
	/**
	* Constructeur ConnexionForm.
	* <p>
	* Avec le parametre joueurDao
	* </p>
	*
	* @param joueur
	* Le joueurDao du formulaire.
	*
	* @see ConnexionForm#joueurDao
	* @see JoueurDao
	*/ 	
	public ConnexionForm( JoueurDao joueur){
		this.joueurDao=joueur;
	}
	
	
	/**
	* Connecte un joueur a la base de donnes si les donnees sont valides
	* 
	* @param request
	* la requete http
	*
	* @return le joueur qui est connecte
	* 
	* @see ConnexionForm#getValeurChamp(HttpServletRequest, String)
	* @see Joueur
	* @see ConnexionForm#traiterLogin(String, Joueur)
	* @see ConnexionForm#traiterMotDePasse(String, String, Joueur)
	* @see JoueurDao
	* @see DAOException
	*/ 
	public Joueur connecterJoueur( HttpServletRequest request ) {
		/* Récupération des champs du formulaire */
		String login = getValeurChamp( request, CHAMP_LOGIN );
		String motDePasse = getValeurChamp( request, CHAMP_PASS );
		Joueur joueur = new Joueur();
		
		try {
            traiterLogin( login, joueur );
            traiterMotDePasse(login,  motDePasse, joueur );

            /* Initialisation du résultat global de la validation. */
    		if ( erreurs.isEmpty() ) {
    			resultat = "Succès de la connexion.";
    		} else {
    			resultat = "Échec de la connexion.";
    		}
        } catch ( DAOException e ) {
            resultat = "Échec de la connexion : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
		
		return joueur;
	}
	
	
	/**
	* Vérifie la validite du login (deja present dans la table)
	* si le login est valide on l'ajoute au joueur
	* 
	* @param login
	* le login du joueur a verifier
	* @param joueur
	* le joueur auquel on ajoute le login
	* 
	* @see ConnexionForm#validationLogin(String)
	* @see Joueur
	* @see FormValidationException
	*/
	private void traiterLogin( String login, Joueur joueur ) {
	        try {
	            validationLogin( login );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_LOGIN, e.getMessage() );
	        }
	        joueur.setLogin( login );
	    }
	
	
	/**
	* Validation du login du joueur
	* 
	* @param login
	* le login a verifie
	* 
	* @throws FormValidationException Si le login n'est pas valide
	* 
	* @see FormValidationException
	*/
	private void validationLogin( String login ) throws FormValidationException {
	        if ( login != null && login.length() > 3 ) {
	        	if (joueurDao.trouver(login)==null){
		            throw new FormValidationException( "Le login n'est pas enregistré." );
	        	}
	        	
	        }else
	        throw new FormValidationException( "Le login doit contenir au moins 3 caractères." );
	    }
	
	 
	/**
	* Validation du mot de passe 
	* par rapport au mot de passe crypte de la base
	* 
	* @param login
	* login du joueur dans la table
	* @param motDePasse
	* mot de passe a verifie
	* @param joueur 
	* joueur auquel on ajoute le mot de passe
	*
	* @see ConnexionForm#validationMotDePasse(String, String)
	* @see Joueur
	* @see FormValidationException
	*/
	 private void traiterMotDePasse( String login, String motDePasse, Joueur joueur) {
	        try {
	        	validationMotDePasse(login, motDePasse );
	        } catch ( FormValidationException e ) {
	            setErreur( CHAMP_PASS, e.getMessage() );
	        }
	 }
	 
	
	/**
	* Validation du mot de passe du joueur
	* 
	* @param motDePasse
	* mot de passe du joueur
	* @param lgin
	* login du joueur
	* 
	* @throws FormValidationException Si le mot de passe ne correspond pas 
	* 
	* @see ConfigurablePasswordEncryptor
	* @see FormValidationException
	*/
	private void validationMotDePasse( String login, String motDePasse ) throws FormValidationException {
		if ( motDePasse != null ) {
			if ( motDePasse.length() > 3 ) {
				Joueur base=joueurDao.trouver(login);
				ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
		        passwordEncryptor.setPlainDigest( false );
		        if( (base!=null) && !passwordEncryptor.checkPassword(motDePasse, base.getMotDePasse()) )
			        throw new FormValidationException( "Le mot de passe saisi n'est pas correct." ); 
			}else
		        	throw new FormValidationException( "Le mot de passe doit contenir au moins 3 caractères." );
		} else {
			throw new FormValidationException( "Merci de saisir votre mot de passe." );
		}
	}
	
	
	/**
	* Ajoute un message correspondant au champ spécifié à la map des erreurs.
	* 
	* @param champ
	* champ spécifié pour l'erreur
	* @param message
	* message correspondant au type d'erreur
	*/
	private void setErreur( String champ, String message ) {
		erreurs.put( champ, message );
	}
	
	
	/**
	* Méthode qui retourne null si le champ est vide,
	* son contenu sinon 
	* 
	* @param nomchamp
	* champ spécifié 
	* @param request
	* requete http
	*/
	private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
		String valeur = request.getParameter( nomChamp );
		if ( valeur == null || valeur.trim().length() == 0 ) {
			return null;
		} else {
			return valeur;
		}
	}
	
} 