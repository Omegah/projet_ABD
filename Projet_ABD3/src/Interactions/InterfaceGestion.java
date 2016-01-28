package Interactions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JLabel;


public class InterfaceGestion {

	private static Connection conn;
	//private static int id = 1;
	
	public InterfaceGestion(Connection conn){
		InterfaceGestion.conn = conn;
	}
	
	public void AjoutPrestataire(String nomS,String adresse,int pref,int delai) throws SQLException{
		
		
		PreparedStatement st = conn.prepareStatement("insert into Societe values (?,?,?,?,?)");

		st.setInt(1, Utils.getLastId("Societe","idS",conn));
		st.setString(2, nomS);
		st.setString(3, adresse);
		st.setInt(4, pref);
		st.setInt(5, delai);	
		st.executeQuery();
		conn.commit();
		System.out.println("prestataire "+nomS+" ajouté.");
		
	}
	
	public void ModifierPrestataire(String societe,String nouveauNom,String adresse,int pref,int delai) throws SQLException{
		
		PreparedStatement req = conn.prepareStatement("select idS from societe where nomSociete=?");
		req.setString(1,societe);
		ResultSet res = req.executeQuery();
		res.next();
		int idS = res.getInt(1);
		req.close();
		
		PreparedStatement st = conn.prepareStatement("update societe set adresse=? , preference=? , delai=?,nomsociete=?  where idS=?");

		st.setString(1, adresse);
		st.setInt(2, pref);
		st.setInt(3, delai);
		st.setString(4, nouveauNom);	
		st.setInt(5, idS);	
		st.executeQuery();
		conn.commit();
		System.out.println("prestataire "+societe+" mis a jour.");
		
	}
	

	public boolean AjoutDispositif(String societe) throws SQLException {
		
		PreparedStatement req = conn.prepareStatement("select idS from societe where nomSociete=?");
		req.setString(1,societe);
		ResultSet res = req.executeQuery();
		res.next();
		int idS = res.getInt(1);
		req.close();
		
		int idD = Utils.getLastId("dispositif","idD",conn);
		
		PreparedStatement st = conn.prepareStatement("insert into dispositif values (?,?)");
		st.setInt(1, idD);
		st.setInt(2, idS);
		
		st.executeQuery();
		conn.commit();
		System.out.println("Dispositif " + idD + "  ajouté pour la société "+societe);
		
		return false;
	}
	
	public boolean AjoutFormatSociete(int idD,int idF,int stock,float prixU,int tirageJour,String societe) throws SQLException {
		
		PreparedStatement req = conn.prepareStatement("select idS from societe where nomSociete=?");
		req.setString(1,societe);
		ResultSet res = req.executeQuery();
		res.next();
		int idS = res.getInt(1);
		req.close();
				
		PreparedStatement st2 = conn.prepareStatement("insert into DispositifFormat values (?,?)");
		st2.setInt(1, idD);
		st2.setInt(2, idF);
		st2.executeQuery();
		
		conn.commit();
		
		PreparedStatement st = conn.prepareStatement("insert into FormatSociete values (?,?,?,?,?)");
		st.setInt(1, idF);
		st.setInt(2, idS);
		st.setInt(3, stock);
		st.setFloat(4, prixU);
		st.setInt(5, tirageJour);
		st.executeQuery();
		
		conn.commit();
		
		System.out.println("Format " + idF + "  ajouté pour la societé : " + societe);

		
		return false;
	}
	
	 public void ajouterFormat(String taille,int nbPixel,String libelle) throws SQLException {

			int idF = Utils.getLastId("FormatSociete","idF",conn);
			
			PreparedStatement st2 = conn.prepareStatement("insert into Format values (?,?,?,?)");
			st2.setInt(1, idF);
			st2.setString(2, taille);
			st2.setInt(3, nbPixel);
			st2.setString(4, libelle);

			st2.executeQuery();
			
			conn.commit();
			
			System.out.println("Format" + idF + "  ajouté.");

		 
	 }

	 public void supprimerDispositif(int idD) throws SQLException {
		 
		 PreparedStatement req0 = conn.prepareStatement("select count(idAlbum) from commande natural join dispositif natural join lot where idD=? and (statutCommande='en cours' or statutCommande='envoi partiel' )");
			req0.setInt(1,idD);
			ResultSet res0 = req0.executeQuery();
			res0.next();
			int nbCom = res0.getInt(1);
			
			if (nbCom==0) {
			
					
				 PreparedStatement req = conn.prepareStatement("select idF from FormatSociete natural join DispositifFormat where idD=?");
					req.setInt(1,idD);
					ResultSet res = req.executeQuery();
					
					
					while(res.next()) {
						int idF = res.getInt(1);
						PreparedStatement req2 = conn.prepareStatement("delete from FormatSociete where idF=?");
						req2.setInt(1, idF);
						req2.executeQuery();			
					}
				 
					PreparedStatement req3 = conn.prepareStatement("delete from DispositifFormat where idD=?");
					req3.setInt(1, idD);
					req3.executeQuery();
					
					PreparedStatement req4 = conn.prepareStatement("delete from dispositif where idD=?");
					req4.setInt(1, idD);
					req4.executeQuery();
					conn.commit();
					
					System.out.println("Dispositif" + idD + "  supprimé.");

			}
			/*
			 * Trigger : verifier si dernier dispositif a faire un format pour une societe
			 */
			
	 }
	
	
	 public void supprimerPrestataire(String societe) throws SQLException {
		 /*
		  * Il faut ajouter dans la liste d'attente le prestataire a supprimer 
		  * si il a des commandes en cours 
		  */	 
			PreparedStatement req = conn.prepareStatement("select idS from societe where nomSociete=?");
			req.setString(1,societe);
			ResultSet res = req.executeQuery();
			res.next();
			int idS = res.getInt(1);
			req.close();
			
			PreparedStatement req2 = conn.prepareStatement("select count(idLot) from Lot natural join commande where idS=? and(statutcommande='en cours' or statutcommande='envoi partiel')");
			req2.setInt(1,idS);
			ResultSet res2 = req2.executeQuery();
			res2.next();
			int nbLot = res2.getInt(1);
			req2.close();
			
			if (nbLot == 0) {
				
				 PreparedStatement req4 = conn.prepareStatement("select idD from dispositif where idS=?");
					req4.setInt(1,idS);
					ResultSet res4 = req4.executeQuery();		
					
					while(res4.next()) {
						int idD = res4.getInt(1);
						supprimerDispositif(idD);				
					}
					req4.close();
							
				PreparedStatement req3 = conn.prepareStatement("delete from societe where idS=?");
				req3.setInt(1, idS);
				req3.executeQuery();
				conn.commit();
				
				
				System.out.println("Prestataire " + societe + "  supprimé. ");

			}
			else {
				PreparedStatement st2 = conn.prepareStatement("insert into ListeSuppPrestataire values (?,?)");
				st2.setInt(1, idS);
				st2.setInt(2, nbLot);

				st2.executeQuery();
				
				conn.commit();
				
				System.out.println("Prestataire " + societe + "  ajouté a la file d'attente de suppression. ");
			}
		 
	 }
	 
	 public void supprimerClient(String mail) throws SQLException {
		 /*
		  * Il faut ajouter dans la liste d'attente le client a supprimer 
		  * si il a des commandes en cours 
		  * 
		  * A COMPLETER !
		  */	 
		 
			PreparedStatement req2 = conn.prepareStatement("select count(idCom) from commande where mailClient=? and (statutcommande='envoie partiel' or statutcommande='en cours' )");
			req2.setString(1,mail);
			ResultSet res2 = req2.executeQuery();
			res2.next();
			int nbCom = res2.getInt(1);
			req2.close();
			
			if (nbCom == 0) {
				
				 PreparedStatement req5 = conn.prepareStatement("select idCom from commande where mailclient=?");
					req5.setString(1,mail);
					ResultSet res = req5.executeQuery();
					
					
					while(res.next()) {
						
						int idCom = res.getInt(1);
						
						PreparedStatement req1 = conn.prepareStatement("select idLot from Lot where idCom=?");
						req1.setInt(1,idCom);
						ResultSet res3 = req5.executeQuery();
								while(res3.next()) {
										int idLot =res3.getInt(1);
										PreparedStatement req8 = conn.prepareStatement("delete from livraison where idLot=?");
										req8.setInt(1, idLot);
										req8.executeQuery();	
								}							
							
										PreparedStatement req9 = conn.prepareStatement("delete from Lot where idCom=?");
										req9.setInt(1, idCom);
										req9.executeQuery();	
								
	
										PreparedStatement req6 = conn.prepareStatement("delete from commande where idCom=?");
										req6.setInt(1, idCom);
										req6.executeQuery();	
									
					
					}
					
					PreparedStatement req6 = conn.prepareStatement("update client set MDP='gfdgdsfgdfgdf' where mail=?");
					req6.setString(1, mail);
					req6.executeQuery();	
					//DESACTIVER COMPTE CLIENT
					
					
					//InterfaceClient ic = new InterfaceClient();
					conn.commit();

			}
			else {

				PreparedStatement st2 = conn.prepareStatement("insert into ListeSuppClient values (?,?,?)");
				st2.setString(1, mail);
				st2.setInt(2, nbCom);
				st2.setInt(3, 0);
				st2.executeQuery();

				conn.commit();
				
				System.out.println("Client: " + mail + "  ajouté a la file d'attente de suppression. ");
				
				
				 PreparedStatement req5 = conn.prepareStatement("select idI from Image where mailclient=? ");
					req5.setString(1,mail);
					ResultSet res = req5.executeQuery();
					
					
					while(res.next()) {
						
						int idI = res.getInt(1);
						
						PreparedStatement req1 = conn.prepareStatement("update image set partage=0 where idI=?");
						req1.setInt(1,idI);
						req1.executeQuery();		
						supprimmerPhotosSansCommande(idI);
						//PreparedStatement req8 = conn.prepareStatement("delete from photo where idI=?");
						//req8.setInt(1,idI);
						//ResultSet res4 = req8.executeQuery();
					}
					
					conn.commit();
				
			}
		 
	 }
	 
	 private void supprimmerPhotosSansCommande(int idI) throws SQLException  {
		
		 PreparedStatement req1 = conn.prepareStatement("select idPhoto from"
		 		+ " photo join Album using(idAlbum) join lot using(idAlbum) join commande using(idCom)"
		 		+ " where idI=? and not(statutCommande='en cours' or statutCommande='envoi partiel' )");
			req1.setInt(1,idI);
			ResultSet res = req1.executeQuery();
			while(res.next()) {
				int idPhoto = res.getInt(1);
				
				try {
					PreparedStatement req6;
					req6 = conn.prepareStatement("delete from photo where idPhoto=?");
					req6.setInt(1, idPhoto);
					req6.executeQuery();		
				} catch (SQLException e) {
					PreparedStatement req7;
					req7 = conn.prepareStatement("update photo set idI=0 where idPhoto=?");
					req7.setInt(1, idPhoto);
					req7.executeQuery();	
				}
				
			}
			insererImageListeSuppp(idI);
			conn.commit();
	}
	 
	 
	 private void insererImageListeSuppp(int idI) throws SQLException {
		 PreparedStatement req2 = conn.prepareStatement("select count(idPhoto) from photo natural join commande natural join lot natural join Album where idI=? and (statutCommande='en cours' or statutCommande='envoi partiel' )");
			req2.setInt(1,idI);
			ResultSet res2 = req2.executeQuery();
			while(res2.next()) {
				int nbPhoto = res2.getInt(1);
					if (nbPhoto>0) {
						PreparedStatement req7 = conn.prepareStatement("insert into ListeSuppImage values (?,?)");
						req7.setInt(1, idI);
						req7.setInt(2, nbPhoto);
						req7.executeQuery();	
					}
			}
			conn.commit();

	 }

	 public void changerStatutCommande(int idCom,String statut) throws SQLException {
			PreparedStatement req1 = conn.prepareStatement("update commande set statutcommande=? where idCom=?");
			req1.setString(1,statut);
			req1.setInt(2,idCom);

			ResultSet res3 = req1.executeQuery();	
			conn.commit();

	 }
	 
	public void verifListeSuppPrestataire() throws SQLException {
		 PreparedStatement req = conn.prepareStatement("select idS from ListeSuppPrestataire where nbLot=0");
			ResultSet res = req.executeQuery();
						
			while(res.next()) {
				int idS = res.getInt(1);
				PreparedStatement req2 = conn.prepareStatement("delete from ListeSuppPrestataire where idS=?");
				req2.setInt(1, idS);
				req2.executeQuery();	
				
				PreparedStatement req3 = conn.prepareStatement("delete from societe where idS=?");
				req3.setInt(1, idS);
				req3.executeQuery();			
			}
			conn.commit();

	 }
	 
	 
	 public void verifListeSuppClient() throws SQLException {
		 PreparedStatement req = conn.prepareStatement("select mailClient from ListeSuppClient where nbCommande=0 and nbImageP=0");
			ResultSet res = req.executeQuery();
						
			while(res.next()) {
				String mail = res.getString(1);
				PreparedStatement req2 = conn.prepareStatement("delete from ListeSuppClient where mailClient=?");
				req2.setString(1, mail);
				req2.executeQuery();					
				
				PreparedStatement req3 = conn.prepareStatement("delete from client where mailClient=?");
				req3.setString(1, mail);
				req3.executeQuery();		
			}
			conn.commit();

	 }
	 	 
	 public void AfficherPrestataires() throws SQLException {

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT nomSociete,preference FROM Societe");
			System.out.println("Societe | preference\n");

			if (!rs.isBeforeFirst())
				System.out.println("pas de prestataire dans la base.");
			while (rs.next()) {
				System.out.println(rs.getString(1) + " : "+ rs.getString(2)+ "");
			}
			
			conn.commit();

			rs.close();
			stmt.close();
	 }
	 
	 public void AfficherDispositifsFormats(String societe) throws SQLException {
		 
		 	PreparedStatement req = conn.prepareStatement("select idS from societe where nomSociete=?");
			req.setString(1,societe);
			ResultSet res = req.executeQuery();
			res.next();
			int idS = res.getInt(1);
			req.close();

			PreparedStatement req2 = conn.prepareStatement("SELECT idS,idD,taille,nbPixel,libelle FROM FormatSociete NATURAL JOIN format NATURAL JOIN DispositifFormat WHERE idS=?");
			req2.setInt(1, idS);
			ResultSet rs = req2.executeQuery();

			if (!rs.isBeforeFirst())
				System.out.println("pas de formats pour cette société.");
			else{
				System.out.println("Dispositifs et formats de la société : "+ societe);
				System.out.println("Dispositifs / taille / nb Pixel / libellé");
			}

			while (rs.next()) {
				System.out.println(rs.getString(2) + " : "+ rs.getString(3)+ ""+ " : "+ rs.getString(4)+ ""+ " : "+ rs.getString(5)+ "");
			}
			conn.commit();

			rs.close();
			req2.close();
	 }
}
