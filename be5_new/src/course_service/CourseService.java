package course_service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection_mysql.DBConnection;
import course_data.Course;
import course_data.Mentor;

public class CourseService {

	public List<Course> showAllCourse() throws SQLException {
		
		List<Course> courseList = new ArrayList<Course>();

		Connection connection = DBConnection.makeConnection();
		String SQL = "SELECT * FROM course";

		Statement stmt = connection.createStatement(); 
		ResultSet resultSet = stmt.executeQuery(SQL);


		while (resultSet.next()) { 
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			Date beginDate = resultSet.getDate("begin_date");
			Date endDate = resultSet.getDate("end_date");
			int fee = resultSet.getInt("fee");
			Course course = new Course(id, name, beginDate, endDate, fee);
			courseList.add(course);

			System.out.println("------------");
			System.out.println("Course ID: " + id + ", Name: " + name + ", Begin Date: " + beginDate + ", End Date: "
					+ endDate + ", Fee: " + fee);
		}

		return courseList;
	}

	public static List<Mentor> showMentorByCourse(Course course) {
		List<Mentor> mentors = new ArrayList<>();

		try {
			Connection connection = DBConnection.makeConnection();
			String SQL = "SELECT teacher.id, teacher.name, teacher.email, teacher.phone "
					+ "FROM teacher JOIN teaching_info ON teacher.id = teaching_info.teacher_id "
					+ "WHERE teaching_info.course_id = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setInt(1, course.getId());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int mentorId = resultSet.getInt("id");
				String mentorName = resultSet.getString("name");
				String mentorEmail = resultSet.getString("email");
				String mentorPhone = resultSet.getString("phone");

				Mentor mentor = new Mentor(mentorId, mentorName, mentorEmail, mentorPhone);
				mentors.add(mentor);
			}

			connection.close();
		} catch (SQLException e) {
			System.out.println("An error occurred while fetching mentors: " + e.getMessage());
		}

		return mentors;
	}

}
