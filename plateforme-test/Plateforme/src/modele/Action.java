package modele;

/**
* Classe Action representant une action du jeu 
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Action extends Titre {
	/**
	* Le dividende de l'action.
	*
	* @see Action#Action(String, String, Integer, Double)
	* @see Action#getDividende()
	* @see Action#setDividende(Double)
	*/ 
	private Double dividende;

	
	/**
	* Constructeur Action.
	* <p>
	* Avec les parametres code, libelle, nombreDisponible et dividende
	* correspondant aux entrees
	* </p>
	*
	* @param code de l'action
	* @param libelle de l'action
	* @param nombreDisponible de l'action
	* @param dividende de l'action
	*
	* @see Action#dividende
	* @see Titre#Titre(String, String, Integer)
	*/ 	
	public Action(String code, String libelle, Integer nombreDisponible, Double dividende) {
		super(code, libelle, nombreDisponible);
		this.setDividende(dividende);
	}

	
	/**
	* Retourne le dividende de l'action.
	*
	* @return le dividende de l'action.
	*/ 
	public Double getDividende() {
		return dividende;
	}

	
	/**
	* Met a jour le dividende de l'action.
	*
	* @param dividende , nouveau dividende de l'action.
	*
	*/ 
	public void setDividende(Double dividende) {
		this.dividende = dividende;
	}

	
	/**
	* toString correspondant a l'action.
	*
	* @return String correspondant a notre action.
	*/ 
	public String toString(){
		return "ACTION;"+getLibelle();
	}
}
