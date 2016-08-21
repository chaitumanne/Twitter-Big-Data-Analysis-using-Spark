import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.util
import org.apache.spark.sql.SQLContext
/**
  * Created by VARSHA-PC on 4/2/2016.
  */
object TopUrls {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc);
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    //EntertainmentTable.printSchema();
    //val state = sqlContext.sql("SELECT media.media_url, media.type, count(*) as c FROM  EntertainmentTable where type <> 'null' group by media.media_url, media.type order by c desc")
    val state = sqlContext.sql("SELECT source, count(*) as c FROM EntertainmentTable where source <> 'null' group by source order by c desc limit 10")
    state.rdd.saveAsTextFile("E:/SourceTweets.txt")
    readLine()
    //state.show()
  }
}