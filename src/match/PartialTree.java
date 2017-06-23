/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package match;
import java.util.*;
import java.io.*;
import java.lang.*;
import myscite.AncestorMatrix;
import myscite.MutationNameSpace;
/**
 *
 * @author Jianshu
 */
public class PartialTree {
    TreeNode root;
    int size;
    ArrayList<String> muts;
    private PartialTree(){
        this.root = null;
        this.size = 0;
        this.muts = new ArrayList<String>();
    }
    
    private PartialTree(TreeNode root){
        this.root = root;
        this.size = 1;
        this.muts = new ArrayList<String>();
        muts.add(this.root.getName());
    }
    
    public int size(){
        return this.size;
    }
    
    public AncestorMatrix getAncestorMatrix(){
        int mSize = this.size();
        ArrayList<String> nameSpace = this.muts;
        AncestorMatrix mMatrix = new AncestorMatrix(mSize);
        recBuildMatrix(this.root, mMatrix, nameSpace, mSize);
        //System.out.println(muts.size());
        //System.out.println(mMatrix.size());
        //System.out.println(mSize);
        mMatrix.setNameSpace(new MutationNameSpace(nameSpace));
        
        return mMatrix;
    }
    
    private void recBuildMatrix(TreeNode current, AncestorMatrix mMatrix, ArrayList<String> nameSpace, int mSize){
        int[] column = new int[mSize];
        ArrayList<String> ancestors = current.getAncestors();
        for(int i = 0; i < mSize; i ++){
            if(ancestors.contains(nameSpace.get(i))){
                column[i] = 1;
            }
        }
        int j = nameSpace.indexOf(current.getName());
        mMatrix.setColumn(column, j);
        ArrayList<TreeNode> children = current.getChildren();
        for(TreeNode child: children){
            recBuildMatrix(child, mMatrix, nameSpace, mSize);
        }
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
                    this.muts.add(temp.getName());
                    this.size++;
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
        //System.out.println("This is the root now: " + muts[0]);
        PartialTree pt = new PartialTree();
        if(muts.length > 0){
            pt.setRoot(new TreeNode(muts[0]));
            pt.muts.add(muts[0]);
            pt.size++;
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
    
    public void reduceRepeat(int method){
        //method id == i
        //method 0
        //leave the high level one, delete the lowers
        if(method == 0){
            ArrayList<String> muts = new ArrayList<String>();
            ArrayList<TreeNode> level = new ArrayList<TreeNode>();
            
            level.add(this.root);
            while(level.size() > 0){
                ArrayList<TreeNode> temp = new ArrayList<TreeNode>();
                for(TreeNode tn: level){
                    this.checkNode(tn, muts, temp);
                }
                level = temp;
            }
        }
        //method 1
        //random pick one between the repeat nodes
        else if(method == 1){
            this.checkRepeat();
        }
    }
    
    private void checkRepeat(){
        boolean noRepeat = false;
        ArrayList<TreeNode> allNode = new ArrayList<TreeNode>();
        this.reAddNode(this.root, allNode);
        while(true){
            if(!this.findRepeat(allNode)){
                break;
            }
        }
        ArrayList<String> names = new ArrayList<String>();
        for(TreeNode node: allNode){
            names.add(node.getName());
        }
        this.muts = names;
    }
    
    
    
    private void reAddNode(TreeNode node, ArrayList<TreeNode> nodes){
        nodes.add(node);
        for(TreeNode child: node.getChildren()){
            this.reAddNode(child, nodes);
        }
    }
    
    private void removeNode(TreeNode node){
        if(node == this.root){
            return;
        }
        else{
            ArrayList<TreeNode> childrens = node.getChildren();
            TreeNode parent = node.getParent();
            for(TreeNode child: childrens){
                parent.addChild(child);
            }
            parent.removeChild(node);
            this.size--;
        }
    }
    
    private boolean findRepeat(ArrayList<TreeNode> nodes){
        boolean flag = false;
        for(int i = 0; i < nodes.size(); i++){
            for(int j = i+1; j < nodes.size(); j++){
                if(nodes.get(i).getName() == nodes.get(j).getName()){
                    double k = (new Random()).nextDouble();
                    if(k < 0.5 && i != 0){
                        this.removeNode(nodes.get(i));
                        nodes.remove(i);                       
                    }
                    else{
                        this.removeNode(nodes.get(j));
                        nodes.remove(j);
                    }
                    flag = true;
                    break;
                }
                
            }
            if(flag){
                break;
            }
        }
        return flag;
    }
    
    private void checkNode(TreeNode tn, ArrayList<String> muts, ArrayList<TreeNode> level){
        if(tn == null){
            return;
        }
        else{
            if(muts.contains(tn.getName())){
                
                TreeNode parent = tn.getParent();
                ArrayList<TreeNode> children = parent.getChildren();
                for(int i = 0; i < children.size(); i++){
                    if(children.get(i).getName() == tn.getName()){
                        parent.removeChild(i);
                        this.size--;
                        int k = this.muts.indexOf(tn.getName());
                        this.muts.remove(k);
                        break;
                    }
                }
                for(TreeNode node: tn.getChildren()){
                    parent.addChild(node);
                    this.checkNode(node, muts, level);
                }
                
            }
            else{
                muts.add(tn.getName());
                for(TreeNode n: tn.getChildren()){
                    if(n != null){
                        level.add(n);
                    }
                }
            }
        }
    }
}
