import com.wordnik.swagger.codegen.BasicJavaGenerator

object AttuneJavaGenerator extends BasicJavaGenerator {
  def main(args: Array[String]) = generateClient(args)

  override def templateDir = "attune-java"

  override def typeMapping = Map(
    "Array" -> "List",
    "array" -> "List",
    "List" -> "List",
    "boolean" -> "Boolean",
    "string" -> "String",
    "int" -> "Integer",
    "float" -> "Float",
    "long" -> "Long",
    "short" -> "Short",
    "char" -> "String",
    "double" -> "Double",
    "object" -> "Object",
    "integer" -> "Integer",
    "date-time" -> "DateTime")

 override def importMapping = Map(
    "File" -> "java.io.File",
    "Date" -> "java.util.Date",
    "Array" -> "java.util.*",
    "ArrayList" -> "java.util.*",
    "List" -> "java.util.*",
    "Set" -> "java.util.*",
    "DateTime" -> "org.joda.time.*",
    "LocalDateTime" -> "org.joda.time.*",
    "LocalDate" -> "org.joda.time.*",
    "LocalTime" -> "org.joda.time.*",
    "Date-time" -> null
  )

  // api invoker package
  override def invokerPackage = Some("attune.ranking.client")

  // package for models
  override def modelPackage = Some("attune.ranking.client.model")

  // package for api classes
  override def apiPackage = Some("attune.ranking.client.api")

}
