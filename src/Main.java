import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	private static String studentBidding = "student";
	private static String schoolBidding = "school";

	public static void main(String[] args) {
		TestFileGeneration fileGen = new TestFileGeneration();
		try {
			// fileGen.createFile();
			ParticipantGroup participantGroup = fileGen.createParticipants();
			List<Student> listStudents = participantGroup.getStudents();
			List<School> listSchools = participantGroup.getSchools();
			String userPrompt = promptBidding();
			
			// admission algorithm
			MatchingManager manager = null;
			switch (userPrompt) {
			case "student":
				manager = new MatchingManager<School, Student>(listSchools, listStudents);
				manager.setStrategy(userPrompt);
				manager.findAssociation();
				break;
			case "school":
				manager = new MatchingManager<Student, School>(listStudents, listSchools);
				manager.setStrategy(userPrompt);
				manager.findAssociation();
				break;
			default:
				System.out.println("erreur");
			}
		} catch (IOException e) {
			System.err.println("An error occurred while manipulating the data file.");
		}
		
	}

	/**
	 * Asks the user of who does the bidding
	 * 
	 * @return : who does the bidding
	 */
	private static String promptBidding() {
		Scanner console = new Scanner(System.in);
		String userPrompt = "";
		while (!schoolBidding.equals(userPrompt) && !studentBidding.equals(userPrompt)) {
			System.out.println("Who does the bidding ? (student, school)");
			userPrompt = console.next();
		}
		return userPrompt;
	}

}
