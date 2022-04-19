package ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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

public class StudentPane {
	private ArrayList<Student> students;
	private ArrayList<Course> courses;
	ArrayList<String> al;
	private Label lcreate, lback, lnoOfCourses, lcourse;
	private ComboBox<String> cbCourses;
	private Button btnCancel, btnDelete, btnShow;
	
	private TableView tvStudents;
	private TableColumn<Student, String> idCol;
	private TableColumn<Student, String> nameCol;
	private TableColumn<Student, String> phoneCol;
	private TableViewSelectionModel<Student> selectionModel;
	private FlowPane addTopPane, backTopPane;
	private GridPane infoPane;
	private BorderPane mainPane;
	
	public void createNode()
	{
		lcreate = new Label("Add Student");
		lcreate.getStyleClass().add("option");
		lcreate.setOnMouseClicked(e->{
			mainPane.setTop(null);
			mainPane.setTop(backTopPane);
			mainPane.setCenter(null);
			mainPane.setRight(null);
			mainPane.setCenter(new AddStudent().getAddStudent());
		});
		lback = new Label("Back");
		lback.getStyleClass().add("option");
		lback.setOnMouseClicked(e->{
			mainPane.setTop(null);
			mainPane.setTop(addTopPane);
			mainPane.setCenter(null);
			tvStudents.getItems().clear();
			setData();
			mainPane.setCenter(tvStudents);
			mainPane.setRight(infoPane);
		});
		
		lnoOfCourses = new Label("No of Course: ");
		lcourse = new Label("");
		cbCourses = new ComboBox<String>();
		btnCancel = new Button("Cancel Enrollment");
		btnCancel.setOnAction(e->{
			cancelEnroll();
		});
		btnDelete = new Button("Delete");
		btnDelete.setOnAction(e->{
			delete();
		});
		btnShow = new Button("Show Course");
		btnShow.setOnAction(e->{
			new ShowCourses(courses);
		});
	}
	
	public void cancelEnroll()
	{
		Alert alt=new Alert(AlertType.CONFIRMATION);
		Alert alt2 = new Alert(AlertType.INFORMATION);
		alt.setHeaderText("Confirmation");
		alt.setContentText("Do you want to cancel enrollment?");
		Optional<ButtonType> ans = alt.showAndWait();
		if(ans.isPresent() && ans.get()==ButtonType.OK) {
			Student s = selectionModel.getSelectedItem();
			String cid = cbCourses.getValue();
			if(DBHandler.deleteEnroll(s.getStudentID(),cid)) {
				alt2.setContentText("Successfully Deleted");
				alt2.show();
				setUpdateInfo();
			}
			else
				alt2.setContentText("Can't delete");
		}
	}
	
	public void delete()
	{
		Alert alt=new Alert(AlertType.CONFIRMATION);
		Alert alt2 = new Alert(AlertType.INFORMATION);
		alt.setHeaderText("Confirmation");
		alt.setContentText("Do you want to delete?");
		Optional<ButtonType> ans = alt.showAndWait();
		if(ans.isPresent() && ans.get()==ButtonType.OK) {
			Student s = selectionModel.getSelectedItem();
			if(DBHandler.deleteStudent(s.getStudentID())) {
				alt2.setContentText("Successfully Deleted");
				alt2.show();
				tvStudents.getItems().remove(s);
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
	
	public void createStudentTable()
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
		
		selectionModel = tvStudents.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
		tvStudents.setOnMouseClicked((e->{
			if(e.getButton()==MouseButton.PRIMARY) {
				if(e.getClickCount() == 2) {
					Student s = selectionModel.getSelectedItem();
					mainPane.setTop(null);
					mainPane.setTop(backTopPane);
					mainPane.setCenter(null);
					mainPane.setRight(null);
					mainPane.setCenter(new UpdateStudent().getUpdateStudent(s));
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
		students = DBHandler.getAllStudent();
		tvStudents.getItems().addAll(students);
	}
	
	public void setUpdateInfo()
	{
		Student s = selectionModel.getSelectedItem();
		courses = DBHandler.getStudentCourse(s.getStudentID());
		lcourse.setText(""+courses.size());
		al = new ArrayList<String>();
		for(Course c: courses) {
			al.add(c.getCourseID());
		}
		cbCourses.setItems(FXCollections.observableArrayList(al));
	}
	
	public void getInfoPane()
	{
		infoPane = new GridPane();
		infoPane.add(lnoOfCourses, 0, 0);
		infoPane.add(lcourse, 1, 0);
		infoPane.add(cbCourses, 0, 1);
		infoPane.add(btnCancel, 1, 1);
		infoPane.add(btnDelete, 0, 2);
		infoPane.add(btnShow, 1, 2);
		infoPane.setHgap(20);
		infoPane.setVgap(20);
		infoPane.setAlignment(Pos.TOP_CENTER);
		infoPane.setPadding(new Insets(20));
	}
	
	public BorderPane getStudentPane()
	{
		createNode();
		createTopPane();
		backTopPane();
		createStudentTable();
		getInfoPane();
		mainPane = new BorderPane();
		mainPane.setTop(addTopPane);
		mainPane.setCenter(tvStudents);
		mainPane.setRight(infoPane);
		
		return mainPane;
	}
}
