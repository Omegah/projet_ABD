import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
