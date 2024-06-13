import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

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
	 * Get the prefered students of the school with the constraint that
	 * their "grade" is > than participant.nbRejects and the school cant
	 * choose more students than its capacity
	 * @return : the list of prefered students
	 */
	public List<Student> getBestPreference() {		
	    List<Student> result = new ArrayList<Student>();
	    Student[] temporaryList = new Student[this.preferences.size() + 1];
		Iterator<Map.Entry<Student, Integer>> iterator = this.preferences.entrySet().iterator();	    
	    
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
		// Si nbCandidats < capacité --> let's go
		if(listOfCandidates.size() <= this.capacity) {
			return listOfCandidates;
		}
		
		// Trier les étudiants par valuePreference la plus basse
		List<Student> studentList = new ArrayList<Student>(listOfCandidates);
		Collections.sort(studentList, (stu1, stu2) -> {
	        return this.preferences.get(stu1) - this.preferences.get(stu2);
	    });
		
	   int currentMin = Integer.MAX_VALUE;
	   HashSet<Student> currentResult = new HashSet<Student>();
	   int nbAccepted = 0;
	   while(nbAccepted < this.capacity) {
			currentResult.add(studentList.get(nbAccepted));
			nbAccepted++;
	   }
		return currentResult;
	}
}
