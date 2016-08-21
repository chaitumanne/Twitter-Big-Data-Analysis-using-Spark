import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

import scala.collection.mutable.ListBuffer

/**
  * Created by VARSHA-PC on 4/2/2016.
  */
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(Array("/"))
class MyServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, res: HttpServletResponse) {
    //val writer = res.getWriter
    res.sendRedirect("demo.html")
  }

}

object MaxFollwers {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // initialise spark context
    val conf = new SparkConf().setAppName("PbSpark").setMaster("local[2]").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc);
    val hadoopConf = new org.apache.hadoop.conf.Configuration()
    val hdfs = org.apache.hadoop.fs.FileSystem.get(new java.net.URI("hdfs://localhost:9000"), hadoopConf)
    val filepath = "E:/PB Visuals/MaxFollowers"
    val EntertainmentTable = sqlContext.jsonFile("E:/tweetsdump.txt");
    EntertainmentTable.registerTempTable("EntertainmentTable");
    //EntertainmentTable.printSchema();
    val maxTweets = sqlContext.sql("SELECT user.name, user.followers_count FROM EntertainmentTable where text like '%movie%' order by user.followers_count desc limit 10")
    //maxTweets.show()

    try { hdfs.delete(new org.apache.hadoop.fs.Path(filepath), true) } catch { case _ : Throwable => { } }
    maxTweets.rdd.saveAsTextFile("E:/PB Visuals/MaxFollowers")
    readLine()
    val df = scala.io.Source.fromFile("E:/PB Visuals/MaxFollowers/part-00000").getLines.flatMap(_.split(","))
    var users = new ListBuffer[String]()
    for(x <- df){
      if (x.startsWith("[") && x.endsWith("]")){
        users +=x.substring(1,x.length-1)
      }
      else if(x.startsWith("[")){
        users +=x.substring(1,x.length)
      }
    }
    val usersList = users.toList
    println(usersList)
    sc.stop()
  }
}