package ssdsd;

import java.io.File; 
import java.io.FileWriter; 
import java.io.Writer; 
import java.util.Iterator; 
import java.util.LinkedHashMap; 
import java.util.List; 
import java.util.Map; 
import java.util.Set; 
import java.util.Stack; 

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.transform.Transformer; 
import javax.xml.transform.TransformerFactory; 
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult; 

//import org.dom4j.Element; 
//import org.dom4j.io.OutputFormat; 
//import org.dom4j.io.SAXReader; 
//import org.dom4j.io.XMLWriter; 
import org.w3c.dom.Document; 
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 
import org.xml.sax.Attributes; 
import org.xml.sax.SAXException; 
import org.xml.sax.helpers.DefaultHandler; 

 
public class HandleXML { 

/** 
  * 采用DOM方式 
  *    
  */ 
public class DOMForXml{ 
    
  public Document getDocument(String fileName){ 
   Document document = null; 
   try{ 
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
    DocumentBuilder builder = factory.newDocumentBuilder(); 
    document = builder.parse(new File(fileName)); 
    
   }catch(Exception e){ 
    e.printStackTrace(); 
   } 
   
   return document; 
  } 
 
  public void modifyFile(Document doc,String distFileName){ 
   try{ 
    TransformerFactory tf = TransformerFactory.newInstance(); 
    Transformer tfer = tf.newTransformer(); 
    DOMSource dsource = new DOMSource(doc); 
    StreamResult sr = new StreamResult(new File("tel.xml")); 
    tfer.transform(dsource, sr); 
   }catch(Exception e){ 
    e.printStackTrace(); 
   } 
   
  } 
  /** 
   * 查询
   *  
   */ 
  public String paseXml(String fileName){ 
	
   Document document = getDocument(fileName); 
   NodeList nodeList = document.getElementsByTagName("student"); 
   String s="";
   for(int i=0;i<nodeList.getLength();i++){ 
    StringBuilder sb = new StringBuilder(); 
    sb.append("姓名："+document.getElementsByTagName("name").item(i).getFirstChild().getNodeValue()); 
    sb.append(" , "); 
     sb.append("电话："+document.getElementsByTagName("tel").item(i).getFirstChild().getNodeValue()); 
    sb.append(" , "); 
    sb.append("地址："+document.getElementsByTagName("address").item(i).getFirstChild().getNodeValue()); 
     s =s+"\n"+sb.toString();
    
   // System.out.println(sb.toString()); 
   } 
   return s;
  } 
  /** 
   * 创建 
   *  
   */ 
  public void addNewNode(String name, String tel, String address ){ 
   try{ 
    Document document = getDocument("tel.xml"); 
    NodeList nodeList = document.getElementsByTagName("tels"); 
    //创建新的节点 
    Node studentNode = document.createElement("student"); 
    Node nameNode = document.createElement("name"); 
    nameNode.appendChild(document.createTextNode(name)); 
    Node telNode = document.createElement("tel"); 
   telNode.appendChild(document.createTextNode(tel)); 
     Node addressNode = document.createElement("address"); 
    addressNode.appendChild(document.createTextNode(address)); 
    
    // 添加节点 
    studentNode.appendChild(nameNode);
    studentNode.appendChild(telNode);
   
     studentNode.appendChild(addressNode); 
    nodeList.item(0).appendChild(studentNode); 
    // 此时真正的处理将新数据添加到文件中（磁盘） 
    TransformerFactory tf = TransformerFactory.newInstance(); 
    Transformer tfer = tf.newTransformer(); 
    DOMSource dsource = new DOMSource(document); 
    StreamResult sr = new StreamResult(new File("tel.xml")); 
    tfer.transform(dsource, sr); 
   }catch(Exception e){ 
    e.printStackTrace(); 
   } 
   
   paseXml("tel.xml"); 
  } 
  /** 
   * 删除一个节点 
   * 
   */ 
  public void deleteNode(String name){ 
   Document document = getDocument("tel.xml"); 
   
   NodeList nodeList = document.getElementsByTagName("name"); 
   for(int i=0;i<nodeList.getLength();i++){ 
    String value = nodeList.item(i).getFirstChild().getTextContent(); 
    if(name!=null && name.equalsIgnoreCase(value)){ 
     Node parentNode = nodeList.item(i).getParentNode(); 
     document.getFirstChild().removeChild(parentNode); 
    } 
   } 
   modifyFile(document,"tel.xml"); 
  } 
  /** 
   * 根据name修改某个节点的内容 
   *   
   */ 
  public void updateNode(String name, String tel, String address ){ 
          
   Document document = getDocument("tel.xml"); 
   NodeList nodeList = document.getElementsByTagName("name"); 
   for(int i=0;i<nodeList.getLength();i++){ 
    String value = nodeList.item(i).getFirstChild().getTextContent(); 
    if(name!=null && name.equalsIgnoreCase(value)){ 
     Node parentNode = nodeList.item(i).getParentNode(); 
     NodeList nl = parentNode.getChildNodes(); 
     for(int j=0;j<nl.getLength();j++){ 
      String modifyNode= nl.item(j).getNodeName(); 
      if(modifyNode.equalsIgnoreCase("name")){ 
       nl.item(j).getFirstChild().setTextContent(name); 
      } 
      if(modifyNode.equalsIgnoreCase("tel")){ 
          nl.item(j).getFirstChild().setTextContent(tel); 
         } 
      
      if(modifyNode.equalsIgnoreCase("address")){ 
          nl.item(j).getFirstChild().setTextContent(address); 
     
     } 
    } 
   } 
   modifyFile(document,"tel.xml"); 
  } 
}

 
}
 

}