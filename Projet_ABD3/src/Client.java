
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
			resolution = LectureClavier.lireEntier("entrez la resolution de l'image :");
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

	// Ajout d'un nouveau livre (Creer le livre sans referencer a un album
	// existant) : Livre(#idAlbum, préface, postface, #photoCouverture,titreLivre)
	// idI prend la valeur de n'importe quoi
	public void AjoutNouveauLivre(InterfaceClient interfaceCLient) {
		if (true) {
			System.out.println("--- Ajout d'un livre ---");
			System.out.println("Donner la preface du livre : ");
			System.out.flush();
			String preface = LectureClavier.lireChaine();

			System.out.println("Donner la postface du livre : ");
			System.out.flush();
			String postface = LectureClavier.lireChaine();

			interfaceCLient.AfficheTousImages(mail);
			int idI = LectureClavier.lireEntier("choisir l'id de l'image desire pour la couverture  :");
			
			
			
			System.out.println("Donner le titre du livre : ");
			System.out.flush();
			String titreL = LectureClavier.lireChaine();

			interfaceCLient.AjoutLivre(false, 0, preface, postface, idI, titreL, mail);
		}
	}

	// transformation d'un album normal en livre
	public void ConvertALivre(InterfaceClient interfaceClient) {
		if (true) {
			System.out.println("--- Ajout d'un livre apartir d'un album existant ---");
			interfaceClient.AfficheTousAlbum(mail);
			
			int idI = LectureClavier.lireEntier("Choisir un album de la liste precedente : ");
			
			System.out.println("Donner la preface du livre : ");
			System.out.flush();
			String preface = LectureClavier.lireChaine();

			System.out.println("Donner la postface du livre : ");
			System.out.flush();
			String postface = LectureClavier.lireChaine();

			interfaceClient.AfficheTousImages(mail);
			int idp = LectureClavier.lireEntier("choisit une photo pour la couverture (Donner l'id ) :");

			System.out.println("Donner le titre du livre : ");
			System.out.flush();
			String titreL = LectureClavier.lireChaine();

			interfaceClient.AjoutLivre(true, idI, preface, postface, idp, titreL, mail);
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
