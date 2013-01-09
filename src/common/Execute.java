package common;

import swe.entwurfsmuster.uniquelist.Studi;
import swe.entwurfsmuster.uniquelist.Studi.StudiAlreadyExistsException;

public class Execute {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		SWEliste();
	}
	
	public static void SWEliste() throws StudiAlreadyExistsException {
		Studi studi = new Studi("asd");
		Studi studi2 = new Studi("egh");
		Studi studi3 = new Studi("asd");
	}
	
	public static void compilerbauliste() {
		// Listen aufbauen
		SimpleNode list = SimpleNode.createList(5, "Hello List!");
		SimpleNode list2 = SimpleNode.createList(17);

		// Etwas Zeugs für list2
		list2 = list2.addFirst(3);
		
		// Liste 1 füllen
		for(int i = 4; i > 0; i--) {
			list = list.addFirst(i);
		}
		
		// Liste ausgeben
		System.out.println("Liste 1 ausgeben:");
		System.out.println(list.toString());
		
		// Liste umdrehen
		list = list.reverse();
		list2 = list2.reverseWithEnd();
		list2.setInfo(list);

		// Liste wieder ausgeben
		System.out.println("\nListe 1 umgedreht ausgeben:");
		System.out.println(list.toString());
		System.out.println("\nListe 2 ausgeben:");
		System.out.println(list2.toString());
	}

}
