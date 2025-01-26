package customer.connection; // Make sure this matches your package declaration

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AzureSqlDatabaseConnection {
	static Connection con;
	//define and initialize database driver
	private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//define and initialize database url
	private static final String DB_CONNECTION = "jdbc:sqlserver://nikkospace.database.windows.net:1433;database=Nikko Space;user=nikko@nikkospace;password=Muhammadyazid01!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	//define and initialize database user
	private static final String DB_USER = "nikko@nikkospace";
	//define and initialize database password
	private static final String DB_PASSWORD = "Muhammadyazid01!";
	
	public static Connection getConnection() {
	
		try {
			//1. load the driver
			Class.forName(DB_DRIVER);
			
			try {
				//2. create connection
				con = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
				System.out.println("Connected");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}		
		return con;
    }
}
