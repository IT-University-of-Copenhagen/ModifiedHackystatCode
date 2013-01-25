package org.itu.thesis.db;

import static org.itu.thesis.server.ServerProperties.DB_DIR_KEY;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;


import org.hackystat.utilities.stacktrace.StackTrace;
import org.hackystat.utilities.tstamp.Tstamp;
import org.hackystat.sensorbase.resource.projects.jaxb.Project;
import org.hackystat.sensorbase.resource.projects.jaxb.ProjectSummary;
import org.hackystat.sensorbase.resource.projects.jaxb.SensorDataSummaries;
import org.hackystat.sensorbase.resource.projects.jaxb.SensorDataSummary;
import org.hackystat.sensorbase.resource.sensordata.jaxb.SensorData;
import org.hackystat.sensorbase.resource.sensordatatypes.jaxb.SensorDataType;
import org.hackystat.sensorbase.resource.users.jaxb.User;
import org.itu.thesis.client.SensorBaseClient;
import org.itu.thesis.client.SensorBaseClientException;
import org.itu.thesis.server.Server;


/**
 * Provides a implementation of DbImplementation using Derby in embedded mode.
 * 
 * Note: If you are using this implementation as a guide for implementing an alternative database,
 * you should be aware that this implementation does not do connection pooling.  It turns out
 * that embedded Derby does not require connection pooling, so it is not present in this code.
 * You will probably want it for your version, of course. 
 * 
 * @author Philip Johnson
 */
public class DerbyImplementation extends DbImplementation {
  
  /** The JDBC driver. */
  private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
  
  /** The Database name. */
  private static final String dbName = "sensorbase";
  
  /**  The Derby connection URL. */ 
  private static final String connectionURL = "jdbc:derby:" + dbName + ";create=true";
  
  /** Indicates whether this database was initialized or was pre-existing. */
  private boolean isFreshlyCreated;
  
  /** The SQL state indicating that INSERT tried to add data to a table with a preexisting key. */
  private static final String DUPLICATE_KEY = "23505";
  
  /** The key for putting/retrieving the directory where Derby will create its databases. */
  private static final String derbySystemKey = "derby.system.home";
  
  /** The logger message for connection closing errors. */
  private static final String errorClosingMsg = "Derby: Error while closing. \n";
  
  /** The logger message when executing a query. */
  private static final String executeQueryMsg = "Derby: Executing query ";
  
  /** Required by PMD since this string occurs multiple times in this file. */
  private static final String ownerEquals = " owner = '";

  /** Required by PMD since this string occurs multiple times in this file. */
  private static final String sdtEquals = " sdt = '";
  private static final String toolEquals = " tool = '";
  
  /** Required by PMD as above. */
  private static final String quoteAndClause = "' AND ";
  private static final String andClause = " AND ";
  private static final String selectPrefix = "SELECT XmlSensorDataRef FROM SensorData WHERE "; 
  private static final String selectSnapshot = 
    "SELECT XmlSensorDataRef, Runtime, Tool FROM SensorData WHERE "; 
  private static final String orderByTstamp = " ORDER BY tstamp";
  private static final String orderByRuntime = " ORDER BY runtime DESC";
  private static final String derbyError = "Derby: Error ";
  private static final String indexSuffix = "Index>";
  private static final String xml = "Xml";
 
  private static String host = "http://localhost:8182/dbWS";
  private static String username="admin@hackystat.org";
  private static String password="admin@hackystat.org";
  /**
   * Instantiates the Derby implementation.  Throws a Runtime exception if the Derby
   * jar file cannot be found on the classpath.
   * @param server The SensorBase server instance. 
   */
  public DerbyImplementation(Server server) {
    super(server);

  }
  

  /** {@inheritDoc} */
  @Override
  public void initialize() {

  }

  /** {@inheritDoc} */
 @Override
  public boolean storeSensorData(SensorData data, String xmlSensorData, String xmlSensorDataRef) {
   SensorBaseClient client = new SensorBaseClient(host, username, password);
   
    try {
      if(client.storeSensorData(data, xmlSensorData, xmlSensorDataRef))
        return true;
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }


  /** {@inheritDoc} */
 
  @Override
  public boolean isFreshlyCreated() {
   // SensorBaseClient client = new SensorBaseClient(host, username, password);
    return this.isFreshlyCreated;
  }
  
  
  /** {@inheritDoc} */
  @Override
  public String getSensorDataIndex() {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      return(client.getSensorDataTypeIndex());
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return "Can't find the SensorDataIndex"; //NOPMD  (See below)
  }
  
  /*
   * Interestingly, I could not refactor out the string "SensorData" to avoid the PMD error
   * resulting from multiple occurrences of the same string. 
   * This is because if I made it a private String, then Findbugs would throw a warning asking
   * for it to be static:
   * 
   * private static final String sensorData = "SensorData"; 
   * 
   *  However, the above declaration causes the system to deadlock! 
   *  So, I'm just ignoring the PMD error. 
   */
 
  
  /** not implement */
  /** {@inheritDoc} */
  @Override
  public String getSensorDataIndex(User user) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
   
   // client.getS
    return "";
  }
  /** not implement */
  /** {@inheritDoc} */
  @Override
  public String getSensorDataIndex(User user, String sdtName) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    return "";
  }
  
  /** not implement */
  /** {@inheritDoc} */
  @Override
  public String getSensorDataIndex(List<User> users, XMLGregorianCalendar startTime, 
      XMLGregorianCalendar endTime, List<String> uriPatterns, String sdt) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    return "";
  }
  
  /** not implement */
  /** {@inheritDoc} */
  @Override
  public String getSensorDataIndex(List<User> users, XMLGregorianCalendar startTime, 
      XMLGregorianCalendar endTime, List<String> uriPatterns, String sdt, String tool) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    return "";
  }
  
  /** not implement */
  /** {@inheritDoc} */
  @Override
  public String getProjectSensorDataSnapshot(List<User> users, XMLGregorianCalendar startTime, 
      XMLGregorianCalendar endTime, List<String> uriPatterns, String sdt, String tool) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
 
    return "";
  }
  
  /** not implement */
  /** {@inheritDoc} */
  @Override
  public String getSensorDataIndex(List<User> users, XMLGregorianCalendar startTime, 
      XMLGregorianCalendar endTime, List<String> uriPatterns, int startIndex, 
      int maxInstances) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    return "";
  }
  
  /**
   * Constructs a set of LIKE clauses corresponding to the passed set of UriPatterns.
   * <p>
   * Each UriPattern is translated in the following way:
   * <ul>
   * <li> If there is an occurrence of a "\" or a "/" in the UriPattern, then 
   * two translated UriPatterns are generated, one with all "\" replaced with "/", and one with 
   * all "/" replaced with "\".
   * <li> The escape character is "\", unless we are generating a LIKE clause containing a 
   * "\", in which case the escape character will be "/".
   * <li> All occurrences of "%" in the UriPattern are escaped.
   * <li> All occurrences of "_" in the UriPattern are escaped.
   * <li> All occurrences of "*" are changed to "%".
   * </ul>
   * The new set of 'translated' UriPatterns are now used to generate a set of LIKE clauses
   * with the following form:
   * <pre>
   * (RESOURCE like 'translatedUriPattern1' escape 'escapeChar1') OR
   * (RESOURCE like 'translatedUriPattern2' escape 'escapeChar2') ..
   * </pre>
   * 
   * <p>
   * There is one special case.  If the List(UriPattern) is null, empty, or consists of exactly one 
   * UriPattern which is "**" or "*", then the empty string is returned. This is an optimization for
   * the common case where all resources should be matched and so we don't need any LIKE clauses.
   * <p>
   * We return either the empty string (""), or else a string of the form:
   * " AND ([like clause] AND [like clause] ... )"
   * This enables the return value to be appended to the SELECT statement.
   * <p>
   * This method is static and package private to support testing. See the class 
   * TestConstructUriPattern for example invocations and expected return values. 
   *  
   * @param uriPatterns The list of uriPatterns.
   * @return The String to be used in the where clause to check for resource correctness.
   */
  static String constructLikeClauses(List<String> uriPatterns) {
    // Deal with special case. UriPatterns is null, or empty, or "**", or "*"
    if (((uriPatterns == null) || uriPatterns.isEmpty()) ||
        ((uriPatterns.size() == 1) && uriPatterns.get(0).equals("**")) ||
        ((uriPatterns.size() == 1) && uriPatterns.get(0).equals("*"))) {
      return "";
    }
    // Deal with the potential presence of path separator character in UriPattern.
    List<String> translatedPatterns = new ArrayList<String>();
    for (String pattern : uriPatterns) {
      if (pattern.contains("\\") || pattern.contains("/")) {
        translatedPatterns.add(pattern.replace('\\', '/'));
        translatedPatterns.add(pattern.replace('/', '\\'));
      }
      else {
        translatedPatterns.add(pattern);
      }        
    }
    // Now escape the SQL wildcards, and make our UriPattern wildcard into the SQL wildcard.
    for (int i = 0; i < translatedPatterns.size(); i++) {
      String pattern = translatedPatterns.get(i);
      pattern = pattern.replace("%", "`%"); // used to be /
      pattern = pattern.replace("_", "`_"); // used to be /
      pattern = pattern.replace('*', '%');
      translatedPatterns.set(i, pattern);
    }

    // Now generate the return string: " AND (<like clause> OR <like clause> ... )".
    StringBuffer buff = new StringBuffer();
    buff.append(" AND (");
    if (!translatedPatterns.isEmpty()) {
      buff.append(makeLikeClause(translatedPatterns, "`")); // used to be /
    }

    buff.append(')');
    
    return buff.toString();
  }
  
  /**
   * Creates a set of LIKE clauses with the specified escape character.
   * @param patterns The patterns. 
   * @param escape The escape character.
   * @return The StringBuffer with the LIKE clauses. 
   */
  private static StringBuffer makeLikeClause(List<String> patterns, String escape) {
    StringBuffer buff = new StringBuffer(); //NOPMD generates false warning about buff size.
    if (patterns.isEmpty()) {
      return buff;
    }
    for (Iterator<String> i = patterns.iterator(); i.hasNext(); ) {
      String pattern = i.next();
      buff.append("(RESOURCE LIKE '");
      buff.append(pattern);
      buff.append("' ESCAPE '");
      buff.append(escape);
      buff.append("')");
      if (i.hasNext()) {
        buff.append(" OR ");
      }
    }
    buff.append(' ');
    return buff;
  }
  
  /**
   * Constructs a clause of form ( OWNER = 'user1' [ OR OWNER = 'user2']* ). 
   * @param users The list of users whose ownership is being searched for.
   * @return The String to be used in the where clause to check for ownership.
   */
  private String constructOwnerClause(List<User> users) {
    StringBuffer buff = new StringBuffer();
    buff.append('(');
    // Use old school iterator so we can do a hasNext() inside the loop.
    for (Iterator<User> i = users.iterator(); i.hasNext(); ) {
      User user = i.next();
      buff.append(ownerEquals);
      buff.append(user.getEmail());
      buff.append('\'');
      if (i.hasNext()) {
        buff.append(" OR");
      }
    }
    buff.append(") ");
    return buff.toString();
  }
  
  /** {@inheritDoc} */
  @Override
  public String getSensorDataIndexLastMod(User user, XMLGregorianCalendar lastModStartTime,
      XMLGregorianCalendar lastModEndTime) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    
    try {
      return client.getSensorDataIndexLastMod(user.getEmail(), lastModStartTime, lastModEndTime);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
     return null;
  }
  
  /** not implement */
  /** {@inheritDoc} */
  @Override
  public boolean hasSensorData(User user, XMLGregorianCalendar timestamp) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public void deleteSensorData(User user, XMLGregorianCalendar timestamp) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      client.deleteSensorData(user, timestamp);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /** {@inheritDoc} */
  @Override
  public void deleteSensorData(User user) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      client.deleteSensorData(user);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getSensorData(User user, XMLGregorianCalendar timestamp) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      return client.getSensorData(user, timestamp);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null; 
  }

  // ********************   Start SensorDataType specific stuff here *****************  //



  /** {@inheritDoc} */
  @Override
  public boolean storeSensorDataType(SensorDataType sdt, String xmlSensorDataType, 
      String xmlSensorDataTypeRef) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
     try {
      return client.storeSensorDataType(sdt, xmlSensorDataType, xmlSensorDataTypeRef);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public void deleteSensorDataType(String sdtName) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      client.deleteSensorDataType(sdtName);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getSensorDataTypeIndex() {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
     return client.getSensorDataTypeIndex();
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public String getSensorDataType(String sdtName) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
     return client.getSensorDataType(sdtName);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }


  /** {@inheritDoc} */
  @Override
  public void deleteUser(String email) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      client.deleteUser(email);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getUser(String email) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      return client.getUser(email);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }


  /** {@inheritDoc} */
  @Override
  public String getUserIndex() {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
     return client.getUserIndex();
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean storeUser(User user, String xmlUser, String xmlUserRef) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
     return client.storeUser(user, xmlUser, xmlUserRef);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }

  // ********************   Start Project specific stuff here *****************  //

 

  /** {@inheritDoc} */
  @Override
  public void deleteProject(User owner, String projectName) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      client.deleteProject(owner, projectName);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getProject(User owner, String projectName) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      return client.getProject(owner, projectName);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public String getProjectIndex() {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      return client.getProjectIndex();
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  /** not implement */
  /** {@inheritDoc} */
  @Override  
  public ProjectSummary getProjectSummary(List<User> users, XMLGregorianCalendar startTime, 
      XMLGregorianCalendar endTime, List<String> uriPatterns, String href) {

    SensorBaseClient client = new SensorBaseClient(host, username, password);
     
    return null;
  }

  /**
   * Creates a ProjectSummary instances from the passed data. 
   * @param href  The Href representing this resource.
   * @param startTime The startTime for this data.
   * @param endTime The endTime for this data.
   * @param sdtInstances The data structure containing the instances. 
   * @return The ProjectSummary instance. 
   */
  private ProjectSummary makeProjectSummary(String href, XMLGregorianCalendar startTime, 
      XMLGregorianCalendar endTime, Map<String, Map<String, Integer>> sdtInstances) {
    ProjectSummary projectSummary = new ProjectSummary();
    projectSummary.setHref(href);
    projectSummary.setStartTime(startTime);
    projectSummary.setEndTime(endTime);
    projectSummary.setLastMod(Tstamp.makeTimestamp());
    SensorDataSummaries summaries = new SensorDataSummaries();
    projectSummary.setSensorDataSummaries(summaries);
    int totalInstances = 0;
    for (Map.Entry<String, Map<String, Integer>> entry : sdtInstances.entrySet()) {
      String sdt = entry.getKey();
      Map<String, Integer> tool2NumInstances = entry.getValue();
      for (Map.Entry<String, Integer> entry2 : tool2NumInstances.entrySet()) {
        SensorDataSummary summary = new SensorDataSummary();
        summary.setSensorDataType(sdt);
        summary.setTool(entry2.getKey());
        int numInstances = entry2.getValue();
        totalInstances += numInstances;
        summary.setNumInstances(BigInteger.valueOf(numInstances));
        summaries.getSensorDataSummary().add(summary);
      }
    }
    summaries.setNumInstances(BigInteger.valueOf(totalInstances));
    return projectSummary;
  }
  

  /** {@inheritDoc} */
  @Override
  public boolean storeProject(Project project, String xmlProject, String xmlProjectRef) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
     return client.storeProject(project, xmlProject, xmlProjectRef);
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }
  
  // **************************** Internal helper functions *****************************
  
  /**
   * Returns a string containing the Index for the given resource indicated by resourceName.
   * @param resourceName The resource name, such as "Project". 
   * @param statement The SQL Statement to be used to retrieve the resource references.
   * @return The aggregate Index XML string. 
   */
  private String getIndex(String resourceName, String statement) {
    StringBuilder builder = new StringBuilder(512);
    builder.append("<").append(resourceName).append(indexSuffix);
    // Retrieve all the SensorData
    Connection conn = null;
    PreparedStatement s = null;
    ResultSet rs = null;
    try {
      conn = DriverManager.getConnection(connectionURL);
      s = conn.prepareStatement(statement);
      rs = s.executeQuery();
      String resourceRefColumnName = xml + resourceName + "Ref";
      while (rs.next()) {
        builder.append(rs.getString(resourceRefColumnName));
      }
    }
    catch (SQLException e) {
      this.logger.info("Derby: Error in getIndex()" + StackTrace.toString(e));
    }
    finally {
      try {
        rs.close();
        s.close();
        conn.close();
      }
      catch (SQLException e) {
        this.logger.warning(errorClosingMsg + StackTrace.toString(e));
      }
    }
    builder.append("</").append(resourceName).append(indexSuffix);
    //System.out.println(builder.toString());
    return builder.toString();
  }
  
  /**
   * Returns a string containing the Index of all of the SensorData whose runtime field matches
   * the first runtime in the result set.  Since the passed statement will retrieve sensor
   * data in the given time period ordered in descending order by runtime, this should result
   * in an index containing only  
   * @param statement The SQL Statement to be used to retrieve the resource references.
   * @return The aggregate Index XML string. 
   */
  private String getSnapshotIndex(String statement) {
    String resourceName = "SensorData";
    StringBuilder builder = new StringBuilder(512);
    builder.append("<").append(resourceName).append(indexSuffix);
    // Retrieve all the SensorData
    Connection conn = null;
    PreparedStatement s = null;
    ResultSet rs = null;
    String firstRunTime = null;
    try {
      conn = DriverManager.getConnection(connectionURL);
      s = conn.prepareStatement(statement);
      rs = s.executeQuery();
      String resourceRefColumnName = xml + resourceName + "Ref";
      boolean finished = false;
      // Add all entries with the first retrieved nruntime value to the index.
      while (rs.next() && !finished) {
        String runtime = rs.getString("Runtime");
        // Should never be null, but just in case. 
        if (runtime != null) {
          // Initial firstRunTime to the first retrieved non-null runtime value.
          if (firstRunTime == null) {
            firstRunTime = runtime;
          }
          // Now add every entry whose runtime equals the first retrieved run time.
          if (runtime.equals(firstRunTime)) {
            builder.append(rs.getString(resourceRefColumnName));
          }
          else {
            // As soon as we find a runtime not equal to firstRunTime, we can stop.
            finished = true;
          }
        }
      }
    }
    catch (SQLException e) {
      this.logger.info("Derby: Error in getIndex()" + StackTrace.toString(e));
    }
    finally {
      try {
        rs.close();
        s.close();
        conn.close();
      }
      catch (SQLException e) {
        this.logger.warning(errorClosingMsg + StackTrace.toString(e));
      }
    }
    builder.append("</").append(resourceName).append(indexSuffix);
    //System.out.println(builder.toString());
    return builder.toString();
  }
  
  /**
   * Returns a string containing the Index for the given resource indicated by resourceName, 
   * returning only the instances starting at startIndex, and with the maximum number of
   * returned instances indicated by maxInstances.   
   * @param resourceName The resource name, such as "Project".
   * @param startIndex The (zero-based) starting index for instances to be returned.
   * @param maxInstances The maximum number of instances to return.  
   * @param statement The SQL Statement to be used to retrieve the resource references.
   * @return The aggregate Index XML string. 
   */
  private String getIndex(String resourceName, String statement, int startIndex, int maxInstances) {
    StringBuilder builder = new StringBuilder(512);
    builder.append("<").append(resourceName).append(indexSuffix);
    // Retrieve all the SensorData to start.
    Connection conn = null;
    PreparedStatement s = null;
    ResultSet rs = null;
    try {
      conn = DriverManager.getConnection(connectionURL);
      s = conn.prepareStatement(statement);
      rs = s.executeQuery();
      int currIndex = 0;
      int totalInstances = 0;
      String resourceRefColumnName = xml + resourceName + "Ref";
      while (rs.next()) {
        if ((currIndex >= startIndex) && (totalInstances < maxInstances)) {
          builder.append(rs.getString(resourceRefColumnName));
          totalInstances++;
        }
        currIndex++;
      }
    }
    catch (SQLException e) {
      this.logger.info("Derby: Error in getIndex()" + StackTrace.toString(e));
    }
    finally {
      try {
        rs.close();
        s.close();
        conn.close();
      }
      catch (SQLException e) {
        this.logger.warning(errorClosingMsg + StackTrace.toString(e));
      }
    }
    builder.append("</").append(resourceName).append(indexSuffix);
    //System.out.println(builder.toString());
    return builder.toString();
  }
  
  /**
   * Returns a string containing the Resource as XML, or null if not found.
   * @param resourceName The name of the resource, such as "User".
   * @param statement The select statement used to retrieve the resultset containing a single
   * row with that resource.
   * @return The string containing the resource as an XML string.
   */
  private String getResource(String resourceName, String statement) {
    StringBuilder builder = new StringBuilder(512);
    Connection conn = null;
    PreparedStatement s = null;
    ResultSet rs = null;
    boolean hasData = false;
    try {
      conn = DriverManager.getConnection(connectionURL);
      server.getLogger().fine(executeQueryMsg + statement);
      s = conn.prepareStatement(statement);
      rs = s.executeQuery();
      String resourceXmlColumnName = xml + resourceName;
      while (rs.next()) { // the select statement must guarantee only one row is returned.
        hasData = true;
        builder.append(rs.getString(resourceXmlColumnName));
      }
    }
    catch (SQLException e) {
      this.logger.info("DB: Error in getResource()" + StackTrace.toString(e));
    }
    finally {
      try {
        rs.close();
        s.close();
        conn.close();
      }
      catch (SQLException e) {
        this.logger.warning(errorClosingMsg + StackTrace.toString(e));
      }
    }
    return (hasData) ? builder.toString() : null;
  }
  
  /**
   * Deletes the resource, given the SQL statement to perform the delete.
   * @param statement The SQL delete statement. 
   */
  private void deleteResource(String statement) {
    Connection conn = null;
    PreparedStatement s = null;
    try {
      conn = DriverManager.getConnection(connectionURL);
      server.getLogger().fine("Derby: " + statement);
      s = conn.prepareStatement(statement);
      s.executeUpdate();
    }
    catch (SQLException e) {
      this.logger.info("Derby: Error in deleteResource()" + StackTrace.toString(e));
    }
    finally {
      try {
        s.close();
        conn.close();
      }
      catch (SQLException e) {
        this.logger.warning(errorClosingMsg + StackTrace.toString(e));
      }
    }
  }

  
  /** {@inheritDoc} */
  @Override
  public boolean compressTables() {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    try {
      client.compressTables();
      return true;
    } catch (SensorBaseClientException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }
  
  /** {@inheritDoc} */
  @Override
  public boolean indexTables() {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    return true;
  }
  
  /** {@inheritDoc} */
  @Override
  public int getRowCount(String table) {
    SensorBaseClient client = new SensorBaseClient(host, username, password);
    return 1;
  }
  
  /** {@inheritDoc} */
  @Override
  public Set<String> getTableNames() {
    Set<String> tableNames = new HashSet<String>();
    tableNames.add("SensorData");
    tableNames.add("SensorDataType");
    tableNames.add("HackyUser");
    tableNames.add("Project");
    return tableNames;
  }
}
