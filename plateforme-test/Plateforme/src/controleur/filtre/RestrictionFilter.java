package controleur.filtre;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
* Filtre réduisant l'accés
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class RestrictionFilter implements Filter {
	/**
	* ACCES_CONNEXION correspond a le vue connexion
	*/ 
	public static final String ACCES_CONNEXION = "/connexion";
	
	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut session Joueur
	*/ 
	public static final String ATT_SESSION_USER = "sessionJoueur";
	
	
	/**
	* ALERTE_PB_CONNEXION correspond au message d'alerte
	*/ 
	public static final String ALERTE_PB_CONNEXION="messageAlerte";
	
	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si l'init se passe mal
	*/ 
	public void init( FilterConfig config ) throws ServletException {
	}
	
	
	/**
	* Implementation de la methode doFilter qui realise le filtre
	* 
	* @param req
	* la HttpRequeteServlet
	* @param res
	* la HttpServletResponse 
	* @param chain 
	* le filtre chain
	* 
	* @throws ServletException en cas d'erreur
	* @throws IOException en cas d'erreur
	*/ 
	public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
	ServletException {
		/* Cast des objets request et response */
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		/* Non-filtrage des ressources statiques */
		String chemin = request.getRequestURI().substring( request.getContextPath().length() );
		if ( chemin.startsWith( "/inc" ) ) {
			chain.doFilter( request, response );
			return;
		}
		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();
		/*
		* Si l'objet utilisateur n'existe pas dans la session en cours, alors
		* l'utilisateur n'est pas connecté.
		*/
		if ( session.getAttribute( ATT_SESSION_USER ) == null ) {
			/* Redirection vers la page publique */
			session.setAttribute(ALERTE_PB_CONNEXION, "test");
			request.getRequestDispatcher( ACCES_CONNEXION ).forward( request, response );
		} else {
			/* Affichage de la page restreinte */
			chain.doFilter( request, response );
		}
	} 
	
	
	/**
	* Fonction qui detruit la servlet
	*/ 
	public void destroy() {
		
	}
} 