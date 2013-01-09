package dhbw.events;

public class Test_exec {

	public static void main(String[] args) {
		MrHappyMood freudi = new MrHappyMood();
		Birds birds = new Birds();
		Sky sky = new Sky();
		
		freudi.addMoodListener(birds);
		freudi.addMoodListener(sky);
		
		freudi.pinch();
		freudi.hug();
		freudi.pinch();
		freudi.pinch();
		freudi.pinch();
		freudi.hug();
		freudi.hug();
		freudi.hug();

	}

}
