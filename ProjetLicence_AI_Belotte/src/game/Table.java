package game;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import assets.Couleur;
import assets.Valeur;

public class Table {
	
	Joueur joueur1;
	Joueur joueur2;
	Joueur joueur3;
	Joueur joueur4;
	
	Equipe equipe1;
	Equipe equipe2;
	
	Joueur joueurCourant;
	static Manche mancheCourante;
	Couleur atout;
	
	LinkedList<Carte> cartesEnMain;
	LinkedList<Carte> cartesPosees;
	ArrayList<Carte> ensCartes;
	
	boolean gameOver = false;
	
	void setEnsCartes() {
		//TODO
	}
	
	void distribuer() {
		//TODO
	}
	
	/**
	 * Initialise toutes les variables nécessaires pour débuter une partie
	 * @throws Exception
	 */
	private void init() throws Exception {
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
	void run() {
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
