import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class InterfaceClient {

	private static Connection conn;

	public InterfaceClient(Connection conn) {
		InterfaceClient.conn = conn;
	}

	public boolean CreationClient(String nom, String prenom, String mail, String adresse, String mdp) {
		try {
			Statement stmt = conn.createStatement();

			PreparedStatement st = conn.prepareStatement("insert into Client values (?,?,?,?,?)");
			st.setString(1, mail);
			st.setString(2, nom);
			st.setString(3, prenom);
			st.setString(4, adresse);
			st.setString(5, mdp);

			st.executeQuery();
			conn.commit();
			System.out.println("Ajout client");
			return true;
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}

	public Client connection(String mail, String motDePasse) {
		try {
			Statement stmt = conn.createStatement();
			String nom = null, prenom = null, adresse = null, MDP = null;
			PreparedStatement req = conn
					.prepareStatement("select nom, prenom, adressePostale, MDP from Client where mailClient = ? ");
			req.setString(1, mail);
			ResultSet res = req.executeQuery();
			while (res.next()) {
				nom = res.getString(1);
				prenom = res.getString(2);
				adresse = res.getString(3);
				MDP = res.getString(4);
			}
			if (MDP.equals(motDePasse)) {
				System.out.println("CONNECTED !");
				return new Client(true, mail, nom, prenom, adresse, MDP, this);
			}
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();

		}

		return null;

	}

	public void Ajoutimage(String mail, String uRL, String information, int resolution) {
		int numImage = 0;

		try {
			Statement stmt = conn.createStatement();

			PreparedStatement req = conn.prepareStatement("select max(idI) from image");
			ResultSet res = req.executeQuery();
			while (res.next())
				numImage = res.getInt(1);

			System.out.println("il y a " + numImage + " images sur la base de donnï¿½es");

			PreparedStatement st = conn.prepareStatement("insert into image values (?,?,?,?,?,?)");
			st.setInt(1, numImage + 1);
			st.setInt(2, 0);
			st.setString(3, uRL);
			st.setString(4, mail);
			st.setString(5, information);
			st.setInt(6, resolution);

			st.executeQuery();
			conn.commit();
			System.out.println("Ajout d'une image : REUSSI !!");
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	// Ajout Album Album(idAlbum, #mailCLient)
	public void AjoutAlbum(String mailC) {
		try {
			Statement stmt = conn.createStatement();

			PreparedStatement req = conn.prepareStatement("select max(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			System.out.println("il y a " + numAlbum + " album sur la base de donnï¿½es");
			PreparedStatement st = conn.prepareStatement("insert into Album values (?,?)");
			st.setInt(1, numAlbum + 1);
			st.setString(2, mailC);

			st.executeQuery();
			conn.commit();
			System.out.println("Ajout d'un album : REUSSI !! ");
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Ajout d'un livre : Livre(#idAlbum, prÃ©face, postface, #photoCouverture,
	// titreLivre)
	// var a pour dire si on creer un nouveau livre ou transformer un album
	// normal en livre a = true => transformer
	public void AjoutLivre(String preface, String postface, int idI, String titreL, String mailC) {
		try {
			Statement stmt = conn.createStatement();
			int idPC;

			AjoutAlbum(mailC);
			PreparedStatement req = conn.prepareStatement("select count(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			PreparedStatement req1 = conn.prepareStatement("insert into Livre values (?,?,?,?,?)");
			req1.setInt(1, numAlbum);
			req1.setString(2, preface);
			req1.setString(3, postface);
			idPC = creerPhoto(idI, numAlbum);
			req1.setInt(4, idPC);
			req1.setString(5, titreL);

			req1.executeQuery();
			conn.commit();
			System.out.println("Ajout du livre : REUSSI !!");

		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void AjoutCalendrier(String typeC, int idp, String mailC) {

		try {
			Statement stmt = conn.createStatement();
			int idPC;

			AjoutAlbum(mailC);
			PreparedStatement req = conn.prepareStatement("select count(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			PreparedStatement req1 = conn.prepareStatement("insert into Calendrier values (?,?,?)");
			req1.setInt(1, numAlbum);
			req1.setString(2, typeC);
			idPC = creerPhoto(idp, numAlbum);
			req1.setInt(3, idPC);

			req1.executeQuery();
			conn.commit();
			System.out.println("Ajout du calendrier : REUSSI !!");

		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void AjoutAgenda(String type, String mailC) {
		// TODO Auto-generated method stub
		try {
			Statement stmt = conn.createStatement();
			AjoutAlbum(mailC);
			PreparedStatement req = conn.prepareStatement("select count(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			PreparedStatement req1 = conn.prepareStatement("insert into Agenda values (?,?)");
			req1.setInt(1, numAlbum);
			req1.setString(2, type);

			req1.executeQuery();
			res.close();
			conn.commit();
			System.out.println("Ajout d'un agenda : REUSSI !!");

		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void PartageImage(int idI) {
		try{
			PreparedStatement req = conn.prepareStatement("update image set partage = 1 where idI =  ? ");
			req.setInt(1, idI);
			req.executeQuery();
			conn.commit();
			System.out.println("Partage de l'image : REUSSI !!");
		}catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	// Affiche la table pour un client : Image(idI, partagÃ©, URL, #mailCLient,
	// informationImage, rÃ©solution)

	public void AfficheTousImages(String mailC) {
		try {
			//Statement stmt = conn.createStatement();
			int idI, resolution;
			String URL, mail, info;
			boolean partage;
			PreparedStatement req = conn.prepareStatement("select * from image where mailClient Like ? or partage = ?");
			req.setString(1, mailC);
			req.setInt(2, 1);
			System.out.println("** Tous les images disponibles **");
			System.out.println("idI | partage | URL | mailClient | informationImage | resolution  ");
			ResultSet res = req.executeQuery();
			while (res.next()) {
				idI = res.getInt(1);
				partage = res.getBoolean(2);
				URL = res.getString(3);
				mail = res.getString(4);
				info = res.getString(5);
				resolution = res.getInt(6);
				System.out.println(
						idI + " | " + partage + " | " + URL + " | " + mail + " | " + info + " | " + resolution);
			}
			System.out.println("\n \n");
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();

		}
	}

	// Afficher les album d'un client : Album(idI,mailClient)
	public void AfficheTousAlbum(String mail) {
		try {
			Statement stmt = conn.createStatement();
			int idA;
			boolean partage;
			PreparedStatement req = conn.prepareStatement(
					"select idAlbum from album where mailClient Like ? minus (select idAlbum from album natural join Livre natural join Calendrier natural join Agenda where mailClient = ?)");
			req.setString(1, mail);
			req.setString(2, mail);
			System.out.println("** Tous les albums normaux **");
			System.out.println("idAlbum   ");
			ResultSet res = req.executeQuery();
			while (res.next()) {
				idA = res.getInt(1);

				System.out.println(idA);
			}
			System.out.println("\n \n");
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();

		}

	}
	// Creer une photo de l'image i : Photo(idPhoto, titrePhoto,numPage,
	// #idAlbum, #idI, commentaire)

	public int creerPhoto(int idI, int idAlbum) {
		try {
			System.out.println("Information de la photo ");
			System.out.println("Donner un titre a la photo choisie : ");
			System.out.flush();
			String titreP = LectureClavier.lireChaine();

			int numPage = LectureClavier.lireEntier("Donner le numero de la page dans l'album ");

			System.out.println("Donner une commentaire a la photo choisie : ");
			System.out.flush();
			String comment = LectureClavier.lireChaine();

			PreparedStatement req = conn.prepareStatement("select max(idPhoto) from Photo");
			ResultSet res = req.executeQuery();
			int numPhoto = 0;
			while (res.next())
				numPhoto = res.getInt(1);
			PreparedStatement req1 = conn.prepareStatement("insert into Photo values (?,?,?,?,?,?)");
			req1.setInt(1, numPhoto + 1);
			req1.setString(2, titreP);
			req1.setInt(3, numPage);
			req1.setInt(4, idAlbum);
			req1.setInt(5, idI);
			req1.setString(6, comment);

			req1.executeQuery();
			conn.commit();
			System.out.println("Creation de la photo : REUSSI !!");
			return numPhoto;
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return 0;
	}

	// Client(mailClient, nom, prenom, adressePostal, MDP)
	public void AfficheTousClients() {
		try {
			String mail, nom, prenom, adP, MDP;
			PreparedStatement req = conn
					.prepareStatement("select mailClient,nom,prenom,adressePostale,MDP from Client");
			ResultSet res = req.executeQuery();
			System.out.println("-- La liste des clients -- ");
			System.out.println("mailClient | Nom | Prenom | Adresse Postale | MDP ");
			while (res.next()) {
				mail = res.getString(1);
				nom = res.getString(2);
				prenom = res.getString(3);
				adP = res.getString(4);
				MDP = res.getString(5);
				System.out.println(mail + " | " + nom + " | " + prenom + " | " + adP + " | " + MDP);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void CreationCommande(String mail) {
		// TODO Auto-generated method stub
		try {
			Statement stmt = conn.createStatement();

			PreparedStatement req = conn.prepareStatement("select count(idCom) from Commande");
			ResultSet res = req.executeQuery();
			int numCommande = 0;
			while (res.next())
				numCommande = res.getInt(1);

			System.out.println("il y a " + numCommande + " commandes sur la base de donnï¿½es");
			PreparedStatement st = conn.prepareStatement("insert into Commande values (?,?,?,?)");
			st.setInt(1, numCommande + 1);
			st.setString(2, mail);
			st.setInt(3, 0);
			st.setString(4, "en Création");

			st.executeQuery();
			conn.commit();
			System.out.println("Ajout d'une commande : REUSSI !! ");
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void afficherTousCommande(String mail) {
		// TODO Auto-generated method stub
		try{
			String mailCLient, statut;
			int idCom, prixTotal;
			PreparedStatement req = conn.prepareStatement("select idCom, PrixTotal, statutCommande from Commande where mailclient = ?");
			req.setString(1, mail);
			ResultSet res = req.executeQuery();
			System.out.println("-- La liste des clients -- ");
			System.out.println("idCom | prixTotal | statut ");
			while (res.next()){
				idCom = res.getInt(1);
				prixTotal = res.getInt(2);
				statut = res.getString(3);
				System.out.println(idCom+" | "+prixTotal+" | "+ statut);
			}
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	public void afficherTousFormat() {
	// TODO Auto-generated method stub
	try{
		String taille, libelle;
		int idF, nbPixel;
		PreparedStatement req = conn.prepareStatement("select idF, taille, nbPixel, libelle from Format");
		ResultSet res = req.executeQuery();
		System.out.println("-- La liste des format -- ");
		System.out.println("idF | taille | nbPixel | libelle ");
		while (res.next()){
			idF = res.getInt(1);
			taille = res.getString(2);
			nbPixel = res.getInt(3);
			libelle = res.getString(4);
			System.out.println(idF+" | "+taille+" | "+nbPixel+" | "+ libelle);
		}
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
		}

	public int TrouverSociété(int idF, int quantite) {
		try{
			int idS;
			PreparedStatement req = conn.prepareStatement(""
					+ "select ids "
					+ "from formatSociete "
					+ "where idf = ? and prixunitaire = ("
					+ "select min(prixunitaire) "
					+ "from formatsociete "
					+ "where stock >= ? and idf = ?)");
			req.setInt(1, idF);
			req.setInt(2, quantite);
			req.setInt(3, idF);
			ResultSet res = req.executeQuery();
			while (res.next()){
				idS = res.getInt(1);
				System.out.println("la societe "+idS+" s'occupera du lot");
			}
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
		return 0;
	}	
	
}
