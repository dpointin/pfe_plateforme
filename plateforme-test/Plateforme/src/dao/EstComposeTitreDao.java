package dao;

import java.util.Vector;

import modele.Portefeuille;
import modele.Titre;

public interface EstComposeTitreDao {

	//met a jour la quantite de l'obligation passé en entree dans le portefeuille 
		//si elle n'est pas presente on la cree
		//sinon s'il n'y en a plus on la supprime
		//sinon juste un update
		void mettreAJour(Portefeuille portefeuille, Titre titre) throws DAOException;
		
		// sert à supprimer lorsque le joueur supprime tout son portefeuille
		void supprimer(Integer idPortefeuille) throws DAOException;
		
		
		//Renvoie toutes les obligations correspondant au joueur
		void trouver(Portefeuille portefeuille) throws DAOException;
}
