package dao;

import java.util.Vector;

import modele.Obligation;

public interface ObligationDao {

	Vector<String> trouverToutesObligations() throws DAOException;
	Obligation recupererObligation(String emetteur) throws DAOException;
}
