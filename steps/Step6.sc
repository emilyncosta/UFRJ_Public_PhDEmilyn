#!/usr/bin/env amm

/*
 Step6 - FastQC analysis

Here, we are running the FastQC analysis of all trimmed_paired files

 */

import ammonite.ops._
import ammonite.ops.ImplicitWd._



import scala.collection.mutable.ArrayBuffer

def is_trimmed_paired_?(file_name:String) : Boolean = {

  //  "ab c d e f".matches(".*f$")

  return file_name.matches(".*trimmed_paired.fastq$")

}


//var all_files = ls! pwd

var all_files = ls.rec!



var all_trimmed_paired_files = all_files.filter( (f) => is_trimmed_paired_?(f.toString) ).map( (f) => f.toString)



def fastqc_analysis(genome_name:String) = {



  var cmd_string =  "fastqc " + genome_name

  println(cmd_string)

  %("bash", "-c", cmd_string)

  println("\n\n")
}



for (f <- all_trimmed_paired_files) {


  fastqc_analysis(f)

}
