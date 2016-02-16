/**
 * 
 */
package lastFM;

import java.net.URL;
import java.util.regex.*;
import java.util.ArrayList;
import java.io.StringWriter;
import org.w3c.dom.Document;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import objectStructures.User;

import java.io.IOException;
import org.xml.sax.SAXException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * @author Sajith Dananjaya
 *
 */
public class AccessLastFM {

	private static String accessToken ="";
	private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/?method=";
	
	
	public static void initUsers(){
		
	}


	/**
	 * 
	 * @param regPattern
	 * @param responseXML
	 * @return String ArrayList with all the matched information
	 */
	public static ArrayList<String> extractPattern(String regPattern,Document responseXML,int tagNameLength){
		ArrayList<String> matchList = new ArrayList<>();

		String tempXML = docToString(responseXML);

		Pattern searchPattern = Pattern.compile(regPattern);
		Matcher patternMatcher = searchPattern.matcher(tempXML);

		while(patternMatcher.find()){
			String item = patternMatcher.group();
			matchList.add(item.substring(tagNameLength+2, (item.length()-(tagNameLength+3))));
			//System.out.println(patternMatcher.group());
		}
		return matchList;
	}

	
	/**
	 * 
	 * @param url
	 * @return XML document
	 * 
	 */
	public static Document grabXML(URL url){
		Document responseXML = null;

		try{
			URLConnection response = url.openConnection();
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();

			responseXML = docBuilder.parse(response.getInputStream());
		}
		catch (IOException e){
			System.err.println("ERROR@grabXML_IOException");
			System.err.println(e.toString());
		} catch (ParserConfigurationException e) {
			System.err.println("ERROR@grabXML_ParserConfig");
			System.err.println(e.toString());
		} catch (SAXException e) {
			System.err.println("ERROR@grabXML_SAX");
			System.err.println(e.toString());
		}
		return responseXML;
	}	

	private static String docToString(Document doc){
		StringWriter writer = new StringWriter();

		DOMSource domSource = new DOMSource(doc);
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);
		} catch (TransformerException e) {
			System.err.println("ERROR@docToString");
			System.err.println(e.toString());
		}
		return writer.toString();
	}
	
}
