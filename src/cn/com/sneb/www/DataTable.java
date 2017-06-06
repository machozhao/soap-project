package cn.com.sneb.www;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataTable {

  private String version = "0";
  private String name;
  private int rowcount;

  private List<Map<String, String>> rows = new ArrayList<Map<String, String>>();

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRowcount() {
    return this.rows.size();
  }

  public void setRowcount(int rowcount) {
    this.rowcount = rowcount;
  }

  public List<Map<String, String>> getRows() {
    return rows;
  }

  public void setRows(List<Map<String, String>> rows) {
    this.rows = rows;
  }

  public static DataTable parse(String xml) throws Exception {
    DataTable dt = new DataTable();

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes("GBK")));
    
    NodeList verionNodeList = doc.getElementsByTagName("datatable");
    Node versionNode = verionNodeList.item(0);
    String version = ((Element) versionNode).getAttribute("version");
    dt.setVersion(version);
    String name = ((Element) versionNode).getAttribute("name");
    dt.setName(name);
    
    NodeList nList = doc.getElementsByTagName("item");

    for (int temp = 0; temp < nList.getLength(); temp++) {
      Node nNode = nList.item(temp);
      if (nNode.getNodeType() == Node.ELEMENT_NODE) {

        Element eElement = (Element) nNode;
        NamedNodeMap attributes = eElement.getAttributes();
        Map<String, String> row = new LinkedHashMap<String, String>();
        for (int j = 0; j < attributes.getLength(); j++) {
          String attrName = attributes.item(j).getNodeName();
          String attrValue = attributes.item(j).getNodeValue();
          row.put(attrName, attrValue);
        }
        dt.addRow(row);
      }
    }
    return dt;
  }

  private void addRow(Map<String, String> row) {
    this.rows.add(row);
  }
  
  public void merge(DataTable dt) {
    this.rows.addAll(dt.getRows());
    this.version = dt.getVersion();
  }
  
  public void mergeColumn(DataTable dest, String srcCol, String destCol) {
    for (Map<String, String> destRow: dest.getRows()) {
      for (Map<String, String> srcRow: this.rows) {
        if (srcRow.get(srcCol) != null && srcRow.get(srcCol).equals(destRow.get(destCol))) {
          srcRow.putAll(destRow);
          break;
        }
      }
    }
  }

  @Override
  public String toString() {
    return "DataTable [version=" + version + ", name=" + name + ", rowcount=" + rowcount + "]";
  }

  
}
