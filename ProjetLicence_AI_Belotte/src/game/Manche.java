package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import AI.Entrainement;
import AI.NeuralNetwork;
import assets.Couleur;
import assets.Valeur;

public class Manche {

	private Pli[] plis;
	private int idPremierJoueur, equipePreneur, nbPlis;
	private int[] pointsEquipe, belotte;

	// variables pour l'entrainement
	public Joueur j1, j2, j3, j4;
	public Equipe e1, e2;
	public ArrayList<Carte> ensCarte;

	/**
	 * Constructeur de la classe Manche
	 * 
	 * @param atout           l'atout de la première manche
	 * @param idPremierJoueur id du premier joueur à jouer
	 * @param joueurPreneur   id du joueur prenant l'atout
	 * @throws Exception si l'un des id de joueurs n'est pas valide
	 */
	public Manche(int idPremierJoueur, int joueurPreneur) throws Exception {
		if ((this.idPremierJoueur > 4 || this.idPremierJoueur < 1) && (joueurPreneur > 4 || joueurPreneur < 1))
			throw new Exception("game.Manche.Manche() : Un id de joueur n'est pas valide");

		this.plis = new Pli[8];
		this.idPremierJoueur = idPremierJoueur;
		this.equipePreneur = (joueurPreneur + 1) % 2; // soit 0 = joueurs 1 et 3 ; 1 = joueurs 2 et 4
		this.pointsEquipe = new int[2];
		this.pointsEquipe[0] = 0; // joueurs 1 et 3
		this.pointsEquipe[1] = 0; // joueurs 2 et 4
		this.nbPlis = 0;
		this.belotte = new int[2];
		this.j1 = null;
		this.j2 = null;
		this.j3 = null;
		this.j4 = null;
		this.e1 = null;
		this.e2 = null;
		this.ensCarte = null;
	}

	public Manche(int idPremierJoueur, int joueurPreneur, Joueur j1, Joueur j2, Joueur j3, Joueur j4,
			ArrayList<Carte> ensCarte) throws Exception {
		if ((this.idPremierJoueur > 4 || this.idPremierJoueur < 1) && (joueurPreneur > 4 || joueurPreneur < 1))
			throw new Exception("game.Manche.Manche() : Un id de joueur n'est pas valide");

		this.plis = new Pli[8];
		this.idPremierJoueur = idPremierJoueur;
		this.equipePreneur = (joueurPreneur + 1) % 2; // soit 0 = joueurs 1 et 3 ; 1 = joueurs 2 et 4
		this.pointsEquipe = new int[2];
		this.pointsEquipe[0] = 0; // joueurs 1 et 3
		this.pointsEquipe[1] = 0; // joueurs 2 et 4
		this.nbPlis = 0;
		this.belotte = new int[2];
		this.j1 = j1;
		this.j2 = j2;
		this.j3 = j3;
		this.j4 = j4;
		this.e1 = null;
		this.e2 = null;
		this.ensCarte = ensCarte;
	}

	/**
	 * Gestion de la fin d'une manche (points etc)
	 */
	public void finManche() {
		if (this.nbPlis == 8) {

			// calcul des points dans la classe Partie à la fin

			if (Table.joueur1.aBelote || Table.joueur3.aBelote)
				this.belotte[0] = 20;
			else if (Table.joueur2.aBelote || Table.joueur4.aBelote)
				this.belotte[1] = 20;
			System.out.println("Fin de Manche ! ");
			System.out.print("Points de l'équipe 1 : " + this.pointsEquipe[0]);
			if (this.belotte[0] == 20)
				System.out.print(" + " + this.belotte[0] + " points de Belote");
			System.out.println();
			System.out.print("Points de l'équipe 2 : " + this.pointsEquipe[1]);
			if (this.belotte[1] == 20)
				System.out.print(" + " + this.belotte[1] + " points de Belote");
			System.out.println();

			if (this.pointsEquipe[0] == 162) { // Équipe 2 capot
				this.pointsEquipe[0] = 252;
				System.out.println("Équipe 2 capot ! ");
			}

			else if (this.pointsEquipe[1] == 162) {// Équipe 1 capot
				this.pointsEquipe[1] = 252;
				System.out.println("Équipe 2 capot ! ");
			}

			else if (this.pointsEquipe[(this.equipePreneur)] < 82
					&& this.belotte[(this.equipePreneur)] + this.belotte[(this.equipePreneur + 1) % 2] == 0 // Si une
																											// équipe
																											// prend et
																											// qu'il n'y
																											// a pas de
																											// Belote
					|| (this.pointsEquipe[(this.equipePreneur + 1) % 2]
							+ this.belotte[(this.equipePreneur + 1) % 2] >= 91 // Ou
							&& this.belotte[(this.equipePreneur)] + this.belotte[(this.equipePreneur + 1) % 2] == 20)) { // Si
																															// une
																															// équipe
																															// prend
																															// et
																															// qu'il
																															// y
																															// a
																															// une
																															// Belote
				this.pointsEquipe[this.equipePreneur] = 0;
				this.pointsEquipe[(this.equipePreneur + 1) % 2] = 162;
				if (this.equipePreneur == 0)
					System.out.println("Équipe 1 dedans ! ");
				else
					System.out.println("Équipe 2 dedans ! ");

			}

			Table.equipe1.addScore(this.pointsEquipe[0] + this.belotte[0]);
			Table.equipe2.addScore(this.pointsEquipe[1] + this.belotte[1]);

			System.out.println("Score équipe 1 : " + Table.equipe1.score);
			System.out.println("Score équipe 2 : " + Table.equipe2.score);
			// On remet les cartes des plis dans le paquet, en commençant par les plis de
			// l'équipe qui ne prend pas.
			ArrayList<Carte> pasPris = new ArrayList<Carte>();
			ArrayList<Carte> pris = new ArrayList<Carte>();
			for (int i = 0; i < plis.length; i++) {
				if (plis[i].equipeGagnante() != equipePreneur) // L'équipe qui n'a pas pris retourne son tas de plis et
																// compte les cartes une à une
					for (int j = plis[i].getCartes().length - 1; j >= 0; j--)
						pasPris.add(plis[i].getCartes()[j]);
				else
					for (int j = 0; j < plis[i].getCartes().length; j++)
						pris.add(0, plis[i].getCartes()[j]);
			}
			Table.ensCartes.addAll(pasPris);
			Table.ensCartes.addAll(pris);
		}
		Table.joueur1.hasBelote(Table.atout);
		Table.joueur2.hasBelote(Table.atout);
		Table.joueur3.hasBelote(Table.atout);
		Table.joueur4.hasBelote(Table.atout);
		Table.mancheCour++;
	}

	/**
	 * Reset de la manche courante
	 * 
	 * @param atout         le nouvel atout
	 * @param joueurPreneur le joueur prenant
	 * @throws Exception si l'id du joueur prenant n'est pas valide
	 */
	public void reset(int joueurPreneur) throws Exception {
		if (joueurPreneur > 4 || joueurPreneur < 1)
			throw new Exception("game.Manche.reset() : Un id de joueur n'est pas valide");

		if (this.idPremierJoueur < 4)
			this.idPremierJoueur++;
		else
			this.idPremierJoueur = 1;
		this.equipePreneur = (joueurPreneur + 1) % 2; // soit 0 = joueurs 1 et 3 ; 1 = joueurs 2 et 4
		this.pointsEquipe[0] = 0; // joueurs 1 et 3
		this.pointsEquipe[1] = 0; // joueurs 2 et 4
		this.nbPlis = 0;
		this.belotte[0] = 0;
		this.belotte[1] = 0;
		// this.initPliSuivant(); // pas sûr que ça doive être ici

		// resetManche des IA
		if (Table.joueur1 instanceof NeuralNetwork) {
			((NeuralNetwork) Table.joueur1).resetManche();
		}
		if (Table.joueur2 instanceof NeuralNetwork) {
			((NeuralNetwork) Table.joueur2).resetManche();
		}
		if (Table.joueur3 instanceof NeuralNetwork) {
			((NeuralNetwork) Table.joueur3).resetManche();
		}
		if (Table.joueur4 instanceof NeuralNetwork) {
			((NeuralNetwork) Table.joueur4).resetManche();
		}
	}

	/**
	 * Ajout du score d'un pli à une équipe
	 * 
	 * @param equipe l'id de l'équipe (0 pour joueurs 1 3 et 1 pour joueurs 2 4)
	 * @param score  le score à incrémenter
	 */
	public void addScoreEquipe(int equipe, int score) {
		this.pointsEquipe[equipe] += score;
	}

	// /**
	// * Initialisation du pli suivant et incrémentation du score du pli précédent
	// au
	// * score de la manche
	// *
	// * @throws Exception si le pli précédent n'est pas terminé ou si le nombre de
	// * pli maximum a déjà été atteint
	// */
	// public void initPliSuivant() throws Exception {
	// if (this.nbPlis == 8)
	// throw new Exception("game.Manche.initPliSuivant() : la manche contient déjà 8
	// plis");
	// try {
	// if (this.plis[this.nbPlis - 1].getNbCarte() < 4)
	// throw new Exception("game.Manche.initPliSuivant() : le pli actuel n'est pas
	// terminé");
	// this.pointsEquipe[this.plis[this.nbPlis - 1].equipeGagnante()] +=
	// this.plis[this.nbPlis - 1].calculPoints();
	// this.plis[this.nbPlis] = new Pli(this.plis[this.nbPlis -
	// 1].getIdJoueurGagnant());
	// } catch (Exception e) {
	// this.plis[0] = new Pli(this.idPremierJoueur);
	// }
	// this.nbPlis++;
	// }

	public void runManche(Couleur atout) {
		boolean half = false; // Pour savoir si la moitié de la belote a été utilisée.
		Joueur jCourant = Table.joueurCourant;
		Scanner sc = Table.scannerString;
		String carteAJouer = "";
		Table.joueur1.hasBelote(atout);
		Table.joueur2.hasBelote(atout);
		Table.joueur3.hasBelote(atout);
		Table.joueur4.hasBelote(atout);

		// init des IA

		if (Table.joueur1 instanceof NeuralNetwork) {
			((NeuralNetwork) Table.joueur1).initInput();
		}
		if (Table.joueur2 instanceof NeuralNetwork) {
			((NeuralNetwork) Table.joueur2).initInput();
		}
		if (Table.joueur3 instanceof NeuralNetwork) {
			((NeuralNetwork) Table.joueur3).initInput();
		}
		if (Table.joueur4 instanceof NeuralNetwork) {
			((NeuralNetwork) Table.joueur4).initInput();

		}

		// Une manche == 8 plis
		for (int i = 0; i < 8; i++) {

			System.out.println("Pli numéro " + (i + 1));
			System.out.println("L'équipe " + (equipePreneur + 1) + " a pris.");

			// resetPli des IA
			if (Table.joueur1 instanceof NeuralNetwork) {
				((NeuralNetwork) Table.joueur1).resetPli();
			}
			if (Table.joueur2 instanceof NeuralNetwork) {
				((NeuralNetwork) Table.joueur2).resetPli();
			}
			if (Table.joueur3 instanceof NeuralNetwork) {
				((NeuralNetwork) Table.joueur3).resetPli();
			}
			if (Table.joueur4 instanceof NeuralNetwork) {
				((NeuralNetwork) Table.joueur4).resetPli();
			}

			/*
			 * try { initPliSuivant(); } catch (Exception e) { e.printStackTrace(); }
			 */

			// initialisation du i ème pli

			plis[i] = new Pli(idPremierJoueur);
			while (idPremierJoueur != Table.joueurCourant.id) {
				jCourant = Table.joueurSuivant();
				Table.joueurCourant = Table.joueurSuivant();
			}

			// Tour de table
			boolean aLaCarte;
			boolean played;

			for (int j = 0; j < 4; j++) {
				aLaCarte = false;
				played = false;
				while (!played) {
					if (jCourant.id == 1 || jCourant.id == 3)
						System.out.println("Joueur " + jCourant.id + " Équipe 1");
					else
						System.out.println("Joueur " + jCourant.id + " Équipe 2");
					System.out.println("Entrez le nom de la carte à jouer.");
					System.out.println("Atout : " + atout);
					System.out.print("Main : ");
					jCourant.printMain();
					System.out.println("\nÉtat actuel du pli : " + plis[i].toString());
					if (j > 0)
						System.out.println("Équipe maîtresse : " + (plis[i].equipeGagnante()));
					// TODO adapter a l'ia
					carteAJouer = sc.nextLine();

					for (Carte carte : jCourant.main) {
						if (carte.toString().toLowerCase().equals(carteAJouer.toLowerCase())) {
							aLaCarte = true;

							if (jCourant.isLegalMove(carte)) {
								if (jCourant.aBelote && ((carte.getValeur().name().equals("Dame")
										|| (carte.getValeur().name().equals("Roi"))
												&& carte.getCouleur().name().equals(atout.name())))) {
									if (!half) {
										System.out.println("Belote ! ");
										half = !half;
									} else {
										System.out.println("Rebelote ! ");
									}
								}
								if (jCourant instanceof NeuralNetwork) {
									((NeuralNetwork) jCourant).joueCoup();
								} else {
									jCourant.joueCoup(carte);
								}
								played = true;
								jCourant = Table.joueurSuivant();
								Table.joueurCourant = Table.joueurSuivant();
							} else
								System.out.println("Coup impossible.");
							break;
						}
					}

					if (!aLaCarte)
						System.out.println("Vous n'avez pas cette carte ! ");
				}

			}

			// recuperation id equipe gagnante et id premier joueur du pli suivant
			int idGagnante = plis[i].equipeGagnante() - 1;
			idPremierJoueur = plis[i].getIdJoueurGagnant();
			System.out.println("\n\nId du joueur gagnant est " + idPremierJoueur + "\n\n");

			// attribution des points du pli a l'equipe gagnante
			pointsEquipe[idGagnante] += plis[i].calculPoints();
			if (i == 7)
				pointsEquipe[idGagnante] += 10; // 10 de der
			nbPlis++;
		}
		// attribution des points remportes par chaque equipe
		finManche();
	}

	/*
	 * run manche pour l'entrainement de l'ia sans table
	 */
	public void runMancheEntrainement(Couleur atout) throws Exception {

		boolean half = false; // Pour savoir si la moitié de la belote a été utilisée.
		NeuralNetwork joueurCourant = new NeuralNetwork("", 1, 3);
		int idJoueurCourant = this.idPremierJoueur;
		
		
		switch (idJoueurCourant) {
		case 1:
			if (joueurCourant instanceof NeuralNetwork && j1 instanceof NeuralNetwork)
				joueurCourant = (NeuralNetwork) j1;
			break;
		case 2:
			if (joueurCourant instanceof NeuralNetwork && j2 instanceof NeuralNetwork)
				joueurCourant = (NeuralNetwork) j2;
			break;
		case 3:
			if (joueurCourant instanceof NeuralNetwork && j3 instanceof NeuralNetwork)
				joueurCourant = (NeuralNetwork) j3;
			break;
		case 4:
			if (joueurCourant instanceof NeuralNetwork && j4 instanceof NeuralNetwork)
				joueurCourant = (NeuralNetwork) j4;
			break;
		default:
			throw new Exception("identifiant du joueur non compatible");
		}

		// distribution directe sans choix de l'atout
		for (int i = 0; i < 8; i++) {
			j1.main.add(this.ensCarte.get(0));
			this.ensCarte.remove(0);
		}
		for (int i = 0; i < 8; i++) {
			j2.main.add(this.ensCarte.get(0));
			this.ensCarte.remove(0);
		}
		for (int i = 0; i < 8; i++) {
			j3.main.add(this.ensCarte.get(0));
			this.ensCarte.remove(0);
		}
		for (int i = 0; i < 8; i++) {
			j4.main.add(this.ensCarte.get(0));
			this.ensCarte.remove(0);
		}

		// Scanner sc = Table.scannerString;
		// String carteAJouer = "";
		j1.hasBelote(atout);
		j2.hasBelote(atout);
		j3.hasBelote(atout);
		j4.hasBelote(atout);

		// init des IA

		if (j1 instanceof NeuralNetwork && j2 instanceof NeuralNetwork && j3 instanceof NeuralNetwork
				&& j4 instanceof NeuralNetwork) {
			((NeuralNetwork) j1).initHashmap();
			((NeuralNetwork) j1).initHashmapOutput();
			// on regarde la main du joueur et on update le init
			for (Carte carte : j1.main) {
				for (Carte c2 : NeuralNetwork.posCartesInput.keySet()) {
					if (carte.equal(c2))
						((NeuralNetwork) j1).getInput()[NeuralNetwork.posCartesInput.get(c2)] = 1;
				}
			}

			for (Carte carte : j2.main) {
				for (Carte c2 : NeuralNetwork.posCartesInput.keySet()) {
					if (carte.equal(c2))
						((NeuralNetwork) j2).getInput()[64 + NeuralNetwork.posCartesInput.get(c2)] = 1;
				}
			}

			for (Carte carte : j3.main) {
				for (Carte c2 : NeuralNetwork.posCartesInput.keySet()) {
					if (carte.equal(c2))
						((NeuralNetwork) j3).getInput()[2 * 64 + NeuralNetwork.posCartesInput.get(c2)] = 1;
				}
			}
			for (Carte carte : j4.main) {
				for (Carte c2 : NeuralNetwork.posCartesInput.keySet()) {
					if (carte.equal(c2))
						((NeuralNetwork) j4).getInput()[3 * 64 + NeuralNetwork.posCartesInput.get(c2)] = 1;
				}
			}

			// init de l'atout
			switch (atout) {
			case Carreau:
				((NeuralNetwork) j1).getInput()[288] = 1;
				break;
			case Coeur:
				((NeuralNetwork) j1).getInput()[289] = 1;
				break;
			case Trefle:
				((NeuralNetwork) j1).getInput()[290] = 1;
				break;
			case Pique:
				((NeuralNetwork) j1).getInput()[291] = 1;
				break;
			}

			((NeuralNetwork) j2).initHashmap();
			((NeuralNetwork) j2).initHashmapOutput();
			// on regarde la main du joueur et on update le init
			for (Carte carte : ((NeuralNetwork) j2).main) {
				((NeuralNetwork) j2).getInput()[NeuralNetwork.posCartesInput.get(carte)] = 1;
			}

			// TODO grace à la fonction qui calcule l'ordre des cartes et donc le jeu des
			// autres joueurs onupdate le init de la même manière
			// solution intermédiaire pour savoir le jeu des autres joueurs
			for (Carte carte : ((NeuralNetwork) j1).main) {
				((NeuralNetwork) j1).getInput()[1 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}
			for (Carte carte : ((NeuralNetwork) j3).main) {
				((NeuralNetwork) j3).getInput()[2 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}
			for (Carte carte : ((NeuralNetwork) j4).main) {
				((NeuralNetwork) j4).getInput()[3 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}

			// init de l'atout
			switch (atout) {
			case Carreau:
				((NeuralNetwork) j2).getInput()[288] = 1;
				break;
			case Coeur:
				((NeuralNetwork) j2).getInput()[289] = 1;
				break;
			case Trefle:
				((NeuralNetwork) j2).getInput()[290] = 1;
				break;
			case Pique:
				((NeuralNetwork) j2).getInput()[291] = 1;
				break;
			}

			((NeuralNetwork) j3).initHashmap();
			((NeuralNetwork) j3).initHashmapOutput();
			// on regarde la main du joueur et on update le init
			for (Carte carte : ((NeuralNetwork) j3).main) {
				((NeuralNetwork) j3).getInput()[NeuralNetwork.posCartesInput.get(carte)] = 1;
			}

			// TODO grace à la fonction qui calcule l'ordre des cartes et donc le jeu des
			// autres joueurs onupdate le init de la même manière
			// solution intermédiaire pour savoir le jeu des autres joueurs
			for (Carte carte : ((NeuralNetwork) j1).main) {
				((NeuralNetwork) j1).getInput()[1 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}
			for (Carte carte : ((NeuralNetwork) j2).main) {
				((NeuralNetwork) j2).getInput()[2 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}
			for (Carte carte : ((NeuralNetwork) j4).main) {
				((NeuralNetwork) j4).getInput()[3 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}

			// init de l'atout
			switch (atout) {
			case Carreau:
				((NeuralNetwork) j3).getInput()[288] = 1;
				break;
			case Coeur:
				((NeuralNetwork) j3).getInput()[289] = 1;
				break;
			case Trefle:
				((NeuralNetwork) j3).getInput()[290] = 1;
				break;
			case Pique:
				((NeuralNetwork) j3).getInput()[291] = 1;
				break;
			}

			((NeuralNetwork) j4).initHashmap();
			((NeuralNetwork) j4).initHashmapOutput();
			// on regarde la main du joueur et on update le init
			for (Carte carte : ((NeuralNetwork) j4).main) {
				((NeuralNetwork) j4).getInput()[NeuralNetwork.posCartesInput.get(carte)] = 1;
			}

			// TODO grace à la fonction qui calcule l'ordre des cartes et donc le jeu des
			// autres joueurs onupdate le init de la même manière
			// solution intermédiaire pour savoir le jeu des autres joueurs
			for (Carte carte : ((NeuralNetwork) j1).main) {
				((NeuralNetwork) j1).getInput()[1 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}
			for (Carte carte : ((NeuralNetwork) j2).main) {
				((NeuralNetwork) j2).getInput()[2 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}
			for (Carte carte : ((NeuralNetwork) j3).main) {
				((NeuralNetwork) j3).getInput()[3 * 64 + NeuralNetwork.posCartesInput.get(carte)] = 1;
			}

			// init de l'atout
			switch (atout) {
			case Carreau:
				((NeuralNetwork) j4).getInput()[288] = 1;
				break;
			case Coeur:
				((NeuralNetwork) j4).getInput()[289] = 1;
				break;
			case Trefle:
				((NeuralNetwork) j4).getInput()[290] = 1;
				break;
			case Pique:
				((NeuralNetwork) j4).getInput()[291] = 1;
				break;
			}

		}

		// Une manche == 8 plis
		for (int i = 0; i < 8; i++) {

			// System.out.println("Pli numéro " + (i+1));
			// System.out.println("L'équipe " + (equipePreneur+ 1 ) + " a pris.");

			// resetPli des IA
			if (j1 instanceof NeuralNetwork) {
				((NeuralNetwork) j1).resetPli();
			}
			if (j2 instanceof NeuralNetwork) {
				((NeuralNetwork) j2).resetPli();
			}
			if (j3 instanceof NeuralNetwork) {
				((NeuralNetwork) j3).resetPli();
			}
			if (j4 instanceof NeuralNetwork) {
				((NeuralNetwork) j4).resetPli();
			}

			/*
			 * try { initPliSuivant(); } catch (Exception e) { e.printStackTrace(); }
			 */

			// initialisation du i ème pli

			plis[i] = new Pli(idPremierJoueur);
			if (idPremierJoueur != joueurCourant.id) {
				switch (idPremierJoueur) {
				case 1:
					if (joueurCourant instanceof NeuralNetwork && j1 instanceof NeuralNetwork)
						joueurCourant = (NeuralNetwork) j1;
					break;
				case 2:
					if (joueurCourant instanceof NeuralNetwork && j2 instanceof NeuralNetwork)
						joueurCourant = (NeuralNetwork) j2;
					break;
				case 3:
					if (joueurCourant instanceof NeuralNetwork && j3 instanceof NeuralNetwork)
						joueurCourant = (NeuralNetwork) j3;
					break;
				case 4:
					if (joueurCourant instanceof NeuralNetwork && j4 instanceof NeuralNetwork)
						joueurCourant = (NeuralNetwork) j4;
					break;
				}
			}

			// Tour de table
			boolean played;

			for (int j = 0; j < 4; j++) {
				played = false;
				while (!played) {

					if (joueurCourant instanceof NeuralNetwork) {
						// joue coup
						Carte carteAJouer = ((NeuralNetwork) joueurCourant).joueCoup(
								this.getPli(this.getNbPlis()).getCouleurDemandee(),
								this.getPli(this.getNbPlis()).getCartes(), this.getPli(this.getNbPlis()).getIdJoueurGagnant(),
								this, atout);

						this.getPli(this.getNbPlis()).addCarte(carteAJouer, atout);
						joueurCourant.main.remove(carteAJouer);

						// met à jour les inputs des autres ia en jeu que l'on soit une ia ou un joueur
						// réel
						NeuralNetwork.initHashmap();
						NeuralNetwork.initHashmapOutput();
						if (j1 instanceof NeuralNetwork && joueurCourant.id != j1.id) {
							((NeuralNetwork) j1).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
									+ 64 * (joueurCourant.id - 1)] = 0;
							((NeuralNetwork) j1).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
									+ 64 * (joueurCourant.id - 1) + 32] = 1;
						}
						if (j2 instanceof NeuralNetwork && joueurCourant.id != j2.id) {
							if (joueurCourant.id == 1) {
								((NeuralNetwork) j2).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
										+ 64 * (joueurCourant.id)] = 0;
								((NeuralNetwork) j2).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
										+ 64 * (joueurCourant.id) + 32] = 1;
							} else {
								((NeuralNetwork) j2).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
										+ 64 * (joueurCourant.id - 1)] = 0;
								((NeuralNetwork) j2).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
										+ 64 * (joueurCourant.id - 1) + 32] = 1;
							}
						}
						if (j3 instanceof NeuralNetwork && joueurCourant.id != j3.id) {
							if (joueurCourant.id == 1 || joueurCourant.id == 2) {
								((NeuralNetwork) j3).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
										+ 64 * (joueurCourant.id)] = 0;
								((NeuralNetwork) j3).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
										+ 64 * (joueurCourant.id) + 32] = 1;
							} else {

								((NeuralNetwork) j3).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
										+ 64 * (joueurCourant.id - 1)] = 0;
								((NeuralNetwork) j3).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
										+ 64 * (joueurCourant.id - 1) + 32] = 1;
							}
						}
						if (j4 instanceof NeuralNetwork && joueurCourant.id != j4.id) {
							((NeuralNetwork) j4).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
									+ 64 * (joueurCourant.id)] = 0;
							((NeuralNetwork) j4).getInput()[NeuralNetwork.posCartesInput.get(carteAJouer)
									+ 64 * (joueurCourant.id) + 32] = 1;
						}

						// met à jour si belote
						if (joueurCourant.aBelote && ((carteAJouer.getValeur().name().equals("Dame")
								|| (carteAJouer.getValeur().name().equals("Roi"))
										&& carteAJouer.getCouleur().name().equals(atout.name())))) {
							if (!half) {
								half = !half;
							}
						}

					} else {
						System.out.println("Erreur: joueurCourant doit être une ia");
					}

					played = true;

					switch (joueurCourant.id) {
					case 1:
						joueurCourant = (NeuralNetwork) j2;
						break;
					case 2:
						joueurCourant = (NeuralNetwork) j3;
						break;
					case 3:
						joueurCourant = (NeuralNetwork) j4;
						break;
					case 4:
						joueurCourant = (NeuralNetwork) j1;
						break;
					}
				}

			}

			// recuperation id equipe gagnante et id premier joueur du pli suivant
			int idGagnante = plis[i].equipeGagnante(atout) - 1;
			idPremierJoueur = plis[i].getIdJoueurGagnant();

			// attribution des points du pli a l'equipe gagnante
			pointsEquipe[idGagnante] += plis[i].calculPoints(atout);
			if (i == 7)
				pointsEquipe[idGagnante] += 10; // 10 de der
			nbPlis++;
		}
		// attribution des points remportes par chaque equipe
		finMancheEntrainement();
	}

	/**
	 * Gestion de la fin d'une manche pour runMancheEntrainement(points etc)
	 */
	public void finMancheEntrainement() {
		if (this.nbPlis == 8) {

			// calcul des points dans la classe Partie à la fin

			if (j1.aBelote || j3.aBelote)
				this.belotte[0] = 20;
			else if (j2.aBelote || j4.aBelote)
				this.belotte[1] = 20;

			if (this.pointsEquipe[0] == 162) // Équipe 2 capot
				this.pointsEquipe[0] = 252;

			else if (this.pointsEquipe[1] == 162)// Équipe 1 capot
				this.pointsEquipe[1] = 252;

			else if (this.pointsEquipe[(this.equipePreneur)] < 82
					&& this.belotte[(this.equipePreneur)] + this.belotte[(this.equipePreneur + 1) % 2] == 0
					|| (this.pointsEquipe[(this.equipePreneur + 1) % 2]
							+ this.belotte[(this.equipePreneur + 1) % 2] >= 91
							&& this.belotte[(this.equipePreneur)] + this.belotte[(this.equipePreneur + 1) % 2] == 20)) {
				// Si une équipe prend et qu'il n'y a pas de Belote ou Si une équipe prend et
				// qu'il y a une Belote
				this.pointsEquipe[this.equipePreneur] = 0;
				this.pointsEquipe[(this.equipePreneur + 1) % 2] = 162;
			}

			this.pointsEquipe[0] += this.belotte[0];
			this.pointsEquipe[1] += this.belotte[1];
		}
	}

	/**
	 * Getter d'un seul pli
	 * 
	 * @param noPli le numéro du pli souhaité (de 0 à 7)
	 * @return l'objet du pli en question
	 */
	public Pli getPli(int noPli) {
		return this.plis[noPli];
	}

	// /**
	// * Getter de l'id du premier joueur de la manche (joueur après le distribueur)
	// *
	// * @return id du joueur en question
	// */
	// public int getIdPremierJoueur() {
	// return idPremierJoueur;
	// }

	// /**
	// * Getter de l'équipe qui a pris à l'atout
	// *
	// * @return 0 pour joueurs 1 3 et 1 pour joueurs 2 4
	// */
	// public int getEquipePreneur() {
	// return equipePreneur;
	// }

	/**
	 * Getter du nombre de plis joués dans la manche
	 * 
	 * @return nombre de plis joués
	 */
	public int getNbPlis() {
		return nbPlis;
	}

	/**
	 * Getter du nombre de points d'une équipe
	 * 
	 * @param l'id de l'équipe souhaitée (0 ou 1)
	 * @return le score de cette équipe
	 */
	public int getPointsEquipe(int equipe) {
		return pointsEquipe[equipe];
	}

}
