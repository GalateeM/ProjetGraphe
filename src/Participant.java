import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Participant <E extends Participant> {
	
	//The id of the participant
	private int id;
	
	//The preferences of the participant
	//(associate the object E with the value of preference
	private HashMap<E, Integer> preferences;
	
	//The number of rejections of the participant
	private int nbRejects;
	
	public Participant(int id) {
		this.id = id;
		this.nbRejects = 0;
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
	 * Get the prefered object E of the participant with the constraint that
	 * is "grade" is > than participant.nbRejects
	 * @return : the prefered object
	 */
	public E getBestPreference() {
	    E result = null;
		Iterator<Map.Entry<E, Integer>> iterator = this.preferences.entrySet().iterator();
	    boolean estTrouve = false;
	    while (iterator.hasNext() && !estTrouve) {
	        Map.Entry<E, Integer> preference = iterator.next();
	        Integer value = preference.getValue();
	        if(value == this.nbRejects + 1) {
	        	estTrouve = true;
	        	result = preference.getKey();
	        }
	    }
	    return result;
	}
	
	
}
