
import java.util.Scanner;
import java.io.*;
import java.sql.*;

public class Connexionsgbd {
	private static final String configurationFile = "BD.properties.txt";

	public static void main(String args[]) {
		try {
			int choix;
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
			InterfaceClient interfaceClient = new InterfaceClient(conn);
			Client client = new Client(interfaceClient);
			System.out.println("Vous etes connect�");
			Menu.Acceuil();
			choix = LectureClavier.lireEntier("votre choix ? ");
			switch(choix){
			case 1: client.inscription();
					break;
			default :
				break;
			}
						
			//interfaceClient.CreationClient(client);
			//client.AjoutImage(interfaceClient);
			
			

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