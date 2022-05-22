package org.example

import org.apache.spark.{SparkConf, SparkContext}

object WordCountLocal {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordCount").setMaster("local")
    val sc = new SparkContext(conf)
//    val input = sc.parallelize(List("pandas", "i like pandas"))
    val input = sc.textFile("/Users/albert/IdeaProjects/java-world/spark-demo/README.md")
    val words = input.flatMap(line => line.split(" "))
    val counts = words.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}
    counts.coalesce(1, true).saveAsTextFile("/Users/albert/IdeaProjects/java-world/spark-demo/result.txt")
  }
}
