package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBHandler {
	private static Connection con;
	private static String userName = "root";
	private static String password = "root";
	private static int port = 3307;
	private static String dbName = "school_db";
	private static String host = "localhost";
	
	public static boolean openConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://"+host+":"+port+"/"+dbName;
			con=DriverManager.getConnection(url,userName,password);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public static boolean closeConnection()
	{
		try {
			con.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static ArrayList<Course> getAllCourse()
	{
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			openConnection();
			String sql = "select * from course";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Course c = new Course(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getDate(4),rs.getString(5));
				courses.add(c);
			}
;			closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	public static boolean insertCourse(String id, String name, Date sdate, Date edate, String tid )
	{
		try {
			openConnection();
			String sql = "insert into course values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setDate(3, sdate);
			ps.setDate(4, edate);
			ps.setString(5, tid);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateCourse(String id, String name, Date sdate, Date edate, String tid)
	{
		try {
			openConnection();
			String sql = "update course set courseName=?,startDate=?,endDate=?,teacher_id=? where courseID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setDate(2, sdate);
			ps.setDate(3, edate);
			ps.setString(4, tid);
			ps.setString(5, id);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteCourse(String id)
	{
		try {
			openConnection();
			String sql="delete from course where courseID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Teacher getCourseTeacher(String tid)
	{
		Teacher t = new Teacher();
		try {
			openConnection();
			String sql = "select * from teacher where teacherID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, tid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				 t = new Teacher(rs.getString(1),rs.getString(2),rs.getString(3));
			}
			closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public static ArrayList<Student> getAllStudent(String cid)
	{
		ArrayList<Student> students = new ArrayList<Student>();
		try {
			openConnection();
			String sql = "select * from student where studentID in (select student_id from enroll where course_id = ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, cid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Student s = new Student(rs.getString(1),rs.getString(2),rs.getString(3));
				students.add(s);
			}
			closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public static ArrayList<Teacher> getAllTeacher()
	{
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		try {
			openConnection();
			String sql = "select * from teacher";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Teacher t = new Teacher(rs.getString(1),rs.getString(2),rs.getString(3));
				teachers.add(t);
			}
;			closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return teachers;
	}
	
	public static boolean insertTeacher(String id, String name, String phone )
	{
		try {
			openConnection();
			String sql = "insert into teacher values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, phone);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateTeacher(String id, String name, String phone)
	{
		try {
			openConnection();
			String sql = "update teacher set teacherName=?,teacherPhone=? where teacherID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, phone);
			ps.setString(3, id);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteTeacher(String id)
	{
		try {
			openConnection();
			String sql="delete from teacher where teacherID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Course> getTeacherCourse(String tid)
	{
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			openConnection();
			String sql = "select * from course where teacher_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, tid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Course c = new Course(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getDate(4),rs.getString(5));
				courses.add(c);
			}
;			closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	public static ArrayList<Student> getAllStudent()
	{
		ArrayList<Student> students = new ArrayList<Student>();
		try {
			openConnection();
			String sql = "select * from student";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Student s = new Student(rs.getString(1),rs.getString(2),rs.getString(3));
				students.add(s);
			}
;			closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public static boolean insertStudent(String id, String name, String phone )
	{
		try {
			openConnection();
			String sql = "insert into student values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, phone);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateStudent(String id, String name, String phone)
	{
		try {
			openConnection();
			String sql = "update student set studentName=?,studentPhone=? where studentID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, phone);
			ps.setString(3, id);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteStudent(String id)
	{
		try {
			openConnection();
			String sql="delete from student where studentID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Course> getStudentCourse(String sid)
	{
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			openConnection();
			String sql = "select * from course where courseID in (select course_id from enroll where student_id=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, sid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Course c = new Course(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getDate(4),rs.getString(5));
				courses.add(c);
			}
;			closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	public static boolean enroll(String sid, String cid)
	{
		try {
			openConnection();
			String sql = "insert into enroll(student_id,course_id) values(?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, sid);
			ps.setString(2, cid);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteEnroll(String sid, String cid)
	{
		try {
			openConnection();
			String sql="delete from enroll where student_id=? and course_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, sid);
			ps.setString(2, cid);
			int line = ps.executeUpdate();
			closeConnection();
			return line>0;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
