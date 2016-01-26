
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
this.nom=nom;
this.prenom=prenom;
this.motDePasse = mDP;
this.adressePostal=adresse;
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
		boolean a;
		System.out.println(
				"--- Creation d'un livre --- \n** 1- Creer un livre a partir un album existant \n** 2- Creer un nouveau livre \n");
		int choixC = LectureClavier.lireEntier("Votre choix?");
		switch (choixC) {
		case 1:
			ConvertALivre();
			/*
			 * a = true;
			 * 
			 * int idA = LectureClavier.lireEntier(
			 * "choisissez un album de la liste suivante (IdA) : ");
			 * interfaceClient.AfficheTousAlbum(mail);
			 * 
			 * System.out.println("Ecrire une preface pour le livre : "
			 * ); System.out.flush(); String preface =
			 * LectureClavier.lireChaine();
			 * 
			 * System.out.println("Ecrire une postface pour le livre : "
			 * ); System.out.flush(); String postface =
			 * LectureClavier.lireChaine();
			 * 
			 * int idI = LectureClavier.lireEntier(
			 * "Choisir une image de couverture de la liste suivante : "
			 * ); interfaceClient.AfficheTousImages(mail);
			 * 
			 * System.out.println("Donner un titre au livre : ");
			 * System.out.flush(); String titreL =
			 * LectureClavier.lireChaine();
			 * 
			 * interfaceClient.AjoutLivre(a, idA, preface, postface,
			 * idI, titreL, mail);
			 */
			break;
		case 2:
			AjoutNouveauLivre();
			/*
			 * a = false; System.out.println(
			 * "Ecrire une preface pour le livre : ");
			 * System.out.flush(); String preface1 =
			 * LectureClavier.lireChaine();
			 * 
			 * System.out.println("Ecrire une postface pour le livre : "
			 * ); System.out.flush(); String postface1 =
			 * LectureClavier.lireChaine();
			 * 
			 * int idI1 = LectureClavier.lireEntier(
			 * "Choisir une image de couverture de la liste suivante : "
			 * ); interfaceClient.AfficheTousImages(mail);
			 * 
			 * System.out.println("Donner un titre au livre : ");
			 * System.out.flush(); String titreL1 =
			 * LectureClavier.lireChaine();
			 * 
			 * interfaceClient.AjoutLivre(a, 0, preface1, postface1,
			 * idI1, titreL1, mail);
			 */
			break;
		default:
			break;
		}

		
	}
	private void AjoutCalendrier() {
		boolean a;
		System.out.println(
				"--- Creation d'un Calendrier --- \n** 1- Creer un calendrier a partir un album existant \n** 2- Creer un nouveau calendrier \n");
		int choixC = LectureClavier.lireEntier("Votre choix?");
		switch (choixC) {
		case 1 :
			AjoutNouveauCalendrier();
			break;
		case 2 :
			ConvertACalendrier();
			break;
		default:
			break;
		}
		
	}
	private void ConvertACalendrier() {
		// TODO Auto-generated method stub
		
	}

	private void AjoutNouveauCalendrier() {
		// TODO Auto-generated method stub
		
	}
	// Ajout d'un nouveau livre (Creer le livre sans referencer a un album
	// existant) : Livre(#idAlbum, préface, postface,
	// #photoCouverture,titreLivre)
	// idI prend la valeur de n'importe quoi
	public void AjoutNouveauLivre() {
		if (true) {
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

			interfaceClient.AjoutLivre(false, 0, preface, postface, idI, titreL, mail);
		}
	}

	// transformation d'un album normal en livre
	public void ConvertALivre() {
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
