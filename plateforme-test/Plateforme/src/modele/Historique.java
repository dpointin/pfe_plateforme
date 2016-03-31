package modele;

import java.util.Calendar;
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
	* @see Historique#getCode()
	* @see Historique#setCode(String)
	*/ 
	private String code;
	
	
	/**
	* Différentes valeurs du cours aux différentes dates;
	*  0 : ouverture
	*  1 : haut
	*  2 : bas
	*  3 : fermeture
	*  4 : volume
	*  5 : adjFermeture
	*  
	*  @see Historique#getValeurs()
	*  @see Historique#setValeurs(TreeMap)
	*/ 
	private TreeMap<GregorianCalendar, Vector<Double>> valeurs;
		
	
	/**
	* Constructeur Historique.
	*
	* @see Historique#code
	* @see Historique#valeurs
	*/ 
	public Historique() {
		super();
	}	
	
	
	/**
	* Constructeur Historique.
	* <p>
	* Avec le parametre code
	* </p>
	*
	* @param code de l'action qui correspond a l'historique
	*
	* @see Historique#code
	* @see Historique#valeurs
	*/ 
	public Historique(String code) {
		super();
		this.code = code;
	}	
	
	
	/**
	* Methode qui calcule notre historique en baseCent avec 
	* comme reference la date la plus ancienne de l'historique (par rapport a la valeur de fermeture)
	*
	* @return TreeMap contenant pour chaque date la valeur en BaseCent de notre cours.
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	*/ 
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
	
	
	/**
	* Methode qui calcule notre historique en baseCent avec 
	* comme reference la date passee en parametre (par rapport a la valeur de fermeture)
	* 
	* @param dateReference qui correspond a la date voulue pour reference
	*
	* @return TreeMap contenant pour chaque date la valeur en BaseCent de notre cours.
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	*/ 
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
	
	
	/**
	* Methode qui calcule l'esperance de rendement de notre historique
	* grace a une moyenne geometrique des rendements
	*
	* @return Double correspondant a l'esperance de rendement
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	*/ 
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
		rendement=Math.pow(rendement,1.0/nb)-1;
		return rendement;
	}
	
	
	/**
	* Methode qui calcule la variance des rendements de notre historique
	*
	* @return Double correspondant a variance des rendements
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	* @see Historique#calculEsperanceRendement()
	*/ 
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
	
	
	/**
	* Methode qui calcule la covariance des rendements de notre historique
	* avec un deuxieme historique
	* 
	* @param h2 correspond au deuxieme historique qui sert de point de comparaison
	*
	* @return Double correspondant a la covariance des rendements
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	* @see Historique#calculEsperanceRendement()
	*/ 
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
		//	System.out.print(debut);
			if(h2.getValeurs().get(debut)==null)
				debut=null;
		}
		GregorianCalendar fin=null;
		while(it.hasNext()) { 
			//System.out.println("test");

			//System.out.println("test1");
			//date de fin communes aux deux
			while(fin==null && it.hasNext()){
				fin=(GregorianCalendar) it.next();
			//	System.out.println("jour "+fin.get(Calendar.DAY_OF_MONTH)+"mois"+fin.get(Calendar.MONTH));
				if(h2.getValeurs().get(fin)==null)
					fin=null;
			}
			if(fin!=null){
			Double rendementJourh1=(getFermetureJours(debut)-getFermetureJours(fin))/getFermetureJours(debut);
			Double rendementJourh2=(h2.getFermetureJours(debut)-h2.getFermetureJours(fin))/h2.getFermetureJours(debut);
			covariance+=(rendementJourh1-rendementh1)*(rendementJourh2-rendementh2);
			debut=fin;
			fin=null;
			nb++;}
		}
		covariance/=nb;
		return covariance;
	}
	
	
	/**
	* Methode qui calcule l'esperance de notre historique
	* grace a une moyenne geometrique des rendements
	*
	* @return Double correspondant a l'esperance 
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	*/ 
	public Double calculEsperance(){
		Iterator<GregorianCalendar> it=valeurs.keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
		Double esperance=1.0;
		int nb=0;
		while(it.hasNext()) { 
			GregorianCalendar key=(GregorianCalendar) it.next();
			esperance+=getFermetureJours(key);
			nb++;
		}
		esperance/=nb;
		return esperance;
	}
	
	
	/**
	* Methode qui calcule la variance de notre historique
	*
	* @return Double correspondant a la variance 
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	* @see Historique#calculEsperance()
	*/ 
	public Double calculVariance(){
		Iterator<GregorianCalendar> it=valeurs.keySet().iterator(); // on cree un iterator sur les clés de ton hashmap
		//Moyenne géométrique mieux qu'arithmétique ici ??
		Double esperance=calculEsperance();
		Double variance=0.0;
		int nb=0;
		while(it.hasNext()) { 
			GregorianCalendar key=(GregorianCalendar) it.next();
			Double valeurJour=getFermetureJours(key);
			variance+=(valeurJour-esperance)*(valeurJour-esperance);
			nb++;
		}
		variance/=nb;
		return variance;
	}
	
	
	/**
	* Methode qui permet de calculer la moyenne mobile simple sur une periode donnee
	* 
	* @param periode qui correspond la periode sur laquelle on veut les moyennes mobiles
	*
	* @return TreeMap qui correspond a le cours du jour ainsi que la moyenne mobile 
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	*/ 
	public TreeMap<GregorianCalendar, Vector<Double>> calculMoyenneMobileSimple(Integer periode){
		Vector<Double> temp = new Vector<Double>();
		TreeMap<GregorianCalendar, Vector<Double>> resultat = new TreeMap<GregorianCalendar, Vector<Double>>();
		
		if (periode > valeurs.size()) {
			return resultat; // erreur periode trop grande ou alors on fait une moyenne par defaut..?
		}
		
		Iterator<GregorianCalendar> it = valeurs.keySet().iterator();
		if (it.hasNext()) {
			GregorianCalendar date = new GregorianCalendar() ;
			for (int i=0; i<periode; i++) {
				date = (GregorianCalendar) it.next();
				temp.add(getFermetureJours(date));
			}
			
			Vector<Double> val = new Vector<Double>();
			Double moyenne = 0.0;
			for (Double v : temp) {
				moyenne = moyenne + v;
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
				Vector<Double> vals = new Vector<Double>();
				vals.add(dernier);
				vals.add(moyenne/periode);
				resultat.put(date, vals);
			}
		}
		
		return resultat;
	}
	
	/**
	* Methode qui permet de calculer les bandes de bollinger
	* par defaut avec une periode de 20 et 2 x l'ecart type
	*
	* @return TreeMap qui correspond aux bandes de bollinger
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	*/ 
	public TreeMap<GregorianCalendar, Vector<Double>> calculBollinger() {
		TreeMap<GregorianCalendar, Vector<Double>> resultat = new TreeMap<GregorianCalendar, Vector<Double>>();
		TreeMap<GregorianCalendar, Vector<Double>> mms_tree = calculMoyenneMobileSimple(20);
		int n_ecartType = 2;
	
		Iterator<GregorianCalendar> it = mms_tree.keySet().iterator();
		while (it.hasNext()) {
			GregorianCalendar date = (GregorianCalendar) it.next();
			Vector<Double> valeurs = new Vector<Double>(); // le cours, la mm, la bande basse, la bande haute
			valeurs.add(mms_tree.get(date).get(0));
			Double mms = mms_tree.get(date).get(1);	
			valeurs.add(mms);
			Double variance=calculVarianceBollinger(20, date, mms);
			if(variance!=0){
			valeurs.add(mms-n_ecartType*Math.sqrt(variance));
			valeurs.add(mms+n_ecartType*Math.sqrt(variance));
			resultat.put(date, valeurs);}
		}
		return resultat;
	}
	
	
	/**
	* Methode qui calcule la variance de notre historique
	*
	* @return Double correspondant a la variance 
	* 
	* @see Historique#valeurs
	* @see Historique#getFermetureJours(GregorianCalendar)
	* @see Historique#calculEsperance()
	*/ 
	public Double calculVarianceBollinger(int periode, GregorianCalendar debut, Double mms){
		//Moyenne géométrique mieux qu'arithmétique ici ??
		Double variance=0.0;		
		if (periode > valeurs.size()) {
			return 0.0 ; // erreur periode trop grande ou alors on fait une moyenne par defaut..?
		}
		
		Iterator<GregorianCalendar> it = valeurs.keySet().iterator();
		int cpt=0;
		boolean b=false;
		while (it.hasNext() && b==false) {
			cpt++;
			GregorianCalendar date = it.next() ;
			if( (date.get(Calendar.MONTH)==debut.get(Calendar.MONTH)) && (date.get(Calendar.YEAR)==debut.get(Calendar.YEAR)) && (date.get(Calendar.DAY_OF_MONTH)==debut.get(Calendar.DAY_OF_MONTH)))  
				b=true;
		}
		
		it = valeurs.keySet().iterator();
		int i=0;
		if (cpt-20>0){
		while(i<cpt-20 && it.hasNext()){
			it.next();
			i++;
		}
		int j=0;
		while(j<20 && it.hasNext()){
			GregorianCalendar key=(GregorianCalendar) it.next();
			Double valeurJour=getFermetureJours(key);
			variance+=(valeurJour-mms)*(valeurJour-mms);
			j++;
		}	
		variance/=20;}
		return variance;
		
		
	}
	/**
	* Retourne le code de l'historique.
	*
	* @return le code de l'historique.
	* 
	* @see Historique#code
	*/ 
	public String getCode() {
		return code;
	}
	
	
	/**
	* Met a jour le code de l'historique.
	*
	* @param code , nouveau code de l'historique.
	*
	* @see Historique#code 
	*/ 
	public void setCode(String code) {
		this.code = code;
	}
	
	
	/**
	* Retourne les valeurs de l'historique.
	*
	* @return les valeurs de l'historique
	* 
	* @see Historique#valeurs
	*/ 
	public TreeMap<GregorianCalendar, Vector<Double>> getValeurs() {
		return valeurs;
	}

	
	/**
	* Met a jour les valeurs de l'historique.
	*
	* @param valeurs , nouvelles valeurs de l'historique
	*
	* @see Historique#valeurs
	*/ 
	public void setValeurs(TreeMap<GregorianCalendar, Vector<Double>> valeurs) {
		this.valeurs = valeurs;
	}
	
	
	/**
	* Retourne les valeurs de l'historique pour une date donnee.
	* 
	* @param date a laquelle on veut les valeurs
	*
	* @return les valeurs de l'historique
	* 
	* @see Historique#valeurs
	*/ 
	public Vector<Double> getCoursJours(GregorianCalendar date){		
		return getValeurs().get(date);
	}
	
	
	/**
	* Retourne la valeur d'ouverture de l'historique a une date donnee.
	* 
	* @param date a laquelle on veut la valeur
	*
	* @return la valeur de l'historique
	* 
	* @see Historique#valeurs
	*/ 
	public Double getOuvertureJours(GregorianCalendar date){
		return getValeurs().get(date).get(0);
	}
	
	
	/**
	* Retourne la valeur haute de l'historique a une date donnee.
	* 
	* @param date a laquelle on veut la valeur
	* 
	* @return la valeur de l'historique
	* 
	* @see Historique#valeurs
	*/ 
	public Double getHautJours(GregorianCalendar date){
		return getValeurs().get(date).get(1);
	}
	
	
	/**
	* Retourne la valeur basse de l'historique a une date donnee.
	*  
	* @param date a laquelle on veut la valeur
	*
	* @return la valeur de l'historique
	* 
	* @see Historique#valeurs
	*/ 
	public Double getBasJours(GregorianCalendar date){
		return getValeurs().get(date).get(2);
	}
	
	
	/**
	* Retourne la valeur de fermeture de l'historique a une date donnee.
	*  
	* @param date a laquelle on veut la valeur
	*
	* @return la valeur de l'historique
	* 
	* @see Historique#valeurs
	*/ 
	public Double getFermetureJours(GregorianCalendar date){
		return getValeurs().get(date).get(3);
	}
	
	
	/**
	* Retourne la volume de l'historique a une date donnee.
	* 
	* @param date a laquelle on veut la valeur
	*
	* @return la valeur de l'historique
	* 
	* @see Historique#valeurs
	*/ 
	public Double getVolumeJours(GregorianCalendar date){
		return getValeurs().get(date).get(4);
	}
	
	
	/**
	* Retourne la valeur ajustee de l'historique a une date donnee.
	*  
	* @param date a laquelle on veut la valeur
	*
	* @return la valeur de l'historique
	* 
	* @see Historique#valeurs
	*/ 
	public Double getAdjFermetureJours(GregorianCalendar date){
		return getValeurs().get(date).get(5);
	}

}
