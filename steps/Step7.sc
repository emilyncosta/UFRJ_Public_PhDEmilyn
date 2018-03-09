#!/usr/bin/env amm

/*
 Step6 - FastQC analysis

Here, we are running the FastQC analysis of all trimmed_paired files

 */

import ammonite.ops._
import ammonite.ops.ImplicitWd._



import scala.collection.mutable.ArrayBuffer

def is_trimmed_paired_html_?(file_name:String) : Boolean = {

  //  "ab c d e f".matches(".*f$")

  return file_name.matches(".*trimmed_paired.fastqc.html$")

}




//var all_files = ls! pwd

var all_files = ls.rec!



var all_trimmed_paired_files_html = all_files.filter( (f) => is_trimmed_paired_html_?(f.toString) ).map( (f) => f.toString)




for (genomeName <- all_trimmed_paired_files_html){

println(genomeName)

cp.into(Path(genomeName) , pwd/'fastqc_files)

}



def is_trimmed_paired_zip_?(file_name:String) : Boolean = {

  //  "ab c d e f".matches(".*f$")

  return file_name.matches(".*trimmed_paired.fastqc.zip$")

}


var all_trimmed_paired_files_zip = all_files.filter( (f) => is_trimmed_paired_zip_?(f.toString) ).map( (f) => f.toString)




for (genomeName <- all_trimmed_paired_files_zip){

println(genomeName)

cp.into(Path(genomeName) , pwd/'fastqc_files)

}
