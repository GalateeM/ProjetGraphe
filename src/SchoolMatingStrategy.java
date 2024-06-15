import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SchoolMatingStrategy implements MatingStrategy {

	private MatchingManager<Student, School> matchingManager;
	private int totalSchoolsCapacities;

	public SchoolMatingStrategy(MatchingManager<Student, School> manager) {
		this.matchingManager = manager;
		
		for (School school : this.matchingManager.getE2List()) {
			totalSchoolsCapacities += school.getCapacity();
		}
	}

	@Override
	public void executeCeremony() {
		for(School school : this.matchingManager.getE2List()) {
			if(this.matchingManager.getRejected().contains(school)) {
				HashSet<Student> students = school.getBestPreference();
				for(Student student : students) {
					if(this.matchingManager.getCurrentAssociation().get(student) == null) {
						this.matchingManager.getCurrentAssociation().put(student, new HashSet<School>());
					}
					this.matchingManager.getCurrentAssociation().get(student).add(school);
				}
			}			 
		}
	}

	@Override
	public void executeReject() {
		//clear all rejected
		this.matchingManager.getRejected().removeAll(this.matchingManager.getRejected());

		//Loop on the students
		for (Map.Entry<Student, HashSet<School>> association : this.matchingManager.getCurrentAssociation().entrySet()) {
			HashSet<School> schoolSet = association.getValue();
			Student student = association.getKey();

			if(schoolSet.size() > 1) {
				//choose prefered one
				School preferedSchool = student.getPreferenceAmongList(schoolSet);

				//update the rejections
				for(School school : schoolSet) {
					if(school != preferedSchool) {
						school.addReject(student);
						this.matchingManager.getRejected().add(school);
					}
				}

				//replace the list with only the prefered one
				HashSet<School> oneElementSet = new HashSet<School>();
				oneElementSet.add(preferedSchool);
				this.matchingManager.getCurrentAssociation().put(student, oneElementSet);
			}

		}
	}

	/**
	 * Check if the matching is complete.
	 * => All the students have a school OR the total capacity of schools equals the number of
	 * students assigned to a school
	 * @return : is the matching is complete
	 */
	@Override
	public boolean executeIsComplete() {
		int nbStudentsWithSchool = 0;
		for (Map.Entry<Student, HashSet<School>> association : this.matchingManager.getCurrentAssociation().entrySet()) {
			if (!association.getValue().isEmpty()) {
				nbStudentsWithSchool++;
			}
		}
		return this.matchingManager.getCurrentAssociation().size() == this.matchingManager.getE1List().size() ||
				nbStudentsWithSchool == this.totalSchoolsCapacities;
	}

	@Override
	public void saveResult() {
		HashMap<School, HashSet<Student>> reverseAssociation = new HashMap<School, HashSet<Student>>();
		for(Map.Entry<Student, HashSet<School>> association : this.matchingManager.getCurrentAssociation().entrySet()) {
			for(School schoolIter : association.getValue()) {
				//if the key does not exist
				if(!reverseAssociation.containsKey(schoolIter)) {
					reverseAssociation.put(schoolIter, new HashSet<>());
				}
				reverseAssociation.get(schoolIter).add(association.getKey());
			}
		}
		Main.saveResultToFile(reverseAssociation);
	}
}
