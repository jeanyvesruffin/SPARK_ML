import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.Predictor;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;
import org.apache.spark.ml.evaluation.Evaluator;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.param.ParamMap;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class MainSparkML {

	// Corresponds a la structure de champs colonne entete csv
	public static final int nbresFeatures = 15;
	// 1er colonne
	public static final String columnLabel = "label";
	private static final String columnFeatures = "features";

	public static void main(String[] args) throws URISyntaxException {
		// destination csv File
		URI rootFolder = MainSparkML.class.getResource("/").toURI();
		String inputDataSetPath = ((URI) rootFolder).resolve("csvFile.csv").getPath();

		SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
		StructField[] structFieldColonne = new StructField[nbresFeatures];
		StructType structType = new StructType(structFieldColonne);

		List<String> featuresColList = new ArrayList<>();

		// 1er colonne qui est la cible
		structFieldColonne[0] = new StructField(columnLabel, DataTypes.DoubleType, false, Metadata.empty());
		// pour les autres colonnes on fait une boucle
		for (int i = 0; i < nbresFeatures; i++) {
			String columnName = "feature" + i;
			structFieldColonne[i] = new StructField(columnName, DataTypes.DoubleType, false, Metadata.empty());
			featuresColList.add(columnName);
		}

		Dataset<Row> dataSet = sparkSession.read().format("csv").option("delimiter", " ").schema(structType)
				.load(inputDataSetPath);

		// Assembleur feature
		VectorAssembler vectorAssembler = new VectorAssembler().setInputCols(featuresColList.toArray(new String[0]))
				.setOutputCol(columnFeatures);

		dataSet = vectorAssembler.transform(dataSet).select(columnLabel, columnFeatures);

		Dataset<Row>[] splitDataSet = dataSet.randomSplit(new double[] { 0.8, 0.2 });
		Dataset<Row> trainingDataSet = splitDataSet[0];
		Dataset<Row> testDataSet = splitDataSet[1];

		// entrainement avec notre dataSet
		RandomForestClassifier classifier = new RandomForestClassifier().setLabelCol(columnLabel)
				.setFeaturesCol(columnFeatures);
		Predictor predictor = classifier;
		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { predictor });
		Evaluator evaluator = new BinaryClassificationEvaluator().setMetricName("areaUnderROC");
		
		
		
		
	}

}
