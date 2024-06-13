import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	private static String studentBidding = "student";
	private static String schoolBidding = "school";
	private static String yesAnswer = "y";
	private static String noAnswer = "n";

	public static void main(String[] args) {
		TestFileGeneration fileGen;
		//we can generate an input file or use an existing one
		if(promptGenerationFile()) {
			fileGen = new TestFileGeneration("data.txt");
			fileGen.promptGeneration();
			try {
				fileGen.createFile(); 
			} catch (IOException e) {
				System.err.println("An error occurred while manipulating the data file.");
			}
		} else {
			String fileName = promptFileName();
			fileGen = new TestFileGeneration(fileName);
		}
		try {
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
		
		}  catch (IOException e) {
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
	
	/**
	 * Ask the user wether we use an existing file or generate a new one
	 * @return : true if we want to generate a file
	 */
	private static boolean promptGenerationFile() {
		Scanner console = new Scanner(System.in);
		String userPrompt = "";
		while (!yesAnswer.equals(userPrompt) && !noAnswer.equals(userPrompt)) {
			System.out.println("Generate a new file ?");
			userPrompt = console.next();
		}
		return yesAnswer.equals(userPrompt) ? true : false;
	}
	
	/**
	 * Prompt the user for the name of the file he wants to use
	 * @return : the name of the file
	 */
	private static String promptFileName() {
		Scanner console = new Scanner(System.in);
		System.out.println("Name of the file ?");
		return  console.next();
	}
	
	public static void saveResultToFile(HashMap<School, HashSet<Student>> finalAssociation) {
		List<String> fileContent = new ArrayList<String>();

		fileContent.add("Affectations");
		for(Map.Entry<School, HashSet<Student>> finalAssociationIter : finalAssociation.entrySet()) {
			String temp = "School "+ (finalAssociationIter.getKey().getId() + 1) + " : ";
			for(Student studentIter : finalAssociationIter.getValue()) {
				temp += studentIter.getId()+1 + ", ";
			}
			fileContent.add(temp.substring(0, temp.length()-2));
		}
		
		Path file = Paths.get("matchinResult.txt");
		try {
			Files.write(file, fileContent, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
