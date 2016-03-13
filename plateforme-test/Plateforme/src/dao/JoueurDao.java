package dao;

import modele.Joueur;

/**
* Interface JoueurDao definissant les methodes d'un joueur dao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface JoueurDao {
	/**
	* Méthode chargée de creer un joueur dans la bdd qui sera implementer dans 
	* JoueurDaoImpl	
	*
	* @param joueur que l'on veut ajouter
	* 
	* @throws DAOException Si une erreur arrive lors de l'ajout a la bdd
	* 
	* @see Joueur
	* @see DAOException
	* @see JoueurDaoImpl
	*/ 
	void creer( Joueur joueur ) throws DAOException;
	
	
	/**
	* Méthode chargée de trouver un joueur dans la bdd qui sera implementer dans 
	* JoueurDaoImpl	
	*
	* @return login que l'on veut trouver
	* 
	* @throws DAOException Si une erreur arrive lors la recherche dans la bdd
	* 
	* @see Joueur
	* @see DAOException
	* @see JoueurDaoImpl
	*/ 
	Joueur trouver( String login ) throws DAOException;
	
	
	/**
	* Méthode chargée de supprimer un joueur
	*
	* @param login du joueur que l'on veut supprimer
	* 
	* @throws DAOException Si une erreur arrive lors de la suppression dans la bdd
	* 
	* @see Joueur
	* @see DAOException
	* @see JoueurDaoImpl
	*/ 
	void supprimer( String login ) throws DAOException;
} 