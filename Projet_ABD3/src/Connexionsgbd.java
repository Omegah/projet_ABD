
import java.util.Scanner;

import Interactions.InterfaceGestion;

import java.io.*;
import java.sql.*;

public class Connexionsgbd {
	private static final String configurationFile = "BD.properties.txt";

	public static void main(String args[]) {
		try {
			int choix = 0;
			//Variable pour savoir si on se connecte en tant qu'administrateur admin = 1 si on est admin 
			int admin = 1;
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
			
			InterfaceGestion interfaceGestion = new InterfaceGestion(conn);
			InterfaceClient interfaceClient = new InterfaceClient(conn);
			Client client = new Client(interfaceClient);
			//System.out.println("Vous etes connect�");
			
			do {
				if(!client.isConnected() && admin == 0){
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
					case 3 :
						//Creation admin avec interface client ?
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
						
						break;
					default:
						break;
					}
				}
				else if(!client.isConnected() && admin == 1){
					Menu.Administrateur();
					choix = LectureClavier.lireEntier("votre choix ? ");
					switch(choix){
					case 1 : 
						interfaceGestion.AfficherPrestataires();
						break;
					case 2 :
						String nom,adresse;
						int delai,pref;
						System.out.println("Renseignez le nom du prestataire : ");
						nom = LectureClavier.lireChaine();
						System.out.println("Renseignez l'adresse du prestataire :");
						adresse = LectureClavier.lireChaine();
						pref =LectureClavier.lireEntier("Renseignez la qualité du prestataire :");
						delai =LectureClavier.lireEntier("Renseignez le déla maximum du prestataire :");
						interfaceGestion.AjoutPrestataire(nom, adresse, pref, delai);
						break;
					case 3 : 
						String nom2;
						System.out.println("Renseignez le nom du prestataire a supprimer : ");
						nom2 = LectureClavier.lireChaine();
						interfaceGestion.supprimerPrestataire(nom2);;
						break;
					case 4 :
						String nom3;
						System.out.println("Renseignez le nom du prestataire : ");
						nom3 = LectureClavier.lireChaine();
						interfaceGestion.AfficherDispositifsFormats(nom3);
						break;
					case 5 : 
						String nom4;
						System.out.println("Renseignez le nom du prestataire: ");
						nom4 = LectureClavier.lireChaine();
						interfaceGestion.AfficherDispositifsFormats(nom4);
						break;
					case 6 : 
						String nom5;
						System.out.println("Renseignez le nom du prestataire: ");
						nom5 = LectureClavier.lireChaine();
						int idD = LectureClavier.lireEntier("Renseignez l'id du dispositif a supprimer : ");
						interfaceGestion.supprimerDispositif(idD, nom5);
						break;
					default:
						break;
				}
				}
				
			} while (choix != 99);

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
