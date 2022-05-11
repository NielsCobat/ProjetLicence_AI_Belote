package game;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import assets.Couleur;
import assets.Valeur;

public class Table {
	
	static Joueur joueur1;
	static Joueur joueur2;
	static Joueur joueur3;
	static Joueur joueur4;
	
	static Equipe equipe1;
	static Equipe equipe2;
	
	static Joueur joueurCourant;
	static Manche mancheCourante;
	static Couleur atout;
	
	static LinkedList<Carte> cartesEnMain;
	static LinkedList<Carte> cartesPosees;
	static ArrayList<Carte> ensCartes;
	
	static boolean gameOver = false;
	
	void setEnsCartes() {
		//TODO
	}
	
	void distribuer() {
		//TODO
	}
	
	private Table() {
	}
	
	static {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		equipe1 = new Equipe(1, joueur1, joueur3, 0);
		equipe2 = new Equipe(2, joueur2, joueur4, 0);
		
		equipe1.score=0;
		equipe2.score=0;
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
		}
	}
}
