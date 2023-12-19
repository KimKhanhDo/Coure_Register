package connection_mysql;

import java.sql.SQLException;
import java.util.Scanner;

import course_service.CourseService;
import course_service.UserService;

public class Main {
	private static final int LOGIN = 1;
	private static final int REGISTER = 2;
	private static final int EXIT = 0;
	private static final int SHOW_REGISTERED_COURSE = 0;
	private static final int REGISTER_NEW_COURSE = 1;
	private static final int BACK_TO_MAIN_MENU = 2;

	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		CourseService courseService = new CourseService();
		UserService userService = new UserService();
		int selectedOption;

		do {
			userService.displayMainMenu();
			selectedOption = scanner.nextInt();
			scanner.nextLine();

			switch (selectedOption) {
			case LOGIN:
				loginUser(scanner, userService, courseService);
				break;

			case REGISTER:
				registerUser(scanner, userService);
				break;

			case EXIT:
				System.out.println("Goodbye! We Hope To See You Again");
				break;

			default:
				System.out.println("Invalid Option.");
			}

		} while (selectedOption != EXIT);

		scanner.close();
	}

	

	public static void loginUser(Scanner scanner, UserService userService, CourseService courseService) {
		System.out.println("Enter ID: ");
		String loginId = scanner.nextLine();
		System.out.println("Enter password: ");
		String loginPassword = scanner.nextLine();

		try {
			boolean loggedInUser = userService.login(loginId, loginPassword);
			if (loggedInUser) {
				handleLoggedInUser(scanner, userService, courseService, loginId);
			} else {
				System.out.println("Invalid Account. Please try again.");
			}
		} catch (SQLException e) {
			System.out.println("An error occurred during login: " + e.getMessage());
		}
	}

	public static void registerUser(Scanner scanner, UserService userService) {
		System.out.println("----- Registration Form -----");

		// Prompt user for registration details
		System.out.println("Enter ID: ");
		String id = scanner.nextLine();

		System.out.println("Enter password: ");
		String password = scanner.nextLine();

		System.out.println("Enter name: ");
		String name = scanner.nextLine();

		try {
			// Register the new user
			userService.registerNewUser(id, password, name);
		} catch (SQLException e) {
			System.out.println("Error during registration: " + e.getMessage());
		}
	}

	public static void handleLoggedInUser(Scanner scanner, UserService userService, CourseService courseService,
			String loginId) throws SQLException {
		
		int selectedCourseByUser;

		do {
			System.out.println("----------------- ");
			System.out.println("0. Show my registered courses");
			System.out.println("1. Register for a course");
			System.out.println("2. Go back to main menu");
			System.out.println("Enter your option: ");
			int userOption = scanner.nextInt();
			scanner.nextLine();

			switch (userOption) {
			case SHOW_REGISTERED_COURSE:
				UserService.showRegisteredCourseToUser(loginId);
				break;

			case REGISTER_NEW_COURSE:
				courseService.showAllCourse();
				System.out.println("------");
				System.out.println("Enter the course ID to register: ");
				selectedCourseByUser = scanner.nextInt();
				scanner.nextLine(); 
				UserService.registerNewCourse(selectedCourseByUser, loginId);
				break;

			case BACK_TO_MAIN_MENU:
				System.out.println("Going back to the main menu.");
				return;

			default:
				System.out.println("Invalid option. Please choose a valid option.");
			}
		} while (true);
	}

}
