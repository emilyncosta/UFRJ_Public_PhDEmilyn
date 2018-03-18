#!/usr/bin/env amm

/*
Step2

In this step, decompress the fastq.gz files and move all the files belonging to a genome to it's own directory


*/

import ammonite.ops._
import ammonite.ops.ImplicitWd._



import scala.collection.mutable.ArrayBuffer

def is_fastqgz_?(file_name:String) : Boolean = {

  //  "ab c d e f".matches(".*f$")

  return file_name.matches(".*fastq.gz$")

}


def find_unique_genomes_fastq(all_fastqgz_files: Seq[String]): ArrayBuffer[String] = {

  var  all_genome_list = ArrayBuffer[String]()

  for (file_name <- all_fastqgz_files) {

    var genome_name = file_name.toString.split("/").last.split("_")(6)

    all_genome_list += genome_name


  }

  return all_genome_list.distinct

}




var all_files = ls! pwd


var all_fastqgz_files = all_files.filter( (f) => is_fastqgz_?(f.toString) ).map( (f) => f.toString)




//TODO: Unzip all the fastqgz files in the folder

// generate_fastq_names_from_fastqgz( "PT000033_1.fastq.gz")

def generate_fastq_names_from_fastqgz(fastqgz_name:String) : String = {
  var name_array = fastqgz_name.split("\\.")
  return { name_array(0) + ".fastq"}
}





// generate_fastq_names_from_fastqgz(all_fastq_files(0).toString)
def gzip_decompression(genome_name:String) = {

  var fastqgz_name = genome_name

  var fastq_name = generate_fastq_names_from_fastqgz(fastqgz_name)

  var cmd_string =  "gzip -dc " + fastqgz_name + " > " + fastq_name

println(cmd_string)

  %("bash", "-c", cmd_string)
  
  println("\n\n")
}




def decompress_all(all_fastqgz_files:Seq[String]) = {


for(f <- all_fastqgz_files){

//println(f)
gzip_decompression(f)

}

}


def file_name_has_genome_name_?(file_name:String, genome_name:String) : Boolean = {


  var genome_reg_exp = "_" + genome_name + "_"

  return file_name.contains(genome_reg_exp)

}





def all_files_for_a_genome(genome_name:String) :Seq[String] = {

  var genome_files = all_files.filter( (f) => file_name_has_genome_name_?(f.toString, genome_name) ).map((f) => f.toString)
    //println(genome_files)

  return genome_files
}



def bundle_all_files_for_a_genome(genome_name:String) = {


  var files_for_a_genome = all_files_for_a_genome(genome_name)

    mkdir! pwd/genome_name

  for (f <- files_for_a_genome) {

    var cmd_string = "mv " + f + " " + "./" + genome_name + "/"
    println(cmd_string + "\n\n")

  %("bash", "-c", cmd_string)

  }

}



var unique_genome_list = find_unique_genomes_fastq(all_fastqgz_files).toList



for(f <- unique_genome_list) {
bundle_all_files_for_a_genome(f)
}