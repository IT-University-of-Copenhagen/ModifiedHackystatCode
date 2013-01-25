/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.servlet.ServletContext;

/**
 *
 * @author aufeef
 */
public class RoutingManager {

    private static int indexCounter = 0;
    private static int totalUriAvailable = 0;
    private static boolean propertiesLoaded = false;
    private static ControllerProperties controllerPropertiesObj;
    private static String sensorBaseUri[];

    public static String getSensorBaseUri(ServletContext servletContext)
    {
        String returnUri = "";

        try{
            if( !propertiesLoaded )
            {
                controllerPropertiesObj = new ControllerProperties();
                controllerPropertiesObj.loadProperties(servletContext);
                sensorBaseUri = controllerPropertiesObj.getSensorBaseUri();
                totalUriAvailable = sensorBaseUri.length;
                propertiesLoaded = true;
            }
            
            if( totalUriAvailable > 0 )
            {
                int index = indexCounter % totalUriAvailable;
                returnUri = sensorBaseUri[index];
                indexCounter++;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return returnUri;
    }

    public static void main(String args[])
    {
        for(int i = 0 ; i < 10 ; i++)
        {
            //String str = RoutingManager.getSensorBaseUri();
            //System.out.println(str);
        }


    }
}
