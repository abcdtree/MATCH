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
        */
        
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
        
        //MATCH 2.0 06132017 Jianshu Zhang
        
        String inputFile = "./TestData/MatrixTree100/MutMatrix-VAF-SIMU-NUM-0.csv";
        String outputFile = "./Test3.csv";
        DataHandle dh = new DataHandle(inputFile);
        SingleCellMatrix scm = dh.getSCM();
        
        //DataMatrix dm = scm.getDataMatrix();
        
        //System.out.println(dm.columnSize() + " " + dm.rowSize());
        
        BulkSequence bs = dh.getBulk();
        //VAFMatrix vafm = bs.getVafMatrix(dm.getNameSpace());
        
        //System.out.println(vafm.getColumnSize() + " " + vafm.getRowSize());
        
        scm.pathSort(bs);
        scm.commonGeneScoring(0.8);
        
        scm.commonRoot();
        
        PartialTree pt = PartialTree.makeATree(scm);
        
        //pt.reduceRepeat(0);
        
        AncestorMatrix am = pt.getAncestorMatrix();
        for(String s: am.getNameSpace().getNames()){
            System.out.println(s);
        }
        
        /*System.out.println(am.size());
        
        VAFMatrix vafm = bs.getVafMatrix(am.getNameSpace());
        
        DataMatrix dm = dh.getSCM().getDataMatrix(am.getNameSpace());
        vafm.updateWithDataMatrix(dm);
        //am.reArrange(dm.getNameSpace());
        double alpha = 0.00001;
        double beta = 0.00001;
        int repeatLimits = 800000;
        double proportion = 0.9;
        
        MCMC myMcmc = new MCMC(am, dm, vafm, alpha, beta);
        double finalScore = myMcmc.startMCMCPlus(repeatLimits, 1, proportion);
        
        am = myMcmc.getAncestorMatrix();*/
        SciteTree st = SciteTree.makeASciteTree(am, am.getNameSpace());
        
        st.outputCSV(outputFile);
        
        
        
        
 
        
    }
    
}
