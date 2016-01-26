
public class Client {

	private String nom;
	private String prenom;
	private String mail;
	private String adressePostal;
	private String motDePasse;
	private boolean connectee;
	private InterfaceClient interfaceClient;

	public Client(InterfaceClient i) {
		this.interfaceClient = i;
	}

	public Client(String mail, String nom, String prenom, String adresse, String mDP) {
		this.mail = mail;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = mDP;
		this.adressePostal = adresse;
	}

	public void inscription() {
		String tempNom, tempPrenom, tempMail, tempAdresse, tempMotdepasse;
		System.out.print("nom :");
		System.out.flush();
		tempNom = LectureClavier.lireChaine();
		System.out.print("prenom :");
		System.out.flush();
		tempPrenom = LectureClavier.lireChaine();
		System.out.print("mail :");
		System.out.flush();
		tempMail = LectureClavier.lireChaine();
		System.out.print("adresse postal :");
		System.out.flush();
		tempAdresse = LectureClavier.lireChaine();
		System.out.print("mot de passe :");
		System.out.flush();
		tempMotdepasse = LectureClavier.lireChaine();
		if (interfaceClient.CreationClient(tempNom, tempPrenom, tempMail, tempAdresse, tempMotdepasse)) {
			System.out.println("inscription r�ussit");
			this.nom = tempNom;
			this.prenom = tempPrenom;
			this.mail = tempMail;
			this.adressePostal = tempAdresse;
			this.motDePasse = tempMotdepasse;
			this.connectee = true;
		} else {
			System.out.println("Inscription �chouer");
		}

	}

	// public void connection(){
	// String mailC,mdp;
	// System.out.println("Donner votre mail identifiant : ");
	// mailC = LectureClavier.lireChaine();
	// System.out.println("Donner le mot de passe : ");
	// mdp = LectureClavier.lireChaine();
	// interfaceClient.connection(mailC, mdp);
	// }

	public void AjoutImage() {
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

	public void AjoutAlbum() {

		if (true) {
			System.out.println("--- Ajout d'un album ---");
			System.out.println("** 1- Creer un album normal ");
			System.out.println("** 2- Creer un livre ");
			System.out.println("** 3- Creer un calendrier ");
			System.out.println("** 4- Creer un agenda ");
			int choixA = LectureClavier.lireEntier("Votre choix ? ");
			switch (choixA) {
			case 1:
				interfaceClient.AjoutAlbum(mail);
				break;
			case 2:
				AjoutLivre();
				break;
			case 3:
				AjoutCalendrier();
				break;
			default:
				break;
			}
		}
	}

	private void AjoutLivre() {
	
		System.out.println("--- Ajout d'un livre ---");
		System.out.println("Donner la preface du livre : ");
		System.out.flush();
		String preface = LectureClavier.lireChaine();

		System.out.println("Donner la postface du livre : ");
		System.out.flush();
		String postface = LectureClavier.lireChaine();

		interfaceClient.AfficheTousImages(mail);
		int idI = LectureClavier.lireEntier("Choisir une image de couverture de la liste suivante :");

		System.out.println("Donner le titre du livre : ");
		System.out.flush();
		String titreL = LectureClavier.lireChaine();

		interfaceClient.AjoutLivre(preface, postface, idI, titreL, mail);

	}

	private void AjoutCalendrier() {
		boolean a;
		System.out.println(
				"--- Creation d'un Calendrier --- ");
	

	}
//Calendrier( #idAlbum,  typeCalendrier,#photoCouverture) 






	


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

	public boolean isConnected() {
		return connectee;
	}

	public void AfficheTousImages() {
		this.interfaceClient.AfficheTousImages(mail);

	}

	public void AfficheTousAlbums() {
		interfaceClient.AfficheTousAlbum(mail);
	};

}
