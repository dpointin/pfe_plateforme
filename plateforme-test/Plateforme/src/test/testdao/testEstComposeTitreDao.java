package test.testdao;

import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.Vector;

import dao.DAOFactory;
import dao.EstComposeTitreDaoImpl;
import modele.Action;
import modele.Historique;
import modele.Portefeuille;
import modele.Titre;

public class testEstComposeTitreDao {

		public static void main(String[] args) {
			DAOFactory fac= DAOFactory.getInstance();
			EstComposeTitreDaoImpl estctdao = (EstComposeTitreDaoImpl) fac.getEstComposeTitreDao();
			Titre t=new Action("AAL","Accor S.A.",100, 2.1);;
			Historique h=new Historique();
			TreeMap<GregorianCalendar, Vector<Double>> hash=new TreeMap<GregorianCalendar, Vector<Double>>();
			Vector<Double> v=new Vector<Double>();
			v.add(100000.0);
			v.add(100000.0);
			v.add(100000.0);
			v.add(100000.0);
			GregorianCalendar d = new GregorianCalendar();
			hash.put(d, v);
			h.setValeurs(hash);
			t.setHistorique(h);
			Portefeuille p=new Portefeuille();
			p.setIdPortefeuille(1);
			p.setArgentDisponible(10000000.0);
			p.acheter(t, 5);
			estctdao.mettreAJour(p,t);
			Titre t2=new Action("AC.PA","Accor S.A.",100, 2.1);
			t2.setHistorique(h);
			p.acheter(t2, 5);
			estctdao.mettreAJour(p, t2);
			//estctdao.supprimer(p.getIdPortefeuille());
			Portefeuille p2=new Portefeuille();
			p2.setIdPortefeuille(1);
			estctdao.trouver(p2);
			System.out.println(p2.getQuantiteObjetFinancier());
	}

}
