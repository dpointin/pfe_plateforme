package controleur;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.JoueurDao;
import dao.PortefeuilleDao;
import modele.Joueur;
import modele.Portefeuille;

/**
* Servlet classement gerant l'affichage du classement des joueurs
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Classement extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	* CONF_DAO_FACTORY correspond a l'attribut daoFactory.
	*/ 
	public static final String CONF_DAO_FACTORY = "daofactory";

	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut session Joueur
	*/ 
	public static final String ATT_SESSION_JOUEUR = "sessionJoueur";
	
	/**
	* ATT_REQUEST_CLASSEMENT correspond a l'attribut request classement
	*/ 
	public static final String ATT_REQUEST_CLASSEMENT = "classement";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/classement.jsp";

	/**
	* VUE_PORTEFEUILLE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_PORTEFEUILLE = "/WEB-INF/joueurConnecte/porteufeuille.jsp";

	
	/**
	* Le joueurDao de notre servlet
	*/ 
	private JoueurDao joueurDao;
	
	/**
	* Le portefeuilleDao de notre servlet
	*/ 
	private PortefeuilleDao portefeuilleDao;
	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
	public void init() throws ServletException {
		this.joueurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getJoueurDao();
		this.portefeuilleDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPortefeuilleDao();
	}
	
	/**
	* Implementation de la methode doGet
	* affiche la page de classement des joueurs selon l'argent qu'ils ont.
	* 
	* @param request
	* la HttpRequeteServlet
	* @param response
	* la HttpServletResponse 
	* 
	* @throws ServletException en cas d'erreur
	* @throws IOException en cas d'erreur
	*/ 
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Vector<Joueur> joueursClasses = joueurDao.trouverTousLesJoueurs();
		request.setAttribute( ATT_REQUEST_CLASSEMENT, joueursClasses );
	
		String redir = request.getParameter("redir");
		if (redir != null) {
			if (redir.equals("supprimer")) {
				HttpSession session = request.getSession();	
				Joueur joueur = (Joueur) session.getAttribute( ATT_SESSION_JOUEUR );
				portefeuilleDao.supprimer(joueur.getLogin());
				joueur.setPortefeuille(new Portefeuille(10000.0));
				portefeuilleDao.creer(joueur.getLogin(), joueur.getPortefeuille());
				this.getServletContext().getRequestDispatcher( VUE_PORTEFEUILLE ).forward( request, response );
			}
		} else {
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
	}
	
	
	/**
	* Implementation de la methode doPost (ne fait rien)
	* 
	* @param request
	* la HttpRequeteServlet
	* @param response
	* la HttpServletResponse 
	* 
	* @throws ServletException en cas d'erreur
	* @throws IOException en cas d'erreur
	*/ 
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
