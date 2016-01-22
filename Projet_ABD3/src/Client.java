import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class Client {
	
	public static void AjoutClient(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		
		PreparedStatement st = conn.prepareStatement("insert into Client values ('Philipe@free.fr','Philipe','jean','Paris', 'pute')");
		st.executeQuery();
		System.out.println("Ajout client");
		
	}
}
