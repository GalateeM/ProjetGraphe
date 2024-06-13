import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class StudentMatingStrategy implements MatingStrategy {

	private MatchingManager<School, Student> matchingManager;
	
	public StudentMatingStrategy(MatchingManager<School, Student> manager) {
		this.matchingManager = manager;
		
		
	}
	
	@Override
	public void executeCeremony() {
		for(Student student : this.matchingManager.getE2List()) {		
			if(this.matchingManager.getRejected().contains(student)) {
 				 School preferedSchool = (School)student.getBestPreference(); 				 
 				 if(this.matchingManager.getCurrentAssociation().get(preferedSchool) == null) {
					 this.matchingManager.getCurrentAssociation().put(preferedSchool, new HashSet<Student>());
				 }
 				 if(preferedSchool != null) {
 					 this.matchingManager.getCurrentAssociation().get(preferedSchool).add(student); 					 
 				 }
			}
		 }
	}

	@Override
	public void executeReject() {
		//clear all rejected
		this.matchingManager.getRejected().removeAll(this.matchingManager.getRejected());

		//Loop on the students
		for (Map.Entry<School, HashSet<Student>> association : this.matchingManager.getCurrentAssociation().entrySet()) {
			HashSet<Student> studentSet = association.getValue();
			School school = association.getKey();

			if(studentSet.size() > school.getCapacity()) {
				//create a list prefered students
				// !!!!! Must return a LIST !!!!!
				HashSet<Student> preferedStudents = school.getPreferencesAmongList(studentSet);

				//update the rejections
				for(Student student : studentSet) {
					if(!preferedStudents.contains(student)) {
						student.addReject(school);
						this.matchingManager.getRejected().add(student);
					}
				}

				//replace the list with only the prefered ones
				for(Student student : preferedStudents) {
					HashSet<Student> oneElementSet = new HashSet<Student>();
					oneElementSet.add(student);
					this.matchingManager.getCurrentAssociation().put(school, oneElementSet);
				}
			}

		}
	}
	
	@Override
	public boolean executeIsComplete() {
		return this.matchingManager.getCurrentAssociation().size() == this.matchingManager.getE1List().size();
	}

	@Override
	public void saveResult() {
		// TODO Auto-generated method stub
		
	}
}
