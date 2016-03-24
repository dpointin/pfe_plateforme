package modele;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
* Classe Operation representant une operation d'achat
* ou de vente d'un joueur sur un objet financier
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Operation {
	/**
	* L'objetFinancier de l'operation.
	*
	* @see Operation#Operation(ObjetFinancier, double, int)
	* @see Operation#getObjetFinancier()
	* @see Operation#setObjetFinancier(ObjetFinancier)
	*/ 
	private ObjetFinancier objetFinancier;
	
	/**
	* Le prixUnitaire de l'operation.
	*
	* @see Operation#Operation(ObjetFinancier, double, int)
	* @see Operation#getPrixUnitaire()
	* @see Operation#setPrixUnitaire(double)
	*/ 	
	private double prixUnitaire;
	
	/**
	* La quantite d'actifs jouant dans l'operation.
	*
	* @see Operation#Operation(ObjetFinancier, double, int)
	* @see Operation#getQuantite()
	* @see Operation#setQuantite(int)
	*/ 	
	private int quantite;
	
	/**
	* La date de l'operation.
	*
	* @see Operation#getDate()
	* @see Operation#setDate(GregorianCalendar)
	*/ 	
	private GregorianCalendar date;
	
	/**
	* L'id de l'operation.
	*
	* @see Operation#getId()
	* @see Operation#setId(int)
	*/ 	
	int id;
	
	/**
	* Constructeur Operation sans parametres.
	*/
	public Operation(){
		this(null,0.0,0);
	}
	
	/**
	* Constructeur Operation.
	* <p>
	* Avec les parametres objetFinancier, prixUnitaire, quantite
	* </p>
	*
	* @param objetFinancier de l'operation
	* @param prixUnitaire de l'operation
	* @param quantite de l'operation
	* 
	* @see Historique#Historique(String)
	*/
	public Operation(ObjetFinancier objetFinancier, double prixUnitaire, int quantite){
		this.objetFinancier=objetFinancier;
		this.prixUnitaire=prixUnitaire;
		this.quantite=quantite;
		this.date= new GregorianCalendar();
	}
	
	/**
	 * Redefinition de la methode toString pour ecriture dans le fichier csv
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String s=id+";";
		s+=date.get(Calendar.DATE)+"/"+(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.YEAR)+";";
		if(quantite<0)
			s+="VENTE;"+(-quantite)+";";
		else
			s+="ACHAT;"+quantite+";";
		s+=prixUnitaire+";";
		s+=objetFinancier.toString()+";";
		return s;
	}

	/**
	 * Retourne l'objetFinancier de l'operation.
	 * 
	 * @return l'objetFinancier de l'operation.
	 */
	public ObjetFinancier getObjetFinancier() {
		return objetFinancier;
	}
	
	/**
	 * Met a jour l'objetFinancier de l'operation.
	 * 
	 * @param objetFinancier, nouvel objetFinancier de l'operation.
	 */
	public void setObjetFinancier(ObjetFinancier objetFinancier) {
		this.objetFinancier = objetFinancier;
	}
	
	/**
	 * Retourne le prixUnitaire de l'operation.
	 * 
	 * @return le prixUnitaire de l'operation.
	 */	
	public double getPrixUnitaire() {
		return prixUnitaire;
	}
	
	/**
	 * Met a jour le prixUnitaire de l'operation.
	 * 
	 * @param prixUnitaire, nouveau prixUnitaire de l'operation.
	 */
	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	
	/**
	 * Retourne la quantite d'actifs de l'operation.
	 * 
	 * @return la quantite d'actifs de l'operation.
	 */	
	public int getQuantite() {
		return quantite;
	}
	
	/**
	 * Met a jour la quantite d'actifs de l'operation.
	 * 
	 * @param quantite, nouvelle quantite d'actifs de l'operation.
	 */
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	/**
	 * Retourne la date de l'operation.
	 * 
	 * @return la date de l'operation.
	 */		
	public GregorianCalendar getDate() {
		return date;
	}

	/**
	 * Met a jour la date de l'operation.
	 * 
	 * @param date, nouvelle date de l'operation.
	 */
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	
	/**
	 * Retourne l'id de l'operation.
	 * 
	 * @return l'id de l'operation.
	 */		
	public int getId() {
		return id;
	}
	
	/**
	 * Met a jour l'id de l'operation.
	 * 
	 * @param id, nouvel id de l'operation.
	 */	
	public void setId(int id){
		this.id=id;
	}
	
}
