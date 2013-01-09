package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Execute_test {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://10.20.2.59:3306/trac", "sami", "bug");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
