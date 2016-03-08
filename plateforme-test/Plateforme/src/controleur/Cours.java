package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.HistoriqueDao;
import modele.Historique;

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
	public static final String ATT_REQUEST_CODE = "code";
	
	/**
	* ATT_SESSION_COURS correspond a l'attribut cours
	*/ 
	public static final String ATT_REQUEST_COURS = "cours";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/historique.jsp";
	
	/**
	* VUE_CHANDELIER correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_CHANDELIER = "/WEB-INF/joueurConnecte/chandelier.jsp";
	
	/**
	* VUE_CHART correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_CHART = "/WEB-INF/joueurConnecte/chart.jsp";
	
	/**
	* Le historiqueDao de notre servlet
	*/ 
	private HistoriqueDao historiqueDao;

	/**
	* Le code de notre servlet
	*/ 
	private String code;
	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
	public void init() throws ServletException {
		/* Récupération d'une instance de nos DAO Obligation et Titre */
		this.historiqueDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getHistoriqueDao();
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
		this.code = request.getParameter("code");

		Historique cours = historiqueDao.trouver(code,new GregorianCalendar(2015,0,1),new GregorianCalendar());
		
		request.setAttribute( ATT_REQUEST_CODE, code );
		request.setAttribute( ATT_REQUEST_COURS, cours );
		
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
		this.code = request.getParameter("code");
		
		if (request.getParameter("ChargerCours")!=null) {
			String dateDebut = request.getParameter("dateDebut");
			String dateFin = request.getParameter("dateFin");
			System.out.println(dateDebut + "   " + dateFin);
			// voir comment on récupère la date depuis la jsp !!
			String typeGraphe = request.getParameter("typeGraphe");
			
			Historique cours = historiqueDao.trouver(code,new GregorianCalendar(2015,0,1), new GregorianCalendar());
			
			request.setAttribute( ATT_REQUEST_CODE, code );
			request.setAttribute( ATT_REQUEST_COURS, cours );
			
			if (typeGraphe.equals("CHANDELIER")) {
				this.getServletContext().getRequestDispatcher( VUE_CHANDELIER).forward( request, response );
			} else if (typeGraphe.equals("CHART")) {
				this.getServletContext().getRequestDispatcher( VUE_CHART).forward( request, response );
			} else {
				this.getServletContext().getRequestDispatcher( VUE).forward( request, response );
			}
		} else {
			doGet(request,response);
		}
	}

	
}
