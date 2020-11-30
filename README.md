# SPARK_ML
Machine Learning avec Spark ML.

Spark est un framework bigdata, qui permet de faire de la programmation distribuee.

Outil qui execute des programme au sein de plusieur machine (ne sera pas etudie).

Focus sur le machine learning

# Creation projet

* Creation d'un projet Maven

Dependances utilisées:

```xml
<dependencies>
	<dependency>
		<groupId>org.apache.spark</groupId>
		<artifactId>spark-sql_2.12</artifactId>
		<version>3.0.1</version>
	</dependency>
	<dependency>
		<groupId>org.apache.spark</groupId>
		<artifactId>spark-mllib_2.12</artifactId>
		<version>3.0.1</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```


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

## Schema dataset

* Instancier StructField[]

```java
// Corresponds aux nombres de colonne dans le fichiers CSV
public static final int nbresFeatures = 15;
	
SparkSession sparkSession = SparkSession.builder().masterStructField[] structField = new StructField[nbresFeatures];
```

* Definition des structType

```java
StructType structType = new StructType(structField);
```

## Definition du target (target = probabilite recherche) et du chargement des datas par SPARK

```java
public static final String columnLabel = "label";
// 1er colonne qui est la cible
structFieldColonne[0] = new StructField(columnLabel, DataTypes.DoubleType, false, Metadata.empty());
// pour les autres colonnes on fait une boucle
for (int i = 0; i < nbresFeatures; i++) {
	String columnName = "feature" + i;
	structFieldColonne[i] = new StructField(columnName, DataTypes.DoubleType, false, Metadata.empty());

	}
```

Puis chargement du fichier dataset par SPARK

```java
// destination csv File
URI rootFolder = MainSparkML.class.getResource("/").toURI();
String inputDataSetPath = ((URI) rootFolder).resolve("csvFile.csv").getPath();
Dataset<Row> dataSet = sparkSession.read().format("csv").option("delimiter"," ").schema(structType).load(inputDataSetPath);
	
```

## Utilisation assembleur pour mettre toute les feature dans la meme colonne et les transformer.


```java
private static final String colFeatures="features";
for (int i = 0; i < nbresFeatures; i++) {
			String columnName = "feature" + i;
			structFieldColonne[i] = new StructField(columnName, DataTypes.DoubleType, false, Metadata.empty());
			featuresColList.add(columnName);
}

// Assembleur feature
Dataset<Row> dataSet = sparkSession.read().format("csv").option("delimiter", " ").schema(structType)
				.load(inputDataSetPath);
		
// Assembleur feature
VectorAssembler vectorAssembler = new VectorAssembler().setInputCols(featuresColList.toArray(new String[0])).setOutputCol(colFeatures);
vectorAssembler.transform(dataSet);
```

Transformation, 20% pour le tester (l'evaluer) et 80 % pour l'entrainer (creer le modele). et creation du jeu de test pour l'entrainement et le test.

```java
Dataset<Row>[] splitDataSet = dataSet.randomSplit(new double[] {0.8,0.2});
Dataset<Row> trainingDataSet =splitDataSet[0];
Dataset<Row> testDataSet = splitDataSet[1];
```

## Appliquer notre entrainement a notre dataSet.

RandomForest est un arbre de decision qui permet de classifier en reduisant la variant des previsions et ameliore les performances.

Utilisation du crossValidation permet au modele de fractionner les datas en sous ensembles en utilisant le jeu de test et d'entrainement.

Instanciation de l'estimateur a travers un pipepline.

```java
// entrainement avec notre dataSet
RandomForestClassifier classifier = new RandomForestClassifier().setLabelCol(columnLabel)
		.setFeaturesCol(columnFeatures);
Predictor predictor = classifier;
Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { predictor });

```

Puis evaluation du modele en utilisant le metric "areaUnderROC", voir plus de detail !(https://spark.apache.org/docs/2.2.0/mllib-evaluation-metrics.html)[https://spark.apache.org/docs/2.2.0/mllib-evaluation-metrics.html]

```java
Evaluator evaluator = new BinaryClassificationEvaluator().setMetricName("areaUnderROC");

```

Parametre du RandomForestClassifier





