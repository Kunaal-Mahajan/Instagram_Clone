
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class FinalPrediction {
    	/** file names are defined*/
        String file_path = "F:/Skin_Cancer/Dataset_2/HAM10000_images_part_2/ISIC_0029321.jpg";
        
	public String Process(String path) throws Exception {
            
            file_path = path;
        
            String s = null;

                try {

                // run the Unix "ps -ef" command
                    // using the Runtime exec method:
                    //Process p = Runtime.getRuntime().exec("ps -ef");
                    Process p = Runtime.getRuntime().exec("python py_scripts/final_predict.py "+ file_path);

                    BufferedReader stdInput = new BufferedReader(new 
                         InputStreamReader(p.getInputStream()));

                    BufferedReader stdError = new BufferedReader(new 
                         InputStreamReader(p.getErrorStream()));

                    // read the output from the command
                    //System.out.println("Here is the standard output of the command:\n");
                    while ((s = stdInput.readLine()) != null) {
                        System.out.println(s);
                    }

                    // read any errors from the attempted command
                    //System.out.println("Here is the standard error of the command (if any):\n");
                    while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                    }

                    //System.exit(0);
                }
                catch (IOException e) {
                    System.out.println("exception happened - here's what I know: ");
                    e.printStackTrace();
                    //System.exit(-1);
                }        

		String details="";
		
                try {  
                    // Create f1 object of the file to read data  
                    File f1 = new File("final_predict.txt");    
                    Scanner dataReader = new Scanner(f1);  
                    while (dataReader.hasNextLine()) {  
                        details+= dataReader.nextLine();
//                        System.out.println(fileData);  
//                        details+=fileData;
                    }  
                    dataReader.close();

                } catch (FileNotFoundException exception) {  
                    System.out.println("FinalPrediction - File Not Found!");  
                    exception.printStackTrace();  
                }    
            
//		String details="";
//		details+="\n************************** Linear Regression *************************\n";
//                
//		LinearRegression classifier = new LinearRegression();
//		classifier.buildClassifier(trainingDataSet);
//		
//		Evaluation eval = new Evaluation(trainingDataSet);
//		eval.evaluateModel(classifier, testingDataSet);
//		/** Print the algorithm summary */
//		details+="\n** Decision Tress Evaluation with Datasets **\n";
//		details+=eval.toSummaryString();
//		details+="\n**********************************************************************\n";
//		details+="\n The expression for the input data as per alogorithm is \n";
//		details+=classifier;
		
                return details;
		
	}
   
}
