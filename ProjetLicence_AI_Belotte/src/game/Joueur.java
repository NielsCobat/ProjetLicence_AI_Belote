package game;

import java.util.LinkedList;
import java.util.Scanner;
import assets.Couleur;


public class Joueur {

	String nom;
	int id;
	boolean maitre;
	int idPartenaire;
	LinkedList<Carte> main;
	boolean aBelote;
	
	public Joueur() {
		nom = "";
		id = 0;
		idPartenaire = 0;
		main = new LinkedList<Carte>();
	}

	public Joueur(String nom, int id, int partenaire) {
		this.nom = nom;
		this.id = id;
		this.idPartenaire = partenaire;
		this.main = new LinkedList<Carte>(); // A la creation main forcement vide
	}

	/**
	 * 
	 * Détermine si le joueur a la belote
	 */
	void hasBelote() {
		boolean roi = false;
		boolean dame = false;
		String atout = Table.mancheCourante.getAtout().name();
		for (Carte c : this.main) {
			if (c.getCouleur().name().equals(atout) && c.getValeur().name().equals("Roi")) {
					roi = true;
			} else if (c.getCouleur().name().equals(atout) && c.getValeur().name().equals("Dame")) {
					dame = true;
			}
		}
		aBelote = roi && dame;
	}
	
	/**
	 * Met aBelote à faux, utilisé en fin de manche après les calculs de points.
	 */
	public void setABelote() {
		aBelote = false;
	}

	/**
	 * Si le pli est vide on peut mettre n'importe quelle carte. Si on a la couleur
	 * demandée, on doit la jouer, si cette couleur est atout, alors si l'on a un
	 * atout de valeur supérieure, on doit le jouer, sinon on peut jouer n'importe
	 * quel autre atout Si on a pas la couleur demandée, alors si notre partenaire
	 * est maître, on peut jouer n'importe quelle carte, sinon si on a de l'atout,
	 * on doit le mettre, si ce n'est pas le cas on peut tout jouer.
	 * 
	 * @param carte La carte dont on s'interroge sur la légalité
	 * @return true si le coup est légal, false sinon.
	 */
	boolean isLegalMove(Carte carte) {
		Manche manche = Table.mancheCourante;
		Pli pli = manche.getPli(manche.getNbPlis());
		
		Couleur couleurDeLaCarte = carte.getCouleur();
		Couleur atoutManche = manche.getAtout();
		Carte plusGrandAtoutDuPli = null;
		LinkedList<Carte> plusGrandQueLePlusGrandAtoutDuPli = new LinkedList<Carte>();
		boolean pliContientAtout = false;
		int nbDemande = 0;
		int nbAtout = 0;

		if (pli.getNbCarte() == 0) {
			return true;
		} else {
			
			Couleur demande = pli.getCouleurDemandee();
			for (Carte c : pli.getCartes()) { // On détermine le plus grand atout du pli.
				if ((c != null) && c.getCouleur().name().equals(atoutManche.name())) {
					if (!pliContientAtout) {
						pliContientAtout = true;
						plusGrandAtoutDuPli = c;
					} else if (c.compareTo(plusGrandAtoutDuPli) == 1) {
						plusGrandAtoutDuPli = c;
					}
				}
			}

			for (Carte c : main) {
				if (c.getCouleur().name().equals(demande.name()))
					nbDemande++;
				if (c.getCouleur().name().equals(atoutManche.name()))
					nbAtout++;
				if (plusGrandAtoutDuPli == null || c.compareTo(plusGrandAtoutDuPli) == 1)
					plusGrandQueLePlusGrandAtoutDuPli.add(c);
			}

			if (!demande.name().equals(manche.getAtout().name()) ) { // Si on ne demande pas d'atout.
				if (nbDemande > 0) { // Si on a au moins une carte de la couleur demandée dans sa main.
					return (couleurDeLaCarte.name().equals(demande.name()));
				} else { // Si on a pas la couleur demandée.
					if (pli.getIdJoueurGagnant() == this.idPartenaire)
						return true; // Si le partenaire est maître, alors le coup est légal.
					else if (nbAtout > 0) { // Sinon, si l'adversaire et maître et qu'on a un atout.
						if (plusGrandAtoutDuPli == null) { // S'il n'y a pas encore d'atout dans le pli.
							return (couleurDeLaCarte.name().equals(atoutManche.name()));
						} else { // S'il y a au moins un atout dans le pli.
							if (plusGrandQueLePlusGrandAtoutDuPli.isEmpty()) { // Si on a pas plus grand.
								return (couleurDeLaCarte.name().equals(atoutManche.name()));
							} else {
								return plusGrandQueLePlusGrandAtoutDuPli.contains(carte);
							}
						}
					}
					else if (nbAtout == 0){ return true;}
				}
			} else { // Si on demande de l'atout.
				if (nbAtout > 0) { // Si on en a.
					if (plusGrandQueLePlusGrandAtoutDuPli.isEmpty()) { // Si on a pas plus grand.
						return (couleurDeLaCarte.name().equals(atoutManche.name()));
					} else { // Si on a plus grand.
						return plusGrandQueLePlusGrandAtoutDuPli.contains(carte);
					}
				} else { // Si on en n'a pas.
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
	 * Le joueur ne prend pas et le joueurCourant devient le joueur à sa gauche.
	 */
	void passe() {
		Table.joueurCourant = Table.joueurSuivant();
	}

	/**
	 * Met dans la main du joueur la carte prise.
	 * @param carte La carte prise
	 */
	void prend(Carte carte) {
		this.main.add(carte);
	}
	
	/**
	 * Lance l'action de prendre ou passer, est geree avec scanner pour l'instant
	 * @param carte retourner pour decider de l'atout
	 * @return false si le joueur ne prend pas, true sinon
	 */
	boolean veutPrendre(Carte carte) {
		Scanner scanner = Table.scannerString;
		String reponse = "";
		while (!reponse.equals("o") && !reponse.equals("n")) {
			System.out.println("Joueur " + this.id);
			System.out.print("Main : ");
			this.printMain();
			System.out.println("\nVeux prendre ? (o/n)");
			reponse = scanner.nextLine();
			if(reponse.equals("n")) {
				return false;
			}
			else if(reponse.equals("o")){
				prend(carte);
				Table.ensCartes.remove(carte);
				return true;
			}
			else System.out.println("L'entrée doit être valide ! ");
		}
		return false;
		
	}
	
	/**
	 * Durant le deuxieme tour du choix de l'atout, si joueur prend alors designe couleur
	 * differente de la couleur prise, geree avec un scanner pour l'instant
	 * @return Couleur designee par le joueur
	 */
	Couleur designeCouleur() {
		Scanner scanner = Table.scannerString;
		System.out.println("Quelle couleur d'atout ? (carreau/pique/coeur/trefle)");
		String reponse = scanner.nextLine();
		switch (reponse.toLowerCase()) {
		case "carreau" :
			if(this.main.getLast().getCouleur()!=Couleur.Carreau) return Couleur.Carreau;
			break;
		case "pique" :
			if(this.main.getLast().getCouleur()!=Couleur.Pique) return Couleur.Pique;
			break;
		case "coeur" :
			if(this.main.getLast().getCouleur()!=Couleur.Coeur) return Couleur.Coeur;
			break;
		case "trefle" :
			if(this.main.getLast().getCouleur()!=Couleur.Trefle) return Couleur.Trefle;
			break;
		default :
			break;
		}
		System.out.println("Choisissez une autre couleur");
		return designeCouleur();
	}
	
	public void printMain() {
		for (Carte c : main) {
			System.out.print(c.toString() + " | ");
		}
	}
	
	@Override
	public Joueur clone() {
		return new Joueur(this.nom,this.id,this.idPartenaire);
	}
}
