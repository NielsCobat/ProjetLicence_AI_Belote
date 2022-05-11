package game;

import assets.Couleur;
import main.Main;

public class Manche {

	private Pli[] plis;
	private Couleur atout;
	private int idPremierJoueur, equipePreneur, nbPlis;
	private int[] pointsEquipe;

	/**
	 * Constructeur de la classe Manche
	 * 
	 * @param atout           l'atout de la premi�re manche
	 * @param idPremierJoueur id du premier joueur � jouer
	 * @param joueurPreneur   id du joueur prenant l'atout
	 * @throws Exception si l'un des id de joueurs n'est pas valide
	 */
	public Manche(Couleur atout, int idPremierJoueur, int joueurPreneur) throws Exception {
		if ((this.idPremierJoueur > 4 || this.idPremierJoueur < 1) && (joueurPreneur > 4 || joueurPreneur < 1))
			throw new Exception("game.Manche.Manche() : Un id de joueur n'est pas valide");

		this.plis = new Pli[8];
		this.atout = atout;
		this.idPremierJoueur = idPremierJoueur;
		this.equipePreneur = ((joueurPreneur % 2) + 4) % 2 - 1; // soit 0 = joueurs 1 et 3 ; 1 = joueurs 2 et 4
		this.pointsEquipe = new int[2];
		this.pointsEquipe[0] = 0; // joueurs 1 et 3
		this.pointsEquipe[1] = 0; // joueurs 2 et 4
		this.nbPlis = 0;
	}

	/**
	 * Gestion de la fin d'une manche (points etc)
	 */
	public void finManche() {
		if (this.nbPlis == 8) {

			// calcul des points dans la classe Partie � la fin
			
			if (Table.joueur1.hasBelote() || Table.joueur3.hasBelote())
				
			
			if (this.pointsEquipe[0] == 162)
				this.pointsEquipe[0] = 252;
			else if (this.pointsEquipe[1] == 162)
				this.pointsEquipe[1] = 252;
			else if (this.pointsEquipe[(this.equipePreneur) % 2] == 81 || this.pointsEquipe[(this.equipePreneur) % 2] + 0 == 81) {
				this.pointsEquipe[this.equipePreneur] = 0;
				this.pointsEquipe[(this.equipePreneur) % 2] = 81;
			} else if (this.pointsEquipe[this.equipePreneur] < 81) {
				this.pointsEquipe[this.equipePreneur] = 0;
				this.pointsEquipe[(this.equipePreneur + 1) % 2] = 162;
			}
		}
	}

	/**
	 * Reset de la manche courante
	 * 
	 * @param atout         le nouvel atout
	 * @param joueurPreneur le joueur prenant
	 * @throws Exception si l'id du joueur prenant n'est pas valide
	 */
	public void reset(Couleur atout, int joueurPreneur) throws Exception {
		if (joueurPreneur > 4 || joueurPreneur < 1)
			throw new Exception("game.Manche.reset() : Un id de joueur n'est pas valide");

		this.atout = atout;
		if (this.idPremierJoueur < 4)
			this.idPremierJoueur++;
		else
			this.idPremierJoueur = 1;
		this.equipePreneur = ((joueurPreneur % 2) + 4) % 2 - 1; // soit 0 = joueurs 1 et 3 ; 1 = joueurs 2 et 4
		this.pointsEquipe[0] = 0; // joueurs 1 et 3
		this.pointsEquipe[1] = 0; // joueurs 2 et 4
		this.nbPlis = 0;
	}

	/**
	 * Ajout du score d'un pli � une �quipe
	 * 
	 * @param equipe l'id de l'�quipe (0 pour joueurs 1 3 et 1 pour joueurs 2 4)
	 * @param score  le score � incr�menter
	 */
	public void addScoreEquipe(int equipe, int score) {
		this.pointsEquipe[equipe] += score;
	}

	/**
	 * Initialisation du pli suivant et incr�mentation du score du pli pr�c�dent au
	 * score de la manche
	 * 
	 * @throws Exception si le pli pr�c�dent n'est pas termin� ou si le nombre de
	 *                   pli maximum a d�j� �t� atteint
	 */
	public void initPliSuivant() throws Exception {
		if (this.plis[this.nbPlis - 1].getNbCarte() < 4)
			throw new Exception("game.Manche.initPliSuivant() : le pli actuel n'est pas termin�");
		if (this.nbPlis == 8)
			throw new Exception("game.Manche.initPliSuivant() : la manche contient d�j� 8 plis");
		this.pointsEquipe[this.plis[this.nbPlis - 1].equipeGagnante()] += this.plis[this.nbPlis - 1].calculPoints();
		this.plis[this.nbPlis] = new Pli(this.plis[this.nbPlis - 1].getIdJoueurGagnant());
		this.nbPlis++;
	}

	/**
	 * Getter de l'atout de la manche courante
	 * 
	 * @return l'atout
	 */
	public Couleur getAtout() {
		return this.atout;
	}

	/**
	 * Getter d'un seul pli
	 * 
	 * @param noPli le num�ro du pli souhait� (de 1 � 8)
	 * @return l'objet du pli en question
	 */
	public Pli getPli(int noPli) {
		return this.plis[noPli - 1];
	}

	/**
	 * Getter de l'id du premier joueur de la manche (joueur apr�s le distribueur)
	 * 
	 * @return id du joueur en question
	 */
	public int getIdPremierJoueur() {
		return idPremierJoueur;
	}

	/**
	 * Getter de l'�quipe qui a pris � l'atout
	 * 
	 * @return 0 pour joueurs 1 3 et 1 pour joueurs 2 4
	 */
	public int getEquipePreneur() {
		return equipePreneur;
	}

	/**
	 * Getter du nombre de plis jou�s dans la manche
	 * 
	 * @return nombre de plis jou�s
	 */
	public int getNbPlis() {
		return nbPlis;
	}

	/**
	 * Getter du nombre de points d'une �quipe
	 * 
	 * @param l'id de l'�quipe souhait�e (0 ou 1)
	 * @return le score de cette �quipe
	 */
	public int getPointsEquipe(int equipe) {
		return pointsEquipe[equipe];
	}

}
