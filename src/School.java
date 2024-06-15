import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class School extends Participant<Student> {

	private int capacity;
	
	public School (int id, int capacity) {
		super(id);
		this.capacity= capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	@Override
	public String toString() {
		return "id: " + getId() + "\tcapacity: " + capacity + "\n";
	}
	
	/**
	 * Get the prefered students of the school which is not on the rejection's list
	 * @return : the list of prefered students
	 */
	public HashSet<Student> getBestPreference() {		
		HashSet<Student> result = new HashSet<Student>();
	    Student[] temporaryList = new Student[this.preferences.size() + 1];
		Iterator<Map.Entry<Student, Integer>> iterator = this.preferences.entrySet().iterator();	    
	    
		//sort the students in an array according to the school rating
	    while (iterator.hasNext()) {
	    	Map.Entry<Student, Integer> preference = iterator.next();
	        Integer value = preference.getValue(); 
	        temporaryList[value] = preference.getKey();
	    }
	    
	    int nbStudentsChoosen = 0;
	    int i = 1;
	    while (nbStudentsChoosen < this.capacity && i<temporaryList.length) {
	    	Student studentIter = temporaryList[i];
	    	if (!this.rejections.contains(studentIter)) {
	    		result.add(studentIter);
	    		nbStudentsChoosen++;
	    	}
	    	i++;
	    }	    
	    return result;
	}
	
	/**
	 * Get a list of prefered student among the set of student candidates
	 * @return : the list of preferred students
	 * 
	 * { list.size() <= school.capacity }
	 */
	public HashSet<Student> getPreferencesAmongList(HashSet<Student> listOfCandidates) {
		if(listOfCandidates.size() <= this.capacity) {
			return listOfCandidates;
		}
		
		// sort the students with the school preferences
		List<Student> studentList = new ArrayList<Student>(listOfCandidates);
		Collections.sort(studentList, (stu1, stu2) -> 
	        this.preferences.get(stu1) - this.preferences.get(stu2)
		);
		
	   HashSet<Student> currentResult = new HashSet<Student>();
	   int nbAccepted = 0;
	   while(nbAccepted < this.capacity) {
			currentResult.add(studentList.get(nbAccepted));
			nbAccepted++;
	   }
		return currentResult;
	}
}
