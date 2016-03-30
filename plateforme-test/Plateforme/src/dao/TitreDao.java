package dao;

import java.util.ArrayList;

import dao.config.DAOException;
import modele.Titre;

/**
* Interface TitreDao definissant les methodes d'un Titredao
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface TitreDao {
	/**
	* Methode chargee de recuperer tous les codes des titres
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
	* Methode chargee de recuperer tous les titres
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
	* Methode chargee de recuperer les titres contenant certains mots cles et d'un certain type
	*
	* @param motsCles que l'on souhaite retrouver dans le libelle ou le code
	* @param type des titres que l'on souhaite recuperer (TOUS, ACTION, INDICE)
	*
	* @return ArrayList<Titre> correspondant a l'ensemble des titres repondant aux criteres
	* 
	* @throws DAOException Si une erreur arrive lors de la lecture dans la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDaoImpl
	*/ 
	ArrayList<Titre> rechercheTitres(String motsCles, String type) throws DAOException;
	
	/**
	* Methode chargee de recuperer le type du titre en fonction de son code
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
	* Methode chargee de recuperer le type correspondant a un code
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
	
	
	/**
	* Methode chargee de mettreAJour le nombre disponigle d'un titre
	*
	* @param titre que l'on met a jour
	* 
	* @throws DAOException Si une erreur arrive lors de la mise a jour de la bdd
	* 
	* @see Titre
	* @see DAOException
	* @see TitreDaoImpl
	*/ 
	void mettreAJour(Titre titre) throws DAOException;
}
