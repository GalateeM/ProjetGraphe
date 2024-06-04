
public class School extends Participant<Student> {

	private int capacity;
	
	public School (int id, int capacity) {
		super(id);
		this.capacity= capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}
}
