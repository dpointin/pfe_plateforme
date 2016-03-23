package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
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

/**
* Servlet vente gerant la vente d'un objet financier du portefeuille
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Vente extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	* CONF_DAO_FACTORY correspond a l'attribut daoFactory.
	*/ 
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	/**
	* ATT_SESSION_PORTEFEUILLE correspond a l'attribut portefeuille
	*/ 
	public static final String ATT_SESSION_PORTEFEUILLE = "portefeuille";

	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut session Joueur
	*/ 
	public static final String ATT_SESSION_JOUEUR = "sessionJoueur";
	
	/**
	* ATT_SESSION_OBJETS_FINANCIERS correspond a l'attribut session ObjetsFinanciers
	*/ 
	public static final String ATT_SESSION_OBJETS_FINANCIERS = "objetsFinanciers";

	/**
	* ATT_ERREUR correspond a l'attribut erreur en cas de vente impossible
	*/ 
	public static final String ATT_ERREUR = "erreur";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/portefeuilleVendre.jsp";
	
	/**
	* VUE_PORTEFEUILLE correspond a la jsp lie au portefeuille
	*/ 
	public static final String VUE_PORTEFEUILLE = "/WEB-INF/joueurConnecte/portefeuille.jsp";
	
	/**
	* Le portefeuilleDao de notre servlet
	*/ 
	private PortefeuilleDao portefeuilleDao;
	
	/**
	* Le titreDao de notre servlet
	*/ 
	private TitreDao titreDao;
	
	/**
	* Le obligationDao de notre servlet
	*/ 
	private ObligationDao obligationDao;
	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
	public void init() throws ServletException {
		this.portefeuilleDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPortefeuilleDao();
		this.titreDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getTitreDao();
		this.obligationDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getObligationDao();
	}
	
	/**
	* Implementation de la methode doGet
	* affiche la page de vente
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
		HttpSession session = request.getSession();
		Joueur joueur = (Joueur) session.getAttribute( ATT_SESSION_JOUEUR );
		
		Portefeuille portefeuille = portefeuilleDao.charger(joueur.getLogin());
		if (portefeuille != null) {
			session.setAttribute( ATT_SESSION_PORTEFEUILLE, portefeuille );
		} else {
			session.setAttribute( ATT_SESSION_PORTEFEUILLE, new Portefeuille() );
		}

		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );		
	}
	
	
	/**
	* Implementation de la methode doPost
	* effectue la vente d'un objet financier s'il n'y a eu aucune erreur
	* puis affiche le portefeuille si cela s'est bien passe
	* ou reste sur la page de vente si la vente n'est pas possible
	* (quantite superieure a la quantite possedee)
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
		/* Vente */
		HttpSession session = request.getSession();	
		Joueur joueur = (Joueur) session.getAttribute( ATT_SESSION_JOUEUR );
		Portefeuille portefeuille = portefeuilleDao.charger(joueur.getLogin());
		
		Enumeration<ObjetFinancier> enum_ObjetsFinanciers = portefeuille.getPrixObjetFinancier().keys();
		ArrayList<ObjetFinancier> objetsFinanciers = Collections.list(enum_ObjetsFinanciers);
		
		String quantiteString = request.getParameter("quantite");
		if (quantiteString != null && !quantiteString.equals("")) {
			Integer quantite = Integer.parseInt(quantiteString);
			int i = 0;
			while (i < objetsFinanciers.size()) {
				if (objetsFinanciers.get(i) instanceof Titre) {
					if (request.getParameter(((Titre)objetsFinanciers.get(i)).getCode())!=null) {
						Titre titre = titreDao.recupererTitre(((Titre)objetsFinanciers.get(i)).getCode());
						titre=(Titre)portefeuille.trouver(titre);
						if (portefeuille.vendre(titre, quantite)) {
							portefeuilleDao.mettreAJour(portefeuille, titre);
							session.setAttribute(ATT_SESSION_PORTEFEUILLE, portefeuille);
							this.getServletContext().getRequestDispatcher( VUE_PORTEFEUILLE ).forward( request, response );
						} else { 
							request.setAttribute( ATT_ERREUR, "Vente impossible : il n'y pas assez de titres disponibles.");
							request.setAttribute( ATT_SESSION_OBJETS_FINANCIERS, objetsFinanciers );
							this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
						}
					}
				} else {
					if (request.getParameter(((Obligation)objetsFinanciers.get(i)).getEmetteur())!=null) {
						Obligation obligation = obligationDao.recupererObligation(((Obligation)objetsFinanciers.get(i)).getEmetteur());
						obligation=(Obligation)portefeuille.trouver(obligation);
						if (portefeuille.vendre(obligation, quantite)) {
							portefeuilleDao.mettreAJour(portefeuille, obligation);
							session.setAttribute(ATT_SESSION_PORTEFEUILLE, portefeuille);
							this.getServletContext().getRequestDispatcher( VUE_PORTEFEUILLE ).forward( request, response );
						} else {
							request.setAttribute( ATT_ERREUR, "Vente impossible : il n'y pas assez d'obligations disponibles.");
							request.setAttribute( ATT_SESSION_OBJETS_FINANCIERS, objetsFinanciers );
							this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
						}
					}
				}
				i++;
			}
		} else {
			request.setAttribute( ATT_SESSION_OBJETS_FINANCIERS, objetsFinanciers );
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
	}
}
