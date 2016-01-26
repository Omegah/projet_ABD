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
		System.out.println("PO0");

		st.setInt(1, Utils.getLastId("Societe","idS",conn));
		st.setString(2, nomS);
		st.setString(3, adresse);
		st.setInt(4, pref);
		st.setInt(5, delai);	
		st.executeQuery();
		conn.commit();
		System.out.println("prestataire "+nomS+" ajouté.");
		
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
		System.out.println("Dispositif" + idD + "  ajouté pour la société "+societe);
		
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
		st.setInt(1, idS);
		st.setInt(2, idF);
		st.setInt(3, stock);
		st.setFloat(4, prixU);
		st.setInt(5, tirageJour);
		st.executeQuery();
		
		conn.commit();
		
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
			
			System.out.println("Format" + idF + "  ajouté ");

		 
	 }

	 public void supprimerDispositif(int idD) throws SQLException {
		 
		 
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
			
			System.out.println("Dispositif" + idD + "  supprimé");

			/*
			 * Trigger : verifier si dernier dispositif a faire un format pour une societe
			 */
			
	 }
	
	
	 public void supprimerPrestataire(String societe) {
		 /*
		  * Il faut ajouter dans la liste d'attente le prestataire a supprimer 
		  * si il a des commandes en cours 
		  */	 
		 
	 }
	 
	 public void supprimerClient(String mail) {
		 /*
		  * Il faut ajouter dans la liste d'attente le client a supprimer 
		  * si il a des commandes en cours 
		  */	 
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

			rs.close();
			req2.close();
	 }
}
