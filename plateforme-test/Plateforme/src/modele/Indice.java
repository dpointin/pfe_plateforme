package modele;

public class Indice extends Titre {
	/**
	* Constructeur Indice.
	* <p>
	* Avec les parametres code, libelle, nombreDisponible
	* </p>
	*
	* @param code de l'indice
	* @param libelle de l'indice
	* @param nombreDisponible de l'indice
	*
	* @see Titre#Titre(String, String, Integer)
	*/ 
	public Indice(String code, String libelle, Integer nombreDisponible) {
		super(code, libelle, nombreDisponible);
	}
	
	
	/**
	* toString correspondant a l'indice.
	*
	* @return String correspondant a notre indice.
	*/ 
	public String toString(){
		return "INDICE;"+getLibelle();
	}
}
