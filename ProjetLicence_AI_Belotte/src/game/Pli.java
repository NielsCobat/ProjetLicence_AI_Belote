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
		int idGagnant = (idPremierJoueur + indiceMeilleureCarte())%4;
		if (idGagnant == 0) idJoueurGagnant = 4;
		else idJoueurGagnant = idGagnant;
		if(idJoueurGagnant == 1 || idJoueurGagnant == 3) return 1;
		else return 2;
	}
	
	/**
	 * 
	 * @return indice de la meilleure carte dans le pli
	 */
	private int indiceMeilleureCarte() {
		Carte meilleureCarte = cartes[0];
		int res=0;
		for (int i = 1; i < cartes.length; i++) {
			if(cartes[i] != null && meilleureCarte.compareTo(cartes[i])==-1) {
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
			if (carte != null) {
				res += carte.getPoints();
				if(carte.getValeur().name().equals("Valet") && carte.getCouleur().name().equals(Table.atout.name())) res += 18;//valet d'atout = 18 points de plus
				if(carte.getValeur().name().equals("Neuf") && carte.getCouleur().name().equals(Table.atout.name())) res += 14;
			}
			
		}
		return res;
	}

//	public int getIdPremierJoueur() {
//		return idPremierJoueur;
//	}

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
	
	public String toString() {
		String res = "";
		for(Carte c : cartes) {
			if (c==null)break;
			res += c.toString() + " | ";
		}
		return res;
	}

}
