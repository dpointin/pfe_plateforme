package dao;

import java.util.ArrayList;

import modele.Titre;

public interface TitreDao {

	ArrayList<String> trouverTousCodes() throws DAOException;
	ArrayList<Titre> trouverTousTitres() throws DAOException;
	String recupererTypeTitre(String code) throws DAOException;
	Titre recupererTitre(String code) throws DAOException;
}
