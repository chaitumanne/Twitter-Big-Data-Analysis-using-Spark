import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkContext, SparkConf}
import scala.collection.mutable.ListBuffer

/**
  * Created by VARSHA-PC on 4/10/2016.
  */

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//@WebServlet(Array("/"))
object Test extends HttpServlet {
  override def doGet(req: HttpServletRequest, res: HttpServletResponse) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc);
    /*val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    //EntertainmentTable.printSchema();
    val maxTweets = sqlContext.sql("SELECT place.country, count(*) as c FROM  EntertainmentTable where place.country <> 'null' group by place.country order by c desc limit 10")
    //maxTweets.show()
    maxTweets.rdd.saveAsTextFile("E:/PB Visuals/CountryTweetsTest.txt")*/
    //val df = sqlContext.read.json("E:/PB Visuals/CountryTweetsTest.txt/part-00000")
    //df.select("_corrupt_record").show()
    val df = scala.io.Source.fromFile("E:/PB Visuals/TopTenUsers.txt/part-00000").getLines.flatMap(_.split(","))
    var users = new ListBuffer[String]()
    for(x <- df){
      if (x.startsWith("[") && x.endsWith("]")){
        users +=x.substring(1,x.length-1)
      }
      else if(x.startsWith("[")){
        println(x.substring(1,x.length))
      }
    }
    val usersList = users.toList
    println(usersList)
    res.sendRedirect("demo.html")
  }
}
