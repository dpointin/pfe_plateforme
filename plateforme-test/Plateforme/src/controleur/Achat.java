package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.ObligationDao;
import dao.PortefeuilleDao;
import dao.TitreDao;
import modele.Joueur;
import modele.ObjetFinancier;
import modele.Obligation;
import modele.Portefeuille;
import modele.Titre;

public class Achat extends HttpServlet {
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
	public static final String ATT_SESSION_PORTEFEUILLE = "portefeuille";

	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut session Joueur
	*/ 
	public static final String ATT_SESSION_JOUEUR = "sessionJoueur";
	
	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut titres
	*/ 
	public static final String ATT_SESSION_TITRES = "titres";

	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/portefeuilleAcheter.jsp";
	
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_PORTEFEUILLE = "/WEB-INF/joueurConnecte/portefeuille.jsp";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_GESTION_PORTEFEUILLE = "/WEB-INF/joueurConnecte/portefeuilleIndicateurs.jsp";
	
	/**
	* Le joueurDao de notre servlet
	*/ 
	private PortefeuilleDao portefeuilleDao;
	private TitreDao titreDao;	
	private ObligationDao obligationDao;
	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
	public void init() throws ServletException {
		/* Récupération d'une instance de nos DAO Obligation et Titre */
		this.portefeuilleDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPortefeuilleDao();
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
		
		HttpSession session = request.getSession();
		session.setAttribute( ATT_SESSION_TITRES, titres );
	
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
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
		HttpSession session = request.getSession();
		
		Joueur joueur = (Joueur) session.getAttribute( ATT_SESSION_JOUEUR );
		Portefeuille portefeuille = portefeuilleDao.charger(joueur.getLogin());
		
		String motsCles = request.getParameter("motscles");
		String type = request.getParameter("type");
		ArrayList<Titre> titres = new ArrayList<Titre>();
		ArrayList<Obligation> obligations = new ArrayList<Obligation>();
		Vector<ObjetFinancier> objetsFinanciers = new Vector<ObjetFinancier>();
		
		if (type != null) {
			if (type.equals("TOUS")) {
				titres = titreDao.rechercheTitres(motsCles, type);
				obligations = obligationDao.rechercheObligations(motsCles);
			} else if (type.equals("ACTION")) {
				titres = titreDao.rechercheTitres(motsCles, type);
			} else if (type.equals("INDICE")) {
				titres = titreDao.rechercheTitres(motsCles, type);				
			} else {
				obligations = obligationDao.rechercheObligations(motsCles);
			}
		} else {
			titres = titreDao.trouverTousTitres();
			obligations = obligationDao.trouverToutesObligations();
		}
		
		for (Titre t : titres) {
			objetsFinanciers.add(t);
		}
		for (Obligation o : obligations) {
			objetsFinanciers.add(o);
		}
		
	/*
		String code = request.getParameter("code");
		
		Integer quantite = Integer.parseInt(request.getParameter("quantite"));

		Titre titre = titreDao.recupererTitre(code);
		
		
		Joueur joueur = (Joueur) session.getAttribute( ATT_SESSION_JOUEUR );
		Portefeuille portefeuille = portefeuilleDao.charger(joueur.getLogin());
		
		titre=(Titre)portefeuille.trouver(titre);
		portefeuille.acheter(titre, quantite);
		
		portefeuilleDao.mettreAJour(portefeuille, titre);
		
		session.setAttribute(ATT_SESSION_PORTEFEUILLE, portefeuille);
	*/
		request.setAttribute("objetsFinanciers",objetsFinanciers);
		this.getServletContext().getRequestDispatcher( VUE_PORTEFEUILLE ).forward( request, response );
	}

	
}
