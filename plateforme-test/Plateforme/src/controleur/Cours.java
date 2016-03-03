package controleur;

import java.io.IOException;
import java.util.ArrayList;
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
	public static final String ATT_SESSION_CODE = "code";
	
	/**
	* ATT_SESSION_COURS correspond a l'attribut cours
	*/ 
	public static final String ATT_SESSION_COURS = "cours";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/historique.jsp";
	
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
		/* Affichage de la page de connexion */
		HttpSession session = request.getSession();
		this.code = request.getParameter("code");
		//System.out.println("GET : " + code);
		Historique cours = historiqueDao.trouver(code,new GregorianCalendar(2000,0,1),new GregorianCalendar());
		//System.out.println("Taille map : " +cours.getCode());
		session.setAttribute( ATT_SESSION_CODE, code );
		session.setAttribute( ATT_SESSION_COURS, cours );
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
		this.code = request.getParameter("code");
		System.out.println("POST : " + code);
		
		Historique cours = historiqueDao.trouver(code,new GregorianCalendar(1980,0,1));

		session.setAttribute( ATT_SESSION_CODE, code );
		session.setAttribute( ATT_SESSION_COURS, cours.getValeurs() );
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

	
}
