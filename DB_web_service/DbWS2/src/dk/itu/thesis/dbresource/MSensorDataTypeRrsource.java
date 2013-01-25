package dk.itu.thesis.dbresource;

import java.io.IOException;

import org.hackystat.sensorbase.resource.sensorbase.SensorBaseResource;
import org.hackystat.sensorbase.resource.sensordatatypes.jaxb.SensorDataType;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;

public class MSensorDataTypeRrsource extends DBaseResource {
  String sdtName;
  String dSdtName;
  String xmlSensorDataType;
  String xmlSensorDataTypeRef;
  

  public MSensorDataTypeRrsource(Context context, Request request, Response response) {
    super(context, request, response);
    this.sdtName = (String) request.getAttributes().get("sdtName");
    this.dSdtName= (String) request.getAttributes().get("dSdtName");
    this.xmlSensorDataType = (String) request.getAttributes().get("xmlSensorDataType");
    this.xmlSensorDataTypeRef = (String) request.getAttributes().get("xmlSensorDataTypeRef");
  }

  @Override
  public boolean allowGet() {
    return true;
  }
  
  public Representation represent(Variant variant) {
   
    String sdt = super.dbmanager.getSensorDataType(this.sdtName);
    return super.getStringRepresentation(sdt);
    
  }

  @Override
  public boolean allowPut() {
    return true;
  }

  @Override
  public void storeRepresentation(Representation entity) {
    String entityString = null;
    SensorDataType sdt;

    try {
      entityString = entity.getText();
      sdt = super.dbmanager.makeSensorDataType(entityString);

    } catch (Exception e) {
      setStatusMiscError("Bad SensorDataType representation: " + entityString);
      return;
    }

    if (super.dbmanager.storeSensorDataType(sdt, xmlSensorDataType, xmlSensorDataTypeRef)) {
      getResponse().setStatus(Status.SUCCESS_CREATED);
    }
  }
  
  @Override
  public boolean allowDelete() {
      return true;
  }
  
  /**
   * Implement the DELETE method that deletes an existing SDT given its name.
   */
  @Override
  public void removeRepresentations() 
  {
    try{
    super.dbmanager.deleteSensorDataType(dSdtName);
    getResponse().setStatus(Status.SUCCESS_OK);
    }
    catch (RuntimeException e) {
      setStatusInternalError(e);
    }
  }

}
