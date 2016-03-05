package modele;

public abstract class ObjetFinancier {
	protected Integer nombreDisponible;

	public ObjetFinancier(int nombreDispo) {
		nombreDisponible=nombreDispo;
	}
	
	public Integer getNombreDisponible() {
		return nombreDisponible;
	}
	public void setNombreDisponible(Integer nombreDisponible) {
		this.nombreDisponible = nombreDisponible;
	}

	abstract double getPrix(); 
}
