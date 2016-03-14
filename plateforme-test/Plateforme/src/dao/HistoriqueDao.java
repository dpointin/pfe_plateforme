package dao;

import java.util.GregorianCalendar;

import modele.Historique;
import modele.Titre;
/**
* Interface JoueurDao definissant les methodes dialoguant avec la table Historique
*
* @author  Celine Chaugny & Damien Pointin 
*/
public interface HistoriqueDao {
	/**
	* Methode chargee de mettre a jour la table Historique pour le code donne
	* c'est a dire telecharger les cours et les inserer
	*
	* @param code du titre dont on veut mettre a jour l'historique
	* 
	* @throws DAOException Si une erreur arrive lors de l'ajout dans la bdd
	* 
	* @see Historique
	* @see Titre
	* @see DAOException
	* @see HistoriqueDaoImpl
	*/
	void mettreAJour(String code) throws DAOException;
	
	
	/**
	* Methode qui permet de trouver la valeur d'un titre a une date donnee
	*
	* @param code du titre dont on veut la valeur
	* @param date a laquelle on veut la valeur
	* 
	* @return historique correspondant a la valeur a la date voulue
	* 
	* @throws DAOException Si une erreur arrive lors de la recherche dans la bdd
	* 
	* @see Historique
	* @see Titre
	* @see GregorianCalendar
	* @see DAOException
	* @see HistoriqueDaoImpl
	*/
	Historique trouver( String code, GregorianCalendar date) throws DAOException;
	
	
	/**
	* Methode qui permet de trouver la valeur d'un titre dans un intervalle de date donne
	*
	* @param code du titre dont on veut la valeur
	* @param date_debut date a partir de laquelle on veut la valeur
	* @param date_fin date jusqu'a laquelle on veut la valeur
	* 
	* @return historique correspondant a la valeur du titre pour l'intervalle donne en entree
	* 
	* @throws DAOException Si une erreur arrive lors de la recherche dans la bdd
	* 
	* @see Historique
	* @see Titre
	* @see GregorianCalendar
	* @see DAOException
	* @see HistoriqueDaoImpl
	*/
	Historique trouver( String code, GregorianCalendar date_debut, GregorianCalendar date_fin) throws DAOException;
}
