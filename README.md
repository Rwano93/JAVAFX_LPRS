# Gestion d'Événements - JavaFX

## Description
Ce projet est une application de gestion d'événements développée en JavaFX. Il permet de gérer les stocks, les étudiants, les professeurs et la secrétaire, qui ont un lien entre eux dans le cadre de l'organisation des événements.

## Fonctionnalités
- Gestion des étudiants : ajout, modification, suppression et consultation.
- Gestion des professeurs : ajout, modification, suppression et consultation.
- Gestion de la secrétaire : ajout et gestion des tâches administratives.
- Gestion du stock : suivi des ressources et matériel nécessaire aux événements.
- Organisation des événements : liaison entre les différentes entités.

## Technologies utilisées
- **Langage** : Java
- **Framework** : JavaFX
- **Base de données** : SQLite/MySQL (selon configuration)
- **Outils** : Scene Builder, Maven/Gradle

## Prérequis
- Java 17 ou supérieur
- Maven ou Gradle installé
- Une base de données configurée (SQLite ou MySQL)

## Installation
1. Cloner le dépôt :
   ```sh
   git clone https://github.com/Rwano93/JAVAFX_LPRS
   ```
2. Ouvrir le projet avec votre IDE préféré (IntelliJ IDEA, Eclipse, NetBeans...)
3. Vérifier les dépendances et les installer avec Maven :
   ```sh
   mvn clean install
   ```
   ou avec Gradle :
   ```sh
   gradle build
   ```
4. Configurer la base de données dans le fichier `application.properties` ou `config.xml`.
5. Lancer l'application depuis la classe principale :
   ```sh
   mvn javafx:run
   ```
   ou
   ```sh
   java -jar nom-du-projet.jar
   ```

## Utilisation
1. Se connecter en tant que gestionnaire, professeur, secrétaire ou étudiant.
2. Accéder aux fonctionnalités selon les droits d'utilisateur.
3. Ajouter/Modifier/Supprimer les informations des étudiants, professeurs, stock, et événements.
4. Suivre et gérer les événements en temps réel.

## Captures d'écran
(Insérez ici des captures d'écran de votre application en action.)

## Contributions
Les contributions sont les bienvenues ! Suivez ces étapes :
1. Forker le projet.
2. Créer une branche avec vos modifications : `git checkout -b feature-nouvelle-fonctionnalite`
3. Committer vos modifications : `git commit -m "Ajout d'une nouvelle fonctionnalité"`
4. Pousser la branche : `git push origin feature-nouvelle-fonctionnalite`
5. Ouvrir une Pull Request.

## Auteur
- **Erwan** - [Rwano93](https://github.com/Rwano93))
- **Nathan** - [ItsNTH](https://github.com/ItsNTH)))
- **Ilyes** - [lyes9](https://github.com/lyes9)))

