import org.apache.commons.io.FileUtils
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.util
import org.apache.spark.sql.SQLContext
/**
  * Created by VARSHA-PC on 4/2/2016.
  */
object MaxCountryTweets {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc);
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    //EntertainmentTable.printSchema();
    val maxTweets = sqlContext.sql("SELECT place.country, count(*) as c FROM  EntertainmentTable where place.country <> 'null' group by place.country order by c desc limit 30")
    //val contryTweets = sqlContext.sql("SELECT place.country, count(*) as country_count FROM tweets2 where place.country is not null GROUP BY place.country Order By country_count desc limit 30")
    //maxTweets.show()
    maxTweets.rdd.saveAsTextFile("E:/PB Visuals/CountryTweetsTester.txt")
    sc.stop()
  }
}