package dao;


import modele.Portefeuille;
import modele.Titre;

/**
* Interface estComposeObligationDao definissant les methodes de la table estComposeTitreDao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface EstComposeTitreDao {
	/**
	* Methode chargee de creer mettre a jour la quantite 
	* du titre passe en entree dans le portefeuille 
	* si elle n'est pas presente on la cree
	* sinon s'il n'y en a plus on la supprime
	* sinon juste un update 
	*
	* @param portefeuille que l'on modifie
	* @param titre dont on modifie la quantite et le prix
	* 
	* @throws DAOException Si une erreur arrive lors de la modification de la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	* @see Titre
	*/ 		
	void mettreAJour(Portefeuille portefeuille, Titre titre) throws DAOException;
		
	
	/**
	* Sert a supprimer toutes les lignes d'un même portefeuille
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
	* Permet de rajouter tous les titres appartenant au joueur dans son portefeuille
	*
	* @param portefeuille où on veut ajouter les titres
	* 
	* @throws DAOException Si une erreur arrive lors de la recherche dans la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	*/ 
	void trouver(Portefeuille portefeuille) throws DAOException;
}
