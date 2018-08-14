package sparkSql;

import org.apache.spark.sql.api.java.UDF1;

public class toUpper {
	private static UDF1 toUpper1 = new UDF1<String, String>() {
	    public String call(final String str) throws Exception {
	        return str.toUpperCase();
	    }
	};
}
