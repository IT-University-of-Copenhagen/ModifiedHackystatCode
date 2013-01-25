package dk.itu.thesis.dbresource;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hackystat.sensorbase.client.SensorBaseClient;
import org.hackystat.sensorbase.resource.sensorbase.SensorBaseResource;
import org.hackystat.sensorbase.resource.sensordata.jaxb.SensorData;
import org.hackystat.sensorbase.resource.sensordatatypes.jaxb.SensorDataType;
import org.hackystat.sensorbase.resource.users.jaxb.User;
import org.hackystat.utilities.tstamp.Tstamp;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.Variant;

public class DbSensorDataResource extends DBaseResource {
  private String xmlSensorData;
  private String xmlSensorDataRef;
  private String xmlGregorian;

  public DbSensorDataResource(Context context, Request request, Response response) {
    super(context, request, response);

    this.xmlSensorData = (String) request.getAttributes().get("xmlSensorData");
    this.xmlSensorDataRef = (String) request.getAttributes().get("xmlSensorDataRef");
    this.xmlGregorian = (String) request.getAttributes().get("xmlGregorian");

    // getVariants().add(new Variant(MediaType.TEXT_XML));
  }

  @Override
  public Representation represent(Variant variant) {
    SensorData data = null;
    try {
      // data=super.dbmanager.makeSensorData(SensorData);
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    try {
      if (variant.getMediaType().equals(MediaType.TEXT_XML)) {
        // String Data =super.dbmanager.getSensorDataIndex();

        // if(super.dbmanager.storeSensorData(data, xmlSensorData,
        // xmlSensorDataRef))
        // {
        // return super.getStringRepresentation("true");
        // }
        // String Data ="";
        // System.out.println(Data);
        // return super.getStringRepresentation(data.getTool());
        // return Data;
        // return super.getStringRepresentation(data.getTool());
      }
    } catch (RuntimeException e) {
      setStatusInternalError(e);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean allowPut() {
    return true;
  }

  @Override
  public void storeRepresentation(Representation entity) {
    String entityString = null;
    SensorData sd;

    try {
      entityString = entity.getText();
      // SensorBaseClient a=new SensorBaseClient();
      sd = super.dbmanager.makeSensorData(entityString);
    } catch (Exception e) {
      setStatusMiscError("Bad SensorDataType representation: " + entityString);
      return;
    }

    if (super.dbmanager.storeSensorData(sd, xmlSensorData, xmlSensorDataRef))
      getResponse().setStatus(Status.SUCCESS_CREATED);
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
      getResponse().setEntity(super.dbmanager.getSensorData(user, timeXml), MediaType.TEXT_XML);
    } catch (RuntimeException e) {
      setStatusInternalError(e);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
