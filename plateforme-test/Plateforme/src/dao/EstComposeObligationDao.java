package dao;

import modele.Obligation;
import modele.Portefeuille;

public interface EstComposeObligationDao {

	//met a jour la quantite de l'obligation passé en entree dans le portefeuille 
	//si elle n'est pas presente on la cree
	//sinon s'il n'y en a plus on la supprime
	//sinon juste un update
	void mettreAJour(Portefeuille portefeuille, Obligation obligation) throws DAOException;
	
	// sert à supprimer lorsque le joueur supprime tout son portefeuille
	void supprimer(Integer idPortefeuille) throws DAOException;
	
	
	//Renvoie toutes les obligations correspondant au joueur
	void trouver(Portefeuille portefeuille) throws DAOException;
	
}
