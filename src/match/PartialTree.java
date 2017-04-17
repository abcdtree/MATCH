/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package match;
import java.util.*;
import java.io.*;
import java.lang.*;
/**
 *
 * @author Jianshu
 */
public class PartialTree {
    TreeNode root;
    private PartialTree(){
        this.root = null;
    }
    
    private PartialTree(TreeNode root){
        this.root = root;
    }
    
    private void addAPath(ArrayList<String> path){
        TreeNode current = null;
        for(int i = 0; i < path.size(); i++){
            if(i == 0){
                if(this.root == null){
                    this.root = new TreeNode(path.get(i));
                }
                else{
                    if(this.root.getName() != path.get(i)){
                        throw new Error("Common the Root first");
                    }
                }
                current = this.root;
            }
            else{
                ArrayList<TreeNode> children = current.getChildren();
                boolean flag = false;
                for(TreeNode node: children){
                    if(node.getName() == path.get(i)){
                        flag = true;
                        current = node;
                        break;
                    }
                }
                if(!flag){
                    TreeNode temp = new TreeNode(path.get(i));
                    current.addChild(temp);
                    current = temp;
                }
            }
            
        }
    }
    
    public void setRoot(TreeNode root){
        this.root = root;
    }
    
    public static PartialTree makeATree(SingleCellMatrix scm){
        String[] muts = scm.getMuts();
        PartialTree pt = new PartialTree();
        if(muts.length > 0){
            pt.setRoot(new TreeNode(muts[0]));
        }
        else{
            return pt;
        }
        for(int i = 0; i < scm.getColumnSize(); i++){
            ArrayList<String> path = new ArrayList<String>();
            for(int j = 0; j < scm.getRowSize(); j++){
                if(scm.getCell(j, i) == 1){
                    path.add(muts[j]);
                }
            }
            pt.addAPath(path);
            
        }
        return pt;
    }
    
    public void outputCSV(String filePath){
        ArrayList<String> rows = new ArrayList<String>();
        rows.add("id,xxx,gene\n");
        recBuildCSVRow(this.root, "1", rows);
        try(FileWriter fileWriter = new FileWriter(filePath)){
            for(String row: rows){
                fileWriter.append(row);
            }
            fileWriter.flush();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void recBuildCSVRow(TreeNode current, String id, ArrayList<String> rows){
        rows.add(id +",XXX,"+current.getName()+"\n");
        int count = 0;
        for(TreeNode child: current.getChildren()){
            recBuildCSVRow(child, id + Integer.toString(count), rows);
            count ++;
        }
    }
}
