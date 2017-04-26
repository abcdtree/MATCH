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
        /*
        SingleCellMatrix scm = SingleCellMatrix.readFromCSV("./testData.csv");
        System.out.println(scm);
        
        BulkSequence bs = BulkSequence.readFromCSV("./test/Bulk.csv");
        System.out.println(bs);
        
        scm.commonGeneScoring(0.2);
        scm.commonRoot();;
        
        
        System.out.println(scm);
        PartialTree pt = PartialTree.makeATree(scm);
        pt.outputCSV("./testOutput.csv");
        
        
        pt.reduceRepeat(0);
        pt.outputCSV("./testOutput2.csv");*/
        //String path = "./TestData/ddCloneSimulatedMatrix";
        String path = "./TestData/MatrixTree100";
        
        File[] files = new File(path).listFiles();
        for (File file: files){
            DataHandle dh = new DataHandle(path + "/" + file.getName());
        
            SingleCellMatrix scm = dh.getSCM();
            //System.out.println(scm);
            BulkSequence bs = dh.getBulk();
            //System.out.println(bs);

            scm.pathSort(bs);
            System.out.println(scm);
            scm.commonGeneScoring(0.8);
            scm.commonRoot();

            //System.out.println(scm);

            PartialTree pt = PartialTree.makeATree(scm);
            pt.reduceRepeat(0);
            pt.outputCSV(path + "Output1/" + file.getName());
        }
        
        
    }
    
}
