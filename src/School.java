
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
}
