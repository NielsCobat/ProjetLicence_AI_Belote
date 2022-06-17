# ProjetLicence_AI_Belote

Projet de fin de licence, IA de belote.
Les membres de ce projet sont Niels COBAT, Yael MOUSEL, Florian ALPHONZAIR et Thomas BATKO et nous le réalisons comme projet de fin de licence informatique à l'université de Rennes 1.

Dans ce projet, nous visons à développer une application de belotte à 4 joueurs dans laquelle un ou plusieurs joueurs peuvent être remplacés par une intelligence artificielle (réseau neuronal).

## Fonctionnalités suportées

Une partie complète de belote est jouable, c'est-à-dire deux équipes de deux joueurs (et/ou IA) s'afrontent et tentent d'arriver à 501 points en premier en remportant le plus de manches. Vous trouverez ici les règles complètes de la belote : https://www.belote.com/regles-et-variantes/les-regles-de-la-belote-classique/
Pour jouer ces parties, une interface graphique (présentée plus en détail dans IHM.md) permet d'interagir avec les différents éléments de l'application.

Il est également possible d'entrainer l'IA de réseau neuronal via un système de génération et de reproduction des meilleurs éléments de chaque génération.

## Fonctionnalités à développer

De nombreux bugs restent dans le code de l'entrainement, qui sont à fixer.

Par manque de temps, nous n'avons pas pu gérer la sauvegarde des résultats de l'entrainement dans un fichier, pour ensuite utiliser ce fichier afin de créer les IAs du jeu avec ces résultats.

L'entrainement de l'IA est pour l'instant très demandant en ressources (temps et mémoire vive), il sertait donc intéressant à l'avenir de revoir une grande partie du code pour l'optimiser.

IHM :
 - animer les cartes afin de fluidifier le déroulement du jeu
 - créer plusieurs scènes pour les différentes étapes (accueil, jeu, fin)
 - mettre en évidence les coups légaux du joueur courant
 - développer un menu pour le choix des adversaires robots ou non
 - mettre en lien les coups

## Technologies utilisées

Pour la partie graphique, nous utilisions la librairie javaFX de java. Pour le jeu en lui-même et l'entrainement, nous n'utilisons aucune librairie, et avons tout codé nous même. Par ailleurs nous n'utilisions pas de base de données pour entrainer l'IA. Ces deux derniers points sont probablement la source de nos difficultés à coder et entrainer l'IA. Cette dernière utilise un réseau neuronal codé grâce à des matrices plutôt que des classes objets, ce qui est plus complexe mais plus optimisé. Cette classe Matrix.java que nous utilisons a été codé par Suyash Sonawane.

## Vidéo démonstrative

https://youtu.be/5goI6FeBo7Q

## Licence du projet

Ce projet est sous licence Creative Commons Attribution 4.0 International License, c'est-à-dire que vous pouvez copier, utiliser, présenter, modifier ce travail mais pour des raisons non-commerciales seulement. Plus d'informations sur https://creativecommons.org/licenses/by-nc/4.0/legalcode.fr
