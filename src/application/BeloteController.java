package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import assets.Couleur;
import game.*;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BeloteController {
	
	/*********************************************************************************
	 *********************************** VARIABLES ***********************************
	 *********************************************************************************/

	private Joueur firstPlayer;
	private Joueur currentPlayer;
	private Carte topDeck;
	private Couleur atout;
	private HashMap<Button,Carte> buttonCardMap;
	private ArrayList<Carte> cartesJouees;
	private int nbTour=0; //Permet de redistribuer les cartes quand deux tours passent sans atout choisi
	private Pli currentPli;

	private final Image CARDS[] = {
			new Image("file:PNG-cards-1.3/7_of_diamonds.png"), //Carreau
			new Image("file:PNG-cards-1.3/8_of_diamonds.png"),
			new Image("file:PNG-cards-1.3/9_of_diamonds.png"),
			new Image("file:PNG-cards-1.3/10_of_diamonds.png"),
			new Image("file:PNG-cards-1.3/ace_of_diamonds.png"),
			new Image("file:PNG-cards-1.3/jack_of_diamonds.png"),
			new Image("file:PNG-cards-1.3/queen_of_diamonds.png"),
			new Image("file:PNG-cards-1.3/king_of_diamonds.png"),
			new Image("file:PNG-cards-1.3/7_of_hearts.png"), // Coeur
			new Image("file:PNG-cards-1.3/8_of_hearts.png"),
			new Image("file:PNG-cards-1.3/9_of_hearts.png"),
			new Image("file:PNG-cards-1.3/10_of_hearts.png"),
			new Image("file:PNG-cards-1.3/ace_of_hearts.png"),
			new Image("file:PNG-cards-1.3/jack_of_hearts.png"),
			new Image("file:PNG-cards-1.3/queen_of_hearts.png"),
			new Image("file:PNG-cards-1.3/king_of_hearts.png"),
			new Image("file:PNG-cards-1.3/7_of_spades.png"), //Pique
			new Image("file:PNG-cards-1.3/8_of_spades.png"),
			new Image("file:PNG-cards-1.3/9_of_spades.png"),
			new Image("file:PNG-cards-1.3/10_of_spades.png"),
			new Image("file:PNG-cards-1.3/ace_of_spades.png"),
			new Image("file:PNG-cards-1.3/jack_of_spades.png"),
			new Image("file:PNG-cards-1.3/queen_of_spades.png"),
			new Image("file:PNG-cards-1.3/king_of_spades.png"),
			new Image("file:PNG-cards-1.3/7_of_clubs.png"), //Trefle
			new Image("file:PNG-cards-1.3/8_of_clubs.png"),
			new Image("file:PNG-cards-1.3/9_of_clubs.png"),
			new Image("file:PNG-cards-1.3/10_of_clubs.png"),
			new Image("file:PNG-cards-1.3/ace_of_clubs.png"),
			new Image("file:PNG-cards-1.3/jack_of_clubs.png"),
			new Image("file:PNG-cards-1.3/queen_of_clubs.png"),
			new Image("file:PNG-cards-1.3/king_of_clubs.png"),
			new Image("file:PNG-cards-1.3/dosCarte.png")
	};

	private final Image COLORS[] = {
			new Image("file:couleur/carreau.png"),
			new Image("file:couleur/coeur.png"),
			new Image("file:couleur/pique.png"),
			new Image("file:couleur/trefle.png"),
	};

	@FXML ImageView carteAChoisir;
	@FXML ImageView animationCard;

	@FXML Label score1;
	@FXML Label score2;

	@FXML Button start;
	@FXML Button prendreAtout;
	@FXML Button passer;
	@FXML Button quit;
	@FXML Label resString;
	@FXML Button restart;
	

	@FXML Label instructAtout;
	@FXML Button color1;
	@FXML Button color2;
	@FXML Button color3;

	//Player1 field
	@FXML Button j11;
	@FXML Button j12;
	@FXML Button j13;
	@FXML Button j14;
	@FXML Button j15;
	@FXML Button j16;
	@FXML Button j17;
	@FXML Button j18;
	@FXML Button center1;
	Button[] p1Cards;


	//player2 field
	@FXML Button j21;
	@FXML Button j22;
	@FXML Button j23;
	@FXML Button j24;
	@FXML Button j25;
	@FXML Button j26;
	@FXML Button j27;
	@FXML Button j28;
	@FXML Button center2;
	Button[] p2Cards;

	//player3 field
	@FXML Button j31;
	@FXML Button j32;
	@FXML Button j33;
	@FXML Button j34;
	@FXML Button j35;
	@FXML Button j36;
	@FXML Button j37;
	@FXML Button j38;
	@FXML Button center3;
	Button[] p3Cards;

	//player4 field
	@FXML Button j41;
	@FXML Button j42;
	@FXML Button j43;
	@FXML Button j44;
	@FXML Button j45;
	@FXML Button j46;
	@FXML Button j47;
	@FXML Button j48;
	@FXML Button center4;
	Button[] p4Cards;

	@FXML Label j1;
	@FXML Label j2;
	@FXML Label j3;
	@FXML Label j4;
	Label highLight;

	
	
	
	/********************************************************************************
	 *********************************** HANDLERS ***********************************
	 ********************************************************************************/
	
	/**
	 * Button : joue le coup demande par l'utilisateur si celui-ci est legal
	 * @param evt
	 */
	public void imageClickHandler(ActionEvent evt) {
		Button clickedCard = (Button) evt.getTarget();
		int idCurrentPlayer = getPlayerFromBtn(clickedCard);
		Carte playedCard = buttonCardMap.get(clickedCard);
			
		if(idCurrentPlayer==currentPlayer.id && clickedCard.getGraphic()!=null && currentPlayer.isLegalMove(playedCard) && atout!=null) {

			//Tentative d'animation non fructueuse
			//Image img = getImageFromCard(playedCard);
			//animationCard.setImage(img);
			//cardAnimation(animationCard, clickedCard);
			
			clickedCard.setDisable(true);
			cartesJouees.add(playedCard);
			currentPli.addCarte(playedCard, atout);
			ImageView cardView = (ImageView) clickedCard.getGraphic();
			switch (currentPlayer.id) {
			case 1 :
				center1.setGraphic(cardView);
				break;
			case 2 :
				center2.setGraphic(cardView);
				break;
			case 3 :
				center3.setGraphic(cardView);
				break;
			case 4 :
				center4.setGraphic(cardView);
				break;
			}

			Table.joueurCourant = Table.joueurSuivant();
			currentPlayer = Table.joueurCourant;
			highlightCurrPlayer(currentPlayer);

			if(cartesJouees.size() == 4) {
				int winTeam = currentPli.equipeGagnante();
				Table.mancheCourante.addPli();
				
				if (winTeam==1)
					Table.equipe1.addScore(currentPli.calculPoints());
				else
					Table.equipe2.addScore(currentPli.calculPoints());
				
				center1.setGraphic(null);center2.setGraphic(null);center3.setGraphic(null);center4.setGraphic(null);
				String scoreTeam1 = String.valueOf(Table.equipe1.getScore());
				String scoreTeam2 = String.valueOf(Table.equipe2.getScore());
				score1.setText(scoreTeam1);
				score2.setText(scoreTeam2);
				
				if(Table.mancheCourante.getNbPlis() == 8) {
					restartManche();
					prendreAtout.setDisable(false);
					for (int i=0 ; i<p1Cards.length ; i++) {
						p1Cards[i].setDisable(false);
						p2Cards[i].setDisable(false);
						p3Cards[i].setDisable(false);
						p4Cards[i].setDisable(false);
					}
				}
				else {
					cartesJouees = new ArrayList<Carte>();
					int idLastWinner = currentPli.getIdJoueurGagnant();
					Table.joueurCourant = getPlayerFromId(idLastWinner);
					currentPlayer = Table.joueurCourant;
					highlightCurrPlayer(Table.joueurCourant);
					System.out.println("ID joueur Gagnant : " + idLastWinner + "  id courant = " + Table.joueurCourant.id);
					currentPli = new Pli(idLastWinner);
					Table.mancheCourante.plis[Table.mancheCourante.getNbPlis()] = currentPli;
				}
			}
		}
		System.out.println("nb de plis = " + Table.mancheCourante.getNbPlis());
		
		//calcul des points de la manche et attribution
		endManche(Table.mancheCourante);	
	}

	/**
	 * Button : Lance une partie (distribution, affichage des cartes et decision du 1er joueur)
	 * @param evt
	 */
	public void startHandler(ActionEvent evt) {
		try {
			passer.setDisable(false);
			prendreAtout.setDisable(false);
			start.setDisable(true);
			restart.setVisible(false);
			restart.setDisable(true);
			quit.setVisible(false);
			quit.setDisable(true);
			resString.setVisible(false);
			score1.setText("0");
			score2.setText("0");
			
			Table.init();
			topDeck = Table.distribuer();
			currentPlayer = Table.joueurCourant;
			Table.joueur1.printMain();
			initBoardCards();
			setBoardCards();
			carteAChoisir.setImage(getImageFromCard(topDeck));
			j1.setVisible(true);
			j2.setVisible(true);
			j3.setVisible(true);
			j4.setVisible(true);
			highlightCurrPlayer(currentPlayer);
			firstPlayer = currentPlayer.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Button : donne la carte d'atout au joueur courant,
	 * l'atout devient la couleur de cette carte et le reste du paquet est distribue
	 * @param evt
	 */
	public void prendreAtoutHandler(ActionEvent evt) {
		prendreAtout.setDisable(true);
		passer.setDisable(true);
		currentPlayer.prend(topDeck);
		atout= topDeck.getCouleur();
		Table.ensCartes.remove(topDeck);
		carteAChoisir.setImage(null);
		if(currentPlayer.id==1) {
			ImageView imgV = new ImageView(getImageFromCard(topDeck));
			imgV.setFitHeight(90);
			imgV.setFitWidth(58);
			j16.setGraphic(imgV);
		}
		else if(currentPlayer.id==2) {
			ImageView imgV = new ImageView(getImageFromCard(topDeck));
			imgV.setFitHeight(90);
			imgV.setFitWidth(58);
			j26.setGraphic(imgV);
		}
		else if(currentPlayer.id==3) {
			ImageView imgV = new ImageView(getImageFromCard(topDeck));
			imgV.setFitHeight(90);
			imgV.setFitWidth(58);
			j36.setGraphic(imgV);
		}
		else if(currentPlayer.id==4) {
			ImageView imgV = new ImageView(getImageFromCard(topDeck));
			imgV.setFitHeight(90);
			imgV.setFitWidth(58);
			j46.setGraphic(imgV);
		}
		Table.atout = atout;
		Table.distribuerReste(currentPlayer);
		setBoardCards();
		
		System.out.println(buttonCardMap);
		try {
			Table.mancheCourante= new Manche(firstPlayer.id, currentPlayer.clone().id);
			currentPli = new Pli(firstPlayer.id);
			Table.mancheCourante.plis[Table.mancheCourante.getNbPlis()] = currentPli;
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentPlayer = firstPlayer;
		Table.joueurCourant = firstPlayer;
		highlightCurrPlayer(currentPlayer);
		displayAtout(String.valueOf(atout));
	}

	/**
	 * Button : le joueur courant devient le joueur suivant
	 * @param evt
	 */
	public void passerHandler(ActionEvent evt) {
		nbTour++;
		System.out.println(nbTour);
		if(nbTour>=4 && atout==null) {
			color1.setDisable(false); color2.setDisable(false); color3.setDisable(false);
			instructAtout.setVisible(true);
			displayColorToChoose(topDeck);
			prendreAtout.setDisable(true);
		}
		
		if(nbTour<8) {
			Table.joueurCourant = Table.joueurSuivant();
			currentPlayer = Table.joueurCourant;
			highlightCurrPlayer(currentPlayer);
		}
		else {
			passer.setDisable(true);
		}
		
		if(nbTour>=8 && atout==null) {
			restartManche();
			passer.setDisable(false);
			prendreAtout.setDisable(false);
		}
	}

	/**
	 * Button : lors du deuxième tour de table, quand l'atout n'est pas choisi,
	 * le joueur courant selectionne une des 3 couleurs d'atout possibles et
	 * recupere la carte d'atout (le reste du paquet est ensuite distribue)
	 * @param evt
	 */
	public void selectionCouleurHandler(ActionEvent evt) {
		passer.setDisable(false);
		prendreAtout.setDisable(false);
		Button clickedBtn = (Button) evt.getTarget();
		String colorStr = clickedBtn.getText();
		if(colorStr == "Carreau") Table.atout = Couleur.Carreau;
		else if(colorStr == "Coeur") Table.atout = Couleur.Coeur;
		else if(colorStr == "Pique") Table.atout = Couleur.Pique;
		else if(colorStr == "Trefle") Table.atout = Couleur.Trefle;
		atout = Table.atout;
		instructAtout.setVisible(false);
		color1.setDisable(false); color1.setVisible(false);
		color2.setDisable(false); color2.setVisible(false);
		color3.setDisable(false); color3.setVisible(false);
		System.out.println("Atout = " + atout);
		displayAtout(colorStr);

		prendreAtout.setDisable(true);
		currentPlayer.prend(topDeck);
		Table.ensCartes.remove(topDeck);
		if(currentPlayer.id==1) {
			ImageView imgV = new ImageView(getImageFromCard(topDeck));
			imgV.setFitHeight(90);
			imgV.setFitWidth(58);
			j16.setGraphic(imgV);
		}
		else if(currentPlayer.id==2) {
			ImageView imgV = new ImageView(getImageFromCard(topDeck));
			imgV.setFitHeight(90);
			imgV.setFitWidth(58);
			j26.setGraphic(imgV);
		}
		else if(currentPlayer.id==3) {
			ImageView imgV = new ImageView(getImageFromCard(topDeck));
			imgV.setFitHeight(90);
			imgV.setFitWidth(58);
			j36.setGraphic(imgV);
		}
		else if(currentPlayer.id==4) {
			ImageView imgV = new ImageView(getImageFromCard(topDeck));
			imgV.setFitHeight(90);
			imgV.setFitWidth(58);
			j46.setGraphic(imgV);
		}
		Table.distribuerReste(currentPlayer);
		setBoardCards();
		try {
			Table.mancheCourante= new Manche(firstPlayer.id, currentPlayer.clone().id);
			currentPli = new Pli(firstPlayer.id);
			Table.mancheCourante.plis[Table.mancheCourante.getNbPlis()] = currentPli;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ferme la fenetre et arrete l'execution du programme
	 * @param event
	 */
	public void quitHandler(ActionEvent event) {
		((Stage) ((Node)(event.getSource())).getScene().getWindow()).close();
	}

	
	
	
	/*******************************************************************************
	 *********************************** GRAPHICS **********************************
	 *******************************************************************************/

	/**
	 * Affiche toutes les cartes des joueurs face decouverte
	 */
	private void setBoardCards() {
		for(int i=0 ; i<Table.joueur1.main.size(); i++) {
			Image img1 = getImageFromCard(Table.joueur1.main.get(i));
			ImageView imgV1 = new ImageView(img1);
			Image img2 = getImageFromCard(Table.joueur2.main.get(i));
			ImageView imgV2 = new ImageView(img2);
			Image img3 = getImageFromCard(Table.joueur3.main.get(i));
			ImageView imgV3 = new ImageView(img3);
			Image img4 = getImageFromCard(Table.joueur4.main.get(i));
			ImageView imgV4 = new ImageView(img4);
			imgV1.setFitWidth(58);
			imgV1.setFitHeight(90);
			imgV2.setFitWidth(58);
			imgV2.setFitHeight(90);
			imgV3.setFitWidth(58);
			imgV3.setFitHeight(90);
			imgV4.setFitWidth(58);
			imgV4.setFitHeight(90);
			p1Cards[i].setGraphic(imgV1); p1Cards[i].setDisable(false);
			p2Cards[i].setGraphic(imgV2); p2Cards[i].setDisable(false);
			p3Cards[i].setGraphic(imgV3); p3Cards[i].setDisable(false);
			p4Cards[i].setGraphic(imgV4); p4Cards[i].setDisable(false);
			buttonCardMap.put(p1Cards[i],Table.joueur1.main.get(i));
			buttonCardMap.put(p2Cards[i],Table.joueur2.main.get(i));
			buttonCardMap.put(p3Cards[i],Table.joueur3.main.get(i));
			buttonCardMap.put(p4Cards[i],Table.joueur4.main.get(i));
		}
	}

	/**
	 * Initialise tous les boutons qui contiendront des cartes,
	 * le nombre de tour, l'arrayList des cartes jouees et la hashmap (Button,Carte)
	 */
	private void initBoardCards() {
		nbTour = 0;
		p1Cards = new Button[8];
		p2Cards = new Button[8];
		p3Cards = new Button[8];
		p4Cards = new Button[8];
		buttonCardMap = new HashMap<Button,Carte>();
		cartesJouees = new ArrayList<Carte>();
		//player1
		p1Cards[0] = j11;
		p1Cards[1] = j12;
		p1Cards[2] = j13;
		p1Cards[3] = j14;
		p1Cards[4] = j15;
		p1Cards[5] = j16;
		p1Cards[6] = j17;
		p1Cards[7] = j18;
		//player2
		p2Cards[0] = j21;
		p2Cards[1] = j22;
		p2Cards[2] = j23;
		p2Cards[3] = j24;
		p2Cards[4] = j25;
		p2Cards[5] = j26;
		p2Cards[6] = j27;
		p2Cards[7] = j28;
		//player3
		p3Cards[0] = j31;
		p3Cards[1] = j32;
		p3Cards[2] = j33;
		p3Cards[3] = j34;
		p3Cards[4] = j35;
		p3Cards[5] = j36;
		p3Cards[6] = j37;
		p3Cards[7] = j38;
		//player4
		p4Cards[0] = j41;
		p4Cards[1] = j42;
		p4Cards[2] = j43;
		p4Cards[3] = j44;
		p4Cards[4] = j45;
		p4Cards[5] = j46;
		p4Cards[6] = j47;
		p4Cards[7] = j48;
	}

	/**
	 * Affiche le joueur courant en jaune, et les autres en blanc
	 * @param joueurCourant
	 */
	private void highlightCurrPlayer(Joueur joueurCourant) {
		switch(joueurCourant.id) {
		case 1: 
			j1.setStyle("-fx-text-fill: yellow; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j2.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j3.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j4.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			break;
		case 2: 
			j2.setStyle("-fx-text-fill: yellow; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j1.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j3.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j4.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			break;
		case 3: 
			j3.setStyle("-fx-text-fill: yellow; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j1.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j2.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j4.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			break;
		case 4: 
			j4.setStyle("-fx-text-fill: yellow; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j1.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j2.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			j3.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12pt;");
			break;
		}
	}

	/**
	 * Affiche les 3 couleurs possibles pour l'atout quand l'atout n'est
	 * pas encore decide apres un tour de table
	 * @param carte la carte d'atout
	 */
	private void displayColorToChoose(Carte carte){
		ImageView carreau = new ImageView(COLORS[0]);
		ImageView coeur = new ImageView(COLORS[1]);
		ImageView pique = new ImageView(COLORS[2]);
		ImageView trefle = new ImageView(COLORS[3]);
		carreau.setFitHeight(50);
		carreau.setFitWidth(50);
		coeur.setFitHeight(50);
		coeur.setFitWidth(50);
		pique.setFitHeight(50);
		pique.setFitWidth(50);
		trefle.setFitHeight(50);
		trefle.setFitWidth(50);

		switch (carte.getCouleur()) {
		case Coeur :
			color1.setGraphic(carreau);
			color2.setGraphic(pique);
			color3.setGraphic(trefle);
			color1.setText("Carreau");
			color2.setText("Pique");
			color3.setText("Trefle");
			break;
		case Carreau :
			color1.setGraphic(coeur);
			color2.setGraphic(pique);
			color3.setGraphic(trefle);
			color1.setText("Coeur");
			color2.setText("Pique");
			color3.setText("Trefle");
			break;
		case Pique :
			color1.setGraphic(carreau);
			color2.setGraphic(coeur);
			color3.setGraphic(trefle);
			color1.setText("Carreau");
			color2.setText("Coeur");
			color3.setText("Trefle");
			break;
		case Trefle :
			color1.setGraphic(carreau);
			color2.setGraphic(coeur);
			color3.setGraphic(pique);
			color1.setText("Carreau");
			color2.setText("Coeur");
			color3.setText("Pique");
			break;
		}
	}
	
	/**
	 * Cache les couleurs le choix des couleurs possibles pour l'atout
	 * quand une couleur a ete selectionnee par un joueur
	 */
	private void hideColorToChoose() {
		color1.setGraphic(null); color1.setText(""); color1.setDisable(true);
		color2.setGraphic(null); color2.setText(""); color2.setDisable(true);
		color3.setGraphic(null); color3.setText(""); color3.setDisable(true);
		instructAtout.setVisible(false);
	}

	/**
	 * Affiche au centre de la fenetre de jeu la couleur de l'atout
	 * @param couleur Couleur de l'atout decicde
	 */
	private void displayAtout(String couleur) {
		color1.setDisable(false);
		color2.setDisable(false);
		color3.setDisable(false);
		switch(couleur){
		case "Carreau" :
			Image img = COLORS[0];
			carteAChoisir.setImage(img);
			carteAChoisir.setX(carteAChoisir.getX()+10);
			carteAChoisir.setY(carteAChoisir.getY()+10);
			break;
		case "Coeur" :
			Image img1 = COLORS[1];
			carteAChoisir.setImage(img1);
			carteAChoisir.setX(carteAChoisir.getX()+10);
			carteAChoisir.setY(carteAChoisir.getY()+10);
			break;
		case "Pique" :
			Image img2 = COLORS[2];
			carteAChoisir.setImage(img2);
			carteAChoisir.setX(carteAChoisir.getX()+10);
			carteAChoisir.setY(carteAChoisir.getY()+10);
			break;
		case "Trefle" :
			Image img3 = COLORS[3];
			carteAChoisir.setImage(img3);
			carteAChoisir.setX(carteAChoisir.getX()+10);
			carteAChoisir.setY(carteAChoisir.getY()+10);
			break;
		}
	}
	
	/**
	 * Animation d'une carte quand elle est jouee (ne fonctionne pas encore)
	 * @param img l'image qui sera animee a l'ecran
	 * @param btn bouton que le joueur courant vient de presser pour jouer une carte
	 */
	private void cardAnimation(ImageView img, Button btn) {
	
		double centerX = 0;
		double centerY = 0;
		int currentID = getPlayerFromBtn(btn);
		System.out.println(currentID + " = joueur from bouton");
		Bounds boundInScene = btn.localToScene(btn.getBoundsInLocal());
		double cardX = boundInScene.getCenterX();
		double cardY = boundInScene.getCenterY();
		img.setX(cardX - (298));
		img.setY(cardY - (378));
		if(currentID==1) {
			Bounds boundCenter1 = center1.localToScene(center1.getBoundsInLocal());
			centerX = boundCenter1.getCenterX();
			centerY = boundCenter1.getCenterY();
		}
		else if(currentID==2) {
			Bounds boundCenter2 = center2.localToScene(center2.getBoundsInLocal());
			centerX = boundCenter2.getCenterX();
			centerY = boundCenter2.getCenterY();
		}
		else if(currentID==3) {
			Bounds boundCenter3 = center3.localToScene(center3.getBoundsInLocal());
			centerX = boundCenter3.getCenterX();
			centerY = boundCenter3.getCenterY();
		}
		else if(currentID==4) {
			Bounds boundCenter4 = center4.localToScene(center4.getBoundsInLocal());
			centerX = boundCenter4.getCenterX();
			centerY = boundCenter4.getCenterY();
		}
		
		
		double xTranslate = (centerX - cardX);
		double yTranslate = (centerY - cardY);
		System.out.println("centerX = "  + centerX + " centerY = " + centerY);
		System.out.println("cardX = " + cardX + " cardY = " + cardY );
		System.out.println("xTranslate  = " + xTranslate + "yTranslate = " + yTranslate);
		
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(img);
		translate.setDuration(Duration.millis(1000));
		translate.setByX(xTranslate);
		translate.setByY(yTranslate);
		translate.play();
		
	}
	
	
	/**********************************************************************************
	 *********************************** GAME LOGIC ***********************************
	 **********************************************************************************/
	
	/**
	 * Reprend des elements de la fonction finManche() de la classe Manche en l'adaptant
	 * au contexte de cette IHM. Quand une manche est finie, compte les points supplementaires
	 * a donner a une equipe dans certains cas particulier (capot, belote, 10 de der...)
	 * @param manche manche courante
	 */
	private void endManche(Manche manche) {
		if (manche.getNbPlis() == 8) {
			System.out.println("Fin de manche");
			// calcul des points dans la classe Partie à la fin

			if (Table.joueur1.aBelote || Table.joueur3.aBelote)
				manche.belotte[0] = 20;
			else if (Table.joueur2.aBelote || Table.joueur4.aBelote)
				manche.belotte[1] = 20;
			System.out.println("Fin de Manche ! ");
			System.out.print("Points de l'équipe 1 : " + manche.pointsEquipe[0]);
			if (manche.belotte[0] == 20) System.out.print(" + " + manche.belotte[0] + " points de Belote");
			System.out.println();
			System.out.print("Points de l'équipe 2 : " + manche.pointsEquipe[1]);
			if (manche.belotte[1] == 20) System.out.print(" + " + manche.belotte[1] + " points de Belote");
			System.out.println();

			if (manche.pointsEquipe[0] == 162) { //Équipe 2 capot
				manche.pointsEquipe[0] = 252;
				System.out.println("Équipe 2 capot ! ");
			}


			else if (manche.pointsEquipe[1] == 162) {//Équipe 1 capot
				manche.pointsEquipe[1] = 252;
				System.out.println("Équipe 2 capot ! ");
			}


			else if (manche.pointsEquipe[(manche.equipePreneur)] < 82	  
					&& manche.belotte[(manche.equipePreneur)] + manche.belotte[(manche.equipePreneur + 1) % 2] == 0 //Si une équipe prend et qu'il n'y a pas de Belote
					|| (manche.pointsEquipe[(manche.equipePreneur + 1) % 2] + manche.belotte[(manche.equipePreneur + 1) % 2] >= 91  //Ou
					&& manche.belotte[(manche.equipePreneur)] + manche.belotte[(manche.equipePreneur + 1) % 2] == 20 )) { //Si une équipe prend et qu'il y a une Belote	
				manche.pointsEquipe[manche.equipePreneur] = 0;
				manche.pointsEquipe[(manche.equipePreneur + 1) % 2] = 162;
				if (manche.equipePreneur == 0) System.out.println("Équipe 1 dedans ! ");
				else System.out.println("Équipe 2 dedans ! ");

			} 

			Table.equipe1.addScore(manche.pointsEquipe[0] + manche.belotte[0]);
			Table.equipe2.addScore(manche.pointsEquipe[1] + manche.belotte[1]);
			


			if(Table.equipe1.getScore()>=Table.scoreToWin || Table.equipe2.getScore() >= Table.scoreToWin) {
				restart.setVisible(true);
				restart.setDisable(false);
				quit.setVisible(true);
				quit.setDisable(false);
				if(Table.equipe1.getScore()>Table.equipe2.getScore()) {
					resString.setText("J1 et J3 remportent la partie");
					
				}
				else if(Table.equipe1.getScore()<Table.equipe2.getScore()) {
					resString.setText("J2 et J4 remportent la partie");
					
				}
				else
					resString.setText("égalité");
			}
		}
	}
	
	/**
	 * Vide les mains des joueurs, et redistribue les cartes aux joueurs
	 * en respectant l'ordre et la coupe du paquet
	 */
	private void restartManche() {
		ArrayList<Carte> tl = (ArrayList<Carte>) Table.ensCartes.clone();
		ArrayList<Carte> hd = new ArrayList<Carte>();
		for (int i = 0; i< 4; i++) {
			hd.addAll((Collection<? extends Carte>) Table.joueurCourant.main.clone());
			Table.joueurCourant = Table.joueurSuivant();
		}
		
		
		Table.ensCartes.clear();
		Table.ensCartes.addAll(hd);
		Table.ensCartes.addAll(tl);
		hd.clear();
		tl.clear();
		Table.joueur1.main.clear();
		Table.joueur2.main.clear();
		Table.joueur3.main.clear();
		Table.joueur4.main.clear();
		
		
		firstPlayer = Table.joueurSuivant().clone();
		Table.joueurCourant= firstPlayer; //a revoir
		currentPlayer = Table.joueurCourant;
		initBoardCards();
		hideColorToChoose();
		Table.coupe();
		topDeck = Table.distribuer();
		carteAChoisir.setImage(getImageFromCard(topDeck));
		setBoardCards();
	}

	/**
	 * Obtenir le joueur associe a l'identifiant passe en parametre
	 * @param id identifiant du joueur
	 * @return le joueur associe a l'id
	 */
	private Joueur getPlayerFromId(int id) {
		switch(id) {
		case 1 :
			return Table.joueur1;
		case 2 :
			return Table.joueur2;
		case 3 :
			return Table.joueur3;
		case 4 :
			return Table.joueur4;
		default :
			return null;
		}
	}
	
	/**
	 * Obtenir l'image associee a une carte
	 * @param carte
	 * @return Image de la carte dans le tableau d'image des cartes
	 */
	private Image getImageFromCard(Carte carte) {
		int indice = 0;

		//detection couleur
		switch (carte.getCouleur()) {
		case Carreau :
			indice = 0;
			break;
		case Coeur :
			indice = 8;
			break;
		case Pique :
			indice = 16;
			break;
		case Trefle :
			indice = 24;
			break;
		}

		//detection valeur
		switch (carte.getValeur()) {
		case Sept:
			indice += 0;
			break;
		case Huit:
			indice += 1;
			break;
		case Neuf:
			indice += 2;
			break;
		case Dix:
			indice += 3;
			break;
		case As:
			indice += 4;
			break;
		case Valet:
			indice += 5;
			break;
		case Dame:
			indice += 6;
			break;
		case Roi:
			indice += 7;
			break;
		}
		return CARDS[indice];
	}
	
	/**
	 * Obtenir l'identifiant du joueur associe au bouton presse par le joueur
	 * @param btn bouton presse par le joueur
	 * @return int id du joueur associe au bouton
	 */
	private int getPlayerFromBtn(Button btn) {
		for(int i=0 ; i<p1Cards.length ; i++) {
			if(btn==p1Cards[i]) return 1;
			if(btn==p2Cards[i]) return 2;
			if(btn==p3Cards[i]) return 3;
			if(btn==p4Cards[i]) return 4;
		}
		return -1;
	}
	
	
	
	
	/********************************************************************************************
	 ********************************* SWITCH SCENE TESTS ***************************************
	 ********************************************************************************************/
	
	private Stage stage;
	private Scene scene;
	private BorderPane root;
	/**
	 * Passe de la scene de fin de partie a la scene de jeu, si l'utilisateur veut rejouer
	 * @param event
	 * @throws IOException
	 */
	public void switchToMainScene(ActionEvent event) throws IOException {
		root = (BorderPane)FXMLLoader.load(getClass().getResource("userPOV.fxml"));
		stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Passe de la scene de jeu a la scene de fin de partie quand un vainqueur est decide
	 * @param event
	 * @throws IOException
	 */
	public void switchToEndScene(ActionEvent event) throws IOException {
		root = (BorderPane)FXMLLoader.load(getClass().getResource("endScene.fxml"));
		stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		if(Table.equipe1.getScore()>Table.equipe2.getScore()) {
			resString.setText("J1 et J3 remportent la partie");
			
		}
		else if(Table.equipe1.getScore()<Table.equipe2.getScore()) {
			resString.setText("J2 et J4 remportent la partie");
			
		}
		else
			resString.setText("égalité");
	}
}
