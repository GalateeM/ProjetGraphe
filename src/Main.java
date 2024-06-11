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
		String userPrompt = promptBidding();
		System.out.println(userPrompt);

		// TODO : read file
		TestFileGeneration fileGen = new TestFileGeneration();
		try {
			fileGen.createFile(3, 3);
			ParticipantGroup participantGroup = fileGen.createParticipants();
			List<Student> listStudents = participantGroup.getStudents();
			List<School> listSchools = participantGroup.getSchools();
			// admission algorithm
			MatchingManager manager = null;
			switch (userPrompt) {
			case "student":
				manager = new MatchingManager<School, Student>(listSchools, listStudents);
				manager.findAssociation();
				break;
			case "school":
				manager = new MatchingManager<Student, School>(listStudents, listSchools);
				manager.findAssociation();
				break;
			default:
				System.out.println("erreur");
			}

		} catch (IOException e) {
			System.err.println("An error occurred while manipulating the data file.");
		}
		

		// TODO : delete
//		Student student1 = new Student(0);
//		Student student2 = new Student(1);
//		Student student3 = new Student(2);
//		List<Student> listStudents = new ArrayList<Student>();
//		listStudents.add(student1);
//		listStudents.add(student2);
//		listStudents.add(student3);
//
//		School school1 = new School(0, 3);
//		School school2 = new School(1, 3);
//		School school3 = new School(2, 3);
//		List<School> listSchools = new ArrayList<School>();
//		listSchools.add(school1);
//		listSchools.add(school2);
//		listSchools.add(school3);
//
//		// Preferences
//		HashMap<School, Integer> prefStud1 = new HashMap<School, Integer>();
//		prefStud1.put(school1, 1);
//		prefStud1.put(school2, 2);
//		prefStud1.put(school3, 3);
//		student1.setPreferences(prefStud1);
//		HashMap<School, Integer> prefStud2 = new HashMap<School, Integer>();
//		prefStud2.put(school1, 2);
//		prefStud2.put(school2, 1);
//		prefStud2.put(school3, 3);
//		student2.setPreferences(prefStud2);
//		HashMap<School, Integer> prefStud3 = new HashMap<School, Integer>();
//		prefStud3.put(school1, 1);
//		prefStud3.put(school2, 2);
//		prefStud3.put(school3, 3);
//		student3.setPreferences(prefStud3);
//
//		HashMap<Student, Integer> prefSch1 = new HashMap<Student, Integer>();
//		prefSch1.put(student1, 2);
//		prefSch1.put(student2, 1);
//		prefSch1.put(student3, 3);
//		school1.setPreferences(prefSch1);
//		HashMap<Student, Integer> prefSch2 = new HashMap<Student, Integer>();
//		prefSch2.put(student1, 1);
//		prefSch2.put(student2, 2);
//		prefSch2.put(student3, 3);
//		school2.setPreferences(prefSch2);
//		HashMap<Student, Integer> prefSch3 = new HashMap<Student, Integer>();
//		prefSch3.put(student1, 3);
//		prefSch3.put(student2, 1);
//		prefSch3.put(student3, 2);
//		school3.setPreferences(prefSch3);

		
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
		console.close();
		return userPrompt;
	}

}
