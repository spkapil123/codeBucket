

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.xml.stream.*;

public class xmlStaxParsing{

    public static void main(String argv[]) throws Exception {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream("C:\\Users\\kapil\\Desktop\\testFile.xml");
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
        streamReader.nextTag(); // Advance to "book" element
        streamReader.nextTag(); // Advance to "person" element

        int persons = 0;
        String email="spkapil";
        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
                case "firstname": {
                    System.out.print("First Name : ");
                    System.out.println(streamReader.getElementText());
                    break;
                }
                case "lastname": {
                    System.out.print("Last Name : ");
                    //System.out.println(streamReader.getElementText());
                    email=streamReader.getElementText();
                    break;
                }
                case "email": {
                    System.out.print("Age : ");
                    System.out.println(streamReader.getElementText());
                    break;
                }
                case "salary" : {
                    persons ++;
                }
                }
            }
            streamReader.next();
        }
        System.out.print(persons);
        System.out.println(" persons");
        
        System.out.println(java.time.LocalTime.now());
        Date startTime=Calendar.getInstance().getTime();
        
        for(int i=0;i<3000;i++){
        	
        	String line;
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\kapil\\Desktop\\testResult.txt"));
        try {
            //StringBuilder sb = new StringBuilder();
            line = br.readLine();
            String newline=line.replace("UBS9861",email+i);
            if(newline.isEmpty()||newline==null){
            	
            }else{
            	line=newline;
            }
            /*while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }*/
            
        } finally {
            br.close();
        }
        BufferedWriter bufferedWriter= new BufferedWriter(new FileWriter("C:\\Users\\kapil\\Desktop\\testResult.txt",true));
        System.out.println("line : "+line);
        bufferedWriter.newLine();
        bufferedWriter.append(line);
        bufferedWriter.flush();
        bufferedWriter.close();
        /*FileWriter fileWriter=new FileWriter("C:\\Users\\kapil\\Desktop\\testResult.txt",true);
        System.out.println("line : "+line);
        fileWriter.write(line);
        fileWriter.flush();
        fileWriter.close();*/
        }
        System.out.println(java.time.LocalTime.now());
        Date endTime=Calendar.getInstance().getTime();
        System.out.println((startTime.getTime()-endTime.getTime())/1000);
       
        
    }

}
