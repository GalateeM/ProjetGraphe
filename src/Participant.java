import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public abstract class Participant <E extends Participant> {
	
	//The id of the participant
	private int id;
	
	//The preferences of the participant
	//(associate the object E with the value of preference
	protected HashMap<E, Integer> preferences;
	
	//The set of rejections of the participant
	protected HashSet<E> rejections;
	
	public Participant(int id) {
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
	 * Get the prefered object E among the set of E (list of candidates)
	 * @return : the prefered object
	 */
	public E getPreferenceAmongList(HashSet<E> listOfCandidates) {
	   int currentMin = Integer.MAX_VALUE;
	   E currentResult = null;
		for(E candidate : listOfCandidates) {
			int valuePreference = this.preferences.get(candidate);
			if(valuePreference < currentMin) {
				currentMin = valuePreference;
				currentResult = candidate;
			}
		}
		return currentResult;
	}
	
	/**
	 * Add the participant who rejected the instance in the set of rejections
	 * @param participant
	 */
	public void addReject(E participant) {
		this.rejections.add(participant);
	}	
	
}
