package modele;

import java.util.GregorianCalendar;

/**
* Classe Option representant une option du jeu 
* herite d'ObjetFinancier
* 
* @see ObjetFinancier
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Option extends ObjetFinancier{

	/**
	 * L'idOption de l'option.
	 *
	 * @see Option#Option(Integer,TypeOption,TypePosition,GregorianCalendar,Double,Double,Titre)
	 * @see Option#getIdOption()
	 * @see Option#setIdOption(Integer)
	 */ 
	private Integer idOption;

	/**
	 * Le type de l'option.
	 *
	 * @see TypeOption
	 * @see Option#Option(Integer,TypeOption,TypePosition,GregorianCalendar,Double,Double,Titre)
	 * @see Option#getTypeOption()
	 * @see Option#setTypeOption(TypeOption)
	 */ 
	private TypeOption type;
	
	/**
	 * La position de l'option.
	 *
	 * @see TypePosition
	 * @see Option#Option(Integer,TypeOption,TypePosition,GregorianCalendar,Double,Double,Titre)
	 * @see Option#getTypePosition()
	 * @see Option#setTypePosition(TypePosition)
	 */ 	
	private TypePosition position;
	
	/**
	 * La maturite de l'option.
	 *
	 * @see Option#Option(Integer,TypeOption,TypePosition,GregorianCalendar,Double,Double,Titre)
	 * @see Option#getMaturite()
	 * @see Option#setMaturite(GregorianCalendar)
	 */ 		
	private GregorianCalendar maturite;
	
	/**
	 * Le strike de l'option.
	 *
	 * @see Option#Option(Integer,TypeOption,TypePosition,GregorianCalendar,Double,Double,Titre)
	 * @see Option#getStrike()
	 * @see Option#setStrike(Double)
	 */ 	
	private Double strike;
	
	/**
	 * La prime de l'option.
	 *
	 * @see Option#Option(Integer,TypeOption,TypePosition,GregorianCalendar,Double,Double,Titre)
	 * @see Option#getPrime()
	 * @see Option#setPrime(Double)
	 */ 		
	private Double prime;
	
	/**
	 * Le titre de l'option.
	 *
	 * @see Titre
	 * @see Option#Option(Integer,TypeOption,TypePosition,GregorianCalendar,Double,Double,Titre)
	 * @see Option#getTitre()
	 * @see Option#setTitre(Titre)
	 */ 		
	private Titre titre;	

	
	/**
	 * Constructeur Option.
	 * <p>
	 * Avec les parametres idOption, type, position, maturite, strike, prime et titre
	 * correspondant aux entrees
	 * </p>
	 * 
	 * @param idOption
	 * @param type
	 * @param position
	 * @param maturite
	 * @param strike
	 * @param prime
	 * @param titre
	 * 
     * @see ObjetFinancier#ObjetFinancier(int)
	 */
	public Option(Integer idOption, TypeOption type, TypePosition position, GregorianCalendar maturite, Double strike, Double prime,
			Titre titre) {
		super(0);
		this.idOption = idOption;
		this.type = type;
		this.position = position;
		this.maturite = maturite;
		this.strike = strike;
		this.prime = prime;
		this.titre = titre;
	}


	/**
	 * Redefinition de la methode getPrix d'ObjetFinancier
	 * calcul du prix de l'option
	 * 
	 * @see modele.ObjetFinancier#getPrix()
	 */
	public double getPrix(){
		// methode non finie
		return 0.0;
	}
	

	/**
	 * Retourne l'idOption de l'option.
	 * 
	 * @return l'idOption de l'option.
	 */
	public Integer getIdOption() {
		return idOption;
	}
	
	/**
	 * Met a jour l'idOption de l'option.
	 * 
	 * @param idOption nouvel idOption de l'option.
	 */
	public void setIdOption(Integer idOption) {
		this.idOption = idOption;
	}
	
	/**
	 * Retourne le type de l'option.
	 * 
	 * @return le type de l'option.
	 */	
	public TypeOption getType() {
		return type;
	}
	
	/**
	 * Met a jour le type de l'option.
	 * 
	 * @param type nouveau type de l'option.
	 */
	public void setType(TypeOption type) {
		this.type = type;
	}
	
	/**
	 * Retourne la position de l'option.
	 * 
	 * @return la position de l'option.
	 */	
	public TypePosition getPosition() {
		return position;
	}
	
	/**
	 * Met a jour la position de l'option.
	 * 
	 * @param position nouvelle position l'option.
	 */	
	public void setPosition(TypePosition position) {
		this.position = position;
	}
	
	/**
	 * Retourne la maturite de l'option.
	 * 
	 * @return la maturite de l'option.
	 */		
	public GregorianCalendar getMaturite() {
		return maturite;
	}
	
	/**
	 * Met a jour la maturite de l'option.
	 * 
	 * @param maturite nouvelle maturite l'option.
	 */	
	public void setMaturite(GregorianCalendar maturite) {
		this.maturite = maturite;
	}
	
	/**
	 * Retourne le strike de l'option.
	 * 
	 * @return le strike de l'option.
	 */
	public Double getStrike() {
		return strike;
	}
	
	/**
	 * Met a jour le strike de l'option.
	 * 
	 * @param strike nouveau strike de l'option.
	 */
	public void setStrike(Double strike) {
		this.strike = strike;
	}
	
	/**
	 * Retourne la prime de l'option.
	 * 
	 * @return la prime de l'option.
	 */		
	public Double getPrime() {
		return prime;
	}
	
	/**
	 * Met a jour la prime de l'option.
	 * 
	 * @param prime nouvelle prime l'option.
	 */	
	public void setPrime(Double prime) {
		this.prime = prime;
	}
	
	/**
	 * Retourne le titre de l'option.
	 * 
	 * @return le titre de l'option.
	 */	
	public Titre getTitre() {
		return titre;
	}
	
	/**
	 * Met a jour le titre de l'option.
	 * 
	 * @param titre nouveau titre l'option.
	 */
	public void setTitre(Titre titre) {
		this.titre = titre;
	}

}
