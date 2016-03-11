package test.testdao;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.Vector;

import dao.DAOException;
import dao.DAOFactory;
import dao.PortefeuilleDaoImpl;
import modele.Action;
import modele.Historique;
import modele.Obligation;
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
			p = p_dao.charger("test");
		} catch (DAOException e) {
			System.out.println(e);
		}
		
		System.out.println("Fin du chargement :");
		System.out.println("Argent disponible : "+ p.getArgentDisponible());
		System.out.println("Rendement du portefeuille : "+ p.getRendement());
		System.out.println(p.getQuantiteObjetFinancier());
		
		Portefeuille p2=new Portefeuille();
		p2.setArgentDisponible(1000.0);
		Titre t=new Action("AAL", "aal",100, 2.1);
		Historique h=new Historique();
		TreeMap<GregorianCalendar, Vector<Double>> hash=new TreeMap<GregorianCalendar, Vector<Double>>();
		Vector<Double> v=new Vector<Double>();
		v.add(100.0);
		v.add(100.0);
		v.add(100.0);
		v.add(100.0);
		Date d=new Date();
		hash.put(new GregorianCalendar(d.getYear(),d.getMonth(),d.getDay()), v);
		h.setValeurs(hash);
		t.setHistorique(h);
		p2.acheter(t,2);
		Obligation o=new Obligation("orange", 100.0, 0.1, 100, new GregorianCalendar());
		p2.acheter(o, 6);
		p_dao.creer("test2", p2);
		p2.vendre(o, 2);
		p_dao.mettreAJour(p2, o);
		p_dao.supprimer("test2");
		/*
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
		}*/
	}
}
