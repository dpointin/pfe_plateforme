package modele;

import java.util.Comparator;

/**
* Classe qui effectue la comparaison entre deux joueurs
* 
* @author  Celine Chaugny & Damien Pointin 
*/
public class JoueurComparateur implements Comparator<Joueur>{

	/**
	 * Methode qui compare deux joueurs
	 *
	 * @param j1 premier joueur que l'on compare
	 * @param j2 deuxieme joueur que l'on compare
	 * 
	 * @return int negatif si joueur 1 avant joueur 2 positif sinon.
	 */
	@Override
	public int compare(Joueur j1, Joueur j2) {
		if(j1.calculScore()<j2.calculScore())
			return -1;
		else
			return 1;
	}

}
