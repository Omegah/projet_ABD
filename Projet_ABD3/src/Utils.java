

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {

	public static int getLastId(String table,String id,Connection conn) throws SQLException {
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT MAX("+ id +")	FROM "+table);
		//System.out.println("PO1" + rs.getInt(1));
		rs.next();
		int res = rs.getInt(1);
		rs.close();
		stmt.close();
		
		return res+1;
	
	}

	public static void updateTable(String table,String attribut, String valeur,String condition, String valeurCondition,Connection conn) throws SQLException {
		
		PreparedStatement st = conn.prepareStatement("UPDATE TABLE ? SET ?=? WHERE ?=?");
		st.setString(1, table);
		st.setString(2, attribut);
		st.setString(3, valeur);
		st.setString(4, condition);
		st.setString(5, valeurCondition);

	}
	

	public static void updateTable(String table,String attribut, int valeur,String condition, String valeurCondition,Connection conn) throws SQLException {
		
		PreparedStatement st = conn.prepareStatement("UPDATE TABLE ? SET ?=? WHERE ?=?");
		st.setString(1, table);
		st.setString(2, attribut);
		st.setInt(3, valeur);
		st.setString(4, condition);
		st.setString(5, valeurCondition);

	}
	
	public static void updateTable(String table,String attribut, int valeur,String condition, int valeurCondition,Connection conn) throws SQLException {
		
		PreparedStatement st = conn.prepareStatement("UPDATE TABLE ? SET ?=? WHERE ?=?");
		st.setString(1, table);
		st.setString(2, attribut);
		st.setInt(3, valeur);
		st.setString(4, condition);
		st.setInt(5, valeurCondition);

	}
	
	public static void updateTable(String table,String attribut, String valeur,String condition, int valeurCondition,Connection conn) throws SQLException {
		
		PreparedStatement st = conn.prepareStatement("UPDATE TABLE ? SET ?=? WHERE ?=?");
		st.setString(1, table);
		st.setString(2, attribut);
		st.setString(3, valeur);
		st.setString(4, condition);
		st.setInt(5, valeurCondition);

	}
	
}
