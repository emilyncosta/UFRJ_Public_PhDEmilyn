#!/usr/bin/env amm

/*
Step1 

In this step we just separate out the good genomes from the bad genomes


*/


import ammonite.ops._
import ammonite.ops.ImplicitWd._


// Move all the genomes as per the division of good and bad genomes

import scala.collection.mutable.ArrayBuffer



var all_files = ls! pwd


def file_name_has_genome_name_?(file_name:String, genome_name:String) : Boolean = {


  var genome_reg_exp = "_" + genome_name + "_"

  return file_name.contains(genome_reg_exp)

}

def is_fastqgz_?(file_name:String) : Boolean = {

  //  "ab c d e f".matches(".*f$")

  return file_name.matches(".*fastq.gz$")

}




def all_files_for_a_genome(genome_name:String) :Seq[String] = {

  var genome_files = all_files.filter( (f) => file_name_has_genome_name_?(f.toString, genome_name) ).map((f) => f.toString)
    //println(genome_files)

  return genome_files
}


var all_fastqgz_files = all_files.filter( (f) => is_fastqgz_?(f.toString) ).map( (f) => f.toString)





// Usage
// find_unique_genomes_fastq(all_fastq_files)

def find_unique_genomes_fastqgz(all_fastqgz_files: Seq[String]): ArrayBuffer[String] = {

  var  all_genome_list = ArrayBuffer[String]()

  for (file_name <- all_fastqgz_files) {

    var genome_name = file_name.toString.split("/").last.split("_")(6)

    all_genome_list += genome_name


  }

  return all_genome_list.distinct

}


var list_of_all_genomes = find_unique_genomes_fastqgz(all_fastqgz_files)


// Calling the << combine_all_r_code_files_for_genome >> functions per genome for both << R >> files
 // The << println >> is used for a well informed user experience while running the script
def move_a_genome(genomeName:String, kind:String ) = {

  var target_folder = pwd + "/" + kind

  var cmd_string = "mv " + genomeName + " " + target_folder

  println(cmd_string + "\n\n")

  %%("bash", "-c", cmd_string)
}



var bad_genomes = Array(

  "G04870",
  "G04874",
  "G04878",
  "G04879",
  "G04880",
  "G04882",
  "G04885",
  "G04886",
  "G04889",
  "G04890",
  "G04893",
  "G04894",
  "G04895",
  "G04896",
  "G04897",
  "G04958"
)


// TODO: mkdir good && bad


mkdir! pwd/'good
mkdir! pwd/'bad

for( g <- list_of_all_genomes ){

if(bad_genomes.contains(g)) {

  var files_per_genome = all_files_for_a_genome(g)

  for( f <- files_per_genome) {

    move_a_genome(f, "bad")
  }


} else {

  var files_per_genome = all_files_for_a_genome(g)

  for( f <- files_per_genome) {

    move_a_genome(f, "good")
  }

}

}


// TODO: now we move inside the good/ bad folder and start the unzipping and further analysis
