import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestFileGeneration {

	private String filename = "data.txt";

	//inputs for the choice of the capacity generation
	private final static String capacityInferiorRandom = "IR";
	private final static String capacityInferiorSame = "IE";
	private final static String capacityEquals = "E";
	private final static String capacitySuperior = "S";

	private String capacityChoice;
	private int numberOfStudentsChoice;
	private int numberOfSchoolsChoice;
	
	private List<Integer> capacities;

	/**
	 * Creates the generator of test file by prompting the user the capacity
	 * caracteristic, the number of students and the number of schools
	 */
	public TestFileGeneration() {
		Scanner console = new Scanner(System.in);
		// capacity
		this.capacityChoice = "";
		while (!isValidCapacity()) {
			System.out.println("Total capacity (compared to number of students) ?"
					+ "\n- inferior (random numbers): IR"
					+ "\n- inferior (all numbers equals): IE"
					+ "\n- equals: E"
					+ "\n- superior: S");
			capacityChoice = console.next();
		}

		// number of students
		this.numberOfStudentsChoice = -1;
		while (this.numberOfStudentsChoice < 0) {
			System.out.println("Number of students ?");
			this.numberOfStudentsChoice = console.nextInt();
		}

		// number of schools
		this.numberOfSchoolsChoice = -1;
		while (this.numberOfSchoolsChoice < 0) {
			System.out.println("Number of schools ?");
			this.numberOfSchoolsChoice = console.nextInt();
		}
		//instantiate the capacity per school
		createCapacities();

	}
	
	/**
	 * Check the user prompt for the capacity
	 */
	private boolean isValidCapacity() {
		return capacityInferiorRandom.equals(this.capacityChoice) || capacityInferiorSame.equals(this.capacityChoice)
				|| capacityEquals.equals(this.capacityChoice) || capacitySuperior.equals(this.capacityChoice);
	}


	/**
	 * Generate a file with students and schools.
	 * 
	 * @param nbStudents the number of students.
	 * @param nbSchools  the number of schools.
	 * @throws IOException
	 */
	public void createFile() throws IOException {
		List<String> fileContent = new ArrayList<String>();

		// Students part
		fileContent.add("students");
		fileContent.addAll(generateParticipants(this.numberOfStudentsChoice, this.numberOfSchoolsChoice, false));

		// Schools part
		fileContent.add("schools");
		fileContent.addAll(generateParticipants(this.numberOfSchoolsChoice, this.numberOfStudentsChoice, true));

		//System.out.println(fileContent);

		Path file = Paths.get(filename);
		Files.write(file, fileContent, StandardCharsets.UTF_8);
	}


	/**
	 * Generate nb1 participants with nb2 preferences each.
	 * 
	 * @param nb1 the number of participants who has preferences
	 * @param nb2 the number of preferences
	 * @return a list of all the preferences.
	 */
	private List<String> generateParticipants(int nb1, int nb2, boolean isSchool) {
		
		int totalCapacity = 0;
		List<String> part = new ArrayList<String>();

		for (int i = 0; i < nb1; i++) {

			String line = "";
			HashSet<Integer> set = new HashSet<Integer>();

			while (set.size() != nb2) {
				int pref = (int) (Math.random() * nb2) + 1;
				if (!set.contains(pref)) {
					set.add(pref);
					line += pref + " ";
				}
			}

			if (isSchool) {
				int capacity = this.capacities.get(i);
				totalCapacity += capacity;
				line += "[cap" + capacity + "]";
			}
			
			part.add(line);
		}
		if(isSchool) {
			part.add("Total capacity: " + totalCapacity);
		}
		return part;
	}

	/**
	 * Creates one capacity per school according to the caracteristic chosen by the user
	 */
	private void createCapacities() {
		int totalCapacity = 0;
		Random r = new Random();
		List<Integer> capacities = new ArrayList<Integer>();
		for (int i = 0; i < this.numberOfSchoolsChoice; i++) {
			switch (this.capacityChoice) {
				case capacityInferiorRandom:
					//random number between 0 and (the residual capacity / 2)
					int capacityIR = r.nextInt(0, (this.numberOfStudentsChoice - totalCapacity) / 2);
					capacities.add(capacityIR);
					totalCapacity += capacityIR;
					break;
				case capacityInferiorSame:
					capacities.add((int) Math.floor(this.numberOfStudentsChoice - 1) / this.numberOfSchoolsChoice);
					break;
				case capacityEquals:
					if (i == this.numberOfSchoolsChoice - 1) {
						//for the last school we want to have the residual capacity to have totalCapacity = numberOfStudents
						capacities.add(this.numberOfStudentsChoice - totalCapacity);
					} else {
						int capacityE = this.numberOfStudentsChoice / this.numberOfSchoolsChoice;
						capacities.add(capacityE);
						totalCapacity += capacityE;
					}
					break;
				case capacitySuperior:
					//arbitrary number to be certain that the totalCapacity is bigger than the number of students
					capacities.add((int) ((this.numberOfStudentsChoice * 2) / this.numberOfSchoolsChoice));
					break;
			}

		}

		this.capacities = capacities;
	}

	/**
	 * Read the input file and create the students and the schools.
	 * 
	 * @return an object containing the students and the schools.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ParticipantGroup createParticipants() throws FileNotFoundException, IOException {
		ParticipantGroup participants = new ParticipantGroup();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = br.readLine();

			boolean isStudentPart = false;
			boolean isSchoolPart = false;

			HashMap<Integer, List<Integer>> prefsStudents = new HashMap<Integer, List<Integer>>();
			HashMap<Integer, List<Integer>> prefsSchools = new HashMap<Integer, List<Integer>>();

			HashMap<Integer, Student> students = new HashMap<Integer, Student>();
			HashMap<Integer, School> schools = new HashMap<Integer, School>();

			int counterStudents = 0;
			int counterSchools = 0;

			while (line != null) {
				if (line.contains("students")) {
					isStudentPart = true;
					isSchoolPart = false;

				} else if (line.contains("schools")) {
					isStudentPart = false;
					isSchoolPart = true;
				} else if (!line.contains("Total capacity")) {
					if (isStudentPart) {
						// Create the student
						students.put(counterStudents, new Student(counterStudents));

						// Create the preferences of the student
						String[] prefs = line.split(" ");
						for (int i = 0; i < prefs.length; i++) {
							if (!prefsStudents.containsKey(counterStudents)) {
								prefsStudents.put(counterStudents, new ArrayList<Integer>());
							}
							prefsStudents.get(counterStudents).add(Integer.parseInt(prefs[i]));
						}

						counterStudents++;
					} else if (isSchoolPart) {

						// Create the preferences of the school
						String[] prefs = line.split(" ");
						int i = 0;
						for (i = 0; i < prefs.length - 1; i++) {
							if (!prefsSchools.containsKey(counterSchools)) {
								prefsSchools.put(counterSchools, new ArrayList<Integer>());
							}
							prefsSchools.get(counterSchools).add(Integer.parseInt(prefs[i]));
						}
						int capacity = Integer.parseInt(prefs[i].substring(4, prefs[i].length() - 1));

						// Create the school
						schools.put(counterSchools, new School(counterSchools, capacity));

						counterSchools++;
					}
				}
				line = br.readLine();
			}

			// Add the students in participants
			for (int i = 0; i < students.size(); i++) {
				HashMap<School, Integer> finalPrefs = new HashMap<School, Integer>();
				List<Integer> prefs = prefsStudents.get(i);
				int j = 0;
				for (int pref : prefs) {
					finalPrefs.put(schools.get(j), pref);
					j++;
				}
				Student student = students.get(i);
				student.setPreferences(finalPrefs);
				participants.addStudent(student);
			}

			// Add the schools in participants
			for (int i = 0; i < schools.size(); i++) {
				HashMap<Student, Integer> finalPrefs = new HashMap<Student, Integer>();
				List<Integer> prefs = prefsSchools.get(i);
				int j = 0;
				for (int pref : prefs) {
					finalPrefs.put(students.get(j), pref);
					j++;
				}
				School school = schools.get(i);
				school.setPreferences(finalPrefs);
				participants.addSchool(school);
			}
		}

		return participants;
	}
}