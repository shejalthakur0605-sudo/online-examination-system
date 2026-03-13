import java.util.*;

public class OnlineExamSystem {
	static class User {
		String username, password, name;
		boolean loggedIn = false;
		
		User(String username, String password, String name) {
			this.username = username;
			this.password = password;
			this.name = name;
		}
		
		void updateProfile(String newName) {
			this.name = newName;
		}
		
		void updatePassword(String newPassword) {
			this.password = newPassword;
		}
	}
	
	static class Question {
		String question; 
		List<String> options;
		int correctIndex;
		
		Question(String question, List<String> options, int correctIndex) {
			this.question = question;
			this.options = options;
			this.correctIndex = correctIndex;
		}
	}
	
	static Scanner sc = new Scanner(System.in);
	static Map<String, User> users = new HashMap<>();
	static List<Question> questions = new ArrayList<>();
	static final int EXAM_DURATION_SECONDS = 30;
	
	public static void main(String[] args) {
		
		users.put("john", new User("John@12", "1234", "John"));
		
		questions.add(new Question("Which of these is a programming language ? ",
				Arrays.asList("Google", "HTML", "Java", "Microsoft"), 2));
		questions.add(new Question("What is the square root of 81 ?",
				Arrays.asList("3", "9", "8", "6"), 1));
		
	System.out.println("-------ONLINE EXAMINATION LOGIN-------");
	System.out.println("\nEnter username: ");
	String uname = sc.nextLine();
	System.out.println("Enter password: ");
	String pwd = sc.nextLine();
	
	User user = users.get(uname);
	if(user != null && user.password.equals(pwd)) {
		user.loggedIn = true;
		System.out.println("Logged in successfully! Welcome, " + user.name);
		showMenu(user);
	} else {
		System.out.println("Invalid credentials.");
	}
  }
	static void showMenu(User user) {
		while(user.loggedIn) {
			System.out.println("\n-------Menu-------");
			System.out.println("1.Update Profile");
			System.out.println("2.Change Password");
			System.out.println("3.Start Exam");
			System.out.println("4.Logout");
			System.out.println("Choose an option: ");
			int choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1:
				System.out.println("Enter new name: ");
				user.updateProfile(sc.nextLine());
				System.out.println("Profile updated.");
				break;
			case 2:
				System.out.println("Enter new password: ");
				user.updatePassword(sc.nextLine());
				System.out.println("Password updated.");
				break;
			case 3:
				startExam(user);
				break;
			case 4:
				user.loggedIn = false;
				System.out.println("Logged out successfully.");
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
	}
	
	static void startExam(User user) {
		System.out.println("\n-------Starting Exam-------");
		long startTime = System.currentTimeMillis();
		int score = 0;
		
		for(int i = 0; i < questions.size(); i++) {
			Question q = questions.get(i);
			System.out.println("\nQ" + (i + 1) + ": " + q.question);
			for(int j = 0; j < q.options.size(); j++) {
				System.out.println((j + 1) + ". " + q.options.get(j));
			}
			
			System.out.println("Enter your choice: ");
			int ans = Integer.parseInt(sc.nextLine());
			
			if(ans - 1 == q.correctIndex) {
				score++;
			}
			
			long currentTime = System.currentTimeMillis();
			if((currentTime - startTime) / 1000 >= EXAM_DURATION_SECONDS) {
				System.out.println("\nTime is up! Auto-submitting your exam.");
				break;
			}
		}
		
		System.out.println("\nExam finished");
		System.out.println("\nYour score is: " + score + "/" + questions.size());
	}
}
