package ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import model.*;

public class CoursePane {
	
	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	private Label lcreate, lback, lteacher, teacher, lnoOfStudents, student;
	private Button btnDelete, btnShow;
	
	private TableView tvCourses;
	private TableColumn<Course, String> idCol;
	private TableColumn<Course, String> nameCol;
	private TableColumn<Course, Date> sdateCol;
	private TableColumn<Course, Date> edateCol;
	private TableViewSelectionModel<Course> selectionModel;
	private FlowPane createTopPane, backTopPane;
	private GridPane infoPane;
	private BorderPane mainPane;
	
	public void createNode()
	{
		lcreate = new Label("Create Course");
		lcreate.getStyleClass().add("option");
		lcreate.setOnMouseClicked(e->{
			mainPane.setTop(null);
			mainPane.setTop(backTopPane);
			mainPane.setCenter(null);
			mainPane.setRight(null);
			mainPane.setCenter(new CreateCourse().getCreateCourse());
		});
		lback = new Label("Back");
		lback.getStyleClass().add("option");
		lback.setOnMouseClicked(e->{
			mainPane.setTop(null);
			mainPane.setTop(createTopPane);
			mainPane.setCenter(null);
			tvCourses.getItems().clear();
			setData();
			mainPane.setCenter(tvCourses);
			mainPane.setRight(infoPane);
		});
		
		lteacher = new Label("Teacher:");
		teacher = new Label();
		lnoOfStudents = new Label("No of Students:");
		student = new Label();
		btnDelete = new Button("Delete");
		btnDelete.setOnAction(e->{
			delete();
		});
		btnShow = new Button("Show Students");
		btnShow.setOnAction(e->{
			Course c = selectionModel.getSelectedItem();
			new ShowStudents(c.getCourseID());
		});
	}
	
	public void createTopPane() {
		createTopPane = new FlowPane(lcreate);
		createTopPane.setAlignment(Pos.CENTER_RIGHT);
		createTopPane.setPadding(new Insets(20));
	}
	
	public void backTopPane() {
		backTopPane = new FlowPane(lback);
		backTopPane.setAlignment(Pos.CENTER_RIGHT);
		backTopPane.setPadding(new Insets(20));
	}
	
	public void createCourseTable()
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
		
		selectionModel = tvCourses.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
		tvCourses.setOnMouseClicked((e->{
			if(e.getButton()==MouseButton.PRIMARY) {
				if(e.getClickCount() == 2) {
					Course c = selectionModel.getSelectedItem();
					mainPane.setTop(null);
					mainPane.setTop(backTopPane);
					mainPane.setCenter(null);
					mainPane.setRight(null);
					mainPane.setCenter(new UpdateCourse().getUpdateCourse(c));
				}
				else {
					setUpdateInfo();
				}
			}
		}));
		
		setData();
		
	}
	
	public void setData()
	{
		courses = DBHandler.getAllCourse();
		tvCourses.getItems().addAll(courses);
	}
	
	public void setUpdateInfo()
	{
		Course c = selectionModel.getSelectedItem();
		Teacher t = DBHandler.getCourseTeacher(c.getTeacher_id());
		teacher.setText(""+t.getTeacherID()+": "+t.getTeacherName());
		students = DBHandler.getAllStudent(c.getCourseID());
		student.setText(""+students.size());
	}
	
	public void getInfoPane()
	{
		infoPane = new GridPane();
		infoPane.add(lteacher, 0, 0);
		infoPane.add(teacher, 1, 0);
		infoPane.add(lnoOfStudents, 0, 1);
		infoPane.add(student, 1, 1);
		infoPane.add(btnDelete, 0, 2);
		infoPane.add(btnShow, 1, 2);
		infoPane.setHgap(20);
		infoPane.setVgap(20);
		infoPane.setAlignment(Pos.TOP_CENTER);
		infoPane.setPadding(new Insets(20));
	}
	
	public void delete()
	{
		Alert alt=new Alert(AlertType.CONFIRMATION);
		Alert alt2 = new Alert(AlertType.INFORMATION);
		alt.setHeaderText("Confirmation");
		alt.setContentText("Do you want to delete?");
		Optional<ButtonType> ans = alt.showAndWait();
		if(ans.isPresent() && ans.get()==ButtonType.OK) {
			Course c = selectionModel.getSelectedItem();
			if(DBHandler.deleteCourse(c.getCourseID())) {
				alt2.setContentText("Successfully Deleted");
				alt2.show();
				tvCourses.getItems().remove(c);
			}
			else
				alt2.setContentText("Can't delete");
				alt2.show();
		}
	}
	
	public BorderPane getCoursePane()
	{
		createNode();
		createTopPane();
		backTopPane();
		createCourseTable();
		getInfoPane();
		mainPane = new BorderPane();
		mainPane.setTop(createTopPane);
		mainPane.setCenter(tvCourses);
		mainPane.setRight(infoPane);
		
		return mainPane;
	}

}
