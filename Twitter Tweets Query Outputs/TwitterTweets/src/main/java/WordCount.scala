/**
  * Created by VARSHA-PC on 2/11/2016.
  */
import org.apache.spark._
import org.apache.spark.SparkContext._

object WordCount {
  def main(args: Array[String]) {
    // Create a Scala Spark Context.
    val sparkContent = new SparkContext("local[*]","Word Count")
    // Load our input data.
    val inputFile =  sparkContent.textFile("/E:/UMKC/Tweets.txt")
    // Hadoop path set.
    System.setProperty("hadoop.home.dir", "/E:/winutil/")
    // Split up into words and count.
    val counts = inputFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_+_)
    // Save the word count back out to a text file, causing evaluation.
    counts.saveAsTextFile("/E:/UMKC/Tweets Output")
    readLine()
  }
}