package applets;

import javax.swing.JPanel;

public abstract class AppletShape extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public abstract String toString();
	public abstract void setIntParam(Integer param);
	
	public AppletShape() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] concatArray(T[] ar1, T[] ar2) {
		T[] ar3 = (T[])(new Object[ar1.length+ar2.length]);
		System.arraycopy(ar1, 0, ar3, 0, ar1.length);
		System.arraycopy(ar1, 0, ar3, ar1.length, ar2.length);
		return ar3;
	}

}