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
public class TreeNode {
    private TreeNode parent;
    private ArrayList<TreeNode> children;
    private String name;
    
    public TreeNode(String mName){
        this.name = mName;
        this.parent = null;
        children = new ArrayList<TreeNode>();
    }
    
    public void addChild(TreeNode child){
        this.children.add(child);
        child.setParent(this);
    }
    
    public void setParent(TreeNode mParent){
        this.parent = mParent;
    }
    
    public void changeName(String newName){
        this.name = newName;
    }
    
    public ArrayList<TreeNode> getChildren(){
        return new ArrayList<TreeNode>(children);
    }
    
    public String getName(){
        return this.name;
    }
    
    public TreeNode getParent(){
        return this.parent;
    }
    
    public ArrayList<String> getAncestors(){
        ArrayList<String> ancestorNames = new ArrayList<String>();
        ancestorNames.add(this.name);
        TreeNode current = this.parent;
        while(current != null){
            ancestorNames.add(current.name);
            current = current.parent;
        }
        return ancestorNames;
    }
    
    public String toString(){
        return this.name;
    }
}
