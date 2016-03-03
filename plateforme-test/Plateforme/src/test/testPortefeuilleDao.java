package test;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import dao.DAOException;
import dao.DAOFactory;
import dao.PortefeuilleDaoImpl;
import modele.Obligation;
import modele.Option;
import modele.Portefeuille;
import modele.Titre;

public class testPortefeuilleDao {
	public static void main(String[] args) {
		DAOFactory fac= DAOFactory.getInstance();
		PortefeuilleDaoImpl p_dao = (PortefeuilleDaoImpl) fac.getPortefeuilleDao();
		System.out.println("Debut des tests");
		System.out.println("Test de la fonction charger");
		
		/*	Vector<Double> :
		 * 	0 : ouverture
		 *  1 : haut
		 *  2 : bas
		 *  3 : fermeture
		 *  4 : volume
		 *  5 : adjFermeture
		*/		
		Portefeuille p = null;

		try {
			p = p_dao.charger("cchaugny");
		} catch (DAOException e) {
			System.out.println(e);
		}
		
		System.out.println("Fin du chargement :");
		System.out.println("Argent investi : "+ p.getArgentInvesti());
		System.out.println("Argent disponible : "+ p.getArgentDisponible());
		System.out.println("Rendement du portefeuille : "+ p.getRendement());
		
		System.out.println("Liste de titres :");		
		Enumeration<Titre> titres = p.getPrixTitre().keys();
	    List<Titre> list = Collections.list(titres);
	    Double prix;
	    Integer quantite;
	    for (Titre titre_l : list) {
	    	prix = p.getPrixTitre().get(titre_l);
	    	quantite = p.getQuantiteTitre().get(titre_l);
			System.out.println(titre_l.getCode() + " - prix : " + prix + " / quantite : " + quantite);
		}
	    
	    System.out.println("Liste des options :");		
		Enumeration<Option> options = p.getPrixOption().keys();
	    List<Option> list_op = Collections.list(options);
	    for (Option option_l : list_op) {
	    	prix = p.getPrixOption().get(option_l);
	    	quantite = p.getQuantiteOption().get(option_l);
			System.out.println(option_l.getType() + " " + option_l.getPosition() + " - prix : " + prix + " / quantite : " + quantite);
		}
	    
		System.out.println("Liste des obligations :");		
		Enumeration<Obligation> obligations = p.getPrixObligation().keys();
	    List<Obligation> list_ob = Collections.list(obligations);
	    for (Obligation obligation_l : list_ob) {
	    	prix = p.getPrixObligation().get(obligation_l);
	    	quantite = p.getQuantiteObligation().get(obligation_l);
			System.out.println(obligation_l.getEmetteur() + " - prix : " + prix + " / quantite : " + quantite);
		}
	}
}
