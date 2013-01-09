package dhbw.events;

public class Mood {
	
	public static final Mood ANGRY = new Mood("angry");
	public static final Mood ANNOYED = new Mood("annoyed");
	public static final Mood HAPPY = new Mood("happy");
	
	private String mood;
	
	private Mood (String nMood) {
		this.mood = nMood;
	}
	
	public String toString() {
		return this.mood;
	}

}
