import java.util.HashMap;
import java.util.HashSet;

public abstract class Participant <E extends Participant> {
	
	//The id of the participant
	private int id;
	
	//The preferences of the participant
	//(associate the object E with the value of preference
	protected HashMap<E, Integer> preferences;
	
	//The set of rejections of the participant
	protected HashSet<E> rejections;
	
	protected Participant(int id) {
		this.id = id;
		this.rejections = new HashSet<E>();
	}

	public int getId() {
		return id;
	}

	public HashMap<E, Integer> getPreferences() {
		return preferences;
	}

	public void setPreferences(HashMap<E, Integer> preferences) {
		this.preferences = preferences;
	}
	
	
	/**
	 * Add the participant who rejected the instance in the set of rejections
	 * @param participant
	 */
	public void addReject(E participant) {
		this.rejections.add(participant);
	}	
	
}
