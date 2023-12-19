package course_service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection_mysql.DBConnection;
import course_data.Course;

public class UserService {

	public void displayMainMenu() {
		System.out.println("----- Welcome To Coding Mentor -----");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("0. Exit");
		System.out.println("Enter your option: ");
	}

	public boolean checkExistUser(String userId) throws SQLException {

		Connection connection = DBConnection.makeConnection();
		String checkQuery = "SELECT * FROM student WHERE user_id = ?";

		PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
		checkStatement.setString(1, userId);
		return checkStatement.executeQuery().next();
	}

	public void registerNewUser(String userId, String password, String name) throws SQLException {
		// Check if the user already exists
		if (checkExistUser(userId)) {
			System.out.println("This Account Already Exists. Please register a new one");
			return;
		}

		// Insert the new user into the student table
		Connection connection = DBConnection.makeConnection();
		String insertQuery = "INSERT INTO student (user_id, password, name) VALUES (?, ?, ?)";

		PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
		insertStatement.setString(1, userId);
		insertStatement.setString(2, password);
		insertStatement.setString(3, name);

		insertStatement.executeUpdate();

		System.out.println("Register successfully!  Welcome, " + name + "!");

	}

	public boolean login(String userId, String password) throws SQLException {

		// Check if the user exists and the password is correct
		Connection connection = DBConnection.makeConnection();
		String query = "SELECT * FROM student WHERE user_id = ? AND password = ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, userId);
		statement.setString(2, password);

		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			System.out.println("Login Successfully");
			connection.close();
			return true;
		} else {
			System.out.println("Invalid Account or Password");
		}

		connection.close();
		return false;
	}

	public static void registerNewCourse(int courseID, String userId) {
		try {
			// Check if the course is already registered for the user
			Connection connection = DBConnection.makeConnection();
			String checkQuery = "SELECT COUNT(*) FROM enrolment WHERE user_id = ? AND course_id = ?";

			PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
			checkStatement.setString(1, userId);
			checkStatement.setInt(2, courseID);

			ResultSet record = checkStatement.executeQuery();
			int count = 1;
			if (record.next()) {
				count = record.getInt(1);
			}

			if (count > 0) {
				System.out.println("Course is already registered for the user");
				return;
			}

			// Register the course for the user
			String insertQuery = "INSERT INTO enrolment (user_id, course_id) VALUES (?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

			insertStatement.setString(1, userId);
			insertStatement.setInt(2, courseID);
			insertStatement.executeUpdate();
			System.out.println("Register Successfully");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void showRegisteredCourseToUser(String userId) {
	    try {
	        Connection connection = DBConnection.makeConnection();
	        String query = "SELECT c.* FROM course c " +
                    "JOIN enrolment e ON c.id = e.course_id " +
                    "WHERE e.user_id = ?";

	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, userId);

	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            System.out.println("Registered Courses:");
	            do {
	                Course course = new Course();
	                course.setId(resultSet.getInt("id"));
	                course.setName(resultSet.getString("name"));

	                System.out.println(course.getName());
	            } while (resultSet.next());
	        } else {
	            System.out.println("No course is registered");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
