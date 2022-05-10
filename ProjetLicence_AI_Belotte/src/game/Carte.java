package game;
import assets.Couleur;
import assets.Valeur;

public class Carte {
	
	Couleur couleur;
	Valeur valeur;
	int point;
	
	public Carte(Couleur couleur, Valeur valeur, int point) {
		this.couleur = couleur;
		this.valeur = valeur;
		this.point = point;
	}
	
	boolean isLegalMove() {
		return false;
	}
	
	int compareTo(Carte c2) {
		return -1;
	}
}
