

# Highest quality k_mer : 49


# abacas_align_contigs <<<<<


cd G04868_49 &&  cp ../NC000962_3.fasta ./ && abacas.pl -r ../NC000962_3.fasta -q contigs.fa -p promer -b -d -a 


# prokka_annotation <<<<<


cd ./G04868_49 && prokka --outdir ./G04868_prokka --prefix G04868 contigs.fa_NC000962_3.fasta.fasta



# gzip_compression <<<<<


gzip -c G04868_1.fastq > G04868_1.fastq.gz



# gzip_compression <<<<<


gzip -c G04868_2.fastq > G04868_2.fastq.gz



# snippy_command <<<<<


snippy --cpus 4 --outdir G04868 --ref ./NC000962_3.gbk --R1 ./G04868_1.fastq.gz --R2 ./G04868_2.fastq.gz



# SNPtable <<<<<


SNPtable_filter_Mtb.R core.tab



# HammingFasta <<<<<


HammingFasta.R coreSNP_alignment_filtered.fas


# ALL DONE 

