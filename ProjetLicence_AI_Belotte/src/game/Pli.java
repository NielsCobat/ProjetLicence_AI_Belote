package game;

import assets.Couleur;

public class Pli {

	private int idPremierJoueur, nbCarte, idJoueurGagnant;
	private Couleur couleurDemandee;
	private Carte[] cartes;

	public Pli(int idPremierJoueur) {
		this.idPremierJoueur = idPremierJoueur;
		this.cartes = new Carte[4];
		this.couleurDemandee = null;
		this.nbCarte = 0;
		this.idJoueurGagnant = 0;
	}

	/**
	 * Retourne l'indice dans le tableau des scores, present dans classe Manche, de l'equipe qui remporte le pli
	 * @return 0 si equipe1 gagne 1 si equipe2 gagne
	 */
	public int equipeGagnante() {
		int idGagnant = idPremierJoueur + indiceMeilleureCarte();
		if (idGagnant>4) idGagnant -= 4;
		idJoueurGagnant = idGagnant;
		return idGagnant<=2? 0 : 1;
	}
	
	/**
	 * 
	 * @return indice de la meilleure carte dans le pli
	 */
	private int indiceMeilleureCarte() {
		Carte meilleureCarte = cartes[0];
		int res=0;
		for (int i = 1; i < cartes.length; i++) {
			if(meilleureCarte.compareTo(cartes[i])==-1) {
				meilleureCarte = cartes[i];
				res = i;
			}
		}
		return res;
	}

	public void addCarte(Carte c) {
		if (this.nbCarte == 0)
			this.couleurDemandee = c.getCouleur();
		try {
			this.cartes[nbCarte] = c;
			this.nbCarte++;
		} catch (Exception e) {
			System.out.println("game.Pli.addCarte() : maximum de carte pour ce pli déjà atteint");
		}
	}

	/**
	 * 
	 * @return total des points d'un pli
	 */
	public int calculPoints() {
		int res = 0;
		for (Carte carte : cartes) {
			res += carte.getPoints();
		}
		return res;
	}

	public int getIdPremierJoueur() {
		return idPremierJoueur;
	}

	public int getNbCarte() {
		return nbCarte;
	}

	public Couleur getCouleurDemandee() {
		return couleurDemandee;
	}

	public int getIdJoueurGagnant() {
		return this.idJoueurGagnant;
	}
	
	public Carte[] getCartes() {
		return this.cartes;
	}

}
