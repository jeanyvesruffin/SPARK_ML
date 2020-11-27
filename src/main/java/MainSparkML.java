import java.net.URI;

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
	// destination csv File
	URI rootFolder = MainSparkML.class.getResource("/").toURI();
	String inputDataSetPath = ((URI) rootFolder).resolve()
	
	
	
	public static void main(String[] args) {
		SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
		StructField[] structFieldColonne = new StructField[nbresFeatures];
		StructType structType = new StructType(structFieldColonne);

		// 1er colonne qui est la cible
		structFieldColonne[0] = new StructField(columnLabel, DataTypes.DoubleType, false, Metadata.empty());
		// pour les autres colonnes on fait une boucle
		for (int i = 0; i < nbresFeatures; i++) {
			String columnName = "feature" + i;
			structFieldColonne[i] = new StructField(columnName, DataTypes.DoubleType, false, Metadata.empty());

		}

		sparkSession.read().format("csv").option("delimiter"," ").schema(structType).load(path);
	}

}
