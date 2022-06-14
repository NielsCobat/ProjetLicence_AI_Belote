package AI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import assets.Couleur;
import assets.Valeur;
import game.Carte;
import game.Manche;

public class Entrainement {

	public final static int NB_AI_PAR_GENERATION = 10, NB_GENERATION = 10, POURCENTAGE_REPROD_DEBUT = 50,
			POURCENTAGE_REPROD_FIN = 10;

	// controle le "taux d'apprentissage" durant l'optimisation des poids
	public final static double LEARNING_RATE = 0.1;

	static ArrayList<Carte> ensCartes = new ArrayList<Carte>();

	static HashMap<Integer, Integer> bests = new HashMap<Integer, Integer>();

	static ArrayList<NeuralNetwork> ais = new ArrayList<NeuralNetwork>();

	public static void setEnsCartes() {
		getEnsCartes().clear();
		getEnsCartes().add(new Carte(Couleur.Carreau, Valeur.Sept, 0));
		getEnsCartes().add(new Carte(Couleur.Carreau, Valeur.Huit, 0));
		getEnsCartes().add(new Carte(Couleur.Carreau, Valeur.Neuf, 0));
		getEnsCartes().add(new Carte(Couleur.Carreau, Valeur.Dix, 10));
		getEnsCartes().add(new Carte(Couleur.Carreau, Valeur.Valet, 2));
		getEnsCartes().add(new Carte(Couleur.Carreau, Valeur.Dame, 3));
		getEnsCartes().add(new Carte(Couleur.Carreau, Valeur.Roi, 4));
		getEnsCartes().add(new Carte(Couleur.Carreau, Valeur.As, 11));
		getEnsCartes().add(new Carte(Couleur.Coeur, Valeur.Sept, 0));
		getEnsCartes().add(new Carte(Couleur.Coeur, Valeur.Huit, 0));
		getEnsCartes().add(new Carte(Couleur.Coeur, Valeur.Neuf, 0));
		getEnsCartes().add(new Carte(Couleur.Coeur, Valeur.Dix, 10));
		getEnsCartes().add(new Carte(Couleur.Coeur, Valeur.Valet, 2));
		getEnsCartes().add(new Carte(Couleur.Coeur, Valeur.Dame, 3));
		getEnsCartes().add(new Carte(Couleur.Coeur, Valeur.Roi, 4));
		getEnsCartes().add(new Carte(Couleur.Coeur, Valeur.As, 11));
		getEnsCartes().add(new Carte(Couleur.Trefle, Valeur.Sept, 0));
		getEnsCartes().add(new Carte(Couleur.Trefle, Valeur.Huit, 0));
		getEnsCartes().add(new Carte(Couleur.Trefle, Valeur.Neuf, 0));
		getEnsCartes().add(new Carte(Couleur.Trefle, Valeur.Dix, 10));
		getEnsCartes().add(new Carte(Couleur.Trefle, Valeur.Valet, 2));
		getEnsCartes().add(new Carte(Couleur.Trefle, Valeur.Dame, 3));
		getEnsCartes().add(new Carte(Couleur.Trefle, Valeur.Roi, 4));
		getEnsCartes().add(new Carte(Couleur.Trefle, Valeur.As, 11));
		getEnsCartes().add(new Carte(Couleur.Pique, Valeur.Sept, 0));
		getEnsCartes().add(new Carte(Couleur.Pique, Valeur.Huit, 0));
		getEnsCartes().add(new Carte(Couleur.Pique, Valeur.Neuf, 0));
		getEnsCartes().add(new Carte(Couleur.Pique, Valeur.Dix, 10));
		getEnsCartes().add(new Carte(Couleur.Pique, Valeur.Valet, 2));
		getEnsCartes().add(new Carte(Couleur.Pique, Valeur.Dame, 3));
		getEnsCartes().add(new Carte(Couleur.Pique, Valeur.Roi, 4));
		getEnsCartes().add(new Carte(Couleur.Pique, Valeur.As, 11));
	}

	static int getBestsMinInd() {
		int res = -1;
		int min = 1000000;
		for (int i : bests.keySet()) {
			if (bests.get(i) < min) {
				res = i;
				min = bests.get(i);
			}
		}
		return res;
	}

	static int getBestsMaxInd() {
		int res = -1;
		int max = -1;
		for (int i : bests.keySet()) {
			if (bests.get(i) > max) {
				res = i;
				max = bests.get(i);
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		setEnsCartes();

		for (int id = 0; id < NB_AI_PAR_GENERATION; id++) { // initialisation des IA de bases
			ais.add(new NeuralNetwork("", id, 3)); // valeurs al�atoires d�j� en places dans le constructeur de base de
													// Matrix.java
		}

		Random r = new Random();

		ArrayList<NeuralNetwork> bestsAIs = new ArrayList<NeuralNetwork>();

		Manche manche = null;

		NeuralNetwork bestOne = null;

		for (int gen = 1; gen <= NB_GENERATION; gen++) {

			System.out.print("Ex�cution de la g�n�ration n�" + gen + " ... ");

			int nbReproduction = (int) (NB_AI_PAR_GENERATION
					* ((double) (((POURCENTAGE_REPROD_FIN - POURCENTAGE_REPROD_DEBUT) / NB_GENERATION) * (gen - 1)
							+ POURCENTAGE_REPROD_DEBUT) / 100));

			Couleur atout;
			int atoutCourant = r.nextInt(4) + 1; // selectionne un int entre 1 et 4
			switch (atoutCourant) {
			case 1:
				atout = Couleur.Trefle;
				break;
			case 2:
				atout = Couleur.Carreau;
				break;
			case 3:
				atout = Couleur.Coeur;
				break;
			case 4:
				atout = Couleur.Pique;
				break;
			default:
				throw new Exception("atout non compatible");
			}

			Collections.shuffle(getEnsCartes());

			for (int id = 0; id < NB_AI_PAR_GENERATION; id++) {
				int idPJ = r.nextInt(4) + 1;
				int idJP = r.nextInt(4) + 1;
				manche = new Manche(idJP, idPJ, (NeuralNetwork) ais.get(id), (NeuralNetwork) ais.get(id).clone(),
						(NeuralNetwork) ais.get(id).clone(), (NeuralNetwork) ais.get(id).clone(),
						(ArrayList<Carte>) getEnsCartes().clone());
				manche.j2.idPartenaire = 4;
				manche.j3.idPartenaire = 1;
				manche.j4.idPartenaire = 2;

				manche.runMancheEntrainement(atout);

				int indMin = getBestsMinInd();
				int score = manche.getPointsEquipe(1);
				if (bests.size() >= nbReproduction) {
					if (score > bests.get(indMin)) {
						bests.remove(indMin);
						bests.put(id, score);
					}
				} else
					bests.put(id, score);
			}

			for (int i : bests.keySet()) {
				bestsAIs.add(ais.get(i));
			}
			ais.clear();
			int nbReproParBest = NB_AI_PAR_GENERATION / nbReproduction - 1;
			int reste = NB_AI_PAR_GENERATION % nbReproduction;
			for (int i = 0; i < bestsAIs.size(); i++) {
				ais.add(bestsAIs.get(i));
				ais.get(ais.size() - 1).id = ais.size() - 1;
				for (int j = 0; j < nbReproParBest; j++) {
					NeuralNetwork toAdd = ais.get(ais.size() - 1).clone();
					// TODO ajouter l�g�res modifications des valeurs dans l'IA

					for (Matrix m : toAdd.allBias) {
						for (int k = 0; i < m.rows; i++) {
							for (int l = 0; j < m.cols; j++) {
								m.data[k][l] *= r.nextDouble() * LEARNING_RATE + 0.5;
							}
						}
					}
					for (Matrix m : toAdd.allHidden) {
						for (int k = 0; i < m.rows; i++) {
							for (int l = 0; j < m.cols; j++) {
								m.data[k][l] *= r.nextDouble() * LEARNING_RATE + 0.5;
							}
						}
					}
					for (Matrix m : toAdd.allWeightHidden) {
						for (int k = 0; i < m.rows; i++) {
							for (int l = 0; j < m.cols; j++) {
								m.data[k][l] *= r.nextDouble() * LEARNING_RATE + 0.5;
							}
						}
					}

					ais.add(toAdd);
				}
			}
			for (int i = 0; i < reste; i++) {
				ais.add(new NeuralNetwork("", ais.size(), 3));
			}

			int ind = getBestsMaxInd();
			bestOne = ais.get(ind);
			int bestOneScore = bests.get(ind);

			System.out.println("Fin");
			System.out.println(nbReproduction
					+ " ont �t� conserv�s pour la g�n�ration suivante. Le score de la meilleure IA de la g�n�ration est : "
					+ bestOneScore);

			bestsAIs.clear();
			bests.clear();

		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		LocalDateTime now = LocalDateTime.now();

		File sauvegardeBestOne = new File("results/" + dtf.format(now) + ".txt");
		try {
			sauvegardeBestOne.getParentFile().mkdir();
			if (sauvegardeBestOne.createNewFile()) {
				System.out.println("Fichier de sauvegarde cr��: " + sauvegardeBestOne.getName());

				try {
					FileWriter myWriter = new FileWriter("results/" + sauvegardeBestOne.getName());
					myWriter.write(NeuralNetwork.nbHiddenLayer + "_" + bestOne.getInput().length + "_"
							+ bestOne.getOutput().length + "_ //TODO nb neurones par couches cach�es");
					myWriter.write("//TODO fichier � compl�ter");
					myWriter.close();
					System.out.println("Fichier de sauvegarde compl�t� avec succ�s.");
				} catch (IOException e) {
					System.out.println("Une erreur a eu lieu.");
					e.printStackTrace();
				}

			} else {
				System.out.println("Un fichier de ce nom existe d�j�.");
			}
		} catch (IOException e) {
			System.out.println("Une erreur a eu lieu.");
			e.printStackTrace();
		}

	}

	public static ArrayList<Carte> getEnsCartes() {
		return ensCartes;
	}

}
