package dao;

import modele.ObjetFinancier;
import modele.Portefeuille;

/**
* Interface PortefeuilleDao definissant les methodes d'un portefeuille dao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface PortefeuilleDao {
	/**
	* Methode chargee de creer un portefeuille dans la bdd qui sera implementee dans 
	* PortefeuilleDaoImpl	
	*
	* @param login du joueur dont on veut ajouter le portefeuille
	* @param portefeuille que l'on veut ajouter
	* 
	* @throws DAOException Si une erreur arrive lors de l'ajout a la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	* @see PortefeuilleDaoImpl
	*/ 
	void creer(String login, Portefeuille portefeuille) throws DAOException;
	
	
	/**
	* Methode chargee de lire le portefeuille d'un joueur dans la bdd 
	* qui sera implementee dans JoueurDaoImpl	
	*
	* @param login du joueur dont on veut lire le portefeuille
	* 
	* @return Portefeuille du joueur
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	* @see PortefeuilleDaoImpl
	*/ 
	Portefeuille charger(String login) throws DAOException;
	
	
	/**
	* Methode chargee de mettre a jour la quantite et le prix d'un objet financier dans la bdd
	* qui sera implementee dans JoueurDaoImpl	
	*
	* @param portefeuille du joueur que l'on met a jour
	* @param objetFinancier dont on met a jour la quantite et le prix
	* 
	* @throws DAOException Si une erreur arrive lors de la mise a jour de la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	* @see PortefeuilleDaoImpl
	*/ 
	void mettreAJour(Portefeuille portefeuille, ObjetFinancier objetFinancier) throws DAOException;
	
	
	/**
	* Methode chargee de supprimer le portefeuille d'un joueur dans la bdd 
	* qui sera implementee dans JoueurDaoImpl	
	*
	* @param login du joueur dont on veut supprimer le portefeuille
	* 
	* @throws DAOException Si une erreur arrive lors de la suppression dans la bdd
	* 
	* @see Portefeuille
	* @see DAOException
	* @see PortefeuilleDaoImpl
	*/ 
	void supprimer(String login) throws DAOException;
	
}
