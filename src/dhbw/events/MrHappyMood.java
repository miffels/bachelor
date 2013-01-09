package dhbw.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MrHappyMood {

	private Mood mood = Mood.HAPPY;
	private List<MoodListener> listeners = new ArrayList<MoodListener>();
	
	
	public synchronized void pinch() {
		System.out.println("Freudi is being pinched!");
		if(this.mood == Mood.HAPPY) {
			this.mood = Mood.ANNOYED;
		} else {
			this.mood = Mood.ANGRY;
		}
		this.fireMoodEvent();
	}
	
	public synchronized void hug() {
		System.out.println("Freudi is being hugged!");
		if(this.mood == Mood.ANGRY) {
			this.mood = Mood.ANNOYED;
		} else {
			this.mood = Mood.HAPPY;
		}
		this.fireMoodEvent();
	}
	
	public void addMoodListener(MoodListener l) {
		this.listeners.add(l);
	}
	
	public void removeMoodListener(MoodListener l) {
		this.listeners.remove(l);
	}
	
	private synchronized void fireMoodEvent() {
		MoodEvent me = new MoodEvent(this, this.mood);
		Iterator<MoodListener> it = this.listeners.iterator();
		while(it.hasNext()) {
			((MoodListener)it.next()).moodReceived(me);
		}
	}
}
