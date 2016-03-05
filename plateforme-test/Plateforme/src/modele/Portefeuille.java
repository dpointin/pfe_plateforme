package modele;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
public class Portefeuille {
	
	private Double argentInvesti;
	private Double argentDisponible;
	private Hashtable<ObjetFinancier,Integer> quantiteObjetFinancier;
	private Hashtable<ObjetFinancier,Double> prixObjetFinancier;	
	private Double rendement;
	private Integer idPortefeuille;
	
	public Portefeuille() {
		this(0.0);
	}

	public Portefeuille(Double argentDisponible) {
		super();
		this.argentInvesti = 0.0;
		this.argentDisponible = argentDisponible;
		this.quantiteObjetFinancier = new  Hashtable<ObjetFinancier,  Integer>();
		this.prixObjetFinancier = new Hashtable<ObjetFinancier, Double>();
		this.rendement = 0.0;
		this.idPortefeuille = -1;
	}

	
	public boolean acheter(ObjetFinancier objetFinancier, int quantite){
		if( (objetFinancier.getNombreDisponible()>=quantite) && (objetFinancier.getPrix()*quantite<=argentDisponible) ){
			int quantiteTemp=0;
			if(quantiteObjetFinancier.get(objetFinancier)!=null)
				quantiteTemp=quantiteObjetFinancier.get(objetFinancier);
			double tempPrix=0;
			if(prixObjetFinancier.get(objetFinancier)!=null)
				tempPrix=prixObjetFinancier.get(objetFinancier);
			Date d=new Date();
			double prixFinal=(quantiteTemp*tempPrix+quantite*objetFinancier.getPrix());
		    int quantiteFinal=quantiteTemp+quantite;
		    prixFinal/=quantiteFinal;
		    quantiteObjetFinancier.put(objetFinancier, quantiteFinal);
		    prixObjetFinancier.put(objetFinancier, prixFinal);
			objetFinancier.setNombreDisponible(objetFinancier.getNombreDisponible()-quantite);
			return true;
	    }
		return false;
	}

    public boolean vendre(ObjetFinancier objetFinancier, Integer quantite){
    	if(quantiteObjetFinancier.get(objetFinancier)!=null){
	    	int tempQuantite=quantiteObjetFinancier.get(objetFinancier);
	    	if(tempQuantite>=quantite){
		    	tempQuantite-=quantite;
		    	quantiteObjetFinancier.put(objetFinancier, tempQuantite);
				objetFinancier.setNombreDisponible(objetFinancier.getNombreDisponible()+quantite);
				return true;
			}
    	}
    	return false;
    }	
	
    
    
    
    //METHODE UTILISE DANS LE DAO POUR LE MOMENT
	public void ajoutQuantiteObjetFinancier(ObjetFinancier o, Integer q) {
		getQuantiteObjetFinancier().put(o,q);
	}
	public void ajoutPrixObjetFinancier(ObjetFinancier o, Double p) {
		getPrixObjetFinancier().put(o,p);
	}
	
	
	
	//GETTER ET SETTER
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
	public Hashtable<ObjetFinancier, Integer> getQuantiteObjetFinancier() {
		return quantiteObjetFinancier;
	}
	public void setQuantiteObjetFinancier(Hashtable<ObjetFinancier, Integer> quantiteTitre) {
		this.quantiteObjetFinancier = quantiteTitre;
	}
	public Hashtable<ObjetFinancier, Double> getPrixObjetFinancier() {
		return prixObjetFinancier;
	}
	public void setPrixObjetFinancier(Hashtable<ObjetFinancier, Double> prixTitre) {
		this.prixObjetFinancier = prixTitre;
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
