package org.example

object PkgUtil {

  def transformDot(pkg: String): String = pkg.replaceAll("\\.", "\\\\.")

  def rlikePkgs(pkgs: Array[String]): String = {
    if(pkgs == null || pkgs.length == 0) ""
    else "^(" + pkgs.map(x => transformDot(x)).mkString(")|(") + ")"
  }

  def main(args: Array[String]): Unit = {
    val rlike1 = rlikePkgs(Array.apply("com.lge.", "com.sharp."))
    val rlike2 = """^(com.lge.)|(com.sharp.)"""
    println()
  }

}
