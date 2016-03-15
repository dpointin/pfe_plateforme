package test.testdao;

import java.util.GregorianCalendar;
import java.util.Iterator;

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
	
		try {
			h_dao.mettreAJour("AAL");
		} catch (DAOException e) {
			System.out.println(e);
		}
		
		
		GregorianCalendar d1 = new GregorianCalendar(2016,0,4);
		System.out.println("Test de la fonction trouver");
		Historique h_rep = h_dao.trouver("AAL", d1);
		System.out.println(h_rep==null);
		System.out.println("apres la creation : " +h_rep.getCode() + " - " + h_rep.getOuvertureJours(d1) +" - " + h_rep.getValeurs().size());
		
		h=h_dao.trouver("AAL", new GregorianCalendar(2016,0,4), new GregorianCalendar());
		Iterator<GregorianCalendar> it=h.getValeurs().keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
		while(it.hasNext())
		{
		   GregorianCalendar key=(GregorianCalendar) it.next();
		   System.out.println(h.getFermetureJours(key)+"-"+h.getBaseCent().get(key));
		}

	}
}