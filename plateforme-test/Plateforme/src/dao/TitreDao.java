package dao;

import java.util.ArrayList;

import modele.Titre;

/**
* Interface TitreDao definissant les methodes d'un Titredao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface TitreDao {
	/**
	* Méthode chargée de récupérer tous les codes des titres
	*
	* @return ArrayList<String> correspondant a l'ensemble des codes
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDaoImpl
	*/ 
	ArrayList<String> trouverTousCodes() throws DAOException;
	
	
	/**
	* Méthode chargée de récupérer tous les titres
	*
	* @return ArrayList<Titre> correspondant a l'ensemble des titres
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDaoImpl
	*/ 
	ArrayList<Titre> trouverTousTitres() throws DAOException;
	
	
	/**
	* Méthode chargée de récupérer le type du titre en fonction de son code
	*
	* @param code dont on veut savoir le type
	*
	* @return String correspondant au type
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDaoImpl
	*/ 
	String recupererTypeTitre(String code) throws DAOException;
	
	
	/**
	* Méthode chargée de récupérer le type correspondant a un code
	*
	* @param code dont on veut recuperer le titre
	*
	* @return Titre correspondant au code
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDaoImpl
	*/ 
	Titre recupererTitre(String code) throws DAOException;
}
