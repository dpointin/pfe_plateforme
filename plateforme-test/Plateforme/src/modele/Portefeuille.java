package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
* Classe Portefeuille representant le portefeuille d'un joueur
* 
* @author  Celine Chaugny & Damien Pointin 
*/
public class Portefeuille {
	/**
	* L'argentDisponible du portefeuille.
	*
	* @see Portefeuille#Portefeuille(Double)
	* @see Portefeuille#getArgentDisponible()
	* @see Portefeuille#setArgentDisponible(Double)
	*/ 
	private Double argentDisponible;
	
	/**
	* La quantiteObjetFinancier du portefeuille.
	* Hashtable qui pour chaque ObjetFinancier associe une quantite.
	*
	* @see Portefeuille#getQuantiteObjetFinancier()
	* @see Portefeuille#setQuantiteObjetFinancier(Hashtable<ObjetFinancier,Integer>)
	*/ 
	private Hashtable<ObjetFinancier,Integer> quantiteObjetFinancier;
	
	/**e
	* L prixObjetFinancier du portefeuille.
	* Hashtable qui pour chaque ObjetFinancier associe un prix.
	*
	* @see Portefeuille#getPrixObjetFinancier()
	* @see Portefeuille#setPrixObjetFinancier(Hashtable<ObjetFinancier,Double>)
	*/ 	
	private Hashtable<ObjetFinancier,Double> prixObjetFinancier;
	
	/**
	* Le rendement du portefeuille.
	*
	* @see Portefeuille#getRendement()
	* @see Portefeuille#setRendement(Double)
	*/ 	
	private Double rendement;
	
	/**
	* L'idPortefeuille du portefeuille.
	*
	* @see Portefeuille#getIdPortefeuille()
	* @see Portefeuille#setIdPortefeuille(Integer)
	*/ 	
	private Integer idPortefeuille;
	
	/**
	* Les operations du portefeuille.
	*
	* @see Portefeuille#getOperations()
	* @see Portefeuille#setOperations(Vector<Operation>)
	*/ 		
	private Vector<Operation> operations;
	
	/**
	 * Constructeur Portefeuille sans parametres.
	 */
	public Portefeuille() {
		this(10000.0);
	}

	/**
	 * Constructeur Portefeuille.
	 * <p>
	 * Avec le parametre argentDisponible
	 * correspondant a l'entree
	 * </p>
	 * 
	 * @param argentDisponible
	 */
	public Portefeuille(Double argentDisponible) {
		super();
		this.argentDisponible = argentDisponible;
		this.quantiteObjetFinancier = new  Hashtable<ObjetFinancier,  Integer>();
		this.prixObjetFinancier = new Hashtable<ObjetFinancier, Double>();
		this.rendement = 0.0;
		this.idPortefeuille = -1;
		this.operations=new Vector<Operation>();
	}
	
	
	/**
	 * Methode qui compte le nombre d'objets financiers du portefeuille 
	 *
	 * @return int le nombre d'objets financiers du portefeuille.
	 */
	public int getNbActifs() {
		Enumeration<ObjetFinancier> enum_ObjetsFinanciers = getPrixObjetFinancier().keys();
		ArrayList<ObjetFinancier> objetsFinanciers = Collections.list(enum_ObjetsFinanciers);

		int n=0;
		for (ObjetFinancier o : objetsFinanciers) {
			if (o instanceof Titre) {
				n++;
			}
		}
		return n;
	}
	
	
	/**
	 * Methode qui permet d'acheter un Objet financier
	 * <p>
	 * Avec les parametres objetFinancier et quantite
	 * correspondant aux entrees
	 * </p>
	 * 
	 * @param objetFinancier qui fait l'objet de l'achat
	 * @param quantite d'objets financiers a acheter
	 * @return un boolean pour savoir si l'achat a pu avoir lieu ou non
	 */
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

	/**
	 * Methode qui permet de vendre un Objet financier du portefeuille
	 * <p>
	 * Avec les parametres objetFinancier et quantite
	 * correspondant aux entrees
	 * </p>
	 * 
	 * @param objetFinancier qui fait l'objet de la vente
	 * @param quantite d'objets financiers a vendre
	 * @return un boolean pour savoir si la vente a pu avoir lieu ou non
	 */	
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
	
    
    /**
     * Methode permettant d'avoir le portefeuille au format csv
     * 
     * @return le portefeuille sous forme de string pour ecrire dans le fichier csv
     */
    public String ecrire(){
    	String s;
    	s="Argent disponible;"+argentDisponible+"<br><br><br>";
    	s+="Composition Portefeuille <br>";
    	s+="Type du produit;Descriptif ; quantite; prixUnitaire <br>";
    	Iterator<ObjetFinancier> it = quantiteObjetFinancier.keySet().iterator(); 
		while(it.hasNext()) {
		   ObjetFinancier key = it.next();
		   s+=key.toString()+";"+quantiteObjetFinancier.get(key)+";"+prixObjetFinancier.get(key)+"<br>";
		}
		s+="<br><br>";
    	s+="Numero ;Date ; ACHAT/VENTE; Quantite; Prix Unitaire; Type du produit; Detail du produit"+"<br>";
    	for(Operation o:operations){
    		s+=o.toString()+"<br>";
    	}
    	return s;
    }
    
    /**
     * Methode qui permet d'ajouter une certain quantite d'un objet financier
	 * <p>
	 * Avec les parametres objetFinancier et quantite
	 * correspondant aux entrees
	 * </p>
     * 
     * @param objetFinancier a ajouter dans le portefeuille
     * @param quantite d'objets a ajouter
     */
    public void ajoutQuantiteObjetFinancier(ObjetFinancier objetFinancier, Integer quantite) {
		getQuantiteObjetFinancier().put(objetFinancier,quantite);
	}
    
    /**
     * Methode qui permet d'ajouter le prix d'un objet financier
	 * <p>
	 * Avec les parametres objetFinancier et prix
	 * correspondant aux entrees
	 * </p>
     * 
     * @param objetFinancier a ajouter dans le portefeuille
     * @param prix de l'objet a ajouter
     */    
	public void ajoutPrixObjetFinancier(ObjetFinancier objetFinancier, Double prix) {
		getPrixObjetFinancier().put(objetFinancier,prix);
	}
	
	
	/**
	 * Methode permettant de trouver un objet financier du portefeuille
	 * 
	 * @param objetFinancier
	 * @return l'objet financier du portefeuille
	 */
	public ObjetFinancier trouver(ObjetFinancier objetFinancier){
		Iterator<ObjetFinancier> it = quantiteObjetFinancier.keySet().iterator(); 
		while(it.hasNext()) {
		   ObjetFinancier key = it.next();
		   if ((objetFinancier instanceof Obligation) && (((Obligation)objetFinancier).equals(key)) ){
			   return key;
		   } else  if ((objetFinancier instanceof Titre) && (((Titre)objetFinancier).equals(key)) ){
			   		return key;
		   } else if  (key instanceof Option) {
		   }
		}
		return objetFinancier;
	}
	
	/**
	 * Methode permettant de recuperer les quantites de chaque type d'objets financiers du portefeuille.
	 * 
	 * @return un tableau d'entier avec le nombre de chaque type d'objets financiers.
	 * 			Integer[Obligation,Action,Indice,Option]
	 */
	public Integer[] getCamembertQuantite() {
		// obligation, ation, indice, option
		Integer[] n = {0,0,0,0};
		Iterator<ObjetFinancier> it = quantiteObjetFinancier.keySet().iterator(); 
		while(it.hasNext()) {
		   ObjetFinancier key = it.next();
		   if (key instanceof Obligation) {
			   n[0] = n[0] + quantiteObjetFinancier.get(key);
		   } else if  (key instanceof Action) {
			   n[1] = n[1] + quantiteObjetFinancier.get(key);			   
		   } else if  (key instanceof Indice) {
			   n[2] = n[2] + quantiteObjetFinancier.get(key);			   
		   } else if  (key instanceof Option) {
			   n[3] = n[3] + quantiteObjetFinancier.get(key);			   
		   }
		}
		return n;
	}
	
	/**
	 * Methode permettant de recuperer les sommes d'argent investies dans chaque type d'objets financiers du portefeuille.
	 * 
	 * @return un tableau de valeurs avec les sommes investies dans chaque type d'objets financiers.
	 * 			Double[Obligation,Action,Indice,Option]
	 */
	public Double[] getCamembertPrix() {
		// obligation, ation, indice, option
		Double[] n = {0.0,0.0,0.0,0.0};
		Iterator<ObjetFinancier> it = prixObjetFinancier.keySet().iterator(); 
		while(it.hasNext()) {
		   ObjetFinancier key = it.next();
		   if (key instanceof Obligation) {
			   n[0] = n[0] + prixObjetFinancier.get(key)*quantiteObjetFinancier.get(key);
		   } else if  (key instanceof Action) {
			   n[1] = n[1] + prixObjetFinancier.get(key)*quantiteObjetFinancier.get(key);			   
		   } else if  (key instanceof Indice) {
			   n[2] = n[2] + prixObjetFinancier.get(key)*quantiteObjetFinancier.get(key);			   
		   } else if  (key instanceof Option) {
			   n[3] = n[3] + prixObjetFinancier.get(key)*quantiteObjetFinancier.get(key);			   
		   }
		}
		return n;
	}
	
	/**
	 * Retourne l'argentDisponible du portefeuille.
	 * 
	 * @return l'argentDisponible du portefeuille.
	 */
	public Double getArgentDisponible() {
		return argentDisponible;
	}
	
	/**
	 * Met a jour l'argentDisponible du portefeuille.
	 * 
	 * @param argentDisponible, nouvel argentDisponible du portefeuille.
	 */
	public void setArgentDisponible(Double argentDisponible) {
		this.argentDisponible = argentDisponible;
	}
	
	/**
	 * Retourne la quantiteObjetFinancier du portefeuille.
	 * 
	 * @return la quantiteObjetFinancier du portefeuille.
	 */	
	public Hashtable<ObjetFinancier, Integer> getQuantiteObjetFinancier() {
		return quantiteObjetFinancier;
	}
	
	/**
	 * Met a jour la quantiteObjetFinancier du portefeuille.
	 * 
	 * @param la quantiteObjetFinancier, nouvelle quantiteObjetFinancier du portefeuille.
	 */	
	public void setQuantiteObjetFinancier(Hashtable<ObjetFinancier, Integer> quantiteObjetFinancier) {
		this.quantiteObjetFinancier = quantiteObjetFinancier;
	}
	
	/**
	 * Retourne le prixObjetFinancier du portefeuille.
	 * 
	 * @return le prixObjetFinancier du portefeuille.
	 */		
	public Hashtable<ObjetFinancier, Double> getPrixObjetFinancier() {
		return prixObjetFinancier;
	}
	
	/**
	 * Met a jour la prixObjetFinancier du portefeuille.
	 * 
	 * @param la prixObjetFinancier, nouveau prixObjetFinancier du portefeuille.
	 */	
	public void setPrixObjetFinancier(Hashtable<ObjetFinancier, Double> prixObjetFinancier) {
		this.prixObjetFinancier = prixObjetFinancier;
	}
	
	/**
	 * Retourne le rendement du portefeuille.
	 * 
	 * @return le rendement du portefeuille.
	 */		
	public Double getRendement() {
		return rendement;
	}
	
	/**
	 * Met a jour le rendement du portefeuille.
	 * 
	 * @param le rendement, nouveau rendement du portefeuille.
	 */		
	public void setRendement(Double rendement) {
		this.rendement = rendement;
	}

	/**
	 * Retourne l'idPortefeuille du portefeuille.
	 * 
	 * @return l'idPortefeuille du portefeuille.
	 */	
	public Integer getIdPortefeuille() {
		return idPortefeuille;
	}
	
	/**
	 * Met a jour l'idPortefeuille du portefeuille.
	 * 
	 * @param l'idPortefeuille, nouvel idPortefeuille du portefeuille.
	 */			
	public void setIdPortefeuille(Integer idPortefeuille) {
		this.idPortefeuille = idPortefeuille;
	}
	
	/**
	 * Retourne les operations du portefeuille.
	 * 
	 * @return les operations du portefeuille.
	 */	
	public Vector<Operation> getOperations() {
		return operations;
	}

	/**
	 * Met a jour les operations du portefeuille.
	 * 
	 * @param les operations, nouvelles operations du portefeuille.
	 */	
	public void setOperations(Vector<Operation> operations) {
		this.operations = operations;
	}

}
