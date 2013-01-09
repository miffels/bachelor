package dhbw.sondierung;

import java.util.Random;

public abstract class Hashtable {
	
	protected static final class Type{
		public HashType type = null;
		public Type(HashType type) {
			this.type = type;
		}
	}
	
	// Size - use prime numbers!
	// 1009: Default value
	protected Integer num = 1009;
	protected Integer[] table = null;
	protected Integer putCount = 0;
	protected Integer readCount = 0;
	protected Integer failCount = 0;
	protected Integer sucCount = 0;
	protected Integer sucColl = 0;
	protected Integer failColl = 0;
	protected Integer runCount = 0;
	protected Status status = Status.S0;
	protected Double f50 = 0.0;
	protected Double f90 = 0.0;
	protected Double f95 = 0.0;
	protected Double f100 = 0.0;
	protected Double s50 = 0.0;
	protected Double s90 = 0.0;
	protected Double s95 = 0.0;
	protected Double s100 = 0.0;
	protected Double df50 = 0.0;
	protected Double df90 = 0.0;
	protected Double df95 = 0.0;
	protected Double df100 = 0.0;
	protected Double ds50 = 0.0;
	protected Double ds90 = 0.0;
	protected Double ds95 = 0.0;
	protected Double ds100 = 0.0;
	protected Integer[] reading = null;
	protected Integer[] values = null;
	
	public Hashtable(Integer num) {
		this.num = num;
		this.table = new Integer[num];
	}
	
	public Hashtable() {
	}
	
	public Integer getRest(Integer i) {
		return i < 0 && i % this.num != 0 ? i % this.num + this.num : i % this.num;
	}
	
	public Integer getRest(Integer i, Integer base) {
		return i < 0 && i % base != 0 ? i % base + base : i % base;
	}
	
	public Integer getRest(Double i) {
		return getRest(i.intValue());
	}
	
	private void setStatus(Double progress) {
		if(this.status.equals(Status.S0) && progress >= 0.5) {
			this.status = Status.S50;
			this.read();
			this.s50 = this.sucCount > 0.0 ? (double)this.sucColl/this.sucCount : 0.0;
			this.f50 = this.failCount > 0.0 ? (double)this.failColl/this.failCount : 0.0;
		} else if(this.status.equals(Status.S50) && progress >= 0.9) {
			this.status = Status.S90;
			this.read();
			this.s90 = this.sucCount > 0.0 ? (double)this.sucColl/this.sucCount : 0.0;
			this.f90 = this.failCount > 0.0 ? (double)this.failColl/this.failCount : 0.0;
		} if(this.status.equals(Status.S90) && progress >= 0.95) {
			this.status = Status.S95;
			this.read();
			this.s95 = this.sucCount > 0.0 ? (double)this.sucColl/this.sucCount : 0.0;
			this.f95 = this.failCount > 0.0 ? (double)this.failColl/this.failCount : 0.0;
		} if(this.status.equals(Status.S95) && progress == 1.0) {
			this.status = Status.S100;
			this.read();
			this.s100 = this.sucCount > 0.0 ? (double)this.sucColl/this.sucCount : 0.0;
			this.f100 = this.failCount > 0.0 ? (double)this.failColl/this.failCount : 0.0;
		}
	}
	
	public void run() {
		this.runCount++;
		for(Integer i = 0; i < values.length; i++) {
			setStatus((double)(i+1)/values.length);
			this.put(values[i]);
		}
	}
	
	private void setArrays() {
		Random rand = new Random();
		for(int j = 0; j < this.num; j++) {
			this.values[j] = rand.nextInt(this.num);
			while(this.values[j] == 0) {
				this.values[j] = rand.nextInt(this.num);
			}
		}
		
		for(int j = 0; j < this.reading.length; j++) {
			this.reading[j] = rand.nextInt(this.num);
			while(this.reading[j] == 0) {
				this.reading[j] = rand.nextInt(this.num);
			}
		}
	}
	
	public String runStatistical(Integer loop) {
		for(Integer i = 0; i < loop; i++) {
			this.resetInstance();
			this.setArrays();
			this.run();
			this.evaluateStats();
		}
		return this.getStatistical();
	}
	
	protected void evaluateStats() {
		this.ds50 = ((this.ds50*(this.runCount - 1)) + this.s50)/this.runCount;
		this.ds90 = ((this.ds90*(this.runCount - 1)) + this.s90)/this.runCount;
		this.ds95 = ((this.ds95*(this.runCount - 1)) + this.s95)/this.runCount;
		this.ds100 = ((this.ds100*(this.runCount - 1)) + this.s100)/this.runCount;
		this.df50 = ((this.df50*(this.runCount - 1)) + this.f50)/this.runCount;
		this.df90 = ((this.df90*(this.runCount - 1)) + this.f90)/this.runCount;
		this.df95 = ((this.df95*(this.runCount - 1)) + this.f95)/this.runCount;
		this.df100 = ((this.df100*(this.runCount - 1)) + this.f100)/this.runCount;
	}
	
	protected void resetInstance() {
		this.putCount = 0;
		this.table = new Integer[this.num];
		this.values = new Integer[this.num];
		this.reading = new Integer[this.num*2];
		this.status = Status.S0;
		f50 = 0.0;
		f90 = 0.0;
		f95 = 0.0;
		f100 = 0.0;
		s50 = 0.0;
		s90 = 0.0;
		s95 = 0.0;
		s100 = 0.0;
	}
	
	protected void resetPart() {
		this.readCount = 0;
		this.failColl = 0;
		this.failCount = 0;
		this.sucColl = 0;
		this.sucCount = 0;
	}
	
	protected void read() {
		this.resetPart();
		for(Integer i : reading) {
			this.read(i);
		}
	}
	
	protected boolean read(Integer num) {
		Integer i = 0;
		boolean res = false;
		boolean failure = false;
		Integer hash = 0;
		this.readCount++;
		Integer collCount = 0;
		
		while(i < this.num && !res && !failure) {
			hash = this.getHash(num, i);
			if(this.table[hash] == num) {
				res = true;
			} else if(this.table[hash] == null){
				failure = true;
			} else {
				i++;
				collCount++;
			}
		}
		
		if(!res) {
			this.failCount++;
			this.failColl += collCount;
		} else {
			this.sucCount++;
			this.sucColl += collCount;
		}
		
		return res;
	}
	
	public boolean put(Integer num) {
		Integer i = 0;
		boolean res = false;
		Integer hash = 0;
		
		while(i <= this.num && !res) {
			hash = this.getHash(num, i);
			if(this.table[hash] == null) {
				this.table[hash] = num;
				res = true;
			} else {
				i++;
			}
		}
		this.putCount++;
		return res;
	}
	
	public String toString() {
		String res = this.getStats();
		res += "Failed: " + this.failCount + "\n";
		res += "Successful: " + this.sucCount + "\n";
		for(Integer i = 0; i < table.length; i++) {
			res += i + ": " + table[i] + "\n";
		}
		return res;
	}
	
	public abstract String getStats();
	
	public String getStatistical()  {
		String res = this.getStats();
		res +=	"Success-collisions at 50%: " + this.ds50 + "\n"
			+	"Success-collisions at 90%: " + this.ds90 + "\n"
			+	"Success-collisions at 95%: " + this.ds95 + "\n"
			+	"Success-collisions at 100%: " + this.ds100 + "\n"
			+	"Failure-collisions at 50%: " + this.df50 + "\n"
			+	"Failure-collisions at 90%: " + this.df90 + "\n"
			+	"Failure-collisions at 95%: " + this.df95 + "\n"
			+	"Failure-collisions at 100%: " + this.df100 + "\n";
		return res;
	}
	protected abstract Integer getHash(Integer num, Integer i);
}
