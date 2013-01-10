package applets;

import java.awt.Point;

public class Ring2D {
	private Ring2D next = null;
	private Ring2D last = null;
	private Integer angle = 0;
	public Point o = new Point();
	private Ring2D currentDirection = null;
	
	public static Ring2D getRing(Integer nume, Double h, Integer start, Double angleOffset) {
		Ring2D first = null;
		Ring2D last = null;
		Ring2D r = null;
		Double alpha = new Double(360/nume);
		
		
		for(Integer i = 0; i < nume; i++) {
			r = new Ring2D((int)(h*Math.cos(Math.toRadians(angleOffset + i*alpha))), (int)(h*Math.sin(Math.toRadians(angleOffset + i*alpha))), (int)(angleOffset + alpha*i));
			if(r.o.x == 0 && r.o.y == 0) {
				Double x = h*Math.cos(Math.toRadians(angleOffset + i*alpha));
				Double y = h*Math.sin(Math.toRadians(angleOffset + i*alpha));
				if(x > y) {
					r.o.x = (int)(1*Math.signum(x));
				} else if(x == y) {
					r.o.x = (int)(1*Math.signum(x));
					r.o.y = (int)(1*Math.signum(y));
				} else {
					r.o.y = (int)(1*Math.signum(y));
				}
			}
			if(last != null) {
				r.last = last;
				last.next = r;
			} else {
				first = r;
			}
			last = r;
		}
		
		r.next = first;
		first.last = r;
		
		for(Integer i = 0; i < start; i++) {
			first = first.next;
		}
		
		first.setDirection(first);
		return r.currentDirection;
	}
	
	public static Ring2D getRing(Integer nume, Double h, Integer start) {
		return Ring2D.getRing(nume, h, start, new Double(0));
	}
	
	public Point move(char direction) {
		if(direction == 'F') {
			this.currentDirection.setDirection(this.currentDirection.next);
		} else {
			this.currentDirection.setDirection(this.currentDirection.last);
		}
		return this.currentDirection.o;
	}
	
	public Point getPoint() {
		return this.o;
	}
	
	private void setDirection(Ring2D r) {
		if(r != this.currentDirection) {
			this.currentDirection = r;
			this.next.setDirection(r);
		}
	}
	
	private Ring2D(Integer ox, Integer oy, Integer angle) {
		this.o.x = ox;
		this.o.y = oy;
		this.angle = angle;
	}
	
	public String toString() {
		return "X: "+ this.currentDirection.o.x + " Y: " + this.currentDirection.o.y + " Angle: " + this.currentDirection.angle;
	}

}
