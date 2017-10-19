/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.criteria.KAnonymity;

/**
 * This class implements an example on how to use the API by providing CSV files
 * as input
 * 
 * @author italo
 */
public class TesteFile2 extends printData {
    
    public static void main(String[] args) throws IOException {
        
        Data data = Data.create("data/adult.csv", StandardCharsets.UTF_8, ';');
        
        // Define input files
        
        data.getDefinition().setAttributeType("education", Hierarchy.create("data/adult_hierarchy_education.csv", StandardCharsets.UTF_8, ';'));
        
        data.getDefinition().setAttributeType("marital-status", Hierarchy.create("data/adult_hierarchy_marital-status.csv", StandardCharsets.UTF_8, ';'));
        
        data.getDefinition().setAttributeType("native-country", Hierarchy.create("data/adult_hierarchy_native-country.csv", StandardCharsets.UTF_8, ';'));
        
        data.getDefinition().setAttributeType("occupation", Hierarchy.create("data/adult_hierarchy_occupation.csv", StandardCharsets.UTF_8, ';'));
        
        data.getDefinition().setAttributeType("race", Hierarchy.create("data/adult_hierarchy_race.csv", StandardCharsets.UTF_8, ';'));
        
        data.getDefinition().setAttributeType("salary-class", Hierarchy.create("data/adult_hierarchy_salary-class.csv", StandardCharsets.UTF_8, ';'));
        
        data.getDefinition().setAttributeType("sex", Hierarchy.create("data/adult_hierarchy_sex.csv", StandardCharsets.UTF_8, ';'));
        
        data.getDefinition().setAttributeType("workclass", Hierarchy.create("data/adult_hierarchy_workclass.csv", StandardCharsets.UTF_8, ';'));
        
        data.getDefinition().setAttributeType("age", Hierarchy.create("data/adult_hierarchy_age.csv", StandardCharsets.UTF_8, ';'));
        
        
        // set the minimal generalization height
        /*data.getDefinition().setMaximumGeneralization("sex", 1);
        data.getDefinition().setMaximumGeneralization("age", 1);
        data.getDefinition().setMaximumGeneralization("marital-status", 1);
        data.getDefinition().setMaximumGeneralization("native-country", 1);
        data.getDefinition().setMaximumGeneralization("education", 1);
        data.getDefinition().setMaximumGeneralization("occupation", 1);
        data.getDefinition().setMaximumGeneralization("workclass", 1);*/
        data.getDefinition().setMinimumGeneralization("age", 2);
        data.getDefinition().setMaximumGeneralization("age", 2);
        
        data.getDefinition().setMinimumGeneralization("sex", 1);
        data.getDefinition().setMaximumGeneralization("sex", 1);
        
        data.getDefinition().setMinimumGeneralization("marital-status", 1);
        data.getDefinition().setMaximumGeneralization("marital-status", 1);
        
        
        
        // Create an instance of the anonymizer
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        
        // Execute the algorithm
        ARXConfiguration config = ARXConfiguration.create();
        config.addPrivacyModel(new KAnonymity(2));
        config.setMaxOutliers(0d);
        ARXResult result = anonymizer.anonymize(data, config);
        
        // Print info
        printResult(result, data);
        
        // Write results
        System.out.print(" - Writing data...");
        result.getOutput(false).save("data/adultTeste.csv", ';');
        System.out.println("Done!");
}
    
}
