
package game;

import java.util.ArrayList;

public class Equipe {

	int id;
	Joueur joueurA;
	Joueur joueurB;
	int score;
	ArrayList<Carte> cartesPli;
	
	public Equipe() {
		id=0;
		joueurA=new Joueur();
		joueurB=new Joueur();
		score = 0;
		cartesPli = new ArrayList<Carte>();
	}
	
	public Equipe(int id, Joueur joueurA, Joueur joueurB, int score) {
		this.id=0;
		this.joueurA= joueurA;
		this.joueurB= joueurB;
		this.score = score;
		cartesPli = new ArrayList<Carte>();
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public int getScore() {
		return score;
	}
}
