package test;

import dao.DAOFactory;
import dao.TitreDaoImpl;
import modele.Titre;

public class testPortefeuille {
	
	public static void main(String[] args) {

		DAOFactory fac= DAOFactory.getInstance();
		TitreDaoImpl tit= (TitreDaoImpl) fac.getTitreDao();
		System.out.println(tit.trouverTousTitres());
	
		String code = "^FCHI";
		System.out.println(code + " est un(e) " + tit.recupererTypeTitre(code));
		
		Titre t = tit.recupererTitre(code);

	}
}
