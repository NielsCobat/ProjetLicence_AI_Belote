package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import assets.Couleur;
import assets.Valeur;

public class Table {

	private final static int scoreToWin = 501;

	private static final Couleur Carreau = null;
	static Joueur joueur1;
	static Joueur joueur2;
	static Joueur joueur3;
	static Joueur joueur4;

	static int scoreEquipe1; //joueur 1 et 3
	static int scoreEquipe2; //joueur 2 et 4


	static Equipe equipe1;
	static Equipe equipe2;

	static Joueur joueurCourant;
	static Manche mancheCourante;
	static Couleur atout;

	static LinkedList<Carte> cartesEnMain;
	static LinkedList<Carte> cartesPosees;
	static ArrayList<Carte> ensCartes;
	static int idWinner;
	static boolean gameOver = false;


	static void setEnsCartes() {
		for (int i = 0; i < 4; i++) {
			Couleur couleurCourrante = Couleur.Carreau;
			switch (i) {
			case 0:
				couleurCourrante = Couleur.Carreau;
				break;
			case 1:
				couleurCourrante = Couleur.Coeur;
				break;
			case 2:
				couleurCourrante = Couleur.Pique;
				break;
			case 3:
				couleurCourrante = Couleur.Trefle;
				break;
			default:
			}
			for (int j = 0; j < 8; j++) {
				Valeur valeurCourrante = Valeur.Sept;
				int pts = 0;
				switch (j) {
				case 0:
					valeurCourrante = Valeur.Sept;
					pts = 0;
					break;
				case 1:
					valeurCourrante = Valeur.Huit;
					pts = 0;
					break;
				case 2:
					valeurCourrante = Valeur.Neuf;
					pts = 0;
					break;
				case 3:
					valeurCourrante = Valeur.Dix;
					pts = 10;
					break;
				case 4:
					valeurCourrante = Valeur.Valet;
					pts = 2;
					break;
				case 5:
					valeurCourrante = Valeur.Dame;
					pts = 3;
					break;
				case 6:
					valeurCourrante = Valeur.Roi;
					pts = 4;
					break;
				case 7:
					valeurCourrante = Valeur.As;
					pts = 11;
					break;
				default:
				}
				ensCartes.add(new Carte(couleurCourrante, valeurCourrante, pts));
			}
		}
		Collections.shuffle(ensCartes);
	}

	/**
	 * Distribue les 5 premieres cartes aux joueurs ( 3 + 2 )
	 * Le joueur courant devient le joueur suivant le distributeur
	 * @return la carte au dessus du paquet pour choix de l'atout
	 */
	static Carte distribuer() {
		// TODO
		int indiceCourantEnsCartes = 0;
		for(int i=0 ; i<=7 ; i++) {
			//commence distribution par le joueur suivant
			joueurCourant = joueurSuivant();
			//distribue les 3 premieres cartes a tout le monde
			if(i<=3) {
				int j=0;
				while(j<3) {
					joueurCourant.main.add(ensCartes.get(indiceCourantEnsCartes));
					indiceCourantEnsCartes++;
					j++;
				}
			}
			//distribue les 2 cartes a tout le monde
			else {
				int j=0;
				while(j<2) {
					joueurCourant.main.add(ensCartes.get(indiceCourantEnsCartes));
					indiceCourantEnsCartes++;
					j++;
				}
			}
		}
		//joueur qui commence a parler est le joueur apres celui qui distribue
		joueurCourant = joueurSuivant();
		return ensCartes.get(indiceCourantEnsCartes);
	}


	/**
	 * Distribue le reste des cartes une fois que l'atout est choisi
	 * Le joueur courant est celui qui a choisi l'atout
	 * donc 2 puis 3 puis 3 puis 3
	 */
	static void distribuerReste() {

	}

	/**
	 * @return joueur suivant le joueur courant
	 */
	static Joueur joueurSuivant(){
		int idCourant = joueurCourant.id;
		if(idCourant == 1) return joueur2;
		else if (idCourant == 2) return joueur3;
		else if (idCourant == 3) return joueur4;
		else return joueur1;
	}


	private Table() {
	}


	/**
	 * Initialise toutes les variables nécessaires pour débuter une partie
	 * 
	 * @throws Exception
	 */
	public static void init() throws Exception {
		setEnsCartes();

		joueur1 = new Joueur("", 1, 3);
		joueur2 = new Joueur("", 2, 4);
		joueur3 = new Joueur("", 3, 1);
		joueur4 = new Joueur("", 4, 2);


		equipe1 = new Equipe(1, joueur1, joueur3, 0);
		equipe2 = new Equipe(2, joueur2, joueur4, 0);

		equipe1.score = 0;
		equipe2.score = 0;
		atout = null;
		int idJoueurCourant = (int) (Math.random() * (4 - 1 + 1) + 1); // selectionne un int entre 1 et 4
		switch (idJoueurCourant) {
		case 1:
			joueurCourant = joueur1;
			break;
		case 2:
			joueurCourant = joueur2;
			break;
		case 3:
			joueurCourant = joueur3;
			break;
		case 4:
			joueurCourant = joueur4;
			break;
		default:
			throw new Exception("identifiant du joueur non compatible");
		}
		ensCartes = new ArrayList<Carte>();
		setEnsCartes();
	}

	/**
	 * Deroulement de la partie, de l'init jusqu'au game over
	 */
	static void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!gameOver) {
			//garder en memoire le joueur qui distribue
			Joueur distributeur = joueurCourant.clone();
			
			//Tant que l'atout n'est pas choisi on fait deux tours de table et on redistribue
			while(atout==null) {
				Carte head = distribuer();
				//premier tour de table pour choisir l'atout (une)
				for(int i=0; i<4 ; i++) {
					boolean aPris = joueurCourant.veutPrendre(head);
					if(aPris) {
						atout = joueurCourant.main.getLast().getCouleur(); //peut etre pas besoin, on verra
						break;
					}
					joueurCourant = joueurSuivant();
				}

				//si l'atout n'est pas encore decide alors deuxieme tour de table (deux)
				if(atout==null) {
					for(int i=0; i<4 ; i++) {
						boolean aPris = joueurCourant.veutPrendre(head);
						if(aPris) {
							atout = joueurCourant.designeCouleur();
							break;
						}
					}
				}

				if(atout!=null) distribuerReste();
			}


			if(scoreEquipe1>=scoreToWin) {
				gameOver = true;
				idWinner=1; //je mets l'id d'un seul membre comme le modulo donne forcement l'equipe des deux joueurs
			}
			if(scoreEquipe2>=scoreToWin) {
				gameOver = true;
				idWinner=2; //je mets l'id d'un seul membre comme le modulo donne forcement l'equipe des deux joueurs
			}
		}
	}
}
