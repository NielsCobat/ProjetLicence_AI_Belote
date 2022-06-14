package game;

import java.util.ArrayList;
import java.util.HashMap;

import AI.NeuralNetwork;
import java.util.LinkedList;
import java.util.Scanner;
import assets.Couleur;
import assets.Valeur;


public class Joueur {

	String nom;
	public int id;
	//boolean maitre; //Variable pas utilisée
	public int idPartenaire;
	public ArrayList<Carte> main;
	public ArrayList<Carte> mainFuture; //Aide à décider de la couleur de l'atout une fois que l'on connait la disposition des cartes dans le paquet.
	public boolean aBelote;

	public Joueur() {
		nom = "";
		id = 0;
		idPartenaire = 0;
		main = new ArrayList<Carte>();
	}

	public Joueur(String nom, int id, int partenaire) {
		this.nom = nom;
		this.id = id;
		this.idPartenaire = partenaire;
		this.main = new ArrayList<Carte>(); // A la creation main forcement vide
		this.mainFuture = new ArrayList<Carte>();
	}

	/**
	 * Détermine si le joueur a la belote
	 * @param atout La couleur de l'atout
	 */
	public void hasBelote(Couleur atout) {
		boolean roi = false;
		boolean dame = false;
		String a = atout.name();
		for (Carte c : this.main) {
			if (c.getCouleur().name().equals(a) && c.getValeur().name().equals("Roi")) {
				roi = true;
			} else if (c.getCouleur().name().equals(a) && c.getValeur().name().equals("Dame")) {
				dame = true;
			}
		}
		aBelote = roi && dame;
	}

	//	/**
	//	 * Met aBelote à faux, utilisé en fin de manche après les calculs de points.
	//	 */
	//	public void setABelote() {
	//		aBelote = false;
	//	}

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
	protected boolean isLegalMove(Carte carte) {
		Manche manche = Table.mancheCourante;
		Pli pli = manche.getPli(manche.getNbPlis());

		Couleur couleurDeLaCarte = carte.getCouleur();
		Couleur atout = Table.atout;
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
				if ((c != null) && c.getCouleur().name().equals(atout.name())) {
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
				if (c.getCouleur().name().equals(atout.name()))
					nbAtout++;
				if (plusGrandAtoutDuPli == null || c.compareTo(plusGrandAtoutDuPli) == 1)
					plusGrandQueLePlusGrandAtoutDuPli.add(c);
			}

			if (!demande.name().equals(atout.name()) ) { // Si on ne demande pas d'atout.
				if (nbDemande > 0) { // Si on a au moins une carte de la couleur demandée dans sa main.
					return (couleurDeLaCarte.name().equals(demande.name()));
				} else { // Si on a pas la couleur demandée.
					if (pli.getIdJoueurGagnant() == this.idPartenaire)
						return true; // Si le partenaire est maître, alors le coup est légal.
					else if (nbAtout > 0) { // Sinon, si l'adversaire et maître et qu'on a un atout.
						if (plusGrandAtoutDuPli == null) { // S'il n'y a pas encore d'atout dans le pli.
							return (couleurDeLaCarte.name().equals(atout.name()));
						} else { // S'il y a au moins un atout dans le pli.
							if (plusGrandQueLePlusGrandAtoutDuPli.isEmpty()) { // Si on a pas plus grand.
								return (couleurDeLaCarte.name().equals(atout.name()));
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
						return (couleurDeLaCarte.name().equals(atout.name()));
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
	 * Version entrainement ia sans table
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
	protected boolean isLegalMove(Carte carte, Manche manche, Couleur atout ) {
		Pli pli = manche.getPli(manche.getNbPlis());

		Couleur couleurDeLaCarte = carte.getCouleur();
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
				if ((c != null) && c.getCouleur().name().equals(atout.name())) {
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
				if (c.getCouleur().name().equals(atout.name()))
					nbAtout++;
				if (plusGrandAtoutDuPli == null || c.compareTo(plusGrandAtoutDuPli) == 1)
					plusGrandQueLePlusGrandAtoutDuPli.add(c);
			}

			if (!demande.name().equals(atout.name()) ) { // Si on ne demande pas d'atout.
				if (nbDemande > 0) { // Si on a au moins une carte de la couleur demandée dans sa main.
					return (couleurDeLaCarte.name().equals(demande.name()));
				} else { // Si on a pas la couleur demandée.
					if (pli.getIdJoueurGagnant() == this.idPartenaire)
						return true; // Si le partenaire est maître, alors le coup est légal.
					else if (nbAtout > 0) { // Sinon, si l'adversaire et maître et qu'on a un atout.
						if (plusGrandAtoutDuPli == null) { // S'il n'y a pas encore d'atout dans le pli.
							return (couleurDeLaCarte.name().equals(atout.name()));
						} else { // S'il y a au moins un atout dans le pli.
							if (plusGrandQueLePlusGrandAtoutDuPli.isEmpty()) { // Si on a pas plus grand.
								return (couleurDeLaCarte.name().equals(atout.name()));
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
						return (couleurDeLaCarte.name().equals(atout.name()));
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
	protected void joueCoup(Carte carte) {
		Manche manche = Table.mancheCourante;

		manche.getPli(manche.getNbPlis()).addCarte(carte, Table.atout);
		main.remove(carte);

		
		//met à  jour les inputs des autres ia en jeu que l'on soit une ia ou un joueur réel
		if(Table.joueur1 instanceof NeuralNetwork && this.id!=Table.joueur1.id) {
			((NeuralNetwork) Table.joueur1).getInput()[NeuralNetwork.posCartesInput.get(carte) + 64*(this.id-1)] = 0;
			((NeuralNetwork) Table.joueur1).getInput()[NeuralNetwork.posCartesInput.get(carte)  + 64*(this.id-1) + 32] = 1;
		}
		if(Table.joueur2 instanceof NeuralNetwork && this.id!=Table.joueur2.id) {
			if(this.id ==1) {
				((NeuralNetwork) Table.joueur2).getInput()[NeuralNetwork.posCartesInput.get(carte) + 64*(this.id)] = 0;
				((NeuralNetwork) Table.joueur2).getInput()[NeuralNetwork.posCartesInput.get(carte)  + 64*(this.id) + 32] = 1;
			}
			else {
				((NeuralNetwork) Table.joueur2).getInput()[NeuralNetwork.posCartesInput.get(carte) + 64*(this.id-1)] = 0;
				((NeuralNetwork) Table.joueur2).getInput()[NeuralNetwork.posCartesInput.get(carte)  + 64*(this.id-1) + 32] = 1;
			}
		}
		if(Table.joueur3 instanceof NeuralNetwork && this.id!=Table.joueur3.id) {
			if(this.id == 1 || this.id == 2) {
				((NeuralNetwork) Table.joueur3).getInput()[NeuralNetwork.posCartesInput.get(carte) + 64*(this.id)] = 0;
				((NeuralNetwork) Table.joueur3).getInput()[NeuralNetwork.posCartesInput.get(carte)  + 64*(this.id) + 32] = 1;
			}else {

				((NeuralNetwork) Table.joueur3).getInput()[NeuralNetwork.posCartesInput.get(carte) + 64*(this.id-1)] = 0;
				((NeuralNetwork) Table.joueur3).getInput()[NeuralNetwork.posCartesInput.get(carte)  + 64*(this.id-1) + 32] = 1;
			}
		}
		if(Table.joueur4 instanceof NeuralNetwork && this.id!=Table.joueur4.id) {
			((NeuralNetwork) Table.joueur4).getInput()[NeuralNetwork.posCartesInput.get(carte) + 64*(this.id)] = 0;
			((NeuralNetwork) Table.joueur4).getInput()[NeuralNetwork.posCartesInput.get(carte)  + 64*(this.id) + 32] = 1;
		}
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
			if(!this.main.get(5).getCouleur().name().equals(Couleur.Carreau.name())) return Couleur.Carreau;
			break;
		case "pique" :
			if(!this.main.get(5).getCouleur().name().equals(Couleur.Pique.name())) return Couleur.Pique;
			break;
		case "coeur" :
			if(!this.main.get(5).getCouleur().name().equals(Couleur.Coeur.name())) return Couleur.Coeur;
			break;
		case "trefle" :
			if(!this.main.get(5).getCouleur().name().equals(Couleur.Trefle.name())) return Couleur.Trefle;
			break;
		default :
			break;
		}
		System.out.println("Choisissez une autre couleur");
		return designeCouleur();
	}

    //TODO intégrer le decide couleur
	/**
	 * Permet à une IA de décider de la couleur à prendre
	 * @return c La couleur à prendre
	 */
	Couleur decideCouleur() {
		Couleur c = null;
		int scorePique = 0, scoreCoeur = 0, scoreCarreau = 0, scoreTrefle = 0, scoreMax = 0;
		boolean roiPique = false, roiCoeur = false, roiCarreau = false, roiTrefle = false; //Permet de ne pas à avoir à retraverser le jeu 4 fois
		boolean damePique = false, dameCoeur = false, dameCarreau = false, dameTrefle = false;//pour vérifier que le joueur à la belote (une fois par couleur)
		//Avoir une carte de la couleur ajoute 2 au score de la couleur (afin de faire peser le 7 et le 8).
		//On ajoute le score de la carte à la couleur (en assumant que la carte est un atout).
		//Avoir une belote dans une main ajoute 20 au score de la couleur.

		if (Table.mancheCour == 1) { // Durant la prmière manche, on connait uniquement ses propres cartes.
			for (Carte carte : this.main) {
				if (carte.getCouleur().name().equals(Couleur.Pique.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scorePique += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scorePique += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiPique = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) damePique = true;
					scorePique += carte.point + 2;
				}
				else if (carte.getCouleur().name().equals(Couleur.Coeur.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreCoeur += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreCoeur += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiCoeur = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameCoeur = true;
					scoreCoeur += carte.point + 2;
				}
				else if (carte.getCouleur().name().equals(Couleur.Carreau.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreCarreau += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreCarreau += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiCarreau = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameCarreau = true;
					scoreCarreau += carte.point + 2;
				}
				else { // La carte est un Trèfle
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreTrefle += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreTrefle += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiTrefle = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameTrefle = true;
					scoreTrefle += carte.point + 2;
				}
			}

			if (roiPique && damePique) scorePique +=20;
			if (roiCoeur && dameCoeur) scoreCoeur +=20;
			if (roiCarreau && dameCarreau) scoreCarreau +=20;
			if (roiTrefle && dameTrefle) scoreTrefle +=20;
		}

		else { //Lorsqu'on n'est pas à la première manche, on connait les cartes des autres.
			Table.distribuerResteBis(this);
			ArrayList<Carte> mainFuturePartenaire;
			if(this.idPartenaire == 1) mainFuturePartenaire = Table.joueur1.mainFuture;
			else if(this.idPartenaire == 2) mainFuturePartenaire = Table.joueur2.mainFuture;
			else if(this.idPartenaire == 3) mainFuturePartenaire = Table.joueur3.mainFuture;
			else mainFuturePartenaire = Table.joueur4.mainFuture;

			for (Carte carte : this.mainFuture) {
				if (carte.getCouleur().name().equals(Couleur.Pique.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scorePique += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scorePique += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiPique = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) damePique = true;
					scorePique += carte.point + 2;
				}
				else if (carte.getCouleur().name().equals(Couleur.Coeur.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreCoeur += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreCoeur += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiCoeur = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameCoeur = true;
					scoreCoeur += carte.point + 2;
				}
				else if (carte.getCouleur().name().equals(Couleur.Carreau.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreCarreau += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreCarreau += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiCarreau = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameCarreau = true;
					scoreCarreau += carte.point + 2;
				}
				else { // La carte est un Trèfle
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreTrefle += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreTrefle += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiTrefle = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameTrefle = true;
					scoreTrefle += carte.point + 2;
				}
			}

			if (roiPique && damePique) scorePique +=20;
			if (roiCoeur && dameCoeur) scoreCoeur +=20;
			if (roiCarreau && dameCarreau) scoreCarreau +=20;
			if (roiTrefle && dameTrefle) scoreTrefle +=20;
			roiPique = false;
			roiCoeur = false;
			roiCarreau = false;
			roiTrefle = false;
			damePique = false;
			dameCoeur = false;
			dameCarreau = false;
			dameTrefle = false;

			for (Carte carte : mainFuturePartenaire) {
				if (carte.getCouleur().name().equals(Couleur.Pique.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scorePique += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scorePique += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiPique = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) damePique = true;
					scorePique += carte.point + 2;
				}
				else if (carte.getCouleur().name().equals(Couleur.Coeur.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreCoeur += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreCoeur += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiCoeur = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameCoeur = true;
					scoreCoeur += carte.point + 2;
				}
				else if (carte.getCouleur().name().equals(Couleur.Carreau.name())) {
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreCarreau += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreCarreau += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiCarreau = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameCarreau = true;
					scoreCarreau += carte.point + 2;
				}
				else { // La carte est un Trèfle
					if (carte.getValeur().name().equals(Valeur.Valet.name())) scoreTrefle += 18;
					if (carte.getValeur().name().equals(Valeur.Neuf.name())) scoreTrefle += 14;
					if (carte.getValeur().name().equals(Valeur.Roi.name())) roiTrefle = true;
					if (carte.getValeur().name().equals(Valeur.Dame.name())) dameTrefle = true;
					scoreTrefle += carte.point + 2;
				}
			}

			if (roiPique && damePique) scorePique +=20;
			if (roiCoeur && dameCoeur) scoreCoeur +=20;
			if (roiCarreau && dameCarreau) scoreCarreau +=20;
			if (roiTrefle && dameTrefle) scoreTrefle +=20;
		}

		if (scorePique > scoreMax) {
			scoreMax = scorePique;
			c = Couleur.Pique;
		}
		if (scoreCoeur > scoreMax) {
			scoreMax = scoreCoeur;
			c = Couleur.Coeur;
		}
		if (scoreCarreau > scoreMax) {
			scoreMax = scoreCarreau;
			c = Couleur.Carreau;
		}
		if (scoreTrefle > scoreMax) {
			scoreMax = scoreTrefle;
			c = Couleur.Trefle;
		}
		//En cas d'égalité des scores, on garde la première couleur vérifiée atteignant cette égalité.
		return c;
	}
	
	
	/**
	 * Détermine les cartes de tous les joueurs dans le cas ou this prend.
	 */
	public void compteCartes() {
		Table.joueur1.mainFuture.clear();
		Table.joueur2.mainFuture.clear();
		Table.joueur3.mainFuture.clear();
		Table.joueur4.mainFuture.clear();
		int decalageJoueur = 0;
		int tailleCoupe = 0;
		for (byte i = 1; i <= 4; i++) {
			if(this.id == (((Table.distributeur.id - 1) + i) % 4) + 1 ) {
				decalageJoueur = i - 1;
				break;
			}
		}
		tailleCoupe = Table.ensCartesAvantCoupe.indexOf(this.main.get(0)) - (3*decalageJoueur);
		
		while(Table.joueurCourant.id != Table.distributeur.id) Table.joueurCourant = Table.joueurSuivant();
		
		Table.ensTemp = (ArrayList<Carte>) Table.ensCartesAvantCoupe;
		Table.coupeBis(tailleCoupe);
		Table.distribuerBis();
		this.mainFuture.add(Table.ensTemp.get(0));//donner la carte du milieu au joueur preneur
		Table.ensTemp.remove(0);
		Table.distribuerResteBis(this);
		
		while(Table.joueurCourant.id != this.id) Table.joueurCourant = Table.joueurSuivant();
		
	}

	public void printMain() {
		for (Carte c : main) {
			System.out.print(c.toString() + " | ");
		}
	}

	public void printMainsFutures() {
		System.out.println("Le joueur 1 aura : ");
		for (Carte c : Table.joueur1.mainFuture) {
			System.out.print(c.toString() + " | ");
		}
		System.out.println();
		System.out.println("Le joueur 2 aura : ");
		for (Carte c : Table.joueur2.mainFuture) {
			System.out.print(c.toString() + " | ");
		}
		System.out.println();
		System.out.println("Le joueur 3 aura : ");
		for (Carte c : Table.joueur3.mainFuture) {
			System.out.print(c.toString() + " | ");
		}
		System.out.println();
		System.out.println("Le joueur 4 aura : ");
		for (Carte c : Table.joueur4.mainFuture) {
			System.out.print(c.toString() + " | ");
		}
		System.out.println();
	}

	@Override
	public Joueur clone() {
		return new Joueur(this.nom,this.id,this.idPartenaire);
	}

}
