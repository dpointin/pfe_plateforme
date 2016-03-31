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
	
	public static final String ACCES_CONNEXION = "/connexion";
	public static final String ATT_SESSION_USER = "sessionJoueur";
	public static final String ALERTE_PB_CONNEXION="messageAlerte";
	
	public void init( FilterConfig config ) throws ServletException {
	}
	
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
	
	public void destroy() {
		
	}
} 