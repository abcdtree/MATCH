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
        //MATCH 2.1 06292017 Jianshu Zhang
        
        String inputFile = "./NewCCF/MM1/MM1_Stem_input_newCCF_0.9577.csv";
        //String outputFile = "./Test4.csv";
        DataHandle dh = new DataHandle(inputFile);
        SingleCellMatrix scm = dh.getSCM();
        
        ///System.out.println(scm.rowSize);
        //System.out.println(dm.columnSize() + " " + dm.rowSize());
        BulkSequence bs = dh.getBulk(1);
        //System.out.println(bs.size());
        //System.out.println(vafm.getColumnSize() + " " + vafm.getRowSize());
        
        scm.pathSort(bs);
        scm.commonGeneScoring(0.1);
        scm.commonRoot();
        PartialTree pt = PartialTree.makeATree(scm);
        
        System.out.println(pt.size);
        
        pt.outputCSV("./NewCCF/MM1/MM1_Stem_input_newCCF_0.9577_PartialTree_0.1.csv");
        
        pt.reduceRepeat(2);
        
        pt.outputCSV("./NewCCF/MM1/MM1_Stem_input_newCCF_0.9577_PartialTree_Without_Repeat_0.1.csv");
        //System.out.println(pt.size);
        System.out.println(pt.muts.size());
        pt.rootReduce();
        
        pt.outputCSV("./NewCCF/MM1/MM1_Stem_input_newCCF_0.9577_Without_Created_Root_0.1.csv");
        System.out.println(pt.muts.size());
        
        /*
        AncestorMatrix am = pt.getAncestorMatrix();
        
        //System.out.println(am.size());
        //System.out.println(am);
        System.out.println(am.getNameSpace());
        VAFMatrix vafm = bs.getVafMatrix(am.getNameSpace());
        DataMatrix dm = dh.getSCM().getDataMatrix(am.getNameSpace());
        vafm.updateWithDataMatrix(dm);
        //alpha is false 
        double alpha = 0.6;
        double beta = 0.00001;
        int repeatLimits = 800000;
        double proportion = 0.9;
        
        MCMC myMcmc = new MCMC(am, dm, vafm, alpha, beta);
        double finalScore = myMcmc.startMCMCPlus(repeatLimits, 1, proportion);
       
        //am = myMcmc.getAncestorMatrix();
        SciteTree st = SciteTree.makeASciteTree(am, am.getNameSpace());
        
        st.outputCSV(".//MM1/MM1_MM_AfterMCMCTree_0.5.csv");
        */
        
        
        
 
        
    }
    
}
