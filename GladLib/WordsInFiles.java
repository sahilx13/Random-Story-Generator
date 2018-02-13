
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import java.io.*;
public class WordsInFiles {
    private HashMap<String , ArrayList<String>> wordList;
    public WordsInFiles() {
        wordList = new HashMap<String, ArrayList<String>>();
    }
    private void addWordsFromFile(File f) {
       FileResource fr = new FileResource(f);
       for(String s : fr.words()) {
           if(wordList.keySet().contains(s)) {
               ArrayList<String> a = wordList.get(s);
               if(!a.contains(f.getName())) {
                   a.add(f.getName());
                   wordList.put(s, a);
               } 
           }
           else {
               ArrayList<String> a = new ArrayList<String>();
               a.add(f.getName());
               wordList.put(s, a);
           }
       }
    }
    private void buildWordFileMap () {
        wordList.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        } 
    }
    private int maxNumber() {
        int max = -1;
        for(String s : wordList.keySet()) {
            ArrayList<String> a = wordList.get(s);
            if(a.size() > max) {
                max = a.size();
            }
        }
        return max;
    }
    private ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> words = new ArrayList<String>();
        for(String s : wordList.keySet()) {
            ArrayList<String> a = wordList.get(s);
            if(a.size() == number ) {
                words.add(s);
            }
        }
        return words;
    }
    private void printFilesIn(String word) {
        ArrayList<String> files = wordList.get(word);
        for(String s : files) {
            System.out.print(s+"\t");
        }
    }
    public void tester() {
        buildWordFileMap();
        System.out.println("Maximum number of files a word is in is "+maxNumber());
        System.out.println("Files containing words with maximum number of files : ");
        ArrayList<String> words = wordsInNumFiles(maxNumber());
        for(String s : words) {
            System.out.print(s + "\t");
        }
        System.out.println();
        System.out.println("NUmber of words occuring in all seven files : "+words.size());
        words = wordsInNumFiles(4);
        System.out.println("Words that occur in each four files : "+words.size());
        System.out.println(wordList.get("laid"));
        System.out.println(wordList.get("tree"));
    }
}
