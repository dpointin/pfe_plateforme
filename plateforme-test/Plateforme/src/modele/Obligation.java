package modele;

import java.util.GregorianCalendar;

public class Obligation {

	private String emetteur;
	private Double prix;
	private Double tauxInterets;
	private Integer nombreDisponible;
	private GregorianCalendar dateFin;
	
	public Obligation(String emetteur, Double prix, Double tauxInterets, Integer nombreDisponible,
			GregorianCalendar dateFin) {
		super();
		this.emetteur = emetteur;
		this.prix = prix;
		this.tauxInterets = tauxInterets;
		this.nombreDisponible = nombreDisponible;
		this.dateFin = dateFin;
	}
		
	public String getEmetteur() {
		return emetteur;
	}
	public void setEmetteur(String emetteur) {
		this.emetteur = emetteur;
	}
	public Double getPrix() {
		return prix;
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
