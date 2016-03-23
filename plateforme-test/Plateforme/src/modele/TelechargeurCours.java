package modele;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.Vector;

/**
* Classe TelechargeurCours permettant de telecharger des cours de la bourse
* sur Internet avec l'API Yahoo Finance. 
*
* @author  Celine Chaugny & Damien Pointin 
*/
public class TelechargeurCours {	

	/**
	* L'historique qui va etre rempli avec le TelechargeurCours.
	*
	* @see TelechargeurCours#getHistorique()
	* @see TelechargeurCours#setHistorique(Historique)
	*/ 
	private Historique historique;
	
	/**
	 * Constructeur TelechargeurCours qui telecharge le cours et cree donc l'historique.
	 * <p>
	 * Avec les parametres code, debut, fin
	 * correspondant aux entrees
	 * </p>
	 * 
	 * @param code, code du cours a telecharger
	 * @param debut, date du debut du telechargement
	 * @param fin, date de fin du telechargement
	 */
	public TelechargeurCours(String code, GregorianCalendar debut, GregorianCalendar fin){
		
		historique = new Historique(code);
		
		//Construisons l'url de notre page
		//Chaque url permettant d'accéder aux cours de la bourse est construit de la même manière
		String url="http://real-chart.finance.yahoo.com/table.csv?"+
		           "s="+code  //code correspondant à l'action
		           +"&a="+debut.get(Calendar.MONTH) //mois de debut 00=janvier, 01=fevrier, 02=mars...
		           +"&b="+debut.get(Calendar.DAY_OF_MONTH) //jour de debut
		           +"&c="+debut.get(Calendar.YEAR) //annee de debut
		           +"&d="+fin.get(Calendar.MONTH)
		           +"&e="+fin.get(Calendar.DAY_OF_MONTH)
		           +"&f="+fin.get(Calendar.YEAR)
		           +"&g=d" //On veut par jour
		           +"&ignore=.csv"; //au format csv;
		System.out.println(url);
		
		//Il ne nous reste plus qu'à nous connecter a l'url et copier les données
		try
		{
			URL yahooUrl = new URL(url);
			URLConnection donnee = yahooUrl.openConnection();
			Scanner entree = new Scanner(donnee.getInputStream());
	
			//La première ligne est juste l'en-tête
			if(entree.hasNext()) //au cas ou mauvaise lecture
				entree.nextLine();
		
			TreeMap<GregorianCalendar, Vector<Double>> ht = new TreeMap<GregorianCalendar, Vector<Double>>();
			GregorianCalendar date_calendar = null;
			Vector<Double> v = null;
			
			while(entree.hasNextLine()){
				String ligne = entree.nextLine();
				
				String[] tab = ligne.split(",");
		
				Date date = null;
			    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			    try {
			    	date = simpleDateFormat.parse(tab[0]);
			    } catch (ParseException e) {
			    	e.printStackTrace();
			    }		
			    
			    date_calendar = new GregorianCalendar();
			    date_calendar.setTime(date);

			    v = new Vector<Double>();
			    v.add(Double.parseDouble(tab[1]));
				v.add(Double.parseDouble(tab[2]));
				v.add(Double.parseDouble(tab[3]));
				v.add(Double.parseDouble(tab[4]));
				v.add(Double.parseDouble(tab[5]));
				v.add(Double.parseDouble(tab[6]));
				
				ht.put(date_calendar, v);
			}
			
			entree.close();
			
			historique.setValeurs(ht);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}

	/**
	 * Retourne l'historique du telechargeurCours.
	 * 
	 * @return l'historique du telechargeurCours.
	 */
	public Historique getHistorique() {
		return historique;
	}

	/**
	 * Met a jour l'historique du telechargeurCours.
	 * 
	 * @param historique, nouvel historique du telechargeurCours
	 */
	public void setHistorique(Historique historique) {
		this.historique = historique;
	}
}
