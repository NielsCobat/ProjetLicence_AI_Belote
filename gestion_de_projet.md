# Gestion de projet

**Membres du groupe :** Niels Cobat, Florian Alphonzair, Yaël Mousel, Thomas Batko
**Tuteur :** Adrien Le Roch

## Les grandes tâches

Le développement de ce projet se divise en trois grandes tâches. Premièrement, nous avons abstrait et programmé les règles du jeu, les différentes entités intervenant dans la Belote (carte, joueur...). Puis nous nous sommes séparé en deux, avec d'un côté une equipe concentrée sur le dévelopement de l'intelligence artificielle. Et de l'autre une équipe sur l'implantation de L'IHM.

## Déroulement

##### 09/05 - 15/05

Lors de cette première semaine, nous nous sommes réunis pour découvrir la Belote et poser un diagramme de classe du jeu.
Niels s'est occupé de la classe Pli et Manche. Yaël de la classe carte, Thomas de la classe joueur, et Florian de la classe Table. Il s'agissait ici de poser les principaux objets et les principales méthodes.

##### 16/05 - 22/05

Cette semaine, Yaël a fait la fonction **coupe()**, a commencé à réfléchir au réseau neuronal de l'IA( quel type, nombre d'inputs, d'outputs, etc, ...) et a commencé à modéliser les inputs et les outputs du réseau neuronal.
Florian a développer les fonctions de distributions de cartes **distribuer()** et **distribuerReste()**, et également les fonctions qui exécutent le déroulement d'une partie de Belote à savoir **runManche()** (de la classe Manche) et **run()** (de la classe Table).

##### 23/05 - 29/05

//Niels, tu peux explique ta reflexion sur l'entrainement
Niels et Yael : début mise en oeuvre réseau neuronal, surtout axée sur la réflexion quant à comment modéliser l'IA et le déroulement de l'entrainement.
Yaël a fini de modéliser les outputs et les inputs, il reste les couches intermédiares du réseau (hidden layers) à modéliser et les calculs mathématiques à mettre en place. C'est également une phase de réflexion avec Niels sur comment se déroulera l'entrainement de l'ia pour que ce soit pas trop lourd pour le PC de l'entraîner, remise en question de tout le code de base...
Thomas : debugage

Durant cette semaine Florian a commencé à réflechir à l'implémentation d'une interface graphique. Notre tuteur nous a orienté vers JavaFX qui permet en effet de produire des interfaces assez facilement. Après avoir parcourus internet à la recherche de référence d'IHM, on s'aperçoit qu'il existe beaucoup de façon d'organiser son code en fonction de la tâche à réaliser. C'est pourquoi cette semaine était surtout une phase d'apprentissage et de petites tentatives, et non une semaine de production acharnée de code.

##### 30/05 - 05/06

Du côté du réseau neuronal, Yaël a fini de le modéliser et Niels a codé toute la partie entraînement de l'IA pour qu'elle s'améliore d'elle-même n'ayant pas de base de données sur laquelle l'IA pourrait s'appuyer. S'en suit une partie fastidieuse d'optimisation de code et de réplique de fonction(sans appel à Table) pour que l'entrainement ne fasse pas d'appel à Table et soit donc moins lourd pour nos machines.

Après plusieurs tentatives chronophages et non fructueuses d'IHM, Florian a trouvé son fer de lance, le fichier FXML. Tous les éléments les plus importants de l'interface ont été développé durant cette semaine (mains des joueurs, boutons start, passer...).

Thomas : voit le comptage des cartes pour que l'IA connaisse les cartes de tous les joueurs.

##### 06/05 - 12/06

L'entrainement est enfin fonctionnel, tous les appels à Table ont été enlevés de cette partie, Niels et Yaël s'occupe maintenant non sans peine de la liste sans fin de bugs, notamment un assez bête qui aura pris 4 jours à débugger !   

Florian a corrigé quelques bugs, notament celui qui rendait les coups légaux complètement incohérents. Il a ensuite peaufiné quelques détails afin d'améliorer l'expérience utilisateur. Par exemple, mettre en évidence le joueur courant, afficher la couleur de l'atout au centre, interdire l'accès à certains boutons à un moment donné.

Thomas a fini le comptage des cartes.

##### 13/06 - 17/06

Le debuggage continue du côté de Niels et Yaël, avec des premières réussites et des IAs qui s'entraînent pour la première fois sans crahs. Par contre, pour l'instant, les résultats ne sont pas extra-ordinaires et l'éxécution prends beaucoup de ressources (1 minute d'éxécution et 1Go de RAM pour 10 générations avec 100 IA par génération). Il restera cependant de nombreuses fonctionnalités à mettre en place pour que le jeu soit parfait (pour l'instant, l'IA ne récupère pas les données sauvegardées pour s'améliorer). S'en suit de la documentation, de la mise en forme et de commentaires pour le code pour tout le groupe.

Pour cette dernière semaine, Florian a tenté de développer des animations (déplacer une carte de la main du joueur au centre de la table). Malheureusement, cette tentative s'est révélée infructueuse. Le problème est la gestion des coordonnées dans JavaFX, il faut savoir que les coordonnées d'un Node sont relatives au Parent de ce Node et non à la position absolue de l'objet dans la fenêtre. Ce qui rend la gestion des animations chaotique. Malgrès la possibilité de récupérer la position absolue d'un objet, les animations fonctionnaient une fois sur quatres. Florian a décidé d'abandonner cela pour éviter de perdre trop de temps. Enfin Forian a rédiger le markdown de l'IHM et en partie le markdown de gestion de projet. Aussi, il reste un bug dans la fonction isLegalMove (je pense), quand la couleur demandée n'est pas dans la main du joueur courant, celui-ci ne peut plus jouer et donc le jeu est bloqué. Un bug de dernière minute vient s'ajouter à la liste, quand on passe 8 fois de suite la partie se réinitialise normalement, mais lorsque qu'on tente de prendre l'atout directement après, le jeu crash.
