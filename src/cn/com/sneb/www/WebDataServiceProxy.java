package cn.com.sneb.www;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;

public class WebDataServiceProxy implements cn.com.sneb.www.WebDataService {
  private String _endpoint = null;
  private cn.com.sneb.www.WebDataService webDataService = null;

  public WebDataServiceProxy() {
    _initWebDataServiceProxy();
  }

  public WebDataServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebDataServiceProxy();
  }

  private void _initWebDataServiceProxy() {
    try {

      webDataService = (new cn.com.sneb.www.WebDataServiceDelegateLocator()).getWebDataServicePort();

      if (webDataService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub) webDataService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String) ((javax.xml.rpc.Stub) webDataService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    } catch (javax.xml.rpc.ServiceException serviceException) {
    }
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webDataService != null)
      ((javax.xml.rpc.Stub) webDataService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public cn.com.sneb.www.WebDataService getWebDataService() {
    if (webDataService == null)
      _initWebDataServiceProxy();
    return webDataService;
  }

  public java.lang.String getEmpCertno(java.lang.String unitId, java.lang.String certno) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException {
    if (webDataService == null)
      _initWebDataServiceProxy();
    return webDataService.getEmpCertno(unitId, certno);
  }

  public java.lang.String getEmpInfos(java.lang.String unitId, java.lang.String employeeId, java.lang.String eaid, java.lang.String id)
      throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException {
    if (webDataService == null)
      _initWebDataServiceProxy();
    return webDataService.getEmpInfos(unitId, employeeId, eaid, id);
  }

  public java.lang.String getEmpCode(java.lang.String unitId, java.lang.String year) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException {
    if (webDataService == null)
      _initWebDataServiceProxy();
    return webDataService.getEmpCode(unitId, year);
  }

  public java.lang.String getVersionInfo(java.lang.String appName, java.lang.String jsonParams) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException {
    if (webDataService == null)
      _initWebDataServiceProxy();
    return webDataService.getVersionInfo(appName, jsonParams);
  }

  public java.lang.String getUpdateInfo(java.lang.String appName, java.lang.String jsonParams) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException {
    if (webDataService == null)
      _initWebDataServiceProxy();
    return webDataService.getUpdateInfo(appName, jsonParams);
  }

  public byte[] getAttachment(java.lang.String targetpath, java.lang.String targetfile) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException {
    if (webDataService == null)
      _initWebDataServiceProxy();
    return webDataService.getAttachment(targetpath, targetfile);
  }

  public java.lang.String validateRepeat(java.lang.String unitId, java.lang.String certno, java.lang.String certtype)
      throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException {
    if (webDataService == null)
      _initWebDataServiceProxy();
    return webDataService.validateRepeat(unitId, certno, certtype);
  }
  
  public static void printCSV(File file, DataTable dt) throws IOException {
    FileWriter out = new FileWriter(file);
    
    Set<String> headers = new LinkedHashSet<String>();
    for (Map<String, String> row: dt.getRows()) {
      headers.addAll(row.keySet());
    }
    
    for (String h: headers) {
      out.write(h);
      out.write(",");
    }
    out.write("other\n");
    
    for (Map<String, String> row: dt.getRows()) {
      for (String h: headers) {
        out.write('"');
        out.write(row.get(h));
        out.write('"');
        out.write(",");
      }
      out.write("\"\"\n");
    }
    
    out.flush();
    out.close();
  }

  public static void main(String[] args) throws Exception {
    DataTable persons = getData("hr_emp_basicinfo", "0");
    DataTable personOrg = getData("hr_work_postworkinfo", "0");
    
    persons.mergeColumn(personOrg, "empid", "empid");
    printCSV(new File("/Users/zhaodonglu/Temp/person.csv"), persons);
    
    DataTable org = getData("waf_ac_organ", "0");
    DataTable orgRel = getData("waf_ac_organ2biz", "0");
    
    org.mergeColumn(orgRel, "oid", "oid");
    printCSV(new File("/Users/zhaodonglu/Temp/org.csv"), org);
    
    System.out.println(persons);

  }

  private static DataTable getData(String entityType, String version)
      throws SOAPException, ServiceException, RemoteException, cn.com.sneb.www.SOAPException, Exception {
    SOAPHeaderElement oHeaderElement;
    SOAPElement oElement;

    oHeaderElement = new SOAPHeaderElement("http://www.sneb.com.cn", "SecurityInfo");
    oHeaderElement.setPrefix("wsse");
    oHeaderElement.setMustUnderstand(false);

    oElement = oHeaderElement.addChildElement("User");
    oElement.addTextNode("DEMO");
    oElement = oHeaderElement.addChildElement("Token");
    oElement.addTextNode("zGvgOj==");

    // You can create client code something like this..
    WebDataServiceDelegateLocator service = new WebDataServiceDelegateLocator();
    service.setWebDataServicePortEndpointAddress("http://219.141.246.100:8081/WebServices/WebDataService");
    WebDataService serv = service.getWebDataServicePort();
    WebDataServicePortBindingStub stub = (WebDataServicePortBindingStub) serv;
    stub.setHeader(oHeaderElement);

    // WebDataService service = new
    // WebDataServiceProxy("http://219.141.246.100:8081/WebServices/WebDataService");
    DataTable finalResult = new DataTable();
    DataTable dt = new DataTable();
    do {
      if (dt != null) {
        version = dt.getVersion();
      }
      System.out.println("Fetch [" + entityType +  "] version: " + version);
      String result = serv.getUpdateInfo("DEMO", "{Data:[{DataType:'" + entityType + "',oid:'',Version:'" + version + "'}]}");
      dt = DataTable.parse(result);
      if (dt.getRows().size() > 0) {
        finalResult.merge(dt);
      }
      System.out.println("Total [" + entityType +  "] records: " + finalResult.getRows().size());
    } while (dt.getRows().size() > 0);
    System.out.println("Final total [" + entityType +  "] records: " + finalResult.getRows().size());
    return finalResult;
  }

}