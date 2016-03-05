package test;

import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import dao.DAOException;
import dao.DAOFactory;
import dao.HistoriqueDaoImpl;
import modele.Historique;

public class testHistoriqueDao {
	public static void main(String[] args) {
		DAOFactory fac= DAOFactory.getInstance();
		HistoriqueDaoImpl h_dao = (HistoriqueDaoImpl) fac.getHistoriqueDao();
		System.out.println("Debut des tests");
		System.out.println("Test de la fonction mettreAJour");
		
		/*	Vector<Double> :
		 * 	0 : ouverture
		 *  1 : haut
		 *  2 : bas
		 *  3 : fermeture
		 *  4 : volume
		 *  5 : adjFermeture
		*/		
		Historique h = new Historique("^FCHI");
/*		Hashtable<GregorianCalendar, Vector<Double>> t = new Hashtable<GregorianCalendar, Vector<Double>>();
		
		GregorianCalendar d1 = new GregorianCalendar(2015,0,1);
		Vector<Double> v1 = new Vector<Double>();
		v1.add((double) 4000);
		v1.add((double) 4050);
		v1.add((double) 3950);
		v1.add((double) 4001);
		v1.add((double) 10000);
		v1.add((double) 4003);
		
		t.put(d1, v1);
		h.setValeurs(t);
*/		
		try {
			h_dao.mettreAJour("AAL");
		} catch (DAOException e) {
			System.out.println(e);
		}
		
		
		GregorianCalendar d1 = new GregorianCalendar(2010,0,1);
		System.out.println("Test de la fonction trouver");
		Historique h_rep = h_dao.trouver("AAL", d1);
		System.out.println(h_rep==null);
		System.out.println("apres la creation : " +h_rep.getCode() + " - " + h_rep.getOuvertureJours(d1) +" - " + h_rep.getValeurs().size());
		
	/*	System.out.println("Test de la fonction trouver les cours entre deux dates");
		Historique h_rep_plusieurs_dates = h_dao.trouver("^FCHI", new GregorianCalendar(2015,0,1),new GregorianCalendar(2015,0,5));
		
		Enumeration<GregorianCalendar> dates = h_rep_plusieurs_dates.getValeurs().keys();
		
	    List<GregorianCalendar> list = Collections.list(dates);
	    Collections.sort(list);
	    Vector<Double> valeurs = null;
	    
	    for (GregorianCalendar date_l : list) {
	    	valeurs = h_rep_plusieurs_dates.getValeurs().get(date_l);
			System.out.println(date_l.getTime());
			for (int i = 0; i<valeurs.size(); i++) {
				System.out.println(valeurs.get(i));
			}
		}
	*/	
	}
}