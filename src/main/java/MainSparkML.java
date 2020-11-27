import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructField;

public class MainSparkML {

	// Corresponds a la structure de champs colonne entete csv
	public static final int nbresFeatures = 1500;
	
	
	public static void main(String[] args) {

		SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
		
		StructField[] structField = new StructField[nbresFeatures];
		

		
		
	}

}
