import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InterfaceClient {

	private static Connection conn;

	public InterfaceClient(Connection conn) {
		InterfaceClient.conn = conn;
	}

	public void CreationClient(Client client) {
		try {
			Statement stmt = conn.createStatement();

			PreparedStatement st = conn.prepareStatement("insert into Client values (?,?,?,?,?)");
			st.setString(1, client.getMail());
			st.setString(2, client.getNom());
			st.setString(3, client.getPrenom());
			st.setString(4, client.getAdressePostal());
			st.setString(5, client.getMotDePasse());

			st.executeQuery();
			conn.commit();
			System.out.println("Ajout client");
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

	public void Ajoutimage(String mail, String uRL, String information, int resolution) {
		int numImage = 0;

		try {
			Statement stmt = conn.createStatement();

			PreparedStatement req = conn.prepareStatement("select count(idI) from image");
			ResultSet res = req.executeQuery();
			while (res.next())
				numImage = res.getInt(1);

			System.out.println("il y a " + numImage + " images sur la base de donn�es");

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
	// Affiche la table pour un client : Image(idI, partagé, URL, #mailCLient,
	// informationImage, résolution)

	public void AfficheTousImages(String mailC) {
		try {
			Statement stmt = conn.createStatement();
			int idI, resolution;
			String URL, mail, info;
			boolean partage;
			PreparedStatement req = conn.prepareStatement("select * from image where mailClient Like ?");
			req.setString(1, mailC);
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
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();

		}
	}

	// Ajout Album Album(idAlbum, #mailCLient)
	public void AjoutAlbum(String mailC) {
		try {
			Statement stmt = conn.createStatement();

			PreparedStatement req = conn.prepareStatement("select count(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			System.out.println("il y a " + numAlbum + " album sur la base de donn�es");
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

	// Ajout d'un livre : Livre(#idAlbum, préface, postface, #photoCouverture, titreLivre)
	public void AjoutLivre(boolean a, int idA, String preface, String postface, int idPC, String titreL, String mailC) {
		try {
			Statement stmt = conn.createStatement();

			if (a) {
				PreparedStatement req = conn.prepareStatement("insert into Livre values (?,?,?,?,?)");
				req.setInt(1, idA);
				req.setString(2, preface);
				req.setString(3, postface);
				req.setInt(4, idPC);
				req.setString(5, titreL);
				req.executeQuery();

				conn.commit();
				System.out.println("Ajout du livre : REUSSI !!");
			} else {

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
				req1.setInt(4, idPC);
				req1.setString(5, titreL);
				
				req1.executeQuery();
				conn.commit();
				System.out.println("Ajout d'un livre : REUSSI !!");
				
			}

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

	public void AfficheTousPhoto(String mail) {
		// TODO Auto-generated method stub
		
	}
}
