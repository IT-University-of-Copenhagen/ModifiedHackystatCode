/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;

/**
 *
 * @author aufeef
 */
public class ControllerProperties {

    private String propertiesFile = "/WEB-INF/controller.properties";//"\\src\\java\\controller.properties";
    private String uriKey = "sensorbaseservicesuri";
    private String uriDelimeter = ",";
    private Properties propertiesObj = new Properties();
    private String sensorBaseUri[];

    public String[] getSensorBaseUri() {
        return sensorBaseUri;
    }

    public void loadProperties(ServletContext servletContext)
    {
        try
        {
            //propertiesObj.load(new FileInputStream(propertiesFile));
            propertiesObj.load(servletContext.getResourceAsStream(propertiesFile));

            String urisString = propertiesObj.getProperty(uriKey);

            StringTokenizer strTok = new StringTokenizer(urisString, uriDelimeter);
            sensorBaseUri = new String[strTok.countTokens()];
            for(int index = 0 ; strTok.hasMoreTokens() ; index++)
            {
                sensorBaseUri[index] = strTok.nextToken().trim();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
