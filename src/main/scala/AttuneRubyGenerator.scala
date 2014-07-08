import com.wordnik.swagger.codegen.BasicRubyGenerator
import com.wordnik.swagger.model._

import java.beans.Introspector
import java.io.File

object AttuneRubyGenerator extends BasicRubyGenerator {

  def main(args: Array[String]) = generateClient(args)

  override def apiPackage = Some("/attune/api")

  override def templateDir = "attune-ruby"

  override def destinationDir = "/Users/jason/dev/attune-ruby/lib"

  override def modelPackage = Some("/attune/models")

  override def toApiName(name: String) = {
    var fixedName = name.replaceAll("(\\{|\\})","")
    fixedName(0).toUpper + fixedName.substring(1)
  }

  override def toApiFilename(name: String) = toUnderscore(name).toLowerCase.replaceAll("(\\{|\\})","")
  override def toModelFilename(name: String) = toUnderscore(name).toLowerCase.replaceAll("(\\{|\\})","")

  override def toUnderscore(name: String): String = {
    "[A-Z\\d]".r.replaceAllIn(Introspector.decapitalize(name), {m => "_" + m.group(0).toLowerCase() })
  }

  override def toDeclaration(obj: ModelProperty) = {
    var dataType = obj.`type`(0).toUpper + obj.`type`.substring(1)

    dataType match {
      case "Set" => dataType = "Array"
      case "Int" => dataType = "Integer"
      case "Date-time" => dataType = "String"
      case e: String => e
    }

    val defaultValue = toDefaultValue(dataType, obj)
    dataType match {
      case "Array" => {
        val inner = {
          obj.items match {
            case Some(items) => {
              if(items.ref != null)
                items.ref.getOrElse(items.`type`)
              else
                items.`type`
            }
            case _ => {
              println("failed on " + dataType + ", " + obj)
              throw new Exception("no inner type defined")
            }
          }
        }
        dataType = "Array<" + toDeclaredType(inner) + ">"
      }
      case _ =>
    }
    (dataType, defaultValue)
  }

  override def toDeclaredType(dt: String): String = {
    typeMapping.getOrElse(dt, "Attune::Model::" + dt)
  }

  override def typeMapping = Map(
    "float" -> "BigDecimal",
    "long" -> "Integer",
    "double" -> "BigDecimal",
    "integer" -> "Integer",
    "Array" -> "Array",
    "boolean" -> "Boolean",
    "string" -> "String",
    "date" -> "String",
    "dateTime" -> "String"
  )

  override def languageSpecificPrimitives = Set(
    "String",
    "BigDecimal",
    "Integer",
    "Array",
    "Boolean")

  override def supportingFiles = List(
    ("attune.mustache", destinationDir, "attune.rb"),
    ("attune" + File.separator + "call_dropping.mustache", destinationDir, "attune" + File.separator + "call_dropping.rb"),
    ("attune" + File.separator + "client.mustache", destinationDir, "attune" + File.separator + "client.rb"),
    ("attune" + File.separator + "configurable.mustache", destinationDir, "attune" + File.separator + "configurable.rb"),
    ("attune" + File.separator + "default.mustache", destinationDir, "attune" + File.separator + "default.rb"),
    ("attune" + File.separator + "json_logger.mustache", destinationDir, "attune" + File.separator + "json_logger.rb"),
    ("attune" + File.separator + "net_http_persistent.mustache", destinationDir, "attune" + File.separator + "net_http_persistent.rb"),
    ("attune" + File.separator + "param_flattener.mustache", destinationDir, "attune" + File.separator + "param_flattener.rb"),
    ("attune" + File.separator + "version.mustache", destinationDir, "attune" + File.separator + "version.rb"))

}