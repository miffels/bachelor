package dhbw.events;

public class Sky implements MoodListener {
	
	public void moodReceived(MoodEvent event) {
		if(event.mood() == Mood.HAPPY) {
			System.out.println("Sun shines.");
		} else if(event.mood() == Mood.ANNOYED) {
			System.out.println("Cloudy skies.");
		} else {
			System.out.println("Lightnings rain down the skies.");
		}
	}

}