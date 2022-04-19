package ui;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import model.*;

public class CreateCourse {

	ArrayList<Teacher> teachers;
	private Label lid, lname, lsdate, ledate, lteacher, lidErr, lnameErr;
	private TextField tid, tname;
	private DatePicker dsdate, dedate;
	private ComboBox<String> cbTeacher;
	private Button btnCreate, btnClear;
	
	private VBox idVb, nameVb;
	private GridPane mainPane;
	
	public void  createNode()
	{
		lid = new Label("Course ID:");
		lname = new Label("Course Name:");
		lsdate = new Label("Start date:");
		ledate = new Label("End date:");
		lteacher = new Label("Teacher:");
		lidErr = new Label();
		lnameErr = new Label();
		tid = new TextField();
		tid.setOnKeyReleased(e->{
			String id = tid.getText();
			if(Checker.isValidCourseID(id)) {
				lidErr.setText("");
			}
			else
				lidErr.setText("Wrong Id Format. e.g. C001");
		});
		tname = new TextField();
		tname.setOnKeyReleased(e->{
			String name = tname.getText();
			if(Checker.isValidName(name)) {
				lnameErr.setText("");
			}
			else
				lnameErr.setText("Wrong Name Format.");
		});
		dsdate = new DatePicker(LocalDate.now());
		dedate = new DatePicker(LocalDate.now());
		teachers = DBHandler.getAllTeacher();
		ArrayList<String> al = new ArrayList<String>();
		for(Teacher t: teachers) {
			al.add(t.getTeacherID());
		}
		cbTeacher = new ComboBox<String>(FXCollections.observableArrayList(al));
		
		btnCreate = new Button("Create");
		btnCreate.setOnAction(e->{
			createCourse();
		});
		btnClear = new Button("Clear");
		btnClear.setOnAction(e->{
			clear();
		});
	}
	
	public void createCourse()
	{
		String id = tid.getText();
		String name = tname.getText();
		Date sd = Date.valueOf(dsdate.getValue());
		Date ed = Date.valueOf(dedate.getValue());
		String tid = cbTeacher.getValue();
		Alert alt = new Alert(AlertType.INFORMATION);
		if(lidErr.getText() == "" && lnameErr.getText() == "" && tid != "") {
			if(DBHandler.insertCourse(id, name, sd, ed, tid)) {
				alt.setContentText("Successfully created");
				alt.show();
				clear();
			}
			else {
				alt.setContentText("Can't create");
				alt.show();
			}
		}
		else {
			alt.setContentText("Can't create");
			alt.show();
		}
	}
	
	public void clear()
	{
		tid.setText("");
		tid.requestFocus();
		tname.setText("");
		dsdate.setValue(LocalDate.now());
		dedate.setValue(LocalDate.now());
		cbTeacher.getSelectionModel().select(0);
	}
	
	public void defineLayout()
	{
		mainPane = new GridPane();
		mainPane.add(lid, 0, 0);
		idVb = new VBox(tid,lidErr);
		mainPane.add(idVb, 1, 0);
		mainPane.add(lname, 0, 1);
		nameVb = new VBox(tname,lnameErr);
		mainPane.add(nameVb, 1, 1);
		mainPane.add(lsdate, 0, 2);
		mainPane.add(dsdate, 1, 2);
		mainPane.add(ledate, 0, 3);
		mainPane.add(dedate, 1, 3);
		mainPane.add(lteacher, 0, 4);
		mainPane.add(cbTeacher, 1, 4);
		mainPane.add(btnCreate, 0, 5);
		mainPane.add(btnClear, 1, 5);
		mainPane.setHgap(20);
		mainPane.setVgap(20);
		mainPane.setAlignment(Pos.TOP_CENTER);
		mainPane.setPadding(new Insets(20));
	}
	
	public void setStyle()
	{
		lidErr.getStyleClass().add("err");
		lnameErr.getStyleClass().add("err");
		mainPane.getStyleClass().add("centerPane");
	}
	
	public GridPane getCreateCourse()
	{
		createNode();
		defineLayout();
		setStyle();
		
		return mainPane;
		
	}
}
