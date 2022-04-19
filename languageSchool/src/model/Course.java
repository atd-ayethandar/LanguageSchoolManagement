package model;

import java.sql.Date;

public class Course {
	private String courseID, courseName;
	private Date startDate, endDate;
	private String teacher_id;
	public Course(String courseID, String courseName, Date startDate, Date endDate, String teacher_id) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.teacher_id = teacher_id;
	}
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	
}
