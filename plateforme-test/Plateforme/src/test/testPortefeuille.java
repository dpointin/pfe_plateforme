package test;

import java.util.Date;
import java.util.GregorianCalendar;
<<<<<<< HEAD
import java.util.Hashtable;
=======
>>>>>>> d10256ee013229b04b5bf7bdad2d52c3ffc8d921
import java.util.TreeMap;
import java.util.Vector;

import modele.Action;
import modele.Historique;
import modele.Obligation;
import modele.Portefeuille;
import modele.Titre;

public class testPortefeuille {
	
	public static void main(String[] args) {

		Titre t=new Action("AAL", "aal",100, 2.1);
		Historique h=new Historique();
		TreeMap<GregorianCalendar, Vector<Double>> hash=new TreeMap<GregorianCalendar, Vector<Double>>();
		Vector<Double> v=new Vector<Double>();
		v.add(100000.0);
		v.add(100000.0);
		v.add(100000.0);
		v.add(100000.0);
		Date d=new Date();
		hash.put(new GregorianCalendar(d.getYear(),d.getMonth(),d.getDay()), v);
		h.setValeurs(hash);
		t.setHistorique(h);
		Portefeuille p=new Portefeuille();
		System.out.println("Nombre avant tout achat");
		System.out.println(p.getQuantiteObjetFinancier().get(t));
		System.out.println(p.getPrixObjetFinancier().get(t));
		System.out.println("on tente d'en acheter trop prix =1000 sans argent");
		p.acheter(t, 1000);
		System.out.println(p.getQuantiteObjetFinancier().get(t));
		System.out.println(p.getPrixObjetFinancier().get(t));
		System.out.println("on achete tout sans argent");
		p.acheter(t, 100);
		System.out.println(p.getQuantiteObjetFinancier().get(t));
		System.out.println(p.getPrixObjetFinancier().get(t));
		System.out.println("on achete tout avec argent");
		p.setArgentDisponible(10000000.0);
		p.acheter(t, 100);
		System.out.println(p.getQuantiteObjetFinancier().get(t));
		System.out.println(p.getPrixObjetFinancier().get(t));
		System.out.println("on essaie d'en racheter une");
		p.acheter(t, 1);
		System.out.println(p.getQuantiteObjetFinancier().get(t));
		System.out.println(p.getPrixObjetFinancier().get(t));
		System.out.println("on en vend trop");
		p.vendre(t, 110);
		System.out.println(p.getQuantiteObjetFinancier().get(t));
		System.out.println(p.getPrixObjetFinancier().get(t));
		System.out.println("on en vend la moitie");
		p.vendre(t, 50);
		System.out.println(p.getQuantiteObjetFinancier().get(t));
		System.out.println(p.getPrixObjetFinancier().get(t));
		v.set(3, 0.0);
		System.out.println("on achete tout en mettant le prix a 0");
		p.acheter(t, 50);
		System.out.println(p.getQuantiteObjetFinancier().get(t));
		System.out.println(p.getPrixObjetFinancier().get(t));
		Obligation o=new Obligation("orange", 100.0, 0.1, 100, new GregorianCalendar());
		p.acheter(o, 10);
		System.out.println(p.getQuantiteObjetFinancier());

	}
}
