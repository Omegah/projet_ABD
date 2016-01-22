import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class Client {
	
	private String nom;
	private String prenom;
	private String mail;
	private String adressePostal;
	private String motDePasse;
	private boolean connectee;
	
	
	public Client(String nom, String prenom, String mail, String adressePostal,
			String motDePasse) {
		
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.adressePostal = adressePostal;
		this.motDePasse = motDePasse;
		this.connectee = false;		
	};
	
	
	
	
	
	
}
