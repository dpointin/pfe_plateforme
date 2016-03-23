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

/**
* Servlet portefeuille General gerant la constitution d'un portefeuille
* et la visualisation d'indicateurs
*
* @author  Celine Chaugny & Damien Pointin 
*/
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
	* ATT_SESSION_PORTEFEUILLE correspond a l'attribut session Portefeuille
	*/ 
	public static final String ATT_SESSION_PORTEFEUILLE = "portefeuille";

	/**
	* ATT_SESSION_JOUEUR correspond a l'attribut session Joueur
	*/ 
	public static final String ATT_SESSION_JOUEUR = "sessionJoueur";
	
	/**
	* ATT_SESSION_TITRES correspond a l'attribut titres
	*/ 
	public static final String ATT_SESSION_TITRES = "titres";
	
	/**
	* ATT_COURS_BASE_CENT correspond a l'attribut baseCent
	*/ 
	public static final String ATT_COURS_BASE_CENT = "baseCent";

	/**
	* VUE correspond a la jsp lie a la servlet
	*/ 
	public static final String VUE = "/WEB-INF/joueurConnecte/portefeuille.jsp";
	
	/**
	* VUE_INDICATEURS correspond a la jsp lie a l'affichage des indicateurs
	*/ 
	public static final String VUE_INDICATEURS = "/WEB-INF/joueurConnecte/portefeuilleIndicateurs.jsp";
	
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
		this.portefeuilleDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPortefeuilleDao();
	}
	
	/**
	* Implementation de la methode doGet
	* affiche la page du portefeuille ou ses indicateurs
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
				GregorianCalendar dateReference=null;
				for (GregorianCalendar d : dates) {
					boolean b=true;
					for (int i=1; i<titres.size(); i++) {
						TreeMap<GregorianCalendar, Double> temp2 = titres.get(i).getHistorique().getBaseCent();
						if (temp2.containsKey(d)==false){
							b=false;
							break;
						}	
					}
					if (b){
						if(dateReference==null)
							dateReference=d;
						Vector<Double> v= new Vector<Double>();
						for(int i=0; i<titres.size();i++){
							TreeMap<GregorianCalendar, Double> temp2 = titres.get(i).getHistorique().getBaseCentReference(dateReference);
							v.add(temp2.get(d));
						}
						baseCent.put(d, v);
					}
				}
			}

			session.setAttribute(ATT_SESSION_TITRES, codes);
			session.setAttribute(ATT_COURS_BASE_CENT, baseCent);
			this.getServletContext().getRequestDispatcher( VUE_INDICATEURS ).forward( request, response );			
		} else {
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
	}
	
	
	/**
	* Implementation de la methode doPost
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
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

	
}
