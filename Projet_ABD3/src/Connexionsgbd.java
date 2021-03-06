import java.util.Scanner;

import Interactions.InterfaceGestion;

import java.io.*;
import java.sql.*;

public class Connexionsgbd {
	private static final String configurationFile = "BD.properties.txt";

	public static void main(String args[]) {
		try {
			int choix = 0;
			// Variable pour savoir si on se connecte en tant qu'administrateur
			// admin = 1 si on est admin

			int admin = 0;
			int i = 0;

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

			InterfaceGestion interfaceGestion = new InterfaceGestion(conn);
			InterfaceClient interfaceClient = new InterfaceClient(conn);
			Client client = new Client(interfaceClient);
			// System.out.println("Vous etes connect�");

			do {
				if (!client.isConnected() && admin == 0) {
					Menu.Acceuil();
					choix = LectureClavier.lireEntier("votre choix ? ");
					switch (choix) {
					case 1:
						client.inscription();

						break;
					case 2:
						String mailC,
						mdp;
						System.out.println("Donner votre mail identifiant : ");
						mailC = LectureClavier.lireChaine();
						System.out.println("Donner le mot de passe : ");
						mdp = LectureClavier.lireChaine();
						client = interfaceClient.connection(mailC, mdp);

						while (client == null && i < 3) {
							System.out
									.println("Probleme de connexion recommencez \nDonner le mot de passe : ");
							mdp = LectureClavier.lireChaine();
							client = interfaceClient.connection(mailC, mdp);
							i++;
						}
						break;
					case 3:
						// Creation admin avec interface client ?

						admin =1;

						break;
					case 4:
						// Affichage de tous les clients
						interfaceClient.AfficheTousClients();
						break;
					default:
						break;
					}
				} else if (client.isConnected() && admin == 0) {
					Menu.Client();
					choix = LectureClavier.lireEntier("votre choix ? ");
					switch (choix) {
					case 1:
						client.AfficheTousImages();
						break;
					case 2:
						client.AjoutImage();
						break;
					case 3:
						client.AfficheTousAlbums();
						break;
					case 4:
						client.AjoutAlbum();
						break;

					case 5:
						client.AjoutPhotoAlbum();
						break;
					case 6:
						client.AfficherTousCommande();
						break;
					case 7:
						client.CreationCommande();
						break;

					case 8:
						client.AjouterLot();
						break;
					case 9:
						client.PartageImage();
						break;
					case 10 :
						client.AfficheImgAlbum();
						break;
					case 11 : 
						client.SupprimerImage();
						break;
					case 12 :
						//client.SupprimerAlbum();
						break;
					case 13:
						client.SupprimerPhotoAlbum();
						break;
					case 14:
						client.PayerCommande();
						break;
					case 15:
						client.AfficherLotsCommande();
						break;
					case 99 :
						client.deconnecter();
						break;
					default:
						break;
					}
				} else if (!client.isConnected() && admin == 1) {
					Menu.Administrateur();
					choix = LectureClavier.lireEntier("votre choix ? ");
					switch (choix) {
					case 1: // 1- Afficher les prestataires
						interfaceGestion.AfficherPrestataires();
						break;
					case 2: // 2- Ajouter un prestataire
						String nom,
						adresse;
						int delai,
						pref;
						System.out
								.println("Renseignez le nom du prestataire : ");
						nom = LectureClavier.lireChaine();
						System.out
								.println("Renseignez l'adresse du prestataire :");
						adresse = LectureClavier.lireChaine();
						pref = LectureClavier
								.lireEntier("Renseignez la qualité du prestataire :");
						delai = LectureClavier
								.lireEntier("Renseignez le déla maximum du prestataire :");
						interfaceGestion.AjoutPrestataire(nom, adresse, pref,
								delai);
						break;
					case 3:// Supprimer un prestataire
						String nom2;
						System.out
								.println("Renseignez le nom du prestataire a supprimer : ");
						nom2 = LectureClavier.lireChaine();
						interfaceGestion.supprimerPrestataire(nom2);
						;
						break;
					case 4: // Afficher les dispositifs/formats d'un prestataire
						String nom3;
						System.out
								.println("Renseignez le nom du prestataire : ");
						nom3 = LectureClavier.lireChaine();
						interfaceGestion.AfficherDispositifsFormats(nom3);
						break;
					case 5: // 5- Ajouter un dispositif pour un prestataire
						String nom4;
						System.out
								.println("Renseignez le nom du prestataire: ");
						nom4 = LectureClavier.lireChaine();
						interfaceGestion.AjoutDispositif(nom4);
						break;
					case 6: // Supprimer un dispositif pour un prestataire
						int idD = LectureClavier
								.lireEntier("Renseignez l'id du dispositif a supprimer : ");
						interfaceGestion.supprimerDispositif(idD);
						break;
					case 7: // 7- Ajouter un format pour un dispositif
						String nom6;
						System.out
								.println("Renseignez le nom du prestataire : ");
						nom6 = LectureClavier.lireChaine();
						int idD2 = LectureClavier
								.lireEntier("Renseignez l'id du dispositif : ");
						int idF = LectureClavier
								.lireEntier("Renseignez l'id du format : ");
						int stock = LectureClavier
								.lireEntier("Renseignez le stock pour ce format : ");
						float prixU = LectureClavier
								.lireFloat("Renseignez  le prix Unitaire pour ce format : ");
						int tirageJour = LectureClavier
								.lireEntier("Renseignez le tirage maximum par jour pour ce format/dispositif : ");

						 interfaceGestion.AjoutFormatSociete(idD2,idF, stock,prixU, tirageJour, nom6);
						break;
					case 8: // 8 - Ajouter un Format
						System.out.println("Renseignez la taille du format : ");
						String taille = LectureClavier.lireChaine();
						int nbPixel = LectureClavier
								.lireEntier("Renseignez  le nombre de pixel du format : ");
						System.out.println("Renseignez le nom du format : ");
						String libelle = LectureClavier.lireChaine();

						interfaceGestion
								.ajouterFormat(taille, nbPixel, libelle);
						break;
					case 9: // 9- Supprimer un client
						System.out.println("Renseignez le mail du client : ");
						String mail = LectureClavier.lireChaine();
						interfaceGestion.supprimerClient(mail);
						break;
					case 10: // 10 Modifier P
						String nom7,
						nom8,
						adresse2;
						int delai2,
						pref2;
						System.out
								.println("Renseignez le nom actuel du prestataire : ");
						nom7 = LectureClavier.lireChaine();
						System.out
								.println("Renseignez le nouveau nom du prestataire : ");
						nom8 = LectureClavier.lireChaine();
						System.out
								.println("Renseignez la nouvelle adresse du prestataire :");
						adresse2 = LectureClavier.lireChaine();
						pref2 = LectureClavier
								.lireEntier("Renseignez la nouvelle qualité du prestataire :");
						delai2 = LectureClavier
								.lireEntier("Renseignez le nouveau delai maximum du prestataire :");
						interfaceGestion.ModifierPrestataire(nom7, nom8,
								adresse2, pref2, delai2);
						;
						break;
					case 11:
						String statut;
						interfaceGestion.AfficherCommande();
						int idCom = LectureClavier.lireEntier("Choisir une commande : ");
						int c = LectureClavier.lireEntier("Choisir un statut : \n 1- envoie complet \n 2- annule");
						 if(c==1)statut="envoie complet";
						else if(c==2)statut="annule";
						else statut = "";
						if(statut.isEmpty()){
							System.out.println("statut pas defini !!");
						}else{
							interfaceGestion.changerStatutCommande(idCom, statut);
						}
						break;
					case 12 :
						interfaceGestion.AfficherLivraison();
						int idL = LectureClavier.lireEntier("Choisir une livraison :");
						 c = LectureClavier.lireEntier("Choisir un statut :\n1- en cours \n 2- envoye \n 3- annule");
						if(c==1)statut="en cours";
						else if(c==2)statut="envoye";
						else if(c==3)statut="annule";
						else statut = "";
						if(statut.isEmpty()){
							System.out.println("statut pas defini !!");
						}else{
							interfaceGestion.changerStatutLivraison(idL, statut);
						}
						break;
					case 13:
						interfaceGestion.AfficherCommande();
						break;
					case 14:
						interfaceGestion.AfficherLivraison();
						break;
					case 15 :
						interfaceGestion.afficherListeSuppImg();
						break;
					case 16 :
						System.out
						.println("Renseignez le nom du prestataire: ");
						String nom9 = LectureClavier.lireChaine();
						int idf = LectureClavier
						.lireEntier("Renseignez l'identifiant du format : ");
						float prixu = LectureClavier
						.lireFloat("Renseignez  le prix Unitaire pour ce format : ");
						interfaceGestion.modfitPrixUnitaireFormat(nom9, prixu,idf);
						break;
					case 99: // 10 Deconnecter
						admin = 0;
						break;
					case 999: // 99- Quitter l'application
						System.exit(0);
						;
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
