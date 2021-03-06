package modele;

/**
* Classe Joueur representant un joueur du jeu 
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class Joueur {
	/**
	* Le login du Joueur.
	*
	* @see Joueur#Joueur(String, String, Portefeuille)
	* @see Joueur#getLogin()
	* @see Joueur#setLogin(String)
	*/ 
	private String login;
	
	
	/**
	* Le mot de passe du Joueur.
	*
	* @see Joueur#Joueur(String, String, Portefeuille)
	* @see Joueur#getMotDePasse()
	* @see Joueur#setMotDePasse(String)
	*/ 
	private String motDePasse;
	
	
	/**
	* Le portefeuille du Joueur.
	* Voir la classe Portefeuille pour plus d'information sur le portefeuille
	* Possibilite d'ajouter ou supprimer des actions du portefeuille
	*
	* @see Portefeuille
	* @see Joueur#Joueur(String, String, Portefeuille)
	* @see Joueur#getPortefeuille()
	* @see Joueur#setPortefeuille(Portefeuille)
	* @see Joueur#ajouterAction(Action, int)
	* @see Joueur#supprimerAct(Action, int)
	*/ 
	private Portefeuille portefeuille;
	
	
	/**
	* Constructeur Joueur.
	* <p>
	* Avec les paramètres login, motDePasse et portefeuille
	* correspondant aux entrees
	* </p>
	*
	* @param login
	* Le login du joueur.
	* @param motDePasse
	* Le mot de passe du joueur.
	* @param portefeuille
	* Le portefeuille du joueur
	*
	* @see Joueur#login
	* @see Joueur#motDePasse
	* @see Joueur#portefeuille
	*/ 	
	public Joueur(String login, String motDePasse, Portefeuille portefeuille) {
		super();
		this.login = login;
		this.motDePasse=motDePasse;
		this.portefeuille = portefeuille;
	}
	
	
	/**
	* Constructeur Joueur.
	* <p>
	* Avec les paramètres login, motDePasse 
	* correspondant aux entrees.
	* Instanciation d'un portefeuille vide
	* </p>
	*
	* @param login
	* Le login du joueur.
	* @param motDePasse
	* Le mot de passe du joueur.
	*
	* @see Joueur#login
	* @see Joueur#motDePasse
	* @see Joueur#portefeuille
	*/ 	
	public Joueur(String login, String motDePasse) {
		super();
		this.login = login;
		this.motDePasse=motDePasse;
		this.portefeuille = new Portefeuille(10000.0);
	}
	
	
	/**
	* Constructeur Joueur.
	* <p>
	* Aucun paramètre en entree.
	* </p>
	*
	* @see Joueur#login
	* @see Joueur#motDePasse
	* @see Joueur#portefeuille
	*/ 	
	public Joueur() {
		this("","");
	}

	
	/**
	* Retourne le score du joueur.
	*
	* @return score du joueur.
	*/ 
	public Double calculScore(){
		return portefeuille.calculArgent();
	}
	
	
	/**
	* Effectue l'achat.
	* 
	* @param o objet financier
	* @param quantite de l'objet qu'on achete
	*
	* @return boolean si l'achat a ete effectue.
	*/ 
	public boolean achat(ObjetFinancier o, int quantite ){
		return portefeuille.acheter(o, quantite);
	}
	
	
	/**
	* Effectue la vente.
	* 
	* @param o objet financier
	* @param quantite de l'objet qu'on vend
	*
	* @return boolean si la vente a ete effectue.
	*/ 
	public boolean vendre(ObjetFinancier o, int quantite){
		return portefeuille.vendre(o, quantite);
	}

	
	/**
	* supprime le portefeuille
	*/ 
	public void supprimerPortefeuille(){
		this.setPortefeuille(new Portefeuille());
	}
	
	/**
	* Retourne le portefeuille du joueur.
	*
	* @return portefeuille du joueur.
	*/ 
	public Portefeuille getPortefeuille() {
		return portefeuille;
	}
	
	
	/**
	* Retourne le login du joueur.
	*
	* @return login du joueur.
	*/ 
	public String getLogin() {
		return login;
	}
	
	
	/**
	* Retourne le mot de passe du joueur.
	*
	* @return mot de passe du joueur.
	*/ 
	public String getMotDePasse() {
		return motDePasse;
	}
	
	
	/**
	* Met a jour le login du joueur.
	*
	* @param login
	* Le nouveau login du joueur.
	*
	*/ 
	public void setLogin(String login) {
		this.login = login;
	}
	
	
	/**
	* Met a jour le motDePasse du joueur.
	*
	* @param motDePasse
	* Le nouveau motDePasse du joueur.
	*
	*/ 
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	
	/**
	* Met a jour le portefeuille du joueur.
	*
	* @param portefeuille
	* Le nouveau portefeuille du joueur.
	*
	* @see Portefeuille
	*/ 
	public void setPortefeuille(Portefeuille portefeuille) {
		this.portefeuille = portefeuille;
	}
	
}
