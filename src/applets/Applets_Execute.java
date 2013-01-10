package applets;

import applets.ShapeFactory.FibonacciTree;

public class Applets_Execute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FibonacciTree s = (FibonacciTree)ShapeFactory.getShape(2);
		s.setIntParam(2);
		s.getDimensions();
	}

}
