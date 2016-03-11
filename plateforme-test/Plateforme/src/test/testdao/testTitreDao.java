package test.testdao;

import dao.DAOFactory;
import dao.TitreDaoImpl;
import modele.Action;
import modele.Indice;
import modele.Titre;

public class testTitreDao {
	public static void main(String[] args) {
		DAOFactory fac= DAOFactory.getInstance();
		TitreDaoImpl tit= (TitreDaoImpl) fac.getTitreDao();
		System.out.println(tit.trouverTousTitres());
		
		String code = "^FCHI";
		System.out.println(code + " est un(e) " + tit.recupererTypeTitre(code));
		
		Titre t = tit.recupererTitre(code);
		System.out.println(t.getCode() + " (" + t.getLibelle() + ") - Nombre disponible: " + t.getNombreDisponible());
		
		if (tit.recupererTypeTitre(code).equals("ACTION")) {
			Action a = (Action) tit.recupererTitre(code);
			System.out.println(code + " (" + a.getLibelle() + ") - Nombre disponible: " + a.getNombreDisponible()
								+ " - Dividende: " + a.getDividende());
		} else {
			Indice i = (Indice) tit.recupererTitre(code);
			System.out.println(code + " (" + i.getLibelle() + ") - Nombre disponible: " + i.getNombreDisponible());			
		}
	}
}
