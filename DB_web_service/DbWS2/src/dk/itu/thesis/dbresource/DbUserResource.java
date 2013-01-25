package dk.itu.thesis.dbresource;

import java.util.HashMap;
import java.util.Map;

import org.hackystat.sensorbase.db.DbManager;
import org.hackystat.sensorbase.resource.sensorbase.SensorBaseResource;
import org.hackystat.sensorbase.resource.users.jaxb.Properties;
import org.hackystat.sensorbase.resource.users.jaxb.User;
import org.hackystat.sensorbase.resource.users.jaxb.UserIndex;
import org.hackystat.sensorbase.resource.users.jaxb.UserRef;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

public class DbUserResource extends DBaseResource {

	private String user;
	private String xmlUser;
	private String xmlUserRef;
	private Map<String, User> email2user = new HashMap<String, User>();

	public DbUserResource(Context context, Request request, Response response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
		this.user = (String) request.getAttributes().get("user");
		this.xmlUser = (String) request.getAttributes().get("xmlUser");
		this.xmlUserRef = (String) request.getAttributes().get("xmlUserRef");
	}

	/* GET */
	@Override
	public Representation represent(Variant variant) {
		// TODO Auto-generated method stub
		UserIndex index = null;
		// User user=null;
		String email = "";
	/*	
		try {
			index = super.dbmanager.makeUserIndex(this.dbmanager.getUserIndex());
			for (UserRef ref : index.getUserRef()) {
				System.out.println(ref.getEmail());
				if (this.user.equalsIgnoreCase(ref.getEmail())) {
					email = ref.getEmail();					
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    */
		try {
			if (variant.getMediaType().equals(MediaType.TEXT_XML)) {
				String xmlData = "";
				try {
					xmlData = super.dbmanager.getUser(this.user);
					System.out.println("xmlData "+xmlData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return super.getStringRepresentation(xmlData);
			}// end if
		} catch (RuntimeException e) {
			setStatusInternalError(e);
		}

		return null;
	}// end GET

	/**
	 * Indicate the DELETE method is supported.
	 * 
	 * @return True.
	 */
	@Override
	public boolean allowDelete() {
		return true;
	}

	/**
	 * Implement the DELETE method that deletes an existing User given their
	 * email. Only the authenticated user (or the admin) can delete their User
	 * resource.
	 */
	@Override
	public void removeRepresentations() {
	//	if (!validateAuthUserIsAdminOrUriUser()) {
	//		return;
	//	}
		
		try {
			//super.userManager.deleteUser(uriUser);
			super.dbmanager.deleteUser(this.user);
			getResponse().setStatus(Status.SUCCESS_OK);
		} catch (RuntimeException e) {
			setStatusInternalError(e);
		}
	}// end DELETE

	/**
	 * Indicate the POST method is supported.
	 * 
	 * @return True.
	 */
	@Override
	public boolean allowPost() {
		return true;
	}

	@Override
	  public boolean allowPut() {
	    return true;
	  }

	@Override
	public void storeRepresentation(Representation entity) {
		// if (!validateUriUserIsUser() ||
		// !validateAuthUserIsAdminOrUriUser()) {
		// return;
		// }

		// Attempt to construct a Properties object.
		String entityString = null;
		User user = null;
		// Properties newProperties;
		// Try to make the XML payload into a Properties instance, return
		// failure if this fails.
		try {
			entityString = entity.getText();
			user = super.dbmanager.makeUser(entityString);
			// newProperties = super.userManager.makeProperties(entityString);
			//String xmlUser = entityString;
			//String xmlUserRef = super.dbmanager.makeUserRefString(user);
			if (super.dbmanager.storeUser(user, xmlUser, xmlUserRef)){
				getResponse().setStatus(Status.SUCCESS_CREATED);
			}
			      
		} catch (Exception e) {
			setStatusMiscError("Bad properties representation: " + entityString);
			return;
		}
	}

}
