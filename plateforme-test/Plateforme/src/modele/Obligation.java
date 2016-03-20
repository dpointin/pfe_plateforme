package modele;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class Obligation extends ObjetFinancier{

	private String emetteur;
	private Double prix;
	private Double tauxInterets;
	private GregorianCalendar dateFin;
	
	public Obligation(String emetteur, Double prix, Double tauxInterets, Integer nombreDisponible,
			GregorianCalendar dateFin) {
		super(nombreDisponible);
		this.emetteur = emetteur;
		this.prix = prix;
		this.tauxInterets = tauxInterets;
		this.dateFin = dateFin;
	}
		
	
	public double getPrix(){
		return prix;
	}
	
	public String toString(){
		return "OBLIGATION;"+getEmetteur()+" date de fin : "+getDateFin().get(Calendar.DATE)+"/"+getDateFin().get(Calendar.MONTH+1)+"/"+getDateFin().get(Calendar.YEAR);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Obligation){
			/*System.out.println(((Obligation) obj).getDateFin().get(Calendar.YEAR)==(dateFin.get(Calendar.YEAR)));
			System.out.println(((Obligation) obj).getDateFin().get(Calendar.YEAR));
			System.out.println((dateFin.get(Calendar.YEAR)));
			System.out.println(((Obligation) obj).getDateFin().get(Calendar.MONTH)==(dateFin.get(Calendar.MONTH)));
			System.out.println(((Obligation) obj).getDateFin().get(Calendar.MONTH));
			System.out.println((dateFin.get(Calendar.MONTH)));
			System.out.println(((Obligation) obj).getDateFin().get(Calendar.DAY_OF_MONTH)==(dateFin.get(Calendar.DAY_OF_MONTH)));
			System.out.println(((Obligation) obj).getDateFin().get(Calendar.DAY_OF_MONTH));
			System.out.println((dateFin.get(Calendar.DAY_OF_MONTH)));*/
			boolean date = (((Obligation) obj).getDateFin().get(Calendar.YEAR)==(dateFin.get(Calendar.YEAR)) && ((Obligation) obj).getDateFin().get(Calendar.MONTH)==(dateFin.get(Calendar.MONTH)) && ((Obligation) obj).getDateFin().get(Calendar.DAY_OF_MONTH)==(dateFin.get(Calendar.DAY_OF_MONTH)));
			return  date && ((Obligation) obj).getEmetteur().equals(emetteur) ;
		}
		else
			return false;
	}
	
	//SETTER ET GETTER
	public String getEmetteur() {
		return emetteur;
	}
	public void setEmetteur(String emetteur) {
		this.emetteur = emetteur;
	}
	public void setPrix(Double prix) {
		this.prix = prix;
	}
	public Double getTauxInterets() {
		return tauxInterets;
	}
	public void setTauxInterets(Double tauxInterets) {
		this.tauxInterets = tauxInterets;
	}
	public Integer getNombreDisponible() {
		return nombreDisponible;
	}
	public void setNombreDisponible(Integer nombreDisponible) {
		this.nombreDisponible = nombreDisponible;
	}
	public GregorianCalendar getDateFin() {
		return dateFin;
	}
	public void setDateFin(GregorianCalendar dateFin) {
		this.dateFin = dateFin;
	}
	
}
