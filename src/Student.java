import java.util.Iterator;
import java.util.Map;

public class Student extends Participant<School> {
	
	public Student(int id) {
		super(id);
	}
	
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
	
	
	public String toString() {
		return "" + getId();
	}

}
