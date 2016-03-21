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
import dao.TitreDao;
import modele.ObjetFinancier;
import modele.Obligation;
import modele.Titre;

/**
* Servlet bourse gerant l'affichage d'une "sous" bourse avec une barre de recherche
*
* @author  Celine Chaugny & Damien Pointin 
*/
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
	* ATT_SESSION_TITRES correspond a l'attribut titres
	*/ 
	public static final String ATT_SESSION_TITRES = "titres";

	/**
	* ATT_SESSION_OBLIGATIONS correspond a l'attribut obligations
	*/ 
	public static final String ATT_SESSION_OBLIGATIONS = "obligations";
	
	/**
	* ATT_SESSION_OBJETS_FINANCIERS correspond a l'attribut objetsFinanciers
	*/ 
	public static final String ATT_SESSION_OBJETS_FINANCIERS = "objetsFinanciers";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/bourse.jsp";
	
	/**
	* VUE_ACTIONS correspond a la jsp affichant toutes les actions de la "sous" bourse
	*/ 
	public static final String VUE_ACTIONS = "/WEB-INF/joueurConnecte/bourseActions.jsp";
	
	/**
	* VUE_INDICES correspond a la jsp affichant tous les indices de la "sous" bourse
	*/ 
	public static final String VUE_INDICES = "/WEB-INF/joueurConnecte/bourseIndices.jsp";

	/**
	* VUE_OBLIGATIONS correspond a la jsp affichant toutes les obligations de la "sous" bourse
	*/ 
	public static final String VUE_OBLIGATIONS = "/WEB-INF/joueurConnecte/bourseObligations.jsp";
	
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
		this.titreDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getTitreDao();
		this.obligationDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getObligationDao();
	}
	
	/**
	* Implementation de la methode doGet
	* affiche la page de bourse ou la page de redirection demandee si l'attribut redir existe
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
	* permet d'effectuer une recherche parmis les objets financiers de la bourse,
	* permet aussi d'acceder à l'historique d'un titre et a des graphes
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
		/* Recherche */
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

		request.setAttribute( ATT_SESSION_OBJETS_FINANCIERS, objetsFinanciers );
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

	
}
