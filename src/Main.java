import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	
	private static String studentBidding = "student";
	private static String schoolBidding = "school";
	
	private static int counter;

	public static void main(String[] args) {
		String userPrompt = promptBidding();
		System.out.println(userPrompt);
		
		// TODO : read file
		
		//TODO : delete
		Student student1 = new Student(0);
		Student student2 = new Student(1);
		Student student3 = new Student(2);
		List<Student> listStudents = new ArrayList<Student>();
		listStudents.add(student1);
		listStudents.add(student2);
		listStudents.add(student3);
		
		School school1 = new School(0, 3);
		School school2 = new School(1, 3);
		School school3 = new School(2, 3);
		List<School> listSchools = new ArrayList<School>();
		listSchools.add(school1);
		listSchools.add(school2);
		listSchools.add(school3);
		
		//Preferences
		HashMap<School, Integer> prefStud1 = new HashMap<School, Integer>();
		prefStud1.put(school1, 1);
		prefStud1.put(school2, 2);
		prefStud1.put(school3, 3);
		student1.setPreferences(prefStud1);
		HashMap<School, Integer> prefStud2 = new HashMap<School, Integer>();
		prefStud2.put(school1, 2);
		prefStud2.put(school2, 1);
		prefStud2.put(school3, 3);
		student2.setPreferences(prefStud2);
		HashMap<School, Integer> prefStud3 = new HashMap<School, Integer>();
		prefStud3.put(school1, 1);
		prefStud3.put(school2, 2);
		prefStud3.put(school3, 3);
		student3.setPreferences(prefStud3);
		
		HashMap<Student, Integer> prefSch1 = new HashMap<Student, Integer>();
		prefSch1.put(student1, 2);
		prefSch1.put(student2, 1);
		prefSch1.put(student3, 3);
		school1.setPreferences(prefSch1);
		HashMap<Student, Integer> prefSch2 = new HashMap<Student, Integer>();
		prefSch2.put(student1, 1);
		prefSch2.put(student2, 2);
		prefSch2.put(student3, 3);
		school2.setPreferences(prefSch2);
		HashMap<Student, Integer> prefSch3 = new HashMap<Student, Integer>();
		prefSch3.put(student1, 3);
		prefSch3.put(student2, 1);
		prefSch3.put(student3, 2);
		school3.setPreferences(prefSch3);
		
		//admission algorithm
		findAssociation(listSchools, listStudents);
		
		System.out.println(counter);
		
	}
	
	/**
	 * Asks the user of who does the bidding
	 * @return : who does the bidding
	 */
	private static String promptBidding() {
		Scanner console = new Scanner(System.in);
		String userPrompt = "";
		while(!schoolBidding.equals(userPrompt) && !studentBidding.equals(userPrompt)) {
			System.out.println("Who does the bidding ? (student, school)");
			userPrompt = console.next();
		}
		console.close();
		return userPrompt;
	}
	
	/**
	 * Associates the students to school
	 * @param schoolList : list of schools
	 * @param studentList : list of students
	 */
	private static void findAssociation(List<School> schoolList, List<Student> studentList) {
		counter = 0; // counter of rounds
		
		HashMap<Student, HashSet<School>> currentAssociation = new HashMap<Student, HashSet<School>>();
		while(!isComplete(currentAssociation, studentList)) {
			//Match the schools and students according to school preferences
			currentAssociation = doCeremony(schoolList);
			//The students reject every school except its prefered one
			doReject(currentAssociation);
			counter ++;
		}
		System.out.println(currentAssociation);
	}
	
	/**
	 * Match the schools and students according to school preferences
	 * @param schoolList: list of schools
	 * @return : the association of student to list of schools
	 */
	private static HashMap<Student, HashSet<School>> doCeremony(List<School> schoolList) {
		HashMap<Student, HashSet<School>> associationAtNight = new HashMap<Student, HashSet<School>>();
		
		for(School schoolIter : schoolList) {
			 Student preferedStudent = schoolIter.getBestPreference();
			 if(associationAtNight.get(preferedStudent) == null) {
				 associationAtNight.put(preferedStudent, new HashSet<School>());
			 }
			 associationAtNight.get(preferedStudent).add(schoolIter);
		 }
		return associationAtNight;
	}
	
	/**
	 * The students reject every school except its prefered one
	 * @param associationAtNight : the association of student to list of schools
	 */
	private static void doReject(HashMap<Student, HashSet<School>> associationAtNight) {
		//Loop on the students
		for (Map.Entry<Student, HashSet<School>> association : associationAtNight.entrySet()) {
			HashSet<School> schools = association.getValue();
			Student student = association.getKey();
			
			if(schools.size() > 1) {
				//choose prefered one
				School preferedSchool = student.getPreferenceAmongList(schools);
				
				//update the rejections
				for(School school : schools) {
					if(school != preferedSchool) {
						school.incrementNbRejects();
					}
				}
				
				//replace the list with only the prefered one
				HashSet<School> oneElementSet = new HashSet<School>();
				oneElementSet.add(preferedSchool);
				associationAtNight.put(student, oneElementSet);
			}
			
        }
	}

	/**
	 * Check if the matching is complete 
	 * @param currentAssociation : the association of student to list of schools
	 * @param studentList : the number of student
	 * @return : is the matching is complete
	 */
	private static boolean isComplete(HashMap<Student, HashSet<School>> currentAssociation, List<Student> studentList) {
		return currentAssociation.size() == studentList.size();
	}
	
}
