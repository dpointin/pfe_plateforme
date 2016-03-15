package modele;

import java.util.GregorianCalendar;

public class Operation {
	private ObjetFinancier objetFinancier;
	private double prixUnitaire;
	private int quantite;
	private GregorianCalendar date;
	
	
	//Constructeur
	public Operation(){
		this(null,0.0,0);
	}
	
	public Operation(ObjetFinancier objet, double prix, int quant){
		objetFinancier=objet;
		prixUnitaire=prix;
		quantite=quant;
		date= new GregorianCalendar();
	}
	
	
	//Getter et Setter
	public ObjetFinancier getObjetFinancier() {
		return objetFinancier;
	}
	
	public void setObjetFinancier(ObjetFinancier objetFinancier) {
		this.objetFinancier = objetFinancier;
	}
	
	public double getPrixUnitaire() {
		return prixUnitaire;
	}
	
	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	
	public int getQuantite() {
		return quantite;
	}
	
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	
}
