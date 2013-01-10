package applets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class ShapeFactory {
	public Integer DRAGONCURVE = 0;
	private static ShapeFactory factory = new ShapeFactory();
	private Integer shape = null;
	
	public static AppletShape getShape(Integer shape) {
		ShapeFactory.factory.shape = shape;
		return ShapeFactory.factory.returnShape();
	}
	
	private AppletShape returnShape() {
		switch(this.shape) {
			case 0:
				return new DragonCurve(0);
			case 1:
				return new QuadragonCurve(0);
			case 2:
				return new FibonacciTree(0);
			default:
				return null;
		}
	}
	
	private class DragonCurve extends AppletShape {
		private static final long serialVersionUID = 1L;
		protected Integer degree,
						x = 200,
						y = 200;
		protected String pattern = null;
		protected Ring2D ring = null;
		protected Graphics2D stage;
		
		public DragonCurve(Integer degree) {
			super();
			this.degree = degree;
			this.setPattern(1);
			this.setPreferredSize(new Dimension(2000, 2000));
		}
		
		private void setPattern(Integer degree) {
			if(degree > this.degree) {
				return;
			} else {
				if(this.pattern.equals("")) {
					this.pattern = "F";
				} else {
					char[] car = this.pattern.toCharArray();
					car[(Integer)(car.length / 2)] = 'B';
					String buffer = new String(car);
					this.pattern =	this.pattern + "F" + buffer;
				}
				this.setPattern(degree + 1);
			}
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			this.stage = (Graphics2D)g;
			Integer x = Math.round((int)(200*Math.pow(1.05, this.degree)));
			Integer y = Math.round((int)(200*Math.pow(1.05, this.degree)));
			Integer scale = this.degree < 1 ? 1 : this.degree;
			float[] shsb = Color.RGBtoHSB(Sample.gradientStart.getColor().getRed(),
					Sample.gradientStart.getColor().getGreen(),
					Sample.gradientStart.getColor().getBlue(),
					null);
			float[] ehsb = Color.RGBtoHSB(Sample.gradientEnd.getColor().getRed(),
					Sample.gradientEnd.getColor().getGreen(),
					Sample.gradientEnd.getColor().getBlue(),
					null);

			this.x = x;
			this.y = y;
			RGBValue.initGradient(new RGBValue(shsb), new RGBValue(ehsb), this.pattern.length() + 1);
			this.ring = Ring2D.getRing(4, new Double(20/scale), 3, new Double(180)); 
			this.buildCurve(this.pattern.toCharArray());
		}
		
		protected void buildCurve(final char[] pattern) {
			Point p = this.ring.o;
			this.stage.setColor(RGBValue.nextGradientStep());
			this.stage.drawLine(this.x, this.y, this.x + p.x, this.y + p.y);
			this.x = this.x + p.x;
			this.y = this.y + p.y;
			for(Integer i = 0; i < pattern.length; i++) {
				this.stage.setColor(RGBValue.nextGradientStep());
				p = this.ring.move(pattern[i]);
				this.stage.drawLine(this.x, this.y, this.x + p.x, this.y + p.y);
				this.x = this.x + p.x;
				this.y = this.y + p.y;
			}
		}

		public void setIntParam(Integer param) {
			this.degree = param;
			this.pattern = "";
			this.setPattern(1);
		}

		public String toString() {
			return this.pattern;
		}
	}
	
	private class QuadragonCurve extends DragonCurve {

		private static final long serialVersionUID = 1L;

		public QuadragonCurve(Integer degree) {
			super(degree);
		}
		
		public void paintComponent(Graphics g) {
			this.stage = (Graphics2D)g;
			Integer x = Math.round((int)(200*Math.pow(1.05, this.degree)));
			Integer y = Math.round((int)(200*Math.pow(1.05, this.degree)));
			Integer scale = this.degree < 1 ? 1 : this.degree;
			float[] shsb = Color.RGBtoHSB(Sample.gradientStart.getColor().getRed(),
					Sample.gradientStart.getColor().getGreen(),
					Sample.gradientStart.getColor().getBlue(),
					null);
			float[] ehsb = Color.RGBtoHSB(Sample.gradientEnd.getColor().getRed(),
					Sample.gradientEnd.getColor().getGreen(),
					Sample.gradientEnd.getColor().getBlue(),
					null);

			this.x = x;
			this.y = y;
			RGBValue.initGradient(new RGBValue(shsb), new RGBValue(ehsb), this.pattern.length() + 1);
			this.ring = Ring2D.getRing(4, new Double(20/scale), 3, new Double(0)); 
			this.buildCurve(this.pattern.toCharArray());
			
			shsb[2] = shsb[2] * 1.1f;
			ehsb[2] = ehsb[2] * 1.1f;
			this.x = x;
			this.y = y;
			RGBValue.initGradient(new RGBValue(shsb), new RGBValue(ehsb), this.pattern.length() + 1);
			this.ring = Ring2D.getRing(4, new Double(20/scale), 3, new Double(90)); 
			this.buildCurve(this.pattern.toCharArray());
			
			shsb[2] = shsb[2] * 0.9f;
			ehsb[2] = ehsb[2] * 0.9f;
			this.x = x;
			this.y = y;
			RGBValue.initGradient(new RGBValue(shsb), new RGBValue(ehsb), this.pattern.length() + 1);
			this.ring = Ring2D.getRing(4, new Double(20/scale), 3, new Double(180)); 
			this.buildCurve(this.pattern.toCharArray());
			
			shsb[2] = shsb[2] * 0.8f;
			ehsb[2] = ehsb[2] * 0.8f;
			this.x = x;
			this.y = y;
			RGBValue.initGradient(new RGBValue(shsb), new RGBValue(ehsb), this.pattern.length() + 1);
			this.ring = Ring2D.getRing(4, new Double(20/scale), 3, new Double(270)); 
			this.buildCurve(this.pattern.toCharArray());
		}
		
	}
	
	public class FibonacciTree extends AppletShape {
		
		private static final long serialVersionUID = 1L;
		private FibonacciTree
			left = null,
			right = null;
		private Integer
			value = null;
		private FibonacciIterator
			it = null;
		
		public FibonacciTree(Integer degree) {
			super();
			this.it = new FibonacciIterator(degree);
			this.setIntParam(degree);
		}

		public void setIntParam(Integer param) {
			if(param >= 1) {
				this.value = param;
			}
			if(param >= 2) {
				this.left = new FibonacciTree(param - 1);
				this.right = new FibonacciTree(param - 2);
			}
		}

		public String toString() {
			return null;
		}
		
		public Integer[] getDimensions() {
			if(this.value >= 1) {
				System.out.println(this.left);
				this.left.it = this.it.getNext();
				this.left.getDimensions();
				this.right.it = this.it.getNext().getNext();
				this.right.getDimensions();
			}
			if(this.value != null) {
				this.it.tick();
				return this.it.traverse();
			}
			return null;
		}
		
		public class FibonacciIterator {
			private Integer
				degree = null,
				value = 0;
			private FibonacciIterator next = null;
			
			public FibonacciIterator(Integer degree) {
				this.degree = degree;
				this.buildIterator();
			}
			
			private void buildIterator() {
				if(this.degree > 1) {
					this.next = new FibonacciIterator(this.degree - 1);
				}
			}
			
			public FibonacciIterator getNext() {
				return this.next;
			}
			
			public void tick() {
				this.value++;
			}
			
			private Integer[] traverse() {
				return AppletShape.concatArray(new Integer[] {this.value}, this.next.traverse());
			}
			
			public String toString() {
				return this.value + "";
			}
		}
	}
	
	private ShapeFactory() {}

}
