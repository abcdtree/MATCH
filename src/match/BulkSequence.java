/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package match;
import java.util.*;
import java.io.*;
/**
 *
 * @author Jianshu
 */
public class BulkSequence {
    ArrayList<Mutations> myBulk;
    
    public BulkSequence(){
        this.myBulk = new ArrayList<Mutations>();
    }
    
    public int size(){
        return myBulk.size();
    }
    
    public void add(Mutations mt){
        this.myBulk.add(mt);
    }
    
    public void sort(){
        Collections.sort(myBulk);
    }
    
    public ArrayList<Mutations> getMutations(){
        return new ArrayList<Mutations>(this.myBulk);
    }
    
    public ArrayList<String> getMutNames(){
        ArrayList<String> names = new ArrayList<String>();
        for(Mutations m: this.myBulk){
            names.add(m.getName());
        }
        return names;
    }
    
    public static BulkSequence readFromCSV(String csvFile){
        BulkSequence bs = new BulkSequence();
        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            String line = br.readLine();
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                Mutations ms = new Mutations(lines[2], lines[3]
                        ,Double.parseDouble(lines[5]), Double.parseDouble(lines[6])
                        ,Double.parseDouble(lines[7]));
                bs.add(ms);
            }
            bs.sort();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return bs;
    }
    
    public String toString(){
        String output = "";
        for(Mutations m: this.myBulk){
            output += m.toString() + "\n";
        }
        return output.trim();
    }
}
