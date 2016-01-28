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

	public boolean CreationClient(String nom, String prenom, String mail,
			String adresse, String mdp) {
		try {
			Statement stmt = conn.createStatement();

			PreparedStatement st = conn
					.prepareStatement("insert into Client values (?,?,?,?,?)");
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

	public void Ajoutimage(String mail, String uRL, String information,
			int resolution) {
		int numImage = 0;

		try {
			Statement stmt = conn.createStatement();

			PreparedStatement req = conn
					.prepareStatement("select max(idI) from image");
			ResultSet res = req.executeQuery();
			while (res.next())
				numImage = res.getInt(1);

			System.out.println("il y a " + numImage
					+ " images sur la base de donn�es");

			PreparedStatement st = conn
					.prepareStatement("insert into image values (?,?,?,?,?,?)");
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

			PreparedStatement req = conn
					.prepareStatement("select max(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			System.out.println("il y a " + numAlbum
					+ " album sur la base de donn�es");
			PreparedStatement st = conn
					.prepareStatement("insert into Album values (?,?)");
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

	// Ajout d'un livre : Livre(#idAlbum, préface, postface, #photoCouverture,
	// titreLivre)
	// var a pour dire si on creer un nouveau livre ou transformer un album
	// normal en livre a = true => transformer
	public void AjoutLivre(String preface, String postface, int idI,
			String titreL, String mailC) {
		try {
			Statement stmt = conn.createStatement();
			int idPC;

			AjoutAlbum(mailC);
			PreparedStatement req = conn
					.prepareStatement("select count(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			PreparedStatement req1 = conn
					.prepareStatement("insert into Livre values (?,?,?,?,?)");
			req1.setInt(1, numAlbum);
			req1.setString(2, preface);
			req1.setString(3, postface);
			idPC = creerPhoto(idI, numAlbum, mailC);
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
			PreparedStatement req = conn
					.prepareStatement("select count(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			PreparedStatement req1 = conn
					.prepareStatement("insert into Calendrier values (?,?,?)");
			req1.setInt(1, numAlbum);
			req1.setString(2, typeC);
			idPC = creerPhoto(idp, numAlbum, mailC);
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
			PreparedStatement req = conn
					.prepareStatement("select count(idAlbum) from Album");
			ResultSet res = req.executeQuery();
			int numAlbum = 0;
			while (res.next())
				numAlbum = res.getInt(1);

			PreparedStatement req1 = conn
					.prepareStatement("insert into Agenda values (?,?)");
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
		try {
			PreparedStatement req = conn
					.prepareStatement("update image set partage = 1 where idI =  ? ");

			req.setInt(1, idI);
			req.executeQuery();
			conn.commit();
			System.out.println("Partage de l'image : REUSSI !!");
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
			// Statement stmt = conn.createStatement();
			int idI, resolution;
			String URL, mail, info;
			boolean partage;
			PreparedStatement req = conn
					.prepareStatement("select * from image where mailClient Like ? or partage = ?");
			req.setString(1, mailC);
			req.setInt(2, 1);
			System.out.println("** Tous les images disponibles **");
			System.out
					.println("idI | partage | URL | mailClient | informationImage | resolution  ");
			ResultSet res = req.executeQuery();
			while (res.next()) {
				idI = res.getInt(1);
				partage = res.getBoolean(2);
				URL = res.getString(3);
				mail = res.getString(4);
				info = res.getString(5);
				resolution = res.getInt(6);
				System.out.println(idI + " | " + partage + " | " + URL + " | "
						+ mail + " | " + info + " | " + resolution);
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

			int idA, nbPhoto;

			PreparedStatement req = conn
					.prepareStatement("select idAlbum, count(idphoto) from album natural join photo where mailClient Like ? group by idALbum having idalbum not in(select idalbum from livre) and idalbum not in (select idalbum from calendrier) and idalbum not in (select idalbum from agenda)");

			req.setString(1, mail);
			System.out.println("** albums simple **");
			System.out.println(" idAlbum | nbPhoto");
			ResultSet res = req.executeQuery();
			while (res.next()) {
				idA = res.getInt(1);
				nbPhoto = res.getInt(2);
				System.out.println(idA + "   |    " + nbPhoto);
			}
			PreparedStatement req2 = conn
					.prepareStatement("select idAlbum, count(idPhoto) from album natural join livre natural left outer join Photo where mailClient = ? group by idAlbum");
			req2.setString(1, mail);
			System.out.println("** Livre **");
			System.out.println("idAlbum | nombre de photo");
			ResultSet res2 = req2.executeQuery();
			while (res2.next()) {
				idA = res2.getInt(1);
				nbPhoto = res2.getInt(2);
				System.out.println(idA + "   |   " + nbPhoto);
			}
			PreparedStatement req3 = conn
					.prepareStatement("select idAlbum, count(idPhoto) from album natural join Calendrier natural left outer join Photo where mailClient = ? group by idAlbum");
			req3.setString(1, mail);
			System.out.println("** Calendrier **");
			System.out.println("idAlbum | nombre de photo");
			ResultSet res3 = req3.executeQuery();
			while (res3.next()) {
				idA = res3.getInt(1);
				nbPhoto = res3.getInt(2);
				System.out.println(idA + "   |   " + nbPhoto);
			}
			PreparedStatement req4 = conn
					.prepareStatement("select idAlbum, count(idPhoto) from album natural join Agenda natural left outer join Photo where mailClient = ? group by idAlbum");
			req4.setString(1, mail);
			System.out.println("** Agenda **");
			System.out.println("idAlbum | nombre de photo");
			ResultSet res4 = req4.executeQuery();
			while (res4.next()) {
				idA = res4.getInt(1);
				nbPhoto = res4.getInt(2);
				System.out.println(idA + "   |   " + nbPhoto);
			}

			System.out.println("\n \n");
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();

		}

	}

	// Creer une photo de l'image i : Photo(idPhoto, titrePhoto,numPage,
	// #idAlbum, #idI, commentaire)

	public int creerPhoto(int idI, int idAlbum, String mail) {
		try {
			/*
			 * String mailc = null; PreparedStatement p = conn.prepareStatement(
			 * "select mailclient from album where idAlbum = ? "); p.setInt(1,
			 * idAlbum); ResultSet verif = p.executeQuery(); while
			 * (verif.next()) mailc = verif.getString(1); if
			 * (mailc.equals(mail)) {
			 */
			System.out.println("Information de la photo ");
			System.out.println("Donner un titre a la photo choisie : ");
			System.out.flush();
			String titreP = LectureClavier.lireChaine();

			int numPage = LectureClavier
					.lireEntier("Donner le numero de la page dans l'album ");

			System.out.println("Donner une commentaire a la photo choisie : ");
			System.out.flush();
			String comment = LectureClavier.lireChaine();

			PreparedStatement req = conn
					.prepareStatement("select max(idPhoto) from Photo");
			ResultSet res = req.executeQuery();
			int numPhoto = 0;
			while (res.next())
				numPhoto = res.getInt(1);
			PreparedStatement req2 = conn
					.prepareStatement("select max(numpage) from Photo where idAlbum = ?");
			req2.setInt(1, idAlbum);
			ResultSet res2 = req2.executeQuery();
			while (res2.next())
				numPage = res2.getInt(1);

			PreparedStatement req1 = conn
					.prepareStatement("insert into Photo values (?,?,?,?,?,?)");

			req1.setInt(1, numPhoto + 1);
			req1.setString(2, titreP);
			req1.setInt(3, numPage + 1);
			req1.setInt(4, idAlbum);
			req1.setInt(5, idI);
			req1.setString(6, comment);

			req1.executeQuery();
			conn.commit();
			System.out.println("Ajout de la photo : REUSSI !!");
			return numPhoto;
			/*
			 * } else { System.out.println("Ce n'est pas ton album  !!");
			 * 
			 * }
			 */

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
			System.out
					.println("mailClient | Nom | Prenom | Adresse Postale | MDP ");
			while (res.next()) {
				mail = res.getString(1);
				nom = res.getString(2);
				prenom = res.getString(3);
				adP = res.getString(4);
				MDP = res.getString(5);
				System.out.println(mail + " | " + nom + " | " + prenom + " | "
						+ adP + " | " + MDP);
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

			PreparedStatement req = conn
					.prepareStatement("select count(idCom) from Commande");
			ResultSet res = req.executeQuery();
			int numCommande = 0;
			while (res.next())
				numCommande = res.getInt(1);

			System.out.println("il y a " + numCommande
					+ " commandes sur la base de donn�es");
			PreparedStatement st = conn
					.prepareStatement("insert into Commande values (?,?,?,?)");
			st.setInt(1, numCommande + 1);
			st.setString(2, mail);
			st.setInt(3, 0);
			st.setString(4, "en creation");

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
		try {
			String mailCLient, statut;
			int idCom, prixTotal;
			PreparedStatement req = conn
					.prepareStatement("select idCom, PrixTotal, statutCommande from Commande where mailclient = ?");
			req.setString(1, mail);
			ResultSet res = req.executeQuery();
			System.out.println("-- La liste des clients -- ");
			System.out.println("idCom | prixTotal | statut ");
			while (res.next()) {
				idCom = res.getInt(1);
				prixTotal = res.getInt(2);
				statut = res.getString(3);
				System.out.println(idCom + " | " + prixTotal + " | " + statut);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void afficherTousFormat() {
		// TODO Auto-generated method stub
		try {
			String taille, libelle;
			int idF, nbPixel;
			PreparedStatement req = conn
					.prepareStatement("select idF, taille, nbPixel, libelle from Format");
			ResultSet res = req.executeQuery();
			System.out.println("-- La liste des format -- ");
			System.out.println("idF | taille | nbPixel | libelle ");
			while (res.next()) {
				idF = res.getInt(1);
				taille = res.getString(2);
				nbPixel = res.getInt(3);
				libelle = res.getString(4);
				System.out.println(idF + " | " + taille + " | " + nbPixel
						+ " | " + libelle);

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public int TrouverSociete(int idF, int quantite) {
		try {
			int idS = 0;

			PreparedStatement req = conn.prepareStatement("" + "select ids "
					+ "from formatSociete "
					+ "where idf = ? and prixunitaire = ("
					+ "select min(prixunitaire) " + "from formatsociete "
					+ "where stock >= ? and idf = ?)");
			req.setInt(1, idF);
			req.setInt(2, quantite);
			req.setInt(3, idF);
			ResultSet res = req.executeQuery();
			while (res.next()) {
				idS = res.getInt(1);
				System.out.println("la societe " + idS + " s'occupera du lot");
			}
			return idS;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	}

	public void afficheImgAlbum(int idAlbum, String mail) {
		try {
			int idP, numP, idA, idI;
			String titreP, comment;
			PreparedStatement req = conn
					.prepareStatement("select  idPhoto,titrePhoto,numPage,idAlbum,idI,commentaire from photo natural join Album where mailclient = ? and idAlbum = ?");
			req.setString(1, mail);
			req.setInt(2, idAlbum);
			ResultSet res = req.executeQuery();
			System.out
					.println("idPhoto | titre Photo | numPage | idAlbum | idI | commentaire");
			while (res.next()) {
				idP = res.getInt(1);
				titreP = res.getString(2);
				numP = res.getInt(3);
				idA = res.getInt(4);
				idI = res.getInt(5);
				comment = res.getString(6);
				System.out.println(idP + " | " + titreP + " | " + numP + " | "
						+ idA + " | " + idI + " | " + comment);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void ajouterLot(int idAlbum, int idCom, int quantite, int idF,
			int idS) {
		try {
			Statement stmt = conn.createStatement();

			PreparedStatement req = conn
					.prepareStatement("select count(idLot) from lot");
			ResultSet res = req.executeQuery();
			int numLot = 0;
			while (res.next())
				numLot = res.getInt(1);

			System.out.println("il y a " + numLot
					+ " lots sur la base de donn�es");
			PreparedStatement st = conn
					.prepareStatement("insert into Lot values (?,?,?,?,?,?)");
			st.setInt(1, numLot + 1);
			st.setInt(2, idCom);
			st.setInt(3, idAlbum);
			st.setInt(4, quantite);
			st.setInt(5, idS);
			st.setInt(6, idF);

			st.executeQuery();

			CreationLivraison(numLot + 1);
			System.out.println("Ajout d'un lot : REUSSI !! ");
		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	private void CreationLivraison(int idLot) {
		try {
			Statement stmt = conn.createStatement();

			PreparedStatement req = conn
					.prepareStatement("select max(idlivraison) from Livraison");
			ResultSet res = req.executeQuery();
			int numLivraison = 0;
			while (res.next())
				numLivraison = res.getInt(1);

			System.out.println("il y a " + numLivraison
					+ " commandes sur la base de donn�es");
			PreparedStatement st = conn
					.prepareStatement("insert into livraison values (?,?,?)");
			st.setInt(1, numLivraison + 1);
			st.setInt(2, idLot);
			st.setString(3, "en cours");
			st.executeQuery();
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

	public void MAJStock(int quantite, int idS, int idF, int idAlbum) {
		try {
			int nouveauStock = 0;
			PreparedStatement req = conn
					.prepareStatement("select stock from formatSociete where idf = ? and ids = ?");
			req.setInt(1, idF);
			req.setInt(2, idS);
			ResultSet res = req.executeQuery();
			while (res.next()) {

				nouveauStock = res.getInt(1);
				System.out.println("la societe � un stock de " + nouveauStock);

			}

			int nbPhoto = 0;
			PreparedStatement req4 = conn
					.prepareStatement("select count(idAlbum) from Photo where idAlbum = ?");
			req4.setInt(1, idAlbum);

			ResultSet res3 = req4.executeQuery();
			while (res3.next()) {
				nbPhoto = res3.getInt(1);
				System.out.println("le nombre de photo : " + nbPhoto);
			}

			nouveauStock = nouveauStock - quantite * nbPhoto;

			PreparedStatement req2 = conn
					.prepareStatement("UPDATE formatSociete SET Stock=? WHERE idF=? and idS =?");
			req2.setInt(1, nouveauStock);
			req2.setInt(2, idF);
			req2.setInt(3, idS);
			req2.executeQuery();
			System.out.println("le stock � �tait mis � jours");

		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block-/*87564210
				e1.printStackTrace();

			}
		}
	}

	public void MAJPrixTotal(int quantite, int idS, int idF, int idCom,
			int idAlbum) {

		try {
			int prixUnitaire = 0;
			PreparedStatement req = conn
					.prepareStatement("select prixunitaire from formatSociete where idf = ? and ids = ?");
			req.setInt(1, idF);
			req.setInt(2, idS);
			ResultSet res = req.executeQuery();
			while (res.next()) {
				prixUnitaire = res.getInt(1);
				System.out.println("le prix unitaire est de " + prixUnitaire);
			}
			int nbPhoto = 0;
			PreparedStatement req4 = conn
					.prepareStatement("select count(idAlbum) from Photo where idAlbum = ?");
			req4.setInt(1, idAlbum);

			ResultSet res3 = req4.executeQuery();
			while (res3.next()) {
				nbPhoto = res3.getInt(1);
				System.out.println("le nombre de photo : " + nbPhoto);
			}

			int prixCommande = quantite * prixUnitaire * nbPhoto;
			int prixTotal = 0;
			PreparedStatement req2 = conn
					.prepareStatement("select prixtotal from commande where idcom = ?");
			req.setInt(1, idCom);
			ResultSet res2 = req.executeQuery();
			while (res2.next()) {
				prixTotal = res2.getInt(1);
				System.out.println("le prix unitaire est de " + prixUnitaire);
			}
			prixTotal = prixTotal + prixCommande;

			PreparedStatement req3 = conn
					.prepareStatement("UPDATE Commande SET prixTotal=? WHERE idcom=?");

			req3.setInt(1, prixTotal);
			req3.setInt(2, idCom);
			req3.executeQuery();
			System.out
					.println("le prix de la commande a �t� mis � jours");
			conn.commit();

		} catch (SQLException e) {
			System.out.println("Pb dans BD : ROLLBACK !!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void SupprimerImage(int idI, String mail) {
		try {
			int c = 0;
			PreparedStatement req = conn
					.prepareStatement("select count(idPhoto) from Lot natural join album natural join Photo  natural join image where idI = ?");
			req.setInt(1, idI);
			ResultSet res = req.executeQuery();
			while (res.next()) {
				c = res.getInt(1);
			}

			req = conn
					.prepareStatement("insert into ListeSuppImage values (?,?)");
			req.setInt(1, idI);
			req.setInt(2, c);

			res = req.executeQuery();
			System.out
					.println("-- L'image est dans transfere dans la liste d'attente ");
			verifListeSuppImage();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
	}

	public boolean estCommande(int idCom) {
		String statut = null;
		PreparedStatement req;
		try {
			req = conn
					.prepareStatement("select statutCommande from commande where idcom = ?");

			req.setInt(1, idCom);
			ResultSet res = req.executeQuery();
			while (res.next()) {
				statut = res.getString(1);
				if (statut.equals("en creation")) {
					return true;
				} else {
					System.out.println("Ne peut etre modifi� : Command�");
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public boolean estUlise(int idAlbum) {
		String statut = null;
		PreparedStatement req;
		try {
			req = conn
					.prepareStatement("select distinct statutcommande from commande natural join lot natural join album where idalbum = ?");

			req.setInt(1, idAlbum);
			ResultSet res = req.executeQuery();
			while (res.next()) {
				statut = res.getString(1);
				if (statut.equals("en creation")) {
					return true;
				} else {
					System.out.println("Ne peut etre modifi� : Command�");
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}


	private void verifListeSuppImage() {
		try {
			int idI;

			PreparedStatement req = conn.prepareStatement("select idI from ListeSuppImage where nbPhotos = 0");

			ResultSet res = req.executeQuery();
			while (res.next()) {
				idI = res.getInt(1);

				req = conn
						.prepareStatement("delete from ListeSuppImage where idI = ?");
				req.setInt(1, idI);
				req.executeQuery();

				req = conn.prepareStatement("delete from photo where idI = ?");
				req.setInt(1, idI);
				req.executeQuery();

				req = conn.prepareStatement("delete from image where idI = ?");
				req.setInt(1, idI);
				req.executeQuery();
				System.out.println("L'image " + idI + " est supprimee !! ");
				conn.commit();
				
				System.out.println("Liste de Supp Image updated");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void SupprimerPhoto(int idPhoto, String mail) {
		try {
			String mailc = null;
			PreparedStatement p = conn
					.prepareStatement("select mailclient from image natural join photo where idPhoto = ?");
			p.setInt(1, idPhoto);
			ResultSet verif = p.executeQuery();
			while (verif.next())
				mailc = verif.getString(1);
			if (mailc.equals(mail)) {
				PreparedStatement req = conn
						.prepareStatement("delete from photo where idPhoto = ?");
				req.setInt(1, idPhoto);
				req.executeQuery();
				System.out
						.println("La photo est bien supprimmee de l'album !!");
				conn.commit();
			} else {
				System.out.println("Ce n'est pas ta photo !!!!!!");

			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public boolean verifTonAlbum(int idAlbum, String mail) {
		try {
			String mailc = null;
			PreparedStatement p = conn
					.prepareStatement("select mailclient from album where idAlbum = ? ");
			p.setInt(1, idAlbum);
			ResultSet verif = p.executeQuery();
			while (verif.next())
				mailc = verif.getString(1);
			return mailc.equals(mail);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void PayerCommande(int idCommande, String mail) {
		try {
			PreparedStatement req = conn
					.prepareStatement("UPDATE Commande SET StatutCommande = 'en cours' WHERE idcom=? and mailCLient = ?");

			req.setInt(1, idCommande);
			req.setString(2, mail);
			req.executeQuery();
			System.out
					.println("Vous venez payer ma commande" + idCommande);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public void AfficherLotsCommande(int idCommande) {
		try {
			int idLot, idAlbum, quantite, idF;
			PreparedStatement req = conn
					.prepareStatement("select idLot, idAlbum, Quantite, idF from lot wwhere idCom=?");

			req.setInt(1, idCommande);
			ResultSet res = req.executeQuery();
			while(res.next()){
				idLot = res.getInt(1);
				idAlbum = res.getInt(2);
				quantite = res.getInt(3);
				idF = res.getInt(4);
				System.out.println(idLot + " |" + idAlbum + " | " + quantite + " | " + idF);
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
