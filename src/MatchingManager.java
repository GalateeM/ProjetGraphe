import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
		
		while(!isComplete()) {
			//Match the e2 and e1 according to e2 preferences
			this.strategy.executeCeremony();
			//The e1s reject every e2 except its prefered one
			this.strategy.executeReject();
			counter ++;
		}
		System.out.println(this.currentAssociation);
		System.out.println(this.counter);
	}
	
	/**
	 * Match the e2 and e1 according to e2 preferences
	 */
	private void doCeremony() {
		for(E2 e2Iter : this.e2List) {
			if(this.rejected.contains(e2Iter)) {
				
				//TODO : changer ici : prendre 1 meilleur preference si etudiant bidding
				// prendre 'capacite' meilleure preference si ecole bidding
				E1 preferedE1 = (E1)e2Iter.getBestPreference();
				 if(this.currentAssociation.get(preferedE1) == null) {
					 this.currentAssociation.put(preferedE1, new HashSet<E2>());
				 }
				 this.currentAssociation.get(preferedE1).add(e2Iter);
			}
			 
		 }
	}
	
	/**
	 * The e1 reject every e2 except its prefered one
	 */
	private void doReject() {
		//clear all rejected
		this.rejected.removeAll(rejected);
		
		//Loop on the e1s
		for (Map.Entry<E1, HashSet<E2>> association : this.currentAssociation.entrySet()) {
			HashSet<E2> e2Set = association.getValue();
			E1 e1Person = association.getKey();
			
			if(e2Set.size() > 1) {
				//choose prefered one
				//TODO : the prefered one if school bidding
				// the 'capacite' ones if student bidding
				E2 preferedE2 = (E2)e1Person.getPreferenceAmongList(e2Set);
				
				//update the rejections
				for(E2 e2Person : e2Set) {
					if(e2Person != preferedE2) {
						e2Person.incrementNbRejects();
						rejected.add(e2Person);
					}
				}
				
				//replace the list with only the prefered one
				HashSet<E2> oneElementSet = new HashSet<E2>();
				oneElementSet.add(preferedE2);
				this.currentAssociation.put(e1Person, oneElementSet);
			}
			
        }
	}

	/**
	 * Check if the matching is complete
	 * @return : is the matching is complete
	 * TODO : changer la condition : soit en checkant si toutes les capacites max sont remplies
	 * soit en checkant que tous les etudiants soient affectes
	 */
	private boolean isComplete() {
		return this.currentAssociation.size() == e1List.size();
	}
	
	public void setStrategy(String strategy) {
		switch (strategy) {
		case "student" :
			this.strategy = new StudentMatingStrategy();
			break;
		case "school" :
			this.strategy = new SchoolMatingStrategy();
			break;
		default:
			break;
		}
	}
}
