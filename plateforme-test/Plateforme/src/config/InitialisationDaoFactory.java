package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import dao.DAOFactory;

/**
* Classe InitialisationDaoFactory qui initialise notre daoFactory
* qui implémente ServletContextListener
* 
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class InitialisationDaoFactory implements ServletContextListener {
	/**
	* CONF_DAO_FACTORY correspond a l'attribut daoFactory.
	*/ 
	private static final String ATT_DAO_FACTORY = "daofactory";
	
	

	/**
	* La DAOFactory de notre application
	*/ 
	private DAOFactory daoFactory;
	
	
	/**
	* Implementation de la methode contextInitialized
	* On recupere le servlet context
	* on obtient une instance de daoFactory grace a sa methode statique
	* on fixe l'attribut dans le servlet context
	* 
	* @param event
	* la servletContextEvent prise en entree
	*/
	@Override
	public void contextInitialized( ServletContextEvent event ) {
		/* Récupération du ServletContext lors du chargement de l'application */
		ServletContext servletContext = event.getServletContext();
		/* Instanciation de notre DAOFactory */
		this.daoFactory = DAOFactory.getInstance();
		/* Enregistrement dans un attribut ayant pour portée toute l'application */
		servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
	}
	
	
	/**
	* Implementation de la methode contextDestroyed
	* Rien à effectuer
	*/
	@Override
	public void contextDestroyed( ServletContextEvent event ) {
		/* Rien à réaliser lors de la fermeture de l'application... */
	}
} 