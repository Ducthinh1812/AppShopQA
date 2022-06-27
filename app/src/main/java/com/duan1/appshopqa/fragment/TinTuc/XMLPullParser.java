package com.duan1.appshopqa.fragment.TinTuc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOError;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLPullParser {
    public Document getDocument(String xlm) throws IOError, SAXException, IOError, IOException {
        Document document=null;
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=null;
        try {
            builder=factory.newDocumentBuilder();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }
        InputSource inputSource=new InputSource();
        inputSource.setCharacterStream(new StringReader(xlm));
        inputSource.setEncoding("UTF-8");
        document = builder.parse(inputSource);
        return document;
    }
    public String getValue(Element node, String name){
        NodeList nodeList=node.getElementsByTagName(name);
        String Kq=getTextNodeValue(nodeList.item(0));
        return Kq;
    }
    public  String getTextNodeValue(Node no){
        Node child;
        if (no!=null){
            if (no.hasChildNodes())
            {
                for (child=no.getFirstChild();child!=null;child=child.getNextSibling())
                {
                    if (child.getNodeType()==Node.TEXT_NODE)
                    {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";

    }
}
