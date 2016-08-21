import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.util
import org.apache.spark.sql.SQLContext
/**
  * Created by VARSHA-PC on 4/2/2016.
  */
object TweetsState {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc);
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    EntertainmentTable.printSchema();
    val state = sqlContext.sql("SELECT place.full_name, count(*) as c FROM  EntertainmentTable where text like '%movie%' and place.country = 'United States' group by place.full_name order by c desc limit 15")
    state.rdd.saveAsTextFile("E:/USPlaces.txt")
    //state.show()
  }
}