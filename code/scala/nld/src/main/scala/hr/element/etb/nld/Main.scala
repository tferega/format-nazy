package hr.element.etb.nld

import java.util.Arrays
import scalax.file._
import scalax.io.Codec
import difflib._

import scala.collection.JavaConversions._

object Diff {
  def markLines(body: Array[Byte], codec: Codec) =
    new String(body, codec.charSet)
    .replaceAll("\r\n", "[CR][LF][EOL]")
    .replaceAll("\r", "[CR][EOL]")
    .replaceAll("\n", "[LF][EOL]")
    .split("\\[EOL\\]").toList
}

case class Replacement(file: Path, orig: Array[Byte], body: Array[Byte], codec: Codec) {
  lazy val patch = {
    val oL = Diff.markLines(orig, codec)
    val nL = Diff.markLines(body, codec)

    val patch = DiffUtils.diff(oL, nL)
    patch
  }

  lazy val deltas =
    patch.getDeltas.toList
      .flatMap(_.getRevised.getLines.toList.asInstanceOf[List[String]])
      .toIndexedSeq

  lazy val isDifferent =
    deltas.size > 0

  override def toString() = {
    file.path + ": " + (isDifferent match {
      case true =>
        "[%d lines changed]" format (deltas.size)
      case _ =>
      "[no changes]"
    })
  }
}

object Main {
  def main(args: Array[String]) {

    val base = FileSystem.default(args.head)
    val all =
      processScalaFiles(base) ++
      processJavaFiles(base) ++
      processPhpFiles(base) ++
      processCsFiles(base) ++
      processBatFiles(base) ++
      processBashFiles(base)

    all.filter(_.isDifferent)
      .foreach{ f =>
        println("Processing: " + f)
        f.file.write(f.body)
      }
  }

  def processScalaFiles(base: Path) = {
    (base **  "*.scala").toStream.map{ file =>
      val orig =
        file.byteArray

      val body =
        new String(orig, "UTF-8")
        .split("(\r\n|[\r\n])")
        .map(_.replaceAll("""^(.*?)\s+$""", "$1"))
        .map(_.replaceAll("\t", "  "))
        .mkString("", "\n", "\n")
        .getBytes("UTF-8")

      new Replacement(file, orig, body, Codec.UTF8)
    }
  }

  def processJavaFiles(base: Path) = {
    (base **  "*.java").toStream.map{ file =>
      val orig =
        file.byteArray

      val body =
        new String(orig, "UTF-8")
        .split("(\r\n|[\r\n])")
        .map(_.replaceAll("""^(.*?)\s+$""", "$1"))
        .map(_.replaceAll("\t", "  "))
        .mkString("", "\n", "\n")
        .getBytes("UTF-8")

      new Replacement(file, orig, body, Codec.UTF8)
    }
  }

  def processPhpFiles(base: Path) = {
    (base **  "*.java").toStream.map{ file =>
      val orig =
        file.byteArray

      val body =
        new String(orig, "UTF-8")
        .split("(\r\n|[\r\n])")
        .map(_.replaceAll("""^(.*?)\s+$""", "$1"))
        .map(_.replaceAll("\t", "  "))
        .mkString("", "\n", "\n")
        .getBytes("UTF-8")

      new Replacement(file, orig, body, Codec.UTF8)
    }
  }

  def processBatFiles(base: Path) = {
    (base **  "*.bat").toStream.map{ file =>
      val orig =
        file.byteArray

      val body =
        new String(orig, "cp1250")
        .split("(\r\n|[\r\n])")
        .map(_.replaceAll("""^(.*?)\s+$""", "$1"))
        .map(_.replaceAll("\t", "  "))
        .mkString("", "\r\n", "\r\n")
        .getBytes("cp1250")

      new Replacement(file, orig, body, Codec("cp1250"))
    }
  }

  def processBashFiles(base: Path) = {
    (base **  "*.sh").toStream.map{ file =>
      val orig =
        file.byteArray

      val body =
        new String(orig, "UTF-8")
        .split("(\r\n|[\r\n])")
        .map(_.replaceAll("""^(.*?)\s+$""", "$1"))
        .map(_.replaceAll("\t", "  "))
        .mkString("", "\n", "\n")
        .getBytes("UTF-8")

      new Replacement(file, orig, body, Codec("UTF-8"))
    }
  }

  def processCsFiles(base: Path) = {
    (base **  "*.cs" --- base ** "AssemblyInfo.cs").toStream.map{ file =>
      val orig =
        file.byteArray

      val body =
        new String(orig, "UTF-8")
        .split("(\r\n|[\r\n])")
        .map(_.replaceAll("""^(.*?)\s+$""", "$1"))
        .mkString("", "\n", "\n")
        .getBytes("UTF-8")

      new Replacement(file, orig, body, Codec("UTF-8"))
    }
  }
}