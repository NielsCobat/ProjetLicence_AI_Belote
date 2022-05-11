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

	public int equipeGagnante() {
		return 0; // TODO penser à changer this.idJoueurGagnant
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

	public int calculPoints() {
		return 0; // TODO
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

}
