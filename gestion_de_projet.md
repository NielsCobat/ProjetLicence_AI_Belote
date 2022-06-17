# Gestiond de projet
**Membres du groupe :** Niels Cobat, Florian Alphonzair, Yael Mousel, Thomas Batko
**Tuteur :** Adrien Le Roch
## Les grandes tâches
Le développement de ce projet se divise en trois grandes tâches. Premièrement, nous avons abstrait et programmé les règles du jeu, les différentes entités intervenant dans la Belote (carte, joueur...). Puis nous nous sommes séparé en deux, avec d'un côté une equipe concentrée sur le dévelopement de l'intelligence artificielle. Et de l'autre une équipe sur l'implantation de L'IHM.

## Déroulement
##### 09/05 - 15/05
Lors de cette première semaine, nous nous sommes réunis pour découvrir la Belote et poser un diagramme de classe du jeu.
Niels s'est occupé de la classe Pli et Manche. Yael de la classe carte, Thomas de la classe joueur, et Florian de la classe Table. Il s'agissait ici de poser les principaux objets et les principales méthodes.
##### 16/05 - 22/05

Florian a développer les fonctions de distributions de cartes **distribuer()** et **distribuerReste()**, et également les fonctions qui exécutent le déroulement d'une partie de Belote à savoir **runManche()** (de la classe Manche) et **run()** (de la classe Table).
##### 23/05 - 29/05
Niels-Yael debut mise en oeuvre RNA
Thomas : debugage

Durant cette semaine Florian a commencé à réflechir à l'implémentation d'une interface graphique. Notre tuteur nous a orienté vers JavaFX qui permet en effet de produire des interfaces assez facilement. Après avoir parcourus internet à la recherche de référence d'IHM, on s'aperçoit qu'il existe beaucoup de façon d'organiser son code en fonction de la tâche à réaliser. C'est pourquoi cette semaine était surtout une phase d'apprentissage et de petites tentatives, et non une semaine de production acharnée de code.
##### 30/05 - 05/06
Niels debut entrainement

Après plusieurs tentatives chronophages et non fructueuses d'IHM, Florian a trouvé son fer de lance, le fichier FXML. Tous les éléments les plus importants de l'interface ont été développé durant cette semaine (mains des joueurs, boutons start, passer...).

Thomas voit le comptage des cartes pour que l'IA connaisse les cartes de tous les joueurs.
##### 06/05 - 12/06
Niels et Yael : débuggage entrainement et RNA

Florian a corrigé quelques bugs, notament celui qui rendait les coups légaux complètement incohérents. Il a ensuite peaufiné quelques détails afin d'améliorer l'expérience utilisateur. Par exemple, mettre en évidence le joueur courant, afficher la couleur de l'atout au centre, interdire l'accès à certains boutons à un moment donné.

Thomas a fini le comptage des cartes.
##### 13/06 - 17/06

Pour cette dernière semaine, Florian a tenté de développer des animations (déplacer une carte de la main du joueur au centre de la table). Malheureusement, cette tentative s'est révélée infructueuse. Le problème est la gestion des coordonnées dans JavaFX, il faut savoir que les coordonnées d'un Node sont relatives au Parent de ce Node et non à la position absolue de l'objet dans la fenêtre. Ce qui rend la gestion des animations chaotique. Malgrès la possibilité de récupérer la position absolue d'un objet, les animations fonctionnaient une fois sur quatres. Florian a décidé d'abandonner cela pour éviter de perdre trop de temps. Enfin Forian a rédiger le markdown de l'IHM et en partie le markdown de gestion de projet.
