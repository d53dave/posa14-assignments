package net.d53dev.xmltest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {

		XMLParser xmlParser = new XMLParser();
		xmlParser.TryXML();
	}

	public void TryXML() throws ParserConfigurationException, SAXException,
			IOException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();

		DocumentBuilder dBuilder = builderFactory.newDocumentBuilder();

		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader) cl).getURLs();

		for (URL url : urls) {
			System.out.println(url);
		}

		InputStream strm = this.getClass().getResourceAsStream("/test.xml");
		Document document = dBuilder.parse(strm);
		document.normalize();

		NodeList rootNodes = document.getElementsByTagName("info");
		Node rootNode = rootNodes.item(0);
		Element rootElement = (Element) rootNode;
		NodeList compList = rootElement.getElementsByTagName("computer");
		for (int i = 0; i < compList.getLength(); i++) {

			Node computer = compList.item(i);
			Element compElement = (Element) computer;

			Node theIP = compElement.getElementsByTagName("ipaddress").item(0);
			Element theIpElement = (Element) theIP;

			System.out.println("The comptuer ip is : "
					+ theIpElement.getTextContent());

		}

	}
}