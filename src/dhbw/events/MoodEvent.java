package dhbw.events;

import java.util.EventObject;

public class MoodEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	private Mood mood;
	
	public MoodEvent(Object source, Mood nMood) {
		super(source);
		this.mood = nMood;
	}
	
	public Mood mood() {
		return this.mood;
	}

}
