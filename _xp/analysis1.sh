
# gzip these files

gzip -dc G04868_R1.fastq.gz > G04868_R1.fastq


gzip -dc G04868_R2.fastq.gz > G04868_R2.fastq



# trimmomatic <<<<<


java -jar /opt/Trimmomatic-0.36/trimmomatic-0.36.jar PE -phred33 G04868_1.fastq G04868_2.fastq G04868_1_trimmed_paired.fastq G04868_1_trimmed_unpaired.fastq G04868_2_trimmed_paired.fastq G04868_2_trimmed_unpaired.fastq LEADING:3 TRAILING:3 SLIDINGWINDOW:4:20 MINLEN:36




# bwa_index_reference_genome <<<<<


bwa index NC000962_3.fasta



# map_and_generate_sam_file <<<<<


bwa mem -R "@RG\tID:G04868\tSM:G04868\tPL:Illumina" -M NC000962_3.fasta G04868_1_trimmed_paired.fastq G04868_2_trimmed_paired.fastq > G04868.sam


# samtools_faidx_reference_genome <<<<<


samtools faidx NC000962_3.fasta




# convert_sam_file_to_bam_file <<<<<


samtools view -bt NC000962_3.fasta.fai G04868.sam > G04868.bam




# sort_bam_file <<<<<


samtools sort G04868.bam -o G04868.sorted.bam




# samtools_index_sorted_bam <<<<<


samtools index G04868.sorted.bam




# mapping_statistics <<<<<


samtools flagstat G04868.sorted.bam > G04868_stats.txt




# samtools_mpileup <<<<<


samtools mpileup -Q 23 -d 2000 -C 50 -ugf NC000962_3.fasta G04868.sorted.bam | bcftools call -O v -vm -o G04868.raw.vcf




# vcfutils_filter<<<<<


vcfutils.pl varFilter -d 10 -D 2000 G04868.raw.vcf > G04868.filt.vcf




# bgzip_filt_file <<<<<


bgzip -c G04868.filt.vcf > G04868.filt.vcf.gz




# run_tabix <<<<<


tabix -p vcf G04868.filt.vcf.gz




# snpEff <<<<<


java -Xmx4g -jar /opt/snpEff/snpEff.jar -no-downstream -no-upstream -v -c /opt/snpEff/snpEff.config NC000962_3 G04868.filt.vcf > G04868.ann.vcf.gz


# velveth_assembly <<<<<


velveth G04868_41 41 -fastq -shortPaired  G04868_1_trimmed_paired.fastq G04868_1_trimmed_unpaired.fastq -fastq -short G04868_2_trimmed_paired.fastq G04868_2_trimmed_unpaired.fastq



# velvetg_produce_graph <<<<<


velvetg G04868_41 -exp_cov auto -cov_cutoff auto




# assemblathon_stats <<<<<


assemblathon_stats.pl ./G04868_41/contigs.fa




# velveth_assembly <<<<<


velveth G04868_49 49 -fastq -shortPaired  G04868_1_trimmed_paired.fastq G04868_1_trimmed_unpaired.fastq -fastq -short G04868_2_trimmed_paired.fastq G04868_2_trimmed_unpaired.fastq




# velvetg_produce_graph <<<<<


velvetg G04868_49 -exp_cov auto -cov_cutoff auto




# assemblathon_stats <<<<<


assemblathon_stats.pl ./G04868_49/contigs.fa




# velveth_assembly <<<<<


velveth G04868_55 55 -fastq -shortPaired  G04868_1_trimmed_paired.fastq G04868_1_trimmed_unpaired.fastq -fastq -short G04868_2_trimmed_paired.fastq G04868_2_trimmed_unpaired.fastq

# velvetg_produce_graph <<<<<


velvetg G04868_55 -exp_cov auto -cov_cutoff auto



# assemblathon_stats <<<<<


assemblathon_stats.pl ./G04868_55/contigs.fa


assemblathon_stats.pl ./G04868_41/contigs.fa


assemblathon_stats.pl ./G04868_49/contigs.fa


assemblathon_stats.pl ./G04868_55/contigs.fa


 
