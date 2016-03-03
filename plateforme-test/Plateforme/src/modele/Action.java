package modele;

public class Action extends Titre {
	
	private Double dividende;

	public Action(String code, String libelle, Integer nombreDisponible, Double dividende) {
		super(code, libelle, nombreDisponible);
		this.setDividende(dividende);
	}

	public Double getDividende() {
		return dividende;
	}

	public void setDividende(Double dividende) {
		this.dividende = dividende;
	}

}
