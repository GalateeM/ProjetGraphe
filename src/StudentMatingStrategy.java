
public class StudentMatingStrategy implements MatingStrategy {

	private MatchingManager<School, Student> matchingManager;
	
	public StudentMatingStrategy(MatchingManager<School, Student> manager) {
		this.matchingManager = manager;
	}
	
	@Override
	public void executeCeremony() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeReject() {
		// TODO Auto-generated method stub
		
	}

}
