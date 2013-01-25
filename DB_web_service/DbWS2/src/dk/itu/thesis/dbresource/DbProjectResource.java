package dk.itu.thesis.dbresource;

import org.hackystat.sensorbase.resource.projects.jaxb.Project;
import org.hackystat.sensorbase.resource.projects.jaxb.ProjectIndex;
import org.hackystat.sensorbase.resource.projects.jaxb.ProjectRef;
import org.hackystat.sensorbase.resource.users.jaxb.User;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;

public class DbProjectResource extends DBaseResource{

	private String user;
	private String projectName;
	private String xmlProject;
	private String xmlProjectRef;
	
	public DbProjectResource(Context context, Request request, Response response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
		this.user = (String) request.getAttributes().get("user");
		this.projectName =(String) request.getAttributes().get("projectName");
		this.xmlProject = (String) request.getAttributes().get("xmlProject");
		this.xmlProject = (String) request.getAttributes().get("xmlProjectRef");
	}

	@Override
	public Representation represent(Variant variant) {
		// TODO Auto-generated method stub
		String owner="";
		User userObj=null;
		try {
			ProjectIndex index = super.dbmanager.makeProjectIndex(this.dbmanager.getProjectIndex());
			for (ProjectRef ref : index.getProjectRef()){
				System.out.println(index.getProjectRef());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 if (variant.getMediaType().equals(MediaType.TEXT_XML)) {
		      try {
		        //String xmlData = super.projectManager.getProjectString(this.user, this.projectName);
		    	userObj = super.dbmanager.makeUser(this.user);
		    	String xmlData = super.dbmanager.getProject(userObj, projectName);
		        return super.getStringRepresentation(xmlData);
		      }
		      catch (Exception e) {
		        setStatusInternalError(e);
		      }
		    }
		return null;
	}
	
	@Override
	public boolean allowPost() {
		return true;
	}
	/* PUT */
	@Override
	public void storeRepresentation(Representation entity) {
		String entityString = null;
		Project project= null;
		  try { 
		      entityString = entity.getText();
		      project = super.dbmanager.makeProject(entityString);
		      if(super.dbmanager.storeProject(project, this.xmlProject, this.xmlProjectRef)){
		    	  getResponse().setStatus(Status.SUCCESS_CREATED);
		      }
		    }
		    catch (Exception e) {
		      setStatusMiscError("Bad properties representation: " + entityString);
		      return;
		    }		   
	}
	
	@Override
	  public boolean allowPut() {
	    return true;
	  }
	
	@Override
	public boolean allowDelete() {
		return true;
	}
	@Override
	public void removeRepresentations() {
		try {
		      //if (!validateUriUserIsUser() ||
		      //    !validateAuthUserIsAdminOrUriUser()) {
		      //  return;
		      //}  
		      if ("Default".equals(this.projectName)) {
		        getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Cannot delete Default project.");
		        return;
		      }    
		      // Otherwise, delete it and return success.		     
		      User user = super.dbmanager.makeUser(this.user);
		      super.dbmanager.deleteProject(user, projectName);
		      getResponse().setStatus(Status.SUCCESS_OK);
		    }
		    catch (Exception e) {
		      setStatusInternalError(e);
		    }
	}
	

}
