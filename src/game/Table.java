package game;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.LinkedList;
import java.util.Scanner;

import AI.NeuralNetwork;

import java.util.Collections;
import assets.Couleur;
import assets.Valeur;

public class Table {

	public final static int scoreToWin = 501;

	public static Joueur joueur1;
	public static Joueur joueur2;
	public static Joueur joueur3;
	public static Joueur joueur4;

	public static Equipe equipe1;
	public static Equipe equipe2;

	public static Joueur joueurCourant;
	public static Joueur distributeur;
	public static Manche mancheCourante;
	public static int mancheCour = 1;
	public static Couleur atout;

	// static LinkedList<Carte> cartesEnMain; //Variable pas utilisée
	// static LinkedList<Carte> cartesPosees; //Variable pas utilisée
	public static ArrayList<Carte> ensCartes = new ArrayList<Carte>();
	public static ArrayList<Carte> ensCartesAvantCoupe = new ArrayList<Carte>();
	public static ArrayList<Carte> ensTemp = new ArrayList<Carte>();// ens pour le comptage
	public static int idWinner;
	public static boolean gameOver = false;

	static Scanner scannerString = new Scanner(System.in);
	static Scanner scannerInt = new Scanner(System.in);

	// Crée le paquet de carte
	static void setEnsCartes() {
		ensCartes.add(new Carte(Couleur.Carreau, Valeur.Sept, 0));
		ensCartes.add(new Carte(Couleur.Carreau, Valeur.Huit, 0));
		ensCartes.add(new Carte(Couleur.Carreau, Valeur.Neuf, 0));
		ensCartes.add(new Carte(Couleur.Carreau, Valeur.Dix, 10));
		ensCartes.add(new Carte(Couleur.Carreau, Valeur.Valet, 2));
		ensCartes.add(new Carte(Couleur.Carreau, Valeur.Dame, 3));
		ensCartes.add(new Carte(Couleur.Carreau, Valeur.Roi, 4));
		ensCartes.add(new Carte(Couleur.Carreau, Valeur.As, 11));
		ensCartes.add(new Carte(Couleur.Coeur, Valeur.Sept, 0));
		ensCartes.add(new Carte(Couleur.Coeur, Valeur.Huit, 0));
		ensCartes.add(new Carte(Couleur.Coeur, Valeur.Neuf, 0));
		ensCartes.add(new Carte(Couleur.Coeur, Valeur.Dix, 10));
		ensCartes.add(new Carte(Couleur.Coeur, Valeur.Valet, 2));
		ensCartes.add(new Carte(Couleur.Coeur, Valeur.Dame, 3));
		ensCartes.add(new Carte(Couleur.Coeur, Valeur.Roi, 4));
		ensCartes.add(new Carte(Couleur.Coeur, Valeur.As, 11));
		ensCartes.add(new Carte(Couleur.Trefle, Valeur.Sept, 0));
		ensCartes.add(new Carte(Couleur.Trefle, Valeur.Huit, 0));
		ensCartes.add(new Carte(Couleur.Trefle, Valeur.Neuf, 0));
		ensCartes.add(new Carte(Couleur.Trefle, Valeur.Dix, 10));
		ensCartes.add(new Carte(Couleur.Trefle, Valeur.Valet, 2));
		ensCartes.add(new Carte(Couleur.Trefle, Valeur.Dame, 3));
		ensCartes.add(new Carte(Couleur.Trefle, Valeur.Roi, 4));
		ensCartes.add(new Carte(Couleur.Trefle, Valeur.As, 11));
		ensCartes.add(new Carte(Couleur.Pique, Valeur.Sept, 0));
		ensCartes.add(new Carte(Couleur.Pique, Valeur.Huit, 0));
		ensCartes.add(new Carte(Couleur.Pique, Valeur.Neuf, 0));
		ensCartes.add(new Carte(Couleur.Pique, Valeur.Dix, 10));
		ensCartes.add(new Carte(Couleur.Pique, Valeur.Valet, 2));
		ensCartes.add(new Carte(Couleur.Pique, Valeur.Dame, 3));
		ensCartes.add(new Carte(Couleur.Pique, Valeur.Roi, 4));
		ensCartes.add(new Carte(Couleur.Pique, Valeur.As, 11));
		Collections.shuffle(ensCartes);
	}

	/**
	 * Demande au joueur courant à quelle carte il souhaite couper Coupe Le joueur
	 * courant devient le joueur suivant
	 */
	public static void coupe() {
		// on demande au joueur courant où il souhaite couper
		System.out.println();
		Scanner scanner = scannerInt;
		System.out.println(
				"Joueur " + joueurCourant.id + ", où souhaitez-vous couper? Donnez l'indice de la carte (De 3 à 28). ");
		int index = scanner.nextInt();

		while (index < 3 || index > 28) {
			System.out.println("Choisissez un nombre valide svp ( entre 3 et 28 inclus). ");
			index = scanner.nextInt();
		}

		// on divise le paquet de cartes
		ArrayList<Carte> head = new ArrayList<Carte>();
		for (int i = 0; i < index; i++) {
			head.add(ensCartes.get(i));
		}
		ArrayList<Carte> tail = new ArrayList<Carte>();
		for (int i = index; i < ensCartes.size(); i++) {
			tail.add(ensCartes.get(i));
		}
		// on permute les deux paquets de cartes
		tail.addAll(head);
		ensCartes = tail;

		// on change de joueur courant qui distribuera
		joueurCourant = joueurSuivant();
	}

	/**
	 * Demande au joueur courant à quelle carte il souhaite couper Coupe Le joueur
	 * courant devient le joueur suivant
	 */
	static void coupeBis(int index) {
		// on divise le paquet de cartes
		ArrayList<Carte> head = new ArrayList<Carte>();
		for (int i = 0; i < index; i++) {
			head.add(ensTemp.get(i));
		}
		ArrayList<Carte> tail = new ArrayList<Carte>();
		for (int i = index; i < ensTemp.size(); i++) {
			tail.add(ensTemp.get(i));
		}
		// on permute les deux paquets de cartes
		tail.addAll(head);
		ensTemp = tail;
	}

	/**
	 * Distribue les 5 premieres cartes aux joueurs ( 3 + 2 ) Le joueur courant
	 * devient le joueur suivant le distributeur
	 * 
	 * @return la carte au dessus du paquet pour choix de l'atout
	 */
	public static Carte distribuer() {
		// int indiceCourantEnsCartes = 0;
		for (int i = 0; i <= 7; i++) {
			// commence distribution par le joueur suivant
			joueurCourant = joueurSuivant();
			// distribue les 3 premieres cartes a tout le monde
			if (i <= 3) {
				int j = 0;
				while (j < 3) {
					joueurCourant.main.add(ensCartes.get(0));
					ensCartes.remove(0);
					// indiceCourantEnsCartes++;
					j++;
				}
			}
			// distribue les 2 cartes a tout le monde
			else {
				int j = 0;
				while (j < 2) {
					joueurCourant.main.add(ensCartes.get(0));
					ensCartes.remove(0);
					// indiceCourantEnsCartes++;
					j++;
				}
			}
		}
		// joueur qui commence a parler est le joueur apres celui qui distribue
		joueurCourant = joueurSuivant();
		return ensCartes.get(0);
	}

	/**
	 * Simule une distribution pour le comptage de cartes.
	 */
	static void distribuerBis() {
		// int indiceCourantEnsCartes = 0;
		for (int i = 0; i <= 7; i++) {
			// commence distribution par le joueur suivant
			joueurCourant = joueurSuivant();
			// distribue les 3 premieres cartes a tout le monde
			if (i <= 3) {
				int j = 0;
				while (j < 3) {
					joueurCourant.mainFuture.add(ensTemp.get(0));
					ensTemp.remove(0);
					j++;
				}
			}
			// distribue les 2 cartes a tout le monde
			else {
				int j = 0;
				while (j < 2) {
					joueurCourant.mainFuture.add(ensTemp.get(0));
					ensTemp.remove(0);
					j++;
				}
			}
		}
		// joueur qui commence a parler est le joueur apres celui qui distribue
		// joueurCourant = joueurSuivant();
	}

	/**
	 * Distribue le reste des cartes une fois que l'atout est choisi Le joueur
	 * courant est le distributeur
	 * 
	 * @param peneur joueur qui se voit distribue deux cartes
	 */
	public static void distribuerReste(Joueur preneur) {
		// indice 21 car 4*5 cartes distribuees + 1
		// int indiceCourantEnsCartes = 21;
		for (int i = 0; i < 4; i++) {
			// le distrubution commence avec le joueur suivant le joueur distributeur
			joueurCourant = joueurSuivant();
			// joueur courant est le joueur qui recoit 2 cartes
			if (joueurCourant.id == preneur.id) {
				int j = 0;
				while (j < 2) {
					joueurCourant.main.add(ensCartes.get(0));
					ensCartes.remove(0);
					// indiceCourantEnsCartes++;
					j++;
				}
			} else {
				int j = 0;
				while (j < 3) {
					joueurCourant.main.add(ensCartes.get(0));
					ensCartes.remove(0);
					// indiceCourantEnsCartes++;
					j++;
				}
			}
		}
	}

	/**
	 * Simule la distribution du reste des cartes pour savoir qui à quelles cartes à
	 * partir de la deuxième manche. Le joueur courant est le distributeur
	 * 
	 * @param peneur joueur qui se voit distribue deux cartes
	 */
	static void distribuerResteBis(Joueur preneur) {
		for (int i = 0; i < 4; i++) {
			// le distrubution commence avec le joueur suivant le joueur distributeur
			joueurCourant = joueurSuivant();
			// joueur courant est le joueur qui recoit 2 cartes
			if (joueurCourant.id == preneur.id) {
				int j = 0;
				while (j < 2) {
					joueurCourant.mainFuture.add(ensTemp.get(0));
					ensTemp.remove(0);
					j++;
				}
			} else {
				int j = 0;
				while (j < 3) {
					joueurCourant.mainFuture.add(ensTemp.get(0));
					ensTemp.remove(0);
					j++;
				}
			}
		}
	}

	/**
	 * @return joueur suivant le joueur courant
	 */
	public static Joueur joueurSuivant() {
		int idCourant = joueurCourant.id;
		if (idCourant == 1)
			return joueur2;
		else if (idCourant == 2)
			return joueur3;
		else if (idCourant == 3)
			return joueur4;
		else
			return joueur1;
	}

	private Table() {
	}

	/**
	 * Initialise toutes les variables nécessaires pour débuter une partie
	 * 
	 * @throws Exception
	 */
	public static void init() throws Exception {
		setEnsCartes();
		joueur1 = new Joueur("", 1, 3);
		joueur2 = new Joueur("", 2, 4);
		joueur3 = new Joueur("", 3, 1);
		joueur4 = new Joueur("", 4, 2);
		equipe1 = new Equipe(1, joueur1, joueur3, 0);
		equipe2 = new Equipe(2, joueur2, joueur4, 0);
		equipe1.score = 0;
		equipe2.score = 0;
		atout = null;
		int idJoueurCourant = (int) (Math.random() * (4 - 1 + 1) + 1); // selectionne un int entre 1 et 4
		switch (idJoueurCourant) {
		case 1:
			joueurCourant = joueur1;
			break;
		case 2:
			joueurCourant = joueur2;
			break;
		case 3:
			joueurCourant = joueur3;
			break;
		case 4:
			joueurCourant = joueur4;
			break;
		default:
			throw new Exception("identifiant du joueur non compatible");
		}
		ensCartes = new ArrayList<Carte>();
		setEnsCartes();
	}

	/**
	 * Deroulement de la partie, de l'init jusqu'au game over
	 */
	@SuppressWarnings("unchecked")
	public static void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// boucle principale du jeu
		while (!gameOver) {
			// garder en memoire le joueur qui distribue
			distributeur = joueurCourant.clone();
			Joueur joueurPreneur = null; // pas terrible mais doit etre initialisée

			// Tant que l'atout n'est pas choisi on fait deux tours de table et on
			// redistribue
			while (atout == null) {
				distributeur = joueurCourant.clone();
				Carte head = distribuer();
				// premier tour de table pour choisir l'atout (une)
				for (int i = 0; i < 4; i++) {
					System.out.println(
							"Score équipe 1 : " + equipe1.getScore() + ". Score équipe 2 : " + equipe2.getScore());
					System.out.println("Tour 1\nCarte à prendre : " + head.toString());
					if (mancheCour != 1) {
						joueurCourant.compteCartes();
						joueurCourant.printMainsFutures();
					}
					boolean aPris = joueurCourant.veutPrendre(head);
					if (aPris) {
						joueurPreneur = joueurCourant.clone();
						atout = joueurCourant.main.get(5).getCouleur(); // maj couleur atout
						break;
					} else
						joueurCourant = joueurSuivant();
				}

				// si l'atout n'est pas encore decide alors deuxieme tour de table (deux)
				if (atout == null) {
					for (int i = 0; i < 4; i++) {
						System.out.println(
								"Score équipe 1 : " + equipe1.getScore() + ". Score équipe 2 : " + equipe2.getScore());
						System.out.println("Tour 2\nCarte à prendre : " + head.toString());
						if (mancheCour != 1) {
							joueurCourant.compteCartes();
							joueurCourant.printMainsFutures();
						}
						boolean aPris = joueurCourant.veutPrendre(head);
						if (aPris) {
							joueurPreneur = joueurCourant.clone();
							// second tour donc le preneur doit decider de la couleur de l'atout
							atout = joueurCourant.designeCouleur(); // maj couleur atout
							break;
						} else if (i != 3)
							joueurCourant = joueurSuivant();
					}
				}
				// atout decide, il faut distribuer le reste des cartes
				if (atout != null) {
					// le joueur courant redevient le joueur qui distribue
					joueurCourant = distributeur;
					distribuerReste(joueurPreneur);
				}
				// atout encore non decide alors on remet les cartes dans le paquet
				else {
					ArrayList<Carte> tl = (ArrayList<Carte>) ensCartes.clone();
					ArrayList<Carte> hd = new ArrayList<Carte>();
					for (int i = 0; i < 4; i++) {
						hd.addAll((Collection<? extends Carte>) joueurCourant.main.clone());
						joueurCourant = joueurSuivant();
					}
					ensCartes.clear();
					ensCartes.addAll(hd);
					ensCartes.addAll(tl);
					hd.clear();
					tl.clear();
					joueur1.main.clear();
					joueur2.main.clear();
					joueur3.main.clear();
					joueur4.main.clear();
					ensCartesAvantCoupe = (ArrayList<Carte>) ensCartes;// peut-être remettre le .clone().
					System.out.println("ensCartesAvantCoupe : " + ensCartesAvantCoupe.toString());
					coupe();
					System.out.println("ensCartes : " + ensCartes.toString());
				}
			}
			// lancement de la manche
			// joueur a gauche du joueur distributeur commence a poser une carte
			joueurCourant = joueurSuivant();
			Joueur premierJoueur = joueurCourant.clone();
			try {
				mancheCourante = new Manche(premierJoueur.id, joueurPreneur.id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mancheCourante.runManche(atout);
			// fin de la manche, on reset l'atout et la manche
			atout = null;
			try {
				mancheCourante.reset(joueurPreneur.id);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (equipe1.getScore() >= scoreToWin) {
				gameOver = true;
				idWinner = 1; // je mets l'id d'un seul membre comme le modulo donne forcement l'equipe des
								// deux joueurs
			}
			if (equipe2.getScore() >= scoreToWin) {
				gameOver = true;
				idWinner = 2; // je mets l'id d'un seul membre comme le modulo donne forcement l'equipe des
								// deux joueurs
			}
			if (!gameOver) {
				ensCartesAvantCoupe = (ArrayList<Carte>) ensCartes;// peut-être remettre le .clone().
				System.out.println("ensCartesAvantCoupe : " + ensCartesAvantCoupe.toString());
				joueurCourant = distributeur; // Le distributeur de la manche qui vient de finir doit couper pour la
												// prochaine manche;
				coupe();
				System.out.println("ensCartes : " + ensCartes.toString());
			}
		}
		System.out.println("Equipe " + idWinner + " gagne la partie");
		scannerString.close();
		scannerInt.close();
	}
}
