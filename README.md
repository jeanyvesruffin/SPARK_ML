# SPARK_ML
Machine Learning avec Spark ML.

Spark est un framework bigdata, qui permet de faire de la programmation distribuee.

Outil qui execute des programme au sein de plusieur machine (ne sera pas etudie).

Focus sur le machine learning

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

# Context: Coder un syst�me de recommandation de bouquet TV avec Spark ML

## Systeme de recommandation

* Proposer a l'utilisateur des produits qui sont susceptibles de l'interesser.
* Retenir les utilisateurs et les encourager a consommer toujours plus de contenus.
* Necessite generalement 3 etapes:

1. Collecte d'information
2. Modele utilisateur
3. Liste de recommandations

Les 3 approches les plus courantes sont:

1. Basees sur le contenu (historique user)
2. Collaboratives (profil user similaire a un autre)
3. Hybrides (ou les deux)

But: Calculer la **probabilite d'achat a un bouquet**, a l'aide des algorithmes de machine learning en utilisant un dataset base sur la consommation TV/Web et sur des donnees de connaissance client (genre, age , localite ...)

### Collecte et preparation de donnees

* Identifier les clients ayant achete un bouquet particulier
* Collecter leur consommation TV
* Enrichir le dataset avec d'autres donnees

### Modele user

* Creer le modele en se basant sur la consommation TV des autres foyers, suivant leur similarite/ rapprochement possible

### Recommandation

Calculer la probabilite d'achat d'un bouquet particulier

## Processus de machine learning

Collect >> Pre-traitement (netoyage des datas) >> Modelisation (user a achete ou non) >> Evaluation (eval du modele a l'aide des metrics) >> Deploiement

# DEMO

## Configuration

* Instancier Spark

```java
SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
```

* Instancier StructField[]

```java
// Corresponds aux nombres de variables dans le fichiers CSV
public static final int nbresFeatures = 1500;
	
SparkSession sparkSession = SparkSession.builder().masterStructField[] structField = new StructField[nbresFeatures];
```

* Definition des colonnes du dataset



## Schgema dataset

## Definir les feature (donnees)

## Algorythgme

## Validation (croissvalidation)







