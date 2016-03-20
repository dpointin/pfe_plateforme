package modele;

public class Indice extends Titre {

	public Indice(String code, String libelle, Integer nombreDisponible) {
		super(code, libelle, nombreDisponible);
	}
	
	public String toString(){
		return "INDICE;"+getLibelle();
	}
}
