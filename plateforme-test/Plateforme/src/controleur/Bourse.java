package controleur;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.ObligationDao;
import dao.TitreDao;
import modele.Obligation;
import modele.Titre;

public class Bourse extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	* CONF_DAO_FACTORY correspond a l'attribut daoFactory.
	*/ 
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut titres
	*/ 
	public static final String ATT_SESSION_TITRES = "titres";

	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut titres
	*/ 
	public static final String ATT_SESSION_OBLIGATIONS = "obligations";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/bourse.jsp";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_ACTIONS = "/WEB-INF/joueurConnecte/bourseActions.jsp";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_INDICES = "/WEB-INF/joueurConnecte/bourseIndices.jsp";

	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_OBLIGATIONS = "/WEB-INF/joueurConnecte/bourseObligations.jsp";
	
	/**
	* Le joueurDao de notre servlet
	*/ 
	private TitreDao titreDao;
	
	/**
	* Le joueurDao de notre servlet
	*/ 
	private ObligationDao obligationDao;
	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
	public void init() throws ServletException {
		/* Récupération d'une instance de nos DAO Obligation et Titre */
		this.titreDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getTitreDao();
		this.obligationDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getObligationDao();
	}
	
	/**
	* Implementation de la methode doGet
	* affiche la page de connexion
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
		/* Affichage de la page de connexion */
		ArrayList<Titre> titres = titreDao.trouverTousTitres();
		ArrayList<Obligation> obligations = obligationDao.trouverToutesObligations();
		
		HttpSession session = request.getSession();
		session.setAttribute( ATT_SESSION_TITRES, titres );
		session.setAttribute( ATT_SESSION_OBLIGATIONS, obligations );
		
		String redir = request.getParameter("redir");
		if (redir != null) {
			if (redir.equals("actions")) {
				this.getServletContext().getRequestDispatcher( VUE_ACTIONS ).forward( request, response );
			} else if (redir.equals("indices")) {
				this.getServletContext().getRequestDispatcher( VUE_INDICES ).forward( request, response );				
			} else {
				this.getServletContext().getRequestDispatcher( VUE_OBLIGATIONS ).forward( request, response );
			}
		} else {
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
	}
	
	
	/**
	* Implementation de la methode doPost
	* effectue la connexion du joueur s'il n'y a eu aucune erreur, ajout de l'attribut
	* dans la session puis affichage de la page connexion
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
		ArrayList<Titre> titres = titreDao.trouverTousTitres();

		HttpSession session = request.getSession();
		session.setAttribute( ATT_SESSION_TITRES, titres );
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

	}

	
}
