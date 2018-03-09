#!/usr/bin/env amm

/*
Step5

This script copies all the snippy output directories into a separate folder which is then used for the snippy analysis overall.


*/

import ammonite.ops._
import ammonite.ops.ImplicitWd._
import scala.collection.mutable.ArrayBuffer


// Array of all Genomes 
var allGenomeFolders = (ls!).toArray



// 
// var genome_name = all_genomes(0).toString.split("/").last.split("-")(0)




def findUniqueGenomes(allGenomeFolders: Array[Path]): ArrayBuffer[String] = {

  var  allGenomeNames = ArrayBuffer[String]()

  for (folderName <- allGenomeFolders) {

if( folderName.isFile == false) { 

    var genomeName = folderName.toString.split("/").last.split("-")(0)

    allGenomeNames += genomeName
}
  }


//	println(allGenomeNames)
  return allGenomeNames

}


var allGenomeNames = findUniqueGenomes(allGenomeFolders)


for (genomeName <- allGenomeNames){

println(genomeName)

cp.into(pwd/(genomeName + "-done")/(genomeName + "_analysis")/genomeName, pwd/up/'snippy_outputs/'all_genomes)

}



