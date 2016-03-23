package modele;

/**
* Classe abstraite Titre representant une titre du jeu 
* herite de la classe ObjetFinancier
* 
* @see ObjetFinancier
*
* @author  Celine Chaugny & Damien Pointin 
*/
public abstract class Titre extends ObjetFinancier{

	/**
	* Le code du titre.
	*
	* @see Titre#Titre(String, String, Integer)
	* @see Titre#getCode()
	* @see Titre#setCode(String)
	*/ 
	private String code ;

	/**
	* Le libelle du titre.
	*
	* @see Titre#Titre(String, String, Integer)
	* @see Titre#getLibelle()
	* @see Titre#setLibelle(String)
	*/ 
	private String libelle;
	
	/**
	* L'historique du titre.
	*
	* @see Titre#getHistorique()
	* @see Titre#setHistorique(Historique)
	*/ 
	protected Historique historique;

	/**
	* Constructeur Titre.
	* <p>
	* Avec les parametres code, libelle, nombreDisponible
	* correspondant aux entrees
	* </p>
	*
	* @param code du titre
	* @param libelle du titre
	* @param nombreDisponible du titre
	* 
	* @see Historique#Historique(String)
	*/
	public Titre(String code, String libelle, Integer nombreDisponible) {
		super(nombreDisponible);
		this.code = code;
		this.libelle = libelle;
		this.historique = new Historique(code);
	}
	
	
	/**
	 * Retourne le libelle du titre.
	 * 
	 * @return le libelle du titre.
	 */
	public String getLibelle() {
		return libelle;
	}
	
	/**
	 * Met a jour le libelle du titre.
	 * 
	 * @param libelle, nouveau libelle du titre
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	/**
	 * Retourne le code du titre.
	 * 
	 * @return le code du titre.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Met a jour le code du titre.
	 * 
	 * @param code, nouveau code du titre
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Retourne l'historique du titre.
	 * 
	 * @return l'historique du titre.
	 */
	public Historique getHistorique() {
		return historique;
	}
	
	/**
	 * Met a jour l'historique du titre.
	 * 
	 * @param historique, nouvel historique du titre
	 */
	public void setHistorique(Historique historique) {
		this.historique = historique;
	}

	/**
	 * Retourne le prix du titre qui est par convention le dernier cours de fermeture.
	 * 
	 * @return le prix du titre (dernier cours de fermeture).
	 */
	public double getPrix(){
		return historique.getFermetureJours(historique.getValeurs().lastKey());
	}

	/**
	 * Redefinition de la méthode equals pour tester si deux titres sont identiques.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Titre ) && ((Titre) obj).getCode().equals(code);
	}
}
