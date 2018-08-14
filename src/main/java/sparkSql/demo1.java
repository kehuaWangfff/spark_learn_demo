package sparkSql;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

import scala.Function1;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

public class demo1 {
	public static void main(String[] args) {
		UDF1 toUpper = new UDF1<String, String>() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public String call(final String str) throws Exception {
		        return str.toUpperCase();
		    }
		};
		
		SparkConf conf =new SparkConf()
				.setAppName("sparkSql_demo1")
				.setMaster("local");
		JavaSparkContext spark=new JavaSparkContext(conf);
		spark.setLogLevel("ERROR");
		SQLContext sql=new SQLContext(spark);
		
		Dataset<Row> df=sql.read().format("csv")
				.option("header", "false")
				.option("sep",  "\t")
				.load("/home/kehua/Downloads/purchance.csv");
		
		Dataset<Row> df_header=sql.read()
				.format("csv")
				.option("header", "true")
				.option("sep", ",")
				.load("/home/kehua/Downloads/purchance_dictionary.csv");
		//从df_header中拿出Field_Name	一列转化成字符串数组
		Dataset<Row> test1 = df_header.select(col("Field_Name"));
		//System.out.println(test1);
		sql.udf().register("toUpper", toUpper, DataTypes.StringType);
		//df.select(col("name"),callUDF("toUpper", col("name"))).show();
		df.show();
		df_header.show();
	    df.printSchema();
 	}
  
}
