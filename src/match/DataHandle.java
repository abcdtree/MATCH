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
public class DataHandle {
    String datafile;
    public DataHandle(String csvfile){
        this.datafile = csvfile;
    }
    
    public SingleCellMatrix getSCM(){
        try(BufferedReader br = new BufferedReader(new FileReader(this.datafile))){
            String line = br.readLine();
            String[] lines = line.split(",");
            String[] muts = new String[lines.length-1];
            for(int i = 1; i < lines.length; i++){
                muts[i-1] = lines[i];
            }
            
            ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
            ArrayList<String> cells = new ArrayList<String>();
            while((line = br.readLine()) != null){
                lines = line.split(",");
                cells.add(lines[0]);
                ArrayList<Integer> rows = new ArrayList<Integer>();
                for(int i = 1; i < lines.length; i++){
                    rows.add(Integer.parseInt(lines[i]));
                }
                matrix.add(rows);
            }
            
            String[] mCells = cells.toArray(new String[cells.size()]);
            int[][] dmatrix = new int[muts.length][mCells.length];
            for(int i = 0; i < muts.length; i++){
                for(int j = 0; j < mCells.length; j++){
                    dmatrix[i][j] = matrix.get(j).get(i);
                }
            }
            
            return new SingleCellMatrix(dmatrix, mCells, muts);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return new SingleCellMatrix();
    }
    
    public BulkSequence getBulk(int method){
        BulkSequence bs = new BulkSequence();
        try(BufferedReader br = new BufferedReader(new FileReader(this.datafile))){
            
            String line = br.readLine();
            String[] lines = line.split(",");
            for(int i = 1; i < lines.length; i++){
                String[] values = new String[2];
                if(method == 0)
                {
                    values = lines[i].split("_");
                }
                else if(method == 1){
                    values = lines[i].split(" ");
                }
                Mutations mt = new Mutations(lines[i], Double.parseDouble(values[1]));
                bs.add(mt);
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
    
    
}
