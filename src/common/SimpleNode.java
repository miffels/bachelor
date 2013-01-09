package common;

// Eine einfache Demo zu umkehrbaren Listen in OO
public class SimpleNode {
	
	// Inhalt der Knoten
	protected int value = 0;
	protected SimpleNode next = null;
	
	public static SimpleNode createList(int value, Object someInfo) {
		return new SimpleEndNode(value, someInfo);
	}
	
	public static SimpleNode createList(int value) {
		return new SimpleEndNode(value, null);
	}
	
	// Konstruktor mit Knoteninhalt
	private SimpleNode(int value) {
		this.value = value;
	}
	
	// vorn anhängen, neuen Anfang zurückgeben
	public SimpleNode addFirst(SimpleNode node) {
		if(node != null) {
			node.next = this;
			return node;
		} else {
			return this;
		}
	}
	
	// Für Faule: addFirst nur mit int als Parameter statt Objekten
	public SimpleNode addFirst(int value) {
		return this.addFirst(new SimpleNode(value));
	}
	
	// Info zurückgeben
	public Object getInfo() {
		return this.next.getInfo();
	}
	
	public void setInfo(Object someInfo) {
		this.next.setInfo(someInfo);
	}
	
	// Liste umdrehen
	// Parameter: Neuer Nachfolger des aktuellen Knotens
	// Rückgabe: neuer Listenanfang
	private SimpleNode reverse(SimpleNode next) {
		// Merke den letzten Nachfolger
		SimpleNode nextBuf = this.next;
		// Setze den Nachfolger auf den als Parameter übergebenen Knoten (muss nicht NO_EXPR sein...)
		this.next = next;
		
		// War der aktuelle Knoten Listenende?
		if(nextBuf == null) {
			// Gib Referenz auf aktuellen Knoten zurück
			return this;
		} else {
			// Ansonsten delegiere an den nächsten Knoten
			return nextBuf.reverse(this);
		}
	}
	
	protected String getString() {
		return this.getClass().getSimpleName() + "(" + this.value + ")";
	}
	
	// dreht die Liste um, erstellt aus dem Anfangsknoten einen neuen Endknoten und aus dem Endknoten einen neuen normalen Knoten
	public SimpleNode reverse() {
		if(this.next != null) {
			// Neues "vorletztes" Element speichern
			SimpleNode myOldSuccedor = this.next;
			// Altes Ende wird als SimpleNode neu erstellt, gleichzeitig wird die Liste umgekehrt
			SimpleNode oldEnd = (SimpleEndNode)this.reverse(null);
			SimpleNode newBegin = new SimpleNode(oldEnd.value);
			
			// Enden austauschen
			newBegin.next = oldEnd.next;
			
			// neues Ende erstellen
			SimpleEndNode newEnd = new SimpleEndNode(this.value, ((SimpleEndNode)oldEnd).someInfo);
			myOldSuccedor.next = newEnd;
			
			// Cleanup
			oldEnd.next = null;
			this.next = null;
			
			return newBegin;
		} else {
			return this;
		}
	}
	
	public SimpleNode reverseWithEnd() {
		return this.reverse(null);
	}
	
	// toString() zur Ausgabe der Liste
	public String toString() {
		String res = this.getString();
		return this.next == null ? res : res + " | "  + this.next.toString();
	}
	
	// Innere versteckte Klasse fürs Listenende
	private static class SimpleEndNode extends SimpleNode {
		
		// Irgendwelcher Inhalt, der am Ende der Liste stehen soll
		private Object someInfo = null;
		
		//  Einfacher Konstruktor
		public SimpleEndNode(int value, Object someInfo)  {
			super(value);
			this.someInfo = someInfo;
		}
		
		// Info setzen
		@Override
		public void setInfo(Object someInfo) {
			this.someInfo = someInfo;
		}
		
		// Info zurückgeben
		@Override
		public Object getInfo() {
			return this.someInfo;
		}
		
		@Override
		public SimpleNode reverse() {
			return this.reverseWithEnd();
		}
		
		
		public String toString() {
			String res = this.getString() + " By the way, I'm the end node and my additional info is: " + this.someInfo;
			return this.next == null ? res : res + " | "  + this.next.toString();
		}
	}

}
