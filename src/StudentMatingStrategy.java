import java.util.HashSet;
import java.util.Map;

public class StudentMatingStrategy implements MatingStrategy {

	private MatchingManager<School, Student> matchingManager;
	private int totalSchoolsCapacities;
	
	public StudentMatingStrategy(MatchingManager<School, Student> manager) {
		this.matchingManager = manager;
		
		for (School school : this.matchingManager.getE1List()) {
			totalSchoolsCapacities += school.getCapacity();
		}
	}
	
	@Override
	public void executeCeremony() {
		for(Student student : this.matchingManager.getE2List()) {		
			if(this.matchingManager.getRejected().contains(student)) {
 				 School preferedSchool = student.getBestPreference(); 				 
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
				//create a set prefered students
				HashSet<Student> preferedStudents = school.getPreferencesAmongList(studentSet);

				//update the rejections
				for(Student student : studentSet) {
					if(!preferedStudents.contains(student)) {
						student.addReject(school);
						this.matchingManager.getRejected().add(student);
					}
				}

				//replace the list with only the prefered ones
				this.matchingManager.getCurrentAssociation().put(school, preferedStudents);
			}

		}
	}
	
	@Override
	public boolean executeIsComplete() {
		int nbStudentsWithSchool = 0;
		for (Map.Entry<School, HashSet<Student>> association : this.matchingManager.getCurrentAssociation().entrySet()) {
			nbStudentsWithSchool += association.getValue().size();
		}
		
		return nbStudentsWithSchool == this.matchingManager.getE2List().size() ||
				nbStudentsWithSchool == this.totalSchoolsCapacities;
	}

	@Override 
	public void saveResult() {
		Main.saveResultToFile(this.matchingManager.getCurrentAssociation());
	}
}
