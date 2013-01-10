package dhbw.ndame;

public class SimplePoint {
	
	private Integer x = null;
	private Integer y = null;
	private Integer pos = null;
	private Integer size = null;
	
	public SimplePoint(Integer x, Integer y, Integer size) {
		this.setX(x);
		this.setY(y);
	}
	
	public void setX(Integer x) {
		if(x < 0 || x > this.size - 1) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.pos = this.x * this.size + this.y;
	}
	
	public void setY(Integer y) {
		if(y < 0 || y > this.size - 1) {
			throw new IllegalArgumentException();
		}
		this.y = y;
		this.pos = this.x * this.size + this.y;
	}
	
	public void setPos(Integer pos) {
		if(pos < 0 || pos > this.size*this.size - 1) {
			throw new IllegalArgumentException();
		}
		this.pos = pos;
		this.x = pos / this.size;
		this.y = pos % this.size;
	}
	
	public Integer getX() {
		return this.x;
	}
	
	public Integer getY() {
		return this.y;
	}
	
	public Integer getPos() {
		return this.pos;
	}
	
	public String toString() {
		return "X: " + this.x + ", Y: " + this.y + ", Position: " + this.pos;
	}
}
