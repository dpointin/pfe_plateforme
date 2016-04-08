package modele;

/**
* Classe abstraite ObjetFinancier representant un objet financier du jeu 
*
* @author  Celine Chaugny & Damien Pointin 
*/
public abstract class ObjetFinancier {
	/**
	* Le nombreDisponible de l'objetFinancier.
	*
	* @see ObjetFinancier#ObjetFinancier(int)
	* @see ObjetFinancier#getNombreDisponible()
	* @see ObjetFinancier#setNombreDisponible(Integer)
	*/ 
	protected Integer nombreDisponible;

	/**
	* Constructeur ObjetFinancier.
	* <p>
	* Avec le parametre nombreDisponible
	* correspondant a l'entree
	* </p>
	*
	* @param nombreDisponible de l'objet financier
	*/ 	
	public ObjetFinancier(int nombreDisponible) {
		this.nombreDisponible=nombreDisponible;
	}
	
	/**
	* Retourne le nombreDisponible de l'objet financier.
	*
	* @return le nombreDisponible de l'objet financier.
	*/ 
	public Integer getNombreDisponible() {
		return nombreDisponible;
	}
	
	/**
	* Met a jour le nombreDisponible de l'objet financier.
	*
	* @param nombreDisponible nouveau nombreDisponible de l'objet financier.
	*/ 
	public void setNombreDisponible(Integer nombreDisponible) {
		this.nombreDisponible = nombreDisponible;
	}

	/**
	* Methode abstraite qui sera implementee dans les classes filles
	* car le calcul du prix de l'objet financier differe selon son type.
	*
	* @ruturn le prix de l'objet financier.
	*/ 
	abstract double getPrix(); 
}
