package ui;

import java.sql.Date;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

public class ShowCourses {
	private Stage st;
	private TableView tvCourses;
	private TableColumn<Course, String> idCol;
	private TableColumn<Course, String> nameCol;
	private TableColumn<Course, Date> sdateCol;
	private TableColumn<Course, Date> edateCol;

	public ShowCourses(ArrayList<Course> courses)
	{
		st = new Stage();
		createCourseTable(courses);
		Scene sc = new Scene(tvCourses,700,500);
		st.setScene(sc);
		st.setTitle("Students");
		st.show();
	}
	
	public void createCourseTable(ArrayList<Course> courses)
	{
tvCourses = new TableView<Course>();
		
		idCol = new TableColumn<Course, String>("Course ID");
		idCol.setCellValueFactory(new PropertyValueFactory<Course,String>("courseID"));
	
		nameCol = new TableColumn<Course, String>("Course Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));
		nameCol.setSortable(true);
		
		sdateCol = new TableColumn<Course, Date>("Start-date");
		sdateCol.setCellValueFactory(new PropertyValueFactory<Course, Date>("startDate"));
		sdateCol.setSortable(true);
		
		edateCol = new TableColumn<Course, Date>("End-date");
		edateCol.setCellValueFactory(new PropertyValueFactory<Course, Date>("endDate"));
		edateCol.setSortable(true);
		
		tvCourses.getColumns().add(idCol);
		tvCourses.getColumns().add(nameCol);
		tvCourses.getColumns().add(sdateCol);
		tvCourses.getColumns().add(edateCol);
		
		tvCourses.getItems().addAll(courses);
	}
}
