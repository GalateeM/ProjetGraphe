
public class Student extends Participant<School> {
	
	public Student(int id) {
		super(id);
	}
	
	public String toString() {
		return "" + getId();
	}

}
