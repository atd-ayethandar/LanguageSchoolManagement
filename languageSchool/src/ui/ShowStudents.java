package ui;

import java.net.URL;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

public class ShowStudents {

	ArrayList<Student> students;
	private Stage st;
	private TableView tvStudents;
	private TableColumn<Student, String> idCol;
	private TableColumn<Student, String> nameCol;
	private TableColumn<Student, String> phoneCol;
	
	public ShowStudents(String cid)
	{
		st = new Stage();
		createStudentTable(cid);
		Scene sc = new Scene(tvStudents,700,500);
		st.setScene(sc);
		st.setTitle("Students");
		st.show();
	}
	
	public void createStudentTable(String cid)
	{
		tvStudents = new TableView<Student>();
		
		idCol = new TableColumn<Student, String>("Student ID");
		idCol.setCellValueFactory(new PropertyValueFactory<Student,String>("studentID"));
	
		nameCol = new TableColumn<Student, String>("Student Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("studentName"));
		nameCol.setSortable(true);
		
		phoneCol = new TableColumn<Student, String>("Student Phone");
		phoneCol.setCellValueFactory(new PropertyValueFactory<Student, String>("studentPhone"));
		phoneCol.setSortable(true);
		
		tvStudents.getColumns().add(idCol);
		tvStudents.getColumns().add(nameCol);
		tvStudents.getColumns().add(phoneCol);
		
		setData(cid);
		
	}
	
	public void setData(String cid)
	{
		students = DBHandler.getAllStudent(cid);
		tvStudents.getItems().addAll(students);
	}
}
