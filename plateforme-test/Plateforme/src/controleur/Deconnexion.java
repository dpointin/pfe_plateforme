package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
* Servlet deconnexion gerant la deconnexion d'un joueur
*
* @author  Celine Chaugny & Damien Pointin 
*/
@WebServlet("/Deconnexion")
public class Deconnexion extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	* VUE_REDIRECTION correspond a la jsp lie l'accueil
	*/ 
	private static final String VUE_REDIRECTION ="/accueil.jsp"; 
	
	/**
	* Implementation de la methode doGet
	* affiche la page d'accueil
	* 
	* @param request
	* la HttpRequeteServlet
	* @param response
	* la HttpServletResponse 
	* 
	* @throws ServletException en cas d'erreur
	* @throws IOException en cas d'erreur
	*/ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		this.getServletContext().getRequestDispatcher( VUE_REDIRECTION ).forward( request, response );
	}
}
