package dhbw.ndame;

public class SimpleWalker extends Walker {

	public SimpleWalker(Integer size) {
		super(size);
	}

	@Override
	protected void initialize() {
		for(Integer i = 0; i < this.size; i++) {
			this.points[i] = new SimplePoint(0, i, this.size);
		}
	}

	@Override
	protected void walk(Integer ID) {
		if(ID < 0) {
			this.setFinished(true);
			return;
		}
		Integer x = this.points[ID].getX();
		Integer y = this.points[ID].getX();
		if(x + 1 < this.size) {
			this.points[ID].setX(x + 1);
		} else if(y + 1 < this.size) {
			this.points[ID].setY(y + 1);
			this.points[ID].setX(0);
		} else {
			this.walk(ID - 1);
		}
	}

}
