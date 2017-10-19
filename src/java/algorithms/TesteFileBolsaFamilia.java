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
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.criteria.KAnonymity;

/**
 *
 * @author italo
 */
public class TesteFileBolsaFamilia extends printData {
    
    public static void main(String[] args) throws IOException {
        
        Data data = Data.create("datanew/BolsaFamilia.csv", StandardCharsets.UTF_8, ';');
        
        // Define input files
        data.getDefinition().setAttributeType("UF", AttributeType.Hierarchy.create("datanew/BolsaFamilia_Hierarquia_UF.csv", StandardCharsets.UTF_8, ';'));
        //data.getDefinition().setAttributeType("Valor-Parcela", AttributeType.Hierarchy.create("datanew/BolsaFamilia_Hierarquia_Valor.csv", StandardCharsets.UTF_8, ';'));
        //data.getDefinition().setAttributeType("Municipio", AttributeType.Hierarchy.create("datanew/BolsaFamilia_Hierarquia_Nome-Municipio.csv", StandardCharsets.UTF_8, ';'));
        data.getDefinition().setAttributeType("Codigo-Subfuncao", AttributeType.Hierarchy.create("datanew/BolsaFamilia_Hierarquia_Codigo_Subfuncao.csv", StandardCharsets.UTF_8, ';'));
        data.getDefinition().setAttributeType("Codigo-Programa", AttributeType.Hierarchy.create("datanew/BolsaFamilia_Hierarquia_Codigo_Programa.csv", StandardCharsets.UTF_8, ';'));
        data.getDefinition().setAttributeType("Codigo-Acao", AttributeType.Hierarchy.create("datanew/BolsaFamilia_Hierarquia_Codigo_Acao.csv", StandardCharsets.UTF_8, ';'));
        //data.getDefinition().setAttributeType("NIS-Favorecido", AttributeType.Hierarchy.create("datanew/BolsaFamilia_Hierarquia_NIS_Favorecido.csv", StandardCharsets.UTF_8, ';'));
        
        
        
        // set the minimal generalization height
        data.getDefinition().setMinimumGeneralization("UF", 1);
        data.getDefinition().setMinimumGeneralization("Codigo-Subfuncao", 1);
        data.getDefinition().setMinimumGeneralization("Codigo-Programa", 1);
        data.getDefinition().setMinimumGeneralization("Codigo-Acao", 1);
        //data.getDefinition().setMinimumGeneralization("NIS-Favorecido", 1);
        
        //data.getDefinition().setMinimumGeneralization("Municipio", 1);
        //data.getDefinition().setMinimumGeneralization("Valor-Parcela", 1);
        
        
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
        result.getOutput(false).save("datanew/BolsaFamiliaAnonymizedTeste.csv", ';');
        System.out.println("Done!");
    }
    
}
