package controleur.formulaire;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import dao.JoueurDao;
import dao.config.DAOException;
import modele.Joueur;


/**
* Classe InscriptionForm representant un formulaire d'inscription 
*
* @author  Celine Chaugny & Damien Pointin 
*/
public final class InscriptionForm {
	/**
	* Le CHAMP_LOGIN correspond au login.
	*/ 
    private static final String CHAMP_LOGIN        = "login";
    
    
    /**
	* Le CHAMP_PASS correspond au motDePasse.
	*/
    private static final String CHAMP_PASS       = "motDePasse";
   
    
    /**
	* Le CHAMP_LOGIN correspond à la confirmation.
	*/
    private static final String CHAMP_CONF       = "confirmation";

    
    /**
	* Le ALGO_CHIFFREMENT correspond à SHA-256.
	*/
    private static final String ALGO_CHIFFREMENT = "SHA-256";

    
    /**
	* Le resultat du formulaire.
	*/ 
    private String              resultat;
    
    
    /**
	* Les erreurs du formulaire.
	* Une map liant les mots clés d'erreur à la précision du type d'erreur 
	*/ 
    private Map<String, String> erreurs          = new HashMap<String, String>();
    
    
    /**
	* Le joueurDao du formulaire.
	*
	* @see JoueurDao
	* @see InscriptionForm#InscriptionForm(JoueurDao)
	*/ 
    private JoueurDao      joueurDao;

    
    /**
	* Constructeur InscriptionForm.
	* <p>
	* Avec le parametre joueurDao
	* </p>
	*
	* @param joueurDao
	* Le joueurDao du formulaire.
	*
	* @see InscriptionForm#joueurDao
	* @see JoueurDao
	*/ 	
    public InscriptionForm( JoueurDao joueurDao ) {
        this.joueurDao = joueurDao;
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
	* Retourne le resultat du formulaire.
	*
	* @return resultat du formulaire.
	*/ 
    public String getResultat() {
        return resultat;
    }

    
    /**
	* Inscris un joueur a la base de donnes si il est valide
	* 
	* @param request
	* la requete http
	*
	* @return le joueur qui est inscris
	* 
	* @see InscriptionForm#getValeurChamp(HttpServletRequest, String)
	* @see Joueur
	* @see InscriptionForm#traiterLogin(String, Joueur)
	* @see InscriptionForm#traiterMotsDePasse(String, String, Joueur)
	* @see JoueurDao
	* @see DAOException
	*/ 
    public Joueur inscrireJoueur( HttpServletRequest request ) {
    	String login = getValeurChamp( request, CHAMP_LOGIN );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );
        String confirmation = getValeurChamp( request, CHAMP_CONF );

        Joueur joueur = new Joueur();
        try {
            traiterLogin( login, joueur );
            traiterMotsDePasse( motDePasse, confirmation, joueur );
            if ( erreurs.isEmpty() ) {
                joueurDao.creer( joueur );
                resultat = "Succès de l'inscription.";
            } else {
                resultat = "Échec de l'inscription.";
            }
        } catch ( DAOException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return joueur;
    }

    
    /**
	* Vérifie la validite du login 
	* et l'ajoute au joueur s'il est valide
	* 
	* @param login
	* le login du joueur a verifier
	* @param joueur
	* le joueur auquel on ajoute le login
	* 
	* @see InscriptionForm#validationLogin(String)
	* @see Joueur
	* @see FormValidationException
	*/
    private void traiterLogin( String login, Joueur joueur ) {
        try {
            validationLogin( login );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_LOGIN, e.getMessage() );
        }
        joueur.setLogin(login );
    }

    
    /**
	* Validation des mots de passe 
	* et ajoute le mot de passe au joueur s'il est valide
	* lors de l'ajout on crypte le mot de passe
	* 
	* @param motDePasse
	* mot de passe du joueur
	* @param confirmation
	* confirmation de mot de passe saisie
	* @param joueur 
	* joueur auquel on ajoute le mot de passe
	*
	* @see InscriptionForm#validationMotsDePasse(String, String)
	* @see Joueur
	* @see FormValidationException
	* @see ConfigurablePasswordEncryptor
	*/
    private void traiterMotsDePasse( String motDePasse, String confirmation, Joueur joueur ) {
        try {
            validationMotsDePasse( motDePasse, confirmation );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
            setErreur( CHAMP_CONF, null );
        }
        /*
         * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe
         * efficacement.
         * 
         * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
         * aléatoire et un grand nombre d'itérations de la fonction de hashage.
         * 
         * La String retournée est de longueur 56 et contient le hash en Base64.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );
        joueur.setMotDePasse( motDePasseChiffre );
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
        	if(joueurDao.trouver(login)!=null){
        		throw new FormValidationException("Ce login est deja utilise");
        	}	
        }else
        	throw new FormValidationException( "Le login doit contenir au moins 3 caractères." );
    }


    /**
	* Validation des mots de passe du joueur
	* 
	* @param motDePasse
	* mot de passe du joueur
	* @param confirmation
	* confimation du mot de passe du joueur
	* 
	* @throws FormValidationException Si les mots de passe ne sont pas valides
	* 
	* @see FormValidationException
	*/
    private void validationMotsDePasse( String motDePasse, String confirmation ) throws FormValidationException {
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
                throw new FormValidationException( "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
            } else if ( motDePasse.length() < 3 ) {
                throw new FormValidationException( "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir et confirmer votre mot de passe." );
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