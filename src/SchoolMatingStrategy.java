
public class SchoolMatingStrategy implements MatingStrategy {

	private MatchingManager<Student, School> matchingManager;
	
	public SchoolMatingStrategy(MatchingManager<Student, School> manager) {
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
