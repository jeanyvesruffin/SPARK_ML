# SPARK_ML
Machine Learning avec Spark ML

# Creation projet

* Creation d'un projet Maven

* Ajout d'un fichier Readme et gitignore

* Initialisation depot git

```cmd
cd [projet]
git init
git add .
git commit -am "init projet"
```

* Creation d'un repository dans github

* Faire le lien entre le repository local et Github

```cmd
git branch -M main
git remote add origin https://github.com/jeanyvesruffin/SPRING_BATCH_V2.git
git push -u origin main
```

# Context: Coder un système de recommandation de bouquet TV avec Spark ML

## Systeme de recommandation

* Proposer a l'utilisateur des produits qui sont susceptibles de l'interesser.
* Retenir les utilisateurs et les encourager a consommer toujours plus de contenus.
* Necessite generalement 3 etapes:

1. Collecte d'information
2. Modele utilisateur
3. Liste de recommandations

Les 3 approches les plus courantes sont:

1. Basees sur le contenu
2. Collaboratives
3. Hybrides