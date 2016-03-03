package dao;

import java.util.GregorianCalendar;

import modele.Portefeuille;
import modele.TypeOption;
import modele.TypePosition;

public interface PortefeuilleDao {

	void mettreAJour(String login, Portefeuille portefeuille) throws DAOException;
	
	Portefeuille charger(String login) throws DAOException;
	
	void acquerirOption(TypeOption typeOption, TypePosition typePosition, GregorianCalendar maturite, Double strike, Double prime, Integer idPortefeuille, String code) throws DAOException;

	void exercerOption(Integer idOption) throws DAOException;
	
}
