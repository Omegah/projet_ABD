
import java.util.Scanner;
import java.io.*;
import java.sql.*;

public class Connexionsgbd {
	private static final String configurationFile = "BD.properties.txt";

	public static void main(String args[]) {
		try {
			int choix = 0;
			//Variable pour savoir si on se connecte en tant qu'administrateur admin = 1 si on est admin 
			int admin = 0;
			int i =0;
			String jdbcDriver, dbUrl, username, password;
			DatabaseAccessProperties dap = new DatabaseAccessProperties(configurationFile);
			
			jdbcDriver = dap.getJdbcDriver();
			dbUrl = dap.getDatabaseUrl();
			username = dap.getUsername();
			password = dap.getPassword();
			// Load the database driver
			Class.forName(jdbcDriver);

			Connection conn = DriverManager.getConnection(dbUrl, username, password);
			conn.setAutoCommit(false);
			
			InterfaceClient interfaceClient = new InterfaceClient(conn);
			Client client = new Client(interfaceClient);
			//System.out.println("Vous etes connect�");
			
			do {
				if(!client.isConnected()){
					Menu.Acceuil();
					choix = LectureClavier.lireEntier("votre choix ? ");
					switch (choix) {
					case 1:
						client.inscription();

						break;
					case 2 : 
						String mailC,mdp;
						System.out.println("Donner votre mail identifiant : ");
						mailC = LectureClavier.lireChaine();
						System.out.println("Donner le mot de passe : ");
						mdp = LectureClavier.lireChaine();
						client = interfaceClient.connection(mailC, mdp);
						
						while(client==null && i<3){
							System.out.println("Probleme de connexion recommencez \nDonner le mot de passe : ");
							mdp = LectureClavier.lireChaine();
							client = interfaceClient.connection(mailC, mdp);
							i++;
						}
						break;
					case 3 :
						//Creation admin avec interface client ?
						break;
					case 4 : 
						//Affichage de tous les clients 
						interfaceClient.AfficheTousClients();
						break;
					default:
						break;
					}
				}else if(client.isConnected() && admin == 0){
					Menu.Client();
					choix = LectureClavier.lireEntier("votre choix ? ");
					switch(choix){
					case 1 : 
						client.AfficheTousImages();
						break;
					case 2 :
						client.AjoutImage();
						break;
					case 3 : 
						client.AfficheTousAlbums();
						break;
					case 4 :
						client.AjoutAlbum();
						break;
					case 5 :
						client.AjoutPhotoAlbum();
						break;
					case 99 :
						client.deconnecter();
						break;
					default:
						break;
					}
				}
				
			} while (choix != 999);

			// interfaceClient.CreationClient(client);
			// client.AjoutImage(interfaceClient);

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
