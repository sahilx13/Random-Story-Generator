
/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
public class CodonCount {
    private HashMap<String,Integer> codons;
    public CodonCount () {
        codons = new HashMap<String, Integer> ();
    }
    private void buildCodonMap (int start, String dna) {
        codons.clear();
        int i=start;
        while((dna.length() - i) >= 3) {
            String codon = dna.substring(i,i+3);
            if(!codons.containsKey(codon)) {
                codons.put(codon, 1);
            }
            else {
                codons.put(codon, codons.get(codon)+1);
            }
            i = i+3;
        }
    }
    private String getMostCommonCodon () {
        int high = 0;
        String common ="";
        for (String s : codons.keySet()) {
            if(codons.get(s) > high) {
                high = codons.get(s);
                common = s;
            }
        }
        return common;
    }
    private void printCodonCounts (int start, int end) {
        for (String s : codons.keySet()) {
            if(codons.get(s) >= start && codons.get(s) <= end) {
                System.out.println("Counts of codon between "+start+" and "+ end+ " inclusive are "+s +"\t" + codons.get(s));
            }
        }
    }
    public void tester () {
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        dna = dna.trim();
        for(int i=0; i<3; i++) {
            buildCodonMap(i, dna);
            System.out.println("Reading frame starting with "+i+" results in "+codons.size()+" unique codons");
            System.out.println("Most common codon : "+getMostCommonCodon() + "\t" + codons.get(getMostCommonCodon()));
            printCodonCounts(7, 7);
        }
    }
}
