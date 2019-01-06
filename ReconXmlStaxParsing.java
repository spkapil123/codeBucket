

import java.io.FileOutputStream;
import java.io.FileReader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ReconXmlStaxParsing{
	public static final String xmlInputFilePath = "C:\\Users\\kapil\\Desktop\\recon_xml.xml";
	
	public static final String xmlOutputFilePath = "C:\\Users\\kapil\\Desktop\\recon_output_xml.xml";

    public static void main(String argv[]) throws Exception {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        //XMLStreamReader streamReader = inputFactory.createXMLStreamReader(new FileInputStream(xmlInputFilePath));
        XMLEventReader eventReader=inputFactory.createXMLEventReader(new FileReader(xmlInputFilePath));
        
        XMLOutputFactory outputFactory      = XMLOutputFactory.newInstance();
        //XMLStreamWriter streamWriter = factory.createXMLStreamWriter(new FileWriter("C:\\Users\\kapil\\Desktop\\recon_xml.xml",true));
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(xmlOutputFilePath),"UTF-8");
        XMLEventFactory eventFactory=XMLEventFactory.newInstance();
        //XMLStreamWriter writer = new IndentingXMLStreamWriter(xmlof.createXMLStreamWriter(out));
        //streamWriter = new IndentingXMLStreamWriter(writer);
        
		System.out.println("           ---       ---");
		System.out.println("                 |");
		System.out.println("                 |");
		System.out.println("               -   -");
		System.out.println("                 V");
		System.out.println("\nYou Have Started the automation!!! Sit Tight and Enjoy :-)\n");
		
		/*setting Defaults*/
		String featureTag="Default feature!!";
		String enrichmentTag="Default enrichment!!";
		String keyTag="default key";
		String feedName="Not yet Known";
		int flag=0;
		
        while (eventReader.hasNext()) {
        	XMLEvent event = eventReader.nextEvent();
        	//System.out.println("Event type :"+event.getEventType()+event.isProcessingInstruction()+event.isStartDocument());
        	
        	/*Setting feed specific values*/
        	
        	if(event.isStartElement() && event.asStartElement().getAttributes().hasNext() && "feed1".equals(event.asStartElement().getAttributeByName(new QName("name")).getValue())){
        		feedName="feed1";
        		featureTag="I am in feed 1 feature!!!";
        		enrichmentTag="enrich1";
        		keyTag="you are in feed 1 key!!";
        	}else if(event.isStartElement() && event.asStartElement().getAttributes().hasNext() && "feed2".equals(event.asStartElement().getAttributeByName(new QName("name")).getValue())){
        		feedName="feed2";
        		featureTag="I am in feed 2 feature!!!";
        		enrichmentTag="you are in feed 2 enrichment!!";
        		keyTag="you are in feed 2 key!!";
        	}else if(event.isStartElement() && event.asStartElement().getAttributes().hasNext() && "feed3".equals(event.asStartElement().getAttributeByName(new QName("name")).getValue())){
        		feedName="feed3";
        		featureTag="I am in feed 3 feature!!!";
        		enrichmentTag="you are in feed 3 enrichment!!";
        		keyTag="you are in feed 3 key!!";
        	}
        	
        	
        	/*Specifying Document version and encoding*/
        	
        	if(event.isStartDocument()){
        		eventWriter.add(eventFactory.createStartDocument("UTF-8", "1.0"));
        	}
        	else if(event.isEndElement()){
        		
        		/*This is for BusinessKeys*/
        		
        		if(event.asEndElement().getName().toString().equals("tag2")){
        			
        			Characters characters=eventFactory.createCharacters(featureTag);
        			StartElement startElement=eventFactory.createStartElement("", null, "feature");
        			EndElement endElement=eventFactory.createEndElement("", null, "feature");
        			eventWriter.add(startElement);
        			eventWriter.add(characters);
        			eventWriter.add(endElement);
        			eventWriter.add(event);
        			flag++;
        		}
        		
        		/*This is for Default value enrichment*/
        		
        		else if(event.asEndElement().getName().toString().equals("enrichments")){
        			
        			Characters characters=eventFactory.createCharacters(enrichmentTag);
        			StartElement startElement=eventFactory.createStartElement("", null, "enrichment");
        			EndElement endElement=eventFactory.createEndElement("", null, "enrichment");
        			eventWriter.add(startElement);
        			eventWriter.add(characters);
        			eventWriter.add(endElement);
        			eventWriter.add(event);
        			flag++;
        		}
        		else{
        			eventWriter.add(event);
        		}
        		eventWriter.flush();
        		
        		/*This is for InfutForMatch Ref*/
        		
        	}else if(event.isStartElement()){
        		if(event.asStartElement().getName().toString().equals("key")){
        			eventWriter.add(event);
        			eventWriter.flush();
        			XMLEvent charEvent = eventReader.nextEvent();
        			if(charEvent.isCharacters()){
        				String keyFields=charEvent.asCharacters().getData();
        				keyFields=keyFields.substring(10,keyFields.length()-1).concat("+\"#\"+"+keyTag);
        				keyFields="Obfuscate("+keyFields+")";
        				Characters characters=eventFactory.createCharacters(keyFields);
        				eventWriter.add(characters);
        				flag++;
        			}else{
        				eventWriter.add(charEvent);
        			}
        		}else{
    				eventWriter.add(event);
    			}
        		eventWriter.flush();
    		}
        	else{
        		eventWriter.add(event);
        	}
        	eventWriter.flush();
        	if(flag==3){
        		System.out.println("\n*********************************\n");
        		System.out.println("The updated values of "+feedName);
        		System.out.println("Business Key added : "+featureTag);
        		System.out.println("Enrichemnt added : "+enrichmentTag);
        		System.out.println("Value updated in input matchref: "+keyTag);
        		flag=0;
        	}
        }
        eventReader.close();
        eventWriter.close();
        System.out.println("\n Automation Process completed successfully !!!!");
    }
}
