package com.homeene.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jUtils {

	 public static Map<String, String> readStringXmlOut(String xml )  {
	        Map map = new HashMap<String,String>();
	        Document doc = null;
	        try {
	            // 将字符串转为XML
	            doc = DocumentHelper.parseText(xml);
	            // 获取根节点
	            Element rootElt = doc.getRootElement();
	            // 拿到根节点的名称
	            System.out.println("根节点：" + rootElt.getName());
	            Iterator iter = rootElt.elementIterator();
	            // 遍历head节点
	            while (iter.hasNext()) {
	            	Element recordEle = (Element) iter.next(); 
	                map.put(recordEle.getName(), recordEle.getData().toString());
	            }
	        } catch (DocumentException e ) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return map;
	    }
}
