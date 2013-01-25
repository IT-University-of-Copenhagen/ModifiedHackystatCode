package dk.itu.thesis.dbresource;

import javax.xml.datatype.XMLGregorianCalendar;

import org.hackystat.sensorbase.resource.sensorbase.SensorBaseResource;
import org.hackystat.sensorbase.resource.users.jaxb.User;
import org.hackystat.utilities.tstamp.Tstamp;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;

public class DeleteSensorDataResource extends DBaseResource {

  private String xmlGregorian = "0";

  public DeleteSensorDataResource(Context context, Request request, Response response) {
    super(context, request, response);
    this.xmlGregorian = (String) request.getAttributes().get("xmlGregorian");
  }

  @Override
  public Representation represent(Variant variant) {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public boolean allowPut() {
    return true;
  }
  
  @Override
  public void storeRepresentation(Representation entity) 
  {
    String entityString = null;
    try {
      entityString = entity.getText();
    } catch (Exception e) {
      setStatusMiscError("Bad properties representation: " + entityString);
      return;
    }
    try {

      User user = super.dbmanager.makeUser(entityString);
      super.dbmanager.deleteSensorData(user);
      getResponse().setStatus(Status.SUCCESS_CREATED);
    } catch (RuntimeException e) {
      setStatusInternalError(e);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  
  @Override
  public boolean allowPost() {
    return true;
  }

  @Override
  public void acceptRepresentation(Representation entity) {
    String entityString = null;
    XMLGregorianCalendar timeXml = null;
    try {
      entityString = entity.getText();
    } catch (Exception e) {
      setStatusMiscError("Bad properties representation: " + entityString);
      return;
    }

    try {
      timeXml = Tstamp.makeTimestamp(this.xmlGregorian);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    try {

      User user = super.dbmanager.makeUser(entityString);
      super.dbmanager.deleteSensorData(user, timeXml);
      getResponse().setStatus(Status.SUCCESS_CREATED);
    } catch (RuntimeException e) {
      setStatusInternalError(e);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
