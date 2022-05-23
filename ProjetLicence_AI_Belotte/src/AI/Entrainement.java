package AI;

import java.util.ArrayList;
import java.util.Collections;

import assets.Couleur;
import assets.Valeur;
import game.Carte;

public class Entrainement {
	
	private final static int NB_AI_PAR_GENERATION = 10, NB_GENERATION = 10;
	
	static ArrayList<Carte> ensCartes = new ArrayList<Carte>();
	
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

	public static void main(String[] args) {
		setEnsCartes();
		for (int gen = 0; gen < NB_GENERATION; gen++) {
			Collections.shuffle(ensCartes);
			for (int id = 0; id < NB_AI_PAR_GENERATION; id++) {
				
			}
		}
	}

}
