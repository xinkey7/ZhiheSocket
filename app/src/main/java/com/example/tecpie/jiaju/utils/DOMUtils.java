package com.example.tecpie.jiaju.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DOMUtils
{
	/**
	 * 初始化一个空Document对象返回。
	 * 
	 * @return a Document
	 */
	public static Document newXMLDocument()
	{
		try
		{
			return newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 41. * 初始化一个DocumentBuilder 42. * 43. * @return a DocumentBuilder 44. * @throws
	 * ParserConfigurationException 45.
	 */
	public static DocumentBuilder newDocumentBuilder()
			throws ParserConfigurationException
	{
		return newDocumentBuilderFactory().newDocumentBuilder();
	}

	/**
	 * 52. * 初始化一个DocumentBuilderFactory 53. * 54. * @return a
	 * DocumentBuilderFactory 55.
	 */
	public static DocumentBuilderFactory newDocumentBuilderFactory()
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf;
	}

	/**
	 * 63. * 将传入的一个XML String转换成一个org.w3c.dom.Document对象返回。 64. * 65. * @param
	 * xmlString 66. * 一个符合XML规范的字符串表达。 67. * @return a Document 68.
	 */
	public static Document parseXMLDocument(String xmlString)
	{
		if (xmlString == null)
		{
			throw new IllegalArgumentException();
		}
		try
		{
			return newDocumentBuilder().parse(
					new InputSource(new StringReader(xmlString)));
		} catch (Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 82. * 给定一个输入流，解析为一个org.w3c.dom.Document对象返回。 83. * 84. * @param input 85.
	 * * @return a org.w3c.dom.Document 86.
	 */
	public static Document parseXMLDocument(InputStream input)
	{
		if (input == null)
		{
			throw new IllegalArgumentException("参数为null！");
		}
		try
		{
			return newDocumentBuilder().parse(input);
		} catch (Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 99. * 给定一个文件名，获取该文件并解析为一个org.w3c.dom.Document对象返回。 100. * 101. * @param
	 * fileName 102. * 待解析文件的文件名 103. * @return a org.w3c.dom.Document 104.
	 */
	public static Document loadXMLDocumentFromFile(String fileName)
	{
		if (fileName == null)
		{
			throw new IllegalArgumentException("未指定文件名及其物理路径！");
		}
		try
		{
			return newDocumentBuilder().parse(new File(fileName));
		} catch (SAXException e)
		{
			throw new IllegalArgumentException("目标文件（" + fileName
					+ "）不能被正确解析为XML！" + e.getMessage());
		} catch (IOException e)
		{
			throw new IllegalArgumentException("不能获取目标文件（" + fileName + "）！"
					+ e.getMessage());
		} catch (ParserConfigurationException e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * 123. * 把dom文件转换为xml字符串 124.
	 */
	public static String toStringFromDoc(Document document)
	{
		String result = null;

		if (document != null)
		{
			StringWriter strWtr = new StringWriter();
			StreamResult strResult = new StreamResult(strWtr);
			TransformerFactory tfac = TransformerFactory.newInstance();
			try
			{
				Transformer t = tfac.newTransformer();
				t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
				// text
				t.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
				t.transform(new DOMSource(document.getDocumentElement()),
						strResult);
			} catch (Exception e)
			{
				System.err.println("XML.toString(Document): " + e);
			}
			result = strResult.getWriter().toString();
			try
			{
				strWtr.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 157. * 给定一个节点，将该节点加入新构造的Document中。 158. * 159. * @param node 160. * a
	 * Document node 161. * @return a new Document 162.
	 */

	public static Document newXMLDocument(Node node)
	{
		Document doc = newXMLDocument();
		doc.appendChild(doc.importNode(node, true));
		return doc;
	}

	/**
	 * 171. * 将传入的一个DOM Node对象输出成字符串。如果失败则返回一个空字符串""。 172. * 173. * @param node
	 * 174. * DOM Node 对象。 175. * @return a XML String from node 176.
	 */
	/*
	 * 179. * public static String toString(Node node) { if (node == null) {
	 * throw new 180. * IllegalArgumentException(); } Transformer transformer =
	 * new 181. * Transformer(); if (transformer != null) { try { StringWriter
	 * sw = new 182. * StringWriter(); transformer .transform(new
	 * DOMSource(node), new 183. * StreamResult(sw)); return sw.toString(); }
	 * catch (TransformerException 184. * te) { throw new
	 * RuntimeException(te.getMessage()); } } return ""; } 185.
	 */
	/**
	 * 188. * 将传入的一个DOM Node对象输出成字符串。如果失败则返回一个空字符串""。 189. * 190. * @param node
	 * 191. * DOM Node 对象。 192. * @return a XML String from node 193.
	 */

	/*
	 * 205. * 获取一个Transformer对象，由于使用时都做相同的初始化，所以提取出来作为公共方法。 206. * 207. *
	 * @return a Transformer encoding gb2312 208.
	 */

	public static Transformer newTransformer()
	{
		try
		{
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			Properties properties = transformer.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "gb2312");
			properties.setProperty(OutputKeys.METHOD, "xml");
			properties.setProperty(OutputKeys.VERSION, "1.0");
			properties.setProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperties(properties);
			return transformer;
		} catch (TransformerConfigurationException tce)
		{
			throw new RuntimeException(tce.getMessage());
		}
	}

	/**
	 * 227. * 返回一段XML表述的错误信息。提示信息的TITLE为：系统错误。之所以使用字符串拼装，主要是这样做一般 不会有异常出现。 228.
	 * * 229. * @param errMsg 230. * 提示错误信息 231. * @return a XML String show err
	 * msg 232.
	 */
	/*
	 * 234. * public static String errXMLString(String errMsg) { StringBuffer
	 * msg = new 235. * StringBuffer(100); 236. *
	 * msg.append("<?xml version="1.0" encoding="gb2312" ?>"); 237. *
	 * msg.append("<errNode title="系统错误" errMsg="" + errMsg + ""/>"); return
	 * 238. * msg.toString(); } 239.
	 */
	/**
	 * 241. * 返回一段XML表述的错误信息。提示信息的TITLE为：系统错误 242. * 243. * @param errMsg 244. *
	 * 提示错误信息 245. * @param errClass 246. * 抛出该错误的类，用于提取错误来源信息。 247. * @return a
	 * XML String show err msg 248.
	 */
	/*
	 * 250. * public static String errXMLString(String errMsg, Class errClass) {
	 * 251. * StringBuffer msg = new StringBuffer(100); 252. *
	 * msg.append("<?xml version='1.0' encoding='gb2312' ?>"); 253. *
	 * msg.append("<errNode title=" 254. *
	 * 系统错误" errMsg=""+ errMsg + "" errSource=""+ errClass.getName()+ ""/>");
	 * 255. * 　return msg.toString(); } 256.
	 */
	/**
	 * 258. * 返回一段XML表述的错误信息。 259. * 260. * @param title 261. * 提示的title 262. * @param
	 * errMsg 263. * 提示错误信息 264. * @param errClass 265. * 抛出该错误的类，用于提取错误来源信息。
	 * 266. * @return a XML String show err msg 267.
	 */

	public static String errXMLString(String title, String errMsg,
			Class errClass)
	{
		StringBuffer msg = new StringBuffer(100);
		msg.append("<?xml version='1.0' encoding='utf-8' ?>");
		msg.append("<errNode title=" + title + "errMsg=" + errMsg
				+ "errSource=" + errClass.getName() + "/>");
		return msg.toString();
	}

}
