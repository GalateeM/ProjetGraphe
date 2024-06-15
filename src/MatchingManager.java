import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MatchingManager <E1 extends Participant, E2 extends Participant> {
	
	private int counter; // counter of rounds
	
	private List<E2> rejected;
	
	private List<E1> e1List;
	
	private List<E2> e2List;
	
	private HashMap<E1, HashSet<E2>> currentAssociation;
	
	private MatingStrategy strategy;
	
	public MatchingManager(List<E1> e1List, List<E2> e2List) {
		this.counter = 0;
		rejected = new ArrayList<E2>();
		this.e1List = e1List;
		this.e2List = e2List;
		rejected.addAll(e2List);
		
		//all the e2 are not associated at first 
		currentAssociation = new HashMap<E1, HashSet<E2>>();
	}

	/**
	 * Associates the e1 to e2 (students to schools or schools to students)
	 */
	public void findAssociation() {
		counter = 0; // counter of rounds
		
		while(!this.strategy.executeIsComplete()) {
			//Match the e2 and e1 according to e2 preferences
			this.strategy.executeCeremony();
			//The e1s reject every e2 except its prefered one
			this.strategy.executeReject();
			counter ++;
		}
		this.strategy.saveResult();
		System.out.println("Final state:");
		System.out.println("currentAssociation:\n  " + this.currentAssociation);
		System.out.println("Nb rounds needed to achieve coverage : " + this.counter);
	}
	
	/**
	 * Set the strategy
	 * @param strategy the type of strategy
	 */
	public void setStrategy(String strategy) {
		switch (strategy) {
		case "student" :
			this.strategy = new StudentMatingStrategy((MatchingManager<School, Student>) this);
			break;
		case "school" :
			this.strategy = new SchoolMatingStrategy((MatchingManager<Student, School>) this);
			break;
		default:
			break;
		}
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public List<E2> getRejected() {
		return rejected;
	}

	public void setRejected(List<E2> rejected) {
		this.rejected = rejected;
	}

	public List<E1> getE1List() {
		return e1List;
	}

	public void setE1List(List<E1> e1List) {
		this.e1List = e1List;
	}

	public List<E2> getE2List() {
		return e2List;
	}

	public void setE2List(List<E2> e2List) {
		this.e2List = e2List;
	} 

	public HashMap<E1, HashSet<E2>> getCurrentAssociation() {
		return currentAssociation;
	}

	public void setCurrentAssociation(HashMap<E1, HashSet<E2>> currentAssociation) {
		this.currentAssociation = currentAssociation;
	}
}
