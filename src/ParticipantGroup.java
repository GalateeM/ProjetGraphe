import java.util.ArrayList;
import java.util.List;

public class ParticipantGroup {

	private List<Student> students;
	private List<School> schools;	
	
	public ParticipantGroup() {
		this.students = new ArrayList<Student>();
		this.schools = new ArrayList<School>();
	}
	
	public void addStudent(Student student) {
		this.students.add(student);
	}
	
	public void addSchool(School school) {
		this.schools.add(school);
	}
}
