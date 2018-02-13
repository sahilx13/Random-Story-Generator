import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> categories;
    private ArrayList<String> usedList;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibMap(){
        myMap = new HashMap<>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
        usedList = new ArrayList<String>();
        categories = new ArrayList<>();
    }
    
    public GladLibMap(String source){
        myMap = new HashMap<>();
        initializeFromSource(source);
        myRandom = new Random();
        categories = new ArrayList<>();
    }
    
    private void initializeFromSource(String source) { 
        myMap.put("adjective",readIt(source+"/adjective.txt"));
        myMap.put("noun", readIt(source+"/noun.txt"));
        myMap.put("color", readIt(source+"/color.txt"));
        myMap.put("country", readIt(source+"/country.txt"));
        myMap.put("name", readIt(source+"/name.txt"));      
        myMap.put("animal", readIt(source+"/animal.txt"));
        myMap.put("timeframe", readIt(source+"/timeframe.txt")); 
        myMap.put("verb", readIt(source+"/verb.txt"));  
        myMap.put("fruit", readIt(source+"/fruit.txt"));    
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        if(!categories.contains(label)) {
            categories.add(label);
        }
        return randomFrom(myMap.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub ="";
        do {
            sub = getSubstitute(w.substring(first+1,last));
        }while(usedList.contains(sub));
        usedList.add(sub);
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    private String totalWordsInMap() {
        String words = "";
        for(String s : myMap.keySet()) {
            ArrayList<String> a = myMap.get(s);
            words = words+ s + ":\t";
            for(String s1 : a) {
                words = words + s1 + "\t";
            }
            words += "\n";
        }
        return words;
    }
    private int totalWordsConsidered () {
        int count = 0;
        for(String s : categories) {
           ArrayList<String> a = myMap.get(s);
           count = count + a.size();
        }
        return count;
    }
    public void makeStory(){
        usedList.clear();
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println();
        System.out.println("Replaced words : "+usedList.size());
        System.out.println("Total words in map : "+totalWordsInMap());
        System.out.println("Total words considered : "+totalWordsConsidered());
    }
    


}
