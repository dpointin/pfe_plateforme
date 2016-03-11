package test.testdao;

import java.util.GregorianCalendar;

import dao.DAOFactory;
import dao.EstComposeObligationDaoImpl;
import modele.Portefeuille;
import modele.Obligation;

public class testEstComposeObligationDao {

	public static void main(String[] args) {
		DAOFactory fac= DAOFactory.getInstance();
		EstComposeObligationDaoImpl estctdao = (EstComposeObligationDaoImpl) fac.getEstComposeObligationDao();
		Obligation o=new Obligation("orange", 100.0, 0.1, 100, new GregorianCalendar());
		Portefeuille p=new Portefeuille();
		p.setIdPortefeuille(1);
		p.setArgentDisponible(10000000.0);
		p.acheter(o, 3);
		estctdao.mettreAJour(p,o);
		Obligation o2=new Obligation("bouygues", 100.0, 0.1, 100, new GregorianCalendar());
		p.acheter(o2, 5);
		estctdao.mettreAJour(p, o2);
		//estctdao.supprimer(p.getIdPortefeuille());
		Portefeuille p2=new Portefeuille();
		p2.setIdPortefeuille(1);
		estctdao.trouver(p2);
		System.out.println(p2.getQuantiteObjetFinancier());
		
}
}
