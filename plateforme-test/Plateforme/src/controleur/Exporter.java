package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.PortefeuilleDao;
import modele.Joueur;

/**
* Servlet exporter gerant l'exportation du portefeuille d'un joueur
*
* @author  Celine Chaugny & Damien Pointin 
*/
@WebServlet("/Exporter")
public class Exporter extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/exporter.jsp";
	
	/**
	* CONF_DAO_FACTORY correspond a l'attribut daoFactory.
	*/ 
	public static final String CONF_DAO_FACTORY = "daofactory";

	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut session Joueur
	*/ 
	public static final String ATT_SESSION_JOUEUR = "sessionJoueur";
	
	/**
	* ATT_SESSION_PORTEFEUILLE correspond a l'attribut session Portefeuille
	*/ 
	public static final String ATT_SESSION_PORTEFEUILLE = "portefeuille";
	
	/**
	* Le portefeuilleDao de notre servlet
	*/ 
	private PortefeuilleDao portefeuilleDao;

	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
    @Override
    public void init() throws ServletException {
	 	this.portefeuilleDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPortefeuilleDao();
    }

	/**
	* Implementation de la methode doGet
	* affiche la page du csv
	* 
	* @param request
	* la HttpRequeteServlet
	* @param response
	* la HttpServletResponse 
	* 
	* @throws ServletException en cas d'erreur
	* @throws IOException en cas d'erreur
	*/ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Joueur joueur = (Joueur) session.getAttribute( ATT_SESSION_JOUEUR );
		session.setAttribute(ATT_SESSION_PORTEFEUILLE, portefeuilleDao.charger(joueur.getLogin()));
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );		
	}

	/**
	* Implementation de la methode doPost
	* 
	* @param request
	* la HttpRequeteServlet
	* @param response
	* la HttpServletResponse 
	* 
	* @throws ServletException en cas d'erreur
	* @throws IOException en cas d'erreur
	*/ 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );		
	}

}
