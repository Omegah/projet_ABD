
public class Scenario {
	public void run1(InterfaceClient i) {
		Client client1,client2;
		client1 = i.connection("a", "a");
		client2 = i.connection("b", "b");
		
		i.Ajoutimage(client1.getMail(), "/img", "a", 1222);
		i.Ajoutimage(client1.getMail(), "img", "a2", 1111);
		i.Ajoutimage(client2.getMail(), "/doc", "b", 1312);
		i.Ajoutimage(client2.getMail(), "/Desktop", "b2", 900);

		i.AjoutAlbum(client1.getMail());
		i.AjoutAlbum(client2.getMail());
		System.out.println("Client 1");
		client1.AjoutPhotoAlbum();
		System.out.println("\nClient 2");
		client2.AjoutPhotoAlbum();
		
		client1.CreationCommande();
		client2.CreationCommande();
		
		client1.AjouterLot();
		

		
		
	}
}
