/**
 * WebDataService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.sneb.www;

public interface WebDataService extends java.rmi.Remote {
    public java.lang.String getEmpCertno(java.lang.String unitId, java.lang.String certno) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException;
    public java.lang.String getEmpInfos(java.lang.String unitId, java.lang.String employeeId, java.lang.String eaid, java.lang.String id) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException;
    public java.lang.String getEmpCode(java.lang.String unitId, java.lang.String year) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException;
    public java.lang.String getVersionInfo(java.lang.String appName, java.lang.String jsonParams) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException;
    public java.lang.String getUpdateInfo(java.lang.String appName, java.lang.String jsonParams) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException;
    public byte[] getAttachment(java.lang.String targetpath, java.lang.String targetfile) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException;
    public java.lang.String validateRepeat(java.lang.String unitId, java.lang.String certno, java.lang.String certtype) throws java.rmi.RemoteException, cn.com.sneb.www.SOAPException;
}
