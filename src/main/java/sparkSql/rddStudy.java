package sparkSql;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
public class rddStudy {
	public static void main(String[] args) {
		SparkConf conf=new SparkConf()
				.setAppName("javaRddLearning")
				.setMaster("local");
		JavaSparkContext sc=new JavaSparkContext(conf);
		sc.setLogLevel("ERROR");
		List<Integer> number=Arrays.asList(1,2,3,4,5,6,7,8,9,20);
		JavaRDD<Integer> numberRDD=sc.parallelize(number);
		int sum=numberRDD.reduce(new Function2<Integer,Integer,Integer>(){;
		private static final long serialVersionUID =1L;
		public Integer call(Integer v1,Integer v2) throws Exception{
			return v1+v2;
		}
 		});
		System.out.println("number list 的累加和"+sum);
		
		//利用本地文件创建javardd
		JavaRDD<String> loclRDD=sc.textFile("/home/kehua/eclipse-workspace/javaSpark/dataset/function设计");
		//统计文本内的字数
		JavaRDD<Integer> lineLength = loclRDD.map(new Function<String,Integer>() {
		   private static final long serialVersionUID =1L;
		   public Integer call (String v1) throws Exception{
			   return v1.length();
		   }
		});
		int count =lineLength.reduce(new Function2<Integer,Integer,Integer>(){;
		private static final long serialVersionUID =1L;
		public Integer call(Integer v1,Integer v2) throws Exception{
			return v1+v2;
		}
 		});
		System.out.println("文件总字数 + " + count);	
	}
}
