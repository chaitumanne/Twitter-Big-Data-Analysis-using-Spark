import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.util
import org.apache.spark.sql.SQLContext
/**
  * Created by VARSHA-PC on 4/2/2016.
  */
object Verified {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc);
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    //EntertainmentTable.printSchema();
    val movie = sqlContext.sql("SELECT count(user.verified) as c FROM EntertainmentTable")
    movie.rdd.saveAsTextFile("E:/PB Visuals/Tester.txt")
    val df = sqlContext.read.json("E:/PB Visuals/Tester.txt/part-00000")

    // Displays the content of the DataFrame to stdout
    df.show()
  }
}