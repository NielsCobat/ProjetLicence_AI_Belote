package game;

import java.util.LinkedList;
import game.Table;
import assets.Couleur;


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
	
	
	/**
	 * 
	 * @return true si le joueur a la belote, false sinon.
	 */
	boolean hasBelote() {
		boolean roi = false;
		boolean dame = false;
		Couleur atout = Table.mancheCourante.getAtout();
		for (Carte c : Table.ensCartes) {
			if (c.getCouleur() == atout && c.getValeur() == Valeur.Roi) {
				if (main.contains(c)) roi = true;
			}
			else if (c.getCouleur() == atout && c.getValeur() == Valeur.Dame) {
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
	 * @return true si le coup est légal, false sinon.
	 */
	boolean isLegalMove(Carte carte) {
		Manche manche = Table.mancheCourante;
		Pli pli = manche.getPli(manche.getNbPlis());
		Couleur demande = pli.getCouleurDemandee();
		Couleur couleurDeLaCarte = carte.getCouleur();
		Couleur atoutManche = manche.getAtout();
		Carte plusGrandAtoutDuPli = null;
		LinkedList<Carte> plusGrandQueLePlusGrandAtoutDuPli = new LinkedList<Carte>();
		boolean pliContientAtout = false;
		int nbDemande = 0;
		int nbAtout = 0;
		
		if (pli.getNbCarte() == 0) {
			return true;
		}
		else {
			
			for (Carte c : pli.getCartes()) { //On détermine le plus grand atout du pli.
				if (c.getCouleur() == atoutManche) {
					if (!pliContientAtout) {
						pliContientAtout = true;
						plusGrandAtoutDuPli = c;
					}
					else if (c.compareTo(plusGrandAtoutDuPli) == 1) {
						 plusGrandAtoutDuPli = c;
					}
				}
			}
			
			for (Carte c : main) {
				if (c.getCouleur() == demande) nbDemande++;
				if (c.getCouleur() == atoutManche)nbAtout++;
				if (c.compareTo(plusGrandAtoutDuPli) == 1) plusGrandQueLePlusGrandAtoutDuPli.add(c);
			}
			
			if (demande != manche.getAtout()) { // Si on ne demande pas d'atout.
				if (nbDemande > 0) { // Si on a au moins une carte de la couleur demandée dans sa main.
					return (couleurDeLaCarte == demande);
				}
				else { // Si on a pas la couleur demandée.
					if (pli.getIdJoueurGagnant() == this.idPartenaire) return true; // Si le partenaire est maître, alors le coup est légal.
					else if (nbAtout > 0) { //Sinon, si l'adversaire et maître et qu'on a un atout.
						if (plusGrandAtoutDuPli == null) { //S'il n'y a pas encore d'atout dans le pli.
							return (couleurDeLaCarte == atoutManche);
						}
						else { //S'il y a au moins un atout dans le pli.
							if (plusGrandQueLePlusGrandAtoutDuPli.isEmpty()) { //Si on a pas plus grand.
								return (couleurDeLaCarte == atoutManche);
							}
							else {
								return plusGrandQueLePlusGrandAtoutDuPli.contains(carte);
							}
						}
					}
				}
			}
			else { // Si on demande de l'atout.
				if (nbAtout > 0) { // Si on en a.
					if (plusGrandQueLePlusGrandAtoutDuPli.isEmpty()) { //Si on a pas plus grand.
						return (couleurDeLaCarte == atoutManche);
					}
					else { // Si on a plus grand.
						return plusGrandQueLePlusGrandAtoutDuPli.contains(carte);
					}
				}
				else { // Si on en n'a pas.
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param carte La carte jouée
	 */
	void joueCoup(Carte carte) {
		Manche manche = Table.mancheCourante;
		manche.getPli(manche.getNbPlis()).addCarte(carte);
		main.remove(carte);
	}
	
	
	/**
	 * 
	 * @return
	 */
	boolean passe() {
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	boolean prend() {
		return false;
	}
}
