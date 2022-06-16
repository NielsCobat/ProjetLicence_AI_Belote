# L'IHM en détail

## Architecture
L'interface graphique de notre application est développée avec JavaFX 18.0.1. Elle utilise un fichier FXML **userPOV.fxml** que nous avons édité avec SceneBuilder, dans ce fichier on retrouve les différents boutons avec lesquels l'utilisateur interagie. Les différentes actions déclenchées par les choix de l'utilisateur son programmées dans le fichier **BeloteController.java**. Le fichier **application.css** sert à afficher l'arrière plan de l'interface, à paramétrer tous les textes affichés à l'écran et vient également masquer l'apparence des boutons, puisque chaque carte que possède un joueur est en réalité un bouton déguisée. L'utilisation de boutons permet de récupérer plusieurs informations utiles avec un simple clic de souris (identifiant de l'objet, texte contenu, image associée).

## Interactions
Le bouton start est pressé, une première distribution est effectuée la carte d'atout est posée au centre de la table, et on fait un tour de table pour savoir si chaque joueur veut prendre l'atout ou passer son tour. En appuyant sur passer, le joueur courant passe au joueur suivant. En appuyant sur Un joueur a choisi de prendre. Il prend alors la carte au centre, l'atout prend la valeur de la couleur de celle-ci et s'affiche au centre, puis le reste des cartes est distribué à tout le monde. La manche commence.

![Alt Text](https://github.com/NielsCobat/ProjetLicence_AI_Belotte/blob/master/docs/start.gif)

<img src="https://github.com/NielsCobat/ProjetLicence_AI_Belotte/blob/master/docs/start.gif" width="500" height="500"/>


Quand un tour de table est réalisé sans que personne n'est pris l'atout, un deuxième tour de table commence avec cette fois le choix de la couleur que le joueur courant peut sélectionner. Pour sélectionner une couleur l'utilisateur doit cliquer sur l'une des trois couleurs affichées. Quand elle est prise, le joueur courant prend la carte posée au centre, à l'atout est affecté la couleur tout juste sélectionnée puis le reste des cartes du paquet est distribué. Si à la fin des deux tours personne ne prend l'atout alors les cartes sont remises dans le paquet, on coupe et redistribue les cinq premières cartes aux joueurs (l'ordre des cartes est respecté).

<img src="https://github.com/NielsCobat/ProjetLicence_AI_Belotte/blob/master/docs/choixAtout.gif" width="500" height="500"/>

Quand une manche se termine est qu'une des deux équipes a atteint le nombre de points nécessaire pour gagner alors deux boutons apparaissent. Le bouton restart qui a le même effet que le bouton start, c'est-à-dire qu'il reinitialise les mains des joueurs, l'atout revient à null et les joueurs doivent choisir de prendre ou de passer. Le deuxième bouton est le bouton quit, qui ferme tout simplement la fenêtre d'exécution et arrête le programme.

<img src="https://github.com/NielsCobat/ProjetLicence_AI_Belotte/blob/master/docs/end.gif" width="500" height="500"/>
