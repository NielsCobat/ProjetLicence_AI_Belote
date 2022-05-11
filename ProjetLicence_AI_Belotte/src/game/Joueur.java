package game;

import java.util.LinkedList;
import java.util.List;

public class Joueur {

	String nom;
	int id;
	boolean maitre;
	int idPartenaire;
	LinkedList<Carte> main;
	
	public Joueur() {
		nom="";
		id=0;
		idPartenaire=0;
		main= new LinkedList<Carte>();
	}
	
	public Joueur(String nom, int id,int partenaire){
		this.nom = nom;
		this.id = id;
		this.idPartenaire = partenaire;
		this.main = new LinkedList<Carte>(); //A la creation main forcement vide
	}
	
	boolean hasBelote() {
		return false;
	}
	
	
	boolean isLegalMove() {
		return false;
	}
	
	void joueCoup(Carte carte) {
		
	}
	
	boolean passe() {
		return false;
	}
	
	boolean prend() {
		return false;
	}
}
