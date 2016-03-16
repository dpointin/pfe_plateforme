package modele;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

public class Historique {

	private String code;
	private TreeMap<GregorianCalendar, Vector<Double>> valeurs;
	/*	Vector<Double> :
	 * 	0 : ouverture
	 *  1 : haut
	 *  2 : bas
	 *  3 : fermeture
	 *  4 : volume
	 *  5 : adjFermeture
	*/		
	
	public Historique() {
		super();
	}	
	
	public Historique(String code) {
		super();
		this.code = code;
	}	
	
	
	public TreeMap<GregorianCalendar, Double> getBaseCent(){
		TreeMap<GregorianCalendar, Double> baseCent=new TreeMap<GregorianCalendar, Double>();
		//Notre reference est la premiere date
		Double reference= getFermetureJours(valeurs.firstKey());
		
		//on itere sur toutes les valeurs et on les rajoute
		Iterator<GregorianCalendar> it=valeurs.keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
		
		while(it.hasNext()) { /// que les 50 dernieres valeurs
			GregorianCalendar key=(GregorianCalendar) it.next();
			baseCent.put(key, getFermetureJours(key)/reference*100);
		}
		System.out.println("taille valeurs : "+ valeurs.size()+ "\n nb de dates : " + baseCent.size());
		return baseCent;
	}
	
	// GETTERS AND SETTERS
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public TreeMap<GregorianCalendar, Vector<Double>> getValeurs() {
		return valeurs;
	}

	public void setValeurs(TreeMap<GregorianCalendar, Vector<Double>> valeurs) {
		this.valeurs = valeurs;
	}
	
	
	// GETTER supplémentaires pour simplifier l'accès
	public Vector<Double> getCoursJours(GregorianCalendar date){		
		return getValeurs().get(date);
	}
	
	public Double getOuvertureJours(GregorianCalendar date){
		return getValeurs().get(date).get(0);
	}
	
	public Double getHautJours(GregorianCalendar date){
		return getValeurs().get(date).get(1);
	}
	
	public Double getBasJours(GregorianCalendar date){
		return getValeurs().get(date).get(2);
	}
	
	public Double getFermetureJours(GregorianCalendar date){
		return getValeurs().get(date).get(3);
	}
	
	public Double getVolumeJours(GregorianCalendar date){
		return getValeurs().get(date).get(4);
	}
	
	public Double getAdjFermetureJours(GregorianCalendar date){
		return getValeurs().get(date).get(5);
	}

}
