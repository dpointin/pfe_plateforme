package dao;

import java.util.Vector;

import dao.config.DAOException;
import modele.Joueur;

/**
* Interface JoueurDao definissant les methodes d'un joueur dao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface JoueurDao {
	/**
	* Methode chargee de creer un joueur dans la bdd qui sera implementee dans 
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
	* Methode chargee de trouver un joueur dans la bdd qui sera implementee dans 
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
	* Methode chargee de supprimer un joueur
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
	
	/**
	* Methode chargee de trouver tous les joueurs
	*	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Joueur
	* @see DAOException
	* @see JoueurDaoImpl
	*/ 
	Vector<Joueur> trouverTousLesJoueurs() throws DAOException;
} 