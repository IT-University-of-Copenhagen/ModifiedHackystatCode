package dk.itu.thesis.dbresource;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hackystat.sensorbase.db.DbManager;
import org.hackystat.sensorbase.resource.sensorbase.SensorBaseResource;
import org.hackystat.sensorbase.resource.sensordatatypes.jaxb.SensorDataType;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;
import org.w3c.dom.Document;
/* store sensor data type */
public class TestResource2 extends SensorBaseResource{

	String sdtName;
	
	public TestResource2(Context context, Request request, Response response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
		this.sdtName = (String) request.getAttributes().get("sdtName");
	}

	
	public void storeRepresentation(Representation entity){
		String entityString = null;
	    SensorDataType sdt;
	    if (!validateAuthUserIsAdmin()) {
	      return;
	    }

	    // Try to make the XML payload into an SDT, return failure if this fails. 
	    try { 
	      entityString = entity.getText();
	      sdt = super.sdtManager.makeSensorDataType(entityString);
	    }
	    catch (Exception e) {
	      setStatusMiscError("Bad SensorDataType representation: " + entityString); 
	      return;
	    }
	    
	    try {
	      // Return failure if the URI SdtName is not the same as the XML SdtName.
	      if (!(this.sdtName.equals(sdt.getName()))) {
	        setStatusMiscError("URI SDT name does not equal the representation's name.");
	        return;
	      }
	      // otherwise we add it to the Manager and return success.
	      super.sdtManager.putSdt(sdt);     
	      System.out.println("The sdt "+sdt+" had been putted");
	      getResponse().setStatus(Status.SUCCESS_CREATED);
	    }
	    catch (RuntimeException e) {
	      setStatusInternalError(e);
	    }
	}
	
	public void removeRepresentations() {}
	
	public boolean allowPut() {
	      return true;
	  }
	public boolean allowDelete() {
	      return true;
	  }


	@Override
	public Representation represent(Variant variant) {
		// TODO Auto-generated method stub
		if (!super.sdtManager.hasSdt(this.sdtName)) {
		      getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Unknown SDT: " + this.sdtName);
		      return null;
		    } 
		    if (variant.getMediaType().equals(MediaType.TEXT_XML)) {
		      String xmlData = super.sdtManager.getSensorDataTypeString(this.sdtName);
		      return super.getStringRepresentation(xmlData);
		    }
		    return null;
	}
	

}
