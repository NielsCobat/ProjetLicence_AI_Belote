package game;
import java.util.HashMap;

import assets.Couleur;
import assets.Valeur;

public class NeuralNetwork extends Joueur{

	//tableau des entrées et sorties

	/*
	 * Position:
	 * 	0 à 31: les cartes en main de l'ia 
	 * 	32 à 63 : les cartes jouées parr l'ia
	 * 
	 * 	64 à 95 et 128 à 159 et 192 à 223: les cartes en main des autres joueurs
	 * 	96 à 127 et 160 à 191 et 224 à 255: les cartes jouées des autres joueurs
	 * 
	 * 	256 à 287: les cartes sur table
	 * 
	 * 	288 à 291: l'atout
	 * 	292 à 295: couleur en jeu
	 * 
	 * 	296: 1 si le maître est dans ton équipe
	 */
	double[] input = new double[296]; 
	double[] output = new double[31];

	//hashMap de reference pour les positions des inputs
	HashMap<Carte, Integer> posCartesInput = new HashMap<Carte, Integer>();

	void initHashmap() {
		posCartesInput.put(new Carte(Couleur.Carreau, Valeur.Sept, 0), 0);
		posCartesInput.put(new Carte(Couleur.Carreau, Valeur.Huit, 0), 1);
		posCartesInput.put(new Carte(Couleur.Carreau, Valeur.Neuf, 0), 2);
		posCartesInput.put(new Carte(Couleur.Carreau, Valeur.Dix, 10), 3);
		posCartesInput.put(new Carte(Couleur.Carreau, Valeur.Valet, 2), 4);
		posCartesInput.put(new Carte(Couleur.Carreau, Valeur.Dame, 3), 5);
		posCartesInput.put(new Carte(Couleur.Carreau, Valeur.Roi, 4), 6);
		posCartesInput.put(new Carte(Couleur.Carreau, Valeur.As, 11), 7);
		posCartesInput.put(new Carte(Couleur.Coeur, Valeur.Sept, 0), 8);
		posCartesInput.put(new Carte(Couleur.Coeur, Valeur.Huit, 0), 9);
		posCartesInput.put(new Carte(Couleur.Coeur, Valeur.Neuf, 0), 10);
		posCartesInput.put(new Carte(Couleur.Coeur, Valeur.Dix, 10), 11);
		posCartesInput.put(new Carte(Couleur.Coeur, Valeur.Valet, 2), 12);
		posCartesInput.put(new Carte(Couleur.Coeur, Valeur.Dame, 3), 13);
		posCartesInput.put(new Carte(Couleur.Coeur, Valeur.Roi, 4), 14);
		posCartesInput.put(new Carte(Couleur.Coeur, Valeur.As, 11), 15);
		posCartesInput.put(new Carte(Couleur.Trefle, Valeur.Sept, 0), 16);
		posCartesInput.put(new Carte(Couleur.Trefle, Valeur.Huit, 0), 17);
		posCartesInput.put(new Carte(Couleur.Trefle, Valeur.Neuf, 0), 18);
		posCartesInput.put(new Carte(Couleur.Trefle, Valeur.Dix, 10), 19);
		posCartesInput.put(new Carte(Couleur.Trefle, Valeur.Valet, 2), 20);
		posCartesInput.put(new Carte(Couleur.Trefle, Valeur.Dame, 3), 21);
		posCartesInput.put(new Carte(Couleur.Trefle, Valeur.Roi, 4), 22);
		posCartesInput.put(new Carte(Couleur.Trefle, Valeur.As, 11), 23);
		posCartesInput.put(new Carte(Couleur.Pique, Valeur.Sept, 0), 24);
		posCartesInput.put(new Carte(Couleur.Pique, Valeur.Huit, 0), 25);
		posCartesInput.put(new Carte(Couleur.Pique, Valeur.Neuf, 0), 26);
		posCartesInput.put(new Carte(Couleur.Pique, Valeur.Dix, 10), 27);
		posCartesInput.put(new Carte(Couleur.Pique, Valeur.Valet, 2), 28);
		posCartesInput.put(new Carte(Couleur.Pique, Valeur.Dame, 3), 29);
		posCartesInput.put(new Carte(Couleur.Pique, Valeur.Roi, 4), 30);
		posCartesInput.put(new Carte(Couleur.Pique, Valeur.As, 11), 31);
	}


	/**
	 * Initialise le neural network
	 */
	void initInput() {
		
		//TODO on regarde la main du joueur et on update le init
		for (Carte carte : this.main) {
		      input[posCartesInput.get(carte)] = 1;
		    }

		//TODO grace à la fonction qui calcule l'ordre des cartes et donc le jeu des autres joueurs onupdate le init de la même manière

		//init de l'atout
		switch(Table.atout) {
		case Carreau:
			input[288] = 1;
			break;
		case Coeur:
			input[289] = 1;
			break;
		case Trefle:
			input[290] = 1;
			break;
		case Pique:
			input[291] = 1;
			break;
		}

		//init de la couleur en jeu
		//
		/*switch(Table.atout) {
		case Carreau:
			input[288] = 1;
			break;
		case Coeur:
			input[289] = 1;
			break;
		case Trefle:
			input[290] = 1;
			break;
		case Pique:
			input[291] = 1;
			break;
		}*/
		
		//au debut personne n'est maitre donc le dernier input reste à 0
	}


}
