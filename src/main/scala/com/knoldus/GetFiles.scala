package com.knoldus

import java.io.File

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class GetFiles {

  def getListOfFiles(dir: String): Future[ArrayBuffer[List[File]]] = Future {
    val dirNames = ArrayBuffer[List[File]]()
    val d = new File(dir)
    val files = d.listFiles.toList
    for (file <- files) {
      if (file.exists() && file.isDirectory && file.getName != "target") {
        dirNames += file.listFiles.filter(_.isFile).toList
        getListOfFiles(file.getPath)
      }
      else {
        ArrayBuffer[List[File]]()
      }

    }
    dirNames
  }

}

object GetFiles extends App {
  val getf = new GetFiles
  val files = getf.getListOfFiles("/home/knoldus/IdeaProjects/future-api-assignment/src/main/scala/com/knoldus/my-folder")

  files onComplete {
    case Success(files) => println("List of files: " + files)
    case Failure(exception) => println("Error: " + exception.getMessage)
  }

  println("Ending...")
}
