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

public class TestFileGeneration {
	
	private String filename = "data.txt";

	/**
	 * Generate a file with students and schools.
	 * @param nbStudents the number of students.
	 * @param nbSchools the number of schools.
	 * @throws IOException 
	 */
	public void createFile(int nbStudents, int nbSchools) throws IOException {
		List<String> fileContent = new ArrayList<String>();
		
		// Students part
		fileContent.add("students");
		fileContent.addAll(generateParticipants(nbStudents, nbSchools, false));
		
		// Schools part
		fileContent.add("schools");
		fileContent.addAll(generateParticipants(nbSchools, nbStudents, true));
		
		System.out.println(fileContent);
		
		Path file = Paths.get(filename);
		Files.write(file, fileContent, StandardCharsets.UTF_8);
	}
	
	/**
	 * Generate nb1 participants with nb2 preferences each.
	 * @param nb1 the number of participants who has preferences
	 * @param nb2 the number of preferences
	 * @return a list of all the preferences.
	 */
	private List<String> generateParticipants(int nb1, int nb2, boolean isSchool) {
		
		List<String> part = new ArrayList<String>();
		
		for (int i=0; i<nb1; i++) {
			
			String line = "";
			HashSet<Integer> set = new HashSet<Integer>();
			
			while (set.size() != nb2) {
				int pref = (int)(Math.random() * nb2) + 1;
				if (!set.contains(pref)) {
					set.add(pref);
					line += pref + " ";
				}
			}
			
			if (isSchool) {
				line += "[cap" + (int) ((Math.random() * (nb2)) + 1) + "]";
			}
			
			part.add(line);
		}
		
		return part;
	}
	
	/**
	 * Read the input file and create the students and the schools.
	 * @return an object containing the students and the schools.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ParticipantGroup createParticipants() throws FileNotFoundException, IOException {
		ParticipantGroup participants = new ParticipantGroup();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
					
		    	} else {
		    		if (isStudentPart) {
		    			// Create the student
		    			students.put(counterStudents, new Student(counterStudents));
		    			
		    			// Create the preferences of the student
		    			String[] prefs = line.split(" ");
		    			for (int i=0; i<prefs.length; i++) {
		    				if (!prefsStudents.containsKey(counterStudents)) {
		    					prefsStudents.put(counterStudents, new ArrayList<Integer>());
		    				}
		    				prefsStudents.get(counterStudents).add(Integer.parseInt(prefs[i]));
		    			}
		    			
		    			counterStudents++;		    			
		    		} else if (isSchoolPart) {
		    			
		    			// Create the preferences of the school
		    			String[] prefs = line.split(" ");
		    			int i=0;
		    			for (i=0; i<prefs.length - 1; i++) {
		    				if (!prefsSchools.containsKey(counterSchools)) {
		    					prefsSchools.put(counterSchools, new ArrayList<Integer>());
		    				}
		    				prefsSchools.get(counterSchools).add(Integer.parseInt(prefs[i]));
		    			}		    			
		    			int capacity = Integer.parseInt(prefs[i].substring(4, 5));
		    			
		    			// Create the school
		    			schools.put(counterSchools, new School(counterSchools, capacity));
		    			
		    			counterSchools++;		    			
		    		}
		    	}		    	
		    	line = br.readLine();
		    }
			
		    // Add the students in participants
			for (int i=0; i<students.size(); i++) {
				HashMap<School, Integer> finalPrefs =  new HashMap<School, Integer>();
				List<Integer> prefs = prefsStudents.get(i);
				int j=0;
				for (int pref : prefs) {
					finalPrefs.put(schools.get(j), pref);
					j++;
				}				
				Student student = students.get(i);
				student.setPreferences(finalPrefs);
				participants.addStudent(student);
			}
			
			// Add the schools in participants
			for (int i=0; i<schools.size(); i++) {
				HashMap<Student, Integer> finalPrefs =  new HashMap<Student, Integer>();
				List<Integer> prefs = prefsSchools.get(i);
				int j=0;
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