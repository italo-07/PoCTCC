/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import config.Config;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.criteria.KAnonymity;

/**
 *
 * @author leoomoreira
 */
public class SMDKAnonymity {

    public static boolean execute(List<String> attributes, List<String> hierarchyFiles, String fileName) {
        boolean retorno = false;
        try {
            Data data = Data.create(fileName, StandardCharsets.UTF_8, ';');

            for (int i = 0; i < attributes.size(); i++) {
                data.getDefinition().setAttributeType(attributes.get(i), AttributeType.Hierarchy.create(hierarchyFiles.get(i), StandardCharsets.UTF_8, ';'));
            }

            for (int i = 0; i < attributes.size(); i++) {
                data.getDefinition().setMinimumGeneralization(attributes.get(i), 1);
            }
            
            ARXAnonymizer anonymizer = new ARXAnonymizer();

            ARXConfiguration config = ARXConfiguration.create();
            config.addPrivacyModel(new KAnonymity(2));
            config.setMaxOutliers(0d);
            ARXResult result = anonymizer.anonymize(data, config);

            File save = new File(Config.RESULT_SAVE);
            if (!save.exists()) {
                save.mkdir();
            }
            
            File dataRaw = new File(fileName);
            
            result.getOutput(false).save(Config.RESULT_SAVE + "save_" + dataRaw.getName(), ';');
            retorno = true;
        } catch (IOException ex) {
            retorno = false;
        }
        return retorno;
    }

}
