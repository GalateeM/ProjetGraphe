import java.util.HashMap;

public class Ecole extends Participant {

	private int capacite;
	
	public Ecole (int id, int capacite) {
		super(id);
		this.capacite = capacite;
	}
	
	public int getCapacite() {
		return capacite;
	}
}
