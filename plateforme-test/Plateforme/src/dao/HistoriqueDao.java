package dao;

import java.util.GregorianCalendar;

import modele.Historique;

public interface HistoriqueDao {
	
	void mettreAJour(String code) throws DAOException;
	
	Historique trouver( String code, GregorianCalendar date) throws DAOException;
	
	Historique trouver( String code, GregorianCalendar date_debut, GregorianCalendar date_fin) throws DAOException;
}
