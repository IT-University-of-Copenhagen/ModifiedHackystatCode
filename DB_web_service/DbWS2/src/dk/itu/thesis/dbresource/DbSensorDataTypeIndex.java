package dk.itu.thesis.dbresource;

import org.hackystat.sensorbase.resource.sensorbase.SensorBaseResource;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;

public class DbSensorDataTypeIndex extends DBaseResource{

	public DbSensorDataTypeIndex(Context context, Request request,
			Response response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Representation represent(Variant variant) {
		try {
		      if (variant.getMediaType().equals(MediaType.TEXT_XML)) {
		        //String xmlData = super.sdtManager.getSensorDataTypeIndex();
		        String xmlData = super.dbmanager.getSensorDataIndex();
		    	  return super.getStringRepresentation(xmlData);
		      }
		    }
		    catch (RuntimeException e) {
		      setStatusInternalError(e);
		    }
		return null;
	}

}
