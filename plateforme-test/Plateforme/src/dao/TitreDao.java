package dao;

import java.util.ArrayList;

import modele.Titre;


public interface TitreDao {

	//Récupére tous les codes des titres --> utile pour la MAJ
	ArrayList<String> trouverTousCodes() throws DAOException;
	
	//Recupere tous les titres
	ArrayList<Titre> trouverTousTitres() throws DAOException;
	
	//Recupere le type du titre en fonction de son code
	String recupererTypeTitre(String code) throws DAOException;
	
	//Recupere le titre en fonction de son code
	Titre recupererTitre(String code) throws DAOException;
}
