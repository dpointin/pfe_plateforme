package modele;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
* Classe Obligation representant une obligation du jeu 
* herite de la classe ObjetFinancier
* 
* @see ObjetFinancier
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Obligation extends ObjetFinancier{
	/**
	* L'emetteur de l'obligation.
	*
	* @see Obligation#Obligation(String, Double, Double, Integer, GregorianCalendar)
	* @see Obligation#getEmetteur()
	* @see Obligation#setEmetteur(String)
	*/
	private String emetteur;
	
	/**
	* Le prix de l'obligation.
	*
	* @see Obligation#Obligation(String, Double, Double, Integer, GregorianCalendar)
	* @see Obligation#getPrix()
	* @see Obligation#setPrix(Double)
	*/ 
	private Double prix;
	
	/**
	* Le tauxInteret de l'obligation.
	*
	* @see Obligation#Obligation(String, Double, Double, Integer, GregorianCalendar)
	* @see Obligation#getTauxInteret()
	* @see Obligation#setTauxInteret(Double)
	*/ 
	private Double tauxInteret;
	
	/**
	* La dateFin de l'obligation.
	*
	* @see Obligation#Obligation(String, Double, Double, Integer, GregorianCalendar)
	* @see Obligation#getDateFin()
	* @see Obligation#setDateFin(GregorianCalendar)
	*/ 	
	private GregorianCalendar dateFin;
	
	/**
	* Constructeur Obligation.
	* <p>
	* Avec les parametres emetteur, prix, tauxInteret, nombreDisponible et dateFin
	* correspondant aux entrees
	* </p>
	*
	* @param emetteur de l'obligation
	* @param prix de l'obligation
	* @param tauxInteret de l'obligation
	* @param nombreDisponible de l'obligation
	* @param dateFin de l'obligation
	* 
	* @see ObjetFinancier#ObjetFinancier(int)
	*/ 	
	public Obligation(String emetteur, Double prix, Double tauxInteret, Integer nombreDisponible,
			GregorianCalendar dateFin) {
		super(nombreDisponible);
		this.emetteur = emetteur;
		this.prix = prix;
		this.tauxInteret = tauxInteret;
		this.dateFin = dateFin;
	}

	/**
	* toString correspondant a l'obligation.
	*
	* @return String correspondant a notre obligation.
	*/ 
	public String toString(){
		return "OBLIGATION;"+getEmetteur()+" date de fin : "+dateFin.get(Calendar.DAY_OF_MONTH)+"/"+(dateFin.get(Calendar.MONTH)+1)+"/"+dateFin.get(Calendar.YEAR);
	}
	
	/**
	 * Redefinition de la méthode equals pour tester si deux obligations sont identiques.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Obligation){
			boolean date = (((Obligation) obj).getDateFin().get(Calendar.YEAR)==(dateFin.get(Calendar.YEAR)) && ((Obligation) obj).getDateFin().get(Calendar.MONTH)==(dateFin.get(Calendar.MONTH)) && ((Obligation) obj).getDateFin().get(Calendar.DAY_OF_MONTH)==(dateFin.get(Calendar.DAY_OF_MONTH)));
			return  date && ((Obligation) obj).getEmetteur().equals(emetteur) ;
		}
		else
			return false;
	}

	/**
	* Retourne l'emetteur de l'obligation.
	*
	* @return l'emetteur de l'obligation.
	*/ 
	public String getEmetteur() {
		return emetteur;
	}
	
	/**
	* Met a jour l'emetteur de l'obligation.
	*
	* @param emetteur nouvel emetteur de l'obligation.
	*/ 
	public void setEmetteur(String emetteur) {
		this.emetteur = emetteur;
	}
	
	/**
	* Retourne le prix de l'obligation.
	*
	* @return le prix de l'obligation.
	*/ 	
	public double getPrix(){
		return prix;
	}
	
	/**
	* Met a jour le prix de l'obligation.
	*
	* @param prix nouveau prix de l'obligation.
	*/ 
	public void setPrix(Double prix) {
		this.prix = prix;
	}
	
	/**
	* Retourne le tauxInteret de l'obligation.
	*
	* @return le tauxInteret de l'obligation.
	*/ 		
	public Double getTauxInteret() {
		return tauxInteret;
	}
	
	/**
	* Met a jour le tauxInteret de l'obligation.
	*
	* @param tauxInteret nouveau tauxInteret de l'obligation.
	*/ 
	public void setTauxInteret(Double tauxInteret) {
		this.tauxInteret = tauxInteret;
	}
	
	/**
	* Retourne le nombreDisponible de l'obligation.
	*
	* @return le nombreDisponible de l'obligation.
	*/ 	
	public Integer getNombreDisponible() {
		return nombreDisponible;
	}
	
	/**
	* Met a jour le nombreDisponible de l'obligation.
	*
	* @param nombreDisponible nouveau nombreDisponible de l'obligation.
	*/ 
	public void setNombreDisponible(Integer nombreDisponible) {
		this.nombreDisponible = nombreDisponible;
	}
	
	/**
	* Retourne la dateFin de l'obligation.
	*
	* @return la dateFin de l'obligation.
	*/ 	
	public GregorianCalendar getDateFin() {
		return dateFin;
	}
	
	/**
	* Met a jour la dateFin de l'obligation.
	*
	* @param dateFin nouvelle dateFin de l'obligation.
	*/ 
	public void setDateFin(GregorianCalendar dateFin) {
		this.dateFin = dateFin;
	}
	
}
