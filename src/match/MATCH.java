/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package match;
import java.util.*;
import java.lang.*;
import java.io.*;
import myscite.ResultQualify;
import myscite.SciteTree;
import myscite.AncestorMatrix;

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
        
        BulkSequence bs = BulkSequence.readFromCSV("./test/Bulk.csv");
        System.out.println(bs);
        
        scm.commonGeneScoring(0.2);
        scm.commonRoot();;
        
        
        System.out.println(scm);
        PartialTree pt = PartialTree.makeATree(scm);
        pt.outputCSV("./testOutput.csv");
        
        
        //pt.reduceRepeat(0);
        //pt.outputCSV("./testOutput2.csv");*/
        //String path = "./TestData/ddCloneSimulatedMatrix";
        /*
        String path = "./TestData/MatrixTree100";
        String comparePath = "./csvTrees/";
        
        File[] files = new File(path).listFiles();
        for (File file: files){
            DataHandle dh = new DataHandle(path + "/" + file.getName());
            String[] temp = file.getName().split("-");
            String compareFile = temp[1] + "-" + temp[2] + "-" + temp[3] + "-" + temp[4];
            SingleCellMatrix scm = dh.getSCM();
            //System.out.println(scm);
            BulkSequence bs = dh.getBulk();
            //System.out.println(bs);

            scm.pathSort(bs);
            //System.out.println(scm);
            scm.commonGeneScoring(0.8);
            scm.commonRoot();

            //System.out.println(scm);

            PartialTree pt = PartialTree.makeATree(scm);
            pt.reduceRepeat(0);
            pt.outputCSV(path + "Output1/" + file.getName());
            
            ResultQualify rq = new ResultQualify(SciteTree.makeASciteTree(comparePath + "/" + compareFile));
            
            AncestorMatrix am = pt.getAncestorMatrix();
            //System.out.println(am);
            //System.out.println(am.getNameSpace());
            System.out.println(rq.getResultQualify(am));
        }*/
        /*DataHandle dh = new DataHandle("./output1.csv");
        SingleCellMatrix scm = dh.getSCM();
            //System.out.println(scm);
        BulkSequence bs = dh.getBulk();
            //System.out.println(bs);

        scm.pathSort(bs);
            //System.out.println(scm);
        scm.commonGeneScoring(0.8);
        scm.commonRoot();

            //System.out.println(scm);

        PartialTree pt = PartialTree.makeATree(scm);
        pt.reduceRepeat(0);
        pt.outputCSV("./right1.csv");*/
        
        
        
    }
    
}
