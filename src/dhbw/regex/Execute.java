package dhbw.regex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Execute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Diverse Tags
		// Syntaktisch valide, ohne alt
		String nAlt = "<img asd=\"123\" />";
		// Valide, mit alt
		String wAlt = "<img asd=\"234\" alT=\'text\'>";
		// Invalide Attribute, fliegen raus
		String nDquo = "<img asd=567 Alt=text>";
		// Invalide, fliegt raus
		String nCont = "<img asd=567 Alt=>";
		// Valide, allerdings leer
		String nContDQuo = "<img Alt=\"\">";
		
		// Faulheit siegt
		String[] inputs = new String[] {nAlt, wAlt, nDquo, nCont, nContDQuo};
		
		// Alle Strings testen
		for(Integer i = 0; i < inputs.length; i++) {
			testInput(inputs[i]);
			System.out.println();
		}
	}
	
	public static void testInput(String input) {
		// Attribute: Key-Value-Map
		Map<String, String> attrs = new HashMap<String, String>();
		
		// Pattern für das Schema key=value
		Pattern eqAttrs = Pattern.compile("\\s([a-zA-Z]*)=(\"|')([^\\s^>^\"^\']*)");
		// Pattern für den TagName
		Pattern namePattern = Pattern.compile("<\\s*([a-zA-Z]*)(\\s*|/|>)");
		
		// TagName extrahieren
		Matcher nameMatcher = namePattern.matcher(input);
		String tagName = null;
		
		// Error handling
		if(nameMatcher.find()) {
			tagName = nameMatcher.group(1);
		} else {
			tagName = "MissingTag";
		}
		
		// Valide Attribute extrahieren
		Matcher matcher = eqAttrs.matcher(input);
		while(matcher.find()) {
			
			// Gruppen holen 
			String attr = matcher.group(1);
			String value = matcher.group(3);
	
			// Normalisiert speichern
			attrs.put(attr.toLowerCase(), value);
		}

		// Tag rekonstruieren
		System.out.println("Restoring tag:");
		
		// Falls der Attributwert leer ist oder nie vorhanden war:
		if(attrs.get("alt") == null || attrs.get("alt").equals("")) {
			System.out.println("Adding default alt statement.");
			attrs.put("alt", "This is a default value");
		}
		
		// Attributnamen aus der Map ziehen
		Iterator<String> it = attrs.keySet().iterator();
		
		// Attributliste zusammensetzen
		String res = "";
		while(it.hasNext()) {
			String attr = it.next();
			String value = attrs.get(attr);
			System.out.println("In set: " + attr + "=" + value);
			res += " " + attr + "=\"" + value + "\"";
		}
		
		// Tag zusammensetzen	
		res = "<" + tagName + res + " " + "/>"; 
		
		// Ausgabe
		System.out.println("Restored: " + res);
	}

}