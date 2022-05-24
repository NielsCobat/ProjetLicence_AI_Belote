package AI;

import java.util.ArrayList;
import java.util.HashMap;

import assets.Couleur;
import assets.Valeur;
import game.Carte;
import game.Joueur;
import game.Manche;
import game.Table;

public class NeuralNetwork extends Joueur{

	//tableau des entr�es et sorties

	/*
	 * Position input:
	 * 	0 � 31: les cartes en main de l'ia 
	 * 	32 � 63 : les cartes jou�es par l'ia
	 * 
	 * 	64 � 95 et 128 � 159 et 192 � 223: les cartes en main des autres joueurs
	 * 	96 � 127 et 160 � 191 et 224 � 255: les cartes jou�es des autres joueurs
	 * 
	 * 	256 � 287: les cartes sur table
	 * 
	 * 	288 � 291: l'atout
	 * 	292 � 295: couleur en jeu
	 * 
	 * 	296: 1 si le ma�tre est dans ton �quipe
	 */
	private double[] input; 

	/*
	 * Position output:
	 * 0 �31: les 32 cartes du jeu parmi lesquelles l'ia va faire son choix
	 */
	private double[] output;

	//hashMap de reference pour les positions des inputs et outputs
	private HashMap<Carte, Integer> posCartesInput = new HashMap<Carte, Integer>();
	private HashMap<Integer,Carte> posCartesOutput = new HashMap<Integer,Carte>();

	//variables utiles pour les fonctions
	private int compteurJoueur = 1;

	/*
	 * Constructeur neural network
	 */
	public NeuralNetwork(String nom, int id, int partenaire) {
		super(nom, id, partenaire);
		this.main = new ArrayList<Carte>(); // A la creation main forcement vide
		this.input = new double[296]; 
		this.output = new double[31];
		this.isIA = true;
	}

	/*
	 * Remplit le hashMap output
	 */
	void initHashmapOutput() {
		posCartesOutput.put(0, new Carte(Couleur.Carreau, Valeur.Sept, 0));
		posCartesOutput.put(1,new Carte(Couleur.Carreau, Valeur.Huit, 0));
		posCartesOutput.put(2,new Carte(Couleur.Carreau, Valeur.Neuf, 0));
		posCartesOutput.put(3,new Carte(Couleur.Carreau, Valeur.Dix, 10));
		posCartesOutput.put(4,new Carte(Couleur.Carreau, Valeur.Valet, 2));
		posCartesOutput.put(5,new Carte(Couleur.Carreau, Valeur.Dame, 3));
		posCartesOutput.put(6,new Carte(Couleur.Carreau, Valeur.Roi, 4));
		posCartesOutput.put(7,new Carte(Couleur.Carreau, Valeur.As, 11));
		posCartesOutput.put(8,new Carte(Couleur.Coeur, Valeur.Sept, 0));
		posCartesOutput.put(9,new Carte(Couleur.Coeur, Valeur.Huit, 0));
		posCartesOutput.put(10,new Carte(Couleur.Coeur, Valeur.Neuf, 0));
		posCartesOutput.put(11,new Carte(Couleur.Coeur, Valeur.Dix, 10));
		posCartesOutput.put(12,new Carte(Couleur.Coeur, Valeur.Valet, 2));
		posCartesOutput.put(13,new Carte(Couleur.Coeur, Valeur.Dame, 3));
		posCartesOutput.put(14,new Carte(Couleur.Coeur, Valeur.Roi, 4));
		posCartesOutput.put(15,new Carte(Couleur.Coeur, Valeur.As, 11));
		posCartesOutput.put(16,new Carte(Couleur.Trefle, Valeur.Sept, 0));
		posCartesOutput.put(17,new Carte(Couleur.Trefle, Valeur.Huit, 0));
		posCartesOutput.put(18,new Carte(Couleur.Trefle, Valeur.Neuf, 0));
		posCartesOutput.put(19,new Carte(Couleur.Trefle, Valeur.Dix, 10));
		posCartesOutput.put(20,new Carte(Couleur.Trefle, Valeur.Valet, 2));
		posCartesOutput.put(21,new Carte(Couleur.Trefle, Valeur.Dame, 3));
		posCartesOutput.put(22,new Carte(Couleur.Trefle, Valeur.Roi, 4));
		posCartesOutput.put(23,new Carte(Couleur.Trefle, Valeur.As, 11));
		posCartesOutput.put(24,new Carte(Couleur.Pique, Valeur.Sept, 0));
		posCartesOutput.put(25,new Carte(Couleur.Pique, Valeur.Huit, 0));
		posCartesOutput.put(26,new Carte(Couleur.Pique, Valeur.Neuf, 0));
		posCartesOutput.put(27,new Carte(Couleur.Pique, Valeur.Dix, 10));
		posCartesOutput.put(28,new Carte(Couleur.Pique, Valeur.Valet, 2));
		posCartesOutput.put(29,new Carte(Couleur.Pique, Valeur.Dame, 3));
		posCartesOutput.put(30,new Carte(Couleur.Pique, Valeur.Roi, 4));
		posCartesOutput.put(31,new Carte(Couleur.Pique, Valeur.As, 11));
	}

	/*
	 * Remplit le hashMap input
	 */
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

		//on regarde la main du joueur et on update le init
		for (Carte carte : this.main) {
			input[posCartesInput.get(carte)] = 1;
		}


		//TODO grace � la fonction qui calcule l'ordre des cartes et donc le jeu des autres joueurs onupdate le init de la m�me mani�re
		//solution interm�diaire pour savoir le jeu des autres joueurs
		if(this.id != 1) {
			for (Carte carte : Table.joueur1.main) {
				input[compteurJoueur*64 +posCartesInput.get(carte)] = 1;
				compteurJoueur++;
			}
		}
		if(this.id != 2) {
			for (Carte carte : Table.joueur2.main) {
				input[compteurJoueur*64 +posCartesInput.get(carte)] = 1;
				compteurJoueur++;
			}
		}
		if(this.id != 3) {
			for (Carte carte : Table.joueur3.main) {
				input[compteurJoueur*64 +posCartesInput.get(carte)] = 1;
				compteurJoueur++;
			}
		}
		if(this.id != 4) {
			for (Carte carte : Table.joueur4.main) {
				input[compteurJoueur*64 +posCartesInput.get(carte)] = 1;
				compteurJoueur++;
			}
		}

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

		///au debut pas de couleur demand�e

		//au debut personne n'est maitre donc le dernier input reste � 0
	}

	/**
	 * joue le coup et met � jour les inputs
	 * @param carte La carte jou�e
	 */
	protected void joueCoup(Carte carte) {
		//calcul de la meilleure carte � jouer
		int indice = 0;
		double maxNum = output[0];

		do {
			for(int j = 0; j < 32; j++) {
				if(output[j] > maxNum) {
					maxNum = output[j];
					indice = j;
					// on met l'output � zero pour que si cet output n'est pas l�gal, qu'il ne soit pas re-selectionn� � la boucle suivante
					output[j] = 0;
				}
			}
		}while((!isLegalMove(posCartesOutput.get(indice))) && (main.contains(posCartesOutput.get(indice))));


		super.joueCoup(carte);
		input[posCartesInput.get(carte)] = 0;
		input[posCartesInput.get(carte) + 32] = 1;
	}

	//TODO faire le joueCoup des autres joueurs

	/**
	 * Met � jour les cartes du pli dans les inputs
	 */
	void setCartesSurTable() {
		Manche manche = Table.mancheCourante;
		Carte[] cartesDuPli = manche.getPli(manche.getNbPlis()).getCartes();
		for(int i =0; i < cartesDuPli.length; i++) {
			input[posCartesInput.get(cartesDuPli[i]) + 256] = 1;
		}
	}

	/*
	 * Met � jour la couleur demand�e
	 */
	void setCouleurDemandee() {
		Manche manche = Table.mancheCourante;
		Couleur couleurEnCours = manche.getPli(manche.getNbPlis()).getCouleurDemandee();

		switch(couleurEnCours) {
		case Carreau:
			input[292] = 1;
			break;
		case Coeur:
			input[293] = 1;
			break;
		case Trefle:
			input[294] = 1;
			break;
		case Pique:
			input[295] = 1;
			break;
		}
	}


	/*
	 * Met � jour le joueur gagnant 
	 */
	void setMaitre() {
		Manche manche = Table.mancheCourante;
		int joueurGagnant = manche.getPli(manche.getNbPlis()).getIdJoueurGagnant();
		if(this.idPartenaire == joueurGagnant ) {
			input[296] =1;
		}
	}

	/*
	 * Reset le pli
	 */
	void resetPli() {
		for(int i = 256; i < 288; i++) {
			input[i] = 0;
		}
	}

	/*
	 * Reset la manche
	 */
	void resetManche() {
		for(int i = 0; i < 297; i++) {
			input[i] = 0;
		}
	}
	
	/*
	 * 
	 */
	void runIA() {
		//TODO
	}

}