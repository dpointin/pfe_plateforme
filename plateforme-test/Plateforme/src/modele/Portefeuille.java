package modele;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class Portefeuille {
	
	private Double argentDisponible;
	private Hashtable<ObjetFinancier,Integer> quantiteObjetFinancier;
	private Hashtable<ObjetFinancier,Double> prixObjetFinancier;	
	private Double rendement;
	private Integer idPortefeuille;
	private Vector<Operation> operations;
	
	public Portefeuille() {
		this(1000.0);
	}

	public Portefeuille(Double argentDisponible) {
		super();
		this.argentDisponible = argentDisponible;
		this.quantiteObjetFinancier = new  Hashtable<ObjetFinancier,  Integer>();
		this.prixObjetFinancier = new Hashtable<ObjetFinancier, Double>();
		this.rendement = 0.0;
		this.idPortefeuille = -1;
		operations=new Vector<Operation>();
	}
	
	public boolean acheter(ObjetFinancier objetFinancier, int quantite){
		if( (objetFinancier.getNombreDisponible()>=quantite) && (objetFinancier.getPrix()*quantite<=argentDisponible) ){
			int quantiteTemp=0;
			if(quantiteObjetFinancier.get(objetFinancier)!=null)
				quantiteTemp=quantiteObjetFinancier.get(objetFinancier);
			double tempPrix=0;
			if(prixObjetFinancier.get(objetFinancier)!=null)
				tempPrix=prixObjetFinancier.get(objetFinancier);
			argentDisponible-=objetFinancier.getPrix()*quantite;
			double prixFinal=(quantiteTemp*tempPrix+quantite*objetFinancier.getPrix());
		    int quantiteFinal=quantiteTemp+quantite;
		    prixFinal/=quantiteFinal;
		    quantiteObjetFinancier.put(objetFinancier, quantiteFinal);
		    prixObjetFinancier.put(objetFinancier, prixFinal);
			//nombre dispo mis a jour
		    objetFinancier.setNombreDisponible(objetFinancier.getNombreDisponible()-quantite);
			operations.add(new Operation(objetFinancier,objetFinancier.getPrix(),quantite));
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
				//nombre dispo mis a jour
		    	objetFinancier.setNombreDisponible(objetFinancier.getNombreDisponible()+quantite);
				argentDisponible+=quantite*objetFinancier.getPrix();
				operations.add(new Operation(objetFinancier,objetFinancier.getPrix(),-quantite));
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
	
	public ObjetFinancier trouver(ObjetFinancier o){
		Iterator<ObjetFinancier> it = quantiteObjetFinancier.keySet().iterator(); 
		while(it.hasNext()) {
		   ObjetFinancier key = it.next();
		   if ((o instanceof Obligation) && (((Obligation)o).equals(key)) ){
			   return key;
		   } else  if ((o instanceof Titre) && (((Titre)o).equals(key)) ){
			   		return key;
		   } else if  (key instanceof Option) {
		   }
		}
		return o;
	}
	
	//GETTER ET SETTER
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
	
	public Vector<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Vector<Operation> operations) {
		this.operations = operations;
	}

	public Integer[] getCamembert() {
		// obligation, ation, indice, option
		Integer[] n = {0,0,0,0};
		System.out.println("je suis dans cammebert");
		Iterator<ObjetFinancier> it = quantiteObjetFinancier.keySet().iterator(); 
		while(it.hasNext()) {
		   ObjetFinancier key = it.next();
		   if (key instanceof Obligation) {
				System.out.println("je suis dans obligaton");
			   n[0] = n[0] + quantiteObjetFinancier.get(key);
		   } else if  (key instanceof Action) {
				System.out.println("je suis dans action");
			   n[1] = n[1] + quantiteObjetFinancier.get(key);			   
		   } else if  (key instanceof Indice) {
			   n[2] = n[2] + quantiteObjetFinancier.get(key);			   
		   } else if  (key instanceof Option) {
			   n[3] = n[3] + quantiteObjetFinancier.get(key);			   
		   }
		}
		return n;
	}
}
