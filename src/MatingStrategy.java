
public interface MatingStrategy {

	/**
	 * Match the e2 and e1 according to e2 preferences
	 */
	void executeCeremony();
	
	/**
	 * The e1 reject every e2 except its prefered one
	 */
	void executeReject();
	 
	/**
	 * Check if the matching is complete
	 * @return : is the matching is complete
	 * TODO : changer la condition : soit en checkant si toutes les capacites max sont remplies
	 * soit en checkant que tous les etudiants soient affectes
	 */
	boolean executeIsComplete();
	
	/**
	 * Save the result of the matching into a file
	 */
	void saveResult();
}
