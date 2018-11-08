package com.souvc.weixin.util;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

public class XMLNodeOpetation {
	public static String DelCDATAinXML(String XML_str, String node_name) {
		SAXBuilder build = new SAXBuilder();
		try {
			Document doc = build.build(new StringReader(XML_str));
			Element root = doc.getRootElement();
			Element element = root.getChild(node_name);
			System.out.println(element.getText());
			String temp = element.getText();
			String rep = "<![CDATA["+temp+"]]>";
			System.out.println(rep);
			String res = XML_str.replace(rep, temp);
			System.out.println(res);
			return res;
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
