package test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import modele.Action;
import modele.Historique;
import modele.Portefeuille;
import modele.Titre;

public class testPortefeuille {
	
	public static void main(String[] args) {

		Titre t=new Action("AAL", "aal",10000, 2.1);
		Historique h=new Historique();
		Hashtable<GregorianCalendar, Vector<Double>> hash=new Hashtable<GregorianCalendar, Vector<Double>>();
		Vector<Double> v=new Vector<Double>();
		v.add(1000.0);
		v.add(1000.0);
		v.add(1000.0);
		v.add(1000.0);
		Date d=new Date();
		hash.put(new GregorianCalendar(d.getYear(),d.getMonth(),d.getDay()), v);
		h.setValeurs(hash);
		t.setHistorique(h);
		Portefeuille p=new Portefeuille();
		p.acheter(t, 100);
	}
}
