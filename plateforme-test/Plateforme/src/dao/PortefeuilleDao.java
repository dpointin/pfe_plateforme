package dao;

import modele.Portefeuille;

public interface PortefeuilleDao {

	void creer(String login, Portefeuille portefeuille) throws DAOException;
	
	Portefeuille charger(String login) throws DAOException;
	
	void mettreAJour(String login, Portefeuille portefeuille) throws DAOException;
	
	void supprimer(String login) throws DAOException;
	
}
