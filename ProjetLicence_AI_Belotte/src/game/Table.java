package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import assets.Couleur;
import assets.Valeur;

public class Table {
	
	private final static int scoreToWin = 501;

	private static final Couleur Carreau = null;
	static Joueur joueur1;
	static Joueur joueur2;
	static Joueur joueur3;
	static Joueur joueur4;
	
	static int scoreEquipe1; //joueur 1 et 3
	static int scoreEquipe2; //joueur 2 et 4
	

	static Equipe equipe1;
	static Equipe equipe2;

	static Joueur joueurCourant;
	static Manche mancheCourante;
	static Couleur atout;

	static LinkedList<Carte> cartesEnMain;
	static LinkedList<Carte> cartesPosees;
	static ArrayList<Carte> ensCartes;
	static int idWinner;
	static boolean gameOver = false;


	static void setEnsCartes() {
		for (int i = 0; i < 4; i++) {
			Couleur couleurCourrante = Couleur.Carreau;
			switch (i) {
			case 0:
				couleurCourrante = Couleur.Carreau;
				break;
			case 1:
				couleurCourrante = Couleur.Coeur;
				break;
			case 2:
				couleurCourrante = Couleur.Pique;
				break;
			case 3:
				couleurCourrante = Couleur.Trefle;
				break;
			default:
			}
			for (int j = 0; j < 8; j++) {
				Valeur valeurCourrante = Valeur.Sept;
				int pts = 0;
				switch (j) {
				case 0:
					valeurCourrante = Valeur.Sept;
					pts = 0;
					break;
				case 1:
					valeurCourrante = Valeur.Huit;
					pts = 0;
					break;
				case 2:
					valeurCourrante = Valeur.Neuf;
					pts = 0;
					break;
				case 3:
					valeurCourrante = Valeur.Dix;
					pts = 10;
					break;
				case 4:
					valeurCourrante = Valeur.Valet;
					pts = 2;
					break;
				case 5:
					valeurCourrante = Valeur.Dame;
					pts = 3;
					break;
				case 6:
					valeurCourrante = Valeur.Roi;
					pts = 4;
					break;
				case 7:
					valeurCourrante = Valeur.As;
					pts = 11;
					break;
				default:
				}
				ensCartes.add(new Carte(couleurCourrante, valeurCourrante, pts));
			}
		}
		Collections.shuffle(ensCartes);
	}

	void distribuer() {
		// TODO
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
	static void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!gameOver) {
			/**
			 * gerer distrib , atout , maitre ...
			 * 
			 */
			
			if(scoreEquipe1>=scoreToWin) {
				gameOver = true;
				idWinner=1; //je mets l'id d'un seul membre comme le modulo donne forcement l'equipe des deux joueurs
			}
			if(scoreEquipe2>=scoreToWin) {
				gameOver = true;
				idWinner=2; //je mets l'id d'un seul membre comme le modulo donne forcement l'equipe des deux joueurs
			}
		}
	}
}
