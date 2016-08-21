import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.util
import org.apache.spark.sql.SQLContext
/**
  * Created by VARSHA-PC on 4/2/2016.
  */
object TimeZonePlaces {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    EntertainmentTable.printSchema();
    val state = sqlContext.sql("SELECT user.time_zone,count(*) as c FROM  EntertainmentTable where user.time_zone <> 'null' group by user.time_zone order by c desc limit 10 ")
    //state.show()
    state.rdd.saveAsTextFile("E:/TimeZonePlaces.txt")
    readLine()
    //state.rdd.collect()
  }
}