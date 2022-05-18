package game;
import assets.Couleur;
import assets.Valeur;

public class Carte {
	
	private Couleur couleur;
	private Valeur valeur;
	int point;
	boolean estJouée;

	final static Valeur[] ordreNonAtout = {Valeur.Sept, Valeur.Huit, Valeur.Neuf, Valeur.Valet, Valeur.Dame, Valeur.Roi, Valeur.Dix, Valeur.As};
	final static Valeur[] ordreAtout = {Valeur.Sept, Valeur.Huit, Valeur.Dame, Valeur.Roi, Valeur.Dix, Valeur.As, Valeur.Neuf, Valeur.Valet};
	
	
	/*
	 * @param couleur la couleur de la carte
	 * @param valeur sa valeur ( pour un jeu de 32 cartes)
	 * @param le nombre de point qu'elle vaut
	 */
	public Carte(Couleur couleur, Valeur valeur, int point) {
		this.couleur = couleur;
		this.valeur = valeur;
		this.point = point;
	}
	
	/*
	 * @param c2 la carte à comparer 
	 * -1  this ne l'emporte pas face à c
	 * 1  this l'emporte face à c
	 */
	int compareTo(Carte c2) {
		// -1  this ne l'emporte pas face à c
		//1  this l'emporte face à c
		if (c2 != null) {
			if(this.couleur.name().equals(Table.mancheCourante.getAtout().name())) {
			if(c2.couleur.name().equals(Table.mancheCourante.getAtout().name())) {
				for(int i = 0 ; i < ordreAtout.length ; i++) {
					if(ordreAtout[i].name().equals(this.valeur.name())) {
						return -1;
					}
					else if(ordreAtout[i].name().equals(c2.valeur.name())) {
						return 1;
					}
				}
			}
			else{
				return 1;
			}
		}
		else { //this n'est pas un atout
			if(c2.couleur.name().equals(Table.mancheCourante.getAtout().name())) { //c2 est un atout
				return -1;
			}
			else if (c2.couleur.name().equals(this.getCouleur().name())) { //c2 et this sont de la même couleur
				for(int i = 0 ; i < ordreNonAtout.length ; i++) {
					if(ordreNonAtout[i].name().equals(this.valeur.name())) {
						return -1;
					}
					else if(ordreNonAtout[i].name().equals(c2.valeur.name())) {
						return 1;
					}
				}
			}
		}
		}
		
		return 1;
	}
	
	public Couleur getCouleur() {
		return this.couleur;
	}
	
	public Valeur getValeur() {
		return this.valeur;
	}
	
	public int getPoints() {
		return this.point;
	}
	
	public String toString() {
		String res = "" + this.getValeur().name() + " de " + this.getCouleur().name();
		return res;
	}
	
}
