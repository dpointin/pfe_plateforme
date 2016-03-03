package modele;

import java.util.GregorianCalendar;

public class Option {

	private Integer idOption;
	private TypeOption type;
	private TypePosition position;
	private GregorianCalendar maturite;
	private Double strike;
	private Double prime;
	private Titre titre;
	// le titre carrément ou juste le code????
	

	public Option(Integer idOption, TypeOption type, TypePosition position, GregorianCalendar maturite, Double strike, Double prime,
			Titre titre) {
		super();
		this.idOption = idOption;
		this.type = type;
		this.position = position;
		this.maturite = maturite;
		this.strike = strike;
		this.prime = prime;
		this.titre = titre;
	}



	// GETTER SETTER
	public Integer getIdOption() {
		return idOption;
	}
	public void setIdOption(Integer idOption) {
		this.idOption = idOption;
	}
	public TypeOption getType() {
		return type;
	}
	public void setType(TypeOption type) {
		this.type = type;
	}
	public TypePosition getPosition() {
		return position;
	}
	public void setPosition(TypePosition position) {
		this.position = position;
	}
	public GregorianCalendar getMaturite() {
		return maturite;
	}
	public void setMaturite(GregorianCalendar maturite) {
		this.maturite = maturite;
	}
	public Double getStrike() {
		return strike;
	}
	public void setStrike(Double strike) {
		this.strike = strike;
	}
	public Double getPrime() {
		return prime;
	}
	public void setPrime(Double prime) {
		this.prime = prime;
	}
	public Titre getTitre() {
		return titre;
	}
	public void setTitre(Titre titre) {
		this.titre = titre;
	}
	
	
}
