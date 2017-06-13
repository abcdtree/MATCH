/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package match;
import java.util.*;
import java.lang.*;
import java.io.*;
import myscite.*;
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
    
    public SingleCellMatrix(int[][] matrix, String[] cells, String[] mutations){
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
    
    public SingleCellMatrix(){
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
    
    public DataMatrix getDataMatrix(MutationNameSpace mns){
        System.out.println(this.columnSize);
        System.out.println(this.rowSize);
        System.out.println(this.singleCellMatrix.length);
        int[][] matrix = new int[mns.size()][this.columnSize];
        ArrayList<String> muts = new ArrayList<String>(Arrays.asList(this.mutations));
        ArrayList<String> names = mns.getNames();
        for(int i=0; i < names.size(); i++){
            int j = muts.indexOf(names.get(i));
            if(j > -1)
            {
                for(int p = 0; p < this.columnSize; p++){
                    matrix[i][p] = this.singleCellMatrix[j][p];
                }
            }
            else{
                throw new Error("Mutation Name does not match");
            }
        }
        
        return new DataMatrix(matrix, mns);
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
    
    public boolean pathSort(BulkSequence bs){
        int[][] newMatrix = new int[this.rowSize][this.columnSize];
        ArrayList<String> muts = new ArrayList<String>(Arrays.asList(this.mutations));
        if(bs.size() != this.rowSize){
            return false;
        }
        else{
            ArrayList<String> mutNames = new ArrayList<String>();
            int counter = 0;
            for(Mutations s : bs.getMutations()){
                String sName = s.getName();
                mutNames.add(sName);
                int index = muts.indexOf(sName);
                if(index < 0){
                    return false;
                }
                else{
                    newMatrix[counter] = this.singleCellMatrix[index];
                }
                counter++;
            }
            this.singleCellMatrix = newMatrix;
            this.mutations = mutNames.toArray(new String[bs.size()]);
            return true;
        }
    }
    
    public int[] getColumn(int i){
        if(i < this.columnSize){
            int[] column = new int[this.rowSize];
            for(int j = 0; j < this.rowSize ; j++){
                column[j] = this.singleCellMatrix[j][i];
            }
            return column;
        }
        else{
            return new int[this.rowSize];
        }
    }
    
    public void commonRoot(){
        if(!this.checkRoot()){
            this.rowSize++;
            int[][] newMatrix = new int[this.rowSize][this.columnSize];
            String[] newMuts = new String[this.rowSize];
            newMuts[0] = "Root";
            for(int i = 1; i < this.rowSize; i++){
                newMuts[i] = this.mutations[i-1];
            }
            int[] rootRow = new int[this.columnSize];
            for(int j = 0; j < this.columnSize; j++){
                rootRow[j] = 1;
            }
            newMatrix[0] = rootRow;
            for(int i = 1; i < this.rowSize; i++){
                newMatrix[i] = this.singleCellMatrix[i-1];
            }
            this.mutations = newMuts;
            this.singleCellMatrix = newMatrix;
        }
    }
    
    private boolean checkRoot(){
        if(this.rowSize > 0){
            boolean flag = true;
            for(int i: this.singleCellMatrix[0]){
                if(i != 1 ){
                    flag = false;
                    break;                      
                }
            }
            return flag;
        }
        else{
            return false;
        }
    }
    
    public void commonGeneScoring(double thershold){
        HashMap<String, ArrayList<Double>> sMatrix = this.getScoreMatrix();
        //outputHash(sMatrix);
        int[] accessRecord = new int[this.columnSize];
        int[] maxArray = this.findMaxPoint(sMatrix, accessRecord, 2);
        double score = 0;
        if(maxArray.length > 1)
        {
            score = sMatrix.get(""+maxArray[0]+"_"+maxArray[1]).get(maxArray[2]);
        }
        
        //System.out.println(score);
        //System.out.println(maxArray[0] + "," + maxArray[1]+","+maxArray[2]);
        while (score > thershold){
            accessRecord[maxArray[0]] = accessRecord[maxArray[0]] + 1;
            accessRecord[maxArray[1]] = accessRecord[maxArray[1]] + 1;
            this.mergeAtPoint(maxArray[0], maxArray[1], maxArray[2]);
            sMatrix = this.getScoreMatrix();
            //outputHash(sMatrix);
            maxArray = this.findMaxPoint(sMatrix, accessRecord, 2);
            if(maxArray.length > 1)
            {
                score = sMatrix.get(""+maxArray[0]+"_"+maxArray[1]).get(maxArray[2]);
                //System.out.println(score);
                //System.out.println(maxArray[0] + "," + maxArray[1]+","+maxArray[2]);
            }
            else{
                score = 0;
            }
            
        }             
    }
    
    private static void outputHash(HashMap<String, ArrayList<Double>> sMatrix){
        StringBuilder sb = new StringBuilder();
        for(String key : sMatrix.keySet()){
            sb.append(key + ":");
            for(Double d: sMatrix.get(key)){
                sb.append("\t"+d);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
    
    private ArrayList<Double> commonCompare(int[] lineA, int[] lineB){
        int m_sum = 0;
        if(lineA.length != lineB.length){
            throw new Error("The compared paths are not equal line");
        }
        else{
            ArrayList<Double> returnList = new ArrayList<Double>();
            for(int i = 0; i < lineA.length; i++){
                if(lineA[i] == 1 && lineB[i] == 1){
                    if(m_sum == 0){
                        returnList.add(-1.0);
                    }
                    else{
                        returnList.add(1.0/m_sum);
                    }
                    m_sum = 0;
                }
                else{
                    if(lineA[i] != lineB[i]){
                        if(lineA[i] == 1){
                            if(lineB[i] == 0){
                                m_sum += 2;
                            }
                            else{
                                m_sum += 1;
                            }
                        }
                        else if(lineB[i] == 1){
                            if(lineA[i] == 0){
                                m_sum += 2;
                            }
                            else{
                                m_sum += 1;
                            }
                        }
                    }
                    returnList.add(0.0);
                    
                }
            }
            return returnList;
        }
    }
    
    private HashMap<String, ArrayList<Double>> getScoreMatrix(){
        HashMap<String, ArrayList<Double>> sMatrix = new HashMap<String, ArrayList<Double>>();
        for(int i = 0; i < this.columnSize; i++){
            for(int j = i+1; j < this.columnSize; j++){
                ArrayList<Double> scoreLine = new ArrayList<Double>();
                scoreLine = this.commonCompare(this.getColumn(i), this.getColumn(j));
                sMatrix.put(""+i+"_"+j, scoreLine);
            }
        }
        return sMatrix;
    }
    
    private void mergeAtPoint(int i, int j, int point){
        for(int k = 0; k < point; k++){
            if(this.singleCellMatrix[k][i] == 1){
                this.singleCellMatrix[k][j] = 1;
            }
            if(this.singleCellMatrix[k][j] == 1){
                this.singleCellMatrix[k][i] = 1;
            }
        }
    }
    
    private int[] findMaxPoint(HashMap<String, ArrayList<Double>> sMatrix){
        String max_key = "";
        double max_score = 0.0;
        int max_p = 0;
        for(String s: sMatrix.keySet()){
            ArrayList<Double> sLine = sMatrix.get(s);
            for(int i = 0; i < sLine.size(); i++){
                if(max_score < sLine.get(i)){
                    max_score = sLine.get(i);
                    max_p = i;
                    max_key = s;
                }
            }
        }
        if(max_key.length() == 0){
            return new int[1];
        }
        else{
            String[] keys = max_key.split("_");
            int[] returnArray = new int[3];
            returnArray[0] = Integer.parseInt(keys[0]);
            returnArray[1] = Integer.parseInt(keys[1]);
            returnArray[2] = max_p;
            return returnArray;
        }
    }
    
    private int[] findMaxPoint(HashMap<String, ArrayList<Double>> sMatrix, int[] accessRecord, int accessControl){
        int max_pairA = 0;
        int max_pairB = 0;
        double max_score = 0.0;
        int max_p = 0;
        for(String s: sMatrix.keySet()){
            ArrayList<Double> sLine = sMatrix.get(s);
            for(int i = 0; i < sLine.size(); i++){
                if(max_score < sLine.get(i)){
                    String[] tkeys = s.split("_");
                    int A = Integer.parseInt(tkeys[0]);
                    int B = Integer.parseInt(tkeys[1]);
                    if(accessRecord[A] < accessControl && accessRecord[B] < accessControl){
                        max_score = sLine.get(i);
                        max_p = i;
                        max_pairA = A;
                        max_pairB = B;
                    }                    
                }
            }
        }
        if(max_pairA + max_pairB == 0){
            return new int[1];
        }
        else{
            int[] returnArray = new int[3];
            returnArray[0] = max_pairA;
            returnArray[1] = max_pairB;
            returnArray[2] = max_p;
            return returnArray;
        }
    }
    

    
    public int getColumnSize(){
        return this.columnSize;
    }
    
    public int getRowSize(){
        return this.rowSize;
    }
    
    public String[] getMuts(){
        return this.mutations.clone();
    }
    
    public int getCell(int i, int j){
        return this.singleCellMatrix[i][j];
    }
    
}
