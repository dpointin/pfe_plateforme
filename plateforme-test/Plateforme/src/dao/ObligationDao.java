package dao;

import java.util.Vector;

import modele.Obligation;

/**
* Interface JoueurDao definissant les methodes que l'on peut realiser dans la table Obligation
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface ObligationDao {
	/**
	* Methode chargee de recuperer les emetteurs de toutes les obligations
	*
	* @return Vector<String> correspondant a la liste des emetteurs d'obligation
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture de la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDaoImpl
	*/ 
	Vector<String> trouverToutesObligations() throws DAOException;
	
	
	/**
	* Methode chargee de recuperer l'obligation qui correspond a un emetteur
	* 
	* @param emetteur de l'obligation
	*
	* @return Obligation correspondant a l'emetteur
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture de la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDaoImpl
	*/
	Obligation recupererObligation(String emetteur) throws DAOException;
}
