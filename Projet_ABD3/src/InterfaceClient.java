import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class InterfaceClient {

	private static Connection conn;
	
	public InterfaceClient(Connection conn){
		InterfaceClient.conn = conn;
	}
	
	public void CreationClient(Client client) throws SQLException{
		
		Statement stmt = conn.createStatement();
		
		PreparedStatement st = conn.prepareStatement("insert into Client values (?,?,?,?,?)");
		st.setString(1, client.getMail());
		st.setString(2, client.getNom());
		st.setString(3, client.getPrenom());
		st.setString(4, client.getAdressePostal());
		st.setString(5, client.getMotDePasse());
		
		st.executeQuery();
		System.out.println("Ajout client");
		
	}

	public boolean Ajoutimage(String mail, String uRL, String information,
			int resolution) throws SQLException {
		int numImage;
		
		Statement stmt = conn.createStatement();
		PreparedStatement req = conn.prepareStatement("select count(idI) from image");
		ResultSet res = req.executeQuery();
		numImage = res.getInt(1);
		
		
		PreparedStatement st = conn.prepareStatement("insert into image values (?,?,?,?,?)");
		st.setInt(1, numImage);
		st.setInt(2, 0);
		st.setString(3, uRL);
		st.setString(4, mail);
		st.setString(5, information);
		st.setInt(6, resolution);
		
		st.executeQuery();
		return false;
	}

	
}
