import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
/**
  * Created by VARSHA-PC on 4/2/2016.
  */
object LangCount {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc);
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    //EntertainmentTable.printSchema();
    val maxTweets = sqlContext.sql("SELECT lang, count(*) as c FROM EntertainmentTable where lang is not null  group by lang order by c desc limit 10")
    //maxTweets.show()
    maxTweets.rdd.saveAsTextFile("E:/LangTweets.txt")
  }
}