package model;

public class Teacher {
	private String teacherID,teacherName,teacherPhone;

	
	public Teacher() {
		super();
	}

	public Teacher(String teacherID, String teacherName, String teacherPhone) {
		super();
		this.teacherID = teacherID;
		this.teacherName = teacherName;
		this.teacherPhone = teacherPhone;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}
	
}
