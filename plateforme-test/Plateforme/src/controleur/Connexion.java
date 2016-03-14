package controleur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.JoueurDao;
import formulaire.ConnexionForm;
import modele.Joueur;
import dao.DAOFactory;

/**
* Servlet connexion gerant la connexion d'un joueur
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Connexion extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 *Correspond au message si on essaie d'accéder à une page ou la connexion est requise
	 *vaut true ou false 
	 */
	public static final String ALERTE_PB_CONNEXION="messageAlerte";
	
	
	/**
	* CONF_DAO_FACTORY correspond a l'attribut daoFactory.
	*/ 
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	
	/**
	* ATT_USER correspond a l'attribut joueur
	*/ 
	public static final String ATT_JOUEUR = "joueur";
	
	
	/**
	* ATT_FORM correspond a l'attribut form
	*/ 
	public static final String ATT_FORM = "form";
	
	
	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut session Joueur
	*/ 
	public static final String ATT_SESSION_JOUEUR = "sessionJoueur";
	
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/connexion.jsp";
	
	
	/** 
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_CONNECTE = "/WEB-INF/joueurConnecte/acceuilPortefeuille.jsp";
	
	
	/**
	* Le joueurDao de notre servlet
	*/ 
	private JoueurDao joueurDao;

	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.joueurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getJoueurDao();
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
		/* Préparation de l'objet formulaire */
		ConnexionForm form = new ConnexionForm(joueurDao);
		/* Traitement de la requête et récupération du bean en résultant */
		Joueur joueur = form.connecterJoueur( request );
		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();
		/**
		 * Si aucune erreur de validation n'a eu lieu, alors ajout du modele
		 * Joueur à la session, sinon suppression du modele de la session.
		 */
		if ( form.getErreurs().isEmpty() ) {
			session.setAttribute( ATT_SESSION_JOUEUR, joueur );
		} else {
			session.setAttribute( ATT_SESSION_JOUEUR, null );
		}
		/* Stockage du formulaire et du bean dans l'objet request */
		request.setAttribute( ATT_FORM, form );
		request.setAttribute( ATT_JOUEUR, joueur );
		if (session.getAttribute(ALERTE_PB_CONNEXION)!=null)
			session.setAttribute(ALERTE_PB_CONNEXION, null);
		if(session.getAttribute(ATT_SESSION_JOUEUR)==null)
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		else{
		/*	TitreDao titreDao=( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getTitreDao();
			ArrayList<String> t=titreDao.trouverTousCodes();
			int k = 0;
			for(String s : t){
				k++;
				HistoriqueDao hDao=( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getHistoriqueDao();
				hDao.mettreAJour(s);
				System.out.println(s);
				//if (k == 1) break;
				
			}*/
		this.getServletContext().getRequestDispatcher( VUE_CONNECTE ).forward( request, response );
		}
	}

}
