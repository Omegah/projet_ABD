package modele;

public class Client {

	private String nom;
	private String prenom;
	private String adressePostal;
	private String mail;
	private String MDP;
	
	public Client(String nom, String prenom, String adressePostal, String mail,
			String mDP) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adressePostal = adressePostal;
		this.mail = mail;
		MDP = mDP;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getAdressePostal() {
		return adressePostal;
	}

	public String getMail() {
		return mail;
	}

	public String getMDP() {
		return MDP;
	}
	
	
	
}
