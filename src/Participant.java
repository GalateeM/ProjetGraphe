import java.util.HashMap;

public abstract class Participant {
	private int id;
	private HashMap<Participant, Integer> preferences;
	
	public Participant(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public HashMap<Participant, Integer> getPreferences() {
		return preferences;
	}

	public void setPreferences(HashMap<Participant, Integer> preferences) {
		this.preferences = preferences;
	}
	
	
}
