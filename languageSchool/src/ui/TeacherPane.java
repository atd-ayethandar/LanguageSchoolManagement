package ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import model.*;

public class TeacherPane {
	private ArrayList<Teacher> teachers;
	private ArrayList<Course> courses;
	private Label lcreate, lback, lnoOfCourses, course;
	private Button btnDelete, btnShow;
	
	private TableView tvTeachers;
	private TableColumn<Teacher, String> idCol;
	private TableColumn<Teacher, String> nameCol;
	private TableColumn<Teacher, String> phoneCol;
	private TableViewSelectionModel<Teacher> selectionModel;
	private FlowPane addTopPane, backTopPane;
	private GridPane infoPane;
	private BorderPane mainPane;
	
	public void createNode()
	{
		lcreate = new Label("Add Teacher");
		lcreate.getStyleClass().add("option");
		lcreate.setOnMouseClicked(e->{
			mainPane.setTop(null);
			mainPane.setTop(backTopPane);
			mainPane.setCenter(null);
			mainPane.setRight(null);
			mainPane.setCenter(new AddTeacher().getAddTeacher());
		});
		lback = new Label("Back");
		lback.getStyleClass().add("option");
		lback.setOnMouseClicked(e->{
			mainPane.setTop(null);
			mainPane.setTop(addTopPane);
			mainPane.setCenter(null);
			tvTeachers.getItems().clear();
			setData();
			mainPane.setCenter(tvTeachers);
			mainPane.setRight(infoPane);
		});
		
		lnoOfCourses = new Label("No of Courses: ");
		course = new Label("");
		btnDelete = new Button("Delete");
		btnDelete.setOnAction(e->{
			delete();
		});
		btnShow = new Button("Show courses");
		btnShow.setOnAction(e->{
			new ShowCourses(courses);
		});
	}
	
	public void delete()
	{
		Alert alt=new Alert(AlertType.CONFIRMATION);
		Alert alt2 = new Alert(AlertType.INFORMATION);
		alt.setHeaderText("Confirmation");
		alt.setContentText("Do you want to delete?");
		Optional<ButtonType> ans = alt.showAndWait();
		if(ans.isPresent() && ans.get()==ButtonType.OK) {
			Teacher t = selectionModel.getSelectedItem();
			if(DBHandler.deleteTeacher(t.getTeacherID())) {
				alt2.setContentText("Successfully Deleted");
				alt2.show();
				tvTeachers.getItems().remove(t);
			}
			else
				alt2.setContentText("Can't delete");
		}
	}
	
	public void createTopPane() {
		addTopPane = new FlowPane(lcreate);
		addTopPane.setAlignment(Pos.CENTER_RIGHT);
		addTopPane.setPadding(new Insets(20));
	}
	
	public void backTopPane() {
		backTopPane = new FlowPane(lback);
		backTopPane.setAlignment(Pos.CENTER_RIGHT);
		backTopPane.setPadding(new Insets(20));
	}
	
	public void createTeacherTable()
	{
		tvTeachers = new TableView<Teacher>();
		
		idCol = new TableColumn<Teacher, String>("Teacher ID");
		idCol.setCellValueFactory(new PropertyValueFactory<Teacher,String>("teacherID"));
	
		nameCol = new TableColumn<Teacher, String>("Teacher Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("teacherName"));
		nameCol.setSortable(true);
		
		phoneCol = new TableColumn<Teacher, String>("Teacher Phone");
		phoneCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("teacherPhone"));
		phoneCol.setSortable(true);
		
		tvTeachers.getColumns().add(idCol);
		tvTeachers.getColumns().add(nameCol);
		tvTeachers.getColumns().add(phoneCol);
		
		selectionModel = tvTeachers.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
		tvTeachers.setOnMouseClicked((e->{
			if(e.getButton()==MouseButton.PRIMARY) {
				if(e.getClickCount() == 2) {
					Teacher t = selectionModel.getSelectedItem();
					mainPane.setTop(null);
					mainPane.setTop(backTopPane);
					mainPane.setCenter(null);
					mainPane.setRight(null);
					mainPane.setCenter(new UpdateTeacher().getUpdateTeacher(t));
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
		teachers = DBHandler.getAllTeacher();
		tvTeachers.getItems().addAll(teachers);
	}
	
	public void setUpdateInfo()
	{
		Teacher t = selectionModel.getSelectedItem();
		courses = DBHandler.getTeacherCourse(t.getTeacherID());
		course.setText(""+courses.size());
	}
	
	public void getInfoPane()
	{
		infoPane = new GridPane();
		infoPane.add(lnoOfCourses, 0, 0);
		infoPane.add(course, 1, 0);
		infoPane.add(btnDelete, 0, 1);
		infoPane.add(btnShow, 1, 1);
		infoPane.setHgap(20);
		infoPane.setVgap(20);
		infoPane.setAlignment(Pos.TOP_CENTER);
		infoPane.setPadding(new Insets(20));
	}
	
	public BorderPane getTeacherPane()
	{
		createNode();
		createTopPane();
		backTopPane();
		createTeacherTable();
		getInfoPane();
		mainPane = new BorderPane();
		mainPane.setTop(addTopPane);
		mainPane.setCenter(tvTeachers);
		mainPane.setRight(infoPane);
		
		return mainPane;
	}
}
