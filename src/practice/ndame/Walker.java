package practice.ndame;

import java.awt.geom.Point2D;

public abstract class Walker {
	
	protected SimplePoint[] points = null;
	protected Integer size = null;
	protected boolean finished = false;
	
	public Walker(Integer size) {
		if(size <= 0) {
			throw new IllegalArgumentException();
		}
		this.points = new SimplePoint[size];
		this.size = size;
		this.initialize();
	}
	
	protected abstract void initialize();
	protected abstract void walk(Integer ID);
	
	public SimplePoint[] next() {
		this.walk(this.size - 1);
		return this.points;
	}
	
	protected void setFinished(boolean state) {
		this.finished = state;
	}
	
	protected boolean isFinished() {
		return this.finished;
	}
	
	public String toString() {
		return this.points.toString();
	}

}
