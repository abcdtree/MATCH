/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package match;
import java.util.*;
/**
 *
 * @author Jianshu
 */
public class Mutations implements Comparable<Mutations>{
    String mutation;
    String mutation_sht;
    String location;
    String chrome;
    double ccf;
    double tReads;
    double mReads;
    double copyNumber;
    String snp;
    String heterozygosity;
    
    public Mutations(){
        this.mutation = "a";
        this.mutation_sht = "a";
        this.location = "1";
        this.chrome = "1";
        this.ccf = 0;
        this.tReads = 0.0;
        this.mReads = 0.0;
        this.copyNumber = 2.0;
        this.snp = "";
        this.heterozygosity = "";
    }
    
    public Mutations(String mut, String mut_sht, String loc, String chr, double ccf_v, double tR, double mR, double cN, String sNP, String het){
        this.mutation = mut;
        this.mutation_sht = mut_sht;
        this.location = loc;
        this.chrome = chr;
        this.ccf = ccf_v;
        this.tReads = tR;
        this.mReads = mR;
        this.copyNumber = cN;
        this.snp = sNP;
        this.heterozygosity = het;
    }
    
    public Mutations(String mut_sht, double ccf_v){
        this.mutation = mut_sht;
        this.mutation_sht = mut_sht;
        this.location = "1";
        this.chrome = "1";
        this.ccf = ccf_v;
        Random rand = new Random();
        this.tReads = (float)(1.0 + rand.nextInt(2000));
        this.mReads = (float)((int) (this.tReads * ccf_v));
        this.copyNumber = 2.0;
        this.snp = "";
        this.heterozygosity = "";
    }
    
    public Mutations(String mut_sht, double tR, double mR, double cN){
        this.mutation = mut_sht;
        this.mutation_sht = mut_sht;
        this.location = "1";
        this.chrome = "1";
        this.ccf = mR / tR;
        this.tReads = tR;
        this.mReads = mR;
        this.copyNumber = cN;
        this.snp = "";
        this.heterozygosity = "";
    }
    
    public Mutations(String mut, String mut_sht, double tR, double mR, double cN){
        this.mutation = mut;
        this.mutation_sht = mut_sht;
        this.location = "1";
        this.chrome = "1";
        this.ccf = mR / tR;
        this.tReads = tR;
        this.mReads = mR;
        this.copyNumber = cN;
        this.snp = "";
        this.heterozygosity = "";
    }
    
    double getCCF(){
        return this.ccf;
    }
    
    String getName(){
        return this.mutation_sht;
    }
    
    double getTotalReads(){
        return this.tReads;
    }
    
    double getMutReads(){
        return this.mReads;
    }
    
    double getCopyNumber(){
        return this.copyNumber;
    }
    
    void updateCCF(double ccf_v){
        this.ccf = ccf_v;
    }
    

    @Override
    public int compareTo(Mutations t) {
        if(this.ccf == t.getCCF()){
            return 0;
        }
        else{
            return this.ccf > t.getCCF() ? -1:1;
        }
    }
    
    public String toString(){
        return this.mutation_sht + "\t" + this.ccf;
    }
}
