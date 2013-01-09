package dhbw.events;

public class Birds implements MoodListener {

	public void moodReceived(MoodEvent event) {
		if(event.mood() == Mood.HAPPY) {
			System.out.println("Birds are singing.");
		} else if(event.mood() == Mood.ANNOYED) {
			System.out.println("Birds are silent.");
		} else {
			System.out.println("Birds are flying away.");
		}
	}

}
