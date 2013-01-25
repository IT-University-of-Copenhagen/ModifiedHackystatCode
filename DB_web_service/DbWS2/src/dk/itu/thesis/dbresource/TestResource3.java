package dk.itu.thesis.dbresource;

import org.hackystat.sensorbase.resource.sensorbase.SensorBaseResource;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;

public class TestResource3 extends SensorBaseResource{

	private String sdtName;
	public TestResource3(Context context, Request request, Response response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
		this.sdtName = (String) request.getAttributes().get("sdtName");
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
