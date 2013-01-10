package dhbw.uniquelist;

import java.util.HashSet;
import java.util.Set;

public class Studi {
	private static final Set<String> matrnrs = new HashSet<String>();

	private String matrnr = null;
	
	public Studi(String matrnr) throws StudiAlreadyExistsException {
		if(!matrnrs.contains(matrnr)) {
			matrnrs.add(matrnr);
			this.matrnr = matrnr;
		} else {
			throw new StudiAlreadyExistsException();
		}
	}

	public static boolean remove(Studi studi) {
		if(studi == null) {
			return false;
		} else {
			return matrnrs.remove(studi.matrnr);
		}
	}
	
	public static class StudiAlreadyExistsException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}