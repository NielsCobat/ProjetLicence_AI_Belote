package game;

import assets.Signe;

public class Pli {
	
	private int idPremierJoueur, nbCarte, idJoueurGagnant;
	private Signe couleurDemandee;
	//private Carte[] cartes;
	
	public Pli(int idPremierJoueur) {
		this.idPremierJoueur = idPremierJoueur;
		//this.cartes = new Carte[4];
		this.couleurDemandee = null;
		this.nbCarte = 0;
		this.idJoueurGagnant = 0;
	}
	
	public int equipeGagnante() {
		return 0; //TODO penser à changer this.idJoueurGagnant
	}
	
	/*public void addCarte(Carte c) {
		if (this.nbCarte == 0)
			this.couleurDemandee = c.getSigne();
		this.cartes[nbCarte] = c;
		this.nbCarte++;
	}*/
	
	public int calculPoints() {
		return 0; //TODO
	}

	public int getIdPremierJoueur() {
		return idPremierJoueur;
	}

	public int getNbCarte() {
		return nbCarte;
	}

	public Signe getCouleurDemandee() {
		return couleurDemandee;
	}
	
	public int getIdJoueurGagnant() {
		return this.idJoueurGagnant;
	}

}
