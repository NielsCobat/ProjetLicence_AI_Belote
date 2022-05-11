package game;

import java.util.LinkedList;
import java.util.List;
import game.Table;


import assets.Valeur;

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
		boolean roi = false;
		boolean dame = false;
		for (Carte c : Table.ensCartes) {
			if (c.getCouleur() == Table.mancheCourante.getAtout() && c.getValeur() == Valeur.Roi) {
				if (main.contains(c)) roi = true;
			}
			else if (c.getCouleur() == Table.mancheCourante.getAtout() && c.getValeur() == Valeur.Dame) {
				if (main.contains(c)) dame = true;
			}
		}
		return roi && dame;
	}
	
	/**
	 * Si le pli est vide on peut mettre n'importe quelle carte.
	 * Si on a la couleur demandée, on doit la jouer, si cette couleur est atout, alors si l'on a un atout de valeur supérieure,
	 * on doit le jouer, sinon on peut jouer n'importe quel autre atout
	 * Si on a pas la couleur demandée, alors si notre partenaire est maître, on peut jouer n'importe quelle carte,
	 * sinon si on a de l'atout, on doit le mettre, si ce n'est pas le cas on peut tout jouer.
	 * @param carte La carte dont on s'interroge sur la légalité
	 * @return
	 */
	boolean isLegalMove(Carte carte) {
		Manche manche = Table.mancheCourante;
		Pli pli = manche.getPli(manche.getNbPlis());
		if (pli.getNbCarte() == 0) {
			return true;
		}
		else {
			if (pli.getCouleurDemandee() != manche.getAtout()) {
				//TODO
			}
			
		}
		return false;
	}
	
	/**
	 * 
	 * @param carte La carte jouée
	 */
	void joueCoup(Carte carte) {
		Table.mancheCourante.getPli(Table.mancheCourante.getNbPlis()).addCarte(carte);
		main.remove(carte);
	}
	
	boolean passe() {
		return false;
	}
	
	boolean prend() {
		return false;
	}
}
