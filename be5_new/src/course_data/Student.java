package course_data;

import java.util.ArrayList;

public class Student {

	private String id;
	private String password;
	private String name;
	private ArrayList<Course> registeredCourses;
	private int failedCount;

	public Student() {

	}

	public Student(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.failedCount = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Course> getRegisteredCourses() {
		return registeredCourses;
	}

	public void setRegisteredCourses(ArrayList<Course> registeredCourses) {
		this.registeredCourses = registeredCourses;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

}
