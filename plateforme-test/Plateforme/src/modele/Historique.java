package modele;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

/**
* Classe Historique representant l'historique d'un titre 
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Historique {
	/**
	* Le code du tire auquel appartient l'historique.
	*
	*/ 
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
		//System.out.println("taille valeurs : "+ valeurs.size()+ "\n nb de dates : " + baseCent.size());
		return baseCent;
	}
	
	public TreeMap<GregorianCalendar, Double> getBaseCentReference(GregorianCalendar dateReference){
		TreeMap<GregorianCalendar, Double> baseCent=new TreeMap<GregorianCalendar, Double>();
		//Notre reference est la premiere date
		Double reference= getFermetureJours(dateReference);
		
		//on itere sur toutes les valeurs et on les rajoute
		Iterator<GregorianCalendar> it=valeurs.keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
		
		while(it.hasNext()) { /// que les 50 dernieres valeurs
			GregorianCalendar key=(GregorianCalendar) it.next();
			baseCent.put(key, getFermetureJours(key)/reference*100);
		}
		//System.out.println("taille valeurs : "+ valeurs.size()+ "\n nb de dates : " + baseCent.size());
		return baseCent;
	}
	
	public Double calculEsperanceRendement(){
		Iterator<GregorianCalendar> it=valeurs.keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
		Double rendement=1.0;
		int nb=0;
		GregorianCalendar debut=(GregorianCalendar) it.next();
		while(it.hasNext()) { 
			GregorianCalendar fin=(GregorianCalendar) it.next();
			Double rendementJour=(getFermetureJours(debut)-getFermetureJours(fin))/getFermetureJours(debut);
			rendement*=(1.0+rendementJour);
			debut=fin;
			nb++;
		}
		rendement=Math.pow(rendement,1.0/nb);
		return rendement;
	}
	
	
	public Double calculVarianceRendement(){
		Iterator<GregorianCalendar> it=valeurs.keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
		//Moyenne géométrique mieux qu'arithmétique ici ??
		Double rendement=calculEsperanceRendement();
		Double variance=0.0;
		int nb=0;
		GregorianCalendar debut=(GregorianCalendar) it.next();
		while(it.hasNext()) { 
			GregorianCalendar fin=(GregorianCalendar) it.next();
			Double rendementJour=(getFermetureJours(debut)-getFermetureJours(fin))/getFermetureJours(debut);
			variance+=(rendementJour-rendement)*(rendementJour-rendement);
			debut=fin;
			nb++;
		}
		variance/=nb;
		return variance;
	}
	
	public Double calculCoVarianceRendement(Historique h2){
		Iterator<GregorianCalendar> it=valeurs.keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
		Double rendementh1=calculEsperanceRendement();
		Double rendementh2=h2.calculEsperanceRendement();
		Double covariance=0.0;
		int nb=0;
		GregorianCalendar debut=null;
		//date de debut commune aux deux
		while(debut==null && it.hasNext()){
			debut=(GregorianCalendar) it.next();
			if(h2.getFermetureJours(debut)==null)
				debut=null;
		}
		GregorianCalendar fin=null;
		while(it.hasNext()) { 
			//date de fin communes aux deux
			while(fin==null && it.hasNext()){
				fin=(GregorianCalendar) it.next();
				if(h2.getFermetureJours(debut)==null)
					debut=null;
			}
			Double rendementJourh1=(getFermetureJours(debut)-getFermetureJours(fin))/getFermetureJours(debut);
			Double rendementJourh2=(h2.getFermetureJours(debut)-h2.getFermetureJours(fin))/h2.getFermetureJours(debut);
			covariance+=(rendementJourh1-rendementh1)*(rendementJourh2-rendementh2);
			debut=fin;
			nb++;
		}
		covariance/=nb;
		return covariance;
	}
	
	
	public TreeMap<GregorianCalendar, Vector<Double>> calculMoyenneMobileSimple(Integer periode){
		Vector<Double> temp = new Vector<Double>(periode);
		TreeMap<GregorianCalendar, Vector<Double>> resultat = new TreeMap<GregorianCalendar, Vector<Double>>();
		
		if (periode > valeurs.size()) {
			return null; // erreur periode trop grande ou alors on fait une moyenne par defaut..?
		}
		
		Iterator<GregorianCalendar> it = valeurs.keySet().iterator();
		if (it.hasNext()) {
			GregorianCalendar date = null ;
			for (int i=0; i<periode; i++) {
				date = (GregorianCalendar) it.next();
				temp.add(getFermetureJours(date));
			}
			
			Vector<Double> val = new Vector<Double>();
			Double moyenne = 0.0;
			for (Double v : temp) {
				moyenne += v;
			}
			val.add(getFermetureJours(date));
			val.add(moyenne/periode);
			resultat.put(date, val);
			
			while (it.hasNext()) {
				date = (GregorianCalendar) it.next();
				Double dernier = getFermetureJours(date);
				moyenne = moyenne - temp.firstElement() + dernier;
				temp.remove(0); // on retire le plus ancien du vecteur temporaire
				temp.add(dernier); // on ajoute le nouveau
				val.clear(); 
				val.add(dernier);
				val.add(moyenne/periode);
				resultat.put(date, val);
			}
		}
		
		return resultat;
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
