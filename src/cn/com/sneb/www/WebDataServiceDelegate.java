/**
 * WebDataServiceDelegate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.sneb.www;

public interface WebDataServiceDelegate extends javax.xml.rpc.Service {
    public java.lang.String getWebDataServicePortAddress();

    public cn.com.sneb.www.WebDataService getWebDataServicePort() throws javax.xml.rpc.ServiceException;

    public cn.com.sneb.www.WebDataService getWebDataServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
