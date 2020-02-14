package com.knoldus

import java.io.File

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class GetFiles {

  def getListOfFiles(dir: String): Unit = {
    val d = new File(dir)
    val files = d.listFiles.toList
    for (file <- files) {
      if (file.exists() && file.isDirectory) {
        getListOfFiles(file.getPath)
      }
      else {
        println(file.getPath)
      }
    }
  }

}

object GetFiles extends App {
  val getf = new GetFiles
  val files = Future {
    getf.getListOfFiles("/home/knoldus/IdeaProjects/future-api-assignment/src/main/scala/com/knoldus/my-folder")
  }
  files onComplete {
    case Success(files) => println(files)
    case Failure(exception) => println("Error" + exception.getMessage)
  }
}
