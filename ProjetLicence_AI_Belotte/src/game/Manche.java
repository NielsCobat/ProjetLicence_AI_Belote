package game;

import assets.Couleur;

public class Manche {
	
	private Pli[] plis;
	private Couleur atout;
	private int idPremierJoueur, joueurPreneur, pointsEquipeUne, pointsEquipeDeux;
	
	public Manche(Couleur atout, int idPremierJoueur, int joueurPreneur) throws Exception {
		this.plis = new Pli[8];
		this.atout = atout;
		this.idPremierJoueur = idPremierJoueur;
		this.joueurPreneur = joueurPreneur;
		this.pointsEquipeUne = 0;
		this.pointsEquipeDeux = 0;
		
		if ((this.idPremierJoueur > 4 || this.idPremierJoueur < 1) && (this.joueurPreneur > 4 || this.joueurPreneur < 1))
			throw new Exception("Un id de joueur n'est pas valable");
	}
	
	public void finManche() {
		
	}
	
	public void reset(Couleur atout, int joueurPreneur) {
		this.atout = atout;
		if (this.idPremierJoueur < 4)
			this.idPremierJoueur++;
		else
			this.idPremierJoueur = 1;
		this.joueurPreneur = joueurPreneur;
		this.pointsEquipeUne = 0;
		this.pointsEquipeDeux = 0;
	}

}
