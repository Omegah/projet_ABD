
import java.util.Scanner;
import java.io.*;
import java.sql.*;

public class Connexionsgbd {
	private static final String configurationFile = "BD.properties.txt";

	public static void main(String args[]) {
		try {
			String jdbcDriver, dbUrl, username, password;
			DatabaseAccessProperties dap = new DatabaseAccessProperties(
					configurationFile);
			jdbcDriver = dap.getJdbcDriver();
			dbUrl = dap.getDatabaseUrl();
			username = dap.getUsername();
			password = dap.getPassword();
			// Load the database driver
			Class.forName(jdbcDriver);

			Connection conn = DriverManager.getConnection(dbUrl, username,
					password);
			conn.setAutoCommit(false);
			System.out.println("Vous etes connect�");
			
			InterfaceClient interfaceClient = new InterfaceClient(conn);
			
			Client client = new Client("Michelle", "Mimicha", "Michel22@msn.fr", "un endroit dans le monde", "1234mdp");
			
			interfaceClient.CreationClient(client);
			client.AjoutImage(interfaceClient);
			
			

			SQLWarningsException.printWarnings(conn);
			conn.close();
		} catch (SQLException se) {

			SQLWarningsException.printExceptions(se);
			return;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
}