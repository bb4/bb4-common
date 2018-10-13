/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.app

import scala.collection.immutable.ListMap


/**
  * A convenient packaging of command line options that are specified for a program when you
  * run it from the command line.
  * Parse out the args and put them in a hashmap.
  * We expect the args to be some set of flags of the form -&lt;flag name&gt; followed by and  optional value
  * So an example argument list might be
  * java someProgram -p 3434 -type pente -locale en -verbose -safe -title "my title"
  * Note: verbose and -safe are options and do not have values.
  * @param args an array of command line arguments. May be empty, but not null.
  * @author Barry Becker
  */
class CommandLineOptions(val args: Array[String]) {
  var ct = 0
  private var optionsMap = ListMap[String, String]()

  while (ct < args.length) {
    val arg = args(ct)
    assert(arg.charAt(0) == '-', "Command line Options must start with - and then be followed by an optional value")
    val option = arg.substring(1).trim
    var value: String = null
    if (ct < args.length - 1 && args(ct + 1).charAt(0) != '-') {
      value = args(ct + 1).trim
      ct += 1
    }
    optionsMap += option -> value
    ct += 1
  }

  def getOptions: Set[_] = optionsMap.keySet
  def contains(option: String): Boolean = optionsMap.contains(option)
  override def toString: String = "{" + optionsMap.mkString(", ") + "}"

  /** @param option some string representing the option
    * @return value for the option (may be null if no value for the option)
    */
  def getValueForOption(option: String): String = optionsMap(option)

  /** @param option       the command line option.
    * @param defaultValue if option not found.
    * @return value for the arg (may be null if no value for the arg)
    */
  def getValueForOption(option: String, defaultValue: String): String = optionsMap(option)
}