
public class Client {

	private String nom;
	private String prenom;
	private String mail;
	private String adressePostal;
	private String motDePasse;
	private boolean connectee;

	public Client(String nom, String prenom, String mail, String adressePostal, String motDePasse) {

		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.adressePostal = adressePostal;
		this.motDePasse = motDePasse;
		this.connectee = false;
	}

	public void AjoutImage(InterfaceClient interfaceClient) {
		int resolution;
		String URL, information;
		if (true) {
			System.out.println("--- Ajout d'un image ---");
			System.out.print("entrez une l'URL de l'image :");
			System.out.flush();
			URL = LectureClavier.lireChaine();
			System.out.print("Information de l'image :");
			System.out.flush();
			information = LectureClavier.lireChaine();
			System.out.flush();
			resolution = LectureClavier.lireEntier("entrez une la resolution de l'image :");
			interfaceClient.Ajoutimage(getMail(), URL, information, resolution);
			interfaceClient.AfficheTousImages(mail);
		}
	}

	public void AjoutAlbum(InterfaceClient interfaceClient) {
		if (true) {
			System.out.println("--- Ajout d'un album ---");
			interfaceClient.AjoutAlbum(mail);
		}
	}

	// Ajout d'un livre : Livre(#idAlbum, préface, postface, #photoCouverture, titreLivre)
	public void AjoutNouveauLivre(InterfaceClient interfaceCLient) {
		if (true) {
			System.out.println("--- Ajout d'un livre ---");
			System.out.println("Donner la preface du livre : ");
			System.out.flush();
			String preface = LectureClavier.lireChaine();
			
			System.out.println("Donner la postface du livre : ");
			System.out.flush();
			String postface = LectureClavier.lireChaine();
			
			interfaceCLient.AfficheTousPhoto(mail);
			int idp = LectureClavier.lireEntier("choisit une photo pour la couverture (Donner l'id ) :");
			
			System.out.println("Donner le titre du livre : ");
			System.out.flush();
			String titreL = LectureClavier.lireChaine();
			
			interfaceCLient.AjoutLivre(false, 0, preface, postface, idp, titreL, mail);
		}
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
