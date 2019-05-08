# PetitsChevauxJava
## Introduction
Projet réalisé par **Antonin HUAUT** et **Clémence LE ROUX**  

1A TP 3.2  
DUT Informatique  
IUT Caen Campus 3

## Présentation 
Reprise du jeu de plateau traditionnel, il se compose :
- D'un plateau de 15x15
- De 16 pions-chevaux *(4 par joueur)*
- De quatre écuries *(1 par joueur)*
- D'un dé

## Objectif
- Refaire le jeu en respectant les règles du jeu
- Adapter la version C => Java, avec la POO
- Effectuer un affichage en console ansi qu'en interface graphique

## Informations
Le programme a été compilé sous Java 8  
Il utilise la bibliothèque [Gson](https://github.com/google/gson "Gson") 2.8.5 de Google pour le système de sauvegarde  
Cette version est intégrée dans le fichier jar exécutable
De plus, le programme utilise JavaFX, si vous utilisez une version de Java récente qui n'intègre pas JavaFX :  
```shell
$ sudo apt install openjfx
```

## Téléchargement
Téléchargez [ici](https://github.com/Manerr/PetitsChevauxJava/releases/latest "Dernière version") la dernière version  

## Utilisation
> L'argument de lancement `-de` permet de jouer avec un dé truqué  
> L'argument de lancement `-interface` force le lancement du jeu avec l'interface graphique

```shell
$ java -jar PetitsChevaux.jar -de -interface
```

## Liens
- [Code source](https://github.com/Manerr/PetitsChevauxJava/tree/master/src/fr/huautleroux/petitschevaux "Code source")
- [JavaDoc](http://petitschevauxjava.maner.fr/ "JavaDoc")
- [Règles du jeu](https://fr.wikipedia.org/wiki/Jeu_des_petits_chevaux "Règle du jeu des petits chevaux")
- [Sujet du projet](https://www.docdroid.net/8St4O5K/consigne.pdf "Sujet du projet 2019")