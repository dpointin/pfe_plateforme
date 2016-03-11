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

	/////////////////////A MODIFIER AVEC LA DERNIERE VALEUR QUI EST DANS L HISTORIQUE
	/////////////////////CETTE METHODE NE MARCHERA JAMAIS LES WEEK END OU JOUR FERIE SINON VU QUE LA BOURSE NE NOUS DONNERA PAS DE VALEUR
	public double getPrix(){
		//System.out.println(historique.getValeurs().lastKey());
		System.out.println(historique);
	//	return historique.getFermetureJours(historique.getValeurs().firstKey());
		//Date d=new Date();
		return 1000;//historique.getFermetureJours(new GregorianCalendar(2016,2,10));
	}
	
}
