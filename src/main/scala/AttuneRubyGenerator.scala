import com.wordnik.swagger.codegen.BasicRubyGenerator

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

  override def toApiFilename(name: String) = name.toLowerCase.replaceAll("(\\{|\\})","")

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