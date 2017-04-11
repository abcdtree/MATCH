/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package match;
import java.util.*;
import java.lang.*;
import java.io.*;
/**
 *
 * @author Jianshu
 */
public class SingleCellMatrix {
    int[][] singleCellMatrix;
    int columnSize;
    int rowSize;
    String[] mutations;
    String[] cells;
    
    private SingleCellMatrix(int[][] matrix, String[] cells, String[] mutations){
        this.singleCellMatrix = matrix;
        this.rowSize = matrix.length;
        if(this.rowSize > 0){
            this.columnSize = matrix[0].length;
        }
        else{
            this.columnSize = 0;
        }
        if(cells.length == this.columnSize){
            this.cells = cells;
        }
        else{
            throw new Error("The cell number is not Match!");
        }
        if(mutations.length == this.rowSize){
            this.mutations = mutations;
        }
        else{
            throw new Error("The cell number is not Match!");
        }
    }
    
    private SingleCellMatrix(){
        this.singleCellMatrix = new int[4][4];
        this.columnSize = 4;
        this.rowSize = 4;
        this.mutations = new String[4];
        this.cells = new String[4];
        
    }
    
    public static SingleCellMatrix readFromCSV(String csvFile){
        String[] filename = csvFile.split("\\.");
        if(!filename[filename.length - 1].equals("csv")){
            throw new Error("Only CSV File Please");
        }
        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            String line = "";
            List<String> mutations = new ArrayList<String>();
            ArrayList<ArrayList<Integer>> tMatrix = new ArrayList<ArrayList<Integer>>();
            // read cells
            line = br.readLine();
            String[] mCells = line.split(",");
            String[] cells = new String[mCells.length-1];
            for(int i = 1; i < mCells.length; i++){
                cells[i-1] = mCells[i];
            }
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                mutations.add(lines[0]);
                ArrayList<Integer> mLine = new ArrayList<Integer>();
                for(int i = 1; i < lines.length; i++){
                    mLine.add(Integer.parseInt(lines[i]));
                }
                tMatrix.add(mLine);
            }
            String[] tMutations = mutations.toArray(new String[mutations.size()]);
            
            int[][] matrix = new int[mutations.size()][cells.length];
            for(int i = 0; i < mutations.size(); i++){
                for(int j = 0; j < cells.length; j++){
                    matrix[i][j] = tMatrix.get(i).get(j);
                }
            }
            return new SingleCellMatrix(matrix, cells, tMutations);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return new SingleCellMatrix();
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Mutations");
        for(String s: this.cells){
            sb.append("\t" + s);
        }
        sb.append("\n");
        for(int i = 0; i < this.rowSize; i++){
            sb.append(this.mutations[i]);
            for(int j = 0; j < this.columnSize; j++){
                sb.append("\t" + this.singleCellMatrix[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
