import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.util
import org.apache.spark.sql.SQLContext
/**
  * Created by VARSHA-PC on 4/2/2016.
  */
object UserFollowers {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc);
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
   // val EntertainmentTableView = sqlContext.jsonFile("E:/tweetsdump.txt");
    //EntertainmentTableView.registerTempTable("EntertainmentTableView");
    //EntertainmentTable.printSchema();
    /*val temp = sqlContext.sql("SELECT sum(N) as NBA, sum(C) as Cricket, sum(S) as Soccer, sum(T) as Tennis, sum(B) as Baseball " +
     "FROM " +
     "(SELECT count(*) as N, 0 as C, 0 as S, 0 as T, 0 as B FROM EntertainmentTable where text like '%nba%' " +
     "UNION All " +
     "SELECT 0 as N,count(*) as C, 0 as S, 0 as T, 0 as B FROM  EntertainmentTable where text like '%cricket%'" +
     "UNION All " +
     "SELECT 0 as N, 0 as C, count(*) as S, 0 as T, 0 as B FROM  EntertainmentTable where text like '%soccer%' " +
     "UNION ALL " +
     "SELECT 0 as N, 0 as C, 0 as S, count(*) as T, 0 as B FROM  EntertainmentTable where text like '%tennis%' " +
     "UNION ALL " +
     "SELECT 0 as N, 0 as C, 0 as S, 0 as T, count(*) as B FROM  EntertainmentTable where text like '%baseball%')")*/
    //val temp = sqlContext.sql("SELECT if(user.verified=true,'verified_users','nonverified_users') as status,count(*) FROM EntertainmentTable GROUP BY user.verified LIMIT 2 ")
   //val temp = sqlContext.sql("SELECT count(*) as C  FROM EntertainmentTable   WHERE text like '%nba%' UNION SELECT count(*) as C  FROM EntertainmentTable   WHERE text like '%cricket%' order by C")
   val temp = sqlContext.sql("SELECT count(*),text, CASE WHEN text like '%nba%' THEN 'NBA' WHEN text like '%cricket%' THEN 'cricket' END AS n  FROM EntertainmentTable")
    temp.show()
    //or text like '%tennis%' or text like '%soccer%' or text like '%cricket%' or text like '%baseball%'
    //
  }
}