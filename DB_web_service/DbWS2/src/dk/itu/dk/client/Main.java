package dk.itu.dk.client;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.hackystat.sensorbase.resource.projects.jaxb.Project;
import org.hackystat.sensorbase.resource.sensordatatypes.jaxb.SensorDataType;
import org.hackystat.sensorbase.resource.sensordatatypes.jaxb.SensorDataTypeIndex;
import org.hackystat.sensorbase.resource.users.jaxb.User;
import org.hackystat.sensorbase.resource.users.jaxb.UserIndex;

public class Main {

	/**
	 * @param args
	 * @throws SensorBaseClientException 
	 */
	private static String host = "http://localhost:8182/dbWS";
	private static String username="admin@hackystat.org";
	private static String password="admin@hackystat.org";
	public static void main(String[] args) throws SensorBaseClientException {
		// TODO Auto-generated method stub
		
        SensorBaseClient client = new SensorBaseClient(host, username, password);
       // SensorDataType sdt = new SensorDataType();
       // sdt.setName("Commit");
       // client.putSensorDataType(sdt);
        
       // UserIndex ui = client.getUserIndex();
       // XMLGregorianCalendar a = ui.getLastMod();
       // System.out.println(a.getHour());    
       //System.out.println(client.getUser(username).getEmail()+client.getUser(username).getPassword());      
       // SensorDataTypeIndex sdt = client.getSensorDataTypeIndex();
       // System.out.println(sdt.getSensorDataTypeRef().isEmpty());
       // String a = client.testcase();
       // System.out.println(a);
        
       // System.out.println(client.getUserNy("rere@gmail.com"));
        User user = new User();
        user.setEmail("Test555@gmail.com");
        user.setPassword("555555");
        
	   //   client.deleteUserNy("Test99@itu.dk");
        //System.out.println(client.getUserIndexNy());
        //client.registerUser(host, "Test77777@gmail.com");
        //User user = client.getUser("Test777777@gmail.com");
        
        //System.out.println(client.storeUserNy(user, "xmlUser", "xmlUserRef"));
       // System.out.println(client.getProjectNy(user, "Default"));
        //System.out.println(client.getUserNy("Test77777@gmail.com"));
       // System.out.println(client.getProjectIndexNy());
        //System.out.println(client.getSensorDataTypeIndexNy());
        Project project = new Project();
        project.setName("");
        //
        //client.storeProject(project, "xmlProject", "xmlProjectRef");
        
        
     // store sensordata
        // try {
        // Map<String, String> keyValMap = new HashMap<String, String>();
        // // Create the TestUser client.
        // // SensorBaseClient client = new SensorBaseClient(getHostName(), user,
        // user);
        // // See that we can create a SensorData instance with all defaults.
        // // client.makeSensorData(keyValMap);
        // // Add a couple of fields and make a new one.
        // String tool = "Eclipse";
        // keyValMap.put("Tool", tool);
        // // keyValMap.put("MyProperty", "foobar");
        // SensorData data = client.makeSensorData(keyValMap);
        // // sd = makeSensorData(entityString);
        // // Representation representation =
        // SensorBaseResource.getStringRepresentation(data);
        
        
        
        //        
        // client.storeSensorData(data , "nan", "yang");
        // // client.getS
        // // client.registerUser(host, "naya@itu.dk");
        // // User user= client.getUser("admin@hackystat.org");
        // // System.out.println(user.getPassword());
        // // "http://localhost:9876/sensorbase", "naya@itu.dk"
        // // client.registerUser();
        // // System.out.println(client.getSensorIndex());
        // } catch (SensorBaseClientException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        
        // getSensorData
        // User temp=new User();
        // temp.setEmail("naya@itu.dk");
        //     
        // XMLGregorianCalendar endTime=Tstamp.makeTimestamp();
        // try {
        // client.getSensorData(temp, endTime);
        // } catch (SensorBaseClientException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // deleteSensorData
        // User temp=new User();
        // temp.setEmail("naya@itu.dk");
        // XMLGregorianCalendar endTime=Tstamp.makeTimestamp();
        //      
        // try {
        // client.deleteSensorData(temp,endTime);
        // } catch (SensorBaseClientException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // deleteSensorDataType
        // try {
        // client.deleteSensorDataTypeNy("yangyangyang");
        // } catch (SensorBaseClientException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // try {
        // System.out.println(client.getSensorDataTypeNy("Commit"));
        // } catch (SensorBaseClientException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // storeSensorDataType
        SensorDataType a = new SensorDataType();
        a.setName("Commit");
        try {
          client.storeSensorDataType(a, "v", "t");
        } catch (SensorBaseClientException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

	}

}
