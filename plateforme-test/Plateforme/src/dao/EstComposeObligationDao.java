package dao;

import modele.Obligation;
import modele.Portefeuille;

/**
* Interface estComposeObligationDao definissant les methodes de la table estComposeObligationDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface EstComposeObligationDao {

	
	/**
	* Methode chargee de creer mettre a jour la quantite 
	* de l'obligation passe en entree dans le portefeuille 
	* si elle n'est pas presente on la cree
	* sinon s'il n'y en a plus on la supprime
	* sinon juste un update 
	*
	* @param portefeuille que l'on modifie
	* @param obligation dont on modifie la quantite
	* 
	* @throws DAOException Si une erreur arrive lors de la modification de la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	* @see Obligation
	*/ 
	void mettreAJour(Portefeuille portefeuille, Obligation obligation) throws DAOException;
	
	
	/**
	* Sert a supprimer toutes les lignes d'un meme portefeuille
	*
	* @param idPortefeuille que l'on supprime
	* 
	* @throws DAOException Si une erreur arrive lors de la suppression dans la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	*/ 
	void supprimer(Integer idPortefeuille) throws DAOException;
	
	
	/**
	* Permet de rajouter toutes les obligations appartenant au joueur dans son portefeuille
	*
	* @param portefeuille ou on veut ajouter les obligations
	* 
	* @throws DAOException Si une erreur arrive lors de la recheche dans la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	*/ 
	void trouver(Portefeuille portefeuille) throws DAOException;
	
}
