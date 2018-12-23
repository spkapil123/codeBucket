


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadAndModifyXMLFile {

	public static final String xmlFilePath = "C:\\Users\\kapil\\Desktop\\testFile.xml";

	public static void main(String argv[]) {

		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(xmlFilePath);

			// Get employee by tag name
			//use item(0) to get the first node with tage name "employee"
			Node employee = document.getElementsByTagName("employee").item(0);

			// update employee , set the id to 10
			NamedNodeMap attribute = employee.getAttributes();
			Node nodeAttr = attribute.getNamedItem("id");
			nodeAttr.setTextContent("10");

			// append a new node to the first employee
			Element address = document.createElement("address");

			address.appendChild(document.createTextNode("34 Stanley St."));

			employee.appendChild(address);

			// loop the employee node and update salary value, and delete a node
			NodeList nodes = employee.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {

				Node element = nodes.item(i);

				if ("salary".equals(element.getNodeName())) {
					element.setTextContent("2000000");
				}

				// remove firstname
				if ("firstname".equals(element.getNodeName())) {
					employee.removeChild(element);
				}

			}

			// write the DOM object to the file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);

			StreamResult streamResult = new StreamResult(new File(xmlFilePath));
			transformer.transform(domSource, streamResult);

			System.out.println("The XML File was modified successfully!");
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}
}
