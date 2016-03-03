package modele;

import java.util.Hashtable;
public class Portefeuille {
	
	private Double argentInvesti;
	private Double argentDisponible;
	private Hashtable<Titre,Integer> quantiteTitre;
	private Hashtable<Titre,Double> prixTitre;
	private Hashtable<Obligation,Integer> quantiteObligation;
	private Hashtable<Obligation,Double> prixObligation;	
	private Hashtable<Option,Integer> quantiteOption;
	private Hashtable<Option,Double> prixOption;
	private Double rendement;
	private Integer idPortefeuille;
	
	public Portefeuille() {
		this(0.0);
	}

	public Portefeuille(Double argentDisponible) {
		super();
		this.argentInvesti = 0.0;
		this.argentDisponible = argentDisponible;
		this.quantiteTitre = new  Hashtable<Titre, Integer>();
		this.prixTitre = new Hashtable<Titre, Double>();
		this.quantiteObligation = new Hashtable<Obligation, Integer>();
		this.prixObligation = new Hashtable<Obligation, Double>();
		this.quantiteOption = new Hashtable<Option, Integer>();
		this.prixOption = new Hashtable<Option, Double>();
		this.rendement = 0.0;
		this.idPortefeuille = -1;
	}
	
	// methodes : achat(titre, quantite) achat(option, quantite), achat(obligation, quantite)
	// vendre(titre, quantite) vendre(option, quantite), vendre(obligation, quantite)

/*	public void ajouterAction(Action act, int quantite ){
		int tempQuantite=quantiteTitre.get(act);
		double tempPrix=prixTitre.get(act);
		double prixFinal=(tempQuantite*tempPrix+quantite*act.getValeurDuJour());
		int quantiteFinal=tempQuantite+quantite;
		prixFinal/=(double)quantiteFinal;
		quantiteTitre.put(act, quantiteFinal);
		prixTitre.put(act, prixFinal);
	}*/
	
/*	public void supprimerAction(Action act, int quantite){
		int tempQuantite=quantiteAction.get(act);
		tempQuantite-=quantite;
		quantiteAction.put(act, tempQuantite);
	}*/
	
	public void ajoutQuantiteTitre(Titre t, Integer q) {
		getQuantiteTitre().put(t,q);
	}
	public void ajoutPrixTitre(Titre t, Double p) {
		getPrixTitre().put(t,p);
	}
	public void ajoutQuantiteObligation(Obligation t, Integer q) {
		getQuantiteObligation().put(t,q);
	}	
	public void ajoutPrixObligation(Obligation t, Double p) {
		getPrixObligation().put(t,p);
	}
	public void ajoutQuantiteOption(Option t, Integer q) {
		getQuantiteOption().put(t,q);
	}	
	public void ajoutPrixOption(Option t, Double p) {
		getPrixOption().put(t,p);
	}
	
	
	
	public Double getArgentInvesti() {
		return argentInvesti;
	}
	public void setArgentInvesti(Double argentInvesti) {
		this.argentInvesti = argentInvesti;
	}
	public Double getArgentDisponible() {
		return argentDisponible;
	}
	public void setArgentDisponible(Double argentDisponible) {
		this.argentDisponible = argentDisponible;
	}
	public Hashtable<Titre, Integer> getQuantiteTitre() {
		return quantiteTitre;
	}
	public void setQuantiteTitre(Hashtable<Titre, Integer> quantiteTitre) {
		this.quantiteTitre = quantiteTitre;
	}
	public Hashtable<Titre, Double> getPrixTitre() {
		return prixTitre;
	}
	public void setPrixTitre(Hashtable<Titre, Double> prixTitre) {
		this.prixTitre = prixTitre;
	}
	public Hashtable<Obligation, Integer> getQuantiteObligation() {
		return quantiteObligation;
	}
	public void setQuantiteObligation(Hashtable<Obligation, Integer> quantiteObligation) {
		this.quantiteObligation = quantiteObligation;
	}
	public Hashtable<Obligation, Double> getPrixObligation() {
		return prixObligation;
	}
	public void setPrixObligation(Hashtable<Obligation, Double> prixObligation) {
		this.prixObligation = prixObligation;
	}
	public Hashtable<Option, Integer> getQuantiteOption() {
		return quantiteOption;
	}
	public void setQuantiteOption(Hashtable<Option, Integer> quantiteOption) {
		this.quantiteOption = quantiteOption;
	}
	public Hashtable<Option, Double> getPrixOption() {
		return prixOption;
	}
	public void setPrixOption(Hashtable<Option, Double> prixOption) {
		this.prixOption = prixOption;
	}
	public Double getRendement() {
		return rendement;
	}
	public void setRendement(Double rendement) {
		this.rendement = rendement;
	}

	public Integer getIdPortefeuille() {
		return idPortefeuille;
	}

	public void setIdPortefeuille(Integer idPortefeuille) {
		this.idPortefeuille = idPortefeuille;
	}
}
