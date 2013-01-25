package dk.itu.thesis.dbresource;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;
import org.restlet.data.Status;

public class DbProjectIndex extends DBaseResource{

	public DbProjectIndex(Context context, Request request, Response response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Representation represent(Variant variant) {
		try {
		      if (variant.getMediaType().equals(MediaType.TEXT_XML)) {
		        //String xmlData = super.userManager.getUserIndex();
		    	  String xmlData = super.dbmanager.getProjectIndex();
		        return super.getStringRepresentation(xmlData);      
		      }
		    }
		    catch (RuntimeException e) {
		      setStatusInternalError(e);
		    }
		    return null;
	}

 

}
