package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import assets.Couleur;
import assets.Valeur;

public class Table {
	
	private final static int scoreToWin = 501;
	
	static Joueur joueur1;
	static Joueur joueur2;
	static Joueur joueur3;
	static Joueur joueur4;
	
	static int scoreEquipe1; //joueur 1 et 3
	static int scoreEquipe2; //joueur 2 et 4
	
	static Joueur joueurCourant;
	static Manche mancheCourante;
	static Couleur atout;
	
	static LinkedList<Carte> cartesEnMain;
	static LinkedList<Carte> cartesPosees;
	static Carte[] ensCartes;
	
	static boolean gameOver = false;
	static int idWinner;
	
	
	/**
	 * Affecte un ordre aleatoire de cartes au paquet de carte pour debuter la partie
	 * @param ensCarte
	 */
	static void setEnsCartes(Carte[] ensCarte) {
		Couleur randomColor;
		Valeur randomValeur;
		for(int i=0 ; i<ensCarte.length ; i++) {
			//selection une couleur au hasard dans enum Couleur
			randomColor =  Couleur.values()[new Random().nextInt(Couleur.values().length)];
			
			//selectionne une valeur au hasard dans enum Valeur
			randomValeur =  Valeur.values()[new Random().nextInt(Valeur.values().length)];
			
			Carte randomCarte = new Carte(randomColor,randomValeur,getPoint(randomValeur));
			
			//Tant que la carte generee existe deja dans le paquet, on en genere une nouvelle
			while(contains(ensCartes, randomCarte)) {
				randomColor =  Couleur.values()[new Random().nextInt(Couleur.values().length)];
				randomValeur =  Valeur.values()[new Random().nextInt(Valeur.values().length)];
				randomCarte = new Carte(randomColor,randomValeur,getPoint(randomValeur));
			}
			ensCartes[i]=randomCarte;
		}
	}
	
	
	/**
	 * Retourne les points d'une carte en fonction de sa valeur (ne prend en compte l'atout pour l'instant)
	 * @param val une valeur de l'enum Valeur
	 * @return les points associes a une valeur
	 */
	static int getPoint(Valeur val) {
		switch (val) {
		case Sept :
			return 0;

		case Huit :
			return 0;

		case Neuf :
			return 0;

		case Dix :
			return 10;

		case As :
			return 11;

		case Valet :
			return 2;

		case Dame :
			return 3;
			
		case Roi :
			return 4;
		default :
			return -1;
		}
	}
	
	
	/**
	 * Dit si une carte est contenue dans un paquet de carte
	 * @param ensCarte un paquet de carte
	 * @param carte une carte dont on verifie la presence dans le paquet
	 * @return true carte est presente dans paquet, false carte non presente dans le paquet
	 */
	static boolean contains(Carte[] ensCarte, Carte carte) {
		for (int i = 0; i < ensCarte.length; i++) {
			//eviter le nullPointerException si case vide
			if(ensCarte[i]==null) return false;

			if(ensCarte[i].getValeur()==carte.getValeur() && ensCarte[i].getCouleur()==carte.getCouleur())
				return true;
		}
		return false;
	}
	
	void distribuer() {
		//TODO
	}
	
	public Table() {
	}
	
	
	/**
	 * Initialise toutes les variables nécessaires pour débuter une partie
	 * @throws Exception
	 */
	public static void init() throws Exception {
		joueur1 = new Joueur("", 1, 3);
		joueur2 = new Joueur("", 2, 4);
		joueur3 = new Joueur("", 3, 1);
		joueur4 = new Joueur("", 4, 2);
		
		scoreEquipe1=0;
		scoreEquipe2=0;
		int idJoueurCourant = (int) (Math.random()*(4-1+1)+1); //selectionne un int entre 1 et 4
		switch (idJoueurCourant) {
			case 1 :
				joueurCourant = joueur1;
				break;
			case 2 :
				joueurCourant = joueur2;
				break;
			case 3 :
				joueurCourant = joueur3;
				break;
			case 4 :
				joueurCourant = joueur4;
				break;
			default :
				throw new Exception("identifiant du joueur non compatible");
		}
		ensCartes = new Carte[32];
		setEnsCartes(ensCartes);
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
		while(!gameOver) {
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
