
public class Menu {

	
	public static void Acceuil(){
		System.out.println("*********************************************");
		System.out.println("** 1- S'inscrire � PhotoNum");
		System.out.println("** 2- Se connecter � PhotoNum");
		System.out.println("** 3- Se connecter en tant qu'administrateur");
		System.out.println("** 99- Quitter l'application");
		System.out.println("*********************************************");
	}
	
	public static void Client(){
		System.out.println("*********************************************");
		System.out.println("** 1- Afficher ses images");
		System.out.println("** 2- T�l�charger une image");
		System.out.println("** 3- Afficher ses albums");
		System.out.println("** 4- Cr�er un album");
		System.out.println("** 5- Ajouter une photo a un album");
		System.out.println("** 6- Afficher ses commandes");
		System.out.println("** 7- Faire une commande");
		System.out.println("** 9- Deconnecter");
		System.out.println("** 99- Quitter l'application");
		System.out.println("**********************************************");
	}
	
	public static void Administrateur(){
		System.out.println("**********************************************");
		System.out.println("** 1- Afficher les prestataires");
		System.out.println("** 2- Ajouter un prestataire");
		System.out.println("** 3- Supprimer un prestataire");
		System.out.println("** 4- Afficher les commandes");
		System.out.println("** 9- Deconnecter");
		System.out.println("** 99- Quitter l'application");
		System.out.println("***********************************************");
	}
	
}
