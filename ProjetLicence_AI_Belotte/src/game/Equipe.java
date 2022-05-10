package game;

public class Equipe {

	int id;
	Joueur joueurA;
	Joueur joueurB;
	int score;
	
	public Equipe() {
		id=0;
		joueurA=new Joueur();
		joueurB=new Joueur();
		score = 0;
	}
	
	public Equipe(int id, Joueur joueurA, Joueur joueurB, int score) {
		this.id=0;
		this.joueurA= joueurA;
		this.joueurB= joueurB;
		this.score = score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore(int score) {
		return score;
	}
}
