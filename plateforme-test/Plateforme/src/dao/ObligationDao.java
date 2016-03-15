package dao;

import java.util.ArrayList;

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
	* @return ArrayList<String> correspondant a la liste des emetteurs d'obligation
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture de la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDaoImpl
	*/ 
	ArrayList<String> trouverTousEmetteurs() throws DAOException;
	
	/**
	* Methode chargee de recuperer toutes les obligations
	*
	* @return ArrayList<Obligation> correspondant a l'ensemble des obligations
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture de la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDaoImpl
	*/ 
	ArrayList<Obligation> trouverToutesObligations() throws DAOException;

	/**
	* Methode chargee de recuperer toutes les obligations contenant certains mots cles
	* 
	* @param motsCles que l'on souhaite retrouver dans l'emetteur de l'obligation
	*
	* @return ArrayList<Obligation> correspondant a l'ensemble des obligations repondant aux criteres
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture de la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDaoImpl
	*/ 
	ArrayList<Obligation> rechercheObligations(String motsCles) throws DAOException;
	
	
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
	
	
	/**
	* Methode chargee de mettreAJour le nombre disponible d'une obligation
	*
	* @param obligation que l'on met a jour
	* 
	* @throws DAOException Si une erreur arrive lors de la mise a jour de la bdd
	* 
	* @see Obligation
	* @see DAOException
	* @see ObligationDaoImpl
	*/ 
	void mettreAJour(Obligation obligation) throws DAOException;
}
