import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Student extends Participant<School> {
	
	public Student(int id) {
		super(id);
	}
	
	/**
	 * Get the prefered school of the student which is not in the rejection's list
	 * @return : the prefered school
	 */
	public School getBestPreference() {		
		School preferedSchool = null;
		
		Iterator<Map.Entry<School, Integer>> iterator = this.preferences.entrySet().iterator();
		while(iterator.hasNext() && preferedSchool == null) {
			Map.Entry<School, Integer> currentPreference = iterator.next();
			if(currentPreference.getValue() == this.rejections.size() + 1) {
				preferedSchool = currentPreference.getKey();
			}
		}
		
		return preferedSchool;
	}
	
	/**
	 * Get the prefered School among the set of Schools (list of candidates)
	 * @param listOfCandidates the list of schools which has accepted the student
	 * @return : the prefered object
	 */
	public School getPreferenceAmongList(HashSet<School> listOfCandidates) {
	   int currentMin = Integer.MAX_VALUE;
	   School currentResult = null;
		for(School candidate : listOfCandidates) {
			int valuePreference = this.preferences.get(candidate);
			if(valuePreference < currentMin) {
				currentMin = valuePreference;
				currentResult = candidate;
			}
		}
		return currentResult;
	}
	
	
	public String toString() {
		return "" + getId();
	}

}
