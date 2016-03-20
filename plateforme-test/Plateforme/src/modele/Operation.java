package modele;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Operation {
	private ObjetFinancier objetFinancier;
	private double prixUnitaire;
	private int quantite;
	private GregorianCalendar date;
	int id;
	
	
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
	
	public String toString(){
		String s=id+";";
		s+=date.get(Calendar.DATE)+"/"+date.get(Calendar.MONTH+1)+"/"+date.get(Calendar.YEAR)+";";
		if(quantite<0)
			s+="VENTE;"+(-quantite)+";";
		else
			s+="ACHAT;"+quantite+";";
		s+=prixUnitaire+";";
		s+=objetFinancier.toString()+";";
		return s;
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
	
	public void setId(int id){
		this.id=id;
	}
	
}
