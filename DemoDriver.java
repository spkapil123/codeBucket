

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DemoDriver {
	public static void main(String[] args){
	try{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hdatabase?useSSL=false","root", "spkapil123");
	//con.createStatement().executeUpdate("insert into person values('kapil0', 248)");
	//jdbc:mysql://127.0.0.1:3306/?user=root
	//con.commit();
	ResultSet name=con.createStatement().executeQuery("select age from person where name='kapil';");
	
	if(name.next()){
		System.out.print("Age is:        "+name.getString(1));
	}
	for(int i=0;i<=900000;i++){
	System.out.print(" \r");
	System.out.print(i+"  files processed.");
	}
	System.out.println("Done successsfully!!!!!");
	con.close();
	}catch (Exception e){
		e.printStackTrace();
	}
	}
}
