package common.utils

import java.io.File

import com.typesafe.config.ConfigFactory
import slick.codegen.SourceCodeGenerator
import slick.driver.JdbcProfile
import slick.model.Model

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

/**
  * @author iRevThis
  */
class CustomizedCodeGenerator(metamodel: Model) extends SourceCodeGenerator(metamodel) {

  override def code: String = {
    val extensions = tables.map(tableExtension).mkString("\n\n")
    super.code + "\n\n" + generateQueryExtension + "\n\n" + generateTrait + "\n\n" + extensions
  }

  def tableExtension(table: Table) = {
    val tableName = table.model.name.table.toCamelCase
    val filter = queryFilter(table.columnsByName)
    val orderBy = queryOrderBy(tableName, table.columnsByName)

    s"""|implicit class ${tableName}Extension(query: Query[$tableName, $tableName#TableElementType, Seq]) extends ODataSupported {
        |$filter
        |$orderBy
        |$generateODataFunctions
        |}""".stripMargin
  }

  def queryOrderBy(tableName: String, columns: Map[String, TableDef#Column]): String = {
    val regexSeq = columns.toSeq.map(r => orderByRegex(r._1.toCamelCase.uncapitalize))
    val regexes = regexSeq.map(_._2).mkString("\r\n        ")
    val matches = regexSeq.map(r => s"""case ${r._1}Regex(x) => Some(q => if (x == "asc") q.${r._1}.asc else q.${r._1}.desc)""").mkString("\r\n          ")
    s"""  def queryOrderBy(orderByOpt: Option[String]): Option[Function[$tableName,  slick.lifted.ColumnOrdered[_]]] = {
        |    orderByOpt flatMap { orderBy =>
        |      $regexes
        |        orderBy match {
        |          $matches
        |          case _ => None
        |      }
        |    }
        |  }
      """.stripMargin
  }

  def queryFilter(columns: Map[String, TableDef#Column]): String = {
    val validTypes = Seq("String", "Int", "Float", "Long")
    val regexSeq = columns.toSeq.filter(n => validTypes.exists(n._2.actualType.contains)).map(r => filterRegex(r._2.model.nullable, validTypes.find(r._2.actualType.contains).get, r._1.toCamelCase.uncapitalize))
    val regexes = regexSeq.flatMap(_._2).mkString("\r\n        ")
    val matches = regexSeq.flatMap(r => filterQuery(r._1._2, r._1._1._2, r._1._1._1)).mkString("\r\n          ")
    s"""  def queryFilter(filterOpt: Option[String]) = {
        |    filterOpt match {
        |      case None => query
        |      case Some(filter) =>
        |        $regexes
        |        filter match {
        |          $matches
        |          case _ => query
        |        }
        |    }
        |  }
      """.stripMargin
  }

  def generateTrait = {
    """
      |trait ODataSupported {
      |  protected def offset(implicit request: play.api.mvc.Request[_]) = decodedQueryString("offset").map(_.toLong).getOrElse(0L)
      |  protected def limit(implicit request: play.api.mvc.Request[_]) = decodedQueryString("limit").map(_.toLong).getOrElse(50L)
      |  protected def filter(implicit request: play.api.mvc.Request[_]) = decodedQueryString("$filter")
      |  protected def orderBy(implicit request: play.api.mvc.Request[_]) = decodedQueryString("$orderby")
      |  protected def decodedQueryString(key: String)(implicit request: play.api.mvc.Request[_]) = request.getQueryString(key).map(java.net.URLDecoder.decode(_, "UTF-8"))
      |  protected def decodedQueryStrings(key: String)(implicit request: play.api.mvc.Request[_]) = request.queryString.getOrElse(key, Seq.empty).map(java.net.URLDecoder.decode(_, "UTF-8"))
      |}""".stripMargin
  }

  def generateQueryExtension = {
    """implicit class QueryExtension[T, E](q: Query[T, E, Seq]) extends ODataSupported {
      |  def page(offset: Long, limit: Long): Query[T, E, Seq] = q.drop(offset).take(limit)
      |  def page(implicit request: play.api.mvc.Request[_]): Query[T, E, Seq] = q.page(offset, limit)
      |}""".stripMargin
  }

  def generateODataFunctions = {
    """  def applyOData(implicit request: play.api.mvc.Request[_]) = {
      |     val filters = decodedQueryStrings("$filter")
      |     val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
      |     queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
      |  }
      |  def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)""".stripMargin
  }

  def filterRegex(nullable: Boolean, columnType: String, columnName: String) = {
    columnType match {
      case "String" => ((columnName -> columnType) -> nullable) -> Seq( s"""val ${columnName}Regex = "contains\\\\($columnName,'([\\\\w\\\\d].*)'\\\\)".r""")
      case "Int" | "Long" | "Float" =>
        ((columnName -> columnType) -> nullable) -> Seq(
          s"""val ${columnName}GtRegex = "$columnName gt ([\\\\d]{1,})".r""",
          s"""val ${columnName}LtRegex = "$columnName lt ([\\\\d]{1,})".r""",
          s"""val ${columnName}EqRegex = "$columnName eq ([\\\\d]{1,})".r"""
        )
      case _ => ((columnName -> columnType) -> nullable) -> Seq.empty
    }
  }

  def filterQuery(nullable: Boolean, columnType: String, columnName: String) = {
    columnType match {
      case "String" =>
        nullable match {
          case true => Seq( s"""case ${columnName}Regex(x) => query.filter(_.$columnName.filter(_.toLowerCase like s"%$${x.toLowerCase}%").isDefined)""")
          case false => Seq( s"""case ${columnName}Regex(x) => query.filter(_.$columnName.toLowerCase like s"%$${x.toLowerCase}%")""")
        }
      case "Int" =>
        nullable match {
          case true =>
            Seq(
              s"""case ${columnName}GtRegex(x) => query.filter(_.$columnName.filter(_ >= x.toInt).isDefined)""", //gt
              s"""case ${columnName}LtRegex(x) => query.filter(_.$columnName.filter(_ <= x.toInt).isDefined)""", //lt
              s"""case ${columnName}EqRegex(x) => query.filter(_.$columnName.filter(_ === x.toInt).isDefined)""" //eq
            )
          case false =>
            Seq(
              s"""case ${columnName}GtRegex(x) => query.filter(_.$columnName >= x.toInt)""", //gt
              s"""case ${columnName}LtRegex(x) => query.filter(_.$columnName <= x.toInt)""", //lt
              s"""case ${columnName}EqRegex(x) => query.filter(_.$columnName === x.toInt)""" //eq
            )
        }
      case "Long" =>
        nullable match {
          case true =>
            Seq(
              s"""case ${columnName}GtRegex(x) => query.filter(_.$columnName.filter(_ >= x.toLong).isDefined)""", //gt
              s"""case ${columnName}LtRegex(x) => query.filter(_.$columnName.filter(_ <= x.toLong).isDefined)""", //lt
              s"""case ${columnName}EqRegex(x) => query.filter(_.$columnName.filter(_ === x.toLong).isDefined)""" //eq
            )
          case false =>
            Seq(
              s"""case ${columnName}GtRegex(x) => query.filter(_.$columnName >= x.toLong)""", //gt
              s"""case ${columnName}LtRegex(x) => query.filter(_.$columnName <= x.toLong)""", //lt
              s"""case ${columnName}EqRegex(x) => query.filter(_.$columnName === x.toLong)""" //eq
            )
        }
      case "Float" =>
        nullable match {
          case true =>
            Seq(
              s"""case ${columnName}GtRegex(x) => query.filter(_.$columnName.filter(_ >= x.toFloat).isDefined)""", //gt
              s"""case ${columnName}LtRegex(x) => query.filter(_.$columnName.filter(_ <= x.toFloat).isDefined)""", //lt
              s"""case ${columnName}EqRegex(x) => query.filter(_.$columnName.filter(_ === x.toFloat).isDefined)""" //eq
            )
          case false =>
            Seq(
              s"""case ${columnName}GtRegex(x) => query.filter(_.$columnName >= x.toFloat)""", //gt
              s"""case ${columnName}LtRegex(x) => query.filter(_.$columnName <= x.toFloat)""", //lt
              s"""case ${columnName}EqRegex(x) => query.filter(_.$columnName === x.toFloat)""" //eq
            )
        }
      case _ => Seq.empty
    }
  }

  def orderByRegex(columnName: String) = columnName -> s"""val ${columnName}Regex = "$columnName (asc|desc)".r"""

}

object CustomizedCodeGenerator {

  def main(args: Array[String]) = {
    val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()
    val url = conf.getString("slick.dbs.default.db.url")
    val jdbcDriver = conf.getString("slick.dbs.default.db.driver")
    val slickDriver = conf.getString("slick.dbs.default.driver").dropRight(1)
    val pkg = "models"
    val user = conf.getString("slick.dbs.default.db.user")
    val outputDir = s"""${new File("").getAbsolutePath}\\app"""
    val password = conf.getString("slick.dbs.default.db.password")
    run(slickDriver, jdbcDriver, url, outputDir, pkg, Some(user), Some(password))
  }

  def run(slickDriver: String, jdbcDriver: String, url: String, outputDir: String, pkg: String, user: Option[String], password: Option[String]): Unit = {
    val driver: JdbcProfile = Class.forName(slickDriver + "$").getField("MODULE$").get(null).asInstanceOf[JdbcProfile]
    val dbFactory = driver.api.Database
    val db = dbFactory.forURL(url, driver = jdbcDriver, user = user.orNull, password = password.orNull, keepAliveConnection = true)
    try {
      val m = Await.result(db.run(driver.createModel(None, ignoreInvalidDefaults = false)(ExecutionContext.global).withPinnedSession), Duration.Inf)
      new CustomizedCodeGenerator(m).writeToFile(slickDriver, outputDir, pkg)
    } finally db.close
  }
}
