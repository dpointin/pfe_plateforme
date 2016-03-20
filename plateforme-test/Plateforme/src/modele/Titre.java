package modele;

public abstract class Titre extends ObjetFinancier{

	private String code ;
	private String libelle;
	protected Historique historique;

	public Titre(String code, String libelle, Integer nombreDisponible) {
		super(nombreDisponible);
		this.code = code;
		this.libelle = libelle;
		this.historique = new Historique(code);
	}
	
	// GETTER SETTER
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Historique getHistorique() {
		return historique;
	}

	public void setHistorique(Historique historique) {
		this.historique = historique;
	}

	public double getPrix(){
		return historique.getFermetureJours(historique.getValeurs().lastKey());
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Titre ) && ((Titre) obj).getCode().equals(code);
	}
}
