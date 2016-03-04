package dao;

import modele.Obligation;
import modele.Portefeuille;

public interface EstComposeObligationDao {
/*
 * supprimer creer et supprimer : les mettre en privé
 * mettreAJour prendra en entrée le Portefeuille, l'emmeteur et fera appel aux autres
 */
	void creer(Integer idPortefeuille, Obligation obligation) throws DAOException;
	
	void mettreAJour(Portefeuille portefeuille) throws DAOException;
	
	// sert à supprimer lorsque le joueur supprime tout son portefeuille
	void supprimer(Integer idPortefeuille) throws DAOException;
	
}
