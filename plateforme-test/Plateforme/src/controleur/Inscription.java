package controleur;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controleur.formulaire.InscriptionForm;
import dao.DAOFactory;
import dao.JoueurDao;
import dao.PortefeuilleDao;
import modele.Joueur;
import modele.Portefeuille;

/**
* Servlet inscription gerant l'inscription d'un joueur
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Inscription extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	* CONF_DAO_FACTORY correspond a l'attribut daoFactory.
	*/ 
	public static final String CONF_DAO_FACTORY = "daofactory";
		
	/**
	* ATT_USER correspond a l'attribut joueur
	*/ 
	public static final String ATT_USER = "joueur";
	
	/**
	* ATT_FORM correspond a l'attribut form
	*/ 
	public static final String ATT_FORM = "form";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/inscription.jsp";
	
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
	* affiche la page d'inscription
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
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
	
	
	/**
	* Implementation de la methode doPost
	* effectue l'inscription du Joueur puis affiche la page d'inscription
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
		/* Préparation de l'objet formulaire */
		InscriptionForm form = new InscriptionForm( joueurDao );
		/* Traitement de la requête et récupération du bean en résultant */
		Joueur joueur = form.inscrireJoueur( request );
		if(joueur!=null){
		Portefeuille portefeuille = new Portefeuille();
		portefeuilleDao.creer(joueur.getLogin(), portefeuille);}
		
		/* Stockage du formulaire et du bean dans l'objet request */
		request.setAttribute( ATT_FORM, form );
		request.setAttribute( ATT_USER, joueur );
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
} 