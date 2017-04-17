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
public class MATCH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        SingleCellMatrix scm = SingleCellMatrix.readFromCSV("./testData.csv");
        System.out.println(scm);
        /*
        BulkSequence bs = BulkSequence.readFromCSV("./test/Bulk.csv");
        System.out.println(bs);*/
        
        scm.commonGeneScoring(0.2);
        scm.commonRoot();;
        
        
        System.out.println(scm);
        PartialTree pt = PartialTree.makeATree(scm);
        pt.outputCSV("./testOutput.csv");
    }
    
}
