package dao;

import java.util.Vector;

import dao.config.DAOException;
import modele.Operation;
import modele.Portefeuille;

/**
* Interface HistoriquePortefeuilleDao definissant les methodes dialoguant avec la table HistoriquePortefeuille
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface HistoriquePortefeuilleDao {
	/**
	* Methode qui permet de trouver la liste de toutes les operations effectuees sur un portefeuille
	*
	* @param idPortefeuille correspond au portefeuille dont on souhaite recuperer les operations l'operation
	* 
	* @return Vector<Operation> correspond a la liste des operations
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Operation
	* @see Portefeuille
	* @see DAOException
	* @see HistoriquePortefeuilleDaoImpl
	*/
	Vector<Operation> trouver(int idPortefeuille) throws DAOException;
	
	
	/**
	* Methode qui permet d"ajouter une operation dans la table
	*
	* @param idPortefeuille correspond au portefeuille qui a effectue l'operation
	* @param Operation correspond a l'operation que l'on souhaite ajouter
	* 
	* @throws DAOException Si une erreur arrive lors de l'ajout dans la bdd
	* 
	* @see Operation
	* @see Portefeuille
	* @see DAOException
	* @see HistoriquePortefeuilleDaoImpl
	*/
	void ajouter(int idPortefeuille, Operation operation) throws DAOException;
}
