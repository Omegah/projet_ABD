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
	}

	public boolean AjoutImage(InterfaceClient interfaceClient){
		int resolution;
		String URL, information;
		if(true)
			System.out.print("entrez une l'URL de l'image :");
		 	System.out.flush();
		 	URL = LectureClavier.lireChaine();
		 	System.out.print("Information de l'image :");
		 	System.out.flush();
		 	information = LectureClavier.lireChaine();
		 	System.out.print("entrez une la résolution de l'image :");
		 	System.out.flush();
		 	resolution = LectureClavier.lireEntier(LectureClavier.lireChaine());
			try {
				return interfaceClient.Ajoutimage(getMail(),URL, information, resolution);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	public String getNom() {
		return nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public String getMail() {
		return mail;
	}


	public String getAdressePostal() {
		return adressePostal;
	}


	public String getMotDePasse() {
		return motDePasse;
	}


	public boolean isConnectee() {
		return connectee;
	};
	
	
	
	
	
	
}
