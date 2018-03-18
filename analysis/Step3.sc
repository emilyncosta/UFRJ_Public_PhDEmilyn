#!/usr/bin/env amm

/*
Step3

In this step, we merge and rename the fastq files into G0xxxx_1.fastq and G0xxxx_2.fastq names.

*/

import ammonite.ops._
import ammonite.ops.ImplicitWd._



import scala.collection.mutable.ArrayBuffer

def is_fastqgz_?(file_name:String) : Boolean = {

  //  "ab c d e f".matches(".*f$")

  return file_name.matches(".*fastq.gz$")

}




def is_fastq_?(file_name:String) : Boolean = {

  //  "ab c d e f".matches(".*f$")

  return file_name.matches(".*fastq$")

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










// Usage
// find_unique_genomes_fastq(all_fastq_files)

def find_unique_genomes_fastq(all_fastq_files: Seq[String]): ArrayBuffer[String] = {

  var  all_genome_list = ArrayBuffer[String]()

  for (file_name <- all_fastq_files) {

    var genome_name = file_name.toString.split("/").last.split("_")(6)

    all_genome_list += genome_name


  }

  return all_genome_list.distinct

}





// Usage
// all_files_for_a_genome("G04868")

def all_files_for_a_genome(genome_name:String) :Seq[String] = {

  var genome_files = all_files.filter( (f) => file_name_has_genome_name_?(f.toString, genome_name) ).map((f) => f.toString)
    //println(genome_files)

  return genome_files
}

def r_code(genome_name:String) : String = {

  return genome_name.split("/").last.split("_")(13)

}


// Usage
// r_code_files_for_a_genome("G04868","R1")
def r_code_files_for_a_genome(genome_name:String, r_code:String) : Seq[String] = {

  //  ".*G04868.*R1.*"
  var regex = ".*" + genome_name + ".*" + r_code + ".*"

  return all_fastq_files.filter((f) => f.matches(regex)).map((f) => f.toString)

}




// Usage
// combine_all_r_code_files_for_genome("G04868","R1")
def combine_all_r_code_files_for_genome(genome_name:String, r_code:String) = {

  var genome_dir_name = genome_name + "_analysis"

//  mkdir! pwd/genome_dir_name

  var output_file_name = pwd.toString +  "/" + genome_dir_name +"/" + genome_name + "_" + r_code.toList(1) + ".fastq"

  var files_for_a_genome = all_files_for_a_genome(genome_name)



//  mkdir_genome_dir(genome_name)

  // println(files_for_a_genome)

  // println(r_code_files_for_a_genome)

  // construct the string to be executed by the shell
  var cmd_string = "cat "

  var list_of_files_to_be_concatenated = r_code_files_for_a_genome(genome_name, r_code)

  // combine the names of the files into a single string
  for (file <- list_of_files_to_be_concatenated ) {
    cmd_string += file + " "
  }

  cmd_string += " > " + output_file_name
  println(cmd_string + "\n\n")

   %("bash" , "-c", cmd_string)

}




// combine_all_r_code_files_for_genome("G04868","R1")



 // Show time baby!

// Calling the << combine_all_r_code_files_for_genome >> functions per genome for both << R >> files
 // The << println >> is used for a well informed user experience while running the script
 def merge_and_rename(genome_name:String ) = {

 var genome_dir_name = genome_name + "_analysis"
 mkdir! pwd/genome_dir_name

 println("\n\n ~~~~~~~~~~~~~~~~~~~~~ ")
 println("\nworking on the " + genome_name + " files\n\n")
 println("\n>>>>       R1      <<<<\n\n")
 combine_all_r_code_files_for_genome(genome_name,"R1")
 println("\n\n")
 println("\n>>>>       R2      <<<<\n\n")
 combine_all_r_code_files_for_genome(genome_name,"R2")

 println("\n\n\n@@@@@@@@@@@@@@@@@@@@@@@@")

 println("\n\nAll Done!")

 }




decompress_all(all_fastqgz_files)




var all_files_now = ls! pwd

var all_fastq_files = all_files_now.filter( (f) => is_fastq_?(f.toString) ).map( (f) => f.toString)

var unique_genome_list = find_unique_genomes_fastq(all_fastq_files).toList

def merge_and_rename_all(unique_genome_list:Seq[String]) = {

for (f <- unique_genome_list) {


merge_and_rename(f)

}
}

merge_and_rename_all(unique_genome_list)
