package dao;

import modele.ObjetFinancier;
import modele.Portefeuille;

public interface PortefeuilleDao {

	void creer(String login, Portefeuille portefeuille) throws DAOException;
	
	Portefeuille charger(String login) throws DAOException;
	
	void mettreAJour(Portefeuille portefeuille, ObjetFinancier objetFinancier) throws DAOException;
	
	void supprimer(String login) throws DAOException;
	
}
