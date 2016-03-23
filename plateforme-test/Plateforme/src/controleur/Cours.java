package controleur;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.HistoriqueDao;
import modele.Historique;

/**
* Servlet cours gerant l'historique d'un titre
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Cours extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	* CONF_DAO_FACTORY correspond a l'attribut daoFactory.
	*/ 
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	/**
	* ATT_SESSION_CODE correspond a l'attribut code
	*/ 
	public static final String ATT_SESSION_CODE = "code";
	
	/**
	* ATT_SESSION_COURS correspond a l'attribut cours
	*/ 
	public static final String ATT_SESSION_COURS = "cours";
	
	/**
	* ATT_SESSION_MM correspond a l'attribut mm
	*/ 
	public static final String ATT_SESSION_MM = "mm";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/historique.jsp";
	
	/**
	* VUE_CHANDELIER correspond a la jsp d'affichage du graphe en chandeliers
	*/ 
	public static final String VUE_CHANDELIER = "/WEB-INF/joueurConnecte/chandelier.jsp";
	
	/**
	* VUE_CHART correspond a la jsp d'affichage du graphe en courbe
	*/ 
	public static final String VUE_CHART = "/WEB-INF/joueurConnecte/chart.jsp";
	
	/**
	* VUE_VOLUMES correspond a la jsp d'affichage des volumes sous forme de barres
	*/ 
	public static final String VUE_VOLUMES = "/WEB-INF/joueurConnecte/volumes.jsp";
	
	/**
	* VUE_MOYENNEMOBILE correspond a la jsp d'affichage du graphe du cours et de sa moyenne mobile
	*/ 
	public static final String VUE_MOYENNEMOBILE = "/WEB-INF/joueurConnecte/moyennesMobiles.jsp";
	
	/**
	* Le historiqueDao de notre servlet
	*/ 
	private HistoriqueDao historiqueDao;

	/**
	* Le code du titre de notre servlet
	*/ 
	private String code;
	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
	public void init() throws ServletException {
		this.historiqueDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getHistoriqueDao();
	}
	
	/**
	* Implementation de la methode doGet
	* affichage de la page d'historique ou de la page graphique
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
		this.code = request.getParameter("code");

		String[] tabDebut = {"1","1","2015"};
		String[] tabFin = {"1","12","2016"};
		
		GregorianCalendar deb = new GregorianCalendar(Integer.parseInt(tabDebut[2]),Integer.parseInt(tabDebut[1])-1,Integer.parseInt(tabDebut[0]));
		GregorianCalendar fin = new GregorianCalendar(Integer.parseInt(tabFin[2]),Integer.parseInt(tabFin[1])-1,Integer.parseInt(tabFin[0]));
		Historique cours = historiqueDao.trouver(code,deb,fin);
		session.setAttribute( ATT_SESSION_CODE, code );
		session.setAttribute( ATT_SESSION_COURS, cours );
					
		this.getServletContext().getRequestDispatcher( VUE).forward( request, response );
	}
	
	
	/**
	* Implementation de la methode doPost
	* selectionne les donnees sur la periode donnee et redirige vers la page adequate (tableau de valeurs, graphique..)
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
		this.code = (String) session.getAttribute("code");

		String dateDebut = request.getParameter("dateDebut");
		String dateFin = request.getParameter("dateFin");
		String[] tabDebut = {"1","1","2015"};
		String[] tabFin = {"1","12","2016"};
		if (dateDebut != null && dateFin!=null && !dateDebut.equals("") && !dateFin.equals("")) {
			tabDebut = dateDebut.split("-");
			tabFin = dateFin.split("-");
		}
		
		GregorianCalendar deb = new GregorianCalendar(Integer.parseInt(tabDebut[2]),Integer.parseInt(tabDebut[1])-1,Integer.parseInt(tabDebut[0]));
		GregorianCalendar fin = new GregorianCalendar(Integer.parseInt(tabFin[2]),Integer.parseInt(tabFin[1])-1,Integer.parseInt(tabFin[0]));
		Historique cours = historiqueDao.trouver(code,deb,fin);
		session.setAttribute( ATT_SESSION_CODE, code );
		session.setAttribute( ATT_SESSION_COURS, cours );

		String typeGraphe = request.getParameter("typeGraphe");					
		if (typeGraphe!=null && typeGraphe.equals("CHANDELIER")) {
			this.getServletContext().getRequestDispatcher( VUE_CHANDELIER).forward( request, response );
		} else if (typeGraphe!=null && typeGraphe.equals("CHART")) {
			this.getServletContext().getRequestDispatcher( VUE_CHART).forward( request, response );
		} else if (typeGraphe!=null && typeGraphe.equals("VOLUMES")) {
			this.getServletContext().getRequestDispatcher( VUE_VOLUMES).forward( request, response );
		} else if (typeGraphe!=null && typeGraphe.equals("MOYENNEMOBILE")) {
			session.setAttribute(ATT_SESSION_MM, cours.calculMoyenneMobileSimple(5));
			this.getServletContext().getRequestDispatcher( VUE_MOYENNEMOBILE).forward( request, response );
		} else {
			this.getServletContext().getRequestDispatcher( VUE).forward( request, response );
		}
	}

	
}
