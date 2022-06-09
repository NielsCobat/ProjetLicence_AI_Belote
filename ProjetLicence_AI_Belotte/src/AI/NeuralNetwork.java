package AI;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import assets.Couleur;
import assets.Valeur;
import game.Carte;
import game.Joueur;
import game.Manche;
import game.Table;

public class NeuralNetwork extends Joueur {

	// tableau des entrées et sorties

	/*
	 * Position input: 0 à 31: les cartes en main de l'ia 32 à 63 : les cartes
	 * jouées par l'ia
	 * 
	 * 64 à 95 et 128 à 159 et 192 à 223: les cartes en main des autres joueurs 96 à
	 * 127 et 160 à 191 et 224 à 255: les cartes jouées des autres joueurs
	 * 
	 * 256 à 287: les cartes sur table
	 * 
	 * 288 à 291: l'atout 292 à 295: couleur en jeu
	 * 
	 * 296: 1 si le maître est dans ton équipe
	 */
	private double[] input;

	/*
	 * Position output: 0 à31: les 32 cartes du jeu parmi lesquelles l'ia va faire
	 * son choix
	 */
	private double[] output;

	// hashMap de reference pour les positions des inputs et outputs
	public HashMap<Carte, Integer> posCartesInput = new HashMap<Carte, Integer>();
	private HashMap<Integer, Carte> posCartesOutput = new HashMap<Integer, Carte>();

	// variables utiles pour les fonctions
	private int compteurJoueur = 1;

	// nbHiddenLayers doit être un minimum de 2
	public static final int nbHiddenLayer = 5;
	ArrayList<Matrix> allHidden = new ArrayList<Matrix>();
	ArrayList<Matrix> allWeightHidden = new ArrayList<Matrix>();
	ArrayList<Matrix> allBias = new ArrayList<Matrix>();

	/*
	 * Constructeur neural network
	 */
	public NeuralNetwork(String nom, int id, int partenaire) {
		super(nom, id, partenaire);
		this.main = new ArrayList<Carte>(); // A la creation main forcement vide

		this.input = new double[297];
		this.output = new double[32];

		// r sert aux calculs du nombre de neurones pour chaque hidden layer
		int r = (input.length / output.length) ^ (1 / (nbHiddenLayer + 1));

		// initialisation de chaque matrices correspondant aux poids/synapses enrte
		// neurones le tout rassemblés dans une liste
		// idem pour biais
		this.allWeightHidden.add(new Matrix((output.length * (r) ^ (nbHiddenLayer)), input.length));
		this.allBias.add(new Matrix((output.length * (r) ^ (nbHiddenLayer)), 1));

		for (int i = 0; i < nbHiddenLayer - 1; i++) {
			this.allHidden.add(new Matrix((output.length * (r) ^ (nbHiddenLayer - i)), 1));
			this.allWeightHidden.add(new Matrix((output.length * (r) ^ (nbHiddenLayer - i)),
					(output.length * (r) ^ (nbHiddenLayer - (i+1)))));
			this.allBias.add(new Matrix((output.length * (r) ^ (nbHiddenLayer - (i) )), 1));
		}
		this.allHidden.add(new Matrix(output.length * (r), 1));
		this.allWeightHidden.add(new Matrix(output.length, output.length * (r)));
		this.allBias.add(new Matrix(output.length, 1));
	}

	/*
	 * Remplit le hashMap output
	 */
	public void initHashmapOutput() {
		posCartesOutput.put(0, new Carte(Couleur.Carreau, Valeur.Sept, 0));
		posCartesOutput.put(1, new Carte(Couleur.Carreau, Valeur.Huit, 0));
		posCartesOutput.put(2, new Carte(Couleur.Carreau, Valeur.Neuf, 0));
		posCartesOutput.put(3, new Carte(Couleur.Carreau, Valeur.Dix, 10));
		posCartesOutput.put(4, new Carte(Couleur.Carreau, Valeur.Valet, 2));
		posCartesOutput.put(5, new Carte(Couleur.Carreau, Valeur.Dame, 3));
		posCartesOutput.put(6, new Carte(Couleur.Carreau, Valeur.Roi, 4));
		posCartesOutput.put(7, new Carte(Couleur.Carreau, Valeur.As, 11));
		posCartesOutput.put(8, new Carte(Couleur.Coeur, Valeur.Sept, 0));
		posCartesOutput.put(9, new Carte(Couleur.Coeur, Valeur.Huit, 0));
		posCartesOutput.put(10, new Carte(Couleur.Coeur, Valeur.Neuf, 0));
		posCartesOutput.put(11, new Carte(Couleur.Coeur, Valeur.Dix, 10));
		posCartesOutput.put(12, new Carte(Couleur.Coeur, Valeur.Valet, 2));
		posCartesOutput.put(13, new Carte(Couleur.Coeur, Valeur.Dame, 3));
		posCartesOutput.put(14, new Carte(Couleur.Coeur, Valeur.Roi, 4));
		posCartesOutput.put(15, new Carte(Couleur.Coeur, Valeur.As, 11));
		posCartesOutput.put(16, new Carte(Couleur.Trefle, Valeur.Sept, 0));
		posCartesOutput.put(17, new Carte(Couleur.Trefle, Valeur.Huit, 0));
		posCartesOutput.put(18, new Carte(Couleur.Trefle, Valeur.Neuf, 0));
		posCartesOutput.put(19, new Carte(Couleur.Trefle, Valeur.Dix, 10));
		posCartesOutput.put(20, new Carte(Couleur.Trefle, Valeur.Valet, 2));
		posCartesOutput.put(21, new Carte(Couleur.Trefle, Valeur.Dame, 3));
		posCartesOutput.put(22, new Carte(Couleur.Trefle, Valeur.Roi, 4));
		posCartesOutput.put(23, new Carte(Couleur.Trefle, Valeur.As, 11));
		posCartesOutput.put(24, new Carte(Couleur.Pique, Valeur.Sept, 0));
		posCartesOutput.put(25, new Carte(Couleur.Pique, Valeur.Huit, 0));
		posCartesOutput.put(26, new Carte(Couleur.Pique, Valeur.Neuf, 0));
		posCartesOutput.put(27, new Carte(Couleur.Pique, Valeur.Dix, 10));
		posCartesOutput.put(28, new Carte(Couleur.Pique, Valeur.Valet, 2));
		posCartesOutput.put(29, new Carte(Couleur.Pique, Valeur.Dame, 3));
		posCartesOutput.put(30, new Carte(Couleur.Pique, Valeur.Roi, 4));
		posCartesOutput.put(31, new Carte(Couleur.Pique, Valeur.As, 11));
	}

	/*
	 * Remplit le hashMap input
	 */
	public void initHashmap() {
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
	public void initInput() {
		initHashmap();
		initHashmapOutput();
		// on regarde la main du joueur et on update le init
		for (Carte carte : this.main) {
			for (Carte c2 : this.posCartesInput.keySet()) {
				if (carte.equal(c2))
					this.input[posCartesInput.get(c2)] = 1;
			}
		}

		// TODO grace à la fonction qui calcule l'ordre des cartes et donc le jeu des
		// autres joueurs onupdate le init de la même manière
		// solution intermédiaire pour savoir le jeu des autres joueurs
		if (this.id != 1) {
			for (Carte carte : Table.joueur1.main) {
				for (Carte c2 : this.posCartesInput.keySet()) {
					if (carte.equal(c2))
						getInput()[compteurJoueur * 64 + posCartesInput.get(carte)] = 1;
				}
				compteurJoueur++;
			}
		}
		if (this.id != 2) {
			for (Carte carte : Table.joueur2.main) {
				for (Carte c2 : this.posCartesInput.keySet()) {
					if (carte.equal(c2))
						getInput()[compteurJoueur * 64 + posCartesInput.get(carte)] = 1;
				}
				compteurJoueur++;
			}
		}
		if (this.id != 3) {
			for (Carte carte : Table.joueur3.main) {
				for (Carte c2 : this.posCartesInput.keySet()) {
					if (carte.equal(c2))
						getInput()[compteurJoueur * 64 + posCartesInput.get(carte)] = 1;
				}
				compteurJoueur++;
			}
		}
		if (this.id != 4) {
			for (Carte carte : Table.joueur4.main) {
				for (Carte c2 : this.posCartesInput.keySet()) {
					if (carte.equal(c2))
						getInput()[compteurJoueur * 64 + posCartesInput.get(carte)] = 1;
				}
				compteurJoueur++;
			}
		}

		// init de l'atout
		switch (Table.atout) {
		case Carreau:
			getInput()[288] = 1;
			break;
		case Coeur:
			getInput()[289] = 1;
			break;
		case Trefle:
			getInput()[290] = 1;
			break;
		case Pique:
			getInput()[291] = 1;
			break;
		}

		/// au debut pas de couleur demandée

		// au debut personne n'est maitre donc le dernier input reste à 0
	}

	/**
	 * forward propagation de tousle réseau neuronal, les outputs soont prêtes à
	 * être utilisées après
	 */
	double[] forwardPropagation() {

		Matrix input = Matrix.fromArray(this.input);

		// calculs pour la première couche (en lien avec les inputs)
		Matrix hidden = Matrix.multiply(allWeightHidden.get(0), input);
		hidden.add(allBias.get(0));
		//System.out.println("shape mismatch input");
		hidden.sigmoid();

		allHidden.set(0, hidden);

		// calculs pour toutes les couches intermédiaires
		for (int i = 1; i < nbHiddenLayer; i++) {
			Matrix hidden2 = Matrix.multiply(allWeightHidden.get(i), allHidden.get(i-1));
			hidden2.add(allBias.get(i));
			//System.out.println("shape mismatch hidden");
			hidden2.sigmoid();

			allHidden.set(i, hidden2);
		}
		// calculs pour la dernière couche des hiddenLayers(en lien avec les outputs)
		Matrix output = Matrix.multiply(allWeightHidden.get(nbHiddenLayer), allHidden.get(nbHiddenLayer-1));
		output.add(allBias.get(nbHiddenLayer));
		//System.out.println("shape mismatch output");
		output.sigmoid();

		allHidden.set(allHidden.size() - 1, output); 

		return output.toDouble();
	}

	/**
	 * joue le coup et met à jour les inputs
	 */
	public Carte joueCoup() {

		// mises à jour inputs
		setCouleurDemandee();
		setCartesSurTable();
		setMaitre();

		// forward propagation
		this.output = forwardPropagation();

		// calcul de la meilleure carte à jouer
		int indice = 0;
		double maxNum = output[0];

		do {
			for (int j = 0; j < 32; j++) {
				if (output[j] > maxNum) {
					maxNum = output[j];
					indice = j;
					// on met l'output à zero pour que si cet output n'est pas légal, qu'il ne soit
					// pas re-selectionné à la boucle suivante
					output[j] = 0;
				}
			}
		} while ((!isLegalMove(posCartesOutput.get(indice))) || (!main.contains(posCartesOutput.get(indice))));

		// TODO remettre la ligne lorsque l'ia n'est plus en entrainement
		// super.joueCoup(posCartesOutput.get(indice));
		getInput()[posCartesInput.get(posCartesOutput.get(indice))] = 0;
		getInput()[posCartesInput.get(posCartesOutput.get(indice)) + 32] = 1;
		return posCartesOutput.get(indice);
	}
	
	public Carte joueCoup(Couleur couleurDemandee, Carte[] pli, int joueurGagnant, Manche manche, Couleur atout) {

		// mises à jour inputs
		if (couleurDemandee != null)
			setCouleurDemandee(couleurDemandee);
		setCartesSurTable(pli);
		setMaitre(joueurGagnant);

		// forward propagation
		this.output = forwardPropagation();

		// calcul de la meilleure carte à jouer
		int indice = 0;
		double maxNum = output[0];
		initHashmap();
		initHashmapOutput();

		do {
			for (int j = 0; j < output.length; j++) {
				if (output[j] > maxNum) {
					maxNum = output[j];
					indice = j;
					// on met l'output à zero pour que si cet output n'est pas légal, qu'il ne soit
					// pas re-selectionné à la boucle suivante
					output[j] = 0;
				}
			}
			System.out.println(indice +"  "+ maxNum);
			System.out.println(posCartesOutput.get(indice));
		} while ((!isLegalMove(posCartesOutput.get(indice), manche, atout)) || (!main.contains(posCartesOutput.get(indice))));

		// TODO remettre la ligne lorsque l'ia n'est plus en entrainement
		// super.joueCoup(posCartesOutput.get(indice));
		getInput()[posCartesInput.get(posCartesOutput.get(indice))] = 0;
		getInput()[posCartesInput.get(posCartesOutput.get(indice)) + 32] = 1;
		return posCartesOutput.get(indice);
	}

	/**
	 * Met à jour les cartes du pli dans les inputs
	 */
	void setCartesSurTable() {
		Manche manche = Table.mancheCourante;
		Carte[] cartesDuPli = manche.getPli(manche.getNbPlis()).getCartes();
		for (int i = 0; i < cartesDuPli.length; i++) {
			getInput()[posCartesInput.get(cartesDuPli[i]) + 256] = 1;
		}
	}
	
	void setCartesSurTable(Carte[] cartesDuPli) {
		for (int i = 0; i < cartesDuPli.length; i++) {
			for (Carte c2 : this.posCartesInput.keySet()) {
                if (cartesDuPli[i].equal(c2))
                	getInput()[posCartesInput.get(cartesDuPli[i]) + 256] = 1;
            }
		}
	}

	/*
	 * Met à jour la couleur demandée
	 */
	void setCouleurDemandee() {
		Manche manche = Table.mancheCourante;
		Couleur couleurEnCours = manche.getPli(manche.getNbPlis()).getCouleurDemandee();

		switch (couleurEnCours) {
		case Carreau:
			getInput()[292] = 1;
			break;
		case Coeur:
			getInput()[293] = 1;
			break;
		case Trefle:
			getInput()[294] = 1;
			break;
		case Pique:
			getInput()[295] = 1;
			break;
		}
	}
	
	void setCouleurDemandee(Couleur couleurEnCours) {

		switch (couleurEnCours) {
		case Carreau:
			getInput()[292] = 1;
			break;
		case Coeur:
			getInput()[293] = 1;
			break;
		case Trefle:
			getInput()[294] = 1;
			break;
		case Pique:
			getInput()[295] = 1;
			break;
		}
	}

	/*
	 * Met à jour le joueur gagnant
	 */
	void setMaitre() {
		Manche manche = Table.mancheCourante;
		int joueurGagnant = manche.getPli(manche.getNbPlis()).getIdJoueurGagnant();
		if (this.idPartenaire == joueurGagnant) {
			getInput()[296] = 1;
		} else
			getInput()[296] = 0;
	}
	
	void setMaitre(int joueurGagnant) {
		if (this.idPartenaire == joueurGagnant) {
			getInput()[296] = 1;
		} else
			getInput()[296] = 0;
	}

	/*
	 * Reset le pli
	 */
	public void resetPli() {
		for (int i = 256; i < 288; i++) {
			getInput()[i] = 0;
		}
	}

	/*
	 * Reset la manche
	 */
	public void resetManche() {
		for (int i = 0; i < 297; i++) {
			getInput()[i] = 0;
		}
	}

	public double[] getInput() {
		return input;
	}

	public double[] getOutput() {
		return output;
	}

}
