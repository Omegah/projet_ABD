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


	public Client(boolean connectee, String mail, String nom, String prenom,
			String adresse, String mDP, InterfaceClient i) {

		this.connectee = connectee;
		this.mail = mail;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = mDP;
		this.adressePostal = adresse;
		this.interfaceClient = i;
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
		if (interfaceClient.CreationClient(tempNom, tempPrenom, tempMail,
				tempAdresse, tempMotdepasse)) {
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
			resolution = LectureClavier
					.lireEntier("entrez la resolution de l'image :");
			interfaceClient.Ajoutimage(getMail(), URL, information, resolution);
			interfaceClient.AfficheTousImages(mail);
		}
	}

	public void AjoutAlbum() {

		if (true) {
			System.out.println("--- Ajout d'un album ---");
			System.out.println("** 1- Creer un album ");
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
			case 4:
				AjoutAgenda();
			default:
				break;
			}
		}
	}

	private void AjoutLivre() {
		if (true) {
			System.out.println("--- Ajout d'un livre ---");
			System.out.println("Donner la preface du livre : ");
			System.out.flush();
			String preface = LectureClavier.lireChaine();

			System.out.println("Donner la postface du livre : ");
			System.out.flush();
			String postface = LectureClavier.lireChaine();

			interfaceClient.AfficheTousImages(mail);
			int idI = LectureClavier
					.lireEntier("Choisir une image de couverture de la liste suivante :");

			System.out.println("Donner le titre du livre : ");
			System.out.flush();
			String titreL = LectureClavier.lireChaine();

			interfaceClient.AjoutLivre(preface, postface, idI, titreL, mail);
		}

	}

	public void AjoutAgenda() {
		if (true) {
			System.out.println("--- Ajout d'un livre ---");
			System.out.println("**** Type d'agenda ***");
			System.out.println("** 1- Journalier (365 pages)");
			System.out.println("** 2- Hebdomadaire (56 pages)");
			System.out.println("**********************");

			int choix = LectureClavier
					.lireEntier("Choisir le type d'agenda : ");


			if (choix == 1)
				interfaceClient.AjoutAgenda("Journalier", mail);
			else if (choix == 2)
				interfaceClient.AjoutAgenda("Hebdomadaire", mail);
		}
	}

	private void AjoutCalendrier() {
		String typeC;



		System.out.println("--- Creation d'un Calendrier ---");

		int type = LectureClavier
				.lireEntier("Donner le type du calendrier  : \n1- bureau\n2-mural ");

		if (type == 1)
			typeC = "bureau";
		else
			typeC = "mural";

		interfaceClient.AfficheTousImages(mail);
		int idp = LectureClavier
				.lireEntier("choisit une photo pour la couverture (Donner l'id ) :");

		interfaceClient.AjoutCalendrier(typeC, idp, mail);
	}

	public void CreationCommande() {

		System.out.println("************ Creation d'une commande ! **************");

		interfaceClient.CreationCommande(mail);
	}

	public void AjoutPhotoAlbum() {
		System.out.println("--- Ajout d'un image dans un album ---");
		System.out.println("Choisir un image de la liste suivante : ");
		interfaceClient.AfficheTousImages(mail);
		int idp = LectureClavier.lireEntier("Votre choix ?");

		System.out.println("Choisir un album de la liste : ");
		interfaceClient.AfficheTousAlbum(mail);
		int idA = LectureClavier.lireEntier("Votre choix ?");
		if(interfaceClient.estUlise(idA))
			interfaceClient.creerPhoto(idp, idA, mail);


	};

	public void PartageImage() {
		System.out.println("--- Partage d'une image ---");
		System.out.println("Choisir un image de la liste suivante : ");
		interfaceClient.AfficheTousImages(mail);
		int idI = LectureClavier.lireEntier("Votre choix ?");

		interfaceClient.PartageImage(idI);
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
	}

	public void deconnecter() {
		connectee = false;
		interfaceClient.supprimerImages(mail);
	}

	public void AjouterLot() {
		int idAlbum, idCom, quantite, idF, idS;
		System.out.println("******* Ajout d'un lot � une commande *********");
		AfficherTousCommande();
		idCom = LectureClavier.lireEntier("CHoisir une commande (idCom) : ");
		
		if(interfaceClient.estCommande(idCom)){
		AfficheTousAlbums();
		idAlbum = LectureClavier.lireEntier("Choisir un album (IdA):");
		quantite = LectureClavier
				.lireEntier("Combien voulez vous de cet album ?");
		interfaceClient.afficherTousFormat();
		idF = LectureClavier.lireEntier("CHoisir le format de l'album : ");

		idS = interfaceClient.TrouverSociete(idF, quantite);

		interfaceClient.ajouterLot(idAlbum, idCom, quantite, idF, idS);
		interfaceClient.MAJStock(quantite, idS, idF, idAlbum);
		interfaceClient.MAJPrixTotal(quantite, idS, idF, idCom, idAlbum);
		}
		}


	public void AfficherTousCommande() {
		// TODO Auto-generated method stub
		interfaceClient.afficherTousCommande(mail);
	}

	public void AfficheImgAlbum() {
		int idAlbum;
		System.out.println("--- Affichage des photos d'un album choisi ---");
		AfficheTousAlbums();
		idAlbum = LectureClavier.lireEntier("Choisir un album (IdA):");

		interfaceClient.afficheImgAlbum(idAlbum, mail);

	}

	public void SupprimerImage() {
		System.out.println("--- Suppression d'une image ---");
		System.out.println("Choisir une image de la liste suivante : ");
		AfficheTousImages();
		int idI = LectureClavier.lireEntier("Votre choix?");

		interfaceClient.SupprimerImage(idI, mail);
	}

	public void SupprimerPhotoAlbum() {
		int idAlbum;
		System.out.println("--- Suppression d'une photo d'un album ---");
		AfficheTousAlbums();
		idAlbum = LectureClavier.lireEntier("Choisir un album (IdA):");
		if (interfaceClient.verifTonAlbum(idAlbum, mail)) {
			System.out.println("Choisir une photo de la liste suivante :");
			interfaceClient.afficheImgAlbum(idAlbum, mail);
			int idPhoto = LectureClavier.lireEntier("Votre choix?");

			interfaceClient.SupprimerPhoto(idPhoto, mail);
		} else {
			System.out.println("Ce n'est pas ton album !!");

		}

	}


	public void PayerCommande() {
		AfficherTousCommande(); 
		int idCommande = LectureClavier.lireEntier("Quelle commande voulez-vous payer ?");
		interfaceClient.PayerCommande(idCommande,mail);
		
	}


	public void AfficherLotsCommande() {
		AfficherTousCommande(); 
		int idCommande = LectureClavier.lireEntier("Quelle commande voulez-vous voir ?");
		interfaceClient.AfficherLotsCommande(idCommande);
	}


}
