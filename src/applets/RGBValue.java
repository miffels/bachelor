package applets;

import java.awt.Color;

public class RGBValue {
	private Integer r;
	private Integer g;
	private Integer b;
	private Integer mod = 256;
	private static Double	dr,
							dg,
							db,
							curr,
							curg,
							curb;
	
	public RGBValue(Integer r, Integer g, Integer b) {
		this.setRGB(r, g, b);
	}
	
	public RGBValue(Color c) {
		this.setRGB(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public RGBValue(float[] hsb) {
		Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
		this.setRGB(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public void setRGB(Integer r, Integer g, Integer b) {
		this.r = r % this.mod;
		this.g = g % this.mod;
		this.b = b % this.mod;
	}
	
	public Color getColor() {
		return new Color(this.r, this.g, this.b);
	}
	
	public static void initGradient(RGBValue start, RGBValue end, Integer steps) {
		RGBValue.curr = (double)start.r;
		RGBValue.curg = (double)start.g;
		RGBValue.curb = (double)start.b;
		RGBValue.dr = (double)(end.r-start.r)/steps;
		RGBValue.dg = (double)(end.g-start.g)/steps;
		RGBValue.db = (double)(end.b-start.b)/steps;
	}
	
	public static Color nextGradientStep() {
		RGBValue.curr = RGBValue.curr + RGBValue.dr;
		RGBValue.curg = RGBValue.curg + RGBValue.dg;
		RGBValue.curb = RGBValue.curb + RGBValue.db;
		RGBValue curcol = new RGBValue((int)(RGBValue.curr + 0),
				(int)(RGBValue.curg + 0),
				(int)(RGBValue.curb + 0));
		return curcol.getColor();
	}
	
	public String toString() {
		return "RGBValue: r="+r+", g="+g+", b="+b;
	}

}
