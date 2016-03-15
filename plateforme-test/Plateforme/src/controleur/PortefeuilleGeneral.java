package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.PortefeuilleDao;
import modele.Joueur;
import modele.ObjetFinancier;
import modele.Portefeuille;
import modele.Titre;

public class PortefeuilleGeneral extends HttpServlet {
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
	* ATT_SESSION_JOUEUR correspond a l'attribut session Joueur
	*/ 
	public static final String 	ATT_COURS_BASE_CENT = "baseCent";

	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/portefeuille.jsp";
	
	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE_INDICATEURS = "/WEB-INF/joueurConnecte/portefeuilleIndicateurs.jsp";
	
	/**
	* Le joueurDao de notre servlet
	*/ 
	private PortefeuilleDao portefeuilleDao;
	
	/**
	* Implementation de la methode init 
	* 
	* @throws ServletException si jamais la recuperation de l'instance se passe mal
	*/ 
	public void init() throws ServletException {
		/* Récupération d'une instance de nos DAO Obligation et Titre */
		this.portefeuilleDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPortefeuilleDao();
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
		Joueur joueur = (Joueur) session.getAttribute( ATT_SESSION_JOUEUR );
		
		Portefeuille portefeuille = portefeuilleDao.charger(joueur.getLogin());
		if (portefeuille != null) {
			session.setAttribute( ATT_SESSION_PORTEFEUILLE, portefeuille );
		} else {
			session.setAttribute( ATT_SESSION_PORTEFEUILLE, new Portefeuille() );
		}
		
		if (request.getParameter("redir") != null) {
			Enumeration<ObjetFinancier> enum_ObjetsFinanciers = portefeuille.getPrixObjetFinancier().keys();
			TreeMap<GregorianCalendar, Vector<Double>> baseCent = new TreeMap<GregorianCalendar, Vector<Double>>();
			ArrayList<ObjetFinancier> objetsFinanciers = Collections.list(enum_ObjetsFinanciers);
			
			ArrayList<Titre> titres = new ArrayList<Titre>();
			Vector<String> codes = new Vector<String>();
			for (ObjetFinancier o : objetsFinanciers) {
				if (o instanceof Titre) {
					titres.add((Titre) o);
					codes.add(((Titre)o).getCode());
				}
			}
			
			if (titres.size() > 0) {
				TreeMap<GregorianCalendar, Double> temp = titres.get(0).getHistorique().getBaseCent();
				Set<GregorianCalendar> dates = temp.keySet();
				for (GregorianCalendar d : dates) {
					Vector<Double> v = new Vector<Double>();
					v.add(temp.get(d));
					baseCent.put(d,v);
				}
				for (int i=1; i<titres.size(); i++) {
					Double prec = 0.0;
					TreeMap<GregorianCalendar, Double> temp2 = titres.get(i).getHistorique().getBaseCent();
					for (GregorianCalendar d : dates) {
						if (temp2.containsKey(d)) {
							prec = temp2.get(d);
							Vector<Double> v = baseCent.get(d);
							v.add(temp2.get(d));
							baseCent.put(d, v);
						} else {
							Vector<Double> v = baseCent.get(d);
							v.add(prec);
							baseCent.put(d, v);
						}
					}
				}
			}

			session.setAttribute("titres", codes);
			session.setAttribute(ATT_COURS_BASE_CENT, baseCent);
			this.getServletContext().getRequestDispatcher( VUE_INDICATEURS ).forward( request, response );			
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
		this.getServletContext().getRequestDispatcher( VUE_INDICATEURS ).forward( request, response );
	}

	
}
