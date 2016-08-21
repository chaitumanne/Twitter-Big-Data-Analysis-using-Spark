import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.util
import org.apache.spark.sql.SQLContext
/**
  * Created by VARSHA-PC on 4/2/2016.
  */
object TopTenUsers {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc);
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    //EntertainmentTable.printSchema();
    val playingNation = sqlContext.sql("SELECT user.name, user.followers_count FROM EntertainmentTable where text like '%movie%' order by user.followers_count desc limit 10")
      //playingNation.rdd.saveAsTextFile("E:/TopTenFollowingUsers.txt")
      //"where user.name <> 'null' group by user.name, user.followers_count order by user.followers_count desc limit 10 ")
    playingNation.show()

  }
}