package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import assets.Couleur;
import assets.Valeur;

public class Table {

	private final static int scoreToWin = 501;

	static Joueur joueur1;
	static Joueur joueur2;
	static Joueur joueur3;
	static Joueur joueur4;

	static int scoreEquipe1; //joueur 1 et 3 (ne sert pas pour l'instant)
	static int scoreEquipe2; //joueur 2 et 4 (ne sert pas pour l'instant)


	static Equipe equipe1;
	static Equipe equipe2;

	static Joueur joueurCourant;
	static Manche mancheCourante;
	static Couleur atout;

	static LinkedList<Carte> cartesEnMain;
	static LinkedList<Carte> cartesPosees;
	static ArrayList<Carte> ensCartes = new ArrayList<Carte>();
	static int idWinner;
	static boolean gameOver = false;


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
	 * Le joueur courant est le distributeur
	 * @param peneur joueur qui se voit distribue deux cartes
	 */
	static void distribuerReste(Joueur preneur) {
		//TODO
		//indice 21 car 4*5 cartes distribuees + 1
		int indiceCourantEnsCartes = 21;
		for(int i=0 ; i<4 ; i++) {
			//le distrubution commence avec le joueur suivant le joueur distributeur
			joueurCourant = joueurSuivant();
			//joueur courant est le joueur qui recoit 2 cartes
			if(joueurCourant.id==preneur.id) {
				int j=0;
				while(j<2) {
					joueurCourant.main.add(ensCartes.get(indiceCourantEnsCartes));
					indiceCourantEnsCartes++;
					j++;
				}
			}
			else {
				int j=0;
				while(j<3) {
					joueurCourant.main.add(ensCartes.get(indiceCourantEnsCartes));
					indiceCourantEnsCartes++;
					j++;
				}
			}
		}
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
	
	/**
	 * Met les valeurs des cartes a l'atout a jour
	 * @param atout la couleur de l'atout
	 */
	public static void setPointCarte(Couleur atout) {
		for(Carte carte : ensCartes) {
			if(carte.getCouleur()==atout && carte.getValeur()==Valeur.Neuf)
				carte.point = 14;
			
			else if (carte.getCouleur()==atout && carte.getValeur()==Valeur.Valet)
				carte.point = 20;
		}
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
	public static void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//boucle principale du jeu
		while (!gameOver) {
			//garder en memoire le joueur qui distribue
			Joueur distributeur = joueurCourant.clone();
			Joueur joueurPreneur = null; //pas terrible mais doit etre initialisée

			//Tant que l'atout n'est pas choisi on fait deux tours de table et on redistribue
			while(atout==null) {
				Carte head = distribuer();
				//premier tour de table pour choisir l'atout (une)
				for(int i=0; i<4 ; i++) {
					boolean aPris = joueurCourant.veutPrendre(head);
					if(aPris) {
						joueurPreneur = joueurCourant.clone();
						atout = joueurCourant.main.getLast().getCouleur(); //maj couleur atout
						setPointCarte(atout); //maj point des atouts
						break;
					}
				}

				//si l'atout n'est pas encore decide alors deuxieme tour de table (deux)
				if(atout==null) {
					for(int i=0; i<4 ; i++) {
						boolean aPris = joueurCourant.veutPrendre(head);
						if(aPris) {
							joueurPreneur = joueurCourant.clone();
							//second tour donc le preneur doit decider de la couleur de l'atout
							atout = joueurCourant.designeCouleur(); //maj couleur atout
							setPointCarte(atout); //maj point atout
							break;
						}
						joueurCourant = joueurSuivant();
					}
				}
				//atout decide, il faut distribuer le reste des cartes
				if(atout!=null) {
					//le joueur courant redevient le joueur qui distribue
					joueurCourant = distributeur;
					distribuerReste(joueurPreneur);
				}
				//atout encore non decide alors on remelange le paquet de carte
				else Collections.shuffle(ensCartes);
			}
			
			//lancement de la manche
				//joueur a gauche du joueur distributeur commence a poser une carte
				joueurCourant  = joueurSuivant();
				Joueur premierJoueur = joueurCourant.clone();

				try {
					mancheCourante = new Manche(atout, premierJoueur.id, joueurPreneur.id );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mancheCourante.runManche(joueurCourant, atout);
				//fin de la manche, on reset l'atout et la manche
				atout = null;
				try {
					mancheCourante.reset(atout, joueurPreneur.id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			if(equipe1.getScore()>=scoreToWin) {
				gameOver = true;
				idWinner=1; //je mets l'id d'un seul membre comme le modulo donne forcement l'equipe des deux joueurs
			}
			if(equipe2.getScore()>=scoreToWin) {
				gameOver = true;
				idWinner=2; //je mets l'id d'un seul membre comme le modulo donne forcement l'equipe des deux joueurs
			}
		}
		System.out.println("Equipe " + idWinner + " gagne la partie");
	}
}
