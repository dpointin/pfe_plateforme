package modele;

import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

public class Historique {

	private String code;
	private Hashtable<GregorianCalendar, Vector<Double>> valeurs;
	// ATTENTION !! La Hashtable n'est pas triée selon les dates. VOIR testHistoriqueDao.java
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
	
	// GETTERS AND SETTERS
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Hashtable<GregorianCalendar, Vector<Double>> getValeurs() {
		return valeurs;
	}

	public void setValeurs(Hashtable<GregorianCalendar, Vector<Double>> valeurs) {
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
