package AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import assets.Couleur;
import assets.Valeur;
import game.Carte;
import game.Manche;

public class Entrainement {
	
	private final static int NB_AI_PAR_GENERATION = 10, NB_GENERATION = 10, POURCENTAGE_REPROD_DEBUT = 50, POURCENTAGE_REPROD_FIN = 10;
	
	static ArrayList<Carte> ensCartes = new ArrayList<Carte>();
	
	static HashMap<Integer, Integer> bests = new HashMap<Integer, Integer>();
	
	static ArrayList<NeuralNetwork> ais = new ArrayList<NeuralNetwork>();
	
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
	}
	
	static int getBestsMinInd() {
		int res = -1;
		int min = 1000000;
		for (int i = 0; i < bests.size(); i++) {
			if (bests.get(i) < min)
				res = i;
		}
		return res;
	}

	public static void main(String[] args) throws Exception {
		
		setEnsCartes();
		
		for (int id = 0; id < NB_AI_PAR_GENERATION; id++) { // initialisation des IA de bases
			ais.add(new NeuralNetwork("", id, 0));
			//TODO mettre des valeurs aléatoires
		}
		
		Random r = new Random();
		
		ArrayList<NeuralNetwork> bestsAIs = new ArrayList<NeuralNetwork>();
		
		Manche manche;
		
		for (int gen = 0; gen < NB_GENERATION; gen++) {
			
			int nbReproduction = ((POURCENTAGE_REPROD_FIN - POURCENTAGE_REPROD_DEBUT) / NB_GENERATION) * gen + POURCENTAGE_REPROD_DEBUT;
			
			bests.clear();
			
			Collections.shuffle(ensCartes);
			
			for (int id = 0; id < NB_AI_PAR_GENERATION; id++) {
				int idPJ = r.nextInt(4) + 1;
				int idJP = r.nextInt(4) + 1;
				manche = new Manche(idJP, idPJ); //TODO autre constructeur avec joueurs et ens cartes, mettre l'IA principale (ais.get(id)) en 1
				
				//TODO exécution de la manche
				
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
			int nbReproParBest = NB_AI_PAR_GENERATION / nbReproduction;
			int reste = NB_AI_PAR_GENERATION % nbReproduction;
			for (int i = 0; i < bestsAIs.size(); i++) {
				ais.add(bestsAIs.get(i));
				//ais.get(ais.size() - 1).id = ais.size() - 1; //TODO
				for (int j = 0; j < nbReproParBest; j++) {
					ais.add(new NeuralNetwork("", ais.size(), 0)); //TODO idem initialisation
					//TODO ajouter légères modifications des valeurs dans l'IA
				}
			}
			for (int i = 0; i < reste; i++) {
				ais.add(new NeuralNetwork("", ais.size(), 0));
				//TODO valeurs aléatoires pour l'IA
			}
			bestsAIs.clear();
			
		}
	}

}
