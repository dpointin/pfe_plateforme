package test;

import dao.JoueurDaoImpl;
import modele.Joueur;
import dao.DAOFactory;

public class testJoueurDao {
	public static void main(String[] args) {
		DAOFactory fac= DAOFactory.getInstance();
		JoueurDaoImpl jou= (JoueurDaoImpl) fac.getJoueurDao();
		System.out.println("Debut des tests");
		System.out.println("Test de la fonction trouver");
		System.out.println("avec un element existant");
		System.out.println(jou.trouver("test"));
		System.out.println("avec un element non existant");
		System.out.println(jou.trouver("ifousdiofjdskl"));
		System.out.println("Test de la fonction creer");
		System.out.println("avant la creation" +jou.trouver("joueurdao1"));
		jou.creer(new Joueur("joueurdao1", "test"));
		System.out.println("apres la creation" +jou.trouver("joueurdao1"));
	}
}
