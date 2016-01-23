package Interactions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;


public class InterfaceGestion {

	private static Connection conn;
	//private static int id = 1;
	
	public InterfaceGestion(Connection conn){
		InterfaceGestion.conn = conn;
	}
	
	public void CreationPrestataire(String nomS,String adresse,int pref,int delai) throws SQLException{
		
		Statement stmt = conn.createStatement();
		
		PreparedStatement st = conn.prepareStatement("insert into Societe values (?,?,?,?,?)");
		
		st.setInt(1, Utils.getLastId("Societe","idS",conn));
		st.setString(2, nomS);
		st.setString(3, adresse);
		st.setInt(4, pref);
		st.setInt(5, delai);	
		st.executeQuery();
		System.out.println("Ajout prestataire");
		
	}
	
	

	public boolean AjoutDispositif(String societe) throws SQLException {
		
		PreparedStatement req = conn.prepareStatement("select idS from societe where nameSociete=?");
		req.setString(1,societe);
		ResultSet res = req.executeQuery();
		int idS = res.getInt(1);
		
		int idD = Utils.getLastId("dispositif","idD",conn);
		
		PreparedStatement st = conn.prepareStatement("insert into dispositif values (?,?)");
		st.setInt(1, idD);
		st.setInt(2, idS);
		
		st.executeQuery();
		return false;
	}
	
	public boolean AjoutFormatSociete(int idD,int stock,int prixU,int tirageJour,String societe) throws SQLException {
		
		PreparedStatement req = conn.prepareStatement("select idS from societe where nameSociete=?");
		req.setString(1,societe);
		ResultSet res = req.executeQuery();
		int idS = res.getInt(1);
		
		int idF = Utils.getLastId("FormatSociete","idF",conn);
		
		PreparedStatement st = conn.prepareStatement("insert into FormatSociete values (?,?,?,?,?)");
		st.setInt(1, idS);
		st.setInt(2, idF);
		st.setInt(3, stock);
		st.setInt(4, prixU);
		st.setInt(5, tirageJour);
		st.executeQuery();
		

		PreparedStatement st2 = conn.prepareStatement("insert into DispositifFormat values (?,?)");
		st.setInt(1, idD);
		st.setInt(2, idF);
		st.executeQuery();
		return false;
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
	 
}
